package ru.csdeep.security.services

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.csdeep.model.UserInformation
import java.util.*
import java.util.stream.Collectors

/**
 * User Details.
 * @author Evgenii Melnikov
 */
class UserDetailsImpl(
    private val username: String,
    private val email: String?,
    @field:JsonIgnore private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null || javaClass != other.javaClass)
            return false
        val user = other as UserDetailsImpl
        return username == user.username
    }

    override fun hashCode(): Int {
        return Objects.hash(
            getAuthorities(), getPassword(), getUsername(), isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired,
            isEnabled
        )
    }

    companion object {
        private const val serialVersionUID = 1L

        /**
         * Build User Details.
         */
        @JvmStatic
        fun build(user: UserInformation): UserDetailsImpl {
            var authorities =
                if (user.userAuthority != null)
                    Arrays.stream(user.userAuthority)
                        .map {
                            SimpleGrantedAuthority(
                                it.code
                            )
                        }
                        .collect(Collectors.toList())
                else
                    emptyList<GrantedAuthority>()

            return UserDetailsImpl(
                user.user?.profile ?: "",
                user.user?.email ?: "",
                "{noop}" + user.user?.password,
                authorities
            )
        }
    }
}
