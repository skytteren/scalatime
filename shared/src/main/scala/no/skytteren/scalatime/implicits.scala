package no.skytteren.scalatime

object implicits {

  implicit class LongDuration(val l: Long) extends AnyVal {
    def years: Years = Years(l)
    def months: Months = Months(l)
    def days: Days = Days(l)
    def weeks: Duration = Duration(days = (l*7).days)
    def hours: Hours = Hours(l)
    def minutes: Minutes = Minutes(l)
    def seconds: Seconds = Seconds(l)
    def milliseconds: Milliseconds = Milliseconds(l)
  }

  implicit class IntDuration(val i: Int) extends AnyVal {
    def years: Years = Years(i)
    def months: Months = Months(i)
    def days: Days = Days(i)
    def weeks: Duration = Duration(days = (i*7).days)
    def hours: Hours = Hours(i)
    def minutes: Minutes = Minutes(i)
    def seconds: Seconds = Seconds(i)
    def milliseconds: Milliseconds = Milliseconds(i)
  }

  implicit class YearsPimp(val theseYears: Years) extends AnyVal {
    def + (years: Years): Years = Years(years.value + theseYears.value)
    def + (months: Months): Duration = Duration(months = months, years = theseYears)
    def + (days: Days): Duration = Duration(days = days, years = theseYears)
    def + (hours: Hours): Duration = Duration(hours = hours, years = theseYears)
    def + (minutes: Minutes): Duration = Duration(minutes = minutes, years = theseYears)
    def + (seconds: Seconds): Duration = Duration(seconds = seconds, years = theseYears)
    def + (milliseconds: Milliseconds): Duration = Duration(milliseconds = milliseconds, years = theseYears)
    def + (duration: Duration): Duration = duration.copy(years = duration.years + theseYears)
    def - (years: Years): Years = Years(theseYears.value - years.value)
    def - (months: Months): Duration = Duration(months = -months, years = theseYears)
    def - (days: Days): Duration = Duration(days = -days, years = theseYears)
    def - (hours: Hours): Duration = Duration(hours = -hours, years = theseYears)
    def - (minutes: Minutes): Duration = Duration(minutes = -minutes, years = theseYears)
    def - (seconds: Seconds): Duration = Duration(seconds = -seconds, years = theseYears)
    def - (milliseconds: Milliseconds): Duration = Duration(milliseconds = -milliseconds, years = theseYears)
    def duration: Duration = Duration(years = theseYears)
  }

  implicit class MonthsPimp(val theseMonths: Months) extends AnyVal {
    def + (years: Years): Duration = Duration(years = years, months = theseMonths)
    def + (months: Months): Months = Months(months.value + theseMonths.value)
    def + (days: Days): Duration = Duration(days = days, months = theseMonths)
    def + (hours: Hours): Duration = Duration(hours = hours, months = theseMonths)
    def + (minutes: Minutes): Duration = Duration(minutes = minutes, months = theseMonths)
    def + (seconds: Seconds): Duration = Duration(seconds = seconds, months = theseMonths)
    def + (milliseconds: Milliseconds): Duration = Duration(milliseconds = milliseconds, months = theseMonths)
    def + (duration: Duration): Duration = duration.copy(months = duration.months + theseMonths)
    def - (years: Years): Duration = Duration(years = -years, months = theseMonths)
    def - (months: Months): Months = Months(theseMonths.value - months.value)
    def - (days: Days): Duration = Duration(days = -days, months = theseMonths)
    def - (hours: Hours): Duration = Duration(hours = -hours, months = theseMonths)
    def - (minutes: Minutes): Duration = Duration(minutes = -minutes, months = theseMonths)
    def - (seconds: Seconds): Duration = Duration(seconds = -seconds, months = theseMonths)
    def - (milliseconds: Milliseconds): Duration = Duration(milliseconds = -milliseconds, months = theseMonths)
    def duration: Duration = Duration(months = theseMonths)
  }

  implicit class DaysPimp(val theseDays: Days) extends AnyVal {
    def + (years: Years): Duration = Duration(years = years, days = theseDays)
    def + (months: Months): Duration = Duration(months = months, days = theseDays)
    def + (days: Days): Days = Days(theseDays.value + days.value)
    def + (hours: Hours): Duration = Duration(hours = hours, days = theseDays)
    def + (minutes: Minutes): Duration = Duration(minutes = minutes, days = theseDays)
    def + (seconds: Seconds): Duration = Duration(seconds = seconds, days = theseDays)
    def + (milliseconds: Milliseconds): Duration = Duration(milliseconds = milliseconds, days = theseDays)
    def + (duration: Duration): Duration = duration.copy(days = duration.days + theseDays)
    def - (years: Years): Duration = Duration(years = -years, days = theseDays)
    def - (months: Months): Duration = Duration(months = -months, days = theseDays)
    def - (days: Days): Days = Days(theseDays.value - days.value)
    def - (hours: Hours): Duration = Duration(hours = -hours, days = theseDays)
    def - (minutes: Minutes): Duration = Duration(minutes = -minutes, days = theseDays)
    def - (seconds: Seconds): Duration = Duration(seconds = -seconds, days = theseDays)
    def - (milliseconds: Milliseconds): Duration = Duration(milliseconds = -milliseconds, days = theseDays)
    def duration: Duration = Duration(days = theseDays)
  }

