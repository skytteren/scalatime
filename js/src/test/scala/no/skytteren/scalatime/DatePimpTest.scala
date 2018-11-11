package no.skytteren.scalatime

import org.scalatest.FunSuite

class DatePimpTest extends FunSuite {

  test("Date now") {
    val date = Date.now()
    val jDate = new scalajs.js.Date()
    assert(date.year.value === jDate.getFullYear())
    assert(date.month.value === jDate.getMonth() + 1)
    assert(date.dayOfMonth.value === jDate.getDate())
  }

}
