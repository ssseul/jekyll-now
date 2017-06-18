---
title: spring programming chapter 4
layout: post
---
Spring Programming의 Chapter 4 에서 중요한 부분 정리 

# Chapter 4

## Enviornment, Properties, Profile, Message

스프링에서는 로컬 개발 환경과 실제 서비스 환경에 따른 DB연결, 디렉토리 구조 등이 바뀌는 경우, **환경에 따라 다른 값**을 사용할 수 있는 방법을 제공한다.

### 1.Enviornment

스프링에서는 설정 변경 없이 외부에서 입력한 정보를 이용해 설정 값을 변경하는 방법을 제공하며 그 중 한 가지가 Enviornment이다.

**시스템 환경변수**, **JVM 시스템 프로퍼티**, **프로퍼티 파일** 등의 프로퍼티를 PropertyService라는 것으로 통합관리 하며, 설정 파일이나 클래스 수정 없이 시스템 프로퍼티나 프로퍼티 파일 등을 이용해서 설정 정보의 일부를 변경할 수 있다.

Enviornment 는 두 가지 기능을 제공한다.

* 프로퍼티 통합 관리
* Profile을 이용해서 선택적으로 설정 정보를 사용할 수 있는 방법을 제공

또한 여러 Profile 중에서 특정 프로필을 활성화 하는 기능도 제공하는데 이 기능을 사용하면 개발 환경, 통합 테스트 환경, 운영 서비스 환경에 따른 스프링 빈 설정을 선택할 수 있기 때문에, 서로 다른 환경을 위한 설정 정보를 편리하게 관리할 수 있다.

#### Enviornmet 구하기 

```java
ConfigurationApplicationContext context = new AnnotationConfigApplicationContext();
ConfigurationEnviornment enviorment = context.getEnviornemt();
enviornment.setActiveProfiles("dev");
```

새로운 PropertySource를 직접 추가할 때 ConfigurationApplicationContext에 정의된 getEnviornment() 메서드를 이용하여 Enviornment를 구한다.

### 2. Enviornment와 PropertySource

Enviornment의 첫번째 기능은 **프로퍼티 값을 제공**하는 기능이다. 스프링 컨테이너가 기본으로 사용하는 Enviornment 구현체는 다수의 PropertySource로 부터 프로퍼티 값을 읽어온다.

![spring_5](/archive/spring_5.PNG "spring_5")

여러 개의 PropertySource가 등록되어 있을 경우, 프로퍼티 값을 구할 때 까지 등록된 순서에 따라 차례대로 확인한다. 스프링은 시스템 프로퍼티와 환경 변수를 사용하는 두 개의 PropertySource를 기본적으로 사용하며, 이 중 우선순위는 시스템 프로퍼티를 사용하는 PropertySource가 높다.

** Enviornment의 설정을 변경하지 않는 이상, 시스템 프로퍼티로부터 먼저 값을 찾고 그 다음에 환경 변수로부터 값을 찾는다.**

#### Enviornmet에서 프로퍼티 읽기

Enviornment를 구한 뒤에 Enviornment에서 제공하는 프로퍼티 관련 메서드를 이용한다.

```java
ConfigurationApplicationContext context = new GenericXmlApplicationContext();
ConfigurationEnviornment env = context.getEnviornment();
String javaVersion = env.getProperty("java.version");
```

##### Enviornment에서 제공하는 프로퍼티 주요 관련 메서드

* boolean containsProperty(String key)
	지정한 key에 해당하는 프로퍼티가 존재하는지 확인한다.
* String getProperty(String key)
	지정한 key에 해당하는 프로퍼티 값을 구한다. 존재하지 않으면 null을 리턴한다.	
* String getProperty(String key, String defaltValue)
	지정한 key에 해당하는 프로퍼티 값을 구한다. 존재하지 않으면 defaultValue를 리턴한다.	
