
HTTP는 web client가 server와 대화하는 방법과 server에서 다시 client로 데이터가 전송되는 방법을 정의한 표준.

HTTP는 일반적으로 HTML파일과 그 안에 포함된 이미지를 전송하는 수단쯤으로 생각되지만, HTTP는 데이터 형식을 가리지 않는다. TIFF이미지, Microsoft word, 윈도우 .exe파일, 또는 바이트로 표현할 수 있는 모든 파일의 전송에 사용될 수 있다. HTTP를 사용하는 프로그램을 만들기 위해서는 일반적인 웹 사이트를 만드는 개발자들보다 HTTP에 대해 더 깊이 알아야 한다.

browser의 주소bar에 http://www.google.com 을 입력했을 때, 실제 어떤 일이 발생하는지 보여 주기 위해 브라우저의 이면에서 일어나는 일들에 대해서 다룬다.

#프로토콜

-----

Web browser와 Web server 사이에 통신을 위한 표준 프로토콜이다. HTTP는 클라이언트와 서버가 연결을 맺는 방법, 클라이언트가 서버에게 데이터를 요청하는 방법, 서버가 요청에 응답하는 방법, 그리고 마지막으로 연결을 종료하는 방법에 대해서 명시한다.

HTTP연결은 데이터 전송을 위해 TCP/IP 프로토콜을 사용한다.
클라이언트가 서버로 보내는 각 요청은 다음 4단계를 거친다.
1. 클라이언트가 서버의 HTTP 기본 포트 80에 대해 TCP 연결을 한다. 다른 포트 사용 시 URL에 명시한다.
2. 클라이언트가 특정 경로에 위치한 리소스를 요청하는 메시지를 서버로 보낸다.
요청에는 헤더와 선택적으로(요청의 성격에 따라 다름) 빈 줄로 구분된 데이터가 포함된다.
3. 서버는 클라이언트에게 응답을 보낸다. 응답은 응답 코드로 시작하며, 메타데이터의 전체 헤더와 빈 줄 그리고 요청된 문서 또는 에러 메시지가 뒤따라온다.
4. 서버는 클라이언트와 연결을 종료한다.

HTTP1.1과 그 이후 버전에서는 다수의 요청과 응답을 단일 TCP 연결을 통해 연속적으로 보낼 수 있다.
즉, 위의 과정에서 2번과 3번을 1번과 4번 사이에서 여러 번 반복할 수 있음.
게다가 HTTP 1.1에서는 요청과 응답을 다수의 청크(chunk)로 보내질 수 있으며, 이 방법은 확장성을 높여준다.

###요청

모든 요청과 응답은 같은 기본 구조로 되어 있다. 헤더 라인(header line)과 메타데이터를 포함한 HTTP헤더, 빈 줄, 그리고 메시지 본문으로 구성되어 있다. 일반적인 client 요청은 다음과 같은 형태로 되어 있다.

예를 들어, 아래와 같은 GET요청은 메시지 본문을 포함하지 않으며, 마지막 빈 줄로 요청의 끝을 나타낸다.
```java
1  GET /index.html HTTP/1.1
2  User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:20.0) Gecko/20100101 Firefox/20.0
3  Host: en.wikipedia.org
4  Connection: keep-alive
5  Accept-Language: en-US, en; q=0.5
6  Accept-Encoding: gzip, deflate
7  Accept: text/html, application/xhtml+xml, application/xml;q=0.9, */ * ;q=0.8
```


1 요청 라인(request line)이라 부름. 메서드(GET), 리소스의 경로(/index.html), HTTP의 버전 정보(HTTP/1.1)
  여기서 메서드는 요청방식을 명시함. GET메서드는 서버에 해당 리소스의 반환을 요청한다.
  HTTP버전은 클라이언트가 지원하는 프로토콜 버전이다.

  GET방식으로 요청 시에 요청 라인만 있어도 충분하지만, 일반적으로 클라이언트는 헤더에 추가적인 정보를 포함하여 요청한다.
```java
        Keyword: Value의 형태로 구성.

        Host: en.wikipedia.org
        Connection: keep-alive
        Accept-Language: en-US, en; q=0.5
```
  각 라인은 위와 같다. Keyword는 대소문자를 가리지 않는다. 그러나 값(Value)은 경우에 따라 대소문자를 가리기도 한다.
  키워드와 값 둘 모두 아스키만 허용하며, 값이 너무 긴 경우 다음줄의 시작 위치에 스페이스나 탭을 입력한 후 계속해서 입력할 수 있다.

  헤더의 각 줄은 Carriage Return과 Line Feed의 쌍으로 끝난다. (CRLF)

