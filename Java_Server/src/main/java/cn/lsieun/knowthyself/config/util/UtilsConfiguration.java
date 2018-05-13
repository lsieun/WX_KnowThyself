package cn.lsieun.knowthyself.config.util;

import cn.lsieun.knowthyself.util.snowflake.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfiguration {
    @Value("${snowflake.dataCenterId}")
    private long dataCenterId;
    @Value("${snowflake.dataCenterId}")
    private long workerId;

    @Bean(name = "snowflakeIdWorker")
    public SnowflakeIdWorker createSnowflakeIdWorker(){
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(workerId, dataCenterId);
        return snowflakeIdWorker;
    }
}
