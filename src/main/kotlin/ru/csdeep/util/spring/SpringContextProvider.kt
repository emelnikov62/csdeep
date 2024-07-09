package ru.csdeep.util.spring

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Service

/**
 * Spring application context provider.
 * @author Evgenii Melnikov
 */
@Service
class SpringContextProvider : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringContextHolder.setApplicationContext(applicationContext)
    }
}
