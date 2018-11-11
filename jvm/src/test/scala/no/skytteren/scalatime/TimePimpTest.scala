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

}
