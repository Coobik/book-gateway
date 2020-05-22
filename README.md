# book-gateway

spring cloud gateway examples

## Prerequisites

- Java 11
- Maven
- Docker
- docker-compose

## Build backend services: authors, books, web-sockets

- build JARs and Docker images: `./build-all.sh`
- run Docker containers: `docker-compose up -d`

## Operations

### web sockets echo page

http://docker-host:8082/chat.html


### authors

**POST** http://docker-host:8080/api/v1/authors

- **Content-Type**: `application/json`
- **Authorization**: `Bearer 58cef136-acdb-49fb-b48e-a5f020c3cabb`

Request:
```
{
    "firstName":"bill",
    "lastName":"gates",
    "address":"seattle",
    "language":"en"
}
```

Response:
```
{
    "id": "4c67cfb5-b7c9-4868-9b1f-feee06572fab",
    "firstName": "bill",
    "lastName": "gates",
    "address": "seattle",
    "language": "en"
}
```


### books

**POST** http://docker-host:8081/api/v1/books

- **Content-Type**: `application/json`
- **Authorization**: `Bearer 1a2517fe-5a0d-43a5-adb4-227e39d02f78`

Request:
```
{
    "title": "640K ought to be enough for anybody",
    "pages": 640,
    "authorId": "4c67cfb5-b7c9-4868-9b1f-feee06572fab"
}
```

Response:
```
{
    "id": "d5405035-f008-48ad-8dba-72b65cbf52fd",
    "title": "640K ought to be enough for anybody",
    "pages": 640,
    "authorId": "4c67cfb5-b7c9-4868-9b1f-feee06572fab"
}
```


## client app - mobile-listener

### build

```
cd mobile-listener
./gradlew clean build
```

### settings

- `server.port` - actuator port

- `authors.url`
- `authors.auth.token`

- `books.url`
- `books.auth.token`
- `books.mode` - `full`, `brief`

- `websocket.client.enabled` - `true`, `false`
- `websocket.url`
    
### command line args

- `--all-books` - fetch all books on start
- `--book-id` - load book by id: `--book-id=d5405035-f008-48ad-8dba-72b65cbf52fd`

### book output modes

- `books.mode=full`: `{ "book": {BookResponse}, "author": {AuthorResponse} }`
- `books.mode=brief`: `{ "bookTitle": "title", "authorName": "firstName lastName" }`

### run

- `cd mobile-listener/build/libs`
    - `java -jar mobile-listener-0.0.1-SNAPSHOT.jar --books.mode=brief --all-books`
    - `java -jar mobile-listener-0.0.1-SNAPSHOT.jar --books.mode=full --book-id=d5405035-f008-48ad-8dba-72b65cbf52fd`
