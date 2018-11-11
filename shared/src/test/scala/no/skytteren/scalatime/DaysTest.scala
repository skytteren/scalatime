package no.skytteren.scalatime

import org.scalatest.FunSuite

class DaysTest extends FunSuite {

  import Days.numeric._

  test("Days must be numeric") {
    assert(Days(15) === Days(10) + Days(5))
  }


}
