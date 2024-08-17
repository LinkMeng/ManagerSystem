<h1 align="center">资源管理系统</h1>
<div align="center">
<a href="https://github.com/LinkMeng/ManagerSystem/stargazers"><img src="https://img.shields.io/github/stars/LinkMeng/ManagerSystem" alt="Stars Badge"/></a>
<a href="https://github.com/LinkMeng/ManagerSystem/network/members"><img src="https://img.shields.io/github/forks/LinkMeng/ManagerSystem" alt="Forks Badge"/></a>
<a href="https://github.com/LinkMeng/ManagerSystem/pulls"><img src="https://img.shields.io/github/issues-pr/LinkMeng/ManagerSystem" alt="Pull Requests Badge"/></a>
<a href="https://github.com/LinkMeng/ManagerSystem/issues"><img src="https://img.shields.io/github/issues/LinkMeng/ManagerSystem" alt="Issues Badge"/></a>
<a href="https://github.com/LinkMeng/ManagerSystem/graphs/contributors"><img alt="GitHub contributors" src="https://img.shields.io/github/contributors/LinkMeng/ManagerSystem?color=2b9348"></a>
<a href="https://github.com/LinkMeng/ManagerSystem/blob/master/LICENSE"><img src="https://img.shields.io/github/license/LinkMeng/ManagerSystem?color=2b9348" alt="License Badge"/></a>
</div>

> 基于SpringBoot

# 一、环境依赖

1. Maven >= 3.9.8
2. OpenJDK = 1.8.0

⚠️注意！请勿使用过高版本的JDK，否则可能会导致功能不正常。

# 二、其它文档

1. [系统简介](./docs/README.md)
2. [接口文档](./docs/Interface.md)
3. [功能说明](./docs/Coding.md)

# 三、运行

可以选择从源码直接启动，或使用jar包启动。

> 当项目运行后，会在项目根目录或jar包同级目录生成默认名为`user_resource_data.json`的数据持久化文件。也可以在上述位置先手动创建该文件，然后再启动项目。需要注意，如果要手动创建或修改该数据持久化文件，请务必保证文件内容格式正确，否则可能会导致项目启动失败，或功能无法正常运行。

两种启动方法的流程如下：

## 3.1 从源码直接运行

在项目根目录执行命令：

```shell
mvn spring-boot:run
```

当看到类似如下日志，证明启动成功：

```text
2024-08-17 08:48:42,463 INFO  [traceId-mainraceId][main][org.springframework.boot.web.embedded.tomcat.TomcatWebServer 220] Tomcat started on port(s): 8080 (http) with context path ''
2024-08-17 08:48:42,468 INFO  [traceId-mainraceId][main][org.springframework.boot.StartupInfoLogger 61] Started ManagerSystemApplication in 1.088 seconds (JVM running for 1.839)
```

启动后的SpringBoot端口号默认为8080，也可以在`/src/main/resources/application.yaml`文件中进行配置。

## 3.2 从jar包运行

在项目根目录执行以下命令进行打包（如果已获取到jar包，则跳过打包步骤）：

```shell
mvn clean install
```

打好的jar包位于`target`目录下，默认文件名为：`ManagerSystem-0.0.1-SNAPSHOT.jar`。

使用`cd`命令跳转到jar包所在的目录，并执行以下命令启动jar包（也可直接双击运行）：

```shell
java -jar <JAR_FILE_NAME>
```

如：

```shell
java -jar ManagerSystem-0.0.1-SNAPSHOT.jar
```
