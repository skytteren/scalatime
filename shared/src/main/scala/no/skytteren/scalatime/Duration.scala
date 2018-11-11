package no.skytteren.scalatime

case class Years(value: Int){
  def unary_- = Years(-value)
}
object Years extends (Int => Years) {
  implicit val numeric: Numeric[Years] = BiNumeric.numeric[Years, Int](_.value, Years)
}
case class Months(value: Int){
  def unary_- = Months(-value)
}
object Months extends (Int => Months){
  implicit val numeric: Numeric[Months] = BiNumeric.numeric[Months, Int](_.value, Months)
}
case class Days(value: Long){
  def unary_- = Days(-value)
}
object Days extends (Long => Days) {
  implicit val numeric: Numeric[Days] = BiNumeric.numeric[Days, Long](_.value, Days)

  def apply(value: Int): Days = new Days(value)
}

case class Hours(value: Int){
  def unary_- = Hours(-value)
}
object Hours extends (Int => Hours) {
  implicit val hoursNumeric = BiNumeric.numeric[Hours, Int](_.value, Hours)
}
case class Minutes(value: Int){
  def unary_- = Minutes(-value)
}
object Minutes extends (Int => Minutes) {
  implicit val minutesNumeric = BiNumeric.numeric[Minutes, Int](_.value, Minutes)
}
case class Seconds(value: Int){
  def unary_- = Seconds(-value)
}
case class Milliseconds(value: Int){
  def unary_- = Milliseconds(-value)
}

case class Duration(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0),
                    hours: Hours = Hours(0), minutes: Minutes = Minutes(0), seconds: Seconds = Seconds(0), milliseconds: Milliseconds = Milliseconds(0)) {

  def format(implicit durationFormat: DurationFormat): String = durationFormat.format(this)
}

object Duration{

  def years(years: Int): Duration = new Duration(years = Years(years))
  def months(months: Int): Duration = new Duration(months = Months(months))
  def days(days: Int): Duration = new Duration(days = Days(days))
  def hours(hours: Int): Duration = new Duration(hours = Hours(hours))
  def minutes(minutes: Int): Duration = new Duration(minutes = Minutes(minutes))
  def seconds(seconds: Int): Duration = new Duration(seconds = Seconds(seconds))
  def milliseconds(milliseconds: Int): Duration = new Duration(milliseconds = Milliseconds(milliseconds))

}

object BiNumeric{
  def numeric[A, B: Numeric](fab: A => B, fba: B => A): Numeric[A] = new Numeric[A]{
    private val numericB = implicitly[Numeric[B]]
    private def map(x: A, y: A, bf: (B, B) => B): A = fba(bf(fab(x), fab(y)))
    override def plus(x: A, y: A): A = map(x, y, numericB.plus)
    override def minus(x: A, y: A): A = map(x, y, numericB.minus)
    override def times(x: A, y: A): A = map(x, y, numericB.times)
    override def negate(x: A): A = fba(numericB.negate(fab(x)))
    override def fromInt(x: Int): A = fba(numericB.fromInt(x))
    override def toInt(x: A): Int = numericB.toInt(fab(x))
    override def toLong(x: A): Long = numericB.toLong(fab(x))
    override def toFloat(x: A): Float = numericB.toFloat(fab(x))
    override def toDouble(x: A): Double = numericB.toDouble(fab(x))
    override def compare(x: A, y: A): Int = numericB.compare(fab(x), fab(y))
  }
}
