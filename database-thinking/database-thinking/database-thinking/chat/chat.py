from flask import Flask, render_template,request,jsonify
from flask_socketio import SocketIO,send,emit,join_room, leave_room
import ConnectMysql
import ConnectRedis
import dbConnect

app = Flask(__name__)
socketio = SocketIO(app)

db = ConnectRedis.RedisDB()
@app.route('/chat')
def chat():
    return render_template('chat1.html') 

@app.route('/')
def home():
    return render_template('index1.html')

@app.route('/friend')
def friend():
    return render_template('friend.html')


@app.route('/menu')
def menu():
    return render_template('menu.html')


@app.route('/api/me', methods = ['POST', 'GET'])
def checkToken():
    header =  request.headers['Authorization']
    bearer = header.split(' ')
    token = bearer[1]
    infoUser = db.decodeToken(token)
    if infoUser:
        return jsonify({"code":'succes',"data":infoUser})
    return jsonify({"code":'fail'})

@app.route('/api/conversation', methods = ['POST'])
def createConversation():
    if request.is_json == True:
        data = request.get_json()
        emailReceive = data["emailReceive"]
        emailSender = data["emailSender"]
        idSender = data["idSender"]
        info = db.createConversation(emailReceive,emailSender,idSender)
        print(info)
        return jsonify(info)

    return jsonify({"code":'fail'})

@app.route('/api/friends', methods = ['POST'])
def makeFriend():
    if request.is_json == True:
        data = request.get_json()
        emailReceive = data["emailReceive"]
        idSender = data["idSender"]
        print(emailReceive)
        if db.insertFriend(idSender,emailReceive):
            return jsonify({"code" : 'succes'})
        return jsonify({"code":'fail'})
    return jsonify({"code":'fail'})

@app.route('/api/friends', methods = ['GET'])
def getFriends():
    if request:
        id_user = request.args.get('id_user')
        listRoom  = db.getListFriend(id_user)
        return jsonify({"data":listRoom,"code":'succes'})
    return jsonify({"code": 'fail'})

@app.route('/api/auth/login', methods=['POST'])
def login():
    if request.is_json == True:
        data = request.get_json()
        email = data["email"]
        password = data["password"]
        res = db.login(email,password)
        if(res["code"] == "fail"):
            return jsonify(res)
        else:
            return jsonify(res)
    return jsonify({"code": 'fail'})


@app.route('/api/conversations', methods=['GET'])
def getListRoom():
    if request:
        id_user = request.args.get('id_user')
        listRoom  = db.getListRoom(id_user)
        print(listRoom)
        return jsonify({"data":listRoom,"code":'succes'})
    return jsonify({"code": 'fail'})


@app.route('/api/auth/register', methods=['POST'])
def register():
    if request.is_json == True:
        data = request.get_json()
        check = db.signUp(data)
        if(check  is True):
            return jsonify({"code":'success'})
        else:
           return jsonify({"code":'fail'})
    return jsonify({"code": 'fail'})

@app.route('/api/messages', methods=['GET'])
def getMessage():
    if request:
        id_conversation = request.args.get('id_conversation')
        listRoom  = db.getHistoryMessage(id_conversation)
        return jsonify({"data":listRoom,"code":'succes'})
    return jsonify({"code": 'fail'})

@app.route('/api/participants', methods=['GET'])
def getUserSameRoom():
    if request:
        id_conversation = request.args.get('id_conversation')
        listRoom  = db.getHistoryMessage(id_conversation)
        return jsonify({"data":listRoom,"code":'succes'})
    return jsonify({"code": 'fail'})

@socketio.on('register_room')
def registerRoom(data):
    idRev = data["id_receive"]
    idSender = int(data["id_sender"])
    room = data["room"]
    emit('start chat',{'room': room},room = idRev)
    emit('start chat',{'room': room},room = idSender)


@socketio.on('register_userRoom')
def registerUserRoom(data):
    idRoom = data["id"]
    join_room(idRoom)

@socketio.on('chat event')
def handle_message(message):
    db.insertMessage(message)
    emit('response',message , room = message["room"])


if __name__ == '__main__':
    # info = {
    #     "email":'thuckhpro@gmail.com',
    #     "password":'1234',
    #     "username":'thuc'
    # }
    # db.signUp(info)
    # db.login('thuckhpro@gmail.com','1234')
    #db.createDB()
    socketio.run(app, debug=True)
