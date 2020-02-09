package no.skytteren.scalatime

import scala.util.Try
import implicits._

trait DateTimeDuration[T]{
  def duration(t: T): (Years, Months, Days, Hours, Minutes, Seconds, Milliseconds)
}

object DateTimeDuration{
  implicit def dateDuration[T: DateDuration]: DateTimeDuration[T] = (t: T) => {
    val (years, months, days) = implicitly[DateDuration[T]].duration(t)
    (years, months, days, Hours(0), Minutes(0), Seconds(0), Milliseconds(0))
  }
  implicit def timeDuration[T: TimeDuration]: DateTimeDuration[T] = (t: T) => {
    val (hours, minutes, seconds, milliseconds) = implicitly[TimeDuration[T]].duration(t)
    (Years(0), Months(0), Days(0), hours, minutes, seconds, milliseconds)
  }
  implicit def durationDuration: DateTimeDuration[Duration] = (d: Duration) => {
      import d._
      (years, months, days, hours, minutes, seconds, milliseconds)
    }

}

case class DateTime(year: Year = Year(0), month: Month = Month(1), dayOfMonth: DayOfMonth = DayOfMonth(1),
                    hour: Hour = Hour(0), minute: Minute = Minute(0), second: Second = Second(0), millisecond: Millisecond = Millisecond(0)) {
  def isLeapYear: Boolean = year.isLeapYear

  def format(implicit formater: DateTimeFormat): String = formater.format(this)

  def +[D: DateTimeDuration](duration: D): DateTime = {
    val (years, months, days, hours, minutes, seconds, milliseconds) = implicitly[DateTimeDuration[D]].duration(duration)
    this + (years, months, days, hours, minutes, seconds, milliseconds)
  }

  private[scalatime] def +(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0),
        hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)
       ): DateTime = {

    val (extraDays, Time(hour, minute, second, millisecond)) = this.toTime plusOverflow (hours, minutes, seconds, milliseconds)
    val Date(year, month, dayOfMonth) = this.toDate + (years, months, days + extraDays)

    DateTime(year, month, dayOfMonth, hour, minute, second, millisecond)
  }


  private[scalatime] def -(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0),
        hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)): DateTime = {
    this + (-years, -months, -days, -hours, -minutes, -seconds, -milliseconds)
  }

  def -[D: DateTimeDuration](duration: D): DateTime = {
    val (years, months, days, hours, minutes, seconds, milliseconds) = implicitly[DateTimeDuration[D]].duration(duration)
    this - (years, months, days, hours, minutes, seconds, milliseconds)
  }

  def < (to: DateTime): Boolean = Ordering[DateTime].lt(this, to)
  def <= (to: DateTime): Boolean = Ordering[DateTime].lteq(this, to)
  def > (to: DateTime): Boolean = Ordering[DateTime].gt(this, to)
  def >= (to: DateTime): Boolean = Ordering[DateTime].gteq(this, to)

  def year(year: Year): DateTime = copy(year = year)
  def month(month: Month): DateTime = copy(month = month)
  def dayOfMonth(dayOfMonth: DayOfMonth): DateTime = copy(dayOfMonth = dayOfMonth)
  def hour(hour: Hour): DateTime = copy(hour = hour)
  def minute(minute: Minute): DateTime = copy(minute = minute)
  def second(second: Second): DateTime = copy(second = second)
  def millisecond(millisecond: Millisecond): DateTime = copy(millisecond = millisecond)

  def toDate: Date = Date(year, month, dayOfMonth)
  def toTime: Time = Time(hour, minute, second, millisecond)

  def toEpochSecond(implicit offset: Offset = Offset.Z): Seconds = {
    ZonedDateTime(this, offset).toEpochSecond
  }

}

object DateTime extends ((Year, Month, DayOfMonth, Hour, Minute, Second, Millisecond) => DateTime) {

  def apply(date: Date, time: Time): DateTime = new DateTime(date.year, date.month, date.dayOfMonth, time.hour, time.minute, time.second, time.millisecond)

  def parse(in: String)(implicit dateTimeParser: DateTimeParser) : Try[DateTime] = dateTimeParser.parse(in)

  def MAX: DateTime = DateTime(Date.MAX, Time.MAX)
  def MIN: DateTime =  DateTime(Date.MIN, Time.MIN)

  implicit val datatimeOrdering: Ordering[DateTime] = Ordering.by(dateTime => dateTime.toEpochSecond.value * 1000 + dateTime.millisecond.value)

}
