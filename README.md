# Spring Boot+MyBatis+Mysql

## 添加依赖
引入  <kbd>lombok</kbd>、<kbd>mysql-connector-java</kbd> 、<kbd>mybatis-plus-boot-starter</kbd>  依赖：

```java
  <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
  </dependency>
  <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.44</version>
  </dependency>
  <dependency>
     <groupId>com.baomidou</groupId>
     <artifactId>mybatis-plus-boot-starter</artifactId>
     <version>3.3.2</version>
  </dependency>
```


## 配置
在  <kbd>application.properties </kbd> 配置文件中添加 mysql数据库的相关配置

```java
#数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

在 Spring Boot 启动类中添加 @MapperScan 注解，扫描 Mapper 目录：

```java
@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(QuickStartApplication.class, args);
    }

}
```

> 注：MapperScan后面的路径必须是自己mapper文件的目录


## 编码
1、编写实体类 User.java

```java
package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```
2、编写Mapper类 UserMapper.java

```java
package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;

public interface UserMapper extends BaseMapper<User> {

}

```
3、编写Controller类 UserController.java

```java
package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping("/")
    public Map<String, Object> index(){
        Map<String, Object> result = new HashMap<>(16);
        List<String> data = new ArrayList<>();
        result.put("errCode",0);
        result.put("errMsg","获取成功");
        List<User> list=userMapper.selectList(null);
        if(list!=null&&list.size()>0){
            for(User g:list){
                data.add(g.toString());
            }
        }else{
            result.put("errMsg","获取失败");
        }
        result.put("data",data);
        return result;
    }
}

```
## 调试
#### 1、测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
```
> 注：UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper，所以不填写就是无任何条件

控制台输出：

```java
User(id=1, name=Jone, age=18, email=test1@baomidou.com)
User(id=2, name=Jack, age=20, email=test2@baomidou.com)
User(id=3, name=Tom, age=28, email=test3@baomidou.com)
User(id=4, name=Sandy, age=21, email=test4@baomidou.com)
User(id=5, name=Billie, age=24, email=test5@baomidou.com)
```
#### 2、浏览器访问



> 注：访问地址 [http://127.0.0.1:8080/](http://127.0.0.1:8080/)

输出结果

```java
{
    "data": [
        "User{id=1, name='Jone', age=18, email='test1@baomidou.com'}",
        "User{id=2, name='Jack', age=20, email='test2@baomidou.com'}",
        "User{id=3, name='Tom', age=28, email='test3@baomidou.com'}",
        "User{id=4, name='Sandy', age=21, email='test4@baomidou.com'}",
        "User{id=5, name='Billie', age=24, email='test5@baomidou.com'}"
    ],
    "errCode": 0,
    "errMsg": "获取成功"
}
```
> 完整的代码示例请移步：[Spring Boot+MyBatis+Mysql示例](https://github.com/TianSiQiang/Spring-Boot-MyBatis-Mysql)

## 数据库
1、<kbd>User</kbd> 表，其结构如下：
|1	|Jone|18|test1@baomidou.com|
|--|--|--|--|
|2|Jack|20|test2@baomidou.com|
|3|Tom	|28|	test3@baomidou.com|
|4	|Sandy|21|	test4@baomidou.com|
|5	|Billie	|24|	test5@baomidou.com|
2、其对应的数据库脚本如下：

```sql
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);
```
3、其对应的数据库 Data 脚本如下：

```sql
DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```


## 小结
通过以上几个简单的步骤，我们就实现了 User 表的 CRUD 功能，甚至连 XML 文件都不用编写！

从以上步骤中，我们可以看到集成 <kbd>MyBatis-Plus</kbd>  非常的简单，只需要引入 starter 工程，并配置 mapper 扫描路径即可。
