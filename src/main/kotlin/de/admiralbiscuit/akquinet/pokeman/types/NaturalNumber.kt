package de.admiralbiscuit.akquinet.pokeman.types

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import de.admiralbiscuit.akquinet.pokeman.errors.NotANaturalNumberError

@JvmInline
value class NaturalNumber private constructor(val value: Int) {
  companion object {
    fun fromInt(integer: Int): Either<NotANaturalNumberError, NaturalNumber> =
      if (integer > 0) {
        NaturalNumber(integer).right()
      } else {
        NotANaturalNumberError(integer).left()
      }
  }
}

fun Int.toNaturalNumberEither(): Either<NotANaturalNumberError, NaturalNumber> =
  NaturalNumber.fromInt(this)
