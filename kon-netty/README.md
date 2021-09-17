#### Kon Netty 服务

#####目前支持功能

- netty服务端
- netty客户端


##### 使用方式

````java
@EnableKonNetty
````

> 通过kon.netty.terminal 区分启动服务。

##### 配置文件详解
```yaml
kon:
  netty:
    # 启动端:默认启动TCP服务 tcp、client
    terminal: tcp
    # ip/域名 默认 空
    host: 
    # 端口
    port: 1217
    # 闲置通道设置
    idle:
      # 读闲置时间
      readerIdleTime: 0
      # 写闲置时间
      writerIdleTime: 0
      # 全部闲置时间
      allIdleTime: 60
      # 时间单位
      timeUnit: seconds
    # 通道操作
    option:
      # 初始化服务端可连接队列, client不支持
      backLogCount: 204800
      # 连接超时时间
      connectTimeOutMs: 30000
      # 缓冲区字节数
      rcvBufSize: 1024
      # 通道保持活跃
      keepLive: true
      # Nagle算法，组装成大的数据包进行发送
      tcpNoDelay: true
```