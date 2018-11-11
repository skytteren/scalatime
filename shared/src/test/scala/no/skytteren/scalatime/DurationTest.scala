package no.skytteren.scalatime

import org.scalatest.FunSuite

class DurationTest extends FunSuite{

  test("Duration ") {
    assert(Duration.days(5) === Duration(days = Days(5)))
  }


  test("duration format") {
    val dateformat = Duration(Years(2018), Months(11), Days(9)).format
    assert(dateformat === "P2018Y11M09D")
  }
}
