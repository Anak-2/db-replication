package replica.study.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

import static replica.study.config.ReplicationRoutingDataSource.DATASOURCE_KEY_MASTER;
import static replica.study.config.ReplicationRoutingDataSource.DATASOURCE_KEY_SLAVE;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // 스프링 부트의 자동 데이터베이스 구성을 비활성화
@EnableTransactionManagement // 트랜잭션 관리를 활성화
@EnableJpaRepositories(basePackages = {"replica.study.repository"}) // JPA 리포지토리를 활성화하고, 패키지 스캔 경로를 지정
public class DataBaseConfig {

    // 마스터 데이터베이스 설정을 위한 빈
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 슬레이브 데이터베이스 설정을 위한 빈
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 라우팅
    @Bean
    public DataSource routingDataSource(@Qualifier("masterDataSource") DataSource master,
                                        @Qualifier("slaveDataSource") DataSource slave) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        HashMap<Object, Object> sources = new HashMap<>();
        sources.put(DATASOURCE_KEY_MASTER, master);
        sources.put(DATASOURCE_KEY_SLAVE, slave);

        routingDataSource.setTargetDataSources(sources);
        routingDataSource.setDefaultTargetDataSource(master);

        return routingDataSource;
    }
    // 데이터베이스에 접근하는 기본적인 데이터 소스를 설정
    @Primary
    @Bean
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}