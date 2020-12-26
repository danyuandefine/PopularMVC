<center><h1>Popular MVC框架</h1></center>

# 1、应用场景

## 简介
> 啥，听说你用了springboot，但是开发的接口还在裸奔？快来试试这个PopularMVC吧，它也许是你想要找的神器！

## 1.1 项目架构说明
### 1.1.1 PopularMVC跟Springboot的关系

1. 说明
    * PopularMVC是基于springboot开发的，自然强依赖于springboot和springmvc
    * PopularMVC在springboot提供的能力之上，为开发者编写API接口提供了很多额外的便利性
2. 示意图

> PopularMVC使用了springboot带来的所有便利，其在springmvc的基础上提供了额外的功能增强，如下图所示
>
> ![PopularMVC业务流程图](imgs/PopularMVC业务流程图.jpg)

### 1.1.2 PopularMVC项目组件架构解析
* 架构图

    ![PopularMVC内部组件架构图](imgs/PopularMVC内部组件架构图.jpg)

* 组件介绍

    主要分为以下几类组件

     1. API调用拦截器

        > 基于AOP切面设计的一套API请求、响应拦截处理流程。提供了在调用接口前后做一些通用的预处理，比如说参数校验、参数装饰、访问控制、日志输出、会话控制等等。目前内置的拦截器有：
        >
        > * 系统参数校验器
        > * 请求参数校验器
        > * 数字签名请求验签器、响应加签器
        > * 接口防重复提交控制器
        > * 接口用户会话控制器
        > * 接口日志打印器
        > * 接口访问控制器
        > * 请求、响应内容装饰器
        >
        > 也支持业务灵活定制通用的API调用拦截器。

     2. 参数装饰器

        > 参数装饰器机制通过遍历参数内容，逐个字段进行修饰，具体修饰规则由特定的装饰器制定。常见的使用场景有，对请求参数解密、响应参数加密、响应参数国际化、响应参数脱敏等等
        >
        > 注意：参数装饰器只能对参数内容做修饰，而无法改变参数类型。
        >
        > 目前内置的参数装饰器有：
        >
        > * 请求参数解密器
        > * 响应参数加密器
        > * 系统参数自动填充器
        > * 响应参数国际化翻译器
        >
        > 也支持业务灵活拓展参数装饰器。

     3. API响应序列化预处理器

        > 响应预处理器机制可以在接口返回内容给调用者之前进行额外的处理，此时可以更灵活的修饰返回内容，可以突破无法修改参数类型的限制，适用于对整个响应结构定制化处理的场景。
        >
        > 目前内置的响应预处理器有：
        >
        > * API响应自动包装预处理器
        > * API响应错误码和系统参数定制化转换器
        >
        > 当然，也支持业务灵活拓展。

     4. 方法查询缓存拦截器

        > 提供对查询类方法调用的结果进行缓存。

     5. API异常统一处理器

        > 接口调用过程中任何步骤出现异常，均能自动处理，并反馈信息给调用者。
        >
        > 支持对特定异常自定义处理，如果未处理，统一交由内置的默认异常处理器进行护理。

     6. 统一国际化翻译管理器

        > 接口返回内容均支持国际化翻译能力。
        >
        > 已整合了spring i18n国际化翻译能力，也支持拓展业务自定义国际化翻译器。

     7. 统一错误码管理器

        > 研发人员无需再关注业务错误码的定义和国际化翻译工作，可以做到定义简单、使用简单、易于理解、配置灵活
        >
        > * 支持自定义系统错误码、业务错误码
        > * 支持业务错误码自动生成
        > * 支持业务错误码以异常的方式抛出，系统自动处理

     8. 自动接口文档生成器

        > 已内置swagger+knif4j，只需指定API包路径即可实现接口文档的自动生成。

## 1.2 其他说明
* 如果你有遇到如下困扰时，你也许可以尝试使用PopularMVC框架 
  >
  >1. 服务器API缺乏统一标准的输入输出参数，难以统一规范开发人员的接口格式
  >
  >2. 缺乏简单、高效、统一的接口异常处理机制
  >
  >3. 绞尽脑汁思考错误码的命名、编号和处理机制
  >
  >4. 虽然使用参数校验注解校验API请求参数已经非常方便，但是又对其缺乏校验结果的统一处理而苦恼
  >
  >5. 项目服务于多个国家的用户，繁重的国际化工作也许会让你崩溃
  >
  >6. API缺乏权限控制、安全认证、数据加密、防重复提交等等，直接裸奔
  >
  >7. 还没有找到顺手的服务器缓存工具，对springcache的注解槽点满满等
  >
  >8. 时常为了写接口文档而占用太多coding time，而且接口文档还无法实时与接口变更保持同步

  > 现在你有福了，你只需要引入PopularMVC框架，并为你的SpringBoot应用添加`@EnablePopularMvc`注解，再添加少量配置信息，即可解决上述所有的困扰！

