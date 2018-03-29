package com.data.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gaogf on 2018/3/29.
 * 双重校验锁法
 * STEP 1. 线程A访问getInstance()方法，因为单例还没有实例化，所以进入了锁定块。
 * STEP 2. 线程B访问getInstance()方法，因为单例还没有实例化，得以访问接下来代码块，而接下来代码块已经被线程1锁定。
 * STEP 3. 线程A进入下一判断，因为单例还没有实例化，所以进行单例实例化，成功实例化后退出代码块，解除锁定。
 * STEP 4. 线程B进入接下来代码块，锁定线程，进入下一判断，因为已经实例化，退出代码块，解除锁定。
 * STEP 5. 线程A获取到了单例实例并返回，线程B没有获取到单例并返回Null。
 * 理论上双重校验锁法是线程安全的，并且，这种方法实现了lazyloading。
 */
@Configuration
public class SparkSingleTonUtil {
    private static volatile SparkSession instance;

    @Bean
    @ConditionalOnClass(SparkConf.class)
    @ConditionalOnMissingBean(SparkSession.class)
    public static SparkSession getInstance(SparkConf sparkConf){
        if(instance == null){
           synchronized (SparkSingleTonUtil.class){
               if(instance == null){
                   instance = SparkSession
                           .builder()
                           .config(sparkConf)
                           .getOrCreate();
               }
           }
        }
        return instance;
    }
    @Bean
    public SparkConf sparkConf(){
        return new SparkConf();
    }
}
