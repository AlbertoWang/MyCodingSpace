# springboot-helloworld
springboot初体验
# 1.前期环境准备
## 1.JDK（必备）
此处以JDK8为例，其他版本未经测试，不确定可行性  
（吐槽一嘴以前JDK9和10在SSM上出的问题是真滴恶心心）
![](http://m.qpic.cn/psb?/V10eJRap0NzfSJ/jokoMeqqbnnaw2ZEh7oXEM8i2*JImD04PCIJ.EWGn8s!/b/dEEBAAAAAAAA&bo=jgWyAI4FsgARCT4!&rf=viewer_4&t=5 "java -version")
## 2.Maven（选配，大多数IDE自带）
此处以mvn3.6.1为例，其他版本未经测试，不确定可行性
![](http://m.qpic.cn/psb?/V10eJRap0NzfSJ/0Zyn*gUwYnY*biZH8vfCVPQ0i2a9M4QNVxgKpsSCnDA!/b/dL4AAAAAAAAA&bo=7gZQAe4GUAEDKQw!&rf=viewer_4&t=5 "mvn -v")
# 2.项目创建(以Eclipse为例)
## 项目初始化有两种方式：
## 1.直接从[Spring官网生成器](https://start.spring.io/)下载demo，在其基础上进行更改
![](http://m.qpic.cn/psb?/V10eJRap0NzfSJ/tPNwQ2Gw9u*ZNArNXJjxddnrJJlI0GYRzMtvof8WSWw!/b/dFIBAAAAAAAA&bo=EQU4BAAAAAARBxg!&rf=viewer_4&t=5 "springboot_init")
Generate项目后，解压压缩包，在Eclipse中导入项目
## 2.手动搭建新项目
### 1.新建Maven项目
![](http://m.qpic.cn/psb?/V10eJRap0NzfSJ/5ZVvn4QcbwRxog6ulLgg1rz2yI5MDasxCcYGBJKWCT4!/b/dLgAAAAAAAAA&bo=YgX6AwAAAAADB7w!&rf=viewer_4&t=5 "file→new→maven project")
![](http://m.qpic.cn/psb?/V10eJRap0NzfSJ/xfILq0jTJHkpnoQSj*9SxbN4GeZchMpDIXapqg23YV8!/b/dL8AAAAAAAAA&bo=zAQsBMwELAQDGTw!&rf=viewer_4&t=5 "next")
![](http://m.qpic.cn/psb?/V10eJRap0NzfSJ/kO27H6T1K.xT0*cQnjXUfqjtvf*Ji1b5Oa*YAG.MgtE!/b/dL4AAAAAAAAA&bo=zAQsBMwELAQDKQw!&rf=viewer_4 "finish")
### 2.添加Maven依赖
将以下代码复制进pom.xml中  
```
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.1.7.RELEASE</version>
	<relativePath />
</parent>
<properties>
	<java.version>1.8</java.version>
</properties>
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
		<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
</dependencies>
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
	</plugins>
</build>
```
至此环境搭建工作完成，下面进行业务代码编写即可
