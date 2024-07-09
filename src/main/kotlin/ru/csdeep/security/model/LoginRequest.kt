package ru.csdeep.security.model

import javax.validation.constraints.NotBlank

/**
 * LoginRequest.
 * @author Evgenii Melnikov
 */
class LoginRequest {
    var username: @NotBlank String? = null
    var password: @NotBlank String? = null
}
