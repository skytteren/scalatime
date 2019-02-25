package no.skytteren.scalatime

import org.scalatest.{FunSpec, FunSuite}

class DateTest extends FunSuite{

  test("Date leap year") {
    assert(Date(Year(1600), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!Date(Year(1700), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!Date(Year(1800), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!Date(Year(1900), Month(1), DayOfMonth(1)).isLeapYear)
    assert(Date(Year(1996), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!Date(Year(1999), Month(1), DayOfMonth(1)).isLeapYear)
    assert(Date(Year(2000), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!Date(Year(2001), Month(1), DayOfMonth(1)).isLeapYear)
    assert(Date(Year(2004), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!Date(Year(2100), Month(1), DayOfMonth(1)).isLeapYear)
  }

  test("Date add nothing") {
    val date = Date(Year(1900), Month(1), DayOfMonth(1))
    assert(date === date + ())
  }

  test("Date remove nothing") {
    val date = Date(Year(1900), Month(1), DayOfMonth(1))
    assert(date === date - ())
  }

  test("Date add year") {
    val date = Date(Year(1900), Month(1), DayOfMonth(1)) + (years = Years(1))
    assert(date === Date(Year(1901), Month(1), DayOfMonth(1)))
  }

  test("Date remove year") {
    val date = Date(Year(2000), Month(1), DayOfMonth(1)) - (years = Years(1))
    assert(date === Date(Year(1999), Month(1), DayOfMonth(1)))
  }

  test("Date add month") {
    val date = Date(Year(1900), Month(1), DayOfMonth(1)) + (months = Months(1))
    assert(date === Date(Year(1900), Month(2), DayOfMonth(1)))
  }

  test("Date remove month") {
    val date = Date(Year(2000), Month(1), DayOfMonth(1)) - (months = Months(1))
    assert(date === Date(Year(1999), Month(12), DayOfMonth(1)))
  }

  test("Date add days") {
    val date = Date(Year(1900), Month(1), DayOfMonth(1)) + (days = Days(41))
    assert(date === Date(Year(1900), Month(2), DayOfMonth(11)))
  }

  test("Date remove days") {
    val date = Date(Year(2000), Month(1), DayOfMonth(1)) - (days = Days(41))
    assert(date === Date(Year(1999), Month(11), DayOfMonth(21)))
  }

  test("Date days conversion") {
    for{
      i <- 0L.until(100000000, 12345)
    } {
      val date = Date.toDate(Days(i))
      assert(Days(i) === Date.tupled.apply(date).toDays)
    }
  }

  test("day of week") {
    val start = Date(Year(2018), Month(11), DayOfMonth(12))
    for{
      i <- 0 until 1000
    } {
      val date = start + (days = Days(i))
      assert(date.dayOfWeek === DayOfWeek(i % 7 + 1))
    }
  }

  test("date format") {
    val dateformat = Date(Year(2018), Month(11), DayOfMonth(9)).format
    assert(dateformat === "2018-11-09")
  }

  test("Parse date") {
    val date = Date.parse("2018-11-09").get
    assert(date === Date(Year(2018), Month(11), DayOfMonth(9)))
  }

  test("Parse date - compact") {
    val date = Date.parse("20181109").get
    assert(date === Date(Year(2018), Month(11), DayOfMonth(9)))
  }

  test("Date compare") {
    import Ordering.Implicits._
    val start = Date(Year(2018), Month(11), DayOfMonth(8))
    assert(start < Date(Year(2018), Month(11), DayOfMonth(9)))
  }

}
