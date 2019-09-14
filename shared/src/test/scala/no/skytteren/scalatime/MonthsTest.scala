package no.skytteren.scalatime

import org.scalatest.FunSuite
import implicits._

class MonthsTest extends FunSuite {

  test("Months must add") {
    assert(15.months === 10.months + 5.months)
  }

  test("Months must subtract") {
    assert(5.months === 10.months - 5.months)
  }

}
