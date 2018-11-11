package no.skytteren.scalatime

trait DateTimeFormat {
  def format(local: DateTime): String
}

object DateTimeFormat{
  implicit case object ISO8601DateTimeFormat extends DateTimeFormat{
    override def format(dateTime: DateTime): String = {
      val date = DateFormat.ISO8601DateFormat.format(dateTime.toDate)
      val time = TimeFormat.ISO8601TimeFormat.format(dateTime.toTime)
      s"${date}T$time"
    }
  }
}

trait ZonedDateTimeFormat {
  def format(local: ZonedDateTime): String
}

object ZonedDateTimeFormat{
  implicit case object ISO8601ZonedDateTimeFormat extends ZonedDateTimeFormat{
    override def format(dateTime: ZonedDateTime): String = {
      val date = DateFormat.ISO8601DateFormat.format(dateTime.toDate)
      val time = TimeFormat.ISO8601TimeFormat.format(dateTime.toTime)
      val offset =
        if (dateTime.offset == Offset.Z) "Z"
        else if (dateTime.offset.hours.value < 0) f"-${-dateTime.offset.hours.value}%02d:${dateTime.offset.minutes.value}%02d"
        else f"${dateTime.offset.hours.value}%02d:${dateTime.offset.minutes.value}%02d"

      s"${date}T$time$offset"
    }
  }
}

trait DateFormat {
  def format(date: Date): String
}

object DateFormat{
  implicit case object ISO8601DateFormat extends DateFormat{
    override def format(date: Date): String = {
      f"${date.year.value}%04d-${date.month.value}%02d-${date.dayOfMonth.value}%02d"
    }
  }
}

trait TimeFormat {
  def format(time: Time): String
}

object TimeFormat{
  implicit case object ISO8601TimeFormat extends TimeFormat{
    override def format(time: Time): String = {
      val milliseconds = if(time.millisecond.value == 0 ) "" else f".${time.millisecond.value}%03d"
      val seconds = if(time.millisecond.value == 0 && time.second.value == 0) "" else f":${time.second.value}%02d"
      f"${time.hour.value}%02d:${time.minute.value}%02d$seconds$milliseconds"
    }
  }
}

trait DurationFormat {
  def format(duration: Duration): String
}

object DurationFormat {
  implicit case object ISO8601DurationFormat extends DurationFormat{
    override def format(duration: Duration): String = {
      List(
        Option(duration.years.value).filter(_ != 0).map(y => f"$y%04dY"),
        Option(duration.months.value).filter(_ != 0).map(m => f"$m%02dM"),
        Option(duration.days.value).filter(_ != 0).map(d => f"$d%02dD"),
        Option(List(
          Option(duration.hours.value).filter(_ != 0).map(h => f"$h%02dH"),
          Option(duration.minutes.value).filter(_ != 0).map(m => f"$m%02dM"),
          Option((duration.seconds.value, duration.milliseconds.value))
            .filter(t => t._1 != 0 && t._2 != 0)
            .map{case (s, m) =>
              f"$s%02d${if(s != 0) f".$m%03d" else ""}S"
            },
        ).flatten.mkString("T", "", "")).filter(_ != "T")
      ).flatten.mkString("P", "", "")
    }
  }
}
