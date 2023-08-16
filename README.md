# Spring boot 集成Jpa和QueryDls

## 期望目标

### 使用jpa和queryDls完成某个领域实体最基本的增删改查功能

### 使用queryDsl完成子查询、联表查询、分页等复杂操作

### 尝试学习理解和使用高级特性最大化简化编码工作

### 尽可能的集成其它组件，包括spring doc、spring boot admin、spring validation和spring security

## 已完成工作

### 已完成和spring boot、jpa和queryDsl的整合

### 已集成spring data admin日志监控

### 已集成spring doc(swagger2)

### 结构化module,抽出共享父pom并更加规范了依赖关系以及相应版本定义

### 完成了全局统一返回对象以及全局异常处理器逻辑

### 完成了sql打印,参数打印以及对日志的过滤信息

### 完成了spring validation的集成，并实现了分组验证

### 完成了queryDsl单表操作/分页/动态查询等

## 待办列表

### spring security

### queryDsl多表操作

### 多数据源自动切换

### 尝试集成消息中间件kafka，完成基础消息订阅和消费

## 总结

1. 表和entity映射规则设置为CamelCaseToUnderscoresNamingStrategy,遇到驼峰时自动使用下划线来解析,除了表名额外指定外所有表字段在java中均采用驼峰,在db中均采用下划线
2. 针对entity抽取出公共属性baseEntity,通过注解@MappedSuperclass来标记它不存在于db表中脱离自动生成范畴
3. 在进行更新/删除操作时jpa要求一定存在有事务,所以需要手动开启事务@Transactional且包含有@Modifying注解

## 遇到问题

### 1.当在具体某个实体类上的某个属性上存在有多个注解，如User对象的sex属性含有@NotNull和@Range，这些注解会被依次触发执行并进行验证，目前解决这个问题的话有想到2个解决思路

* 将注解合二为一统一为自定义注解，需要加上非空判断相关逻辑和动态替换校验结果提示文本相关逻辑
* 将该自定义注解改变成为一个“标记”注解，只用来做标记并尝试在spring validation校验器自定义的校验逻辑上嵌入相关标记注解的验证逻辑(高难度,影响范围大,不推荐此方式)

最终实际上还是选择第一种方案,具体内容参考@EnumCheckNotNull的校验机制相关逻辑

 