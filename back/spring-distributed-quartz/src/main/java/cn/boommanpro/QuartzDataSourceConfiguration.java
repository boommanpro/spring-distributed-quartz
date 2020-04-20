package cn.boommanpro;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/5/29 17:24
 * @created by BoomManPro
 */
@Configuration
public class QuartzDataSourceConfiguration {

    @QuartzDataSource
    @Bean(DataSourceConfig.QUARTZ_DATA_SOURCE)
    @ConfigurationProperties(DataSourceConfig.DB_QUARTZ_PREFIX)
    public DataSource quartzDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
