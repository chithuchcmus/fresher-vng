import redis

r= redis.Redis(host='localhost',port=6379,db=0)

user = {
    'user_name':'thuc',
    'password':'1234'
}

r.set("counter", 40) 
print(r.get("counter"))
print(r.incr("counter"))
print(r.incr("counter"))
print(r.get("counter"))

user = ''
print(len(user))
print(type(user))