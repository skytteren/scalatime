package no.skytteren.scalatime

import org.scalatest.FunSuite

class DateTimeTest extends FunSuite{

  test("Date leap year") {
    assert(DateTime(Year(1600), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!DateTime(Year(1700), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!DateTime(Year(1800), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!DateTime(Year(1900), Month(1), DayOfMonth(1)).isLeapYear)
    assert(DateTime(Year(1996), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!DateTime(Year(1999), Month(1), DayOfMonth(1)).isLeapYear)
    assert(DateTime(Year(2000), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!DateTime(Year(2001), Month(1), DayOfMonth(1)).isLeapYear)
    assert(DateTime(Year(2004), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!DateTime(Year(2100), Month(1), DayOfMonth(1)).isLeapYear)
  }

  test("DateTime add year") {
    val date = DateTime(Year(1900), Month(1), DayOfMonth(1)) + Years(1)
    assert(date === DateTime(Year(1901), Month(1), DayOfMonth(1)))
  }

  test("DateTime remove year") {
    val date = DateTime(Year(2000), Month(1), DayOfMonth(1)) - Years(1)
    assert(date === DateTime(Year(1999), Month(1), DayOfMonth(1)))
  }

  test("DateTime add month") {
    val date = DateTime(Year(1900), Month(1), DayOfMonth(1)) + Months(1)
    assert(date === DateTime(Year(1900), Month(2), DayOfMonth(1)))
  }

  test("DateTime remove month") {
    val date = DateTime(Year(2000), Month(1), DayOfMonth(1)) - Months(1)
    assert(date === DateTime(Year(1999), Month(12), DayOfMonth(1)))
  }

  test("DateTime add days") {
    val date = DateTime(Year(1900), Month(1), DayOfMonth(1)) + Days(41)
    assert(date === DateTime(Year(1900), Month(2), DayOfMonth(11)))
  }

  test("DateTime remove days") {
    val date = DateTime(Year(2000), Month(1), DayOfMonth(1)) - Days(41)
    assert(date === DateTime(Year(1999), Month(11), DayOfMonth(21)))
  }

  test("DateTime plus milliseconds") {
    val time = DateTime(millisecond = Millisecond(999))
    val newTime = time + (milliseconds = Milliseconds(10))
    assert(newTime.second === Second(1))
    assert(newTime.millisecond === Millisecond(9))
  }

  test("DateTime plus lots of milliseconds") {
    val time = DateTime(millisecond = Millisecond(0))
    val newTime = time + (milliseconds = Milliseconds(1000000))
    assert(newTime.hour === Hour(0))
    assert(newTime.minute === Minute(16))
    assert(newTime.second === Second(40))
    assert(newTime.millisecond === Millisecond(0))
  }

  test("DateTime plus seconds") {
    val time = DateTime(second = Second(59))
    val newTime = time + (seconds = Seconds(10))
    assert(newTime.minute === Minute(1))
    assert(newTime.second === Second(9))
  }

  test("DateTime plus minutes") {
    val time = DateTime(minute = Minute(59))
    val newTime = time + (minutes = Minutes(10))
    assert(newTime.hour === Hour(1))
    assert(newTime.minute === Minute(9))
  }

  test("DateTime plus hours") {
    val time = DateTime(hour = Hour(12))
    val newTime = time + (hours = Hours(12))
    assert(newTime.hour === Hour(0))
  }

  test("format") {
    val dateformat = DateTime(Year(2018), Month(11), DayOfMonth(9)).format
    assert(dateformat === "2018-11-09T00:00")
  }

  test("Parse datetime") {
    val date = DateTime.parse("20181109T12:13:14.678").get
    assert(date === DateTime(Year(2018), Month(11), DayOfMonth(9), Hour(12), Minute(13), Second(14), Millisecond(678)))
  }

}
