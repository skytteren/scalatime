package no.skytteren.scalatime

import org.scalatest.FunSuite

class TimePimpTest extends FunSuite {

  test("Time now") {
    val time = Time.now()
    val jTime = java.time.LocalTime.now()
    assert(time.hour.value === jTime.getHour)
    assert(time.minute.value === jTime.getMinute)
    assert(time.second.value === jTime.getSecond)
    assert(time.millisecond.value <= jTime.getNano/1000000)
  }

  test("Time MAX") {
      val jTime = java.time.LocalTime.MAX
      val time = Time.MAX
      assert(time.hour.value === jTime.getHour)
      assert(time.minute.value === jTime.getMinute)
      assert(time.second.value === jTime.getSecond)
      assert(time.millisecond.value === jTime.getNano / 1000000)
    }

  test("Time MIN") {
    val jTime = java.time.LocalTime.MIN
    val time = Time.MIN
    assert(time.hour.value === jTime.getHour)
    assert(time.minute.value === jTime.getMinute)
    assert(time.second.value === jTime.getSecond)
    assert(time.millisecond.value === jTime.getNano / 1000000)
  }

}