2 첫 번째 키워드 User-Agent
  접속에 사용된 브라우저의 종류를 서버에게 알려주며, 서버는 이 헤더를 보고 특정 브라우저 타입에 최적화된 파일을 보내는 것이 가능하다.
  ```java
  User-Agent: Lynx/2.4 libwww/2.1.4
  ```

  오래된 1세대 브라우저 이외에 대부분의 브라우저들은 또한 헤더에 서버의 이름을 명시한 Host필드를 보낸다.
  Host필드는 동일한 IP주소에서 서비스되는 서로 다른 이름의 호스트를 서버가 구분하는 데 사용된다.
  ```java
  Host: www.cafeaulait.org
  ```

  이 예제의 마지막 줄에 사용된 키워드 Accept는 클라이언트가 처리할 수 있는 데이터 타입을 서버에게 알려준다.
  (그러나 이 값을 무시하는 서버를 어렵지 않게 발견할 수 있다.)
  클라이언트가 HTML문서, 텍스트, JPEG 그리고 GIF이미지에 해당하는 네 가지 MIME 미디어 타입을 처리할 수 있음을 의미한다.
  ```java
  Accept: text/html, text/plain, image/gif, image/jpeg
  ```

  MIME타입은 두 레벨로 분류된다. 타입과 서브타입,
  타입은 매우 일반적인 데이터의 종류를 나타낸다.
  > 그림인지, 텍스트인지 또는 동영상인지?

  서브타입은 구체적인 데이터 타입을 나타낸다.
  >GIF이미지, JPEG이미지, TIFF이미지.

  예를 들어, HTML의 콘텐츠 타입은 text/html이다.
  타입은 text이고 서브타입이 html임.
  JPEG이미지에 대한 콘텐츠 타입은 image/jpeg임.
  타입은 image이고 서브타입은 jpeg임. 타입에는 총 8종류가 정의되어 있다.

  | 타입   | 설명  |
  |---|---|
  | text/* | 사람이 읽을 수 있는 문자 타입	|
  | images/* | 그림 타입 |
  | model/* | VRML파일과 같은 3D 모델 타입 |
  | audio/* | 소리 타입 |
  | video/* | 소리를 포함할 수 있는 움직이는 그림 타입 |
  | application/* | 바이너리 데이터 타입 |
  | message/* | 이메일 메시지와 HTTP응답과 같은 프로토콜에 따른 인벨로프 타입 |
  | multipart/* | 다수의 문서와 리소스의 컨테이너 타입 |
  해당 표에 나열된 각 타입들은 다양한 서브타입을 가지고 있다.

  MIME타입에 등록된 최신 목록은 htttp://iana.org/assignments/media-types/ 에서 확인할 수 있다.
  이 밖에도 표준이 아닌 사용자 정의 타입과 서브타입을 자유롭게 정의할 수 있으며, x-로 시작하는 이름만 사용하면 됨.
  예를 들어, 일반적으로 플래시 파일은 application/x-shockwave-flash 타입으로 지정된다.

  요청의 끝은 하나의 빈 줄로 끝난다.
  즉, 두 번의 캐리지리턴, 라인피드 쌍, \r\n\r\n으로 끝난다.

  요청을 받은 서버는 마지막 빈 줄을 확인하는 즉시, 동일한 연결을 통해 클라이언트에게 응답을 보내기 시작한다.

###응답

  응답은 상태 라인(status line)으로 시작하고 이어서 요청 헤더와 동일한 "이름: 값" 구문을 사용하여 응답을 설명하는 헤더가 온다. 그리고 하나의 빈 줄과 요청된 리소스가 온다. 일반적으로 요청이 성공할 경우 다음과 같은 형태의 응답을 받게됨.
  ```java
  1  HTTP/1.1 200 OK
  2  Date: Sun, 21 Apr 2013 15:12:46 GMT
  3  Server: Apache
  4  Connection: close
  5  Content-Type: text/html; charset=ISO-8859-1
  6  Content-length:115
  7  
  8  <html>
  9  <head>
  10 <title>
  11 A Sample HTML file
  12 </title>
  13 </head>
  14 <body>
  15 The rest of the document goes here
  16 </body>
  17 </html>
  ```

1 서버가 사용하는 프로토콜(HTTP/1.1)과 응답 코드를 나타낸다.
  200 OK는 가장 일반적인 응답 코드이며, 요청이 성공적으로 처리되었음을 의미한다.

다른 헤더
2 응답이 만들어진 서버 기준의 시간
```java
Date: Sun, 21 Apr 2013 15:12:46 GMT
```

3 서버 소프트웨어[Apache]
```java
Server: Apache
```

4 전송 종료 후 연결의 상태
```java
Connection: close
```

5 MIME 미디어 타입
```java
Content-Type: text/html; charset=ISO-8859-1
```
6 전송된 문서의 크기(헤더는 포함되지 않는다) - 이 경우에 107바이트 - 를 나타냄.
```java
Content-length:115
```

###HTTP 1.1 응답 코드
> 일반적으로 보게되는 표준 응답 코드와 실험 응답 코드 목록.
> WebDAV에서만 사용되는 일부 응답 코드는 제외하였음.

| 코드와 메시지   | 의미  | HttpUrlConnection 상수 |
|---|---|---|
| **1XX** | **정보 제공**	|**-------**|
| 100 Continue | 클라이언트는 계속해서 요청해야 한다. 서버는 이 코드를 제공하여 요청의 일부를 받았으며 나머지를 받아들일 준비가 됐음을 나타낸다.  | N/A |
| 101 Switching Protocols | 서버가 애플리케이션 프로토콜을 변경하기 위해 Upgrade 헤더 필드에 있는 클라이언트의 요청을 받아들이는 중이다(예: HTTP에서 WebSocket으로 변경) | N/A |
| **2XX Successful** | **요청 성공** |**-------**|
| 200 OK | 가장 일반적인 응답 코드. 요청 메서드가 GET또는 POST인 경우, 요청된 데이터가 헤더와 함께 응답에 포함되어 전송된다. 요청 메서드가 HEAD인 경우, 응답에는 헤더 정보만 포함되어 있다. |HTTP_OK
| 201 Created | 서버는 응답의 본문에 명시된 URL의 리소스를 생성했으며, 클라이언트는 해당 URL을 즉시 읽을 수 있다. 이 코드는 **POST요청에 대한 응답으로만 보내진다**. |HTTP_CREATED|
| 202 Accepted | 일반적으로 흔히 발생하는 응답은 아니다. **POST 요청 시에 일반적으로 발생**하며 아직 요청이 처리 중이기 때문에 아무것도 반환해 주지 않는다. 그러나 서버는 사용자에게 상황을 설명하는 HTML페이지나 작업의 예상 종료 시간을 알려줄 수 있다. 그리고 작업의 처리 상태를 모니터링할 수 있는 링크를 제공해 주는 것이 가장 이상적이다. | HTTP_ACCEPTED|
| 203 Non-authoritative Information | 반환된 리소스는 프록시나 로컬에 캐싱된 데이터이므로 최신 데이터인지 보장되지 않는다.	|HTTP_NOT_AUTHORITATIVE|
| 204 No Content | 서버가 요청을 성공적으로 처리했지만 클라이언트에게 반환해 줄 정보가 없음을 나타낸다. 이것은 일반적으로 클라이언트의 요청을 허용하지만 사용자에 대한 응답을 반환하지 않는 서버상의 잘못 작성된 폼 처리 프로그램에 의해 발생한다.	|HTTP_NO_CONTENT|
| 205 Reset Content | 서버가 요청을 성공적으로 처리했지만 클라이언트에게 반환해 줄 정보가 없음을 나타내며, 클라이언트는 요청을 보낸 폼의 내용을 비워야 한다.	|HTTP_RESET|
| 206 Partial Content | 클라이언트의 HTTP바이트 범위 확장을 사용한 요청에 대해 서버가 리소스 전체가 아닌 일부분을 반환한다.	|HTTP_PARTIAL|
| 226 IM Used | 델타(delta) 인코딩된 응답을 나타낸다.	|N/A|
| **3XX Redirection** | **재배치와 리다이렉션**  |**-------**|
| 300 Multiple Choices | 요청된 문서에 대해 서버가 다양한 선택 사항을 제공하고 있다.(예를 들어, PostScript와 PDF)  |HTTP_MULT_CHOICE|
| 301 Moved Permanently | 리소스가 새로운 URL로 완전히 이동했음을 나타낸다. 클라이언트는 이 응답으로 전달된 URL로부터 자동으로 리소스를 읽어야 하며, 이전 URL을 가리키는 북마크가 있는 경우 업데이트시켜야 한다. |HTTP_MOVED_PERM|
| 302 Moved Temporarily | 리소스가 새로운 URL로 임시적으로 이동했으나, 머지않아 원래 URL로 다시 옮겨올 것을 나타낸다. 그러므로 기존 북마크를 업데이트할 필요는 없다. 이 값은 종종 웹에 접근하기 전에 로컬인증을 요구하는 프록시에 의해 사용된다.|HTTP_MOVED_TEMP|
| 303 See Other | 일반적으로 POST 폼 요청에 대한 응답으로 사용되며, 이 코드는 사용자가 GET을 사용하여 다른 URL로부터 리소스를 받아야 함을 나타낸다. | HTTP_SEE_OTHER |
| 304 NOT Modified | 클라이언트는 요청 시 If-Modified-Since 헤더를 지정하여 최근에 변경된 경우에만 해당 문서를 전송받을 수 있다. 해당 문서에 변경된 내용이 없는 경우 이 코드가 반환된다. 이 경우, 클라이언트는 캐시로부터 문서를 읽어야 한다. |HTTP_NOT_MODIFIED|
| 305 Use Proxy | Location 헤더 필드는 응답을 제공할 프록시 서버의 주소를 포함하고 있다. |HTTP_USE_PROXY|
| 307 Temporary Redirect | 302와 비슷하지만 변경을 위한 HTTP메서드는 허가되지 않는다. |N/A|
| 305 Permanent Redirect | 301과 비슷하지만 변경을 위한 HTTP메서드는 허가되지 않는다. |N/A|
| **4XX** | **클라이언트 에러** |**-------**|
| 400 Bad Request | 클라이언트가 잘못된 구문으로 요청을 보냈다. 일반적으로 웹 브라우저를 통해 웹 서핑을 할 경우에는 잘 발생하지 않지만, 사용자가 직접 클라이언트를 개발하는 경우 흔히 발생한다. |HTTP_BAD_REQUEST|
| 401 Unauthorized | 이 페이지에 접근하기 위해서는 일반적으로 권한부여(authorization), 즉 사용자 이름과 암호가 필요하다. 사용자 이름과 암호가 입력되지 않았거나 올바르지 않은 경우를 나타낸다. |HTTP_UNAUTHORIZED|
| 402 Payment Required | 현재는 사용되지 않지만, 해당 리소스에 접근하기 위해서는 어떤 종류의 지불이나 결제가 필요함을 나타내기 위해 미래에 사용될 수도 있다. |HTTP_PAYMENT_REQUIRED|
| 403 Forbidden | 서버가 요청을 이해하고 있지만, 의도적으로 요청의 처리를 거부했다. **이 응답을 해결하기 위해 권한 부여(authroization)는 도움이 되지 않는다.** 이 응답은 종종 **해당 클라이언트가 할당량을 초과한 경우 사용**된다. |HTTP_FORBIDDEN|
| 404 Not Found | 이 에러는 서버가 요청된 리소스를 찾을 수 없음을 나타내는 일반적인 에러 응답이다. 링크가 잘못됐거나, 포워드 주소 없이 해당 문서가 이동했거나, URL에 오타가 있거나 또는 이와 비슷한 다양한 상황을 나타낸다. |HTTP_NOT_FOUND|
| 405 Method Not Allowed | 요청된 리소스에 대해서 요청 시 사용된 메서드가 허용되지 않는다. 예를 들어, 서버상에 있는 GET만 허가된 파일에 대해서 PUT또는 POST를 요청하는 경우가 있다. |HTTP_BAD_METHOD|
| 406 Not Acceptable | 요청된 리소스를 클라이언트가 요청 HTTP헤더의 Accept필드에 명시한 형태로 제공할 수 없다. |HTTP_NOT_ACCEPTABLE|
| 407 Proxy Authentication Required | 중간 프록시 서버는 요청된 리소스를 가져오기 전에 클라이언트에게 사용자 이름과 암호 형태의 인증(authentication)을 요구한다. |HTTP_PROXY_AUTH|
| 408 Request Timeout | 네트워크가 혼잡하거나 하는 이유로 클라이언트가 요청을 보내는데 너무 오래 걸리는 경우를 나타낸다. |HTTP_CLIENT_TIMEOUT|
| 409 Conflict | 요청이 처리되는 것을 막는 일시적인 충돌을 나타낸다. 예를 들어, 두 클라이언트가 동시에 같은 파일의 PUT을 시도하는 경우가 있다. |HTTP_CONFLICT|
| 410 Gone | 404와 비슷하지만 자원의 존재에 대한 404보다 좀 더 강한 표현이다. 해당 리소스는 단순한 이동이 아닌 의도적으로 제거되었으므로 다시 복원될 가능성은 없다. 이 리소스를 참조하고 있는 링크가 있다면 제거해야 한다. |HTTP_GONE|
| 411 Length Required | 클라이언트가 요청 시에 Content-length 헤더를 보내지 않는다. |HTTP_LENGTH_REQUIRED|
| 412 Precondition Failed | 클라이언트가 HTTP헤더에 명시한 요청 조건이 만족하지 않는다. |HTTP_PRECON_FAILED|
| 413 Request Entity Too Large | 클라이언트 요청 본문이 서버가 현재 처리할 수 있는 양을 초과한다. |HTTP_ENTITY_TOO_LARGE|
| 414 Request-URI TOO LONG | 요청의 URI가 너무 길다. 버퍼 오버플로를 막는 데 사용된다. |HTTP_REQ_TOO_LONG|
| 415 Unsupported Media Type | 요청의 본문이 서버가 지원하지 않는 콘텐츠 타입으로 되어 있다. |HTTP_UNSUPPORTED_TYPE|
| 416 Requested range Not Satisfied | 클라이언트가 요청한 바이트 범위의 데이터를 보낼 수 없다. |N/A|
| 417 Expectation Failed| 서버는 Expect 요청 헤더 입력란의 요구사항을 만족할 수 없다. |N/A|
| 418 I'm a teapot | 주전자로 커피를 끓이고 있음을 의미한다. 서버가 주전자이므로 커피 양조를 거부한다.(이 프로토콜은 1998년 만우절 농담으로 만든 HTCPCP, 커피 주전자 제어 프로토콜의 응답코드) |N/A|
| 420 Enhance Your Calm | 서버가 요청 속도를 제한하고 있다. 표준은 아니며 트위터에서만 사용되고 있다. |N/A|
| 422 Unprocessable Entity | 요청 본문의 콘텐츠 타입이 인식 가능하며, 본문의 내용이 문법적으로 문제는 없지만, 다른 이유로 서버가 처리할 수 없다. |N/A|
| 424 Failed Dependency | 이전 요청의 실패로 인해 요청이 실패했다. |N/A|
| 426 Upgrade Required | 클라이언트가 오래되거나 안전하지 않은 HTTP프로토콜 버전을 사용하고 있다. |N/A|
| 428 Precondition Required | 요청에 If-Match 헤더가 포함되어 있어야 한다. |N/A|
| 429 Too Many Requests | 너무 많은 요청을 나타낸다. 클라이언트는 요청 속도를 제한하고 천천히 해야 한다. |N/A|
| 431 Request Header Fields Too Large| 헤더 전체가 너무 크거나 헤더의 특정 필드가 너무 크다. |N/A|
| 451 Unavailable For Legal Reasons| 인터넷 초안; 서버는 요청을 처리하는 것이 법에 의해 금지되어 있다.|N/A|
| **5XX**| **서버 에러**|**-------**|
| 500 Internal Server Error| 서버가 처리할 수 없는 예상치 못한 상황에서 발생한다. |HTTP_SERVER_ERROR<br>HTTP_INTERNAL_ERROR|
| 501 Not Implemented| 요청을 처리하기 위해 필요한 기능을 서버가 제공하지 않는다. PUT요청을 처리할 수 없는 서버에게 클라이언트가 PUT 폼 요청을 보내면 서버는 클라이언트에게 이 응답을 보낸다. |HTTP_NOT_IMPLEMENTED|
| 502 Bad Gateway| 이 코드는 프록시나 게이트웨이 서버에만 적용되며, 프록시 서버가 상단 서버로부터 유효하지 않은 응답을 받았음을 나타낸다. |HTTP_BAD_GATEWAY|
| 503 Service Unavailable| 서버가 과부하나 점검으로 일시적으로 요청을 처리할 수 없다. |HTTP_UNAVAILABLE|
| 504 Gateway Timeout| 프록시 서버가 상단의 원본 서버로부터 일정한 시간 내에 응답을 받지 못했기 때문에, 원하는 응답을 클라이언트에게 보낼 수 없다. |HTTP_GATEWAY_TIMEOUT|
| 505 HTTP Version Not Supported| 클라이언트가 사용하는 HTTP버전을 서버가 지원하지 못한다.(예를 들어, 아직 존재하지 않는 HTTP2.0에 대한 요청이 들어온 경우) |HTTP_VERSION|
| 507 Insufficient Storage| 요청을 통해 전송된 개체를 저장할 충분한 공간이 서버에 없다. 일반적으로 POST나 PUT메서드의 응답으로 사용된다. |-------|
| 511 Network Authentication Required| 클라이언트가 해당 네트워크 접근을 위한 인증이 필요하다. |N/A|

HTTP 버전에 관계없이,
100에서 199까지의 응답 코드는 항상 정보를 제공하는 용도로 사용되고,
200에서 299까지는 항상 성공을 의미한다.
그리고 300에서 399까지는 항상 전송방향을 바꾸는(redirection)용도로 사용되고,
400에서 499까지는 항상 클라이언트의 요청 에러를 나타낸다.
마지막으로 500에서 599까지는 서버 에러를 나타낸다.

###Keep-alive
HTTP 1.0은 요청마다 새로운 연결을 연다. 일반적인 하나의 웹 세션에서 모든 연결을 열고 닫는 데 드는 시간은 데이터를 전송하는 데 드는 시간보다 더 많은 비중을 차지한다. 특히 작은 문서들이 많이 포함된 세션의 경우 그 비중이 더 크다. SSL이나 TLS를 사용하는 암호화된 HTTPS 연결의 경우, 보안 소켓을 연결하는 데 일반 소켓보다 더 많은 작업을 필요로 하기 때문에 더 심각한 문제가 된다.

HTTP1.1과 그 이후 버전에서는, 서버가 응답을 보낸 후에 소켓을 닫지 않는다. 서버는 소켓을 열어 둔 채로 클라이언트가 해당 소켓을 통해 새로운 요청을 보내길 기다린다. 다수의 요청과 응답을 단일 TCP연결을 통해 연속적으로 보낼 수 있다. 그러나 클라이언트의 요청이 있어야만 응답이 오는 기본 구조는 변하지 않는다.

클라이언트는 HTTP요청 헤더의 Connection 필드의 값을 Keep-Alive로 설정하여 소켓을 재사용할 것임을 표시한다.
```java
  Connection: Keep-Alive
```
URL클래스는 명시적으로 해제하지 않는 한 투명하게 HTTP연결 유지(Keep-Alive) 기능을 지원한다. 즉, URL클래스는 서버가 해당 연결을 종료하기 전에 같은 서버에 다시 연결할 경우 소켓을 재사용한다. 여러분은 몇몇 시스템 속성을 통해 자바의 HTTP Keep-Alive 사용법을 제어할 수 있다.

* HTTP Keep-Alive 설정을 켜거나 끄기 위해 http.keepAlive 속성을 true 또는 false로 설정하라. (기본값으로 true가 설정되어 있다.)
* 동시에 유지할 열린 소켓의 최대값을 http.maxConnections 속성에 설정하라. 기본값은 5.
* 연결이 버려진 후에(abandoned connection) 자바가 정리할 수 있도록 http.keepalive.remainingData값을 true로 설정하라. 기본값은 false다.
* 에러를 나타내는 400-과 500- 레벨 응답에 대해 상대적으로 짧은 에러 스트림을 버퍼링하기 위해 재사용을 위해 빨리 해제될 수 있다. 기본값은 false다.
* 에러 스트림 버퍼링을 위해 사용할 바이트 수를 sun.net.http.errorstream.bufferSize속성에 설정하라. 기본값은 4,096바이트다.
* 에러 스트림으로부터 읽기 타임아웃 값을 sun.net.http.errorstream.timeout설정에 밀리세컨드 단위로 설정하라. 기본값은 300밀리세컨드다.

> 실패한 요청으로부터 에러 스트림을 읽어야 할 경우 외에는 sun.net.http.errorstream.enableBuffering 값을 true로 설정하고 싶은 것만 제외한다면 나머지 기본값은 일반적인 상황에 적합한 값으로 되어 있다.

HTTP 2.0의 경우 대부분의 내용이 구글에서 개발한 SPDY 프로토콜을 기반으로 하고 있으며, 이 프로토콜은 헤더 압축, 요청과 응답의 파이프라이닝(pipelining), 비동기 연결 멀티플렉싱 등을 통해 HTTP전송을 더욱 최적화하였다. 그러나 **이러한 최적화는 일반적으로 전송 계층에서 수행되기 때문에** 애플리케이션 프로그래머에게 자세한 내용은 노출되지 않는다. 그렇기 때문에 여러분이 작성하는 코드는 여전히 앞에서 언급한 4단계를 따르게 된다. 자바는 아직 HTTP2.0을 지원하지 않는다. 그러나 자바에 HTTP 2.0 지원이 추가된다고 해도, 여러분이 URL과 URLConnection 클래스를 이용하여 HTTP 서버에 접근하는 한 기존 코드를 수정하지 않아도 HTTP 2.0 기능을 활용할 수 있다.

###HTTP 메서드

HTTP 서버와의 통신은 요청-응답 패턴을 따른다.
하나의 무상태(stateless)요청과 뒤이어 오는 하나의 무상태 응답으로 구성된다.
각각의 HTTP요청은 다음 둘 또는 세 요소로 구성된다.
* 첫 번째 줄은 HTTP메서드와 메서드가 실행될 리소스의 경로를 포함하고 있다.
* 이름-값 필드로 구성된 헤더는 인증 자격과 선호하는 데이터 타입과 같은 메타정보(meta-information)를 제공한다.
* 요청 본문(request body)은 요청된 리소스의 실제 데이터를 포함하고 있다.
(POST와 PUT에서만 해당된다.)

HTTP메서드는 아래와 같은 네가지 주요 메서드가 있음.
각각의 이름은 메서드의 동작을 의미함

| HTTP 메서드 |
| ---|
| GET|
| POST|
| PUT|
| DELETE|

위 메서드가 부족해 보일 수 있다. 특히 프로그램을 설계할 때 익숙한 무한한 객체 기반 메서드와 비교하면 더욱 그렇다. 그건 HTTP가 대부분 명사에 중점을 두고 있기 때문이다.
리소스는 URI에 의해 식별된다. 이 네가지 메서드가 제공하는 단일화된 인터페이스는 거의 모든 실질적인 환경에서 사용하기에 부족하지 않다.

이 네 가지 메서드를 아무렇게나 사용할 수 있는 것은 아니다. 각각은 애플리케이션들이 준수해야 하는 일정한 규칙을 가지고 있다. GET메서드는 요청한 리소스를 읽어들인다.
GET메서드는 요청에 실패해도 추가적인 부작용(side-effect)이 발생하지 않기 때문에, 실패에 대한 걱정 없이 반복적으로 요청할 수 있다. 게다가 곧 알게 되겠지만, GET의 결과는 종종 캐시에 저장된다. 그러나 캐시는 헤더에 의해 제어된다.

제대로 설계된 시스템에서 GET요청은 어렵지 않게 북마크해 두거나 미리 가져올 수 있다.
예를 들어, 브라우저는 사용자가 요청하기 전에 페이지에 포함된 모든 링크에 대해 미리 가져올 수 있다. 예를 들어, 브라우저는 사용자가 요청하기 전에 페이지에 포함된 모든 링크에 대해 미리 GET요청을 해 둘 수 있기 때문에, 단지 링크를 따라가는 것만으로 파일이 삭제되지 않도록 시스템을 설계해야 한다. 반면에 영리한 브라우저나 웹 스파이더(web spider)는 명시적인 유저의 동작 없이 링크에 대해 POST 요청을 하지 않는다.

PUT 메서드는 URL에 명시된 서버로 리소스를 업로드한다. PUT메서드는 부작용(side-effect)에서 자유롭지 않지만, 멱등성(idempotence)을 가지고 있다. 즉, 실패 여부에 상관없이 반복해서 요청할 수 있다. 같은 문서를 같은 서버의 같은 위치에 연속해서 두 번 올리는 것은 한 번만 올렸을 때와 동일한 상태가 된다.

*멱등성: 연산의 한 성질로, 연산을 여러번 적용해도 결과가 달라지지 않는 성질을 의미함.

DELETE메서드는 지정된 URL의 리소스를 삭제한다. 이 메서드 역시 부작용으로부터 자유롭지 않지만, 멱등성을 가지고 있다. 삭제 요청의 성공 여부가 확실하지 않은 경우 - 예를 들어 요청을 보내고 응답을 받기 전에 소켓 연결이 끊어진 경우 - 요청을 다시 보내기만 하면 된다.

동일한 리소스를 두 번 삭제하는 것은 문제가 되지 않는다.

POST메서드는 가장 일반적으로 사용되는 메서드다. 이 메서드 역시 URL에 명시된 서버로 리소스를 업로드하지만, 새로 업로드된 리소스로 서버가 해야 할 일을 명시하지 않는다. 예를 들어, 서버는 업로드된 리소스에 대해 해당 URL로 반드시 접근 가능하도록 만들어야 하는 것은 아니다.
대신 다른 URL로 이동시키거나, 완전히 다른 리소스의 상태를 변경하는 데 업로드된 리소스를 사요할 수도 있다. POST는 구매하기와 같이 반복 요청에 대해 안전하지 않은 동작에 사용해야 한다.

GET요청은 URL안에 필요한 모든 정보를 포함하고 있기 때문에, 북마크를 하거나 링크를 만들 때 또는 웹 스파이더링(web spidering)등에 이용될 수 있다. 그러나 POST,PUT 그리고 DELETE 요청의 경우 그러한 목적으로 사용할 수 없다. 이 메서드들은 의도적인 어떤 동작에 사용되어야 한다. GET은 정적인 웹 페이지를 보는 것과 같이 변화를 일으키지 않은 비의도적인 동작에 사용되지만, 다른 메서드의 경우 특히 POST는 무엇인가 변화를 일으키기 위해 사용된다.
*웹 스파이더링 : 웹 사이트를 스파이더링하여 디렉토리 구조, 기본 디렉토리, 서버의 파일 및 애플리케이션, 깨진 링크, 액세스 불가능한 링크 및 기타 정보를 검색할 수 있다.

예를 들어, 쇼핑 카트에 아이템을 추가하는 작업은 서버에 어떤 변경을 요청하지 않기 때문에 GET으로 보내야 한다. 사용자는 여전히 카트의 내용을 버릴 수 있다. 그러나 주문을 할 때는 변경 요청이 발생하기 때문에 POST로 보내야 한다.
```java
Post 재전송 확인

Confirm Form Resubmission
The page that you're looking for used information that you entered.
Returning to that page might cause any action you took to be repeated. Do you want to continue?
```
POST를 사용한 페이지로 다시 돌아가려고 할 때 브라우저가 여러분에게 확인을 하는 이유이기도 하다. POST데이터를 재전송할 경우 책을 두 번 구매하거나 카드 결제가 두 번 일어날 수 있다.
실제로 오늘날 웹에서는 POST를 매우 남용하고 있다. 아무런 변경을 가하지 않는 안전한 동작에 대해서는 POST보다는 GET을 사용해야 한다. 오직 변경을 가하는 동작에 대해서만 POST를 사용해야 한다.

때로는 잘못된 이유로 GET보다 POST를 선호하는 경우가 있다. 예를 들어, 폼이 많은 양의 입력을 요구하는 경우다. 이 경우 브라우저가 단지 몇 백 바이트 정도의 쿼리 문자열밖에 처리할 수 없을 것이라는 낡은 오해가 있다. 1990년대 중반까지 이 내용은 사실이었지만, 오늘날 모든 주요 브라우저들은 적어도 2,000문자의 URL길이를 지원한다. 이보다 더 많은 데이터가 있을 때 정말 POST를 지원할 필요가 있다. 그러나 비록 일반적이지는 않지만 브라우저가 아닌 클라이언트들을 위해 안전한 동작들은 여전히 GET방식을 사용하도록 노력해야 한다. 일반적으로 단순히 서버상에 존재하는 리소스를 찾을 때보다, 새로운 리소스를 사용하기 위해 서버에 데이터를 업로드할 때 GET의 제한을 초과하게 된다. 그리고 이러한 경우에는 어쨌든 POST나 PUT을 사용하는 것이 일반적으로 정답이다.

이러한 네 가지 주요 HTTP메서드 외에도, 특수한 환경에서 사용되는 몇가지 다른 메서드가 있다. 그 중에서 가장 일반적인 **HEAD메서드**의 경우, 요청된 리소스에 대한 실제 데이터는 없이 헤더만 반환한다는 것만 제외하면 GET메서드처럼 동작한다. 이 메서드는 일반적으로 **로컬 캐시에 저장된 복사본의 유효성을 확인하기 위해, 서버에 있는 파일의 변경일을 확인하는 데 사용** 된다.

자바가 지원하는 또 다른 두 가지 메서드에는 **서버가 해당 리소스에 대해서 무엇을 지원하는지 물어 볼 때 사용** 하는 **OPTIONS**, 그리고 **프록시 서버의 오동작과 같은 상황에서 디버깅 목적으로 클라이언트가 보낸 요청을 응답 본문에 다시 돌려주는 TRACE** 가 있다. 많은 서버들은 표준이 아닌 COPY와 MOVE같은 메서드를 지원하지만, 자바는 이러한 메서드를 보내지 않는다.  

URL 클래스는 HTTP 서버와 통신하는 데 GET 메서드를 사용한다.
URLConnection 메서드는 위 메서드 네가지 모두를 사용할 수 있다.

###요청본문
GET메서드는 URL에 의해 식별된 리소스의 데이터를 읽는다. GET으로 요청한 리소스의 정확한 위치는 경로와 쿼리 문자열의 다양한 요소에 의해 지정된다. 다른 경로와 쿼리 문자열이 다른 리소스로 연결되는 방법은 서버에 의해 결정된다. URL클래스는 그것에 관해 전혀 관여하지 않으며, 해당 URL을 알고 있는 한, 다운로드할 수 있다.

POST와 PUT 메서드는 좀 더 복잡하다. 이 경우에는 클라이언트가 경로와 쿼리 문자열뿐만 아니라 리소스의 데이터도 함께 제공한다. 리소스의 데이터는 헤더 다음에 요청의 본문으로 보내진다. 즉, POST와 PUT은 다음 네 가지 항목을 순서대로 보낸다.
1. 메서드, 경로 그리고 쿼리 문자열과 HTTP 버전을 포함하고 있는 시작 라인
2. HTTP 헤더
3. 빈 줄(두 번의 연속적인 CR/LF 쌍)
4. 본문

예를 들어, 다음 POST요청은 폼 데이터를 서버로 보낸다.
```java
POST /cgi-bin/register.pl HTTP 1.0
Date: Sun, 27 Apr 2013 12:32:36
Host: www.cafeaulait.org
Content-type: application/x-www-form-urlencoded
Content-length: 54

username=Elliotte+Harold&email=elharo%40ibiblio.org
```
이 예제에서, 본문은 application/x-www-form-urlencoded타입의 데이터를 포함하고 있지만, 단지 하나의 예를 든 것뿐이다. 일반적으로 본문은 임의의 아무 데이터나 포함할 수 있다.
그러나 HTTP헤더에는 본문의 상태를 설명하는 다음 두 개의 필드를 꼭 포함해야 한다.
* Content-length필드는 본문의 바이트 수를 명시한다. (54)
* Content-type필드는 바이트의 MIME 미디어 타입을 명시한다.(application/x-www-form-urlencoded)

application/x-www-form-urlencoded MIME 타입은 대부분의 웹 브라우저가 폼 제출 시 가장 일반적으로 사용하는 인코딩 방법임. 그렇기 때문에 이 MIME타입은 브라우저와 통신하는 많은 서버 측 프로그램에 의해 사용된다.

그러나 이외에도 많은 타입을 본문으로 보낼 수 있다. 예를 들어, 사진 공유 사이트에 사진을 업로드하는 카메라는 image/jpeg타입을 보낼 수 있고, 텍스트 편집기는 text/html 타입을 보낼 수 있다. 결국 모든 바이트를 보낼 수 있다. 예를 들어, 다음은 Atom문서를 업로드하는 PUT 요청이다.
```java
PUT /blog/software-development/the-power-of-pomodoros/ HTTP/1.1
Host: elharo.com
User-Agent: AtomMaker/1.0
Authorization: Basic ZGFmZnk6c2VjZXJldA==
Content-Type: application/atom+xml;type=entry
Content-Length:322

<?xml version="1.0"?>
<entry xmlns="http://www.w3.org/2005/Atom">
<title>The Power of Pomodoros</title>
<id>urn:uuid:101a41a6-722b-4d9b-8afb-ccfb01d77499</id>
<updated>2013-02-22T19:40:52Z</updated>
<author><name>Elliotte Harold</name></author>
<content>I hadn't paid much attention to Pomodoro...</content>
</entry>
```

###쿠키
많은 웹 사이트는 연결들 사이에서 지속적인 클라이언트 측의 상태를 저장하기 위해 쿠키(cookie)라고 알려진 텍스트의 작은 문자열을 사용한다. 쿠키는 서버에서 클라이언트로 전달되고 HTTP요청과 응답의 헤더를 통해 다시 전달된다. 쿠키는 세션 ID, 쇼핑 카트, 로그인 자격 정보, 사용자 설정과 같은 것들을 표시하기 위해 서버에 의해 사용된다.

카트에 담아 둔 책의 정보를 표시하기 위해
```java
ISBN=0802099912&price=$34.95
```
와 같은 쿠키 정보를 설정할 수 있다. 그러나 실제 쿠키에는 ATVPDKIKX0DER와 같은 의미없는 문자열 값이 저장되는 경우가 더 많으며, 이 값은 실제 정보가 저장된 어떤 데이터베이스의 특정 record를 가리킨다. 일반적으로 쿠키는 데이터를 직접 저장하기보다는 서버상의 데이터를 가리키고 있다.

쿠키에는 공백 문자, 콤마, 세미콜론을 제외한 아스키 문자만 사용해야 한다.

서버는 클라이언트의 브라우저에 쿠키를 설정하기 위해 HTTP헤더에 Set-Cookie헤더를 포함시킨다. 예를 들어, 다음 HTTP헤더는 이름이 "cart"이고 값이 "ATVPDKIKX0DER"인 쿠키를 설정한다.

```java
HTTP/1.1 200 OK
Content-type: text/html
Set-Cookie: cart=ATVPDKIKX0DER
```
서버로부터 위 응답을 받은 클라이언트가 같은 서버로 다시 요청을 보낼 경우, 해당 요청에는 아래와 같은 쿠키가 설정되어 보내진다.
```java
GET /index.html HTTP/1.1
Host: www.example.org
Cookie: cart=ATVPDKIKX0DER
Accept: text/html
```
HTTP연결 자체는 어떠한 상태 값도 갖지 않지만, 쿠키 값을 이용하면 개별 사용자와 세션을 추적하는 것이 가능해진다.

서버는 하나 이상의 쿠키를 설정할 수 있다. 예를 들어, 아마존(Amazon) 사이트에 접속하면 아래와 같은 다섯 개의 쿠키가 설정된다.
Set-Cookie:skin=noskin
Set-Cookie:ubid-main=176-5578236-9590213
Set-Cookie:session-token=Zg6afPNqbaMv2WmYF0v57zCU106Ktr
Set-Cookie:session-id-time=2082787201l
Set-Cookie:session-id=187-4969589-3049309

단순한 이름=값 쌍 이외에도, 쿠키에는 유효기간, 경로도메인, 포트, 버전 그리고 보안 옵션과 같은 쿠키 자신의 범위를 제어하는 다양한 속성이 설정을 가질 수 있다.

쿠키는 기본적으로 쿠키를 생성한 서버에 대해서만 적용된다. 만일 www.foo.example.com 사이트에서 쿠키가 설정될 경우,

**브라우저**는 www.foo.example.com 사이트에 대해서만 다시 쿠키를 보낸다. 그러나 쿠키를 생성한 원래 서버 이외에 모든 서브도메인에 적용되도록 지정할 수 있다. 예를 들어, 아래 요청은 foo.example.com 도메인 전체에 대한 user쿠키를 설정한다.
```java
Set-Cookie: user=elharo;Domain=.foo.example.com
```
위 쿠키가 설정된 브라우저는 www.foo.example.com뿐만 아니라, lothar.foo.example.com, eliza.foo.example.com, enoch.foo.example.com 그리고 foo.example.com 도메인 안에 있는 모든 호스트에 대해 이 쿠키를 적용한다. 그러나 서버는 자신이 직접 속해 있는 도메인에 대한 쿠키만 설정할 수 없다.

그러나, **서버**는 자신이 직접 속해 있는 도메인에 대한 쿠키만 설정할 수 있다.
www.foo.example.com 서버는 www.oreilly.com, example.com 서버에 대한 쿠키를 설정할 수 없다.

> 웹 사이트들은 다른 도메인의 이미지 또는 콘텐츠를 삽입하는 방법으로 이 제한을 회피한다.
해당 페이지 자체가 아닌, 내장된 콘텐츠에 의해 설정된 쿠키를 서드파티 쿠키라고 부른다. 많은 사용자들이 모든 서드파티 쿠키를 차단하고 있으며, 몇몇 웹 브라우저들은 개인정보 보호를 이유로 기본적으로 서드파티 쿠키를 차단하기 시작했다.

쿠키는 또한 경로에 의해 범위가 제한된다. 범위가 제한된 쿠키는 전체가 아닌 제한된 몇몇 디렉터리에 대해서만 반환된다. 쿠키의 기본 범위는 자신이 생성된 URL과 해당 URL의 모든 서브 디렉터리이다. 예를 들어, 다음 http://www.cafeconleche.org/XOM/URL 에 대한 쿠키가 설정된다면, 이 쿠키는 또한 다음 http://www.cafeconleche.org/XOM/apidocs/ 에는 적용되지 않는다. 그러나 쿠키의 기본 범위는 Path 속성을 사용하여 변경될 수 있다. 예를 들어, 아래 응답은 해당 서버의 /restricted하위 디렉터리에만 적용되는 이름이 user이며 elharo값을 가진 쿠키를 브라우저에게 보낸다.
```java
Set-Cookie: user=elharo; Path=/restricted
```
위 쿠키가 설정된 클라이언트는 동일한 서버의 /restricted 하위 디렉터리에 있는 문서를 요청할 때만 해당 쿠키를 보내며, 해당 사이트의 다른 디렉터리에는 보내지 않는다.

쿠키에 도메인과 경로를 모두 지정할 수 있다. 예를 들어, 아래 쿠키는 example.com도메인 안에 있는 모든 서버의 /restricted 경로에 적용된다.
```java
Set-Cookie: user=elharo; Path=/restricted; Domain=.foo.example.com
```
쿠키 설정 시 속성들의 순서는 중요하지 않지만, 속성들은 서로 세미콜론으로 구분되어야 하며, 쿠키 자신의 이름과 값을 가장 먼저 써야 한다. 그러나 클라이언트가 쿠키를 서버로 다시 보낼 때는 이 규칙이 적용되지 않는다. 아래의 경우, **항상 경로가 도메인 앞에** 와야 한다.
```java
Cookie: user=elharo; Path=/restricted; Domain=.foo.example.com
```

쿠키는 다음과 같은 Wdy, DD-Mon-YYYY HH:MM:SS GMT 형태의 날짜로 expires 속성을 설정하여 특정 시점에 파기되도록 할 수 있다. 요일과 월은 세 단어로 축약해 쓰며, 그 외에는 숫자로 쓰고 필요한 경우 앞자리에 0을 채운다. java.text.SimpleDateFormat의 패턴 언어로 expires의 날짜 형식은 다음 E, dd-MMM-yyyy H:m:s 와 같이 표현된다.
예를 들어, 아래 쿠키는 2015년 12월 21일 오후 3:23분에 파기된다.
```java
Set-Cookie: user=elharo; expires=Wed, 21-Dec-2015 15:23:00 GMT
```
브라우저는 지정된 날짜가 지나면 자신의 **캐시로부터 이 쿠키를 제거해야** 한다.

Max-Age 속성은 위와 같이 특정 시점이 아닌 특정 시간(초)이 지난 후에 쿠키가 파기되도록 설정한다. 예를 들어, 다음 쿠키는 설정된 이후 한 시간(3,600초)이 지나면 파기된다.

```java
Set-Cookie: user="elharo"; Max-Age=3600
```
브라우저는 Max-Age에 설정된 초만큼의 시간이 경과한 뒤에 이 쿠키를 삭제해야 한다.

쿠키는 패스워드와 세션과 같은 민감한 정보를 포함할 수 있기 때문에, 몇몇 쿠키의 경우 안전하게 전송되어야 한다. 대부분의 경우 이 말은 곧 HTTP 대신 HTTPS 사용을 의미한다.
하지만 그것의 의미와는 상관없이, 각 쿠키는 아래와 같이 값이 없는 secure 속성을 가질 수 있다.
```java
Set-Cookie: key=etrogl7*;Domain=.foo.example.com; secure
```
브라우저는 이와 같은 쿠키에 대해 안전하지 않은 채널로 전송되지 않도록 해야 한다.
XSRF와 같은 쿠키를 훔치는 공격에 대응하기 위해서, 쿠키에 HttpOnly 속성을 설정할 수 있다. 이 속성은 브라우저에게 HTTP와 HTTPS를 통해서만 쿠키 값을 반환하도록 말한다.
특히 JavaScript를 통해 해당 쿠키에 접근할 수 없도록 한다.
```Java
Set-Cookie: key=etrogl7*; Domain=.foo.example.com; secure; httponly
```
지금까지 설명한 내용은 쿠키가 내부적으로 작동하는 방법이다.

다음은 아마존 사이트로부터 받은 온전한 쿠키 집합이다.


이 쿠키 내용을 보면 아마존은 유저의 브라우저가 앞으로 30~33년간 아마존 도메인 내에 있는 어떠한 사이트를 방문하더라도 요청과 함께 이 쿠키를 보내길 원한다.

물론 브라우저는 이러한 쿠키의 요청을 무시할 수 있다. 그리고 언제든지 쿠키를 삭제하거나 차단할 수 있다.
