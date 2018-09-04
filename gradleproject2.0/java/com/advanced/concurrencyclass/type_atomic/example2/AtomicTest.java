package com.advanced.concurrencyclass.type_atomic.example2;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicTest {
    public static void main(String[] args) {

    }

    /**
     * 方法accumulateAndGet() 接受 IntBinaryOperator类型的lambda表达式。我们可以使用这种方法并行计算累加0~1000的值
     */
    @Test
    public void test4(){
        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000)
                .forEach(i -> {
                    Runnable task = () ->
                            atomicInt.accumulateAndGet(i, (n, m) -> n + m);
                    executor.submit(task);
                });

        stop(executor);

        System.out.println(atomicInt.get());// => 499500
    }

    /**
     * AtomicInteger 支持不同类型的原子操作。方法updateAndGet()接受lambda表达式，可以对整数执行任意算术运算
     */
    @Test
    public void test3(){
        AtomicInteger atomicInt = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000)
                .forEach(i -> {
                    Runnable task = () ->atomicInt.updateAndGet(n -> n + 2);
                    executor.submit(task);
                });

        stop(executor);

        System.out.println(atomicInt.get());// => 2000
    }

    @Test
    public void test2(){
        AtomicInteger atomicInt = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000)
                .forEach(i -> executor.submit(atomicInt::incrementAndGet));
        stop(executor);
        System.out.println(atomicInt.get());// => 1000
    }

    @Test
    public void test1() {
        AtomicInteger ai = new AtomicInteger(0);
        int i1 = ai.get();
        System.out.println("@return the current value:"+i1);
        int i2 = ai.getAndSet(5);
        System.out.println("@return the previous value:"+i2);
        int i3 = ai.get();
        System.out.println("@return the current value:"+i3);
        int i4 = ai.getAndIncrement();
        System.out.println("Atomically increments by one the current value.@return the previous value:"+i4);
        System.out.println("@return the previous value:"+ai.get());
        int i5=ai.incrementAndGet();
        System.out.println(" Atomically increments by one the current value. @return the updated value:"+i5);
        System.out.println("@return the previous value:"+ai.get());
    }
    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("termination interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("killing non-finished tasks");
            }
            executor.shutdownNow();
        }
    }
}