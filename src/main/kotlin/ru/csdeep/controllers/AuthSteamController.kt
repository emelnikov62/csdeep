package ru.csdeep.controllers

import org.springframework.stereotype.Service
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Path("/steam")
@Service
@Suppress("TooGenericExceptionCaught")
class AuthSteamController() {
    @POST
    @Path("/set-steamid")
    @Produces(MediaType.APPLICATION_JSON)
    fun Any.setSteamID(): Any {
        return this
    }
}