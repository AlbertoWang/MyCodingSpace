# SpringCloud 历险记
## Feign 与 Springboot 的恩怨情仇
### 问题描述
今天在微服务中进行文件上传时，涉及到了 ```MultiPartFile``` 这个类型，它作为前端传到后台的文件数据类型，被用在了 Controller 的参数中，像下面这个亚子：
``` java
@RequestMapping(value = "/uploadFileURL")
public boolean uploadFile(@RequestParam(value = "file") MultiPartFile file){
  // 这是个其他微服务提供的保存文件的接口
  interfaceProvidedByOtherMicroService.saveFile(file);
}
```
上面提到的那个 ```interfaceProvidedByOtherMicroService``` 接口像下面这样使用 Feign 来发现：
``` java
@FeignClient(name = "service-name-in-discovery-server")
public interface InterfaceProvidedByOtherMicroService{
  @RequestMapping(value = "/requestURLInOtherService")
  public boolean saveFile(@RequestParam(value = "file") MultiPartFile file);
}
```
一切看起来如此美妙，在 ```InterfaceProvidedByOtherMicroService``` 的实际服务生产者上测试也毫无问题，但是怪就怪在调用这个微服务一直报错
### 来看看咋回事儿
Feign 作为一个服务发现的框架，它对 Jackson 的序列化编码方式似乎和 Springboot 格格不入，不同的序列化方式导致了文件传输的问题

作为被抛弃的倒霉孩子，Feign 在 SpringCloud 中的作用大概就是和 OpenFeign 共享同一套注解？所以抛弃 Feign，拥抱 OpenFeign，毫不犹豫地在 ```pom.xml``` 里添加依赖吧：
``` xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
### 来解决吧
1. 文件传输其实是不能用 ```@RequestParam``` 注解的（至于为什么服务生产者自身测试没问题就不得而知了），需要使用 ```@RequestPart``` 这个专门处理文件的注解来映射参数
2. 在  ```@RequestMapping```  注解中，仅仅使用 URL 映射是不够的，还需要添加请求相关的**输入**与**输出**，即 ```produces``` 与 ```consumes```
3. 来看看最终代码：
  * OpenFeign 发现的服务接口定义：
    ``` java
    @FeignClient(name = "service-name-in-discovery-server")
    public interface InterfaceProvidedByOtherMicroService{
      @RequestMapping(value = "/requestURLInOtherService", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
      public boolean saveFile(@RequestPart(value = "file") MultiPartFile file);
    }
    ```
  * Controller 的地址映射：
    ``` java
    @RequestMapping(value = "/uploadFileURL", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean uploadFile(@RequestPart(value = "file") MultiPartFile file){
      // 这是个其他微服务提供的保存文件的接口
      interfaceProvidedByOtherMicroService.saveFile(file);
    }
    ```
### 总结一下
Springboot 给出的很多注解其实还是了解一下的好，不要嫌麻烦，request 该是什么输入输出就声明一下，像使用 PostMan 那样使用相关注解的参数配置。~~或许这也是个去看那些相关参数设置官方文档的契机也说不定呢~~
