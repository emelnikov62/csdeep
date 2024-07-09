package ru.csdeep.util.mybatis

import org.apache.commons.lang3.StringUtils
import java.sql.Connection
import java.util.*

/**
 * Context utility used by ditect access to connection.
 * Service version is [ru.masterdm.orkmzo.service.DBContext]
 * @author Evgenii Melnikov
 */
object DBContextDirect {
    val EMPTY_BINARY = StringUtils.EMPTY.toByteArray()
}

/**
 * Set context hex representation into db server.
 */
fun setContext(connection: Connection, userNameHex: String, clientHostHex: String) {
    connection.createStatement().execute("SET CONTEXT_INFO 0x${userNameHex}23$clientHostHex") // kotlin uses StringBuilder for template strings
}

/**
 * Set user and host context elements into db server.
 */
fun setUserAndHost(connection: Connection, userName: String?, clientHost: String?) {
    setContext(
        connection,
        HexFormat.of().formatHex(userName?.toByteArray() ?: DBContextDirect.EMPTY_BINARY),
        HexFormat.of().formatHex(clientHost?.toByteArray() ?: DBContextDirect.EMPTY_BINARY)
    )
}

/**
 * Clear context information at db server.
 */
fun clearUserAndHost(connection: Connection) {
    connection.createStatement().execute("SET CONTEXT_INFO 0x")
}
