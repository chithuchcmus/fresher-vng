
- [1. Java - thinking](#1-java---thinking)
  - [1.1. Lý thuyết](#11-l%c3%bd-thuy%e1%ba%bft)
    - [1.1.1. Unit Test/Logging/Performance](#111-unit-testloggingperformance)
      - [1.1.1.1. Unit test](#1111-unit-test)
      - [1.1.1.2. Logging](#1112-logging)
      - [1.1.1.3. throughput và latency, P99](#1113-throughput-v%c3%a0-latency-p99)
    - [1.1.2. Threading [R]](#112-threading-r)
      - [1.1.2.1. Khái niệm Thread, multithreading & concurrency](#1121-kh%c3%a1i-ni%e1%bb%87m-thread-multithreading--concurrency)
        - [1.1.2.1.1. Thread](#11211-thread)
        - [1.1.2.1.2. MultiThread](#11212-multithread)
        - [1.1.2.1.3. Concurrency](#11213-concurrency)
      - [1.1.2.2. Thread-safe trong Java, làm sao đạt được Thread-safe?](#1122-thread-safe-trong-java-l%c3%a0m-sao-%c4%91%e1%ba%a1t-%c4%91%c6%b0%e1%bb%a3c-thread-safe)
      - [1.1.2.3. Tìm hiểu về Thread Pool, Executors.](#1123-t%c3%acm-hi%e1%bb%83u-v%e1%bb%81-thread-pool-executors)
        - [1.1.2.3.1. ThreadPool](#11231-threadpool)
        - [1.1.2.3.2. Executors](#11232-executors)
    - [1.1.3. Networking](#113-networking)
      - [1.1.3.1. Connection pooling](#1131-connection-pooling)
      - [1.1.3.2. Caching, Caching với guava, redis](#1132-caching-caching-v%e1%bb%9bi-guava-redis)
        - [1.1.3.2.1. Caching](#11321-caching)
        - [1.1.3.2.2. Guave Cache](#11322-guave-cache)
        - [1.1.3.2.3. Redis Cache](#11323-redis-cache)
      - [1.1.3.3. Khái niệm cơ bản về protocol trong networking](#1133-kh%c3%a1i-ni%e1%bb%87m-c%c6%a1-b%e1%ba%a3n-v%e1%bb%81-protocol-trong-networking)
        - [1.1.3.3.1. http](#11331-http)
        - [1.1.3.3.2. Websocket](#11332-websocket)
        - [1.1.3.3.3. gRPC](#11333-grpc)
        - [1.1.3.3.4. SSL/TLS](#11334-ssltls)
        - [1.1.3.3.5. RESTful API](#11335-restful-api)
    - [1.1.4. Benchmark](#114-benchmark)
      - [1.1.4.1. Benchmark](#1141-benchmark)
      - [1.1.4.2. Jmeter](#1142-jmeter)
      - [1.1.4.3. Các tool hỗ trợ benchmark hệ thống dành cho Java? So sánh ưu, nhược điểm?](#1143-c%c3%a1c-tool-h%e1%bb%97-tr%e1%bb%a3-benchmark-h%e1%bb%87-th%e1%bb%91ng-d%c3%a0nh-cho-java-so-s%c3%a1nh-%c6%b0u-nh%c6%b0%e1%bb%a3c-%c4%91i%e1%bb%83m)
      - [1.1.4.4. Locust](#1144-locust)
    - [1.1.5. JVM](#115-jvm)
      - [1.1.5.1. JVM ? How it work ?](#1151-jvm--how-it-work)
      - [1.1.5.2. JRE vs JDK?](#1152-jre-vs-jdk)
  - [1.2. Tham khảo](#12-tham-kh%e1%ba%a3o)
# 1. Java - thinking

## 1.1. Lý thuyết

### 1.1.1. Unit Test/Logging/Performance 

#### 1.1.1.1. Unit test

Đảm bảo rằng phần code sẽ thực hiện một cách đúng đắn như là các hàm, các method, object, class. Các đoạn mã nguồn được test  bởi Unit test được gọi là test coverage. Unit test là một cách tốt để thực hiện regression testing: mỗi khi có sự thay đổi về code và bạn muốn rằng code mình vẫn chạy đúng như trước, không bị lỗi và phát hiện ra các vấn đề? Thì sử dụng Unit test để có thể phát hiện ra các lỗi và khắc phục dễ dàng.
 
Đối với các phần mã nguồn quan trọng của chương trình cần viết Unit Test để đảm bảo rằng chúng hoạt động tốt, có các trường hợp như sau
- in base cases(trường hợp thông thường): truyền vào các biến và kiểm tra xem các kết quả trả về có theo mong đợi hay không
- in corner test (trường hợp đặc biệt): truyền vào các tham số với các giá trị đặc biệt hoặc các biên giới hạn để kiểm thử chương trình có hoạt động được hay không như 0, -1, MAX_INT
- in case of failure (trường hợp lỗi): kiểm tra  quy trình xử lí khi gặp lỗi của chương trình có đúng đắn hay không.

Các Unit test nên được thực thi độc lập không lồng vào nhau. Và Các Unit Test được thực thi trong hệ thống riêng biệt với chương trình.

Ứng dụng của Unit Test:
- Có thể kiểm tra các thành phần nhỏ như các thuộc tính, hàm, thủ tục.
- Kiểm tra các trạng thái và ràng buộc của đối tượng 
- Kiểm tra các quy trình hoạt động work-flow

Lợi ích của Unit test:
- Có khả năng dò và phát hiện lỗi chính xác
- Phát hiện ra các thuật toán không hiệu quả, chạy quá giới hạn thời gian
- Phát hiện ra các lỗi xảy ra trong các trường hợp hiếm

#### 1.1.1.2. Logging

Log là quá trình khi lại những thông báo, quá trình hoạt động của chương trình và lưu chúng ở chung một nơi thường là các file. Những thông tin được xuất ra sẽ theo một định dạng nhất định như là nó đang xử lí ở hàm class nào, thông điệp là gì.

Lợi ích của việc dùng log:
- khi chương trình xảy ra lỗi ta khó có thể tìm được chi tiết chính xác lỗi của chương trình ở đâu, dựa vào file log và các thông tin trong đó xem chương trình đã hoạt động thế nào và đến đâu để có thể xác định lỗi chương trình
- Ghi nhận lại quá trình hoạt động của chương trình
- Ghi nhận lại các trường hợp lỗi hiếm xảy ra khi chương trình hoạt động ổn định để có thể khắc phục.

Log được phân loại theo mục đích sử dụng và chia theo các level: ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF
- All: Mọi thông tin sẽ được log vào đây, đây là level thấp nhất
- Debug: Các thông tin dùng để debug được log ra, có thể bật tắt dựa vào mode của chương trình
- Info: Các thông tin mà ta muốn ghi nhận thêm vào log file như số lượng request
- Warning: ghi nhận các cảnh báo của chương trình
- Error: Ghi nhận ra các lỗi của chương trình và các phần thông tin liên quan đến lỗi đó
- Fatal: Ghi nhận lại các lỗi nghiêm trọng của chương trình khiến chương trình bị dừng hoạt động
- Off: là cấp độ cao nhất, dùng khi không muốn log thông tin nào nữa

#### 1.1.1.3. throughput và latency,  P99

Latency: thời gian để một công việc từ khi được yêu cầu cho đến khi được hoàn thành. Có thể hình dung Letency là thời gian để đi qua ống nước.

Throughtput: lượng công việc được thực thi trong một đơn vị thời gian. Có thể  hình dung throughput là lượng nước chảy trong một đơn vị thời gian.

Trong cùng một băng thông, nếu thông số throughtput quá cao thì thông só Latency sẽ tăng theo, throughtput giảm thì latency sẽ giảm theo, ta phải đánh đổi giữa việc xử lí nhiều tác vụ cùng một thời gian thì độ trễ các công việc sẽ tăng lên, nếu số lượng công việc giảm đi thì tốc độ phản hồi sẽ tăng lên.

P99: có nghĩa là 99% các yêu cầu phải có độ trễ(latency) nhỏ hơn latency đã được quy định, chỉ có 1% là có độ trễ cao hơn.
Con số này biểu thị tương đương cho việc trải nghiệm của người dùng, nếu ta càng tăng được tỉ lệ phần trăm lên 99.9%,.. thì việc trải nghiệm người dùng sẽ tốt hơn

### 1.1.2. Threading [R]

#### 1.1.2.1. Khái niệm Thread, multithreading & concurrency

##### 1.1.2.1.1. Thread

Thread (tiểu trình): là một tác vụ độc lập nhìn từ CPU, nó bao gồm định danh tiểu trình, một con trỏ lệnh, một tập thanh ghi và stack. Các tiểu trình trong cùng một tiến trình có thể chia sẽ vùng mã nguồn, vùng dữ liệu và những tài nguyên chung khác ví dụ như tập tin đang mở. Một tiến trình có thể có một hoặc nhiều tiểu trình.

##### 1.1.2.1.2. MultiThread 

Thread còn được gọi là lightweight process, là một chuỗi liên tiếp các hoạt động lập trình được định nghĩa với thời điểm bắt đầu và kết thúc cụ thể. Trong thời gian hoạt động của thread, chỉ có duy nhất một thread được thực thi, bản thân nó không phải là một chương trình vì nó không thể tự chạy, thay vào đó, nó chạy bên trong chương trình. Biểu đồ dưới đây thể hiện quá tình chạy 3 thread trên cùng 1 CPU.

![x](https://www3.ntu.edu.sg/home/ehchua/programming/java/images/Multithread.gif)


Các tiểu trình trong cùng một tiến trình có thể chia sẽ vùng mã nguồn, vùng dữ liệu và những tài nguyên chung của mà một tiến trình được cấp phát Một tiến trình có thể có một hoặc nhiều tiểu trình. Nó được gọi là lightweight vì nó sử dụng tài nguyên có sẵn của process mà không cần khởi tạo nhiều.

MultiThread cải thiện hiệu năng của chương trình bằng cách tối ưu hóa việc sử dụng tài nguyên của hệ thống Ví dụ như đối với word, trong khi đang thực thi việc in thì có thể cho phép tiếp tục gõ chữ, điều này làm cho người dùng cảm giác rằng cả hai việc đang thực hiện đồng thời trên GUI.

Việc sử dụng multiThread cần chú ý đến các vấn đề như là race condition hay deadlock. Khi sử dụng multiThread sử dụng chung một tài nguyên mà không xảy ra  race condition thì được gọi là Thread Safe

![x](https://s3-ap-southeast-1.amazonaws.com/kipalog.com/zss1fjcca6_concurrency.jpg)

##### 1.1.2.1.3. Concurrency

Concurrency (tính đồng thời): là khả năng xử lí nhiều yêu cầu cùng một lúc. Giả sử chương trình có nhiều yêu cần xử lí, thay vì  xỉ lí tuần tự hoàn thành hết yêu cầu khác nó sẽ xử lí, nó sẽ xử lí yêu cầu này một lúc rồi chuyển sang yêu cầu khác. Làm như vậy phía người dùng sẽ cảm giác rằng các yêu cầu sẽ được xử lí  cùng một lúc, tăng trải nghiệm người dùng.

#### 1.1.2.2. Thread-safe trong Java, làm sao đạt được Thread-safe?

Thread-safe là khái niệm xảy ra trong môi trường làm việc multithread, khi các thread truy cập đồng thời tài nguyên chương trình thì nhưng vẫn đảm bảo không xảy ra các hiện tượng xấu như race condition

Có một số cách để đạt được thread-safe:
- Stateless Implementations: Sử dụng các hàm không cần phân biệt đầu vào, luôn luôn trả về một kết quả với bất kì input nào
- Immutable Implementations: sử dụng các class immutable để đảm bảo  thread-safe khi ta đảm bảo rằng các thuộc tính sẽ không thể thay đổi khi đã được khởi tạo
- Thread-Local Fields: các class có các thuộc tính của riêng nó và không chia sẻ các thuộc tính này cho các thread khác. Hoặc ta có thể sử dụng ThreadLocal đến các thuộc tính class, cho phép ta get/set các đến các bản sao được khởi tạo một cách độc lập
-Atomic Objects: Sử dụng Atomic class được java cung cấp bao gồm AtomicInteger, AtomicLong, AtomicBoolean, and AtomicReference. Chúng cho phép  ta thực hiện các phép phép tính với việc đảm bảo thread-safe mà không cần sử dụng synchronization. 
- Synchronized collection: sử dụng synchronization wrapper để đảm bảo thread-safe. Khi sử dụng thì nó sẽ đảm bảo rằng việc truy cập vào tài nguyên chỉ được cho phép bởi 1 thread tại thời điểm đó, các thread khác sẽ bị block đến khi thread đầu tiên mở khóa.
- Concurrent Collections: sử dụng phép lock Java cung cấp java.util.concurrent package, chúng chia nhỏ dữ liệu ra thành các segment và với mỗi thread truy cập vào các segment nó sẽ yêu cầu lock để đạt được thread-safe, do đó có thể có nhiều thread truy cập đồng thời vào dữ liệu ở các segment khác nhau
- Synchronized Methods: sử dụng `synchronized ` keyword trong các method ở class, cung cấp cơ chế mỗi thread chỉ có thể truy cập vào method đó ở một thời điểm, các thread khác  phải đợi cho đến khi nó được hoàn thành hoặc method throw an exception
-  Synchronized Statements: trong một method có một số phần không cần synchronized do đó thay vì `synchronized` cả method ta chỉ cần synchronized một phần của method

#### 1.1.2.3. Tìm hiểu về Thread Pool, Executors.

##### 1.1.2.3.1. ThreadPool


Threadpool trong java đước sử dụng trong môi trường multithread, có chức năng hạn chế số lượng thread được tạo ra bên trong chương trình tại một thời điểm. Với mỗi lần tạo thread sẽ tốn một số tài nguyên nhất định, nếu số lượng thread được tạo ra quá nhiều ở cùng một thời điểm thì chương trình sẽ gặp vấn đề về bộ nhớ và hiệu suất.

Thay vào việc ta phải tạo thread với mỗi task mới đến, các task chỉ cần đưa vào một thread pool, ở đây sẽ có một blocking queue để chờ ho việc xử lí, trong lúc này nếu có một thread đã hoàn thành công việc trước đó và rảnh, thì nó sẽ lấy task trong queue này ra và giải quyết

![x](https://www.baeldung.com/wp-content/uploads/2016/08/2016-08-10_10-16-52-1024x572.png)

Trong java có hỗ trợ xây dựng sẵn Thread Pool được gói trong java.util.concurrent.

##### 1.1.2.3.2. Executors

Một Executor là một đối tượng chịu trách nhiệm quản lý các luồng và thực hiện các tác vụ Runnable được yêu cầu xử lý. Nó tách riêng các chi tiết của việc tạo Thread, lập kế hoạch… để chúng ta có thể tập trung phát triển logic của tác vụ mà không quan tâm đến các chi tiết quản lý Thread.

Java Concurrency API định nghĩa 3 interfaces cơ bản sau cho các Executor:
- Executor: là interface cha của tất cả Executor. Nó xác định chỉ một phương thực excute(Runnable).
- ExecutorService: là một Executor cho phép theo dõi tiến trình của các tác vụ trả về giá trị (Callable) thông qua đối tượng Future, và quản lý việc kết thúc các luồng. Các phương thức chính của nó bao gồm submit() và shutdown().
    - shutdown() từ chối nhận thêm các task và đợi cho tast chạy đồng thời để kết thúc.
    - shutdownNow() thì cưỡng chế các task kết thức ngay lập tức đồng thời executor sẽ kết thức ngay lập tức.
- ScheduledExecutorService: là một ExecutorService có thể lên lịch cho các tác vụ để thực thi sau một khoảng thời gian nhất định, hoặc để thực hiện định kỳ. Các phương thức chính của nó là schedule(), scheduleAtFixedRate() and scheduleWithFixedDelay().

### 1.1.3. Networking 

#### 1.1.3.1. Connection pooling

Một connection là một bộ đệm duy trì các kết nối tới cơ sở dữ liệu. Các kết nối tới cơ sở dữ liệu sau khi sử dụng sẽ không đóng lại ngay mà sẽ được dùng lại khi được yêu cầu trong tương lai.Cơ chế hoạt động của nó như sau: khi một connection (một kết nối) được tạo, nó sẽ được đưa vào pool và sử dụng lại cho các yêu cầu kết nối tiếp theo và chỉ bị đóng khi hết thời gian timeout.

Database connection pooling là một phương pháp được sử dụng để giữ kết nối với database vẫn còn mở sau khi được sử dụng thay vì đóng lại ngay sau mỗi lần mở đi, được tái sử dụng cho các lần kết nối sau. Thông thường thì thao tác kết nối với database tốn rất nhiều chi phí đặc biệt là kết nối đến các database không trên cùng một máy, ta phải mở session network, đăng nhập, check thông tin xem thử chính xác không,... 

Khi một user kết nối với database để thao tác, hệ thống sẽ kiểm tra xem trong connection pool có kết nối nào đang rảnh không
- Nếu chưa có kết nối nào trong Connection pooling hoặc tất cả đều đang thực thi và số lượng kết nối chưa đạt ngưỡng quy định thì nó sẽ  tạo ra một kết nối mới và đưa vào Connection pooling
- Nếu tất cả đều đang thực thi và số lượng kết nối đã tới mức quy định thì nó sẽ chờ đến khi nó một kết nối nào rảnh để sử dụng

![x](https://stackjava.com/wp-content/uploads/2018/04/connection-pool-logic.png)

Sau khi kết nối và thao tác xong với database nó sẽ trả lại vào trong connection pool hoặc là hết thời gian quy định

#### 1.1.3.2. Caching, Caching với guava, redis

##### 1.1.3.2.1. Caching

Caching cải thiện thời gian load trang và làm giảm thời gian load trên server và databases. Dựa vào thao tác bạn thực hiện đối với database mà bộ nhớ cache sẽ lưu lại những nội dung đó và được lưu trên gam, khi truy cập lại cùng một nội dung nó sẽ lấy dữ liệu từ trên gam lên tốc độ truy cập sẽ rất nhanh và giảm tải được cho server. Do lưu như vậy nên ta sẽ cần có một số chiến lược thay thế đối với nội dung trên cache như LRU, LFU,...

##### 1.1.3.2.2. Guave Cache

Guava Cache là memory cache, lưu trữ dữ liệu dưới dạng key-value, với các ưu điểm nổi bật là dễ dàng sử dụng, và có nhiều thao tác hỗ trợ với cache như cài đặt giới hạn size của cache, đặt giới hạn thời gian tồn tại trong entry của cache, hỗ trợ thread-safe,...

dưới đây là một demo nhỏ với chức năng là giới hạn size của cache
```java
public void CacheTestWithMaxSize() {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            return key.toUpperCase();
        }
    };
    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder().maximumSize(3).build(loader);
 
    cache.getUnchecked("first");
    cache.getUnchecked("second");
    cache.getUnchecked("third");
    cache.getUnchecked("forth");
    assertEquals(3, cache.size());
    assertNull(cache.getIfPresent("first"));
    assertEquals("FORTH", cache.getIfPresent("forth"));
}
```
Trong ví dụ trên, `first` sẽ bị thay thế bỏi forth vì ở đây nó sẽ lấy ra phần tử ít được truy cập nhất là first

##### 1.1.3.2.3. Redis Cache

#### 1.1.3.3. Khái niệm cơ bản về protocol trong networking

##### 1.1.3.3.1. http

Hypertext transfer protocol là một giao thức ở tầng ứng dụng cho phép các ứng dụng web có thể trao đổi dữ liệu và giao tiếp, hoạt động bằng giao thức TCP/IP, Nó thường được sử dụng để gửi đi thông tin như hình ảnh, video, audio, văn bản,...

HTTP hoạt động dựa trên mô hình server-client. Trong mô hình này, các máy tính của người dùng sẽ đóng vai trò làm máy khách (Client). Sau một thao tác nào đó của người dùng, các máy khách sẽ gửi yêu cầu đến máy chủ (Server) và chờ đợi câu trả lời từ những máy chủ này.

![x](https://images.viblo.asia/7da268f1-718b-465c-87df-700e766df185.png)

Http có điểm quan trọng sau
- Stateless : sau khi gửi request lên, client và server sẽ không lưu lại trạng thái của nhau, khi bắt đầu gửi lại thì sẽ như một request mới, giúp cho server tiết kiệm tài nguyên.
- http có thể gửi bất kì loại dữ liệu nào miễn là 2 máy có thể  đọc được dữ liệu của nhau, giúp ta trao đổi được nhiều loại dữ liệu

Các thành phần chính của một HTTP: Mỗi HTTP có ba thành phần chính là Header, Body  và Footer. Thành phần body và footer chủ yếu dùng để gửi dữ liệu còn phần header được chia thành hai loại như sau:
- Request: phương thức + URI–Request + Phiên bản HTTP.Giao thức HTTP định nghĩa một tập các giao thức GET, POST, HEAD, PUT ... Client có thể sử dụng một trong các phương thức đó để gửi request lên server. Sau khi nhận được request từ server và phân tích tùy vào loại yêu cầu mà server sẽ thực thi và trả về trạng thái trong respones header. 
- Respones: Phiên bản HTTP + Mã trạng thái + Trạng thái. Nội dung được trả về sẽ nằm ở phần body và footer.

##### 1.1.3.3.2. Websocket

Websocket là công nghệ hỗ trợ giao tiếp hai chiều giữa client và server bằng cách sử dụng một TCP socket để tạo một kết nối hiệu quả và ít tốn kém, cho phép server và client có thể gửi data qua lại với nhau bất cứ lúc nào sau khi một connection được thiết lập giúp làm giảm độ trễ giữa client và server. WebSockets là một phần của HTML5 spec và chúng được hỗ trợ trên tất cả các browsers hiện đại và được sử dụng trong nhiều các ứng dụng real-time

Hoạt động: để bắt đầu thì client gửi yêu cầu kết nối đến server, server sẽ gửi về respones nếu chấp nhận và bắt đầu kết nối, sau đó kết nối được tạo và quá trình gửi dữ liệu có thể được thực hiện

![x](https://images.viblo.asia/34e0ae36-e850-49f4-8d61-8aa1ab312d3e.jpg)

Lợi ích:
- dữ liệu có thể được gửi và nhận giữa server và client bất kì lúc nào mà không cần yêu cầu từ client và chờ server xác nhận để gửi phản hồi về.
- Gửi dữ liệu từ server về client ít tốn chi phí hơn vì nó đã được khởi tạo, ta không cần header, cookies,...
- Độ trễ  giữa server và client thấp phục vụ cho các ứng dụng real-time

##### 1.1.3.3.3. gRPC
gRPC là một cơ chế truyền tải (transport) gửi và nhận các tin nhắn giữa hai hệ thống. Hai hệ thống này theo truyền thống là một máy chủ và một máy khách. Trong các ứng dụng gRPC, client có thể gọi trực tiếp các phương thức trên server ở các máy khác nhau như mà một đối tượng cục bộ, điều này giúp ta dễ dàng tạo các ứng dụng phân tán. gRPC giúp ta có thể giao tiếp giữa các instance với nhau mà không cần qua các APIs. Với gRPC sử dụng giao tiếp RPC và nó gọi trực tiếp đến các method của những instance khác nhau về hiệu năng thì vượt trội hơn rất nhiều so với APIs

Thường được sử dụng trong:
- hệ thống microservices
- kết nối với thiết bị di động, browser client và hệ thống backend 

Lợi ích:
- GRPC sử dụng HTTP/2 là giao thức nhị phân
- Nén header trong HTTP/2, có nghĩa là ít chi phí hơn
- Ghép nhiều yêu cầu trên một kết nối
- Sử dụng một kết nối TCP duy nhất để gửi và nhận nhiều tin nhắn giữa máy chủ và máy khách
-  sử dụng Protocol Buffers, nó được được dành cho hai hệ thống backend giao tiếp với nhau với chi phí thấp hơn. Vì kích thước của tệp nhị phân nhỏ hơn văn bản, dữ liệu của Protocol Buffers được marshaled có kích thước nhỏ hơn JSON.

Các bước để sử dụng gRPC:
- Cài đặt công cụ dòng lệnh protoc và thư viện proto.
- Viết một tập tin protobuf với phần mở rộng .proto.
- Biên dịch nó đến một ngôn ngữ lập trình đích 
- Import cấu trúc từ tệp đích được tạo và serialize dữ liệu.
- Trên một máy từ xa, nhận dữ liệu được serialized và giải mã nó thành một cấu trúc hoặc lớp thường là json.

##### 1.1.3.3.4. SSL/TLS

SSL（Secure Sockets Layer）và TLS（Transport Layer Security）là kỹ thuật mã hóa truyền tin trên internet. Sử dụng SSL/TLS , mã hóa data truyền tin giữa máy tính và server giúp ta bảo mật được dữ liệu, ngăn chặn thay đổi dữ liệu data được gửi lên từ client hoặc lấy dữ liệu trả về từ server. SSL đã là phiên bản lỗi thời, nay thường sử dụng TLS.

Lợi ích:
- Cần xác nhận chứng thực: SSL/TLS cho phép bạn xác thực danh tính của server để người dùng biết chắc họ đang giao tiếp với đúng người mà họ muốn giao tiếp.
- Tăng độ tin cậy: SSL/TLS giúp ta mã hóa dữ liệu, đảm bảo thông tin trao đổi giữa hai bên được bảo mật

##### 1.1.3.3.5. RESTful API

RESTful API là một tiêu chuẩn dùng trong việc thết kế các thiết kế API cho các ứng dụng web để quản lý các resource. Nó quy định cách sử dụng các HTTP method (như GET, POST, PUT, DELETE...) và cách định dạng các URL cho ứng dụng web để quản lý truy cập đến dữ liệu. RESTful không quy định logic code ứng dụng và không giới hạn bởi ngôn ngữ lập trình ứng dụng.

Có 4 hoạt động chủ yếu ta có thể kể đến khi làm việc với server: 
- GET: lấy dữ liệu
- POST: tạo mới
- PUT: cập nhật (thay đổi)
- DELETE: Xóa dữ liệu

Nguyên tắt thiết kế của REST API?
- Dùng HTTP method rõ ràng: ví dụ như 
![x](https://cdn-images-1.medium.com/max/800/1*YRFNzFCvu0gdRHWoTOctPw.png)
-  Sử dụng danh từ số nhiều và không sử dụng động từ.
- Chỉ sử dụng danh từ số nhiều.
- Versioning: là bắt buộc, được đánh bằng số nguyên và được đặt ở vị trí đầu tiên của resource
- HTTP status code và error message: tuân theo các status code và message đã quy định

### 1.1.4. Benchmark 

#### 1.1.4.1. Benchmark 

Benchmark là một số liệu hoặc một điểm tham chiếu đối với chương trình để đánh giá chất lượng của chương trình đó. Là tiêu chuẩn để giúp cho việc xác định chất lượng của chương trình đó.

Benchmark là quá trình kiểm tra một phần hoặc toàn bộ hệ thống để đánh giá hiệu năng của chương trình. Cũng có khả năng đo lại những nơi chương trình có sự sai lệnh nhỏ khi lặp đi lặp lại ở mỗi lần chạy. Dùng để xác định khi có sự thay đổi nhỏ của chương trình xác định xem có sự thay cải thiện hay giảm đi về hiệu suất hay không.

#### 1.1.4.2. Jmeter

JMeter cung cấp tất cả những thành phần cơ bản để phục vụ cho việc thiết kế kế hoạch, thực thi và giám sát kết quả trong suốt quá trình test. 

Có các  thành phần sau
- Thread Group
- Controller
- Configuration Element
- Listener
- Timer

Thread Group:  mỗi thread Group đại diện cho một nhóm người dùng, nó mô người dùng để thử nghiệm về một chức năng cụ thể, cho phép thực hiện các tùy chỉnh như
- Thay đổi số lượng thread: mỗi thread tượng trưng cho một người dùng đang sử dụng
- Ram-Up Period: thời gian cần thiết để bắt đầu hoạt động tất cả các thread
- Loop Count: số lần lặp đi lặp lại của user

Controller: Các thành phần Controller được tạo ra để định nghĩa kịch bản thực tế của người dùng bằng việc ghi lại những yêu cầu cụ thể của người dùng tới một server xác định. có hai dạng là Sampler controller và Logic controller.
- Sampler: cho phép Jmetter gửi yêu cầu đến máy chủ
- Logic: Tùy biến việc khi nào thì gửi yêu cầu.

Listeners:  cung cấp cho phép xem những kết quả thu được từ việc chạy thử nghiệm dưới các dạng khác nhau như đồ thị, cây,... Cũng có thể tùy chỉnh thông tin trả về, có một số listener thường được sử dụng như
- View Results in Table: Hiển thị những thông số về thời gian phản hồi của từng yêu cầu, những yêu cầu thực hiện thành công và thất bại… dưới dạng bảng
- Timer: cho phép cài đặt khoảng thời gian giữa 2 yêu cầu kế tiếp nhau mà người dùng ảo gửi đến máy chủ

#### 1.1.4.3. Các tool hỗ trợ benchmark hệ thống dành cho Java? So sánh ưu, nhược điểm?

Jmeter: 
- Ưu điểm:
    - Giao diện thân thiện dễ dàng thao tác, không cần hiểu về lập trình và miễn phí, được hỗ trợ nhiều loại protocol
    - Hỗ trợ chạy trên đa nền tảng vì được phát triển bởi java
    - JMeter có thể phát hiện số lượng người dùng tối đa cùng lúc mà web có thể xử lý
    - JMeter cung cấp các báo cáo một cách hiệu quả với nhiều loại báo cáo khác nhau
    - Linh hoạt: có thể dễ dàng điều chỉnh yêu cầu test, thực hiện nhiều loại test khác nhau.
- Nhược điểm
    - Hiệu suất thấp, tiêu thụ nhiều tài nguyên của hệ thống
    - Chỉ thực hiện test trên các ứng dụng web
    

Grinder: Nếu cần khả năng mở rộng và tốc độ
- Uư điểm:
    - Hiệu suất test cao, thời gian thực thi thấp
    - Khả năng mở rộng test được dùng cho các  thử nghiệm lớn chương trình  
- Nhược điểm:
    - Dữ liệu đầu ra không hỗ trợ với nhiều định dạng report khác nhau, khó phân tích

Gatling: sử dụng cho các vấn đề về phân tích để thay đổi vì có hỗ trợ report chi tiết
- Ưu điểm:
    - Mã nguồn mở được sử dụng miễn phí
    - xử lí kết nối tối ưu  
    - sử dụng ít tài nguyên của máy tính như bộ nhớ, CPU trong quá trình test
    - nội dung báo cáo tự động tạo ra
- Hạn chế
    - Thông tin trong lúc thực thi test chương trình hạn chế
    - Chỉ hỗ trợ các protocol như HTTP, WebSockets, Server-sent events, JMS

#### 1.1.4.4. Locust

Là công cụ để ta có thể kiểm tra độ chịu tải hệ thống trước khi release 
Hoạt động theo mô hình server-client ở trên server được viết bằng ngôn ngữ python ghi nhận tất cả các thao tác từ client báo lên, và client có thể viết theo nhiều ngôn ngữ khác nhau như python, go, java.

Locust được tạo ra để giải quyết các vấn đề như maintain scrip gặp khó khăn, khả năng scaling khó khăn, mặc khác locust khá nhẹ và dễ dàng customize, và mở rộng được nhiều tín năng nếu cần thiết

Một ví dụ đơn giản với Locust server
```python
# locustfile.py
from locust import Locust, TaskSet, task

class UserTaskSet(TaskSet):              #3
    @task                                #4
    def my_task(self):
        print("executing my_task")

class User(Locust):                      #1
    task_set = UserTaskSet               #2 
```

và để chạy locust trên ta sử dụng câu lệnh sau
`locust -f lamgido.py` mặc định nó sẽ tìm kiếm file có tên là locustfile nếu có thay đổi tên thì ta sẽ thêm cờ -f vào. Khi chạy với dòng lệnh này nó sẽ sử dụng cấu hình mặc định

### 1.1.5. JVM

#### 1.1.5.1. JVM ? How it work ?

Java Virtual Machine (JVM) là môi trường dùng để chạy ứng dụng được viết bằng ngôn ngữ lập trình Java, nó là một trình thông dịch. Do càng ngày có các hệ điều hành khác nhau, JVM cung cấp cơ chế. JVM giống như một máy ảo, muốn chạy được java thì phải chạy trên JVM này, với các hệ điều hành khác nhau thì sẽ có JVM tương ứng với các hệ điều hành đó giúp ta có thể chạy trên nhiều Platform khác nhau khác phục được tình trạng chương trình biên dịch ra file .exe lại không thể sử dụng trên linux.

Các thành phần chính của JVM
- Class Loader: là một hệ thống con của JVM, làm nhiệm vụ tải các lớp được định nghĩa.
- Class Area: lưu trữ cấu trúc của các lớp, thuộc tính, phương thức của lớp, và code của các phương thức.
- Heap: là vùng nhớ lưu trử các đối tượng được khởi tạo trong quá trình thực thi.
- Stack: chứa các frame. Mỗi frame chứa các biến cục bộ và thực thi một hàm gọi và trả kết quả về. Mỗi tiến trình có một Stack riêng, được khởi tạo cùng lúc với tiến trình. Mỗi frame sẽ được tạo khi một hàm được gọi và hủy khi việc gọi hàm kết thúc.
- Programming Counter Register: chứa địa chỉ của máy chủ ảo đang thực thi
- Native Method Stack: chứa các hàm của hệ thống được sữ dụng trong chương trình
- Execution Engine: là một hệ thống bao gồm: bộ xử lý ảo, trình thông dịch (đọc Java byte code và thực thi các chỉ thị), JIT compiler biên dịch mã byte code sang mã máy. Các nhiệm vụ chính của JVM bao gồm: tải code, kiểm tra code, thực thi code, cung cấp môi trường runtime.

![x]( https://viblo.asia/uploads/03cc91ac-abf2-40db-896e-035692c253ac.png)
Cơ chế làm việc
- Dầu tiên sau khi biên dịch chương trình java sẽ trở thành các byte code .class
- Class-Loader: chuyên tìm kiếm và load các file .class vào vùng nhớ của Java.
- Execution Engine: chuyển các lệnh của JVM trong file .class thành các lệnh của máy, hệ điều hành tương ứng và thực thi chúng.

#### 1.1.5.2. JRE vs JDK?

Java Runtime Environment(JRE). Nó là một gói phần mềm chứa mọi thứ cần thiết  để chạy chương trình java đã được compiled bao gồm Java virtual Machine, các thư viện java, java command và một số thứ cần thiết khác. Nhưng JRE không thể  tạo hoặc compile chương trình java. 

Java Development Kit(JDK) chứa đầy đủ những gì cần thiết cho một chương trình java, nó có mọi thứ của JRE và một số thứ bổ sung như compiler  (javac) and một số tool hỗ trợ thêm (javadoc and jdb) khả năng debug chương trình. JDK cũng có chức năng là tạo chương trình và biên dịch chương trình.

![x](https://i.stack.imgur.com/CBNux.png)

Nếu ta chỉ muốn chạy chương trình java trên máy, ta chỉ cần cài đặt JRE là đủ. Nhưng ta muốn một môi trường làm việc đầy đủ với việc tạo chương trình, debug,... tất cả với java thì nên sử dụng JDK. Do JDK lớn hơn nên cần nhiều không gian đĩa hơn JRE



## 1.2. Tham khảo

https://engineering.zalopay.vn/unit-tests-why/

https://gpcoder.com/5500-gioi-thieu-java-logging/

https://logging.apache.org/log4j/2.x/manual/customloglevels.html

https://stackoverflow.com/questions/36949735/what-is-the-difference-between-latency-bandwidth-and-throughput

https://www.baeldung.com/guava-cache

https://stackoverflow.com/questions/4041114/what-is-database-pooling

https://viblo.asia/p/tim-hieu-ve-http-hypertext-transfer-protocol-bJzKmgewl9N

https://www.topitworks.com/blogs/nhung-dieu-can-biet-ve-java-virtual-machine/

https://stackoverflow.com/questions/1906445/what-is-the-difference-between-jdk-and-jre

https://viblo.asia/p/mojito-huong-dan-su-dung-jmeter-de-test-performance-cho-he-thong-website-bJzKmLGO59N

https://toilatester.blog/2018/08/26/load-testing-with-locust-io/



