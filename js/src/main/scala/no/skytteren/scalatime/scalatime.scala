package no.skytteren

package object scalatime {

  implicit class DatePimp(date: Date.type) {
    def now(): Date = {
      val jDate = new scalajs.js.Date()
      Date(Year(jDate.getFullYear().toLong), Month(jDate.getMonth().toShort + 1), DayOfMonth(jDate.getDate().toShort))
    }
  }

  implicit class TimePimp(time: Time.type) {
    def now(): Time = {
      val jTime = new scalajs.js.Date()
      new Time(Hour(jTime.getHours.toShort), Minute(jTime.getMinutes.toShort), Second(jTime.getSeconds.toShort), Millisecond(jTime.getMilliseconds().toShort))
    }
  }

  implicit class DateTimePimp(dateTime: DateTime.type) {
    def now(): DateTime = {
      val jDateTime = new scalajs.js.Date()
      new DateTime(
        Year(jDateTime.getFullYear().toLong), Month(jDateTime.getMonth().toShort + 1), DayOfMonth(jDateTime.getDate().toShort),
        Hour(jDateTime.getHours.toShort), Minute(jDateTime.getMinutes.toShort), Second(jDateTime.getSeconds.toShort), Millisecond(jDateTime.getMilliseconds().toShort)
      )
    }
  }
}
