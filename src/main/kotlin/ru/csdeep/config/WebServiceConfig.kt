package ru.csdeep.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.cxf.Bus
import org.apache.cxf.endpoint.Server
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.csdeep.controllers.*
import ru.csdeep.util.ExceptionMapper
import java.util.*

/**
 * Web services configuration.
 * @author Evgenii Melnikov
 */
@Configuration
class WebServiceConfig {
    @Autowired
    private val bus: Bus? = null

    /**
     * Get Jackson staff.
     */
    @Bean
    fun getJacksonJsonProvider() = JacksonJsonProvider(
        ObjectMapper().apply {
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            registerModule(JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, false)

            registerModule(
                KotlinModule.Builder()
                    .configure(KotlinFeature.NullIsSameAsDefault, true)
                    .build()
            )
            setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE)
            setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
        }
    )

    /**
     * Create server end point.
     * @return server
     */
    @Bean
    @Suppress("LongParameterList")
    fun apiServer(
        jacksonJsonProvider: JacksonJsonProvider,
        exceptionMapper: ExceptionMapper,
        authRestService: AuthRestService,
        configRestService: ConfigRestService,
        userRestService: UserRestService
    ): Server? {
        val providers: MutableList<Any> = ArrayList()
        providers.add(jacksonJsonProvider)
        providers.add(exceptionMapper)
        val endpoint = JAXRSServerFactoryBean()
        endpoint.providers = providers
        endpoint.bus = bus
        endpoint.address = "/api"
        endpoint.setServiceBeans(
            Arrays.asList(
                authRestService,
                configRestService,
                userRestService
            ) as List<Any>?
        )
        return endpoint.create()
    }
}
