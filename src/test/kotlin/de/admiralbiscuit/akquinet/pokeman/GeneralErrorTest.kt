package de.admiralbiscuit.akquinet.pokeman

import arrow.core.nel
import arrow.core.nonEmptyListOf
import de.admiralbiscuit.akquinet.pokeman.errors.GeneralError
import de.admiralbiscuit.akquinet.pokeman.errors.toSingleGeneralError
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

private fun generalError(message: String) =
  object : GeneralError {
    override val message: String
      get() = message
  }

class GeneralErrorTest :
  DescribeSpec({
    describe("Nel<GeneralError> to Generalerror") {
      it("wörks for Nel of size 1") {
        val errors = generalError("oof").nel()

        val singleError = errors.toSingleGeneralError()
        singleError.message shouldBe "oof"
      }

      it("wörks for Nel of size 2 or greater") {
        val error1 = generalError("oof")
        val error2 = generalError("foo")
        val errors = nonEmptyListOf(error1, error2)

        val singleError = errors.toSingleGeneralError()
        singleError.message.split("\n") shouldBe
          listOf("There were multiple issues.", "- oof", "- foo")
      }
    }
  })
