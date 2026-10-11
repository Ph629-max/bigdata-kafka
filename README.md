# Kafka 生产消费与自定义组件实战（bigdata-kafka）

> 大数据课程实践 · Kafka Client 示例合集

## 📖 项目简介

本项目基于 **Apache Kafka 原生客户端（kafka-clients）**，系统整理了 Kafka 生产者、消费者以及**自定义序列化器、分区器、拦截器、分区分配策略**的完整示例，适合作为 Kafka 客户端 API 的学习与练习工程。

## ✨ 内容清单

| 分类 | 示例类 |
|------|--------|
| 基础收发 | `ProducerBase`、`SendTest`、`KafkaConsumerTest` |
| 多线程生产 | `ProducerThread`、`MutiProducerTest` |
| 多对象/多类型 | `UserInfoProducerTest`、`MutiObjProducerTest` |
| 自定义序列化 | `UserSerializer`、`UserSerializerTest`、`ApplicationInfoSerilizer` |
| 自定义分区器 | `MyPartitioner`、`MyPartitionerTest`、`ApplicationInfoPartitioner` |
| 生产者拦截器 | `PrefixProducerInterceptor`、`MyInterceptorTest` |
| 消费者拦截器 | `IdentifierInterceptor`、`ConsumerInterceptorTest` |
| 分区分配策略 | `CustormAssignorTest` |
| 业务示例（身份证识别） | `IDIdentifierTest`、`ValidateIDCardUtil`、`ApplicationInfo` |
| 工具 | `KafkaHelper`、`User` |

## 🛠 技术栈

- **语言**：Java 8
- **构建**：Maven
- **Kafka 客户端**：`org.apache.kafka:kafka-clients:2.4.1`
- **JSON 序列化**：`com.fasterxml.jackson.core:jackson-databind:2.4.1`

## 📂 目录结构

```
bigdata-kafka/
├── pom.xml
├── bigdata-kafka/            # IDEA 模块
└── src/main/java/edu/nanzhi/
    ├── Main.java
    ├── IDIdenfier/           # 身份证识别相关生产者/序列化/分区/拦截器
    └── util/                 # KafkaHelper、自定义分配策略
```

## 🚀 快速开始

1. 启动一个可用的 Kafka Broker（本地或容器）。
2. 在各示例类中把 `bootstrap.servers` 改为你的 Broker 地址、`topic` 改为已创建的主题。
3. 编译并运行指定示例：

```bash
mvn clean compile
# 运行具体示例（IDE 中直接右键 Run 即可）
mvn exec:java -Dexec.mainClass="edu.nanzhi.SendTest"
```

## ⚠️ 说明

- 示例中的 Broker 地址、Topic、序列化 UUID 等均为演示值，运行前请按实际环境替换。
- Kafka 2.4.1 客户端与较新 Broker（3.x）通信一般兼容，如需高版本特性请自行升级依赖。
