@file:Suppress("ArrayInDataClass")

package ru.csdeep.model

import ru.csdeep.model.Applications.*
import java.time.LocalDate

/**
 * User data object.
 * @author Evgenii Melnikov
 */
data class User(
    val id: Long? = null,
    val profile: String? = null,
    val password: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val lastname: String? = null,
    val active: Boolean? = null,
    val email: String? = null,
    val idClient: Long? = null,
    val ldts: LocalDate? = null,
    val phone: String? = null,
    val birth: LocalDate? = null,
    val foto: ByteArray? = null
)

/**
 * User authorisation (role) data object.
 */
data class Authority(
    val id: Long? = null,
    val code: String? = null
)

/**
 * User application authority.
 */
typealias ApplicationAuthority = Map<Applications, Boolean>

/**
 * Evaluate application authorities.
 */
fun applicationAuthority(user: UserInformation): ApplicationAuthority {
    return mapOf(
        CSDEEP_ADMIN_INFO to user.hasAnyRoleByCode("AD"),
        CSDEEP_ADMIN_SETTINGS to user.hasAnyRoleByCode("AD")
    )
}

/**
 * User complex data object.
 */
data class UserInformation(
    val user: User?,
    val userAuthority: Array<Authority>? = null
) {
    val applicationAuthority: ApplicationAuthority = applicationAuthority(this)

    /**
     * If user has indicated role.
     */
    fun hasRoleByCode(roleCode: String): Boolean {
        if (!userAuthority.isNullOrEmpty()) {
            return userAuthority.any { it.code == roleCode }
        }
        return false
    }

    /**
     * If user has one of indicated role at least.
     */
    fun hasAnyRoleByCode(vararg roleCodes: String): Boolean {
        for (roleCode in roleCodes) {
            if (hasRoleByCode(roleCode))
                return true
        }
        return false
    }
}
