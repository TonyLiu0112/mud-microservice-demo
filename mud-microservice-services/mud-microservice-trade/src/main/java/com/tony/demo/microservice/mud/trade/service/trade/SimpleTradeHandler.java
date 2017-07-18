package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.trade.service.bean.req.TradeHandleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 简单的交易处理器
 * <p>
 * Created by Tony on 26/06/2017.
 */
@Service
public class SimpleTradeHandler {

    private Logger logger = LoggerFactory.getLogger(SimpleTradeHandler.class);

    private ArrayBlockingQueue<TradeHandleRequest> queue = new ArrayBlockingQueue<>(200);

    @Autowired
    private FundTradePurchaseService fundTradePurchaseService;
    @Autowired
    private FundTradeRedemptionService fundTradeRedemptionService;
    private final ExecutorService executorService;
    private final AtomicBoolean goOn = new AtomicBoolean(true);

    public SimpleTradeHandler() {
        this.executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new handler());
    }

    void publish(TradeHandleRequest orders) throws InterruptedException {
        this.queue.put(orders);
    }

    private class handler implements Runnable {

        @Override
        public void run() {
            while (goOn.get()) {
                logger.debug("异步交易任务开始拉取任务.");
                try {
                    TradeHandleRequest handleRequest = queue.poll(10, TimeUnit.SECONDS);
                    if (handleRequest != null) {
                        if (handleRequest.getType() == 1) {

                            fundTradePurchaseService.purchase(handleRequest);
                        } else {
                            fundTradeRedemptionService.redemption(handleRequest);
                        }
                    }
                } catch (Exception e) {
                    logger.error("执行提交的申购或赎回任务失败.", e);
                }
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        goOn.set(false);
        if (!executorService.isShutdown())
            executorService.shutdown();
    }
}
