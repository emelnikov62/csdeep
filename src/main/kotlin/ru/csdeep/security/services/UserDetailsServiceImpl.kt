package ru.csdeep.security.services

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.csdeep.security.services.UserDetailsImpl.Companion.build
import java.util.*

/**
 * User Details Service.
 * @author Evgenii Melnikov
 */
@Service
class UserDetailsServiceImpl(
    val userService: UserService
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userInformation = userService!!.getCurrentLoginInfo(username)
        return build(userInformation)
    }
}
