package ru.csdeep.util.mybatis

/**
 * Holder of return value. Used as reference parameter to get return from db function direct call.
 * @param <T> type of return value
 * @author Evgenii Melnikov
 */
data class InOutValue<T>(var value: T? = null)
