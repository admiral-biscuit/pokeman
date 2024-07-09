package types

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import errors.NotANaturalNumberError

@JvmInline
value class NaturalNumber private constructor(val value: Int) {
  companion object {
    fun from(integer: Int): Either<NotANaturalNumberError, NaturalNumber> =
      if (integer > 0) {
        NaturalNumber(integer).right()
      } else {
        NotANaturalNumberError(integer).left()
      }
  }
}

fun Int.toNaturalNumberEither(): Either<NotANaturalNumberError, NaturalNumber> =
  NaturalNumber.from(this)