  implicit class HoursPimp(val theseHours: Hours) extends AnyVal {
    def + (years: Years): Duration = Duration(years = years, hours = theseHours)
    def + (months: Months): Duration = Duration(months = months, hours = theseHours)
    def + (days: Days): Duration = Duration(days = days, hours = theseHours)
    def + (hours: Hours): Hours = Hours(hours.value + theseHours.value)
    def + (minutes: Minutes): Duration = Duration(minutes = minutes, hours = theseHours)
    def + (seconds: Seconds): Duration = Duration(seconds = seconds, hours = theseHours)
    def + (milliseconds: Milliseconds): Duration = Duration(milliseconds = milliseconds, hours = theseHours)
    def + (duration: Duration): Duration = duration.copy(hours = duration.hours + theseHours)
    def - (years: Years): Duration = Duration(years = -years, hours = theseHours)
    def - (months: Months): Duration = Duration(months = -months, hours = theseHours)
    def - (days: Days): Duration = Duration(days = -days, hours = theseHours)
    def - (hours: Hours): Hours = Hours(theseHours.value - hours.value)
    def - (minutes: Minutes): Duration = Duration(minutes = -minutes, hours = theseHours)
    def - (seconds: Seconds): Duration = Duration(seconds = -seconds, hours = theseHours)
    def - (milliseconds: Milliseconds): Duration = Duration(milliseconds = -milliseconds, hours = theseHours)
    def duration: Duration = Duration(hours = theseHours)
  }

  implicit class MinutesPimp(val theseMinutes: Minutes) extends AnyVal {
    def + (years: Years): Duration = Duration(years = years, minutes= theseMinutes)
    def + (months: Months): Duration = Duration(months = months, minutes= theseMinutes)
    def + (days: Days): Duration = Duration(days = days, minutes= theseMinutes)
    def + (hours: Hours): Duration = Duration(hours = hours, minutes= theseMinutes)
    def + (minutes: Minutes): Minutes = Minutes(minutes.value + theseMinutes.value)
    def + (seconds: Seconds): Duration = Duration(seconds = seconds, minutes = theseMinutes)
    def + (milliseconds: Milliseconds): Duration = Duration(milliseconds = milliseconds, minutes= theseMinutes)
    def + (duration: Duration): Duration = duration.copy(minutes = duration.minutes + theseMinutes)
    def - (years: Years): Duration = Duration(years = -years, minutes = theseMinutes)
    def - (months: Months): Duration = Duration(months = -months, minutes = theseMinutes)
    def - (days: Days): Duration = Duration(days = -days, minutes = theseMinutes)
    def - (hours: Hours): Duration = Duration(hours = -hours, minutes = theseMinutes)
    def - (minutes: Minutes): Minutes = Minutes(theseMinutes.value - minutes.value)
    def - (seconds: Seconds): Duration = Duration(seconds = -seconds, minutes  = theseMinutes)
    def - (milliseconds: Milliseconds): Duration = Duration(milliseconds = -milliseconds, minutes = theseMinutes)
    def duration: Duration = Duration(minutes = theseMinutes)
  }

  implicit class SecondsPimp(val theseSeconds: Seconds) extends AnyVal {
    def + (years: Years): Duration = Duration(years = years, seconds = theseSeconds)
    def + (months: Months): Duration = Duration(months = months, seconds = theseSeconds)
    def + (days: Days): Duration = Duration(days = days, seconds = theseSeconds)
    def + (hours: Hours): Duration = Duration(hours = hours, seconds = theseSeconds)
    def + (minutes: Minutes): Duration = Duration(minutes = minutes, seconds = theseSeconds)
    def + (seconds: Seconds): Seconds = Seconds(seconds.value + theseSeconds.value)
    def + (milliseconds: Milliseconds): Duration = Duration(milliseconds = milliseconds, seconds = theseSeconds)
    def + (duration: Duration): Duration = duration.copy(seconds = duration.seconds + theseSeconds)
    def - (years: Years): Duration = Duration(years = -years, seconds = theseSeconds)
    def - (months: Months): Duration = Duration(months = -months, seconds = theseSeconds)
    def - (days: Days): Duration = Duration(days = -days, seconds = theseSeconds)
    def - (hours: Hours): Duration = Duration(hours = -hours, seconds = theseSeconds)
    def - (minutes: Minutes): Duration = Duration(minutes = -minutes, seconds = theseSeconds)
    def - (seconds: Seconds): Seconds = Seconds(theseSeconds.value - seconds.value)
    def - (milliseconds: Milliseconds): Duration = Duration(milliseconds = -milliseconds, seconds = theseSeconds)
    def duration: Duration = Duration(seconds = theseSeconds)
  }

