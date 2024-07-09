package ru.csdeep.controllers

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import ru.csdeep.model.ResponseRest
import ru.csdeep.model.User
import ru.csdeep.security.services.UserService
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * User controller
 * @author Evgenii Melnikov
 */
@Path("/user")
@Service
class UserRestService(
    val userService: UserService
) {
    /**
     * User modify.
     */
    @POST
    @Path("/modify")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun modify(@RequestBody user: User): ResponseRest {
        var updated = userService.updateUserProfile(user)

        if (updated) {
            return ResponseRest(true, "Операция выполнена успешно")
        } else {
            return ResponseRest(false, "Ошибка выполнения операции")
        }
    }
}