* PopularMVC为你默默做了哪些工作呢？

    * 首先，PopularMVC是基于springboot的web应用框架，享受了springboot带来的一切便利性
    * 再者，其对开发WEB JSON API 的规范性、易用性、健壮性做了很多的业务增强

* PopularMVC的存在意义在哪？

    > 一个字，那就是"爽"，让开发者爽，让接口使用者爽！

    * 规范web应用的API输入输出
    * 简化开发人员的工作，节省研发成本，给开发者无微不至的关爱！

* PopularMVC提供的业务功能有哪些呢？

    > 那么请听我慢慢道来，其提供了如下业务功能：
    >
    > 1. 规范API请求参数，支持系统参数名自定义和系统参数的拓展，系统参数的校验等等
    > 2. 规范API响应参数，支持系统参数名自定义和添加额外的系统参数
    > 3. API响应信息自动补全系统参数，开发者无需再手动添加响应壳信息
    > 4. 应用内统一的国际化翻译能力，已经整合spring i18n，而且支持业务灵活拓展国际化能力
    > 5. 接口响应信息国际化，为需要国际化的字段添加`@LanguageTranslate`注解即可自动翻译内容后返回给调用者
    > 6. 统一的异常处理机制，全局拦截API调用异常并处理，处理结果经国际化处理后以标准的API响应格式返回给调用者，业务侧可以对特定异常进行灵活处理
    > 7. 简单易用的接口错误码，发现不满足API执行条件，直接抛出`BusinessException`异常即可通过统一异常处理机制得到预期的API响应。支持自定义系统错误码、业务错误码；依据业务错误码KEY值自动生成错误码数值；并可以对服务内的自动生成的业务错误码值进行范围限制，这样有助于不同业务服务间的错误码分段；
    > 8. API请求参数、响应参数的自动校验，校验结果自动处理后通过API响应反馈给调用者
    > 9. 简单方便的缓存管理器，使用Guava cache作为默认的本地缓存管理器，支持缓存管理器的灵活拓展，提供`@CacheMethodResult`和`@CacheMethodResultEvict`注解实现方法接口结果的缓存和失效
    > 10. 接口安全相关组件，验签、加解密、防重复提交等等，使用时只需要为你的API添加一个注解即可搞定
    > 11. 接口会话管理机制，提供Guava cache实现的默认本地会话管理器，支持业务灵活定制以支持分布式会话管理
    > 12. 接口文档化支持，提供了swagger接口文档自动生成，并整合了knife4j提供简单易用的实时api文档
    > 13. 接口调用日志打印，提供对接口请求参数信息、接口响应信息的简要打印，预留了接口调用日志持久化机制，用于业务方定制流量监控相关功能
    > 14. web容器性能参数优化（迭代加）
    > 15. 接口实时流量监控和管理（迭代加）
    > 16. 更细粒度的接口访问权限控制（迭代加），主要为应用提供简洁易用的内置权限管理组件
    > 17. 日志采集功能的集成支持（迭代加）
    > 18. devops的支持（迭代加）

# 2、项目模块

| 模块                 | 说明                                    | 地址                                                         |
| -------------------- | --------------------------------------- | ------------------------------------------------------------ |
| popular-web-mvc      | 为springboot应用提供API业务增强解决方案 | https://mvnrepository.com/artifact/com.danyuanblog.framework/popular-web-mvc |
| popular-web-mvc-demo | popularMVC框架使用示例demo              | https://gitee.com/danyuanblog/PopularMVC/tree/master/popular-web-mvc-demo |

# 3、入门demo

## 3.1 项目结构

