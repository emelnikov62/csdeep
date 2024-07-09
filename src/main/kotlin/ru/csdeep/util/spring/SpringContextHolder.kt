package ru.csdeep.util.spring

import org.springframework.context.ApplicationContext

/**
 * Spring application context holder.
 * @author Evgenii Melnikov
 */
object SpringContextHolder {
    private var applicationContext: ApplicationContext? = null

    /**
     * Sets spring application context.
     * @param applicationContext spring application context
     */
    fun setApplicationContext(applicationContext: ApplicationContext?) {
        SpringContextHolder.applicationContext = applicationContext
    }

    /**
     * Returns spring application context.
     * @return spring application context
     */
    fun getApplicationContext(): ApplicationContext? {
        return applicationContext
    }

    /**
     * Get named service.
     * @param serviceName name
     * @return service
     */
    fun getService(serviceName: String?): Any {
        return getApplicationContext()!!.getBean(serviceName!!)
    }

    /**
     * Get named service.
     * @param serviceName name
     * @param <T> type of service
     * @return service
    </T> */
    fun <T> getService(serviceName: Class<T>): T {
        return getApplicationContext()!!.getBean(serviceName)
    }
}
