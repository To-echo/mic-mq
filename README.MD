# mic-mq
    
    mic-mq 是一款 RockerMQ 工具包，目前只集成了 RokcetMQ 的 DefaultProducer 特性，后续将不断更新。
    目标是打造一个无侵入、一键引入便可以直接使用的消息中间件
    
### mic-rocketmq-client 
    
    事务消息调用客户端，通过该客户端向 mic-rocketmq-server 发送请求，进而来完成：
    1.事务消息的 prepare 及 commit
    2.直接发送
 
    todo
    1.引入 rpc 框架，将调用 mic-rocketmq-server 改造成 将远程调用
    
### mic-rocketmq-server 
    
    mq 消息协调者，提供 prepare、commit、direct send等接口将消息持久化到db,并通过定时任务对中间状态的数据进行恢复补偿
    
    todo
    1.引入 rpc 框架，提供远程调用接口
    2.定时任务补偿

### mic-config-center

    配置文件中心
    
### mic-config-example
    
    使用案例

### mic-rocketmq-example

    使用案例（待完善）