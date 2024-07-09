package ru.csdeep.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

/**
 * Data sources configuration.
 * @author Evgenii Melnikov
 */
@SpringBootConfiguration
class DataSourceConfig {

    /**
     * Data source config.
     */
    @Bean
    @ConfigurationProperties("csdeep.datasource")
    @Primary
    fun csdeepDataSource(): DataSource =
        DataSourceBuilder.create()
            .driverClassName(org.postgresql.Driver::class.qualifiedName)
            .build()

    /**
     * Transaction manager by [csdeepDataSource].
     */
    @Bean
    @Primary
    fun transactionManager(csdeepDataSource: DataSource) = DataSourceTransactionManager(csdeepDataSource)
}
