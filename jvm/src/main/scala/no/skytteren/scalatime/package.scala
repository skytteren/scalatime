package no.skytteren

package object scalatime {

  implicit class DatePimp(date: Date.type) {
    def now(): Date = {
      val jDate = java.time.LocalDate.now()
      new Date(Year(jDate.getYear), Month(jDate.getMonthValue), DayOfMonth(jDate.getDayOfMonth))
    }
  }

  implicit class TimePimp(time: Time.type) {
    def now(): Time = {
      val jTime = java.time.LocalTime.now()
      new Time(Hour(jTime.getHour), Minute(jTime.getMinute), Second(jTime.getSecond), Millisecond(jTime.getNano / 1000000))
    }
  }
}
