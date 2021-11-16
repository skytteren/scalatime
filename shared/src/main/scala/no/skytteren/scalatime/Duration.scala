package no.skytteren.scalatime

case class Years(value: Long){
  def unary_- = Years(-value)
  def <(other: Years): Boolean = value < other.value
  def <=(other: Years): Boolean = value <= other.value
  def >=(other: Years): Boolean = value >= other.value
  def >(other: Years): Boolean = value > other.value
}
object Years extends (Long => Years){
  implicit val orderingYears: Ordering[Years] = Ordering.by[Years, Long](_.value)
}

case class Months(value: Long){
  def unary_- = Months(-value)
  def <(other: Months): Boolean = value < other.value
  def <=(other: Months): Boolean = value <= other.value
  def >=(other: Months): Boolean = value >= other.value
  def >(other: Months): Boolean = value > other.value
}
object Months extends (Long => Months){
  implicit val orderingMonths: Ordering[Months] = Ordering.by[Months, Long](_.value)
}


case class Days(value: Long){
  def unary_- = Days(-value)
  def <(other: Days): Boolean = value < other.value
  def <=(other: Days): Boolean = value <= other.value
  def >=(other: Days): Boolean = value >= other.value
  def >(other: Days): Boolean = value > other.value

  def toSeconds: Seconds = Seconds(value * 24 * 60 * 60)
}
object Days extends (Long => Days) {
  implicit val orderingDays: Ordering[Days] = Ordering.by[Days, Long](_.value)

}

case class Hours(value: Long){
  def unary_- = Hours(-value)
  def <(other: Hours): Boolean = value < other.value
  def <=(other: Hours): Boolean = value <= other.value
  def >=(other: Hours): Boolean = value >= other.value
  def >(other: Hours): Boolean = value > other.value
  def toSeconds: Seconds = Seconds(value * 60 * 60)

}

case class Minutes(value: Long){
  def unary_- = Minutes(-value)
  def <(other: Minutes): Boolean = value < other.value
  def <=(other: Minutes): Boolean = value <= other.value
  def >=(other: Minutes): Boolean = value >= other.value
  def >(other: Minutes): Boolean = value > other.value
  def toSeconds: Seconds = Seconds(value * 60)
}
object Minutes extends (Long => Minutes) {
  implicit val minutesOrdering: Ordering[Minutes] = Ordering.by[Minutes, Long](_.value)
}

case class Seconds(value: Long){
  def unary_- = Seconds(-value)
  def <(other: Seconds): Boolean = value < other.value
  def <=(other: Seconds): Boolean = value <= other.value
  def >=(other: Seconds): Boolean = value >= other.value
  def >(other: Seconds): Boolean = value > other.value
}
object Seconds extends (Long => Seconds) {
  implicit val secondsOrdering: Ordering[Seconds] = Ordering.by[Seconds, Long](_.value)
}

case class Milliseconds(value: Long){
  def unary_- = Milliseconds(-value)
  def <(other: Milliseconds): Boolean = value < other.value
  def <=(other: Milliseconds): Boolean = value <= other.value
  def >=(other: Milliseconds): Boolean = value >= other.value
  def >(other: Milliseconds): Boolean = value > other.value
}

object Milliseconds extends (Long => Milliseconds) {
  implicit val millisecondsOrdering: Ordering[Milliseconds] = Ordering.by[Milliseconds, Long](_.value)
}

case class Duration(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0),
                    hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)) {

  def format(implicit durationFormat: DurationFormat): String = durationFormat.format(this)
}


