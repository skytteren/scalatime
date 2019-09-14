package no.skytteren.scalatime

case class Years(value: Long){
  def unary_- = Years(-value)
}

case class Months(value: Long){
  def unary_- = Months(-value)
}

case class Days(value: Long){
  def unary_- = Days(-value)

  def - (other: Days) = Days(value - other.value)

  def toSeconds: Seconds = Seconds(value * 24 * 60 * 60)
}
object Days extends (Long => Days) {
  implicit val orderingDays: Ordering[Days] = Ordering.by[Days, Long](_.value)

}

case class Hours(value: Long){
  def unary_- = Hours(-value)
  def toSeconds: Seconds = Seconds(value * 60 * 60)

}

case class Minutes(value: Long){
  def unary_- = Minutes(-value)

  def toSeconds: Seconds = Seconds(value * 60)
}
object Minutes extends (Long => Minutes) {
  implicit val minutesOrdering = Ordering.by[Minutes, Long](_.value)
}

case class Seconds(value: Long){
  def unary_- = Seconds(-value)
}
object Seconds extends (Long => Seconds) {
  implicit val secondsOrdering = Ordering.by[Seconds, Long](_.value)
}

case class Milliseconds(value: Long){
  def unary_- = Milliseconds(-value)
}

object Milliseconds extends (Long => Milliseconds) {
  implicit val millisecondsOrdering = Ordering.by[Milliseconds, Long](_.value)
}

case class Duration(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0),
                    hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)) {

  def format(implicit durationFormat: DurationFormat): String = durationFormat.format(this)
}

