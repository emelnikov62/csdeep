package ru.csdeep.security.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.csdeep.config.WebSecuritySetting
import ru.csdeep.dao.AuthorityDao
import ru.csdeep.dao.UserDao
import ru.csdeep.model.User
import ru.csdeep.model.UserInformation

/**
 * User service.
 */
@Service
@EnableConfigurationProperties(WebSecuritySetting::class)
class UserService(
    val userDao: UserDao,
    val authorityDao: AuthorityDao,
    @Qualifier("csdeep.security-ru.csdeep.config.WebSecuritySetting")
    val setting: WebSecuritySetting
) {
    companion object {
        val LOG: Logger = LoggerFactory.getLogger(UserService::class.java)
    }

    /**
     * Get login user info (current).
     */
    fun getCurrentLoginInfo(login: String?): UserInformation =
        getUserInfo(login.also { if (LOG.isDebugEnabled) LOG.debug("Get login information for $it") }!!)

    /**
     * Get login user info (current).
     */
    fun getCurrentLoginInfo(): UserInformation =
        getCurrentLoginInfo(getCurrentUserProfile())

    /**
     * Get user ingo by profile name.
     */
    fun getUserInfo(userProfile: String): UserInformation {
        return UserInformation(
            user = userDao.getUser(userProfile, null),
            userAuthority = authorityDao.geAuthoritiesByUser(userProfile).let {
                if (it.isNotEmpty()) it else null
            }
        )
    }

    /**
     * Check user exists
     * @param userProfile userProfile
     */
    fun checkUserExists(userProfile: String): Boolean {
        var user: User? = userDao.getUser(userProfile, null)
        return user != null
    }

    /**
     * Create user by [userProfile, idPlace].
     */
    @Suppress("TooGenericExceptionCaught")
    fun createUser(userProfile: String, idPlace: Long): Boolean {
        try {
            userDao.createUser(userProfile)
            return true
        } catch (e: Exception) {
            LOG.error(e.message)
            return false
        }
    }

    /**
     * Get current user profile (type of tUser.UserProfile).
     */
    fun getCurrentUserProfile(): String? {
        SecurityContextHolder.getContext()?.authentication?.let {
            return it.name.lowercase()
        }
        return null
    }

    /**
     * Update user profile by [user].
     */
    @Suppress("TooGenericExceptionCaught")
    fun updateUserProfile(user: User): Boolean {
        try {
            userDao.updateUser(user)
            return true
        } catch (e: Exception) {
            LOG.error(e.message)
            return false
        }
    }
}