* String getRequiredProperty(String key) throws IllegalStateExceptioin
	지정한 key에 해당하는 프로퍼티 값을 구한다. 존재하지 않으면 익셉션을 발생시킨다.
* <T> T getProperty(String key, Class<T> targetType)
	지정한 key에 해당하는 프로퍼티의 값을 targetType으로 변환해서 구한다. 존재하지 않을 경우 null을 리턴한다.
* <T> T getProperty(String key, Class<T> targetType, T defaultValue)
	지정한 key에 해당하는 프로퍼티의 값을 targetType으로 변환해서 구한다. 존재하지 않을 경우 defaultValue을 리턴한다.
* <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException
	지정한 key에 해당하는 프로퍼티의 값을 targetType으로 변환해서 구한다. 존재하지 않을 경우 익셉션을 발생시킨다.

#### Enviornmet에서 새 PropertySource 추가

스프링은 기본적으로 환경 변수와 시스템 프로퍼티만 Enviornment의 프로퍼티로 사용하기 때문에 프로퍼티 파일을 Enviornment의 프로퍼티로 추가하고 싶다면 PropertySource를 추가해야 한다.

```java
ConfigurableEnviornment env = context.getEnviornment();
MutablePropertySources propertySources = env.getPropertySource();
// db.properties 추가
propertySources.addLast(new ResourcePropertySource("classpath:/db.properties"));
System dbUser = env.getProperties("db.user"); // DB ㅏ사용자 리턴
```
MutablePropertySources의 addLast() 메서드를 사용하면 파라미터로 전달한 PropertySource를 마지막 Property로 등록하기 때문에 프로퍼티 탐색 과정에서 **우선순위가 제일 낮다.** 반대로 addFirst() 메서드를 사용하면 **우선순위가 제일 높아 진다.**

##### @Configruation 어노테이션 자바 설정 시 프로퍼티 파일 추가

```java
@Configuration
@PropertySource("classpath:/db.properties")
public class ConfigByEnv{

	@Autowired
	private Enviornment env;
}
```
* 두 개 이상의 프로퍼티 파일 지정 시, @PropertySource 값을 배열로 지정, 자원이 없을 경우 익셉션을 발생하지 않고 무시하고 싶을 때는 ignoreResourceNotFound 속성을 true 로 지정한다(기본값을 false)

```java
@Configuration
@PropertySource{"classpath:/db.properties", "classpath:/app.properties", ignoreResourceNotFound = true
}
public class ConfigByEnv{
	...
}
```
* @PropertySource 자체를 두 개 이상 설정할 경우에는 @PropertySources 어노테이션을 사용한다.

```java
@Configuration
@PropertySources({
	@PropertySource("classpath:/db.properties"),
	@PropertySource("classpath:/app.properties", ignoreResourceNotFound = true)	
})
public class ConfigByEnv{
	...
}
```

** @PropertySource 어노테이션은 자바 8의 @Repeatable을 적용하고 있으므로 자바 8 사용 시 @PropertySource 어노테이션을 어러 개 지정이 가능하다.

### 3. 스프링 빈에서 Enviornment 사용

스프링 빈은 Envoirnment에 직접 접근해서 Enviornment가 제공하는 프로퍼티를 사용할 수 있으며, 2가지 방법을 제공한다

	* o.s.context.EnviornmentAware 인터페이스를 구현
	* @Autorwird 어노테이션을 Enviornment 필드에 적용

### 4. 프로퍼티 파일을 이용한 프로퍼티 설정

프로퍼티 파일 내용을 XMl과 자바 설정(Enviornment)없이 직접 사용하는 방법

#### XML에서 프로퍼티 설정 : ```<context:property-placeholder>``` 사용

