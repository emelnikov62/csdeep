package ru.csdeep.dao

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import ru.csdeep.model.User

/**
 * User DAO.
 * @author Evgenii Melnikov
 */
@Mapper
@Suppress("EmptyClassBlock", "NoEmptyClassBody")
interface UserDao {

    /**
     * Get user info by [profile, password].
     */
    fun getUser(@Param("profile") profile: String, @Param("password") password: String?): User

    /**
     * Create user by [profile].
     */
    fun createUser(@Param("profile") profile: String): Long

    /**
     * Update user by [user].
     */
    fun updateUser(@Param("user") user: User)
}
