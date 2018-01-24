package com.learn.conf;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * Created by pactera on 2018/1/23.
 */
@Configuration
@ConditionalOnClass({SparkConf.class, JavaSparkContext.class})
@EnableConfigurationProperties(SparkProperties.class)
public class SparkConfiguration {
    @Autowired
    private SparkProperties sparkProperties;

    @Bean
    @ConditionalOnMissingBean
    public SparkConf sparkConf() {
        Optional.ofNullable(sparkProperties.getAppname()).orElseThrow(() -> new IllegalArgumentException("Spark Conf Bean not created. App Name not defined."));
        final SparkConf conf = new SparkConf();
        sparkProperties.getProps().forEach((prop, value) -> conf.set("spark." + (String)prop, (String)value));
        return conf.setAppName(sparkProperties.getAppname()).setMaster(Optional.ofNullable(sparkProperties.getMaster()).map(master -> master).orElse("local"));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(SparkSession.class)
    @ConditionalOnProperty(name="spark.appname")
    public SparkSession sqlSession(){
        Optional.ofNullable(sparkProperties.getAppname()).orElseThrow(() -> new IllegalArgumentException("Spark Session Bean not created. App Name not defined."));
        return SparkSession.builder().appName(sparkProperties.getAppname()).config(sparkConf()).getOrCreate();
    }
}
