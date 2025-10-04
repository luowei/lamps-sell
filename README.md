# Lamps-Sell 灯饰销售门户系统

一个功能完善的B2C电商门户网站，类似淘宝商城的销售系统，专注于灯饰产品的在线销售。系统包含前台商品展示、用户购物、订单管理，以及后台完整的商品、用户、权限管理功能。

## 📋 项目简介

本项目是一个基于升级版 SSH（Spring + Spring MVC + Hibernate）架构（替换了复杂的Struts框架）的 Java Web 电商系统，采用 Maven 进行项目管理。系统实现了完整的电商业务流程，包括商品分类展示、搜索、用户注册、评价留言、在线下单、订单查询等前台功能，以及用户角色权限、商品管理、厂家管理、留言评价管理、新闻广告管理、订单管理等后台功能。

## ✨ 主要功能

### 前台功能
- **商品展示**：商品分类展示、商品详情查看、商品搜索
- **用户系统**：用户注册、登录、个人信息管理
- **购物功能**：购物车、在线下单、订单查询
- **互动功能**：商品评价、留言反馈、联系我们
- **信息浏览**：公司介绍、新闻资讯、广告展示

### 后台管理
- **用户管理**：用户信息管理、角色管理、权限管理
- **商品管理**：商品信息管理、分类管理、库存管理
- **厂家管理**：厂家信息管理、厂家与商品关联
- **订单管理**：订单查询、订单状态管理
- **内容管理**：新闻管理、广告管理、留言管理
- **系统管理**：菜单管理、模块管理、权限配置

## 🛠️ 技术栈

### 后端技术
- **框架**：Spring 3.1.1、Spring MVC 3.1.1、Hibernate 3.6.8
- **构建工具**：Maven
- **数据库**：MySQL 5.x
- **连接池**：Apache DBCP 1.4
- **缓存**：EhCache 2.5.2
- **日志**：SLF4J + Log4j
- **其他**：AspectJ（AOP）、Javassist、Freemarker

### 前端技术
- **视图技术**：JSP、JSTL 1.2
- **样式**：CSS
- **脚本**：JavaScript

### 工具库
- **JSON处理**：FastJSON 1.1.17
- **HTML解析**：Jsoup 1.6.1
- **HTTP客户端**：Apache HttpClient 4.1.3
- **图片处理**：Java Image Scaling 0.8.5
- **文件上传**：Commons FileUpload 1.2.1
- **通用工具**：Apache Commons (Lang3, Codec, BeanUtils, Pool)

## 📁 项目结构

```
lamps-sell/
├── src/main/
│   ├── java/
│   │   ├── com/vvvv/
│   │   │   ├── common/          # 公共模块
│   │   │   │   ├── config/      # 配置类
│   │   │   │   ├── dao/         # 通用DAO
│   │   │   │   ├── handler/     # 处理器
│   │   │   │   ├── model/       # 通用模型
│   │   │   │   ├── service/     # 通用服务
│   │   │   │   ├── tool/        # 工具类（缓存、分页、查询、模板等）
│   │   │   │   └── view/        # 视图相关（过滤器、DTO）
│   │   │   ├── lamps/           # 灯饰业务模块
│   │   │   │   ├── dao/         # 数据访问层
│   │   │   │   ├── model/       # 实体模型（商品、订单、厂家等）
│   │   │   │   ├── service/     # 业务服务层
│   │   │   │   └── view/        # 控制器层
│   │   │   ├── module/          # 模块管理
│   │   │   │   ├── dao/
│   │   │   │   ├── model/       # 模块、菜单实体
│   │   │   │   ├── service/
│   │   │   │   └── view/
│   │   │   └── user/            # 用户管理模块
│   │   │       ├── dao/
│   │   │       ├── model/       # 用户、角色、权限实体
│   │   │       ├── service/
│   │   │       └── view/
│   │   └── config/              # 配置文件
│   │       ├── database.properties
│   │       └── spring/
│   ├── resources/               # 资源文件
│   │   ├── ehcache.xml
│   │   └── config/spring/
│   └── WebRoot/                 # Web资源
│       ├── WEB-INF/
│       │   ├── web.xml
│       │   └── classes/
│       ├── commons/             # 公共页面
│       ├── manage/              # 后台管理页面
│       ├── css/                 # 样式文件
│       ├── js/                  # 脚本文件
│       ├── images/              # 图片资源
│       └── *.jsp                # 前台页面
├── db/                          # 数据库脚本
│   ├── lampsSell_db3_withoutReference.sql
│   ├── lampsSell_db2.sql
│   └── data_struct_5_18.sql
├── doc/                         # 文档
│   └── images/                  # 截图
├── pom.xml                      # Maven配置文件
└── README.md                    # 项目说明
```

