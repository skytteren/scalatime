package no.skytteren.scalatime

import scala.util.Try


case class Hour(value: Int) {
  require( value >= 0 && value < 24, s"Hour must be in the range [0, 23], but was $value")
}
case class Minute(value: Int) {
  require( value >= 0 && value < 60, s"Minute must be in the range [0, 59], but was $value")
}
case class Second(value: Int) {
  require( value >= 0 && value < 60, s"Second must be in the range [0, 59], but was $value")
}
case class Millisecond(value: Int) {
  require( value >= 0 && value < 1000, s"Millisecond must be in the range [0, 999], but was $value")
}

case class Time(hour: Hour = Hour(0), minute: Minute = Minute(0), second: Second = Second(0), millisecond: Millisecond = Millisecond(0)){

  def +(duration: Duration): Time = {
    import duration._
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

  def +(hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)): Time = {
    plusOverflow(hours, minutes, seconds, milliseconds)._2
  }

  def -(hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)): Time = {

    this + (-hours, -minutes, -seconds, -milliseconds)
  }

  def -(duration: Duration): Time = {
    import duration._
    this - (hours, minutes, seconds, milliseconds)
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
}