```<context:property-placeholder>``` 태그는 location 속성으로 지정한 프로퍼티 파일로부터 정보를 읽어와 빈 설정에 입력한 플레이스홀더(${프로퍼티명})의 값을 프로퍼티 파일에 존재하는 값으로 변경한다. 플레이스홀더는 ${} 사이에 사용할 프로퍼티 이름을 지정하며, ```<context:property-placeholder>```는 플레이스홀더를 동일한 이름을 값는 프로퍼티의 값으로 치환하므로 XML 설정을 사용한 것과 같은 결과를 생성한다.

##### ```<context:property-placeholder>``` 태그에서 제공하는 주요 속성

* file-encoding
	파일을 읽어올 때 사용할 인코디을 지정한다. 이 값이 없으면, 자바 프로퍼티 파일 인코딩을 따른다(JDK에서 제공하는 native2ascii 도구를 이용해서 생성 가능한 인코딩)
* ignore-resource-not-found
	이 속성의 값이 true이면, location 속성에 지정한 자원이 존재하지 않아도 익셉션을 발생시키지 않는다. false 시 자원이 존재하지 않으면 익셉션을 발생시킨다(기본 false)
* ignore-unresolvable
	이 값이 true면, 플레이스홀더에 일치하는 프로퍼티가 없어도 익셉션을 발생시키지 않는다. false면 플레이스홀더와 일치하는 프로퍼티가 없을 경우 익셉션을 발생시킨다(기본 false)

```<context:property-placeholder>``` 태그는 내부적으로 PropertySourcesPlaceholderConfigurer를 빈으로 등록하며, PropertySourcesPlaceholderConfigurer는 location으로 지정한 파일에서 프로퍼티 값을 찾을 수 없는 경우 Enviornment의 프로퍼티를 확인하며, Enviornment가 해당 프로퍼티를 갖고 있으면 그 값을 사용한다.

주의점은 전체 설정에서 이 태그를 두 번 이상 사용할 경우, 첫번째로 사용한 태그의 값이 우선순위를 갖게 된다.

#### Configuration 어노테이션을 이용하는 자바 설정에서의 프로퍼티 사용

@Configuration을 이용한 자바설정에서 프로퍼티 파일을 사용하고 싶을 때는 PropertySourcesPlaceholderConfigurer와 @Value 어노테이션을 함께 사용한다.

주의점은 PropertySourcesPlaceholderConfigurer 타입의 빈을 설정하는 메서드는 **static 메서드** 이므로, 특수한 목적의 빈이기 때문에 정적 메서드로 지정하지 않으면 원하는 방식으로 동작이 되지 않는다.

##### PropertySourcesPlaceholderConfigurer 클래스 사용시 메서드 속성

* setLocation(Resource location)
	location을 프로퍼티 파일로 사용한다.
* setLocations(Resources locatiions) 
	locations를 프로퍼티 파일로 사용한다.
* setFileEncoding(String encoding)
	프로퍼티 파일을 읽어올 때 사용할 인코딩을 지정하며, 지정하지 않을 경우 자바 프로퍼티 파일 인코딩을 따른다.
* setIgnoreResourceNotFound(boolean b)
	true를 전달하면, 자원을 발견할 수 없어도 익셉션을 발생하지 않고 무시한다.
* setIgnoreUnresolvablePlaceholders(boolean b)
	true를 전달하면, 플레이스홀더에 해당하는 프로퍼티를 발견할 수 없어도 익셉션을 발생하지 않고 무시한다.

### 5. 프로필을 이용한 프로퍼티 설정	

보통 로컬 개발 환경, 통합 테스트 환경, 실제 운영 환경은 DB IP 정보, 디렉토리 경로, 외부 서비스 URL 등이 동일하지 않다. 

각 환경에 맞는 설정 정보를 따로 만들어 환경에 따라 알맞은 설정 정보를 사용하기 위해 스프링의 프로필을 이용한다. 환경에 따 따라 설정 정보를 만들어 각각 별도의 프로필 명을 부여하고 환경에 알맞은 프로필을 선택하면 환경에 따른 설정을 사용할 수 있다.

