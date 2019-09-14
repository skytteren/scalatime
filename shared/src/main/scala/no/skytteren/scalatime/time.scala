package no.skytteren.scalatime

import scala.util.Try


case class Hour(value: Short) {
  require( value >= 0 && value < 24, s"Hour must be in the range [0, 23], but was $value")

  def < (to: Hour): Boolean = value < to.value
  def <= (to: Hour): Boolean = !(value > to.value)

  def > (to: Hour): Boolean = value > to.value
  def >= (to: Hour): Boolean = !(value < to.value)
}
object Hour extends (Short => Hour) {
  def apply(value: Int): Hour = new Hour(value.toShort)
  def apply(value: Long): Hour = new Hour(value.toShort)
}

case class Minute(value: Int) {
  require( value >= 0 && value < 60, s"Minute must be in the range [0, 59], but was $value")

  def < (to: Minute): Boolean = value < to.value
  def <= (to: Minute): Boolean = !(value > to.value)

  def > (to: Minute): Boolean = value > to.value
  def >= (to: Minute): Boolean = !(value < to.value)
}
object Minute extends (Short => Minute) {
  override def apply(value: Short): Minute = new Minute(value)
  def apply(value: Int): Minute = new Minute(value.toShort)
  def apply(value: Long): Minute = new Minute(value.toShort)
}

case class Second(value: Int) {
  require( value >= 0 && value < 60, s"Second must be in the range [0, 59], but was $value")

  def < (to: Second): Boolean = value < to.value
  def <= (to: Second): Boolean = !(value > to.value)
  def > (to: Second): Boolean = value > to.value
  def >= (to: Second): Boolean = !(value < to.value)
}
object Second extends (Short => Second) {
  override def apply(value: Short): Second = new Second(value)
  def apply(value: Int): Second = new Second(value.toShort)
  def apply(value: Long): Second = new Second(value.toShort)
}

case class Millisecond(value: Int) {
  require( value >= 0 && value < 1000, s"Millisecond must be in the range [0, 999], but was $value")

  def < (to: Millisecond): Boolean = value < to.value
  def <= (to: Millisecond): Boolean = !(value > to.value)

  def > (to: Millisecond): Boolean = value > to.value
  def >= (to: Millisecond): Boolean = !(value < to.value)
}
object Millisecond extends (Short => Millisecond) {
  override def apply(value: Short): Millisecond = new Millisecond(value)
  def apply(value: Int): Millisecond = new Millisecond(value.toShort)
  def apply(value: Long): Millisecond = new Millisecond(value.toShort)
}

trait TimeDuration[T]{
  def duration(t: T): (Hours, Minutes, Seconds, Milliseconds)
}

object TimeDuration{
  implicit object HoursDuration extends TimeDuration[Hours]{
    override def duration(hours: Hours): (Hours, Minutes, Seconds, Milliseconds) = (hours, Minutes(0), Seconds(0), Milliseconds(0))
  }
  implicit object MinutesDuration extends TimeDuration[Minutes]{
    override def duration(minutes: Minutes): (Hours, Minutes, Seconds, Milliseconds) = (Hours(0), minutes, Seconds(0), Milliseconds(0))
  }
  implicit object SecondsDuration extends TimeDuration[Seconds]{
    override def duration(seconds: Seconds): (Hours, Minutes, Seconds, Milliseconds) = (Hours(0), Minutes(0), seconds, Milliseconds(0))
  }
  implicit object MillisecondsDuration extends TimeDuration[Milliseconds]{
    override def duration(milliseconds: Milliseconds): (Hours, Minutes, Seconds, Milliseconds) = (Hours(0), Minutes(0), Seconds(0), milliseconds)
  }
  implicit object DurationDuration extends TimeDuration[Duration]{
    override def duration(d: Duration): (Hours, Minutes, Seconds, Milliseconds) = {
      import d._
      (hours, minutes, seconds, milliseconds)
    }
  }
}

case class Time(hour: Hour = Hour(0), minute: Minute = Minute(0), second: Second = Second(0), millisecond: Millisecond = Millisecond(0)){

  def +[D: TimeDuration](duration: D): Time = {
    val (hours, minutes, seconds, milliseconds) = implicitly[TimeDuration[D]].duration(duration)
    this + (hours, minutes, seconds, milliseconds)
  }

  private[scalatime] def plusOverflow(hours: Hours = Hours(0), minutes: Minutes = Minutes(0),
                                      seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)): (Days, Time) = {
    val newMillisecond = this.millisecond.value + milliseconds.value

    val newSecond = this.second.value + seconds.value + newMillisecond / 1000 + (if(newMillisecond < 0 ) -1 else 0)
    val newMinute = this.minute.value + minutes.value + newSecond / 60 + (if(newSecond < 0 ) -1 else 0)
    val newHour = this.hour.value + hours.value + newMinute / 60 + (if(newMinute < 0 ) -1 else 0)

    (
      Days(newHour / 24),
      Time(
        Hour((newHour + 24) % 24),
        Minute((newMinute + 60) % 60),
        Second((newSecond + 60) % 60),
        Millisecond((newMillisecond + 1000) % 1000)
      )
    )
  }

  private[scalatime] def +(hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)): Time = {
    plusOverflow(hours, minutes, seconds, milliseconds)._2
  }

  private[scalatime] def -(hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)): Time = {

    this + (-hours, -minutes, -seconds, -milliseconds)
  }

  def -[D: TimeDuration](duration: D): Time = {
    val (hours, minutes, seconds, milliseconds) = implicitly[TimeDuration[D]].duration(duration)
    this - (hours, minutes, seconds, milliseconds)
  }

  def < (to: Time): Boolean = {
    (hour < to.hour) || (
      (hour == to.hour) && (
        (minute < to.minute) || (
          (minute == to.minute) && (
            (second < to.second) || (
              (second == to.second) && (
                millisecond < to.millisecond
              )
            )
          )
        )
      )
    )
  }

  def hour(hour: Hour): Time = copy(hour = hour)
  def minute(minute: Minute): Time = copy(minute = minute)
  def second(second: Second): Time = copy(second = second)
  def millisecond(millisecond: Millisecond) = copy(millisecond = millisecond)

  def format(implicit formater: TimeFormat): String = formater.format(this)
}

object Time extends ((Hour, Minute, Second, Millisecond) => Time) {
  def startOfDay: Time = Time()

  def parse(in: String)(implicit timeParser: TimeParser): Try[Time] = timeParser.parse(in)

  def MAX = Time(hour = Hour(23), minute = Minute(59), second = Second(59), millisecond = Millisecond(999))
  def MIN = Time(hour = Hour(0), minute = Minute(0), second = Second(0), millisecond = Millisecond(0))
}