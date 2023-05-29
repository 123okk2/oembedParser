# oembedParser
oembed 파싱 페이지

## oembed

* oembed 소개 페이지 : https://oembed.com/

oembed는 웹 사이트의 내용을 다른 페이지에 포함할 수 있도록 설계된 개방형 포맷이다.

즉 사용자가 resource의 링크를 입력할 때 직접 해당 resource를 파싱하지 않으면서 API를 이용해 내장된 컨텐츠, 즉 사진이나 비디오 등의 미디어 파일을 보여줄 수 있게 해주는 API이다.


## oembed 예시

youtube, instagram, vimeo, twitter 등에서 oembed를 제공한다.
예를 들어 youtube의 경우 아래와 같이 oembed를 제공한다.

GET : https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=_oSrSG5Y4oQ
```
{
  "title":"[\ub9b4\ub808\uc774\ub304\uc2a4] LE SSERAFIM(\ub974\uc138\ub77c\ud54c) - UNFORGIVEN (feat.Nile Rodgers) (4K)",
  "author_name":"M2",
  "author_url":"https://www.youtube.com/@MnetM2",
  "type":"video",
  "height":113,
  "width":200,
  "version":"1.0",
  "provider_name":"YouTube",
  "provider_url":"https://www.youtube.com/",
  "thumbnail_height":360,
  "thumbnail_width":480,
  "thumbnail_url":"https://i.ytimg.com/vi/_oSrSG5Y4oQ/hqdefault.jpg",
  "html":"\u003ciframe width=\u0022200\u0022 height=\u0022113\u0022 src=\u0022https://www.youtube.com/embed/_oSrSG5Y4oQ?feature=oembed\u0022 frameborder=\u00220\u0022 allow=\u0022accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\u0022 allowfullscreen title=\u0022[\ub9b4\ub808\uc774\ub304\uc2a4] LE SSERAFIM(\ub974\uc138\ub77c\ud54c) - UNFORGIVEN (feat.Nile Rodgers) (4K)\u0022\u003e\u003c/iframe\u003e"
}
```

## 프로그램 설명

해당 프로그램은 사용자로부터 URL을 입력받아 해당 URL의 oembed 정보를 파싱해서 사용자에게 출력하는 프로그램이다.

현재는 youtube, twitter, vimeo, instagram 에서 oembed 정보 파싱을 지원하며, application.properties 내 oembed.source 변수 수정을 통해 추가 웹 사이트에서 oembed 정보를 파싱할 수 있다.

위 네 개의 사이트 중 instagram의 경우는 별도의 key가 필요하며 application.properties 내 oembed.access.token.instagram 에 해당하는 key를 입력해주어야 한다. 안그러면 에러가 발생한다.

사용방법은 웹 사이트 http://{ip}:8080 에 접속한 후 상단의 검색창에 대상 URL을 입력하면 된다.
![image](https://github.com/123okk2/oembedParser/assets/51351974/a2aa644a-726f-441d-942d-1560dec45b13)
