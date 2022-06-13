package no.skytteren

import java.time.LocalDateTime

package object scalatime {

  implicit class TimePimp(from: Time) {
    def toJavaLocalTime: java.time.LocalTime = java.time.LocalTime.of(from.hour.value.toInt, from.minute.value, from.second.value.toInt, from.millisecond.value.toInt * 1000000)
  }
  implicit class TimeObjectPimp(time: Time.type) {
    def now(): Time = java.time.LocalTime.now().toTime
  }

  implicit class DatePimp(date: Date){
    def toJavaLocalDate: java.time.LocalDate = java.time.LocalDate.of(date.year.value.toInt, date.month.value.toInt, date.dayOfMonth.value.toInt)
  }
  implicit class DateObjectPimp(date: Date.type) {
    def now(): Date = {
      val jDate = java.time.LocalDate.now()
      new Date(Year(jDate.getYear), Month(jDate.getMonthValue), DayOfMonth(jDate.getDayOfMonth))
    }
  }

  implicit class DateTimePimp(datetime: DateTime) {
    def toJavaLocalDateTime: LocalDateTime = {
      LocalDateTime.of(
        datetime.year.value.toInt, datetime.month.value.toInt, datetime.dayOfMonth.value.toInt,
        datetime.hour.value.toInt, datetime.minute.value, datetime.second.value, datetime.millisecond.value * 1000000
      )
    }
  }
  implicit class DateTimeObjectPimp(dateTime: DateTime.type) {
    def now(): DateTime = LocalDateTime.now().toDateTime
  }

  implicit class LocalTimePimp(in: java.time.LocalTime) {
    def toTime: Time = Time(Hour(in.getHour), Minute(in.getMinute), Second(in.getSecond), Millisecond(in.getNano / 1000000))
  }

  implicit class LocalDatePimp(in: java.time.LocalDate) {
    def toDate: Date = Date(
        Year(in.getYear), Month(in.getMonthValue), DayOfMonth(in.getDayOfMonth),
    )
  }

  implicit class LocalDateTimePimp(in: LocalDateTime) {
    def toDateTime: DateTime = DateTime(
        Year(in.getYear), Month(in.getMonthValue), DayOfMonth(in.getDayOfMonth),
        Hour(in.getHour), Minute(in.getMinute), Second(in.getSecond), Millisecond(in.getNano / 1000000)
    )
  }

}
