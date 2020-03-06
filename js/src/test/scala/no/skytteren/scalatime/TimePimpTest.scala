package no.skytteren.scalatime

import org.scalatest.funsuite.AnyFunSuite

class TimePimpTest extends AnyFunSuite {

  test("Time now") {
    val time = Time.now()
    val jTime = new scalajs.js.Date()
    assert(time.hour.value === jTime.getHours)
    assert(time.minute.value === jTime.getMinutes)
    assert(time.second.value === jTime.getSeconds)
    assert(time.millisecond.value <= jTime.getMilliseconds())
  }

}
