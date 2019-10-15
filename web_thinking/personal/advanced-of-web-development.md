# Kiến thức

## HTTP và Restful API

### AJAX

AJAX Asynchronous JavaScript And XML đây không phải là một ngôn ngữ lập trình, là một bộ công cụ cho phép load dữ liệu từ server mà không yêu cầu tải lại trang. Nó sử dụng chức năng sẵn có XMLHttpRequest(XHR) của trình duyệt để thực hiện một yêu cầu đến server và xử lý dữ liệu server trả về.

AJAX có ba tham số chính như sau
- URL là URL của ngồn Server-Side để Yêu cầu (Request) được gửi tới.    
-Data là là dữ liệu được tích hợp để được truyền tới server theo một định dạng mà server yêu cầu để có thể thực thi thao tác với server, tham số này có thể không bắt buộc
- Callback: tham số đầu tiên được truyền tới hàm này là vdữ liệu được phản hồi từ Server và tham số thứ hai là mã hóa trạng thái.

Uư điểm:
- Dễ dang sử dụng
- tốc độ nhanh, giảm lưu lượng truy cập trên cả server và thời gian phản hồi
- Không cần tải lại trang khi có dữ liệu được gửi dến vì ajax theo cơ chế asynchronous, do đó tăng tốc độ và hiệu suất của trang web.
- AJAX hộ trợ làm việc với form nhanh chóng và hiệu quả.

### HTTP Method

Http method, hay còn gọi là phương thức HTTP, là cách client yêu cầu server phải làm gì với request của mình. Để bắt đầu trao đổi dữ liệu, phía client khởi tạo một HTTP session bằng cách mở một kết nối TCP đến HTTP server sau đó gửi request đến server này. Request có thể được tạo bằng nhiều cách, trực tiếp khi người dùng nhấp vào một liên kết trên trình duyệt hoặc gián tiếp.


#### GET

Phương thức GET thường dùng để lấy dữ liệu ra từ server.

