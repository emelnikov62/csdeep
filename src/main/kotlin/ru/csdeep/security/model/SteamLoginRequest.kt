package ru.csdeep.security.model

import javax.validation.constraints.NotBlank

/**
 * LoginRequest.
 * @author Evgenii Melnikov
 */
class SteamLoginRequest {
    var ns: @NotBlank String? = null
    var mode: @NotBlank String? = null
    var op_endpoint: @NotBlank String? = null
    var claimed_id: @NotBlank String? = null
    var identity: @NotBlank String? = null
    var return_to: @NotBlank String? = null
    var response_nonce: @NotBlank String? = null
    var assoc_handle: @NotBlank String? = null
    var signed: @NotBlank String? = null
    var sig: @NotBlank String? = null
}
