---
title: Spring Programming Part1~3
layout: post
---
# Spring Programming Chapter 1~3 정리
---------------------------------------------------
Spring Programming의 Chapter 1~3 에서 중요한 부분 정리 

# Chapter 1

## 스프링 프레임워크

스프링 프리엠워크는 일반 사용자를 위한 웹 기반 어플리케이션에서 부터 기업 환경의 어플리케이션 까지 다양한 영역에서 사용되며, 스프링 팀(http://spring.io/team)은 주요 모듈의 다양한 프로젝트 개발을 진행하며, 이를 통해 개발자들에게 효율적으로 어플리케이션을 개발할 수 있도록 하고 있음.

### 스프링 프로젝트 목록

| 프로젝트 | 설명 |
| :-----: | :--- |
| 스프링 프레임워크(Spring Framework) | 스프링을 이용해서 어플리케이션을 개발할 때 기반이 되는 프레임워크이며, 핵심 기능인 DI와 AOP 기능을 제공한다. 웹 어플리케이션을 개발할 때 사용하는 스프링 MVC, 스프링 ORM 등의 기능도 스프링 프레임워크에 포함되어 있다. |
| 스프링 데이터(Spring Data) | 데이터 연동을 위한 단일 API를 제공하며, 이 API를 기반으로 JPA, MongoDB, Neo4j, Redis 등 RDBMS와 NoSQL과의 연동을 적은 양의 코드로 처리할 수 있도록 해준다. |
| 스프링 시큐리티(Spring Security) | 인증과 허가에 대한 기반 프레임워크 및 관련 모듈을 제공한다. 웹 어플리케이션을 위한 보안을 간단한 설정과 약간의 코드 구현으로 처리한다. |
| 스프링 배치(Spring Batch) | 배치 처리를 위한 기반 프레임워크를 제공해준다. 데이터 처리, 흐름 제어, 실패 재처리 등 배치 처리 어플리케이션이 필요로 하는 기능을 기본으로 제공한다. |
| 스프링 인터그레이션(Spring Integration) | 시스템 간의 연동을 위한 메시징 프레임워크를 보여준다. |
| 스프링 소셜(Spring Social) | 트위터, 페이스북 등 소셜 네트워크 연동을 위한 기능을 제공한다. |

### 스프링 프레임워크의 주요 모듈

스프링 프레임워크는 제공하는 기능에 따라 별도의 모듈로 분리 되어 있는데 트랜잭션과 관련 기능을 제공하는 모듈은 spring-tx 이며, 웹 개발과 관련된 기능을 제공하는 모듈은 spring-webmvc 이다. 각각의 모듈은 jar 파일로 제공되며, 한 모듈을 사용하려면 의존된 다른 모듈이 필요한 경우도 있으므로 같이 설정해 주어야 한다.

* 스프링의 주요 모듈 목록

|   프로젝트   | 설명 |
| :---------: | :--- |
| **spring-beans**   | 스프링 컨테이너를 이용해서 객체를 생성하는 기본 기능을 제공한다. |
| **spring-context** | 객체생성, 라이프사이클 처리, 스키마 확장 등의 기능을 제공한다. |
| **spring-aop** | AOP(**Aspect Oriented Programming**) 기능을 제공 |
| spring-web | REST 클라이언트, 데이터 변환 처리, 서블릿 필터, 파일 업로드 지원 등 웹 개발에 필요한 기반 기능을 제공한다. |
| spring-webmvc | 스프링 기반의 MVC 프레임워크이며, 웹 어플리케이션을 개발하는데 필요한 컨트롤러, 뷰 구현을 제공한다. |
| spring-websocket | 스프링 MVC에서 웹 ㅗ소켓 연동을 처리할 수 있도록 한다. |
| spring-oxm | XML과 자바 객체 간의 매핑을 처리하기 위한 API를 제공한다. |
| spring-tx | 트랜잭션 처리를 위한 추상 레이어를 제공한다. |
| spring-jdbc | JDBC 프로그래밍을 보다 쉽게 할 수 있는 템플릿을 제공한다. |
| spring-orm | 하이버네이트, JPA, MyBatis 등과의 연동을 지원한다. |
| spring-jms | JMS 서버와 메시지를 쉽게 주고 받을 수 있도록 하기 위한 템플릿, 어노테이션 등을 제공한다. |
| spring-context-support | 스케줄링, 메일 발송, 캐시 연동, 벨로시티 등 부가 기능을 제공한다. |

메이븐 프로젝트에서 모듈을 사용하려면 사용할 모듈명과 버전을 의존 설정에 추가해준다.

```
<dependencies>
 	<dependency>
 		<groupId>org.springframework</groupId>
		<artifactId>spring-webmvc</artifactId> <!-- 모듈명 -->
 		<version>4.0.4.RELEASE</version> <!-- 버전 -->
 	</dependency>
 	<dependency>
 		<groupId>org.springframework</groupId>
		<artifactId>spring-orm</artifactId> <!-- 모듈명 -->
 		<version>4.0.4.RELEASE</version> <!-- 버전 -->
 	</dependency>
</dependencies>
```
# Chapter 2

## DI(Dependency Injection)를 이용한 객체 생성

### DI(Dependency Injection)

의존(Dependency)를 처리하기 위한 설계 패턴으로, 스프링에서는 기본적으로 DI 기반으로 동작

### 의존(Dependency) 객체 생성
기능을 실행하기 위해 다른 클래스(타입)를 필요로 할 때 **의존(Dependency)** 한다고 한다.

* 의존하는 타입을 로컬 변수 'br' 로 정의
```java
public class FilePrinter {
	public void print(String filePath) throws IOException{
		// 의존하는 타입을 'br'이라는 변수로 정의
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
			String line = null;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
		}
	}
}
```
> FilePrinter 클래스 안에 있는 print 메서드를 실행하기 위해서는 BufferedReader 클래스가 필요하기 때문에 **FilePrinter 클래스가 BufferedReader 클래스에 의존**한다고 말할 수 있다.

* 의존 객체를 필드(encryptor)로 정의 --> 의존객체를 직접 생성하는 방식
```java
private class FileEncrytor{
	// 의존 객체를 필드(encryptor)로 정의
	private Encryptor encryptor = new Encryptor();

	public void encryt(File src, File target) throws IOException{
		try(FileInputStream is = new FileInputStream(src);
			FileOutputStream out = new FileOutPutStream(target)){
				byte[] data = new byte[512];
				int len = -1;
				while((len = is.read(data)) != -1){
					encryptor.encrypt(data, 0, len);
					out.write(data, 0, len);
				}
			}
		)
	}
}
```

* 의존 객체를 생성하지 않고 외부에서 전달 받은 경우 생성자의 파라미터를 이용하여 의존 하는 타입의 객체를 전달받을 수 있다.
```java
public class FileEncryptor{
	private Encryptor encryptor;

	// 의존하는 타입의 객체를 생성자의 파라미터를 통해 전달 받음
	public FileEncryptor(Encryptor enc){
		this.encryptor = enc;
	}
	...
}
```
> 생성자의 파라미터를 통해 의존 객체를 전달받은 경우, FileEncryptor 객체를 생성할 때 의존하는 객체를 생성자의 파라미터로 전달해야 한다.
```java
public static void main(String[] args){
	Encryptor enc = new Encryptor();
	FileEncryptor fileEncryptor = new FileEncryptor(enc);

	try{
		fileEncryptor.encrypt(New File(args[0]), new File(args[1]));
	}catch(IOException ex){
		// 예외처리
	}
}

### 의존 객체를 직접 생성하는 방식의 단점 
* 의존 객체를 직접 생성하는 방식의 코드
```java
private class FileEncrytor{
	// 의존 객체를 필드(encryptor)로 정의
	private Encryptor encryptor = new Encryptor();

	public void encryt(File src, File target) throws IOException{
		try(FileInputStream is = new FileInputStream(src);
			FileOutputStream out = new FileOutPutStream(target)){
				byte[] data = new byte[512];
				int len = -1;
				while((len = is.read(data)) != -1){
					encryptor.encrypt(data, 0, len);
					out.write(data, 0, len);
				}
			}
		)
	}
}
```
> 요구사항의 변화로 Encryptor 클래스의 하위 클래스인 FastEncryptor 클래스를 사용해야 되는 사항이 발생할 경우, FileEncryptor 클래스의 코드를 변경해야 함

** 의존객체를 직접 생성하는 방식은 개발 효율을 낮추는 상황을 만들거나  변경하는 클래스의 객체를 사용해야 하는 코드가 많으면 많을 수록 비례해서 변경해주어야 하는 코드의 양도 증가한다.

### DI를 사용하는 방식의 코드 : 의존 객체를 외부에서 조립함

DI는 의존 객체를 **외부로 부터 전달**받는 구현방식으로 **생성자**를 이용해서 의존 객체를 전달 받는 방식이 DI에 따라 구현한 것

```java
public class FileEncryptor{
	private Encryptor encryptor;

	public FileEncryptor(Encryptor encryptor){
		// 생성자로 전달받은 객체를 필드에 할당
		this.encryptor = encryptor;
	}

	public void encrypt(File src, File target) throws IOException{
		...
		// DI 방식으러 전달받은 객체를 사용
		encryptor.encrypt(data, 0, len);
	}
}
```
> FileEncryptor 클래스는 Encryptor 타입의 객체를 필요로 하는데 **생성자**를 통해서 Encryptor 타입의 객체를 전달받고 있다.
FileEncryptor 객체를 생성하는 부분에서 Encryptor 객체를 전달해 주어야 함을 뜻한다.

```java
// 다른 코드에서 의존 객체를 생성
Encryptor enc = new Encryptor();
// 의존 객체를 전달해준다.
FileEncryptor fileEnc = new FileEncryptor(enc);
```








