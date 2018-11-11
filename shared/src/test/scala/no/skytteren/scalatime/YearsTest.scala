package no.skytteren.scalatime

import org.scalatest.FunSuite

class YearsTest extends FunSuite {

  import Years.numeric._

  test("Months must be numeric") {
    assert(Years(15) === Years(10) + Years(5))
  }


}
