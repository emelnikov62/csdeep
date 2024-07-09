package ru.csdeep.util

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import ru.csdeep.model.ErrorMessage
import java.util.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

/**
 * ExceptionMapper catch all exception and transformation to one format.
 * @author Evgenii Melnikov
 */

@Provider
@Component
class ExceptionMapper : ExceptionMapper<Throwable> {
    override fun toResponse(ex: Throwable): Response {
        val message = ErrorMessage(
            Date(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            ex.javaClass.name,
            ex.stackTraceToString(),
            ex.toString(), //javaClass.name + ": " ,//+ ex.originalMessage,
            "" //ex.pathReference
        )
        return Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).entity(message).type(MediaType.APPLICATION_JSON).build()
    }
}
