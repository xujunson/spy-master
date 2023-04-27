spy-monitor: 使用javaagent 实现对线程池、JVM 的监控

主要是在线程池创建时，对线程池进行增强，将线程池强引用到ThreadPoolMonitorData里。
使用此包的前提是线程池是固定资源，启动后不会销毁，如果动态创建线程池并销毁，需要调用shutdown或者shutdowNow，这块已实现对对强引用的解除，
否则因为这块强引用导致资源无法释放，没法被gc回收。


-javaagent:D:\project\tom\spy-master\spy-monitor\target\spy-monitor-1.0-SNAPSHOT.jar=127.0.0.1,8888

[JDK 内置的轻量级 HTTP 服务器 --- HttpServer](https://blog.csdn.net/xietansheng/article/details/78704783)

### com.atu.monitor 目录结构
#### ByteBuddyAgent  为主类
- 增强ThreadPoolExecutor类
- 初始化httpServer

#### advice 为 具体的增强
#### dto 为传输包装
#### httpserver
- 实现了http服务（参考zabbix的jmx_exporter）
- 功能服务必须继承Collector，同时在构造方法里设置methodName，
- 实现 collect 接口，就是具体要干的事，以及对应的返回参数
- 在 MonitorAgent.initHttpServer()方法中实例化一个对象并且register()
  如  new ThreadPoolCollector().register();

### 主要实现功能
- JVMCollector  jvm的监控
- StackCollector 当前栈信息收集
- ThreadPoolCollector 线程池收集
- ThreadPoolModify  线程池部分信息修改
### 注意事项：
- 线程池的ThreadFactory最好使用NamedThreadFactory，否则无法获取线程池的名称和描述
- 针对没有使用NamedThreadFactory的ThreadPoolExecutor使用对象的hashCode做为key

### 测试
- jvm 数据获取：http://127.0.0.1:8888/jvm
- 线程池数据获取：http://127.0.0.1:8888/threadPool
- 线程池修改：http://127.0.0.1:8888/threadPool/modify?key=execute-pool-1&coreSize=3&maximumPoolSize=100
