<!-- TOC -->  
- [Database-thinking](#database-thinking)
   - [Các khái niệm về database](#các-khái-niệm-về-database)         
      - [Data](#data)         
      - [Database](#database)         
      - [Database Management System (DBMS)](#database-management-system-dbms)     
   - [SQL](#sql)         
     - [Định nghĩa](#định-nghĩa)         
     - [Một số câu lệnh trong SQL được sử dụng thường xuyên](#một-số-câu-lệnh-trong-sql-được-sử-dụng-thường-xuyên)     
   - [MySQL](#mysql)         
     - [Storege engine](#storege-engine)         
     - [Installation](#installation)             
     - [Cài đặt Mysql](#cài-đặt-mysql)             
     - [Tạo database và table](#tạo-database-và-table)         
     - [Data types](#data-types)         
   - [Transaction](#transaction)             
     - [Transaction là gì](#transaction-là-gì)             
     - [Tại sao phải sử dụng transaction?](#tại-sao-phải-sử-dụng-transaction)             
     - [Cách sử dụng transaction](#cách-sử-dụng-transaction)             
     - [Distributed transaction](#distributed-transaction)             
   - [Isolation](#isolation)                 
      - [Read Uncommitted](#read-uncommitted)                  
      - [Read Committed](#read-committed)                
      - [Repeatable read](#repeatable-read)    
      - [Serializable](#serializable)                
      - [SnapShot](#snapshot)     
   - [Redis](#redis)         
      - [Version](#version)         
      - [Cài đặt redis](#cài-đặt-redis)         
      - [Data Types](#data-types)             
         - [String](#string)            
         - [Hash](#hash)             
         - [List](#list)             
         - [Sets](#sets)             
         - [Sorted Set](#sorted-set)         
      - [Pub/Sub](#pubsub)         
      - [Lock](#lock)             
        - [Pessimistic Locking](#pessimistic-locking)             
        - [Optimistic Locking](#optimistic-locking)             
         - [Distributed lock](#distributed-lock)             
      - [Redlock](#redlock)                
        - [The Redlock algorithm](#the-redlock-algorithm)                 
        - [Retry on failure](#retry-on-failure)     
        - [Tài liệu tham khảo](#tài-liệu-tham-khảo)  
   - [Thiết kế schema ](#thiết-kế-schema)  
   - [Tài liệu tham khảo](#tài-liệu-tham-khảo)  
<!-- /TOC -->


# Database-thinking

## Các khái niệm về database

### Data

Dữ liệu là thông liên liên quan đến một đối tượng mà ta đang xem xét ví dụ như tên, cân nặng, chiều cao của một người.

### Database

Một cơ sở dữ liệu là một tập dữ liệu theo cấu trúc. Database hỗ trợ cho việc lưu trữ và thao tác với dữ liệu như thêm xóa sửa truy vấn. Database làm cho viêc quản lí dữ liệu dễ dàng hơn.

### Database Management System (DBMS)

DBMS là tập hơp các chương trình cho phép người dùng của nó tương tác, truy cập vaò dữ liệu, viết báo cáo hoặc trình bày dữ liệu. Chúng cũng hỗ trợ điều khiển truy cập vào database.

Chúng chia làm 4 loại chính

- Hierarchical: loại này sử dụng quan hệ parent-child để lưu trữ dữ liệu, loại này ngày nay hiếm khi sử dụng ở thời điểm hiện tại, Cấu trúc của nó giống như một cây với node là các giá trị và các nhánh trình bày tên thuộc tính của giá trị đó.
  
- Network DMBS: loại này hỗ trợ cho các quan hệ nhiều nhiều. Do đó nó thường trở thành một cấu trúc dữ liệu phức tạp.

- Relational DBMS: loại này định nghĩa cơ sở dữ liệu quan hệ thành các bảng theo mẫu. Chúng không hỗ trợ cho các quan hệ nhiều nhiều giữa các bảng bằng cách tách chúng ra thành một bảng phụ mới có mối quan hệ giữa hai bảng nhiều nhiều. Loại này được sử dụng nhiều nhất ở thời điểm hiện tại, có các loại như MySQL, Oracel, Microsoft SQL Server database.
  
- Object Oriented Relation DBMS: Loại này hỗ trợ để lưu trữ các kiểu dữ liệu mới. Dữ liệu có thể được lưu trữ ở dạng là một đối tượng (Object). Đối tượng được lưu trữ ở cơ sở dữ liệu có các thuộc tính và các thao tác định nghĩa làm gì với dữ liệu đó. PostgreSQL là một object oriented relational DBMS.

Bên cạnh các chức năng cơ bản, DBMS còn có thể hỗ  trợ

- Hỗ trợ Transaction và concurrency
  
  - Concurrency: hỗ trợ việc xử lí nhiều thao tác trên dữ liệu tại một thời điểm.
  
  - Transaction: một tiến trình xử lý có xác định điểm đầu và điểm cuối, được chia nhỏ thành các phần thực thi khác nhau, tiến trình được thực thi một cách tuần tự và độc lập theo nguyên tắc hoặc tất cả đều thành công hoặc một phép thực thi thất bại thì toàn bộ tiến trình thất bại. Nếu việc thực thi nào đó bị hỏng đồng nghĩa với việc dữ liệu phải rollback (trở lại) trạng thái ban đầu.

- Là cơ sở cho việc hỗ trợ viêc phục hồi database khi có sự cố
- Hỗ trợ cho viêc ủy quyền việc truy cập và cập nhật dữ liệu
- Hỗ trợ viêc truy cập database từ các địa điểm từ xa
- Có các ràng buộc để dữ liệu luôn tuân theo một quy tắc nhất định.  


## SQL

### Định nghĩa

Là viết tắc của Structured Query Language, cho phép bạn truy cập và thao tác với cơ sở dữ liệu một cách dễ dàng. Một số thứ hữu dụng mà SQL cho phép bạn thực hiện

- Cho phép thực hiện các câu truy vấn đối với cơ sở dữ liệu

- Có thể lấy dữ liệu ra từ cơ sở dữ liệu

- Có thể thêm, xóa, sửa các dòng dữ liệu trong bảng

- Có thể tạo mới database, tạo mới bảng trong database, tạo stored procedures trong database

- Cấp phát quyền trên các bảng, procedures.

Trong SQL không phân biệt chữ hoa và chữ thường ở các dòng lệnh, và cần dấu kết thúc ở mỗi câu lệnh là dấu `;`

### Một số câu lệnh trong SQL được sử dụng thường xuyên

- SELECT - lấy dữ liệu từ database

- UPDATE - update dữ liệu các bảng trong database

- DELETE - xóa dữ liệu ở database

- INSERT INTO - thêm dòng mới hoặc bảng mới vào database

- CREATE DATABASE - tạo database

- ALTER DATABASE - thay đổi database

- CREATE TABLE - tạo ra bảng mới trong database

- ALTER TABLE - thay đổi, cập nhật bảng trong database

- DROP TABLE - xóa bảng trong database

- CREATE INDEX - tạo index dùng cho viêc search

- DROP INDEX - xóa index

## MySQL

Là cơ sở dữ liệu với mã nguồn mở phổ  biến nhất thế giới, bên cạnh những tính năng mạnh mẽ nó, MySQL còn dễ dàng cài đặt và sử dụng.

### Storege engine

Là thành phần của MySQL, nó có thể xử lí các hoạt động trên nhiều loại bảng khác nhau để lưu trữ và quản lí thông tin trên database. InnoDB thương được sử dụng thường xuyên và là cấu hình mặc định của MySQL từ phiên bản 5.5 trở về sau.

Để hiển thị trạng thái storege engine của server đang sử dụng thì dùng câu lệnh `show engines;`

Có các loại Storage engines và chúng được sử dụng với các mục đích khác nhau

- InnoDB: Cung cấp transaction-safe (tuân thủ ACID) cho MySQL có các commit, rollback và khả năng khôi phục lỗi khi sự cố xảy ra để bảo vệ dữ liệu của người dùng. Cung cơ chế làm cho nhiều người dùng có thể thao tác đồng thời và cải thiện hiệu suất khi thao tác với database. InnoDB lưu trữ dữ liệu người dùng trong các clustered indexes để giảm I/O cho các truy vấn thông thường dựa trên các primary key. Để duy trì tính toàn vẹn của dữ liệu, InnoDB cũng hỗ trợ các ràng buộc toàn vẹn Foreign Key.

- MyISAM: Table-level locking giới hạn hiệu suất read/write dữ liệu, vì vậy nó thường được sử dụng cho các công việc read-only hoặc read-mostly trong các cấu hình Web và lưu trữ dữ liệu. Không hỗ trợ các ràng buộc toàn vẹn và khóa ngoại và khả năng phục hồi dữ liệu khi gặp sự cố kém

- Memory: Lưu trữ tất cả dữ liệu trên RAM, để truy cập nhanh trong các môi trường đòi hỏi tra cứu nhanh các dữ liệu không quan trọng vì dữ liệu lưu trên gam dễ bị mất. Loại này đang được sử dụng ít dần vì InnoDB cung cấp một bộ nhớ đệm cùng chung mục tiêu và cung cấp cách giữ dữ liệu trong bộ nhớ một cách lâu bền hơn và NDBCLUSTER cung cấp cặp khóa giá trị dùng để tra cứu nhanh cho hệ thống phân tán lớn.

- CSV: bảng của chúng thực sự là một file text với việc phân cách nhau bằng dấu phẩy, cho phép nhận và đổ dữ liệu theo định dạng CSV, cho phép ta dễ dàng đọc và chỉnh sửa, có kích thước nhỏ gọn, xử lí nhanh.vì bảng CSV không được lập chỉ mục nên chỉ dùng cho các hoạt động trong giai đoạn nhập và xuất mà không dùng cho các mục đích khác.

### Installation

#### Cài đặt Mysql

Tải mysql về máy trên ubuntu 16.04

```sh
sudo apt-get update
sudo apt-get install mysql-server
```

Cấu hình Mysql

`mysql_secure_installation`

bước này ta sẽ đặt các mật khẩu và các cấu hình liên quan, nếu muốn xài cấu hình mặc định thì chỉ cần nhấn Y và enter tất cả các gợi ý.

Kiểm tra MySQL để đảm bảo rằng đã cài đặt thành công bằng câu lệnh `systemctl status mysql.service` sẽ hiển thị thông tin như sau

```sh
mysql.service - MySQL Community Server
   Loaded: loaded (/lib/systemd/system/mysql.service; enabled; vendor preset: en
   Active: active (running) since T4 2019-07-10 14:32:51 +07; 1h 29min ago
 Main PID: 24460 (mysqld)
    Tasks: 28
   Memory: 131.3M
      CPU: 3.146s
   CGroup: /system.slice/mysql.service
           └─24460 /usr/sbin/mysqld

Th07 10 14:32:50 cpu11183 systemd[1]: Starting MySQL Community Server...
Th07 10 14:32:51 cpu11183 systemd[1]: Started MySQL Community Server.
```

Kết nối với Mysql server với mysql client

Để kết nối với mysql server với quyền super user  trên linux sử dụng câu lệnh sau
`mysql -u root -p`

```sh
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 11
Server version: 5.7.26-0ubuntu0.16.04.1 (Ubuntu)

Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.
```

Từ đây ta có thể thao tác tạo database, tạo bảng và các thao tác với cơ sở dữ liệu mà ta mong muốn với cú pháp của Mysql.

#### Tạo database và table

Để hiển thị các database đã được khởi tạo sử dụng câu lệnh `show databases;`

Để tạo một database mới,với tên là fresher sử dụng câu lệnh `create database fresher;`

Sau khi đã tạo cơ sở dữ liệu fresher ta muốn truy cập vào để có thể tạo bảng và thao tác trên CSDL đó, ta dùng lệnh use `use fresher;`

Để tạo một bảng trong Mysql có thể tạo một bảng trống với kiểu dữ liệu được định nghĩa trước, tạo bảng dựa trên bảng đã có như dữ liệu và kiểu của bảng đã tồn tại, hoặc là có thể import từ file ra.

```sh
 create table fresher
    -> (
    -> id INT unsigned NOT NULL AUTO_INCREMENT,
    -> name varchar(150) NOT NULL,
    -> PRIMARY KEY (id)
    -> );
```

Tương tự như vậy với các câu lệnh insert update delete và alter.

### Data types

Mysql hỗ trợ hầu hết các kiểu dữ liệu, trong đó có một số kiểu dữ liệu đặc biệt như utf8mb4, chúng có một số đặc điểm như sau

- hỗ trợ tối đa 4 byte cho các kí tự lưu trữ

- đại diện đầy đủ cho tất cả các ký hiệu và kí tự của các ngôn ngữ do con người sử dụng vì nó có thể hỗ trợ gần 1,114,112 kí tự

### Transaction

#### Transaction là gì

Là tiến trình xử lí giai đoạn bắt đầu và giai đoạn kết thúc, đoạn chia nhỏ ra làm nhiều phần thực thi riêng biệt khác nhau. Tiến trình thực thi một cách tuần tự từ đầu đến khi kết thúc theo nguyên tắc là nếu một phép thực thi con thất bại thì tất cả tiến trình đều thất bại, ta phải trở về trạng thái tiến trình lúc vào bắt đầu.

Thuộc tính của transacton
- Atomicity: Một transaction xác định ranh giới của nó rất rõ ràng, tức xác định điểm bắt đầu và kết thúc của tiến trình. Như vậy có thể coi nó như một đơn vị thực thi và đơn vị thực thi này thực hiện theo nguyên tắc thực hiện thành công hay là thất bại tất cả các quá trình.
- Consistency: Dữ liệu nhất quán với transaction ở thời điểm bắt đầu và kết thúc
- Isolation: Nếu hai transaction thực thi cùng lúc thì nguyên tắc thực thi là thực thi độc lập, hai transaction không nhìn biết lẫn nhau, không tác động cùng trên một dữ liệu.
- Durability: Dữ liệu của transaction sau khi thực thi xong được cố định, chính thức và đảm bảo. Nghĩa là những thay đổi đã được cố định, không có chuyện có thể chuyển lại trạng thái dữ liệu lúc trước khi thực hiện transaction.

#### Tại sao phải sử dụng transaction?

Thử lấy ví dụ về một tiến trình rút tiền tại ATM, tại ATM chúng ta phải thực hiện theo trình tự những bước: để thẻ vào, chọn ngôn ngữ, nhập mã pin, chọn loại giao dịch, nhập số tiền, lấy thẻ ra và lấy tiền về.

Nếu không có transaction, lỡ như khi chúng ta đang thực hiện bước rút tiền, ngân hàng sẽ trừ tiền trong tài khoản của bạn, và sau đó đưa tiền ra cho người dùng, nhưng lúc này máy ATM lại gặp trục trặc nên không thể làm điều này. Bạn không thể lấy được tiền và vẫn bị trừ tiền vào tài khoản. Transaction để đảm bảo là khi ta hoàn toàn rút được tiền thì mới trừ tiền vào tài khoản, còn không tất cả các thao tác điều bị hủy, đảm bảo tính đúng đắn của dữ liệu.

#### Cách sử dụng transaction

```mysql
START TRANSACTION
    [transaction_characteristic [, transaction_characteristic] ...]

transaction_characteristic: {
    WITH CONSISTENT SNAPSHOT
  | READ WRITE
  | READ ONLY
}

BEGIN [WORK]
COMMIT [WORK] [AND [NO] CHAIN] [[NO] RELEASE]
ROLLBACK [WORK] [AND [NO] CHAIN] [[NO] RELEASE]
```

Ở trạng thái mặc định, mysql sẽ có chế độ tự động commit có nghĩa là sẽ thực hiện những câu lệnh rồi sau đó tác động vào dữ liệu ngay sau đó và làm cho nó không thể rollback được.

với `START TRANSACTION` autocommit sẽ tạm thời ở chế độ tắt đến khi đến câu lệnh commit hoặc rollback thì nó mới trở về lại chế độ ban đầu.

#### Distributed transaction

Được xem là database transaction được dùng để đồng bộ hoặc cung cấp các cơ chế đồng bộ hoặc thuộc tính ACID giữa nhiều database trong hệ thống phân tán. 

#### Isolation 

Là thuộc tính ACID, Một transaction đang thực thi và chưa được xác nhận phải bảo đảm tính độc lập với các transaction khác.

Mức độ Islation càng thấp thì càng làm tăng khả năng nhiều người dùng có thể truy cập vào cùng một dataabase cùng một thời điểm, nhưng lại làm tăng số lượng kết nối đồng thời vào database và dẫn đến càng nhiều vi phạm, có một số tình huống như sau
- Transaction trước hay sau sẽ được tiến hành hay cả 2 cùng được tiến hành một lúc.
- Kết quả cuối cùng là kết quả của transaction nào trước hay sau? Ở đây xảy ra concurency giữa các transaction.

Có các loại level transaction sau

##### Read Uncommitted

Nếu hai transaction khác nhau cùng truy cập vào một bảng tại một thời điểm thì nó sẽ lấy dữ liệu ngay tại thời điểm vào mà không đợi transaction kia kết thúc.
ta có một bản ghi có giá trị như sau
```sh
INSERT INTO `users` (`id`, `name`, `point`) VALUES ('1', 'BaLongStupid', '1');
```
giả sử có hai câu lệnh transaction sau
Query 1
```sh
START TRANSACTION;
    UPDATE `users` SET `point`= 100;
    SELECT SLEEP(30);
ROLLBACK;
```
và
Query 2
```sh
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
	SELECT * FROM `users`;
COMMIT;
```
Nếu câu lệnh Query 1 thực hiện rồi mới đến câu lệnh Query 2 thì kết quả sẽ là 100 nhưng thực tế là kết quả 1 vì khi Query 2 vào sẽ lấy kết quả của câu lệnh Query 1 lúc này là 100 nhưng sau đó thì Query 1 lại sửa thành 1.

Trường hợp này được gọi là dirty read. Là múc isolation thấp nhất, đảm bảo được tốc độ nhưng sự đúng đắn của dữ liệu thì không chắc chắn.

##### Read Committed

Đây là trạng thái mặc định, Luôn đọc được dữ liệu mới nếu có sự thay đổi trên bảng ghi hiện tại từ transaction khác. Không đảm bảo rằng hai lần đọc trên cùng một bảng khi sẽ giống nhau

##### Repeatable read

Không thể xóa hoặc thay đổi dữ liệu trên bảng khi mà transaction này đang đọc, chỉ có thể thêm dòng. Dữ liệu sẽ được đọc giống như lần đọc đầu tiên.

##### Serializable

Transaction có level cao nhất, khi transaction thực hiện thì nó sẽ khóa các bảng có liên quan và mở khóa khi đã thực hiện xong

Uư điểm: đảm bảo tính đúng đắn của dữ liệu
Nhược điểm: tốn thời gian chờ

##### SnapShot

Tương tự như Serializable, nhưng  có một số điểm khác biệt. Ở đây nó sẽ không khóa bảng mà sẽ tạo một bản sau, để ta có thể update/insert dữ liệu trên bảng đó mà không ảnh hưởng dữ liệu ban đầu. 

Uư điểm: giảm độ trễ
Khuyết điểm: tốn bộ nhớ để sao lưu

## Redis

### Version

Redis có thông số để chỉ thị tiêu chuẩn của các phiên bản là `major.minor.patchlevel`. Các phiên bản stable ứng với các số chẵn như 1.2, 2.0, 2.2, 4.0, 5.0,... Còn các phiên bản lẽ sẽ tương ứng với các unstable như 2.9.x là phiên bản không ổn định.

### Cài đặt redis

để cài đặt redis-server bản mới là 5.0.4 về bằng cách lệnh theo thứ tự sau đây

```sh
   wget http://download.redis.io/releases/redis-5.0.4.tar.gz
   tar xzf redis-5.0.4.tar.gz
   cd redis-5.0.4
   make
```

### Data Types
Redis có 5 cấu trúc dữ liệu chính như sau


#### String

 Redis quản lí các cặp khóa - giá trị mà giá trị ở đây là một string. Chuỗi string trong redis là nhị phân, không có chiều dài xác định hay là kí tự kết thúc chuỗi, do đó ta có thể lưu theo kiểu chuỗi bất kì miễn sao không có độ lớn trên 512MB.

Các câu lệnh sử dụng:

- set key value: đặt giá trị value cho key
- get key:  lấy gía trị được lưu trong key
  
   ```sh
   127.0.0.1:6379> set thuc fresher
   OK
   127.0.0.1:6379> get thuc
   "fresher"
   ```

#### Hash

Redis hash là lệnh sử dụng để quản lý các key/value trong đó value có giá trị là hash.  vì thế chúng là kiểu dữ liệu hoàn hảo tới các đối tượng tương ứng.Trong redis mổi hash có thể lưu trử tới hơn 4 tỷ cặp field-value.

Câu lệnh sử dụng:

- HMSET key field value: đặt giá trị cho field là value trong hash
- HGET key field: lấy giá trị field trong hash.

   ```sh
   127.0.0.1:6379> HMSET user:1 ten "thuc" tuoi "21"
   OK
   127.0.0.1:6379> HGET user:1 ten
   "thuc"
   127.0.0.1:6379> HGET user:1 tuoi
   "21"
   ```

#### List

Redis list là danh sách các chuỗi được sắp xếp theo thứ tự truyền vào, có thể thêm vào đầu bằng LPUSH hoặc vào cuối bằng RPUSH. tương tự như vậy ta cũng có thể lấy các phần tử hoặc cập nhật các phần tử trong List.
Redis Lists là danh sách của các chuỗi, Sắp xếp theo thứ tự chèn vào. Bạn có thể thêm element tới một List Redis vào đầu hoặc vào cuối. Chiều dài tối đa của một list  là hơn 4 tỉ của các element/list
Các câu lệnh đã sử dụng:

- LPUSH key value1: thêm value1 vào đầu danh sách
- LRANGE ket begin end: liệt kê các phần tử trong List từ vị trí begin đến end.

```sh
127.0.0.1:6379> LPUSH fresher chithuc
(integer) 1
127.0.0.1:6379> LPUSH fresher 21
(integer) 2
127.0.0.1:6379> LPUSH fresher VNG
(integer) 3
127.0.0.1:6379> LRANGE fresher 0 2
1) "VNG"
2) "21"
3) "chithuc"
```

#### Sets

Redis Sets là một bộ sưu tập có thứ tự của các chuỗi, bạn có thể thêm, xóa, kiểm tra sự tồn tại của chuỗi trong sets với độ phức tạp O(1). CÁc giá trị trong set không có trùng lặp. Số lượng lớn nhất của member trong set là 232 – 1 (hơn 4 tỉ member/set). Dùng lệnh sadd để thêm giá trị vào Sets, và smembers để kiểm tra tất cả các giá trị có trong Sets đó.

Câu lệnh đã sử dụng:

- SADD: key value1: thêm các giá trị value vào tập hợp
- SMEMBERS: lấy các phần tử trong tập hợp.

   ```sh
   127.0.0.1:6379> SADD fresher thuc
   (integer) 1
   127.0.0.1:6379> SADD fresher 21 vng
   (integer) 2
   127.0.0.1:6379> SMEMBERS fresher
   1) "21"
   2) "thuc"
   3) "vng"
   ```

#### Sorted Set

Kiểu dữ liệu tương tự như Redis Sets, không lặp lại giá trị. Điểm khác biệt ở đây là Sorted Set mỗi giá trị liên kết với một số được xem là độ ưu tiên của số đó  (có thể lặp lai), điểm số này là cơ sở cho viêc tạo ra Sorted Sets có thứ tự.

Các câu lệnh cơ bản:

- ZADD key scroce1 value1 score2 value2: thêm các phần tử vào sorted set với độ uư tiên theo scroce
- ZRANGE:  lấy các phần tử trong tập hợp từ start đến stop theo giá trị score của chúng.

   ```sh
   127.0.0.1:6379> ZADD fresher 1 value1
   (integer) 1
   127.0.0.1:6379> ZADD fresher 2 value2
   (integer) 1
   127.0.0.1:6379> ZADD fresher 5 value5
   (integer) 1
   127.0.0.1:6379> ZADD fresher 3 value3
   (integer) 1
   127.0.0.1:6379> ZRANGE fresher 0 3 WITHSCORES
   1) "value1"
   2) "1"
   3) "value2"
   4) "2"
   5) "value3"
   6) "3"
   7) "value5"
   8) "5"
   ```


### Pub/Sub

Pub/sub không được lập trình theo cơ chế là chọn người nhận và gửi cụ thể để gửi tin nhắn đi mà tin nhắn được đưa vào một chanels, không cần biết người nhận là ai, miễn là có subscribers chanels đó là nhận được thông tin tin nhắn. Người nhận có thể subscrib một hoặc nhiều chanels tùy ý và chỉ nhận tin nhắn từ chanel mà mình đã subscrib mà không biết người gửi là ai

### Lock

là cơ chế đồng bộ đảm bảo việc giới hạn việc truy cập vào các tài nguyên dùng chung nơi mà các thread sử dụng. Lock được thiết kế cho việc đảm bảo cơ chế `mutual exclusion` và `concurrency control`.

- concurrrency control: đảm bảo kết quả đúng trong việc thực thi đồng thời cùng lúc trên cùng một tài nguyên như database hay bộ nhớ trong khi sẽ có các kết quả trả về nhanh nhất có thể
- mutual exclusion là thuộc tính của concurrency control. Ngăn chặn xảy ra tình huống race condition. Nó yêu cầu rằng các thread thực thi sẽ không bao giờ vào miền giăng cùng một lúc.

Lock trong sql là một cơ chế RDBMS ngăn chặn người dùng từ nhiều transactions khác nhau gây ra data conflicts. Locking một row giúp ngăn chặn các transactions khác thay đổi row đó cho đến khi transaction đang truy cập vào nó kết thúc.

#### Pessimistic Locking
Cơ chế này sẽ khóa vào dòng trước khi giá trị của nó bị thay đổi rồi mới thực thi. Nếu có bất kì transaction khác cố gắng truy cập vào dòng đã bị khóa đó, chúng sẽ bị buộc phải chờ cho đến khi transaction đầu tiên hoàn thành.

Uư điểm: đảm bảo tính đúng đắn của dữ liệu khi có nhiều transaction vào cùng một dòng cùng một lúc
Nhược điểm: Chậm tốc độ thực thi của các transaction và có thể xảy ra việc deadlock nếu như transaction A và B yêu cầu tài nguyên của nhau.

#### Optimistic Locking

là phương pháp concurrency control được áp dụng cho hệ thống transaction trên cơ sở dữ liệu quan hệ. Nó giả sử nhiều transaction có thể hoàn thành mà không cần can thiệp vào các transaction khác. Trong khi thực thi, transaction sử dụng dữ liệu mà không cần lock ở tại thời điểm đó, trước khi commit, mỗi transaction sẽ kiểm tra lại xem dữ liệu có bị thay đổi hay chưa nếu có thì nó sẽ rollback lại trạng thái ban đầu và thực thi lại từ đầu.
Uư điểm: tiết kiệm được thời gian thực thi của các transaction, được dùng cho các trường hợp mình đã dự đoán trước đươc tại đó sẽ xảy ra đụng độ, đảm bảo an toàn cho dữ liệu.

Được áp dụng cho các tình huống dữ liệu đó sẽ không có nhiều sự đụng độ. Do đó chi phí thực thi sẽ nhẹ hơn nhiều nhưng khi việc đụng độ xảy ra thường xuyên thì chi phí sẽ cao hơn Pessmistic locking

#### Distributed lock

Trong hệ thống phân tán có nhiều máy chủ và các nguồn tài nguyên cần phải giải quyết, Ta cần một hệ thống để quản lí việc máy chủ nào trong hệ thống xử lí tài nguyên nào một cách độc lập, không nên có TH hai máy chủ cùng xử lí một yêu cầu từ hệ thống. Và khi đó xảy ra nó sẽ thực thi việc ghi vào cùng một vùng data dẫn đến việc lãng phí tài nguyên và tính đúng đắn của dữ liệu.

Để hạn chế việc đó ta cần một hệ thống quản lí việc giải quyết thì ta cần một cơ chế để quản lí việc tài nguyên, yêu cầu nào đang được thực thi ở máy chủ nào để tránh tình trạng giải quyết trùng lắp công việc, sẽ có trạng thái làm việc và server đang xử lí, với các yêu cầu đang được xử lí ta sẽ khóa yêu cầu tại đó lại, để các máy chủ trong hệ thống chọn và xử lí các công việc khác.

#### Redlock

Redis sử dụng thuật toán để thực thi distributed lock được gọi là Redlock, được triển khai bằng DML.

Ta xét trường hợp sau đây, giải pháp  master-slave có lẽ không hoạt động tốt trong trường hợp sau để có thể quản lí distributed lock. Ta xét tại thời điểm này A kêu cầu lock với master, lúc master đang đồng bộ với slave về khóa này thì bị lỗi. Do master lỗi nên slave thay thế master và bị mất thông tin về key vừa được client A yêu cầu. Lúc này client B yêu cầu khóa cùng một tài nguyên với A, điều này sẽ bị vi phạm.

Ta xét ví dụ như sau

`SET resource_name my_random_value NX PX 30000`

Câu lệnh trên set một key nếu nó chưa tồn tại và với thời gian hiệu lực của nó là 30000. key có tên là resource_name và giá trị là  my_random_value. Giá trị này phải là duy nhất trong tất cả các client và các lock request. Và chỉ việc xóa key đó nếu key đó tồn tại và giá trị tại key bằng với giá trị mà người đã yêu cầu nó.

```sh
if redis.call("GET", KEYS[1]) == ARGV[1] then
    return redis.call("DEL", KEYS[1])
else
    return 0
end
```

Điều này rất quan trọng để tránh việc xóa đi lock đã được tạo bởi các client khác. Ví dụ một client yêu cầu lock, nhưng khi thực khi lại quá thời gian mà key đã tồn tại, và sau đó khi thực thi xong lại xóa key đang lưu trữ, lúc này nó đã được yêu cầu bới client khác. Sử dụng Del trong trường hợp này không an toàn. Vì vậy thay vì sử dụng các chuỗi randon để đăng kí cho các key, nó sẽ chỉ xóa đi nếu key đó là được xóa bởi chính client đã yêu cầu.

##### The Redlock algorithm

Giả sử ta có 5 redis master. Các node hoạt động độc lập, không cần sử dụng replication, hoạt động trên các máy khác nhau đảm bảo rằng các node hoàn toàn độc lập với nhau.
Để yêu cầu khóa, client thực thi theo các bước sau:
- Lấy thời gian hiện hành theo đơn vị milisecond
- Nó sẽ cố gắng yêu cầu lock trên tất cả các node trên tuần tự, sử dụng cùng một key và value trên tất cả các node. Trong bước này, khi thiết kế đặt lock trong các instance,  client sử dụng một timeout nhỏ hơn so với tổng thời gian tự động giải phóng lock để tạo lock để tránh trường hợp client kết nối với một master redis bị treo trong thời gian dài và để chuyển sáng node tiếp theo một cách nhanh nhất
- Client tính xem thời gian đã trôi qua khi yêu cầu block là bao nhiêu bằng cách trừ đi thời gian được tạo ở bước 1,  nếu client nhận được lock trong số lượng các instace >=3 và thời gian yêu cầu ít hơn thời gian hiệu lực của lock thì nó sẽ được chấp nhận.
- Thời gian hiệu lực của lock là thời gian đã được tính ở bước 3
- Nếu client không thể nhận được lock vì nó không thể tạo lock trên N/2 + 1 instance, hoặc thời yêu cầu lớn yêu thời gian hiệu lực, nó sẽ cố gắng unlock trên tất cả instance

##### Retry on failure

Khi client không được lock, nó sẽ thử lại sau một khoảng thời gian để tránh tình trạng các client cố gắng tạo lock cho cùng một thời gian ở cùng thời điểm.

## Thiết kế schema 

Sơ đồ SQL Diagram

![x](https://i.imgur.com/5289dQ4.png)


## Tài liệu tham khảo

https://www.w3schools.com/sql/sql_datatypes.asp

https://www.guru99.com/introduction-to-database-sql.html

https://dev.mysql.com/doc/mysql-getting-started/en/#mysql-getting-started-installing

https://dev.mysql.com/doc/refman/8.0/en/storage-engines.html

https://viblo.asia/p/gioi-thieu-cac-storage-engine-trong-mysql-Eb85oEb8Z2G

https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-16-04

https://stackoverflow.com/questions/4034976/difference-between-read-commit-and-repeatable-read

https://redis.io/topics/quickstart

https://redis.io/topics/pubsub

https://redis.io/topics/distlock
