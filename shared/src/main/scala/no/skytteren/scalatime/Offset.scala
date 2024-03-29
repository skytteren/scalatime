package no.skytteren.scalatime

import implicits._

case class Offset(hours: Hours, minutes: Minutes = Minutes(0)) {
  require(hours.value >= -12 && hours.value <= 12, "Hours must be in the range [-12, 12] ")
  require(minutes.value >= 0 && hours.value < 60, "Hours must be in the range [0, 60] ")

  private[scalatime] def - (offset: Offset): Duration = {
    Duration(hours = hours - offset.hours, minutes = minutes - offset.minutes)
  }
}
object Offset {
  implicit val Z: Offset = Offset(Hours(0))
}
