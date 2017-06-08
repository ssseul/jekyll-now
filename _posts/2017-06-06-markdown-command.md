---
title: Markdown 문법 정리
layout: post
---
# GitHub에서 자주 사용 되는 Markdown 문법
----------------------------------------
GitHub에서 자주 사용 되는 Markdown 문법을 정리

# 마크다운 사용법(문법)

## 1.1. 타이틀
* 큰제목: 문서 제목
    ```
    This is an H1
    =============
    ```
    This is an H1
    =============

* 작은제목: 문서 부제목
    ```
    This is an H2
    -------------
    ```
    This is an H2
    -------------

* 글머리: 1~6까지만 지원
```
# This is a H1
## This is a H2
### This is a H3
#### This is a H4
##### This is a H5
###### This is a H6
```
# This is a H1
## This is a H2
### This is a H3
#### This is a H4
##### This is a H5
###### This is a H6
####### This is a 7.

## 1.2. BlockQuote
이메일에서 사용하는 ```>``` 블럭인용문자를 이용.
```
> This is a blockqute.
```
> This is a first blockqute.
>	> This is a second blockqute.
>	>	> This is a third blockqute.

이 안에서는 다른 마크다운 요소를 포함.
> ### This is a H3
> * List
>	```
>	code
>	```

## 1.3. 목록
### ● 순서있는 목록(번호)
순서있는 목록은 숫자와 점을 사용.
```
1. 첫번째
2. 두번째
3. 세번째
```
1. 첫번째
2. 두번째
3. 세번째

**현재까지는 어떤 번호를 입력해도 순서는 내림차순으로 정의.**
```
1. 첫번째
3. 세번째
2. 두번째
```
1. 첫번째
3. 세번째
2. 두번째

### ● 순서없는 목록(글머리 기호)
```
* 빨강
  * 녹색
    * 파랑

+ 빨강
  + 녹색
    + 파랑

- 빨강
- 녹색
- 파랑
```
* 빨강
  * 녹색
    * 파랑

+ 빨강
  + 녹색
    + 파랑

- 빨강
  - 녹색
    - 파랑

## 1.4. 코드```<pre><code></code></pre>```
```
This is a normal paragraph:

    This is a code block.
end code block.
```
실제로 적용해보면,
This is a normal paragraph:

    This is a code block.
end code block.

## 1.5. 수평선```<hr/>```
아래 줄은 모두 수평선을 만든다. 마크다운 문서를 미리보기로 출력할 때 *페이지 나누기* 용도로 많이 사용.
```
* * *

***

*****

- - -

---------------------------------------
```


## 1.6. 링크
* 참조링크

```
[link keyword][id]
[id]: URL "Optional Title here"

Link: [Google][googlelink]
[googlelink]: https://google.com "Go google"
```

Link: [Google][googlelink]
[googlelink]: https://google.com "Go google"

* 인라인 링크
```
syntax: [Title](link)
```
Link: [Google](https://google.com, "google link")

* 자동연결
```
syntax : <http://example.com/>
syntax : <address@example.com>
```
<http://example.com/>
<address@example.com>


## 1.7. 강조
```
*single asterisks*
_single underscores_
**double asterisks**
__double underscores__
++underline++
~~cancelline~~
```
*single asterisks*
_single underscores_
**double asterisks**
__double underscores__
++underline++
~~cancelline~~


## 1.8. 이미지
```
syntax : ![Alt text](/path/to/img.jpg)
syntax : ![Alt text](/path/to/img.jpg "Optional title")
```
![openup_dongho_1](/archive/KakaoTalk_20170606_012448033.jpg "openup_dongho_1")
![openup_dongho_2](/archive/KakaoTalk_20170606_012448346.jpg "openup_dongho_2")
![selfie_dongho](/archive/KakaoTalk_20170606_012719090.jpg "selfie_dongho")
![fire_dongho](/archive/KakaoTalk_20170606_012856049.jpg "fire_dongho")