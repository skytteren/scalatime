package no.skytteren.scalatime

import scala.util.Try

case class DateTime(year: Year = Year(0), month: Month = Month(1), dayOfMonth: DayOfMonth = DayOfMonth(1),
                    hour: Hour = Hour(0), minute: Minute = Minute(0), second: Second = Second(0), millisecond: Millisecond = Millisecond(0)) {
  def isLeapYear: Boolean = year.isLeapYear

  def format(implicit formater: DateTimeFormat): String = formater.format(this)

  def +(duration: Duration): DateTime = {
    import duration._
    this + (years, months, days, hours, minutes, seconds, milliseconds)
  }

  def +(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0),
        hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)
       ): DateTime = {
    import Days.numericDays._

    val (extraDays, Time(hour, minute, second, millisecond)) = this.toTime plusOverflow (hours, minutes, seconds, milliseconds)
    val Date(year, month, dayOfMonth) = this.toDate + (years, months, days + extraDays)

    DateTime(year, month, dayOfMonth, hour, minute, second, millisecond)
  }


  def -(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0),
        hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)): DateTime = {
    this + (-years, -months, -days, -hours, -minutes, -seconds, -milliseconds)
  }

  def -(duration: Duration): DateTime = {
    import duration._
    this - (years, months, days, hours, minutes, seconds, milliseconds)
  }

  def isBefore(other: DateTime) = {

  }

  def year(year: Year): DateTime = copy(year = year)
  def month(month: Month): DateTime = copy(month = month)
  def dayOfMonth(dayOfMonth: DayOfMonth): DateTime = copy(dayOfMonth = dayOfMonth)
  def hour(hour: Hour): DateTime = copy(hour = hour)
  def minute(minute: Minute): DateTime = copy(minute = minute)
  def second(second: Second): DateTime = copy(second = second)
  def millisecond(millisecond: Millisecond): DateTime = copy(millisecond = millisecond)

  def toDate: Date = Date(year, month, dayOfMonth)
  def toTime: Time = Time(hour, minute, second, millisecond)

}

object DateTime extends ((Year, Month, DayOfMonth, Hour, Minute, Second, Millisecond) => DateTime) {

  def apply(date: Date, time: Time): DateTime = new DateTime(date.year, date.month, date.dayOfMonth, time.hour, time.minute, time.second, time.millisecond)

  def parse(in: String)(implicit dateTimeParser: DateTimeParser) : Try[DateTime] = dateTimeParser.parse(in)

}
