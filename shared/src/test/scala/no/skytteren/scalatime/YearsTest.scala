package no.skytteren.scalatime

import org.scalatest.funsuite.AnyFunSuite
import implicits._

class YearsTest extends AnyFunSuite {

  test("Years must add") {
    assert(15.years === 10.years + 5.years)
  }

  test("Years must subtract") {
    assert(5.years === 10.years - 5.years)
  }

  test("Years must compare") {
    assert(10.years >= 5.years)
  }


}
