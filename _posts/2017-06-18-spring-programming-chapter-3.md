---
title: spring programming chapter 3
layout: post
---
Spring Programming의 Chapter 3 에서 중요한 부분 정리 

# Chapter 3

## 빈 객체의 라이프사이클

스프링 컨테이너는 빈 객체를 생성하고, 프로퍼티를 할당하고, 초기화를 수행하고, 사용이 끝나면 소멸시키는 일련의 과정을 관리한다.

2 가지 방식을 이용해서 빈의 라이프사이클을 관리한다

* 스프링이 제공하는 특정 인터페이스를 상속받아 빈을 구현
* 스프링 설정에서 특정 메서드를 호출하라고 지정한다.

스프링은 라이프사이클에 특화된 인터페이스를 제공하는데, InitializingBean 인터페이스를 제공하고 있으며, 빈 객체의 클래스가 InitializingBean 인터페이스를 구현하고 있으면 InitializingBean 인터페이스에 정의된 메서드를 호출해서 빈 객체가 초기화를 진행할 수 있도록 한다. 또한 스프링 설정에서 초기화 메서드를 지정하면, 스프링은 그 메서드를 호출해서 빈이 초기화를 수행할 수 있게 한다.

뿐만 아니라 스프링은 빈의 라이프사이클을 관리할 수 있도록 인터페이스와 설정 방법을 제공하고 있다.

### 1.빈 라이프사이클

![spring_4](/archive/spring_2.PNG "spring_4")

[인터페이스명.메서드명()] 형식을 갖는 단계는 빈 객체가 해당 인터페이스를 구현했을 경우, 메서드가 호출됨을 의미.

전체 흐름은 객체 생성/프로퍼티 생성 -> 초기화 -> 사용 -> 소멸의 단계로 진행되고 있다. 컨테이너는 빈 객체를 생성하고 프로퍼티를 설정한 뒤에 빈의 초기화를 진행하며, 컨테이너를 종료하는 과정에서(close()) 생성한 빈 객체의 소멸 과정을 진행한다.

빈의 초기화와 소멸 방법은 각각 3가지가 존재하며, 각 방식이 쌍을 이루어 함께 사용되곤 한다. @PostConstruct 어노테이션을 사용해서 초기화 메서드를 지정했다면 @PreDestory 어노테이션을 사용해서 소멸 메서드를 지정하고, 커스텀 init 메서드를 사용했다면 커스텀 destory 메서드를 사용하는 식이다(초기화와 소멸 방식을 다르게 해도 문제는 없다.)

### 2. Initializing Bean 인터페이스와 DisposableBean 인터페이스

스프링은 객체의 초기화 및 소멸 과정을 위해 다음의 두 인터페이스를 제공한다.

*o.s.beans.factory.initailizingBean : 빈의 초기화 과정에서 실행될 메서드를 정의
*o.s.beans.factory.DisposableBean : 빈의 소멸 과정에서 실행될 메서드를 정의

두 인터페이스는 다음과 같이 정의된다.

```java
public interface InitializingBean{
	void afterPropertiesSet() throws Exception;
}

public interface DisposableBean{
	void destory() throws Exception;
}
```

스프링 컨테이너는 생성한 빈 객체가 InitializingBean 인터페이스를 구현하고 있으면, InitializingBean 인터페이스로 정의되어 있는 afterPropertiesSet() 메서드를 호출한다. 즉 스프링 빈 객체가 정상적으로 동작하기 위해 객체 생성 이외의 추가적인 초기화 과정이 필요하다면, InitializingBean 인터페이스를 상속받고 afterPropertiesSet() 메서드에서 초기화 작업을 수행하면 된다. 비슷하게 스프링 컨테이너가 종료될 때, 빈 객체가 알맞은 처리가 필요하다면 DisposableBean 인터페이스를 상속받아 destory() 메서드에서 소멸 작업을 수행하면 된다.

초기화/소멸 과정이 필요한 전형적인 예는 Connection Pool 기능이다. Connection Pool은 미리 커넥션을 생성해 두었다가 커넥션이 필요할 때 제공하는 기능이므로, 초기화 과정을 필요로 한다. 또한 더 이상의 커넥션이 필요없으면 생성한 커넥션을 모두 닫기 위한 소멸 과정을 필요로 한다. 이런 Connection Pool 기능을 스프링 빈으로 사용하고 싶은 경우, InitializingBean 인터페이스와 DisposableBean 인터페이스를 상속받아 초기화/소멸 과정을 처리한다.

```java
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class ConnPool implements InitializingBean, DisposableBean {
	...
	@Override
	public void afterPropertiesSet() throws Exception{
		// 커넥션 풀 초기화 실행 : DB 커넥션을 여는 코드
	}

	@Override
	public void destory() throws Exception{
		// 커넥션 풀 종료 실행 : 연 DB 커넥션을 닫는 코드
	}
}
```
위 클래스를 스프링 빈으로 등록하면, 스프링 컨테이너는 빈을 생성한 후 afterPropertiesSet() 메서드를 호출해서 초기화를 진행하고 destory() 메서드를 호출하여 소멸을 진행한다.

```xml
<!-- 스프링 컨테이너가 afterPropertiesSet() 메서드를 실행해서 초기화를 진행하고, 컨테이너를 종료할 때 빈의 destory() 메서드를 실행하여 소멸을 진행하도록 한다. -->
<bean id="connPool" class="net.madvirus.spring4.chap03.ConnPool"></bean>
```

2개의 인터페이스를 모두 상속해야 되는 것은 아니고, 필요한 인터페이스만 받으면된다. 초기화만 필요하다면 InitialinzingBean 인터페이스만 상속받아 구현하면 된다.