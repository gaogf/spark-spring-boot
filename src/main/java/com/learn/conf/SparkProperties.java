package com.learn.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by pactera on 2018/1/23.
 */
@Component
@PropertySource("spark.properties")
@ConfigurationProperties(prefix = "spark")
public class SparkProperties {

    private String appname;

    private String master;

    private Properties props = new Properties();

    private StreamingProperties streaming = new StreamingProperties();

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public StreamingProperties getStreaming() {
        return streaming;
    }

    public void setStreaming(StreamingProperties streaming) {
        this.streaming = streaming;
    }

    @ConfigurationProperties("streaming")
    public static class StreamingProperties {

        private Integer duration;

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

    }
}
