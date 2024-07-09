package ru.csdeep.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Settings from config for csdeep.
 * @author Evgenii Melnikov
 */
@ConfigurationProperties(prefix = "csdeep.client")
class ClientSetting {
    var apiUrl: String? = null
    var appWindowBackground: String? = null
}
