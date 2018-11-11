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
}
