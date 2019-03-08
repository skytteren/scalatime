package no.skytteren

package object scalatime {

  implicit class DatePimp(date: Date.type) {
    def now(): Date = {
      val jDate = new scalajs.js.Date()
      Date(Year(jDate.getFullYear()), Month(jDate.getMonth() + 1), DayOfMonth(jDate.getDate()))
    }
  }

  implicit class TimePimp(time: Time.type) {
    def now(): Time = {
      val jTime = new scalajs.js.Date()
      new Time(Hour(jTime.getHours), Minute(jTime.getMinutes), Second(jTime.getSeconds), Millisecond(jTime.getMilliseconds()))
    }
  }

  implicit class DateTimePimp(dateTime: DateTime.type) {
    def now(): DateTime = {
      val jDateTime = new scalajs.js.Date()
      new DateTime(
        Year(jDateTime.getFullYear()), Month(jDateTime.getMonth() + 1), DayOfMonth(jDateTime.getDate()),
        Hour(jDateTime.getHours), Minute(jDateTime.getMinutes), Second(jDateTime.getSeconds), Millisecond(jDateTime.getMilliseconds())
      )
    }
  }
}
