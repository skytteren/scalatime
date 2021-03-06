package no.skytteren.scalatime

import scala.util.Try

import implicits._

case class ZonedDateTime(year: Year = Year(0), month: Month = Month(1), dayOfMonth: DayOfMonth = DayOfMonth(1),
                    hour: Hour = Hour(0), minute: Minute = Minute(0), second: Second = Second(0), millisecond: Millisecond = Millisecond(0),
                       offset: Offset = Offset.Z) {

  def isLeapYear: Boolean = year.isLeapYear

  def format(implicit formater: ZonedDateTimeFormat): String = formater.format(this)

  def +(duration: Duration): ZonedDateTime = {
    import duration._
    this + (years, months, days, hours, minutes, seconds, milliseconds)
  }

  def +(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0),
        hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)
       ): ZonedDateTime = {

    val (extraDays, Time(hour, minute, second, millisecond)) = this.toTime plusOverflow (hours, minutes, seconds, milliseconds)
    val Date(year, month, dayOfMonth) = this.toDate + (years, months, days + extraDays)

    ZonedDateTime(year, month, dayOfMonth, hour, minute, second, millisecond, offset)
  }

  def -(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0),
        hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)): ZonedDateTime = {
    this + (-years, -months, -days, -hours, -minutes, -seconds, -milliseconds)
  }

  def -(duration: Duration): ZonedDateTime = {
    import duration._
    this - (years, months, days, hours, minutes, seconds, milliseconds)
  }

  def < (to: ZonedDateTime): Boolean = {
    (toDate < to.toDate) || ((toDate == to.toDate) && (toTime < to.toTime))
  }

  def <= (to: ZonedDateTime): Boolean = {
    this < to  || this == to
  }

  def > (to: ZonedDateTime): Boolean = {
    !(this < to || this == to)
  }

  def >= (to: ZonedDateTime): Boolean = {
    !(this < to)
  }

  def year(year: Year): ZonedDateTime = copy(year = year)
  def month(month: Month): ZonedDateTime = copy(month = month)
  def dayOfMonth(dayOfMonth: DayOfMonth): ZonedDateTime = copy(dayOfMonth = dayOfMonth)
  def hour(hour: Hour): ZonedDateTime = copy(hour = hour)
  def minute(minute: Minute): ZonedDateTime = copy(minute = minute)
  def second(second: Second): ZonedDateTime = copy(second = second)
  def millisecond(millisecond: Millisecond): ZonedDateTime = copy(millisecond = millisecond)
  def offset(offset: Offset): ZonedDateTime = copy(offset = offset)

  def in(offset: Offset) : ZonedDateTime = (this + (offset - this.offset)).offset(offset)

  def toDate: Date = Date(year, month, dayOfMonth)
  def toTime: Time = Time(hour, minute, second, millisecond)

  def toDateTime: DateTime = DateTime(year, month, dayOfMonth, hour, minute, second, millisecond)

  def toEpochSecond: Seconds = {
    val daysSeconds = (toDate.toDays - ZonedDateTime.epochStart.toDate.toDays).toSeconds
    val daySeconds = Seconds(hour.value * 60 * 60 + minute.value * 60 + second.value)
    daysSeconds + daySeconds
  }

}

object ZonedDateTime extends ((Year, Month, DayOfMonth, Hour, Minute, Second, Millisecond, Offset) => ZonedDateTime) {

  val epochStart = ZonedDateTime(Year(1970))

  def fromEpochSecond(seconds: Seconds): ZonedDateTime = {
    epochStart + (seconds = seconds)
  }

  def apply(date: Date, time: Time, offset: Offset): ZonedDateTime =
    new ZonedDateTime(date.year, date.month, date.dayOfMonth, time.hour, time.minute, time.second, time.millisecond, offset)
  def apply(dateTime: DateTime, offset: Offset): ZonedDateTime =
    new ZonedDateTime(dateTime.year, dateTime.month, dateTime.dayOfMonth, dateTime.hour, dateTime.minute, dateTime.second, dateTime.millisecond, offset)

  def parse(in: String)(implicit parser: ZonedDateTimeParser): Try[ZonedDateTime] = parser.parse(in)

}
