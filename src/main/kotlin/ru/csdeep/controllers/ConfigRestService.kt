package ru.csdeep.controllers

import org.springframework.stereotype.Service
import ru.csdeep.config.ClientConfig
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Config controller
 * @author Evgenii Melnikov
 */
@Path("/config")
@Service
class ConfigRestService(
    val clientConfig: ClientConfig
) {
    /**
     * GET application window background.
     */
    @GET
    @Path("/appWindowBackground")
    @Produces(MediaType.TEXT_PLAIN)
    fun getAppWindowBackground() = clientConfig.portalSetting.appWindowBackground

}
