<center><h1>Popular MVC框架</h1></center>

# 1、应用场景

## 1.1 简介
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
  >5. 如果项目的用户群面临多个国家的用户时，繁重的国际化工作也许会让你崩溃
  >
  >6. API缺乏权限控制、安全认证、数据加密、防重复提交等等，直接裸奔
  >
  >7. 还没有找到顺手的服务器缓存工具，对springcache的注解槽点满满等
  >
  >8. 时常为了写接口文档而占用太多coding time，而且接口文档还无法实时与接口变更保持同步

  > 现在你有福了，你只需要引入PopularMVC框架，并添加为你的SpringBoot应用添加`@EnablePopularMvc`注解，再添加少量配置信息，即可解决上述所有的困扰！

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
    > 1. 规范API请求的系统参数，支持系统参数名自定义和系统参数的拓展，系统参数的校验等等
    > 2. 规范API响应的系统参数，支持系统参数名自定义和添加额外的系统参数
    > 3. API响应信息自动包装系统参数，开发者无需再手动添加响应壳信息
    > 4. 统一的国际化翻译能力，已经整合spring i18n，而且支持业务灵活拓展国际化能力
    > 5. 接口响应信息国际化，为需要国际化的字段添加`@LanguageTranslate`注解即可自动翻译内容后返回给调用者
    > 6. 统一的异常处理机制，全局拦截API调用异常并处理，处理结果经国际化处理后以标准的API响应格式返回给调用者，业务侧可以对特定异常进行灵活处理
    > 7. 简单易用的接口错误码，发现不满足API执行条件，直接抛出`BusinessException`异常即可通过统一异常处理机制得到预期的API响应。支持自定义系统错误码、业务错误码；依据业务错误码KEY值自动生成错误码数值；并可以对服务内的自动生成的业务错误码值进行范围限制，这样有助于不同业务服务间的错误码分段；
    > 8. API请求参数、响应参数的自动校验，校验结果自动处理后通过API响应反馈给调用者
    > 9. 统一的缓存管理器，使用Guava cache作为默认的本地缓存管理器，支持缓存管理器的灵活拓展，提供`@CacheMethodResult`和`@CacheMethodResultEvict`注解实现方法接口结果的缓存和失效
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

| 模块                 | 说明                                    |
| -------------------- | --------------------------------------- |
| popular-web-mvc      | 为springboot应用提供API业务增强解决方案 |
| popular-web-mvc-demo | popularMVC框架使用示例demo              |

# 3、入门demo

## 3.1 引入模块依赖

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
      title: "业务API"
      description: "所有业务接口列表"
      termsOfServiceUrl: "www.danyuanblog.com"
      contact: "admin@danyuanblog.com"
      version: "v1.0.0"
  channels: #渠道应用权限和秘钥配置
      default: #默认渠道
        default: #默认应用信息
          enable: true
          #用于默认的对称加解密、生成数字签名、验证数字签名的秘钥
          secret: "123456"
          keyPair: #用于非对称加解密的秘钥对
            privateSecret: ""
            publicSecret: ""      
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

        ![接口参数校验失败测试结果](imgs/接口参数校验测试结果.png)

    * 接口访问成功结果示例

        ![接口参数校验失败测试结果](imgs/接口参数校验测试结果.png)

    * 国际化接口访问示例（英文）

        ![](imgs/国际化请求参数_en_US.png)

        ![](imgs/国际化翻译响应信息_en_US.png)

    * 国际化接口访问示例（中文）

        ![](imgs/国际化请求参数_zh_CN.png)

        ![](imgs/国际化翻译响应信息_zh_CN.png)

    

# 4、更多特性介绍与使用示例

# 5、业务定制化

# 6、交流与答疑