* 项目结构

    ```ruby
    ├─src
    │  └─main
    │     ├─java
    │     │  └─com
    │     │      └─danyuanblog
    │     │          └─framework
    │     │              └─demo
    │     │                  └─popularmvc
    │     │                      ├─controller
    │     │                      │  │  TestLanguageTranslateController.java
    │     │                      │  │      
    │     │                      │  └─ TestParamsValidateController.java
    │     │                      │          
    │     │                      └─startup
    │     │                              Startup.java #项目启动类
    │     │                              
    │     └─resources
    │         │  application.yml  #项目配置信息
    │         │  
    │         └─i18n #如果需要自定义国际化支持，可以添加如下配置
    │             └─messages #项目自定义国际化翻译信息
    │                     messages.properties
    │                     messages_en_US.properties
    │                     messages_zh_CN.properties
    └─  pom.xml
    ```

* 引入模块依赖，在`pom.xml`添加

```xml
	<dependency>
		<groupId>com.danyuanblog.framework</groupId>
		<artifactId>popular-web-mvc</artifactId>
		<version>${popular-web-mvc.version}</version>
	</dependency>
```

## 3.2 启用PopularMvc框架

```java
package com.danyuanblog.framework.demo.popularmvc.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.danyuanblog.framework.popularmvc.annotation.EnablePopularMvc;

@SpringBootApplication
//启用PopularMvc框架
@EnablePopularMvc
@ComponentScan("com.danyuanblog.framework.demo.popularmvc")
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}
}
```

## 3.3 添加配置信息

* `application.yml`

```yaml
spring.messages:
  #业务错误码翻译文件地址
  basename: i18n/messages/messages
  
popularmvc:
  #业务系统根包名集合,以逗号分隔
  basePackages: "com.danyuanblog.framework.demo.popularmvc"
  api:
    #业务接口api包名
    swagger: 
      #业务接口API所在根包名，swagger会扫描这个包下的内容，生成接口文档
      basePackage: "com.danyuanblog.framework.demo.popularmvc.controller"     
```

* 业务定制国际化翻译配置

    * `messages_zh_CN.properties`

        ```properties
        #业务错误码翻译内容
        USER_ACCOUNT_NOT_FOUND=对不起，你输入的账号[{0}]没有找到,请检查下你的账号哦！
        ACCOUNT_NOT_FOUND=对不起，你的银行账户[{0}]输入错误，请核对！
        #替换系统错误码默认翻译
        system.invalidParam=这是无效参数{0}[{1}]
        #业务信息翻译
        danyuan.info=宇宙无敌帅小伙！
        danyuan.tags=阳光，积极向上，热爱生活，拥抱变化，懂得容忍
        ```

    * `messages_en_US.properties`

        ```properties
        #业务错误码翻译内容U
        SER_ACCOUNT_NOT_FOUND=Sorry , The user account[{0}] not found, please check your input.
        ACCOUNT_NOT_FOUND=Sorry , The bank account[{0}] is invalid, please check your input.
        #业务信息翻译
        danyuan.info=The universe is invincible, handsome boy!
        danyuan.tags=Sunshine, positive, love life, embrace change, understand tolerance
        ```

## 3.4 示例接口

* 请求参数校验示例

    ```java
    //接口示例
    	@PostMapping(value="testRequestParamCheck",
    			name="测试校验请求参数")
    	@ApiOperation(value="测试校验请求参数", notes="测试校验请求参数")
    	public void testRequestParamCheck(@RequestBody ParamsCheckDto req, @RequestParam @Email String adminEmail){
    		
    	}
    
    //请求参数校验DTO
    @Data
    @ApiModel
    public class ParamsCheckDto implements Serializable{/** 
    	 *serialVersionUID
    	 */
    	private static final long serialVersionUID = 1L;
    	
    	@NotBlank
    	@Size(max=10, min=10, message = "账号长度只能为10!")
    	@ApiModelProperty(value = "用户账号", required = true, example = "1321122321")
    	private String account;
    	
    	@Email
    	@ApiModelProperty(value = "用户邮箱号")
    	private String email;
    	
    	@NotNull
    	@Range(max=200, min=1, message = "年龄只能在1-200岁之间!")
    	@ApiModelProperty(value = "用户年龄", required = true)
    	private Integer age;
    	
    	@Size(max=3, min=0, message = "最多只能选择3个爱好!")
    	@ApiModelProperty(value = "用户爱好")
    	private List<String> likes;//爱好
    }
    
    ```

    

