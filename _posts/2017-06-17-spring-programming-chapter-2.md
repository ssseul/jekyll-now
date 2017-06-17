---
title: Spring Programming Part1~3
layout: post
---
# Spring Programming Chapter 2 정리
---------------------------------------------------
Spring Programming의 Chapter 2 에서 중요한 부분 정리 

# Chapter 2

## DI(Dependency Injection)를 이용한 객체 생성

### 1.DI(Dependency Injection)

의존(Dependency)를 처리하기 위한 설계 패턴으로, 스프링에서는 기본적으로 DI 기반으로 동작

#### 의존(Dependency) 객체 생성
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

#### 의존 객체를 직접 생성하는 방식의 단점 
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

의존객체를 직접 생성하는 방식은 개발 효율을 낮추는 상황을 만들거나 변경하는 클래스의 객체를 사용해야 하는 코드가 많으면 많을 수록 비례해서 변경해주어야 하는 코드의 양도 증가한다.

#### DI를 사용하는 방식의 코드 : 의존 객체를 외부에서 조립함

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
**FileEncryptor 스스로 의존하는 객체를 생성하지 않고 외부의 누군가가 의존하는 객체를 FileEncryptor에서 넣어준다는 의미로, 이런 방식을 DI라고 하며, 또 다른 의미로는 객체를 연결한다는 표현을 쓴다.**

**조립기**를 구현을 통해 객체를 생성하고 연결해주는 역할을 수행한다.

* 조립기 구현 코드 
```java
public class Assembler{
	private FileEncryptor fileEnc;
	private Encryptor enc;

	public Assembler(){
		enc = new Encryptor();
		FileEnc = new FileEncryptor(enc);
	}

	public FileEncryptor fileEncryptor(){
		return fileEnc;
	}
}
```

* 조립기를 이용한 FileEncryptor 구하기
```java
Assembler assembler = new Assembler();
FileEncryptor fileEnc = assembler.fileEncryptor();
fileEnc.encrypt(srcFile, targetFile);
```

* 객체 타입이 변경된 경우
```java
public class Assembler{
	private FileEncryptor fileEnc;
	private Encryptor enc;

	public Assembler(){
		enc = new FastEncryptor();
		FileEnc = new FileEncryptor(enc);
	}

	...
}
```
> FileEncryptor 의 코드를 변경하지 않고 **조립기** 코드를 변경하면 된다.
	
** 의존하는 클래스의 구현이 완성되어 있지 않더라도 테스트가 가능하다는 장점이 있다.**

#### DI에서 의존 객체 전달 방법 : 생성자 방식과 프로퍼티 설정 방식

##### 1. 생성자 생성 방식
```java
public class FileEncryptor{
	private Encryptor encryptor;

	// 생성자를 통해 의존 객체를 전달받음
	public FileEncryptor(Encryptor encryptor){
		this.encryptor = encryptor;
	}
}
```

* 생성자 방식의 장점은 **객체를 생성하는 시점에 의존하는 객체를 모두 전달**받을 수 있다. 전달받은 파라미터가 정상인지 확인하는 코드를 생성자에 추가할 경우, 객체 생성 이후 해당 객체가 사용 가능 상태임을 보장 받을 수 있다.

```java
// 객체 생성 시점에 의존 객체가 정상인지 확인
public class FileEncryptor{
	public FileEncryptor(Encryptor enc){
		if(enc == null){
			throw new IllegalArgumentException();
			this.encryptor = enc;
		}
	}
	...
}

// FileEncryptor가 정상적으로 생성되면, FileEncryptor 객체는 사용가능 상태가 됨
FileEncryptor fileEnc = new FileEncryptor(enc);
fileEnc.encrypt(src, target);
```
* 생성자 방식의 단점은 생성자에 전달되는 파라미터 이름으로는 실제 타입을 알아내기 어렵고, 생성자에게 전달되는 파라미터가 증가될수록 코드 가독성이 저하된다.

##### 2. 프로퍼티 설정 방식
의존 객체를 전달받기 위해 메서드를 이용하며, 자바빈(JavaBeans)의 영향으로 setPropertyName()형식의 메서드를 주로 사용

```java
public class FileEncryptor{
	private Encryptor encryptor;

	// set 메서드를 통해 의존 객체를 전달받음
	public void setEncryptor(Encryptor encryptor){
		this.encryptor = encryptor;
	}
}
```

* 프로퍼티 설정 방식의 장점은 어떤 의존 객체를 설정하는 지 메서드 명으로 알 수 있다는 점이다.
```java
FileEncryptor fileEnc = new FileEncryptor()
// enc 파라미터 타입을 확인하지 않아도 setEncryptor() 라는 프로퍼티 설정 메서드의 이름을 통해 enc가 Encryptor 객체임을 파악
fileEnc.setEncryptor(enc);
```
* 프로퍼티 설정 방식의 단점은 객체를 생성한 뒤에 의존 객체가 모두 설정되었다는 보장이 없으므로 사용 가능하지 않는 상태일 가능성이 존재한다.

### 스프링은 객체를 생성하고 연결해주는 DI 컨테이너