#### XML 설정에서 프로필 사용

```<beans>``` 태그의 profile 속성을 이용해서 프로필 명을 지정한다.

```xml
-- dataSource-dev.xml 파일
<beans xmlns="http://springframework.org/schema/beans"
	xmlns:xsi="http://wwww.w3.org/2001/XMLSchema-instance"
	..... profile="dev">
...
</beans>

--- dataSource-prod.xml 파일
<beans xmlns="http://springframework.org/schema/beans"
	xmlns:xsi="http://wwww.w3.org/2001/XMLSchema-instance"
	..... profile="prod">
...
</beans>
```

각 설정파일이 같은 이름의 빈 객체를 설정하고 있고 ```<beans>```태그의 profile 속성을 "dev"와 "prod"를 값으로 가지고 있다.
특정 프로필을 선택하라면 **ConfigurableEnviornment에 정의되어 있는 setActiveProfiles() 메서드를 이용한다.**

```java
GenericXmlApplicationContext context = new GenericXmlApplicationContext();
context.getEnviornment().setActiveProfiles("dev");
...
context.refresh();
```

#### 자바 @Configuration 설정에서 프로필 사용

@Profile 어노테이션을 이용한다.

```java
@Configuration
@Profile("prod")
public class DataSourceProdConfig{
	
	@Bean
	public JndiConnectorProvider connProvider(){
		JndiConnectorProvider connectionProvider = new.JndiConnectionProvider();
		connectionProvider.setJndiName("java:/comp/env/jdbc/db")
		return connectionProvider;
	}
}
```

활성화하는 방법은 setActiveProfiles() 메서드를 이용하거나 spring.profiles.active 시스템 프로퍼티에 값을 설정해준다.

#### 다수 프로필 사용

스프링 설정은 두 개 이상의 프로필 이름을 가질 수 있다.

```xml
-- dataSource-dev.xml 파일
<beans xmlns="http://springframework.org/schema/beans"
	xmlns:xsi="http://wwww.w3.org/2001/XMLSchema-instance"
	..... profile="prod,QA">
...
</beans>
```

```java
@Configuration
@Profile("prod,QA")
public class DataSourceProdConfig{
	...
}
```
### 6. MessageSource를 이용한 메시지 국제화 처리	

웹 어플리케이션은 화면에 다양한 텍스트 메시지를 출력하는데, 개발해야 할 웹 어플리케이션이 다국어를 지원할 경우 각 언어에 맞는 메시지가 화면에 출력되어야 한다.

스프링은 **메시지의 국제화**를 지원하기 위해 o.s.context.MessageSource 인터페이스를 제공하며, 지역 및 언어에 따라 알맞은 메시지를 구할 수 있는 메서드가 정의되어 있다.

```java
public interface MessageSource{
	
	String getMessage(String code, Object[] args, String defaultMessage, Locale locale);

	String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException;

	String getMessage(MessageSourceResovable resolvable, Locale locale) throws NoSuchMessageException;
}
```

각 getMessage() 메서드는 Locale에 따라 알맞은 메시지를 리턴해준다. ApplicationContext는 등록된 빈 객체 중에서 이름이 **MessageSource** 타입의 빈 객체를 이용하여 메시지를 가져오기 때문에 스프링 설정 파일에 이름이 'messageSource' 인 빈 객체를 정의해 주어야한다.

```xml
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
	<property name="basename">
		<value>message.greeting</value>
	</property>
</bean>
```

#### 프로퍼티 파일과 MessageSource

MessageSource 인터페이스에서 주로 사용하는 메서드는 두 가지 이다.

* String getMessage(String code, Object[] args, String defaultMessage, Locale locale)

* String getMessage(String code, Object[] args, Locale locale)

code는 메시지를 식별하기 위한 코드로서, 메시지로 사용될 프로퍼티 파일의 프로퍼티 명과 연결된다.
