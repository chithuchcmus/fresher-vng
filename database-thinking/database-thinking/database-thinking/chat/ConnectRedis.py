import json
import jwt
import hashlib
import uuid
import schema
import dbConnect
import redis


class RedisDB(dbConnect.connectDb):
       # Constructor 
    def __init__(self): 
        try:
            self.redis = redis.Redis(host='localhost',port=6379,db=0)
            print("connect success")
        except:
            print('Failed to connect')

    def createDB(self):
        return
    def signUp(self,data):
        try:
            if self.getIdUserFromEmail(data["email"]) > 0:
                return False
            self.redis.incr('userid')
            userId = self.redis.get('userid').decode('utf8')
            hashPassword = self.get_hashed_password(data["password"])
            userInfo = {
                'user_name':data["username"],
                'password': hashPassword,
                'email': data["email"]
            }
            if self.redis.hmset('user:' + str(userId),userInfo) == False:
                return False
            return True
        except:
            return False
        
    def login(self,email,password):
        try:
            userId = -1
            username = ''
            userEmail = ''
            hashPassword = ''
            for key in self.redis.scan_iter("user:*"):
                user = self.redis.hgetall(key)
                bUsername = user[b'user_name']
                bUserEmail=user[b'email']
                bPassword=user[b'password']
                if bUserEmail == bytes(email,'utf8'):
                    userId= int(key.decode('utf8').split(':')[1])
                    userEmail = bUserEmail.decode('utf8')
                    username = bUsername.decode('utf8')
                    hashPassword = bPassword.decode('utf8')
                    break
            info = {
                'id' : userId,
                'username': username,
                'email': userEmail
            }
            if self.check_password(hashPassword,password):
                encoded = self.CreateToken(info)
                return {"token": encoded,"code":'succes'}
            else:
                return {"code":'fail'}
        except:
            return {"code":'fail'}

    def getIdUserFromEmail(self,email):
        try:
            userId = -1
            for key in self.redis.scan_iter("user:*"):
                user = self.redis.hgetall(key)
                bUserEmail=user[b'email']
                if bUserEmail == bytes(email,'utf8'):
                    userId= int(key.decode('utf8').split(':')[1])
                    break
            return int(userId)
        except:
            return -1

    def getUserNameFromEmail(self,email):
        try:
            UserName = ''
            for key in self.redis.scan_iter("user:*"):
                user = self.redis.hgetall(key)
                bUserName=user[b'user_name']
                bUserEmail=user[b'email']
                if bUserEmail == bytes(email,'utf8'):
                    UserName = bUserName.decode('utf8')
                    break
            return UserName
        except:
            return ''

    def createConversation(self,emailReceive,emailSender,idSender):
        idReceive = int(self.getIdUserFromEmail(emailReceive))
        if(idReceive <= 0):
            return {"code":'fail'}
        if(idReceive == int(idSender)):
            return {"code":'fail'}
        userNameReceive = self.getUserNameFromEmail(emailReceive)
        idConversation = self.getIDRoom(int(idSender),idReceive)
        if len(idConversation) > 0:
            print("da ton tai room")
            return {"code":'succes',"id_ConverSation":idConversation,"id_sender":idSender,"id_rev":idReceive , "use_name_receive": userNameReceive}
        else:
            try:
                conversationName = emailReceive + ' '+ emailSender
                idConversation = str(uuid.uuid1())
                ConversationInfo = {
                    'id_conversation':idConversation,
                    'conversation_name':conversationName
                }
                self.redis.hmset('conversation:' + idConversation,ConversationInfo)
                self.redis.incr('participantsid')
                participantsId = self.redis.get('participantsid').decode('utf8')
                participantInfo = {
                    'id_user1': idSender,
                    'id_user2': idReceive,
                    'id_conversation': idConversation 
                }
                self.redis.hmset('participants:' + str(participantsId),participantInfo)
                return {"code":'succes',"id_ConverSation":idConversation,"id_sender":idSender,"id_rev":idReceive,"use_name_receive": userNameReceive} 
            except:
                return {"code":'fail'}

    def getHistoryMessage(self,idConversation):
        ListMessage = []
        for key in self.redis.scan_iter('message:*'):
                message = self.redis.hgetall(key)
                bContext = message[b'context']
                bCreate = message[b'create_at']
                bIdSender = message[b'id_sender']
                bIdRoom = message[b'room'].decode('utf8')
                if bIdRoom == idConversation:
                    messageContext = []
                    messageId= int(key.decode('utf8').split(':')[1])
                    messageContext.append(messageId)
                    messageContext.append(bContext.decode('utf8'))
                    messageContext.append(bCreate.decode('utf8'))
                    messageContext.append(int(bIdSender.decode('utf8')))
                    messageContext.append(idConversation)
                    ListMessage.append(messageContext)
        return ListMessage
    
    def getIDRoom(self,id_sender,id_rev):
        conversationId = ''
        for key in self.redis.scan_iter("participants:*"):
                participant = self.redis.hgetall(key)
                IdUser1= int(participant[b'id_user1'].decode('utf8'))
                IdUser2= int(participant[b'id_user2'].decode('utf8'))
                bIdConversation = participant[b'id_conversation']
                if (IdUser1 == id_sender and IdUser2 == id_rev) or (IdUser1 ==id_rev and IdUser2 == id_sender):
                    print("da vao")
                    conversationId = bIdConversation.decode('utf8')
                    break
        return conversationId
    
    def getListRoom(self,id_user):
        listRoom = []
        for key in self.redis.scan_iter("participants:*"):
        
                participant = self.redis.hgetall(key)
                idUser1 = participant[b'id_user1']
                idUser2 = participant[b'id_user2']
                idRoom = participant[b'id_conversation'].decode('utf8')              
                if idUser1 == bytes(id_user,'utf8'):
                    idUser = int(idUser2.decode('utf8'))
                    info = self.getUserInfoForConversation(idRoom,idUser)
                    listRoom.append(info)
                if idUser2 == bytes(id_user,'utf8'):
                    idUser = int(idUser1.decode('utf8'))
                    info = self.getUserInfoForConversation(idRoom,idUser)
                    listRoom.append(info)
        return listRoom

    def insertMessage(self,message):
        try:
            self.redis.incr('messageid')
            messageId = int(self.redis.get('messageid').decode('utf8'))
            messageInfo = {
                'context':message["context"],
                'create_at': message["create_at"],
                'id_sender': message["id_sender"],
                'room' : message["room"]
            }

            if self.redis.hmset('message:' + str(messageId),messageInfo) == False:
                return False
            return True
        except:
            return False
    
    def insertFriend(self,idSender, emailReceive):
        try:
            idReceive = int(self.getIdUserFromEmail(emailReceive))
            if idReceive <= 0:
                return False
            if(idReceive == int(idSender)):
                return False
            self.redis.incr('friendid')
            friendId = int(self.redis.get('friendid').decode('utf8'))
            friendInfo = {
                'id_sender': int(idSender),
                'id_receive' : idReceive,
                'status': 'friend'
            }
            if self.redis.hmset('friend:' + str(friendId),friendInfo) == False:
                return False
            return True
        except:
            return False
    
    def getListFriend(self,id_user):
        ListFriend = []
        for key in self.redis.scan_iter('friend:*'):
                friend = self.redis.hgetall(key)
                idUserSend = int(friend[b'id_sender'].decode('utf8'))
                idUserReceive = int(friend[b'id_receive'].decode('utf8'))
                if idUserSend == int(id_user):
                    info = self.getUserInfoForFriend(idUserReceive)
                    ListFriend.append(info)
                if idUserReceive == int(id_user):
                    info = self.getUserInfoForFriend(idUserSend)
                    ListFriend.append(info)
        return ListFriend
    
    def checkExistUser(self,id_user):
        try:
            for key in self.redis.scan_iter("user:*"):
                if id_user == int(key.decode('utf8').split(':')[1]):
                    return True
            return False
        except:
            return False

    def getUserInfoForConversation(self,idConversation,idUser):
        userInfo = []
        userInfo.append(idConversation)
        for keyUser in self.redis.scan_iter("user:*"):
            user = self.redis.hgetall(keyUser)
            username = user[b'user_name'].decode('utf8')
            if idUser == int(keyUser.decode('utf8').split(':')[1]):
                userInfo.append(idUser)
                userInfo.append(username)
                break
        return userInfo
    #id_user,u.username,u.emai
    def getUserInfoForFriend(self,idUser):
        userInfo = []
        for keyUser in self.redis.scan_iter("user:*"):
            user = self.redis.hgetall(keyUser)
            username = user[b'user_name'].decode('utf8')
            email = user[b'email'].decode('utf8')
            if int(idUser) == int(keyUser.decode('utf8').split(':')[1]):
                userInfo.append(int(idUser))
                userInfo.append(username)
                userInfo.append(email)
                print(userInfo)
                break

        return userInfo
    