package no.skytteren.scalatime

import org.scalatest.FunSuite

class DayOfMonthTest extends FunSuite {

  test("DayOfMonth must be between 1 and 31") {
    for(
      i <- 1 until 31
    ) {
      DayOfMonth(i)
    }
    assertThrows[IllegalArgumentException](DayOfMonth(0))
    assertThrows[IllegalArgumentException](DayOfMonth(32))
  }

}
