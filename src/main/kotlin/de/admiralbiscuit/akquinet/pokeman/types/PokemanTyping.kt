// Private constructors for data classes don't really make sense at the moment,
// since the .copy() method is always publicly accessible.
// AFAIK, this will be fixed in a future Kotlin version.

@file:Suppress("DataClassPrivateConstructor")

package de.admiralbiscuit.akquinet.pokeman.types

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import de.admiralbiscuit.akquinet.pokeman.errors.PokemanTypingError

// A Pokeman typing is invalid if it consists of distinct types.
private fun <R : PokemanTyping> ensureDistinctTypes(
  vararg type: PokemanType,
  f: () -> R,
): Either<PokemanTypingError.DuplicateTypes, R> =
  if (type.distinct().size == type.size) {
    f().right()
  } else {
    PokemanTypingError.DuplicateTypes.left()
  }

sealed interface PokemanTyping {
  companion object {
    // for deserialization
    fun eitherFromList(types: List<PokemanType>): Either<PokemanTypingError, PokemanTyping> =
      either {
        when (types.size) {
          1 -> SingleTyping(types[0])
          2 -> DualTyping.eitherFrom(types[0], types[1]).bind()
          3 -> TripleTyping.eitherFrom(types[0], types[1], types[2]).bind()
          else -> raise(PokemanTypingError.InvalidNumberOfTypes(types.size))
        }
      }

    fun eitherFrom(vararg type: PokemanType): Either<PokemanTypingError, PokemanTyping> =
      eitherFromList(type.toList())
  }

  // for serialization
  fun toPokemanTypeList(): List<PokemanType> =
    when (this) {
      is SingleTyping -> listOf(type)
      is DualTyping -> listOf(firstType, secondType)
      is TripleTyping -> listOf(firstType, secondType, thirdType)
    }

  fun joinToString(): String = toPokemanTypeList().joinToString(" / ") { it.string }
}

fun List<PokemanType>.toPokemanTypingEither(): Either<PokemanTypingError, PokemanTyping> =
  PokemanTyping.eitherFromList(this)

data class SingleTyping(val type: PokemanType) : PokemanTyping

data class DualTyping private constructor(val firstType: PokemanType, val secondType: PokemanType) :
  PokemanTyping {
  companion object {
    fun eitherFrom(
      firstType: PokemanType,
      secondType: PokemanType,
    ): Either<PokemanTypingError.DuplicateTypes, DualTyping> =
      ensureDistinctTypes(firstType, secondType) { DualTyping(firstType, secondType) }
  }
}

data class TripleTyping
private constructor(
  val firstType: PokemanType,
  val secondType: PokemanType,
  val thirdType: PokemanType,
) : PokemanTyping {
  companion object {
    fun eitherFrom(
      firstType: PokemanType,
      secondType: PokemanType,
      thirdType: PokemanType,
    ): Either<PokemanTypingError.DuplicateTypes, TripleTyping> =
      ensureDistinctTypes(firstType, secondType, thirdType) {
        TripleTyping(firstType, secondType, thirdType)
      }
  }
}
