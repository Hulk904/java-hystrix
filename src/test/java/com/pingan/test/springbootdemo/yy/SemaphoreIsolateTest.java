package com.pingan.test.springbootdemo.yy;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * Created by yangyuan on 7/27/18.
 */
public class SemaphoreIsolateTest extends HystrixCommand<String>{

    protected SemaphoreIsolateTest() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("group"))
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        /* 配置信号量隔离方式,默认采用线程池隔离 ，信号量可以降低线程隔离带来的开销*/
    }

    @Override
    protected String run() throws Exception {
        return "run logic ，thirdName =" + Thread.currentThread().getName();//将会在主线程中执行
    }

    public static void main(String[] args) {
        SemaphoreIsolateTest semaphoreIsolateTest = new SemaphoreIsolateTest();
        System.out.println(semaphoreIsolateTest.execute());
        System.out.println("main threadName = " + Thread.currentThread().getName());
    }
}
