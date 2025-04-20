# Demo GraphQL với Spring Boot

## GraphQL là gì?

GraphQL là một ngôn ngữ truy vấn dành cho API và một runtime để thực thi các truy vấn đó, GraphQL cho phép client chỉ định chính xác dữ liệu mà họ cần, giúp giảm thiểu dữ liệu dư thừa và số lượng request cần thiết.

### Các khái niệm cơ bản của GraphQL

1. **Schema**: Định nghĩa cấu trúc dữ liệu và các thao tác có thể thực hiện trên dữ liệu.
2. **Type**: Các kiểu dữ liệu trong GraphQL (Object, Scalar, Enum, Interface, Union, Input).
3. **Query**: Thao tác đọc dữ liệu.
4. **Mutation**: Thao tác thay đổi dữ liệu (thêm, sửa, xóa).
5. **Resolver**: Hàm xử lý để lấy dữ liệu cho một field trong schema.
6. **Subscription**: Cơ chế để nhận thông báo khi dữ liệu thay đổi (real-time).
7. **Validation**: Kiểm tra dữ liệu đầu vào.

## Ưu điểm của GraphQL

1. **Tránh over-fetching và under-fetching**: Client chỉ lấy chính xác dữ liệu cần thiết, không thừa không thiếu.
2. **Giảm số lượng request**: Có thể lấy nhiều loại dữ liệu liên quan trong một request duy nhất.
3. **Kiểm soát dữ liệu trả về**: Client quyết định cấu trúc dữ liệu trả về, không phụ thuộc vào cách thiết kế API của server.
4. **Tự động tạo tài liệu**: GraphQL cung cấp khả năng tự động tạo tài liệu từ schema.
5. **Phát triển song song**: Frontend và backend có thể phát triển độc lập, miễn là tuân thủ schema đã thống nhất.
6. **Phiên bản API linh hoạt**: Dễ dàng thêm trường mới mà không ảnh hưởng đến các client hiện có.

## Nhược điểm của GraphQL

1. **Độ phức tạp**: Cấu hình và triển khai GraphQL phức tạp hơn so với REST API đơn giản.
2. **Caching**: Khó khăn trong việc triển khai caching so với REST vì mỗi request có thể khác nhau.
3. **Xử lý lỗi**: Cơ chế xử lý lỗi phức tạp hơn so với HTTP status code của REST.
4. **Tối ưu hóa truy vấn**: Cần cơ chế để ngăn chặn truy vấn quá phức tạp gây tốn tài nguyên server.
5. **Đường cong học tập**: Đòi hỏi thời gian để làm quen với cách tiếp cận khác biệt so với REST.

## So sánh với các API khác

### GraphQL vs REST API

| Tiêu chí | GraphQL | REST API |
|----------|---------|----------|
| Endpoints | Một endpoint duy nhất | Nhiều endpoints khác nhau |
| Dữ liệu trả về | Client quyết định | Server quyết định |
| Số lượng requests | Thường ít hơn | Có thể cần nhiều requests |
| Over-fetching | Không có | Phổ biến |
| Under-fetching | Không có | Phổ biến (N+1 problem) |
| Caching | Phức tạp | Đơn giản (dựa trên HTTP) |
| Xử lý lỗi | Phức tạp hơn | Đơn giản (HTTP status codes) |
| Tài liệu | Tự động từ schema | Cần công cụ bổ sung (Swagger) |
| Phổ biến | Đang tăng | Rất phổ biến |

### GraphQL vs gRPC

| Tiêu chí | GraphQL | gRPC |
|----------|---------|------|
| Giao thức | HTTP/JSON | HTTP/2 & Protocol Buffers |
| Hiệu suất | Tốt | Rất tốt (nhẹ và nhanh hơn) |
| Streaming | Có (Subscriptions) | Có (hỗ trợ tốt hơn) |
| Ngôn ngữ hỗ trợ | Nhiều | Nhiều |
| Trường hợp sử dụng | Web/Mobile API | Microservices, IoT |
| Đường cong học tập | Trung bình | Cao |
| Công cụ debug | Tốt (GraphiQL, Playground) | Hạn chế hơn |

## Công nghệ sử dụng trong Demo

- **Spring Boot**: Framework Java để xây dựng ứng dụng
- **Spring for GraphQL**: Tích hợp GraphQL vào Spring
- **H2 Database**: Cơ sở dữ liệu nhúng
- **Lombok**: Giảm boilerplate code
- **GraphiQL**: Giao diện để thử nghiệm truy vấn GraphQL

## Cách khởi động dự án

1. Clone repository này về máy
2. Mở terminal và di chuyển đến thư mục dự án
3. Chạy lệnh: `./gradlew bootRun` (Linux/Mac) hoặc `gradlew.bat bootRun` (Windows)
4. Truy cập GraphiQL UI tại: http://localhost:8080/graphiql

## Cấu trúc dự án

```
demo-graphql/
├── src/main/java/com/example/demographql/
│   ├── config/           # Cấu hình ứng dụng
│   ├── controller/       # GraphQL resolvers
│   ├── dto/              # Data Transfer Objects
│   ├── model/            # Entities
│   ├── repository/       # JPA repositories
│   ├── service/          # Business logic
│   └── DemoGraphqlApplication.java
├── src/main/resources/
│   ├── graphql/          # GraphQL schema
│   └── application.properties
├── build.gradle
└── README.md
```

## Các thành phần chính của GraphQL trong demo

### 1. Schema (schema.graphqls)

Schema định nghĩa cấu trúc dữ liệu và các thao tác có thể thực hiện. Trong demo này, chúng ta có:

- **Types**: Book và Author
- **Queries**: allBooks, bookById, booksByGenre, allAuthors, authorById
- **Mutations**: createBook, updateBook, deleteBook, createAuthor, updateAuthor, deleteAuthor

### 2. Resolvers (Controllers)

Resolvers là các phương thức xử lý để lấy hoặc thay đổi dữ liệu. Trong Spring GraphQL, chúng được định nghĩa bằng các annotation:

- `@QueryMapping`: Xử lý truy vấn
- `@MutationMapping`: Xử lý thay đổi dữ liệu
- `@SchemaMapping`: Xử lý các trường phức tạp


### 3. Các loại truy vấn GraphQL

#### Query - Truy vấn dữ liệu

## Lưu ý khi sử dụng GraphQL

1. **N+1 Problem**: Cần xử lý vấn đề N+1 query bằng cách sử dụng DataLoader hoặc batch query.
2. **Bảo mật**: Cần giới hạn độ phức tạp của truy vấn để tránh DDoS.
3. **Validation**: Cần kiểm tra dữ liệu đầu vào kỹ lưỡng.
4. **Phân trang**: Sử dụng cursor-based pagination cho hiệu suất tốt.
5. **Error handling**: Xử lý lỗi một cách nhất quán.

## Kết luận

GraphQL là một công nghệ mạnh mẽ cho phép xây dựng API linh hoạt và hiệu quả. Mặc dù có đường cong học tập cao hơn so với REST, nhưng lợi ích mà nó mang lại rất đáng giá, đặc biệt là trong các ứng dụng phức tạp với nhiều loại dữ liệu liên quan.


