package no.skytteren.scalatime

import org.scalatest.FunSuite

class DatePimpTest extends FunSuite {

  test("Date now") {
    val date = Date.now()
    val jDate = java.time.LocalDate.now()
    assert(date.year.value === jDate.getYear)
    assert(date.month.value === jDate.getMonthValue)
    assert(date.dayOfMonth.value === jDate.getDayOfMonth)
  }

}
