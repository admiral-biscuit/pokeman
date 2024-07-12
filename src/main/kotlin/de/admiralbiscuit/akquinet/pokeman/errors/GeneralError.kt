package de.admiralbiscuit.akquinet.pokeman.errors

import arrow.core.Either
import arrow.core.getOrElse

// This is the interface that all errors should implement.
// For simplicity, we just assume a "message" field.

interface GeneralError {
  val message: String
}

fun <R> Either<GeneralError, R>.getOrThrow(): R = getOrElse { error ->
  throw Exception(error.message)
}
