package no.skytteren.scalatime

import org.scalatest.FunSuite

class ZonedDateTimeTest extends FunSuite{

  test("Date leap year") {
    assert(ZonedDateTime(Year(1600), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!ZonedDateTime(Year(1700), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!ZonedDateTime(Year(1800), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!ZonedDateTime(Year(1900), Month(1), DayOfMonth(1)).isLeapYear)
    assert(ZonedDateTime(Year(1996), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!ZonedDateTime(Year(1999), Month(1), DayOfMonth(1)).isLeapYear)
    assert(ZonedDateTime(Year(2000), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!ZonedDateTime(Year(2001), Month(1), DayOfMonth(1)).isLeapYear)
    assert(ZonedDateTime(Year(2004), Month(1), DayOfMonth(1)).isLeapYear)
    assert(!ZonedDateTime(Year(2100), Month(1), DayOfMonth(1)).isLeapYear)
  }

  test("ZonedDateTime add nothing") {
    val date = ZonedDateTime(Year(1900), Month(1), DayOfMonth(1))
    assert(date === date + ())
  }

  test("ZonedDateTime remove nothing") {
    val date = ZonedDateTime(Year(1900), Month(1), DayOfMonth(1))
    assert(date === date - ())
  }

  test("ZonedDateTime add year") {
    val date = ZonedDateTime(Year(1900), Month(1), DayOfMonth(1)) + (years = Years(1))
    assert(date === ZonedDateTime(Year(1901), Month(1), DayOfMonth(1)))
  }

  test("ZonedDateTime remove year") {
    val date = ZonedDateTime(Year(2000), Month(1), DayOfMonth(1)) - (years = Years(1))
    assert(date === ZonedDateTime(Year(1999), Month(1), DayOfMonth(1)))
  }

  test("ZonedDateTime add month") {
    val date = ZonedDateTime(Year(1900), Month(1), DayOfMonth(1)) + (months = Months(1))
    assert(date === ZonedDateTime(Year(1900), Month(2), DayOfMonth(1)))
  }

  test("ZonedDateTime remove month") {
    val date = ZonedDateTime(Year(2000), Month(1), DayOfMonth(1)) - (months = Months(1))
    assert(date === ZonedDateTime(Year(1999), Month(12), DayOfMonth(1)))
  }

  test("ZonedDateTime add days") {
    val date = ZonedDateTime(Year(1900), Month(1), DayOfMonth(1)) + (days = Days(41))
    assert(date === ZonedDateTime(Year(1900), Month(2), DayOfMonth(11)))
  }

  test("ZonedDateTime remove days") {
    val date = ZonedDateTime(Year(2000), Month(1), DayOfMonth(1)) - (days = Days(41))
    assert(date === ZonedDateTime(Year(1999), Month(11), DayOfMonth(21)))
  }

  test("ZonedDateTime plus milliseconds") {
    val time = ZonedDateTime(millisecond = Millisecond(999))
    val newTime = time + (milliseconds = Milliseconds(10))
    assert(newTime.second === Second(1))
    assert(newTime.millisecond === Millisecond(9))
  }

  test("ZonedDateTime plus lots of milliseconds") {
    val time = ZonedDateTime(millisecond = Millisecond(0))
    val newTime = time + (milliseconds = Milliseconds(1000000))
    assert(newTime.hour === Hour(0))
    assert(newTime.minute === Minute(16))
    assert(newTime.second === Second(40))
    assert(newTime.millisecond === Millisecond(0))
  }

  test("ZonedDateTime plus seconds") {
    val time = ZonedDateTime(second = Second(59))
    val newTime = time + (seconds = Seconds(10))
    assert(newTime.minute === Minute(1))
    assert(newTime.second === Second(9))
  }

  test("ZonedDateTime plus minutes") {
    val time = ZonedDateTime(minute = Minute(59))
    val newTime = time + (minutes = Minutes(10))
    assert(newTime.hour === Hour(1))
    assert(newTime.minute === Minute(9))
  }

  test("ZonedDateTime plus hours") {
    val time = ZonedDateTime(hour = Hour(12))
    val newTime = time + (hours = Hours(12))
    assert(newTime.hour === Hour(0))
  }

  test("Timezone / offset") {
    val time = ZonedDateTime(year = Year(2018), hour = Hour(12))
    val exected = ZonedDateTime(year = Year(2018), hour = Hour(13), offset = Offset(Hours(1)))
    assert(exected === time.in(Offset(Hours(1))))
  }

  test("Timezone / offset over date") {
    val time = ZonedDateTime(year = Year(2018), dayOfMonth = DayOfMonth(1), hour = Hour(23))
    val exected = ZonedDateTime(year = Year(2018), dayOfMonth = DayOfMonth(2), hour = Hour(0), offset = Offset(Hours(1)))
    assert(exected === time.in(Offset(Hours(1))))
  }


  test("format") {
    val dateformat = ZonedDateTime(Year(2018), Month(11), DayOfMonth(9), offset = Offset(Hours(-2))).format
    assert(dateformat === "2018-11-09T00:00-02:00")
  }

  test("Parse zoneddatetime Z") {
    val date = ZonedDateTime.parse("20181109T12:13:14.678Z").get
    assert(date === ZonedDateTime(Year(2018), Month(11), DayOfMonth(9), Hour(12), Minute(13), Second(14), Millisecond(678)))
  }

  test("Parse zoneddatetime - offset") {
    val date = ZonedDateTime.parse("20181109T12:13:14.678+10:00").get
    assert(date === ZonedDateTime(Year(2018), Month(11), DayOfMonth(9), Hour(12), Minute(13), Second(14), Millisecond(678), Offset(Hours(10))))
  }
  test("Parse zoneddatetime - minus offset") {
    val date = ZonedDateTime.parse("20181109T12:13:14.678-1000").get
    assert(date === ZonedDateTime(Year(2018), Month(11), DayOfMonth(9), Hour(12), Minute(13), Second(14), Millisecond(678), Offset(Hours(-10))))
  }

  test("epoch") {
    val date = ZonedDateTime.parse("20181109T12:13:14.678Z").get.millisecond(Millisecond(0))
    assert(date === ZonedDateTime.fromEpochSecond(date.toEpochSecond))
  }

}
