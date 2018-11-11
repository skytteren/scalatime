package no.skytteren.scalatime

import org.scalatest.FunSuite

class DayOfWeekTest extends FunSuite {

  test("DayOfWeek must be between 1 and 7") {
    for(
      i <- 1 until 7
    ) {
      Month(i)
    }
    assertThrows[DayOfWeek.NotFound](DayOfWeek(0))
    assertThrows[DayOfWeek.NotFound](DayOfWeek(8))
  }

}
