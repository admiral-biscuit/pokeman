package de.admiralbiscuit.akquinet.pokeman

import de.admiralbiscuit.akquinet.pokeman.errors.NotANaturalNumberError
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import de.admiralbiscuit.akquinet.pokeman.types.NaturalNumber

class NaturalNumberTest :
  DescribeSpec({
    it("is valid") { NaturalNumber.eitherFromInt(9).shouldBeRight() }

    it("is not valid") {
      NaturalNumber.eitherFromInt(0) shouldBeLeft NotANaturalNumberError(0)
      NaturalNumber.eitherFromInt(-9) shouldBeLeft NotANaturalNumberError(-9)
    }
  })
