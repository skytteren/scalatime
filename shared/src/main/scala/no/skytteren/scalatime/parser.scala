package no.skytteren.scalatime


import no.skytteren.scalatime.DateTimeParser.NotADateTimeException

import scala.util.{Failure, Try}

trait DateTimeParser {
  def parse(in: String): Try[DateTime]
}

object DateTimeParser {
  case class NotADateTimeException(pattern: String) extends Throwable{
    override def getMessage: String = s""""$pattern" can not be recognized as a datetime"""
  }

  implicit case object ISO8601 extends DateTimeParser {
    private val full = """(.+)T(.+)""".r
    override def parse(in: String): Try[DateTime] = in match {
      case full(dateString, timeString) =>
        val res = for {
          date <- DateParser.ISO8601.parse(dateString)
          time <- TimeParser.ISO8601.parse(timeString)
        } yield {
          DateTime(date, time)
        }
        res.recoverWith{case _ => Failure(NotADateTimeException(in))}
      case string => Failure(NotADateTimeException(string))
    }
  }
}

trait ZonedDateTimeParser {
  def parse(in: String): Try[ZonedDateTime]
}

object ZonedDateTimeParser {

  case class NotAZonedDateTimeException(pattern: String) extends Throwable{
    override def getMessage: String = s""""$pattern" can not be recognized as a zoneddatetime"""
  }

  implicit case object ISO8601 extends ZonedDateTimeParser {

    private val full = """(.+)T(.+)([+-]\d{2}):?(\d{2})""".r
    private val fullZ = """(.+)T(.+)Z""".r

    override def parse(in: String): Try[ZonedDateTime] = in match {
      case fullZ(dateString, timeString) =>
        val res = for {
          date <- DateParser.ISO8601.parse(dateString)
          time <- TimeParser.ISO8601.parse(timeString)
        } yield {
          ZonedDateTime(date, time, Offset.Z)
        }
        res.recoverWith{case _ => Failure(NotAZonedDateTimeException(in))}
      case full(dateString, timeString, offsetHour, offsetMinutes) =>
        val res = for {
          date <- DateParser.ISO8601.parse(dateString)
          time <- TimeParser.ISO8601.parse(timeString)
        } yield {
          ZonedDateTime(date, time, Offset(Hours(offsetHour.toInt), Minutes(offsetMinutes.toInt)))
        }
        res.recoverWith{case _ => Failure(NotAZonedDateTimeException(in))}
      case string => Failure(NotADateTimeException(string))
    }
  }
}

trait TimeParser {
  def parse(in: String): Try[Time]
}

object TimeParser {
  case class NotATimeException(pattern: String) extends Throwable{
    override def getMessage: String = s""""$pattern" can not be recognized as a time"""
  }
  implicit case object ISO8601 extends TimeParser {
    /**
      * hh:mm:ss.sss	or	hhmmss.sss
      * hh:mm:ss	or	hhmmss
      * hh:mm	or	hhmm
      * hh
      */
    private val full = """(\d{2}):(\d{2}):(\d{2}).(\d{3})""".r
    private val fullSimple = """(\d{2})(\d{2})(\d{2}).(\d{3})""".r
    private val seconds = """(\d{2}):(\d{2}):(\d{2})""".r
    private val secondsSimple = """(\d{2})(\d{2})(\d{2})""".r
    private val minutes = """(\d{2}):(\d{2})""".r
    private val minutesSimple = """(\d{2})(\d{2})""".r
    private val hours = """(\d{2})""".r
    override def parse(in: String): Try[Time] = in match {
      case full(hour, minute, second, millis) => Try{Time(Hour(hour.toInt), Minute(minute.toInt), Second(second.toInt), Millisecond(millis.toInt))}
      case fullSimple(hour, minute, second, millis) => Try{Time(Hour(hour.toInt), Minute(minute.toInt), Second(second.toInt), Millisecond(millis.toInt))}
      case seconds(hour, minute, second) => Try{Time(Hour(hour.toInt), Minute(minute.toInt), Second(second.toInt))}
      case secondsSimple(hour, minute, second) => Try{Time(Hour(hour.toInt), Minute(minute.toInt), Second(second.toInt))}
      case minutes(hour, minute) => Try{Time(Hour(hour.toInt), Minute(minute.toInt))}
      case minutesSimple(hour, minute) => Try{Time(Hour(hour.toInt), Minute(minute.toInt))}
      case hours(hour) => Try{Time(Hour(hour.toInt))}
      case string => Failure(NotATimeException(string))
    }
  }

}


trait DateParser {
  def parse(in: String): Try[Date]
}

object DateParser {
  case class NotADateException(pattern: String) extends Throwable{
    override def getMessage: String = s""""$pattern" can not be recognized as a date"""
  }
  implicit case object ISO8601 extends DateParser {
    private val regExp = """(\d{1,4})-(\d{1,2})-(\d{1,2})""".r
    private val regExpComact = """(\d{4})(\d{2})(\d{2})""".r
    override def parse(in: String): Try[Date] = in match {
      case regExp(year, month, dayOfMonth) => Try{Date(Year(year.toInt), Month(month.toInt), DayOfMonth(dayOfMonth.toInt))}
      case regExpComact(year, month, dayOfMonth) => Try{Date(Year(year.toInt), Month(month.toInt), DayOfMonth(dayOfMonth.toInt))}
      case string => Failure(NotADateException(string))
    }
  }

}

