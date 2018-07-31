package com.pingan.test.springbootdemo.yy;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * Created by yangyuan on 7/27/18.
 */
public class DowngradeTest extends HystrixCommand<String> {

    protected DowngradeTest() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("group"))//命令分组用于对依赖操作分组,便于统计,汇总等.
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
    }

    @Override
    protected String getFallback(){//降级策略
        return "fallback resutl";
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.SECONDS.sleep(1);//让调用超时
        return "hello";
    }

    public static void main(String[] args) {
        DowngradeTest downgradeTest = new DowngradeTest();
        System.out.println(downgradeTest.execute());
    }
}
