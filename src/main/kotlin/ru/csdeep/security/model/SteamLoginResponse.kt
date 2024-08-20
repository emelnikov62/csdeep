package ru.csdeep.security.model

import javax.validation.constraints.NotBlank

/**
 * LoginRequest.
 * @author Evgenii Melnikov
 */
class SteamLoginResponse {
    var success: @NotBlank String? = null
}