  implicit class MillisecondsPimp(val theseMilliseconds: Milliseconds) extends AnyVal {
    def + (years: Years): Duration = Duration(years = years, milliseconds = theseMilliseconds)
    def + (months: Months): Duration = Duration(months = months, milliseconds = theseMilliseconds)
    def + (days: Days): Duration = Duration(days = days, milliseconds = theseMilliseconds)
    def + (hours: Hours): Duration = Duration(hours = hours, milliseconds = theseMilliseconds)
    def + (minutes: Minutes): Duration = Duration(minutes = minutes, milliseconds = theseMilliseconds)
    def + (seconds: Seconds): Duration = Duration(seconds = seconds, milliseconds = theseMilliseconds)
    def + (milliseconds: Milliseconds): Milliseconds = Milliseconds(milliseconds.value + theseMilliseconds.value)
    def + (duration: Duration): Duration = duration.copy(milliseconds = duration.milliseconds + theseMilliseconds)
    def - (years: Years): Duration = Duration(years = -years, milliseconds = theseMilliseconds)
    def - (months: Months): Duration = Duration(months = -months, milliseconds = theseMilliseconds)
    def - (days: Days): Duration = Duration(days = -days, milliseconds = theseMilliseconds)
    def - (hours: Hours): Duration = Duration(hours = -hours, milliseconds = theseMilliseconds)
    def - (minutes: Minutes): Duration = Duration(minutes = -minutes, milliseconds = theseMilliseconds)
    def - (seconds: Seconds): Duration = Duration(seconds = -seconds, milliseconds = theseMilliseconds)
    def - (milliseconds: Milliseconds): Milliseconds = Milliseconds(theseMilliseconds.value - milliseconds.value)
    def duration: Duration = Duration(milliseconds = theseMilliseconds)
  }

  implicit class DurationPimp(val thisDuration: Duration) extends AnyVal {
    def + (years: Years): Duration = thisDuration.copy(years = thisDuration.years + years)
    def + (months: Months): Duration = thisDuration.copy(months = thisDuration.months + months)
    def + (days: Days): Duration = thisDuration.copy(days = thisDuration.days + days)
    def + (hours: Hours): Duration = thisDuration.copy(hours = thisDuration.hours + hours)
    def + (minutes: Minutes): Duration = thisDuration.copy(minutes = thisDuration.minutes + minutes)
    def + (seconds: Seconds): Duration = thisDuration.copy(seconds = thisDuration.seconds + seconds)
    def + (milliseconds: Milliseconds): Duration = thisDuration.copy(milliseconds = thisDuration.milliseconds + milliseconds)
    def + (duration: Duration): Duration = Duration(
      duration.years + thisDuration.years,
      duration.months + thisDuration.months,
      duration.days + thisDuration.days,
      duration.hours + thisDuration.hours,
      duration.minutes + thisDuration.minutes,
      duration.seconds + thisDuration.seconds,
      duration.milliseconds + thisDuration.milliseconds,
    )
    def - (years: Years): Duration = thisDuration.copy(years = thisDuration.years - years)
    def - (months: Months): Duration = thisDuration.copy(months = thisDuration.months - months)
    def - (days: Days): Duration = thisDuration.copy(days = thisDuration.days - days)
    def - (hours: Hours): Duration = thisDuration.copy(hours = thisDuration.hours - hours)
    def - (minutes: Minutes): Duration = thisDuration.copy(minutes = thisDuration.minutes - minutes)
    def - (seconds: Seconds): Duration = thisDuration.copy(seconds = thisDuration.seconds - seconds)
    def - (milliseconds: Milliseconds): Duration = thisDuration.copy(milliseconds = thisDuration.milliseconds - milliseconds)
    def - (duration: Duration): Duration = Duration(
      thisDuration.years - duration.years,
      thisDuration.months - duration.months,
      thisDuration.days - duration.days,
      thisDuration.hours - duration.hours,
      thisDuration.minutes - duration.minutes,
      thisDuration.seconds - duration.seconds,
      thisDuration.milliseconds - duration.milliseconds,
    )
  }

}
