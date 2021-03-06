package no.skytteren.scalatime

import org.scalatest.funsuite.AnyFunSuite
import implicits._

class TimeTest extends AnyFunSuite {

  test("Time plus milliseconds") {
    val time = Time(millisecond = Millisecond(999))
    val newTime = time + 10.milliseconds
    assert(newTime.second === Second(1))
    assert(newTime.millisecond === Millisecond(9))
  }

  test("Time plus lots of milliseconds") {
    val time = Time(millisecond = Millisecond(0))
    val newTime = time + 1000000.milliseconds
    assert(newTime.hour === Hour(0))
    assert(newTime.minute === Minute(16))
    assert(newTime.second === Second(40))
    assert(newTime.millisecond === Millisecond(0))
  }

  test("Time plus seconds") {
    val time = Time(second = Second(59))
    val newTime = time + 10.seconds
    assert(newTime.minute === Minute(1))
    assert(newTime.second === Second(9))
  }

  test("Time plus minutes") {
    val time = Time(minute = Minute(59))
    val newTime = time + 10.minutes
    assert(newTime.hour === Hour(1))
    assert(newTime.minute === Minute(9))
  }

  test("Time plus hours") {
    val time = Time(hour = Hour(12))
    val newTime = time + 12.hours
    assert(newTime.hour === Hour(0))
  }

  test("Time minus milliseconds") {
    val time = Time()
    val newTime = time - 1.milliseconds
    assert(newTime.millisecond === Millisecond(999))
    assert(newTime.second === Second(59))
    assert(newTime.minute === Minute(59))
    assert(newTime.hour === Hour(23))
  }

  test("Time format") {
    val time = Time(Hour(9), Minute(5), Second(3), Millisecond(2)).format

    assert(time === "09:05:03.002")
  }

  test("Parse time millis") {
    val time = Time.parse("18:11:09.123").get
    assert(time === Time(Hour(18), Minute(11), Second(9), Millisecond(123)))
  }

  test("Parse time millis- compact") {
    val time = Time.parse("181109.123").get
    assert(time === Time(Hour(18), Minute(11), Second(9), Millisecond(123)))
  }

  test("Parse time millis 2") {
    val time = Time.parse("18:11:09.12").get
    assert(time === Time(Hour(18), Minute(11), Second(9), Millisecond(120)))
  }

  test("Parse time millis 2 - compact") {
    val time = Time.parse("181109.12").get
    assert(time === Time(Hour(18), Minute(11), Second(9), Millisecond(120)))
  }

  test("Parse time millis 3") {
    val time = Time.parse("18:11:09.1").get
    assert(time === Time(Hour(18), Minute(11), Second(9), Millisecond(100)))
  }

  test("Parse time millis 3 - compact") {
    val time = Time.parse("181109.1").get
    assert(time === Time(Hour(18), Minute(11), Second(9), Millisecond(100)))
  }


  test("Parse time second") {
    val time = Time.parse("18:11:09").get
    assert(time === Time(Hour(18), Minute(11), Second(9)))
  }

  test("Parse time second - compact") {
    val time = Time.parse("181109").get
    assert(time === Time(Hour(18), Minute(11), Second(9)))
  }

  test("Parse time minute") {
    val time = Time.parse("18:11").get
    assert(time === Time(Hour(18), Minute(11)))
  }

  test("Parse time minute- compact") {
    val time = Time.parse("1811").get
    assert(time === Time(Hour(18), Minute(11)))
  }

  test("Parse time hour- compact") {
    val time = Time.parse("18").get
    assert(time === Time(Hour(18)))
  }

}
