from abc import ABC, abstractmethod
import jwt
from passlib.hash import sha256_crypt

class connectDb(ABC):

    def _init_(self):
        super().__init__()
    
    @abstractmethod
    def createDB(self):
        pass

    @abstractmethod
    def signUp(self,data):
        pass
    
    @abstractmethod
    def login(self,email,password):
        pass

    def CreateToken(self,info):
        token = jwt.encode(info, 'fresher', algorithm='HS256')
        return token.decode("utf-8")

    def decodeToken(self,token):
        info = jwt.decode(token, 'fresher', algorithms=['HS256'])
        return info

    def get_hashed_password(self,password):
        return sha256_crypt.hash(password)

    def check_password(self,hashed_password, user_password):
        return sha256_crypt.verify(user_password, hashed_password)
    @abstractmethod
    def createConversation(self,emailReceive,emailSender,idSender):
        pass        

    @abstractmethod
    def getHistoryMessage(self,idConversation):
        pass

    @abstractmethod
    def getIDRoom(self,id_sender,id_rev):
        pass
   
    @abstractmethod
    def getListRoom(self,id_user):
        pass

    @abstractmethod
    def insertMessage(self,message):
        pass
        
    @abstractmethod
    def insertFriend(self,idSender, emailReceive):
        pass
    