![x](https://s3.ap-southeast-1.amazonaws.com/hocdai/static/png/1/1529294906/get-method.png )

GET được xem là phương thức an toàn, vì nó chỉ lấy thông tin từ server mà không làm thay đổi dữ liệu.

Đặc điểm:
- GET request có thể được cached, bookmark và lưu trong lịch sử của trình duyệt.
- GET request bị giới hạn về chiều dài, do chiều dài của URL là có hạn.
- GET request không nên dùng với dữ liệu quan trọng, chỉ dùng để nhận dữ liệu.

#### POST

Phương thức POST thường được dùng để đưa dữ liệu cho server. POST thường xuất hiện trong các form html hoặc trong các rest api để khởi tạo dữ liệu. Thông thường ta cung cấp dữ liệu cho server thông qua body của request.

![X](https://s3.ap-southeast-1.amazonaws.com/hocdai/static/png/1/1529294929/post-method.png)

Đặc điểm
- POST không thể, cached, bookmark hay lưu trong lịch sử trình duyệt.
- POST không bị giới hạn về độ dài.

#### PUT

Phương thức PUT thường được dùng để cập nhật dữ liệu trong server.

![X](https://s3.ap-southeast-1.amazonaws.com/hocdai/static/png/1/1529294947/put-method.png)

#### DELETE

Phương thức Delete dùng để xóa thông tin ra khỏi server.

![X](https://s3.ap-southeast-1.amazonaws.com/hocdai/static/png/1/1529294968/delete-method.png)

### CORS

CORS là một kĩ thuật được sinh ra để làm cho việc tương tác giữa client và server được dễ dàng hơn, nó cho phép JavaScript ở một trang web có thể tạo request lên một REST API được host ở một domain khác.

CORS được sinh ra là vì same-origin policy, là một chính sách liên quan đến bảo mật được cài đặt vào toàn bộ các trình duyệt hiện nay. Chính sách này ngăn chặn việc truy cập tài nguyên của các domain khác một cách tùy tiện. Same-origin policy giúp an toàn hơn khi lướt web.

CORS sử dụng các HTTP header để báo cho trình duyệt rằng, một ứng dụng web chạy ở domain này có thể truy cập được các tài nguyên ở domain khác. Một truy vấn CORS hợp lệ luôn luôn có Origin ở trong header để xác định địa chỉ gốc gửi lên server.

Chi tiết cụ thể về việc thực thi CORS [tại đây](https://topdev.vn/blog/cors-la-gi/
)
### Session, Cookie, JWT

#### Session 
Một session hay còn gọi là một phiên làm việc. Session là cách giao tiếp giữa client và server với nhau, một session bắt đầu khi client gửi yêu cầu đến server, nó sẽ tồn tại cho đến khi hết thời gian timeout hoặc tắt ứng dụng. Giá trị của session sẽ được lưu trên tập tin của máy chủ. Mỗi session sẽ được cung cấp một SessionID duy nhất khi bắt đầu cho đến kết thúc sau đó sẽ bị hủy đi.

Trình tự thực hiện tạo session như sau
- User nhập thông tin đăng nhập
- Server xác minh các thông tin đó là chính xác và tạo 1 session lưu trên server
- Cookie với session ID sẽ được lưu ở browser
- Các request tiếp theo, session ID sẽ được xác minh và nếu nó hợp lệ, request sẽ được xư lý để truy cập các tài nguyên sâu hơn đc bảo vệ
- Khi user log out khỏi ứng dụng, session sẽ bị destroy ở cả 2 phía client và server.

#### Cookie

Cookie là một phần dữ liệu được lưu ở client. Mỗi khi client gửi một yêu cầu tới máy chủ nào đó, thì nó sẽ gửi phần dữ liệu được lưu trong cookie tương ứng với máy chủ đó.

Cookies sẽ được trình duyệt tự động gửi đi theo mỗi lần truy cập lên máy chủ. Trong quá trình làm việc, cookie có thể bị thay đổi giá trị. Cookie sẽ bị vô hiệu hoá nếu cửa sổ trình duyệt điều khiển cookie đóng lại và cookie hết thời gian có hiệu lực. Cookie thường được dùng ddeeer lưu trữ các thông tin liên quan đến các phiên làm việc khác nhau qua các lần đóng mở session. Vì giao thức HTTP là giao thức không lưu trạng thái nên cookie làm việc như một biến trạng thái để hỗ trợ điều này. 

Hạn chế:
- Dữ liệu không bảo mật do có thể lấy trên browser
- bộ nhớ tối đa 4kb
- Không thể lưu trữ thông tin phức tạp chỉ là những chuỗi string đơn giản


#### JSON Web Token

JSON Web Mã (JWT) là một chuẩn mở định nghĩa một cách nhỏ gọn và khép kín để truyền một cách an toàn thông tin giữa các bên dưới dạng đối tượng JSON. Thông tin này có thể được xác minh và đáng tin cậy vì nó có chứa chữ ký số. Phần chữ ký của JWT sẽ được mã hóa lại bằng HMAC hoặc RSA.

Đặc điểm nổi bật
- Kích thước nhỏ: JWT có thể được truyền thông qua URL, hoặc qua giao thức POST, hay nhét vào bên trong phần HTTP Header. Kích thước nhỏ hơn ứng với công việc truyền tải sẽ nhanh hơn.
- Khép kín: Phần Payload chứa toàn bộ những thông tin mà chúng ta cần tới, khi nhận được token ta có thể lấy thông tin này ra sử dụng.

Khi nào nên dùng:
- Authentication: Đây là kịch bản phổ biến nhất cho việc sử dụng JWT. Một khi người dùng đã đăng nhập vào hệ thống thì những request tiếp theo từ phía người dùng sẽ chứa thêm mã JWT, cho phép người dùng quyền truy cập vào các đường dẫn, dịch vụ, và tài nguyên mà cần phải có sự cho phép nếu có mã Token đó.
- Trao đổi thông tin: JSON Web Token là 1 cách thức không tồi để truyền tin an toàn giữa các thành viên với nhau, nhờ vào phần Signature và thông tin ở phần payload. 

Cấu trúc của JWT
- Header: Phần Header dùng để khai báo kiểu chữ ký và thuật toán mã hóa sẽ dùng cho cái token của chúng ta.
- Payload (Claims): nơi chứa các nội dung của thông tin (claim).
- Signature: được tạo bằng cách kết hợp 2 phần Header + Payload, rồi mã hóa nó lại bằng 1 giải thuật encode như HMAC, SHA-256

### Restful API là gì? Design Restful API như thế nào?

#### Restful API

RESTful API là một tiêu chuẩn dùng trong việc thết kế các thiết kế API cho các ứng dụng web để quản lý các resource. Nó quy định cách sử dụng các HTTP method (như GET, POST, PUT, DELETE...) và cách định dạng các URL cho ứng dụng web để quản lý truy cập đến dữ liệu. RESTful không quy định logic code ứng dụng và không giới hạn bởi ngôn ngữ lập trình ứng dụng.

#### Design Restful API

##### Terninologies

Có một số điều khoản quan trọng liên quan đến REST APIS
- Resource là một đối tượng hoặc một tài nguyên có liên kết với dữ liệu và tập hợp các method để thao tác trên nó
- Collection: là tập dữ liệu, ví dụ như companies
- URL là đường dẫn thông qua đó cáctài nguyên có thể được xác định và thực hiện một số thao tác đến tài nguyên đó

##### API endpoint

Chỉ sử dụng danh từ số nhiều và không sử dụng động từ, thay vào đó đã có các action liên quan đến tài nguyên đó được gửi từ HTTP method(GET,POST,DELETE,PUT). Ví dụ như
- method GET path /companies lấy toàn bộ danh sách công ty
- method GET path /companies/34 lấy công ty có id là 34
- method DELETE path /companies/34 xóa công ty có id là 34


##### HTTP method

Có 4 hoạt động chủ yếu ta có thể kể đến khi làm việc với server
- GET: lấy dữ liệu
- POST: tạo mới
- PUT: cập nhật (thay đổi)
- DELETE: Xóa dữ liệu

![x](https://cdn-images-1.medium.com/max/800/1*YRFNzFCvu0gdRHWoTOctPw.png )

##### HTTP response status code

Khi client gửi yêu cầu đến server thông qua API, thì client cũng muốn biết phản hồi từ server, nếu thất bại thì vì nguyên nhân gì. HTTP status code là một tập các mã được chuẩn hóa với nhiều tình huống khác nhau.

##### Field name casing convention

tên miền trả về nên được thống nhất trong toàn bộ chương trình.

##### Searching, sorting, filtering and pagination

Các thao tác này đều trên một tập dữ liệu, không cần thêm các method mới, mà chỉ cần thêm các tham số vào trong method get trên api.
- Sorting: thêm paramt sort trên trên đường dẫn, ví dụ `GET /companies?sort=rank_asc` sẽ sắp xếp tăng dần theo thứ tự danh sách công ty
- Filtering: dùng cho lọc dữ liệu trên tập dữ liệu, ta có thể thêm các tùy chọn khác nhau, ví dụ `GET /companies?category=banking&location=india` để lọc ra các công ty có category là banking và location là india.
- Searching: `GET /companies?search=Digital Mckinsey`
- Pagination: khi dữ liệu quá lớn ta nên chia dữ liệu thành các phần nhỏ hơn để load lên, cải thiện hiệu suất `GET /companies?page=23`

##### Versioning

Khi có nhu cầu thay đổi API đột ngột có thể dẫn đến chương trình đang sử dụng vì thế nên sử dụng version. Ví dụ
`http://api.yourservice.com/v1/companies/34/employees`

### Server Render và Single Page App

#### Server render

Server render xử lí phần lớn logic đối với file html để hiển thị ra cho người dùng. Khi người dùng vào một trang web, trình duyệt sẽ gửi GET request tới web server, Web server sẽ nhận request và render HTML,Web server sẽ nhận request, đọc dữ liệu từ database, render HTML trả về cho browser để hiển thị cho người dùng.

![x](https://toidicodedao.files.wordpress.com/2018/07/diagram-serversiderendered.png )

Uư điểm
- Load trang nhanh hơn CSR, người dùng có thể bắt đầu nhìn thấy trang web ngay trong khi tất cả quá trình render đang diễn ra. Đối với CSR, ta sẽ cần chờ tới khi tất cả các quá trình trả về file HTML và file JS hoàn thành hết để trang web có thể xem được.
- Dễ code và dễ hiểu, lập trình viên chỉ cần code 1 project không cần tách ra backend và front-end

Nhược điểm
- Mỗi lần chuyển trang là phải load lại trang
- Server chịu taiar năng vì phải xử lí logic nhiều
- Tốn băng thông vì server phải gửi dữ liệu cho client.
- Tương tác với người dung không tốt

#### Single Page App

Single page Application là một ứng dụng web giúp nâng cao trải nghiệm người dùng bằng cách sử dụng HTML5 và AJAX. Đầu tiên khi tải một trang web bất kỳ, SPA sẽ tải một trang HTML đơn, sau đó dựa trên request của người dùng, SPA sẽ tiếp tục tải các HTML khác trong cùng một trang đó.

Ví dụ như một trang web có các header, footer và phần nội dung chính, khi muốn chuyển sang trang khác, ta không cần load lại toàn bộ nội dung trang mới mà chỉ cần load ở phần nội dung bằng cách sử dụng ajax request để lấy dữu liệu cần thiết.

![x](https://images.viblo.asia/904a1aff-a68f-4216-a306-5a987597d3d6.png )

Uư điểm
- Tăng hiệu suất và tốc độ khi lướt web vì lần đầu tiên các tài nguyên của hệ thống như file HTML, CSS, JS sẽ được tải một lần duy nhất trong vòng đời của ứng dụng. sau đó chỉ có dữ liệu được truyền qua lại giữa client và server. 
- SPA tách riêng phần client và server ra, dễ dàng  tách ra để phát triển
- Dễ debug vói chrome, ta có thể dễ dàng theo dõi các hoạt động của mạng, và dữ liệu liên quan
- Dễ dàng để phát triển ứng dụng mobile vì ta chỉ cần sử dụng lại phân backend code trên web
- SPA có thể cache bất từ local storage nào một cách hiệu quả. Ứng dụng chỉ cần gửi request, lưu trữ dữ liệu xuống và ta có thể hoạt động kể cả không có mạng.

Nhược điểm
- Khó để có thể tối ưu SEO (khả năng tìm kiếm ra trang web của mình thông qua các công cụ search)




## Tham khảo

https://viblo.asia/p/mot-so-vi-du-su-dung-ajax-RnB5pGablPG

https://topdev.vn/blog/cors-la-gi/

https://techtalk.vn/session-va-cookies.html

https://techtalk.vn/thiet-ke-restful-apis.html

https://toidicodedao.com/2018/09/11/su-khac-biet-giua-server-side-rendering-va-client-side-rendering/

https://hackernoon.com/restful-api-designing-guidelines-the-best-practices-60e1d954e7c9




