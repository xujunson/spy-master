spy-master
 - spy-guide: 使用javaagent+MDC实现全链路监控基础版

# 跨线程传递traceId

-javaagent:D:\project\tom\spy-master\spy-guide\target\spy-guide-1.0-SNAPSHOT-jar-with-dependencies.jar -Xbootclasspath/a:D:\maven\repository\org\slf4j\slf4j-api\1.7.29\slf4j-api-1.7.29.jar;D:\maven\repository\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;D:\maven\repository\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;D:\maven\repository\net\bytebuddy\byte-buddy\1.10.21\byte-buddy-1.10.21.jar;D:\maven\repository\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar;D:\maven\repository\cn\hutool\hutool-core\5.8.6\hutool-core-5.8.6.jar

[调用链上下文跨线程传递](https://mp.weixin.qq.com/s?search_click_id=11594836081973060259-1682303733996-1473811714&__biz=MzU5ODc3NjM0MA==&mid=2247483716&idx=1&sn=ce0e3538b55dfa8f1cdfaefac9781212&chksm=febe4e3fc9c9c72958ded67ab17530104e76b431ace8fb7e2c56734aa6e5546f3d2b36e0bfad&scene=7#rd)

[一次「找回」TraceId的问题分析与过程思考](https://mp.weixin.qq.com/s/T7P2-tiroXWI9xd8FhsuFA)


## 问题
 - 为什么跨线程不能透传traceId
 - 类加载顺序：Bootstrap、ExtClassLoader、AppClassLoader，三者之间的关系
 - 为什么会报 NoClassDefFoundError 问题？ClassNotFoundException 和 NoClassDefFoundError区别
 - InheritableThreadLocal、TransmittableThreadLocal和ThreadLocal有什么区别？

### NoClassDefFoundError问题

 - ClassNotFoundException是在运行期间（runtime）无法找到指定的类，即程序已经启动运行，而在某个时刻需要使用某个类时，发现无法找到该类。这通常是由于类名拼写错误、类所在的包路径不正确、或者类文件缺失等原因导致的。
 - 而NoClassDefFoundError是在类加载期间（class loading）无法找到某个类的定义，即在程序启动运行之前，在进行类加载时就已经发生错误。这通常是由于类路径或者依赖的库发生了变化，或者类文件被破坏或者删除等原因导致的。

https://www.cnblogs.com/hainange/p/6334011.html

[Springboot上运行javaagent时出现NoClassDefFoundError错误的分析和解决 ](https://juejin.cn/post/7067363361368834061#heading-26)
[接入javaagent项目报错NoClassDefFoundError](https://www.jianshu.com/p/a36a35b66fab)
### ThreadLocal VS InheritableThreadLocal VS TransmittableThreadLocal

> ThreadLocal

多线程访问同一个共享变量的时候容易出现并发问题，特别是多个线程对一个变量进行写入的时候，为了保证线程安全，一般使用者在访问共享变量的时候需要进行额外的同步措施才能保证线程安全性。

ThreadLocal 是除了加锁这种同步方式之外的一种保证一种规避多线程访问出现线程不安全的方法，当我们在创建一个变量后，
如果每个线程对其进行访问的时候访问的都是线程自己的变量这样就不会存在线程不安全问题。

ThreadLocal 是JDK包提供的，它提供本地变量，如果创建一个 ThreadLocal 变量，那么访问这个变量的每个线程都会有这个变量的一个副本，在实际多线程操作的时候，
操作的是自己本地内存中的变量，从而规避了线程安全问题。

ThreadLocal 缺陷：ThreadLocal 无法在父子线程之间传递，看源码可以发现都是`Thread.currentThread`。

> InheritableThreadLocal

由于 ThreadLocal 在父子线程交互中子线程无法访问到存储在父线程中的值，无法满足某些场景的需求，比如链路追踪。

为了解决上述问题，jdk 引入了 InheritableThreadLocal，即子线程可以访问父线程中的线程本地变量，准确的说是子线程可以访问在创建子线程时父线程当时的本地线程变量，
其实现原理是在创建子线程将父线程当前存在的本地线程变量拷贝到子线程的本地线程变量中。

局限性：InheritableThreadLocal 支持子线程访问在父线程的核心思想是在创建线程的时候将父线程中的本地变量值复制到子线程，即复制的时机为创建子线程时。
线程池能够复用线程，减少线程的频繁创建与销毁，如果使用 InheritableThreadLocal ，那么线程池中的线程拷贝的数据来自于第一个提交任务的外部线程，
即后面的外部线程向线程池中提交任务时，子线程访问的本地变量都来源于第一个外部线程，造成线程本地变量混乱。

> TransmittableThreadLocal

TransmittableThreadLocal 是阿里巴巴开源的专门解决 InheritableThreadLocal 的局限性，实现线程本地变量在线程池的执行过程中，能正常的访问父线程设置的线程变量。

TransmittableThreadLocal（TTL）是一个线程本地变量（ThreadLocal）的增强版，它可以在线程之间进行值传递。
TTL通过拦截线程的创建和销毁，以及线程池的线程复用等操作，将线程本地变量的值在不同线程之间进行传递。
TTL的本质是使用了Java的动态代理机制，在拦截线程操作的同时，动态地生成代理对象，并将线程本地变量的值保存在代理对象中，从而实现值传递的功能。

从InheritableThreadLocal 不支持线程池的根本原因是 InheritableThreadLocal 是在父线程创建子线程时复制的，
由于线程池的复用机制，“子线程”只会复制一次。要支持线程池中能访问提交任务线程的本地变量，
其实只需要在父线程在向线程池提交任务时复制父线程的上下环境，那在子线程中就能够如愿访问到父线程中的本地遍历，
实现本地环境变量在线程调用之中的透传，实现链路跟踪，这也就是 TransmittableThreadLocal 最本质的实现原理。


