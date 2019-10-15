
 - [Rock-Scissors-Paper](#rock-scissors-paper) 
    - [Thiết kế tổng quan](#thiết-kế-tổng-quan)   
    -  [Sequence diagram](#sequence-diagram)         
       - [Register](#register)         
       - [Login](#login)         
       - [Create game](#create-game)         
       - [Play game](#play-game)         
       - [History game of user](#history-game-of-user)         
       - [Ranking](#ranking)     
    - [Database design](#database-design)     
    - [API description](#api-description)         
      - [Register](#register-1)         
      - [Login](#login-1)
      -   [Create game](#create-game-1)         
      - [Play game](#play-game-1)         
      - [Get Top](#get-top)         
      - [Get History](#get-history)     
    - [Performance](#performance) 
# Rock-Scissors-Paper

## Thiết kế tổng quan

![x](image/Architecture&#32;bao&#32;bua&#32;keo.png)

## Sequence diagram

### Register

![x](image/Register&#32;Sq.png)

### Login

![x](image/login&#32;sq.png)

### Create game

![x](image/creategame&#32;sq.png)

### Play game

![x](image/play&#32;game.png)

### History game of user

![x](image/history.png)

### Ranking

![x](image/top.png)

## Database design

![x](image/db.png)

## API description

### Register

Data request: gửi thông tin tài khoản và mật khẩu lên để đăng kí, sau đó dùng thông tin này để đăng nhập
```json
{
    "username":"thuc",
    "password":"1234
}

```

Data respones: Trả về tin nhắn và trạng thái, succes khi thực thi thành công, fail khi thất bại

```json
{
    "message": "Đăng kí thành công ",
    "status": "succes",
    "data": null
}
```

### Login

Data request: gửi mật khẩu và tài khoản lên để kiểm tra có đúng thông tin hay không
```json
{
    "username":"thuc",
    "password":"1234
}

```

Data respones: trả về dữ liệu và token, kèm theo trạng thái và tin nhắn từ server,fail nếu thất bại và succces khi thành công.

```json
{
    "message": "Đăng nhập thành công",
    "status": "succes",
    "data": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwidXNlcm5hbWUiOiJ0aHVjIiwiZXhwIjoxNTY1MDE3MDQ3fQ.9dRxnt1R71ikThO9sUu6G7NpirOykXsfRA0rZf5Wb-g"
}
```

### Create game

Data request: gửi token sau khi đã login lên server trong đó có kèm thông tin user để tạo game mới

Data respones: trả về trạng thái và tin nhắn từ server, succes nếu thành công và fail nếu thất bại

```json
{
    "message": "Tạo game mới thành công",
    "status": "succes",
    "data": {
        "idUser": 1,
        "result": 2,
        "idGame": 505
    }
}
```

### Play game

Data request: gửi token sau khi đã login lên server trong đó có kèm thông tin user để tạo game mới, kèm theo id game và bao búa kéo của user tương đương với những con số
```java
    public final int isWin = 1;
    public  final int isLose = 0;
    public final  int isDraw = 2;
    public  final int bao =1;
    public  final int bua =2;
    public  final int keo = 3;
```

data respone: trả về trạng thái và tin nhắn từ server, succes nếu thành công và fail nếu thất bại và thông tin của lượt chơi vừa rồi

```json
{
    "message": "Server choi: 1Ket qua: 2",
    "status": "succes",
    "data": {
        "userPlay": 2,
        "serverPlay": 1,
        "idGame": 1,
        "idTurn": 209
    }
}
```
### Get Top

Data request: gửi request get lên server để lấy top 100 người, ở đây có tùy chọn là số người trong top.

`localhost:8080/users/top?top=100`

data respone: trả về trạng thái và tin nhắn từ server, succes nếu thành công và fail nếu thất bại và tên của các user trong top.
```json
{
    "message": "Lấy danh sách top tỉ lệ thắng thành công",
    "status": "succes",
    "data": [
        [
            "6e7eaae5baef",
            1.0
        ],
        [
            "00909daa8ab5",
            1.0
        ],
        [
            "3bfc8b637c39",
            1.0
        ]
    ]
}
```

### Get History


Data request: gửi token sau khi đã login lên server trong đó có kèm thông tin user để lấy danh sách game và lượt chơi của user

Data respones: trả về trạng thái và tin nhắn từ server, succes nếu thành công và fail nếu thất bại

```json
{
    "message": "Lấy lịch sử game của người dùng thành công,iduser: 1",
    "status": "succes",
    "data": [
        {
            "userName": "thuc",
            "result": 0,
            "userPlay": 2,
            "serverPlay": 2
        },
        {
            "userName": "thuc",
            "result": 0,
            "userPlay": 2,
            "serverPlay": 1
        },
        {
            "userName": "thuc",
            "result": 0,
            "userPlay": 2,
            "serverPlay": 1
        }
    ]
}
```

## Performance

đây là kết quả test khi chạy 500 user trên các api 

![x](image/performance.png)