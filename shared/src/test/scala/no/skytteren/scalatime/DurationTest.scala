package no.skytteren.scalatime

import org.scalatest.FunSuite
import implicits._

class DurationTest extends FunSuite{

  test("Duration ") {
    assert(5.days.duration === Duration(days = Days(5)))
  }


  test("duration format") {
    val dateformat = Duration(Years(2018), Months(11), Days(9)).format
    assert(dateformat === "P2018Y11M09D")
  }
}
