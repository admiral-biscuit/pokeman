package de.admiralbiscuit.akquinet.pokeman

import de.admiralbiscuit.akquinet.pokeman.errors.PokemanNameError
import de.admiralbiscuit.akquinet.pokeman.types.PokemanName
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.arrow.core.shouldHaveSize
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class PokemanNameTest :
  DescribeSpec({
    describe("valid names") {
      it("is a valid PokemanName") {
        val input = "Piggechu"

        val name = PokemanName.eitherFromString(input).shouldBeRight()
        name.value shouldBe "Piggechu"
      }

      it("uses a capitalised string") {
        val input = "pIgGeChU"

        val name = PokemanName.eitherFromString(input).shouldBeRight()
        name.value shouldBe "Piggechu"
      }
    }

    describe("invalid names") {
      it("does not match regex") {
        val input = "P1993chu"

        val error = PokemanName.eitherFromString(input).shouldBeLeft().single()
        error.shouldBeInstanceOf<PokemanNameError.InvalidRegex>()
      }

      it("is too long") {
        val input = "PiggechuPiggechuPiggechu"

        val error = PokemanName.eitherFromString(input).shouldBeLeft().single()
        error.shouldBeInstanceOf<PokemanNameError.TooLong>()
      }

      it("does not end with allowed suffix") {
        val input = "Squirtle"

        val error = PokemanName.eitherFromString(input).shouldBeLeft().single()
        error.shouldBeInstanceOf<PokemanNameError.InvalidSuffix>()
      }

      it("has multiple issues") {
        val input = "PiggechuPiggechuPiggechu2"

        val errors = PokemanName.eitherFromString(input).shouldBeLeft().shouldHaveSize(3)
        errors[0].shouldBeInstanceOf<PokemanNameError.InvalidRegex>()
        errors[1].shouldBeInstanceOf<PokemanNameError.TooLong>()
        errors[2].shouldBeInstanceOf<PokemanNameError.InvalidSuffix>()
      }

      it("is blank") {
        val input = "   "

        val errors = PokemanName.eitherFromString(input).shouldBeLeft().shouldHaveSize(3)
        errors[0].shouldBeInstanceOf<PokemanNameError.Blank>()
        errors[1].shouldBeInstanceOf<PokemanNameError.InvalidRegex>()
        errors[2].shouldBeInstanceOf<PokemanNameError.InvalidSuffix>()
      }
    }
  })