**DI : 스프링의 핵심 기능 중의 하나**
**스프링은 객체를 생성하고 각각의 객체를 연결해주는 조립기 역할을 하게 된다.**

```java
String configLocation = "classpath:applicationContext.xml";
AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
Project project = ctx.getBean("sampleProject", Project.class);
project.build();
ctx.close();
```
* GenericXmlApplicationContext
조립기 기능을 구현한 클래스이며, 조립기에서 생성할 객체가 뭔지, 각 객체를 어떻게 연결하는 지에 대한 정보는 **XML 파일**에 정의 되어있다. XML 파일에 정의된 설정 정보를 읽어봐 객체를 생성하고 각각의 객체를 연결한 뒤에 내부적으로 보관한다. 이렇게 **생성한 객체를 보관하기 때문에 스프링은 객체 컨테이너(Object Container)** 라고도 한다.

XML을 이용한 스프링 설정은 컨테이너가 생성할 객체를 지정하기 위해 <bean> 태크를 사용하는데, 스프링 컨테이너가 생성해서 보관하는 객체를 스프링 빈(Spring Bean) 객체라고 부르며, 일반적인 자바 객체와 동일하다

![spring_1](/archive/spring_1.PNG "spring_1")

** 스프링 설정으로부터 스프링 컨테이너를 만들고, 컨테이너는 설정에 명시한 스프링 빈 객체를 생성해서 지정한 이름으로 보관

스프링 컨테이너는 생성한 빈 객체를 <이름, 빈 객체> 쌍으로 보관하며, 스프링 컨테이너가 보관하고 있는 객체를 사용할 경우, 빈 객체와 연결된 이름을 이용해서 객체를 참조하면 된다.

### 2. 스프링 컨테이너 종류

스프링은 **BeanFactory**와 **ApplicationContext** 두 가지 타입의 컨테이너를 제공한다.

#### BeanFactory와 ApplicationContext의 관계

![spring_2](/archive/spring_2.PNG "spring_2")

BeanFactory 계열의 인터페이스만 구현한 클래스는 단순히 컨테이너에서 객체를 생성하고 DI를 처리해주는 기능만 제공한다. 하지만 스프링을 사용하는 이유는 편리한 트랜잭션 처리, 자바 코드 기반 스프링 설정, 어노테이션을 이용한 빈 설정, 스프링을 이용한 웹 개발, 메세지 처리 등의 **다양한 부가 기능** 때문인데, 이러한 부가 기능을 사용하기 위해서는 **ApplicationContext** 계열을 사용해야 한다. 이런 이유로 ApplcationContext를 주로 사용하며, BeanFactory를 사용하는 경우는 매우 드물다.

#### ApplicationContext 인터페이스와 관련된 클래스 계층 구조

![spring_3](/archive/spring_2.PNG "spring_3")

말단에 위치한 클래스가 실제로 사용되는 구현 클래스이며, 다음과 같다

* GenericXmlApplicationContext : XML 파일을 설정 정보로 사용하는 스프링 컨테이너 구현 클래스이다. 독립형 어플리케이션을 개발할 때 사용된다.

* AnnotationConfigApplicationContext : 자바 코드를 설정 정보로 사용하는 스프링 컨테이너이다. 독립형 어플리케이션을 개발할 때 사용된다.

* GenericGroovyApplicationContext : 웹 어플리케이션을 개발할 때 사용하는 스프링 컨테이너로써 XML 파일을 설정 정보로 사용한다.

* AnnotationConfigWebApplicationContext : 웹 어플리케이션을 개발할 때 사용하는 스프링 컨테이너로써 자바 코드를 설정 정보로 사용한다.

GenericApplicationContext 클래스를 상속받는 3개의 클래스는 스프링 컨테이너를 코드에서 직접 생성할 때 사용된다.

WebApplicationContext 로 끝나는 2개의 클래스는 스프링 MVC를 이용해서 웹 어플리케이션을 개발할 때 사용한다. 두 WebApplicationContext 클래스를 코드에서 직접 사용할 일은 없으며, web.xml과 같은 웹 어플리케이션 설정 파일에서 간접적으로 사용하게 된다.

#### GenericXmlApplicationContext 설정 파일 지정 

* GenericXmlApplicationContext 생성자에 전달하면 되며, 생성자 파라미터는 가변인자이므로 한 개 이상의 값을 파라미터로 전달이 가능하다.
```java
// 1개 이상의 설정 파일 경로를 값으로 전달 가능
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:/spring-member.xml", "spring:/spring-board.xml", "spring:/datasource.xml");
```
* 스프링 설정이 클래스패스 루트가 아닌 다른 곳에 위치한다면, 루트를 기준으로 경로 형식을 입력해준다.
```java
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:/conf/spring/conf.xml");
```

* 파일시스템에서 설정 파일을 읽어오고 싶다면, file:접두어를 이용한다.
```java
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:/conf/spring-*.xml");
```

* 특정 경로에 있는 모든 xml 파일을 설정파일로 사용할 경우 아스테리크(*)를 사용해서 지정한다.
```java
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:/conf/spring-*.xml")
```



