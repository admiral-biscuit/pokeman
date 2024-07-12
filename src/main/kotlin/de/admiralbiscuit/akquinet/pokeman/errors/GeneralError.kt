package de.admiralbiscuit.akquinet.pokeman.errors

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.getOrElse

// This is the interface that all errors should implement.
// For simplicity, we just assume a "message" field.

interface GeneralError {
  val message: String
}

fun NonEmptyList<GeneralError>.toSingleGeneralError(): GeneralError =
  object : GeneralError {
    override val message: String
      get() =
        when (size) {
          1 -> single().message
          else ->
            "There were multiple issues.\n" + joinToString(separator = "\n") { "- ${it.message}" }
        }
  }

fun <R> Either<GeneralError, R>.getOrThrow(): R = getOrElse { error ->
  throw Exception(error.message)
}
