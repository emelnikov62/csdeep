package ru.csdeep.config

import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource

/**
 * Mybatis configuration.
 * @author Evgenii Melnikov
 */
@SpringBootConfiguration
@MapperScan(
    value = ["ru.csdeep.dao"],
    sqlSessionFactoryRef = "sqlserverSqlSessionFactory"
)
class MybatisConfig {

    /**
     * SQL Server sql session factory.
     */
    @Suppress("SpreadOperator")
    @Bean
    fun sqlserverSqlSessionFactory(dataSource: DataSource, resourceLoader: ResourceLoader) =
        SqlSessionFactoryBean().apply {
            setDataSource(dataSource)
            setConfigLocation(resourceLoader.getResource("classpath:mybatis-config.xml"))
            setMapperLocations(
                *PathMatchingResourcePatternResolver()
                    .getResources("classpath:ru/csdeep/**/*.xml")
            )
        }
}
