package ru.csdeep.dao

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import ru.csdeep.model.Authority

/**
 * Authority DAO.
 * @author Evgenii Melnikov
 */
@Mapper
@Suppress("EmptyClassBlock", "NoEmptyClassBody")
interface AuthorityDao {

    /**
     * Get user authorities by [user].
     */
    fun geAuthoritiesByUser(@Param("profile") profile: String): Array<Authority>
}
