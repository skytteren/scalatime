package no.skytteren.scalatime

import org.scalatest.funsuite.AnyFunSuite
import implicits._
import Ordered._

class DaysTest extends AnyFunSuite {

  test("Days must add") {
    assert(15.days === 10.days + 5.days)
  }

  test("Days must subtract") {
    assert(5.days === 10.days - 5.days)
  }

  test("Days is weeks") {
    assert(Duration(days = 35.days) === 5.weeks)
  }

  test("Days ordering") {
    assert(35.days >= 5.days)
  }

}
