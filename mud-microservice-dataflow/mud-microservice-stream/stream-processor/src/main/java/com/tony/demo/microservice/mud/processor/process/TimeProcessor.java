package com.tony.demo.microservice.mud.processor.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class TimeProcessor {

    private final Logger logger = LoggerFactory.getLogger(TimeProcessor.class);

    private int count = 0;

    /**
     * 命令式编程模型
     *
     * @param timestamp
     * @return
     */
    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Object transform(Long timestamp) {
        logger.info("processor input: {}", ++count);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return dateFormat.format(timestamp);
    }
/*
    // 功能性编程模型(反应器模式)
    @StreamListener
    @Output(Processor.OUTPUT)
    public Flux<String> method2(@Input(Processor.INPUT) Flux<Long> input) {
        return input.map(timestamp -> "当前时间戳 " + timestamp);
    }
*/
}
