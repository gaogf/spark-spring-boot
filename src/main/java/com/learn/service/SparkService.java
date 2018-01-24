package com.learn.service;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by pactera on 2018/1/23.
 */
@Service
public class SparkService implements Serializable{
    @Autowired
    private SparkSession sparkSession;

    public String getVersion(){
        return sparkSession.version();
    }
}
