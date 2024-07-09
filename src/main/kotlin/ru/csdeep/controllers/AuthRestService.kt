package ru.csdeep.controllers

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import ru.csdeep.model.UserInformation
import ru.csdeep.security.jwt.JwtUtils
import ru.csdeep.security.model.JwtResponse
import ru.csdeep.security.model.LoginRequest
import ru.csdeep.security.services.UserService
import java.util.stream.Collectors
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Auth Rest Controller.
 * @author Evgenii Melnikov
 */

@Path("/auth")
@Service
@Suppress("TooGenericExceptionCaught")
class AuthRestService(
    val authenticationManager: AuthenticationManager,
    val jwtUtils: JwtUtils,
    val userService: UserService
) {
    /**
     * Authenticate User.
     */
    @POST
    @Path("/sign-in")
    @Produces(MediaType.APPLICATION_JSON)
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): JwtResponse {
        var result: JwtResponse
        try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest!!.username, loginRequest.password)
            )
            SecurityContextHolder.getContext().authentication = authentication
            val jwt = jwtUtils.generateJwtToken(authentication)
            val userDetails = authentication.principal as UserDetails
            val roles = userDetails.authorities.stream()
                .map { item: GrantedAuthority -> item.authority }
                .collect(Collectors.toList())
            val userInformation = userService.getCurrentLoginInfo()

            result = JwtResponse(
                jwt,
                userDetails.username,
                "",
                roles,
                "",
                0,
                userInformation
            )
        } catch (ex: Exception) {
            result = JwtResponse(
                "",
                "",
                "",
                null,
                ex.message,
                1,
                null
            )
        }
        return result
    }

    /**
     * Login info.
     */
    @GET
    @Path("/login-info")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLoginInfo(): UserInformation = userService.getCurrentLoginInfo()

}