* 国际化响应信息示例

    * `TestLanguageTranslateController.java`

    ```java
    package com.danyuanblog.framework.demo.popularmvc.controller;
    
    import io.swagger.annotations.Api;
    import io.swagger.annotations.ApiOperation;
    
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;
    
    import com.danyuanblog.framework.demo.popularmvc.controller.dto.LanguageInfoDto;
    
    @Api(tags = "测试响应结构自动包装和业务异常的使用")
    @RestController
    public class TestLanguageTranslateController {
    	
    	@GetMapping(value="testGetLanguageInfo",
    			name="测试信息国际化自动翻译功能")
    	@ApiOperation(value="测试信息国际化自动翻译功能", notes="测试信息国际化自动翻译功能")
    	public LanguageInfoDto testGetLanguageInfo(@RequestParam("info") String info){
    		return new LanguageInfoDto(info,"danyuan",22);
    	}
    
    }
    
    ```

    * `LanguageInfoDto.java`

    ```java
    package com.danyuanblog.framework.demo.popularmvc.controller.dto;
    
    import java.io.Serializable;
    
    import com.danyuanblog.framework.popularmvc.annotation.LanguageTranslate;
    
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class LanguageInfoDto implements Serializable{
    
    	/** 
    	 *serialVersionUID
    	 */
    	private static final long serialVersionUID = 1L;
        
    	@LanguageTranslate
    	private String info;
    	
    	private String name;
    	
    	private int age;
    }
    
    ```

    

## 3.5 接口文档示例

* 接口文档访问地址：http://localhost:8080/doc.html

* 系统内置接口

    ![系统内置接口](imgs/系统内置接口.png)

* 业务系统接口

    ![业务系统接口](imgs/业务系统接口.png)

* 接口文档示例

    ![](imgs/接口文档示例.png)

* 接口测试示例

    * 接口参数校验失败测试结果示例

        ![接口参数校验失败测试结果](imgs/接口参数校验异常测试结果.png)

    * 接口访问成功结果示例

        ![接口正常响应测试结果](imgs/接口正常响应测试结果.png)

    * 国际化接口访问示例（英文）

        ![](imgs/国际化请求参数_en_US.png)

        ![](imgs/国际化翻译响应信息_en_US.png)

    * 国际化接口访问示例（中文）

        ![](imgs/国际化请求参数_zh_CN.png)

        ![](imgs/国际化翻译响应信息_zh_CN.png)

    

# 4、更多特性介绍与使用示例

## 4.1 参数校验示例



## 4.2 自动填充API响应参数示例



## 4.3 业务错误码使用示例



## 4.4 接口响应国际化翻译示例



## 4.5 使用数字签名示例



## 4.6 接口数据加解密示例



## 4.7 接口防重复提交功能示例



## 4.8 接口查询缓存使用示例



## 4.9 接口用户会话功能使用示例



## 4.10 静态资源使用示例



# 5、业务定制化

## 5.1 配置定制化

* 参数自定义
    * 系统公共请求参数名自定义
    * 添加额外的系统公共请求参数
    * 系统公共响应参数名自定义
    * 添加额外的系统响应参数

* 业务国际化内容

    > 可以通过springboot i18n添加或者定制国际化内容

## 5.2 组件定制化

> PopularMVC提供了灵活定制通用组件的能力。
>
> 1. 内置的几大管理器都可以替换成自定义的管理器，如：
>     * 缓存管理器
>     * 会话管理器
>     * 国际化管理器
>     * 数字签名管理器
>     * 秘钥管理器
>     * 应用管理器
>     * 接口日志管理器
>     * 接口调用频次控制管理器
> 2. 可以添加自定义接口调用拦截器，实现一些通用业务
> 3. 可以添加自定义接口请求、响应参数装饰器，对参数进行加工
> 4. 可以添加自定义响应序列化预处理器，根据业务需要定制化返回信息
> 5. 可以添加自定义异常处理器，
> 6. 可以灵活定制和使用自定义的加解密处理器

# 6、交流与答疑

| 渠道        | 联系地址                                                | 说明                                              |
| ----------- | ------------------------------------------------------- | ------------------------------------------------- |
| QQ群        | 757901536                                               | 可加QQ群，进行技术交流                            |
| 邮箱号      | admin@danyuanblog.com                                   | 有问题也可以发邮件留言                            |
| 留言板      | http://www.danyuanblog.com/blog/app/words/msgBoard.html | 通过留言板留言                                    |
| gitee Issue | https://gitee.com/danyuanblog/PopularMVC/issues         | 在使用过程中如果遇到问题，可以提Issue，不定时回复 |




