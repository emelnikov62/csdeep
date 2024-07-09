package ru.csdeep.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Security settings.
 * @author Evgenii Melnikov
 */
@ConfigurationProperties(prefix = "csdeep.security")
class WebSecuritySetting {
    /** Common settings for any security subsection.  */
    open class BaseSetting {
        /**
         * Returns enabled.
         * @return enabled
         */
        /**
         * Sets enabled.
         * @param enabled enabled
         */
        var isEnabled = true
    }

    /** hold .user-database.  */
    class UserDatabaseSetting : BaseSetting()

    /**
     * Returns userDatabase.
     * @return userDatabase
     */
    private var _userDatabase: UserDatabaseSetting? = null
    val userDatabase: UserDatabaseSetting
        get() = if (_userDatabase == null) UserDatabaseSetting().also { _userDatabase = it } else _userDatabase!!
}
