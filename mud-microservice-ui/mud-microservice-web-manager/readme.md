## web后台服务

### Hystrix熔断策略配置

    @HystrixCommand(defaultFallback = "bizMethodFallBack",
            commandProperties = {
                    // 执行隔离策略，信号量,默认线程池策略
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
                    // 当在配置时间窗口(默认十秒一个窗口)内达到此数量的失败后，进行短路，默认20个。
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    // 熔断器默认工作时间,默认:5秒.熔断器中断请求后的10秒内会进入半打开状态,10秒后会放部分请求通过尝试。
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    // 熔断器容错比率，默认50%。意味着在时间窗口内，有50%的请求失败，就会熔断。
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
                    // 熔断器的窗口(类似spark stream里面的抽象)时间范围，默认10秒一个窗口
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000")
            })
    @Override
    public Optional<List<ActivityDto>> bizMethod() throws Exception {
        // you biz code
    }
    
具体配置参考: https://github.com/Netflix/Hystrix/wiki/Configuration