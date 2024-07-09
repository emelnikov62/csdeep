package ru.csdeep.model

import java.util.*

/**
 * Error message.
 * @author Evgenii Melnikov
 */

data class ErrorMessage(
    val timestamp: Date,
    val status: Int,
    val error: String,
    val exception: String,
    val trace: String,
    val message: String,
    val path: String
)
