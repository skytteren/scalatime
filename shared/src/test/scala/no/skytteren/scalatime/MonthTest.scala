package no.skytteren.scalatime

import no.skytteren.scalatime.Month.NotFound
import org.scalatest.FunSuite

class MonthTest extends FunSuite {

  test("Month must be between 1 and 12") {
    for(
      i <- 1 until 12
    ) {
      Month(i)
    }
    assertThrows[NotFound](Month(0))
    assertThrows[NotFound](Month(13))
  }

  test("Month next") {
    for(
      i <- 1 until 12
    ) {
      if(i == 12)
        assert(Month(1) === Month(i).next)
      else
        assert(Month(i + 1) === Month(i).next)
    }
  }

  test( "all months") {
    val year = Year(2018)
    assert(Month.all.map(_.daysInMonth(year)).sum === Days(365))

  }

}
