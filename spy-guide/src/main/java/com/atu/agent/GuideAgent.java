package com.atu.agent;

import com.atu.aop.TracingAdvice;
import com.atu.transformer.TlTransformer;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author: Tom
 * @date: 2023/4/15 17:11
 * @description: TODO
 **/
public class GuideAgent {

    private static final String transmittableJar = "D:/maven/repository/com/alibaba/transmittable-thread-local/2.12.2/transmittable-thread-local-2.12.2.jar";
    private static final String bytebuddyJar = "D:/maven/repository/net/bytebuddy/byte-buddy/1.10.21/byte-buddy-1.10.21.jar";

    public static void premain(String agentArgs, Instrumentation instrumentation) {

        System.out.println(">>>>>>> agent 启动...");
        // 使用启动类加载器load
        File transmittableFile = new File(transmittableJar);
        if (!transmittableFile.exists()) {
            System.out.println("transmittableFile does not exist: " + transmittableFile);
            return;
        }
        File bytebuddyFile = new File(bytebuddyJar);
        if (!bytebuddyFile.exists()) {
            System.out.println("bytebuddyFile does not exist: " + bytebuddyFile);
            return;
        }
        // 使用启动类加载器加载
        try {
            instrumentation.appendToBootstrapClassLoaderSearch(new JarFile(transmittableFile));
            instrumentation.appendToBootstrapClassLoaderSearch(new JarFile(bytebuddyFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AgentBuilder agentBuilder = new AgentBuilder.Default();
        handleTraceId(instrumentation, agentBuilder);

        ClassFileTransformer transformer = new TlTransformer();
        instrumentation.addTransformer(transformer, true);
    }

    private static void handleTraceId(Instrumentation instrumentation, AgentBuilder agentBuilder) {

        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            builder = builder.visit(
                    Advice.to(TracingAdvice.class)
                            .on(isMethod()
                                    .and(ElementMatchers.any()).and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")))));

            builder.method(named("invokeForRequest"));
            return builder;
        };

        agentBuilder = agentBuilder.type(named("org.springframework.web.method.support.InvocableHandlerMethod"))
                .transform(transformer);

        //监听
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
                System.out.println("onTransformation：" + typeDescription);
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

        };

        agentBuilder.with(listener).installOn(instrumentation);
    }

}