## 📊 数据库设计

### 核心数据表
- **userinfo**：用户信息表
- **role**：角色表
- **action**：权限表
- **module**：模块表
- **menu**：菜单表
- **category**：商品分类表
- **product**：商品表
- **complany**：厂家表
- **orders**：订单表
- **orderrow**：订单明细表
- **message**：留言表
- **news**：新闻表
- **ads**：广告表
- **contact**：联系信息表
- **tradinfo**：交易信息表

### 关联表
- **userrole**：用户角色关联表
- **roleaction**：角色权限关联表
- **rolemenu**：角色菜单关联表
- **complanyproduct**：厂家商品关联表
- **ordersproduct**：订单商品关联表

## 🚀 快速开始

### 环境要求
- JDK 1.6+
- Maven 3.x
- MySQL 5.x
- Tomcat 7.x 或 Jetty 8.x

### 安装步骤

1. **克隆项目**
```bash
git clone https://github.com/luowei/lamps-sell.git
cd lamps-sell
```

2. **创建数据库**
```bash
# 登录 MySQL
mysql -u root -p

# 执行数据库脚本
source db/lampsSell_db3_withoutReference.sql
```

3. **配置数据库连接**

编辑 `src/main/java/config/database.properties` 文件，修改数据库连接信息：
```properties
database.url=jdbc:mysql://localhost:3306/lampsSell?useUnicode=true&characterEncoding=utf-8
database.username=root
database.password=your_password
```

4. **编译项目**
```bash
mvn clean install
```

5. **运行项目**

**方式一：使用 Jetty（推荐用于开发）**
```bash
mvn jetty:run
```
访问：http://localhost:9090/lamps-sell

**方式二：部署到 Tomcat**
```bash
# 将生成的 war 包部署到 Tomcat
cp target/lampsSell.war $TOMCAT_HOME/webapps/
# 启动 Tomcat
$TOMCAT_HOME/bin/startup.sh
```
访问：http://localhost:8080/lampsSell

### 默认账号
- 前台用户：需注册后使用
- 后台管理：请查看数据库初始化数据

## 📸 系统截图

### 前台首页
![首页截图](https://raw.github.com/luowei/lamps-sell/master/doc/images/index.png)

### 后台管理
![后台截图](https://raw.github.com/luowei/lamps-sell/master/doc/images/manage.png)

## 🔧 配置说明

### Spring 配置
- **applicationContext.xml**：Spring 核心配置，包含数据源、SessionFactory、事务管理
- **webmvc-config.xml**：Spring MVC 配置，包含视图解析器、拦截器等

### Hibernate 配置
- 使用注解方式配置实体映射
- 集成 EhCache 二级缓存
- 支持查询缓存

### 数据库连接池配置
- 使用 Apache DBCP 连接池
- 配置了连接池的各项参数（最大连接数、超时时间等）
- 支持连接有效性验证

## 📝 开发说明

### 项目特点
1. **标准 MVC 架构**：清晰的分层结构，便于维护和扩展
2. **Spring 事务管理**：声明式事务，保证数据一致性
3. **通用 DAO 封装**：简化数据访问层开发
4. **权限管理**：基于角色的权限控制（RBAC）
5. **缓存支持**：使用 EhCache 提升系统性能
6. **响应式延迟加载**：Hibernate 懒加载 + 自定义过滤器

### 代码统计
- Java 类：157 个
- JSP 页面：93 个
- 数据表：20+ 张

## 📄 许可证

本项目采用开源协议，具体请查看项目源码。

## 👨‍💻 作者

- GitHub: [@luowei](https://github.com/luowei)

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📮 联系方式

如有问题或建议，欢迎通过 GitHub Issues 反馈。