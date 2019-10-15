- [Docker](#docker)
  - [Khái niệm container](#kh%C3%A1i-ni%E1%BB%87m-container)
  - [Kiến trúc của Docker](#ki%E1%BA%BFn-tr%C3%BAc-c%E1%BB%A7a-docker)
    - [The Docker daemon](#the-docker-daemon)
    - [The Docker client](#the-docker-client)
    - [Docker registries](#docker-registries)
    - [Docker images](#docker-images)
    - [Docker Container](#docker-container)
  - [Quy trình thực thi của một hệ thống sử dụng Docker](#quy-tr%C3%ACnh-th%E1%BB%B1c-thi-c%E1%BB%A7a-m%E1%BB%99t-h%E1%BB%87-th%E1%BB%91ng-s%E1%BB%AD-d%E1%BB%A5ng-docker)
    - [Build](#build)
    - [Push](#push)
    - [Pull, Run](#pull-run)
  - [Các lệnh docker cơ bản](#c%C3%A1c-l%E1%BB%87nh-docker-c%C6%A1-b%E1%BA%A3n)
  - [Dockerfile deloy Server](#dockerfile-deloy-server)
  
# Docker

## Khái niệm container

là một phương pháp để đóng gói phần mềm và tất cả những thứ cần thiết liên quan đến phần mềm đó. Do đó  ứng dụng có thể chạy nhanh và có độ tin cậy cao, có thể chuyển các chương trình từ nơi này sang nơi khác mà không cần bận tâm môi trường làm việc khác nhau giữa các máy mà vẫn có thểchạy tốt. Nó chứa tất cả những thứ cần thiết để có thể chạy phần mềm đó như: code, system tool, các thư viện cần thiết và cài đặt của phần mềm đó giúp ta có thể triển khai ứng dụng một cách dễ dàng mà không cần quan tâm đến môi trường triển khai trên các máy khác nhau. Vì vậy khi chuyển từ máy này sang máy khác không cần cài đặt lại các môi trường làm việc y trên máy cũ. Cung cấp quyền kiểm soát đối với các tài nguyên của hệ thống trên máy tính giúp ta có thể sử dụng tốt hơn tài nguyên của máy tính hiện tại.

## Kiến trúc của Docker

Dockers sử dụng kiến trúc client-server. Docker client nối liên lạc với Docker daemon cái mà có chức năng chủ yếu là building, running và phân phối các Docker container của bạn. Docker client và daemon có thể chạy trên cùng một máy hoặc bạn có thể kết nối Docker client đến Docker daemon từ xa. Docker client và daemin giao tiếp với nhau bằng cách sử dụng REST API, UNIX socket hoặc network interface.

![kiến trúc docker](https://docs.docker.com/engine/images/architecture.svg )

### The Docker daemon

Docker deamon lắng nghe các API mà Docker yêu cầu và quản lí các Docker object như images, container, networks and volumes. daemon cũng có thể giao tiếp với các daemons khác để quản lí các Docker services.

### The Docker client

Là giao diện người dùng của Docker, nó cung cấp cho người dùng giao diện dòng lệnh và thực hiện phản hồi với các Docker daemon. Docker client có thể giao tiếp với nhiều daemon.

### Docker registries

Docker registry lưu trữ Docker images. Docker Hub được công khai mà mọi người đều có thể sử dụng và Docker được cấu hình từ images trên Docker Hub. Khi sử dụng docker pull hặc docker run, yêu cầu images được lấy từ cấu hình trên registry. Khi sử dụng docker push, images được đẩy lên registry của bạn.

### Docker images

Là một template chỉ cho phép đọc, ví dụ một image có thể chứa hệ điều hành Ubuntu và web app. Images được dùng để tạo Docker container. Docker cho phép chúng ta build và cập nhật các image có sẵn một cách cơ bản nhất, hoặc bạn có thể download Docker images của người khác từ Docker registers.

### Docker Container

Docker container có nét giống với các directory. Một Docker container giữ mọi thứ chúng ta cần để chạy một app. Mỗi container được tạo từ Docker image. Docker container có thể có các trạng thái run, started, stopped, moved và deleted.

## Quy trình thực thi của một hệ thống sử dụng Docker

![quy trình thực thi](https://luanbn.files.wordpress.com/2015/08/basics-of-docker-system.png )

### Build

Để có thể build ra thành một docker thì ta phải tạo một dockerile, file này chứa các cấu hình dùng, khi được build trên máy đã được cài đặt Docker Engine thì sẽ tạo ra được Container.

### Push

Sau khi có được Container, để có tể lưu, share images của Docker này trên Docker Hub registry thì ta thực hiện bằng lệnh push Container

### Pull, Run

Giả sử một máy tính muốn sử dụng Container chúng ta đã push lên đám mây (máy đã cài Docker Engine) thì bắt buộc máy phải thực hiện việc Pull container này về máy. Sau đó thực hiện Run Container này.

## Các lệnh docker cơ bản

- Pull một image từ Docker Hub `docker pull {image_name}`
  
- Liệt kê các images hiện có `docker images`

- Xóa một image: docker rmi {image_id/name}

- Liệt kê các container đang chạy `docker ps`

- Xóa một container `docker rm -f {container_id/name}`

- Dừng container `docker stop <container_name>`

- Đổi tên một container `docker rename {old_container_name} {new_container_name}`

- Khởi động một container `docker start {new_container_name}`

- build docker `docker build -t kangzeroo`

- run docker `docker run -d -it -p 80:8080 --name=kz kangzeroo npm run ec2 -- --host=0.0.0.0`
  - p 80:8080 là mapping port 80 giữa máy của ta và port 8080 của container
  - name=kz cung cấp cho container tên kz
  - kangzeroo npm run ec2: tên của images là kangzeroo và lệnh cmd được chạy tương ứng
  - host=0.0.0.0 chạy trên đây thay vì localhost.
Xem các thay đổi trên container:  docker diff {container_name}

## Dockerfile deloy Server

```sh
FROM gcc:latest
WORKDIR /server
COPY . /server
RUN gcc -pthread -o server server.c
CMD ["./server"]
```

Với mỗi dòng trong docker file sẽ tạo ra một image layer được lưu bởi docker. Điều này có nghĩa là khi ta build ra một images mới, Docker sẽ chỉ build các layout khác những lần build trước.

`FROM gcc:lastest` cung cấp phiên bản gcc mới nhất

`WORKDIR /server` tạo và mở thư mục server trên Docker

`COPY . /server` copy tất cả các file cùng đường dẫn hiện tại với dockerfile vào thư mục server

`RUN gcc -pthread -o server server.c` build chương trình server.c trên docker thành file thực tho

`CMD["./server"]` thực thi file server khi docker khởi động

```sh
sudo docker build -t server .
sudo docker run -it --rm --net=host --name game server
```

sau khi đã có dockerfile ta sử dụng lệnh sau đây để build docker image thành docker và chạy docker
