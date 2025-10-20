package com.yupi.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Kafka Learning Application
 * 
 * @author canyon.zhao
 */
@SpringBootApplication
@EnableConfigurationProperties
@Slf4j
public class KafkaLearningApplication {
    
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(KafkaLearningApplication.class);
        springApplication.setRegisterShutdownHook(false); // disable Spring original shutdownhook
        ConfigurableApplicationContext context = springApplication.run(args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down...");
            // 这里可以做其他优雅退出处理，例如回收本地线程池、关闭定时调度器等操作
            try {
                // 如果线程处于阻塞状态(sleep、wait、join等)，调用interrupt会跑出了该异常并清除中断状态
                Thread.sleep(2000); // 等待一段时间，这里给时间dubbo的shutdownhook执行
                log.info("Shutting down complete");
            } catch (InterruptedException e) {
                log.error("Web context start fail", e);
                Thread.currentThread().interrupt();
            }
            // 关闭Spring容器
            context.close();
        }));
    }
}