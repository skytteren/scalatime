package no.skytteren.scalatime

import java.time.ZoneOffset

import org.scalatest.funsuite.AnyFunSuite

class DateTimePimpTest extends AnyFunSuite {

  test("DateTime now") {
    val dateTime = DateTime.now()
    val jDateTime = dateTime.toJavaLocalDateTime
    assert(dateTime.year.value === jDateTime.getYear)
    assert(dateTime.month.value === jDateTime.getMonthValue)
    assert(dateTime.dayOfMonth.value === jDateTime.getDayOfMonth)
  }
  test("DateTime epoch") {
    val date = DateTime.now()
    val jDate = date.toJavaLocalDateTime
    assert(date.toEpochSecond.value === jDate.toEpochSecond(ZoneOffset.UTC))
  }

}
