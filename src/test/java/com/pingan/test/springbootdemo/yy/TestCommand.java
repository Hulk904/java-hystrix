package com.pingan.test.springbootdemo.yy;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by yangyuan on 7/27/18.
 */
public class TestCommand extends HystrixCommand<String>{

    protected TestCommand(HystrixCommandGroupKey group) {
        super(group);
    }

    @Override
    protected String run() throws Exception {//run方法将在不同的线程中执行
        return "hello";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        TestCommand testCommand = new TestCommand(HystrixCommandGroupKey.Factory.asKey("group"));//每个command对象只能调用一次
        System.out.println(testCommand.execute());//同步获取
        testCommand = new TestCommand(HystrixCommandGroupKey.Factory.asKey("group"));
        System.out.println(testCommand.queue().get(1, TimeUnit.SECONDS));//异步调用
        testCommand = new TestCommand(HystrixCommandGroupKey.Factory.asKey("group"));
        Subscription subscriber = testCommand.observe().subscribe(new Action1<String>() {//注册结果回调事件
            @Override
            public void call(String s) {
                System.out.println("callback :" + s);
            }
        });
        testCommand = new TestCommand(HystrixCommandGroupKey.Factory.asKey("group"));
        testCommand.observe().subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {// onNext/onError完成之后最后回调
                System.out.println("complete..");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error");
            }

            @Override
            public void onNext(String s) {// 获取结果后回调
                System.out.println("result " + s);
            }
        });
    }

}
