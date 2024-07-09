package ru.csdeep.security.model

import ru.csdeep.model.UserInformation

/**
 * JwtResponse.
 * @author Evgenii Melnikov
 */
@Suppress("LongParameterList")
class JwtResponse(
    var accessToken: String?,
    var username: String?,
    var email: String?,
    val roles: List<String>?,
    val message: String?,
    val code: Int,
    val userInformation: UserInformation?
) {
    var tokenType = "Bearer"
}
