package no.skytteren.scalatime

import org.scalatest.FunSuite

class MonthsTest extends FunSuite {

  import Months.numeric._

  test("Months must be numeric") {
    assert(Months(15) === Months(10) + Months(5))
  }


}
