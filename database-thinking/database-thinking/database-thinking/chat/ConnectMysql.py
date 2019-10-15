import mysql.connector
from mysql.connector import errorcode
import json
import hashlib
import requests
import uuid
import schema
import dbConnect

class DbMysql(dbConnect.connectDb):
       # Constructor 
    def __init__(self): 
        self.cnx = mysql.connector.connect(user='root', password='1234',
                              host='localhost',
                              port= 33066,
                              database='db',
                              charset='utf8',
                             use_unicode=True)
        cursor = self.cnx.cursor()
        for table_name in schema.TABLES:
            table_description = schema.TABLES[table_name]
            try:
                print("Creating table {}: ".format(table_name), end='')
                cursor.execute(table_description)
            except mysql.connector.Error as err:
                if err.errno == errorcode.ER_TABLE_EXISTS_ERROR:
                    print("already exists.")
                else:
                    print(err.msg)
            else:
                print("OK")
        cursor.close()


    def getConversationName(self,emailReceive, emailSender):
        return emailReceive + ' ' + emailSender

    def getUserId(self,userInfo):
        if userInfo:
            return userInfo[0]
        return -1

    def getUserName(self,userInfo):
        if userInfo:
            return userInfo[2]
        return -1

    def signUp(self,data):
        cursor = self.cnx.cursor()
        sql_select_Query = "select email from user where email = %s"
        email = data["email"]
        cursor.execute(sql_select_Query,(email,))
        record = cursor.fetchall()
        if(len(record) > 0 ):
            cursor.close()
            return False

        data["password"] = self.get_hashed_password(data["password"])
        add_user = ("INSERT INTO user "
                "(username, password, email) "
                "VALUES (%(username)s, %(password)s, %(email)s)")
        cursor.execute(add_user, data)
        self.cnx.commit()
        cursor.close()
        return True

    def getIdUserFromEmail(self,email):
        cursor = self.cnx.cursor()
        sql_select_Query = "select * from user where email = %s"
        cursor.execute(sql_select_Query,(email,))
        idUser = self.getUserId(cursor.fetchone())
        cursor.close()
        return idUser

    def getUserNameFromEmail(self,email):
        cursor = self.cnx.cursor()
        sql_select_Query = "select * from user where email = %s"
        cursor.execute(sql_select_Query,(email,))
        userName = self.getUserName(cursor.fetchone())
        cursor.close()
        return userName

    def createConversation(self,emailReceive,emailSender,idSender):
        cursor = self.cnx.cursor()
        idRevceive = self.getIdUserFromEmail(emailReceive)
        if(int(idRevceive) == int(idSender)):
            cursor.close()
            return {"code":'fail'}
        userNameReceive = self.getUserNameFromEmail(emailReceive)
        room = self.getIDRoom(int(idSender),idRevceive)
        print(room)
        if (len(room) > 0):
            return {"code":'succes',"id_ConverSation":room,"id_sender":idSender,"id_rev":idRevceive , "use_name_receive": userNameReceive}
        if(idRevceive > 0):
            try:
                conversationName = self.getConversationName(emailReceive,emailSender)
                idConversation = str(uuid.uuid1())
                createConverSationQuery = ("INSERT INTO conversation "
                        "(id_conversation,conversation_name) "
                        "VALUES (%(id_conversation)s,%(conversation_name)s)")
                infoConversation = {
                    'id_conversation':idConversation,
                    'conversation_name': conversationName
                }
                cursor.execute(createConverSationQuery, infoConversation)
                CreateParticipantsQuery = ("INSERT INTO participants "
                "(id_user,id_conversation) "
                "VALUES (%s, %s)")
                cursor.execute(CreateParticipantsQuery,(idRevceive,idConversation))
                cursor.execute(CreateParticipantsQuery,(idSender,idConversation))
                self.cnx.commit()
                cursor.close()
                return {"code":'succes',"id_ConverSation":idConversation,"id_sender":idSender,"id_rev":idRevceive,"use_name_receive": userNameReceive}
            except:
                self.cnx.rollback()
                cursor.close()
                return {"code":'fail'}
        else:
            cursor.close()

        return {"code":'fail'}
    
    def login(self,email,password):

        try:
            cursor = self.cnx.cursor()
            query = ("SELECT * FROM user "
                "WHERE email = %s ")
            cursor.execute(query, (email,))
            result = cursor.fetchone()
            info = {
                'id' : result[0],
                'username': result[2],
                'email': result[3] }
            cursor.close()
            if self.check_password(result[1],password):
                encoded = self.CreateToken(info)
                return {"token": encoded,"code":'succes'}
            else:
                return {"code":'fail'}
        except:
            return {"code":'fail'}

    def getHistoryMessage(self,idConversation):
        cursor = self.cnx.cursor()
        queryMessageHistory = "select * from message where id_conversation = %s"
        cursor.execute(queryMessageHistory,(idConversation,))
        ListMessage =cursor.fetchall()
        cursor.close()
        if ListMessage:
            return ListMessage
        # return empty list
        return ListMessage.clear()
    
    def getIDRoom(self,id_sender,id_rev):
        try:
            cursor = self.cnx.cursor()
            QueryGetListConversation = """select  c.id_conversation from participants  c 
            where c.id_user = %(id_sender)s and  exists(  select * from participants  d 
            where c.id_conversation = d.id_conversation and d.id_user = %(id_revceive)s)"""
            info = {
                'id_sender' : id_sender,
                'id_revceive': id_rev
            }
            cursor.execute(QueryGetListConversation,info)
            ListConversation = cursor.fetchone()
            cursor.close()
            if ListConversation:
                return ListConversation[0]
            return []
        except:
            return []
    
    def getListRoom(self,id_user):
        try:
            if not self.checkExistUser(id_user):
                return []
            cursor = self.cnx.cursor()
            QueryGetListConversation = """select pp.id_conversation,pp.id_user,u.username from participants pp join user u on
                    pp.id_user = u.id_user where
                    pp.id_conversation in (select id_conversation
                    from participants where id_user = %s) and pp.id_user != %s"""

            cursor.execute(QueryGetListConversation,(id_user,id_user))
            ListConversation = cursor.fetchall()
            print(ListConversation)
            cursor.close()
            if ListConversation:
                return ListConversation
            return []
        except:
            return []

    def insertMessage(self,message):
        try:
            cursor = self.cnx.cursor()
            message_query = ("INSERT INTO message "
                "(context, create_at, id_sender, id_conversation) "
                "VALUES (%(context)s, %(create_at)s, %(id_sender)s, %(room)s)")
            cursor.execute(message_query, message)
            self.cnx.commit()
            cursor.close()
            return True
        except:
            return False
    
    def insertFriend(self,idSender, emailReceive):
        try:
            idRev = self.getIdUserFromEmail(emailReceive)
            if(int(idRev) == int(idSender)):
                return False
            cursor = self.cnx.cursor()
            friend_query = ("INSERT INTO friends "
                "(id_sender, id_receive,status) "
                "VALUES (%(id_sender)s, %(id_receive)s, %(status)s)")
            data = {
                'id_sender': idSender,
                'id_receive' : idRev,
                'status': 'friend'
            }
            cursor.execute(friend_query, data)
            self.cnx.commit()
            cursor.close()
            return True
        except:
            return False
    
    def getListFriend(self,id_user):
        try:
            if not self.checkExistUser(id_user):
                return []
            cursor = self.cnx.cursor()
            QueryGetListConversation = """select f.id_sender as id_user,u.username,u.email from friends f join user u on 
                                            f.id_sender = u.id_user where f.id_receive = %s
                                            union
                                            select f.id_receive as id_user,uu.username,uu.email from 
                                            friends f  join user uu on f.id_receive = uu.id_user where f.id_sender=%s"""

            cursor.execute(QueryGetListConversation,(id_user,id_user))
            ListConversation = cursor.fetchall()
            cursor.close()
            if ListConversation:
                return ListConversation
            return []
        except:
            return []
    
    def checkExistUser(self,id_user):
        cursor = self.cnx.cursor()
        sql_select_Query = "select * from user where id_user = %s"
        cursor.execute(sql_select_Query,(id_user,))
        infoUser =  cursor.fetchone()
        cursor.close()
        if infoUser:
            return True
        return False

