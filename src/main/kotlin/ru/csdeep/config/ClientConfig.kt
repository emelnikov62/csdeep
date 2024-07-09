package ru.csdeep.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Portal configuration in genetral.
 * @author Evgenii Melnikov
 */
@Configuration
@EnableConfigurationProperties(ClientSetting::class)
class ClientConfig constructor(
    @Qualifier("csdeep.client-ru.csdeep.config.ClientSetting") val portalSetting: ClientSetting
) {
    init {
        if (portalSetting.apiUrl == null)
            portalSetting.apiUrl = portalSetting.apiUrl?.replaceAfterLast('/', "csdeep")
    }
}
