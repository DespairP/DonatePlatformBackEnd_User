package org.wangtianyu.userPlatform.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:Secret/DataBaseSecret.properties")
public class HikariPoolConfig {
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;

    /**Hikari连接池配置*/
    @Bean
    public HikariConfig hikariConfig(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306?database=donateplatform");
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(30);
        return config;
    }

    @Bean
    public DataSource dataSource(HikariConfig config) {
        return new HikariDataSource(config);
    }
}
