package no.skytteren.scalatime

import scala.util.Try

case class Year(value: Int) extends AnyVal {
  def isLeapYear: Boolean = {
    value % 4 == 0 && (value % 100 != 0 || value % 400 == 0)
  }
}

object Year extends (Int => Year) {
  implicit val numericYear: Numeric[Year] = BiNumeric.numeric[Year, Int](_.value, Year)
}

sealed abstract class Month(val value: Int, val daysInMonth: Year => Days)  {
  require(value >= 1 && value <= 12, s"Month must be in the range [1, 12], but was $value")

  def next = if (value == 12) Month.January else Month(value + 1)
}

object Month extends (Int => Month) {

  implicit val numericMonth: Numeric[Month] = BiNumeric.numeric[Month, Int](_.value, Month)

  case class NotFound(value: Int) extends Exception

  case object January extends Month(1, _ => Days(31))
  case object February extends Month(2, year => if(year.isLeapYear) Days(29) else Days(28))
  case object March extends Month(3, _ => Days(31))
  case object April extends Month(4, _ => Days(30))
  case object May extends Month(5, _ => Days(31))
  case object June extends Month(6, _ => Days(30))
  case object July extends Month(7, _ => Days(31))
  case object August extends Month(8, _ => Days(31))
  case object September extends Month(9, _ => Days(30))
  case object October extends Month(10, _ => Days(31))
  case object November extends Month(11, _ => Days(30))
  case object December extends Month(12, _ => Days(31))
  val all = List(
      January ,February ,March ,April ,May ,June ,July ,August ,September ,October ,November ,December
  )

  val map: Map[Int, Month] = all.map(a => a.value -> a).toMap

  override def apply(i: Int): Month = {
    if(i < 1 || i > 12)
      throw NotFound(i)
    else
      map(i)
  }
}

case class DayOfMonth(value: Int)  {
  require(value >= 1 && value <= 31, s"DayOfMonth must be in the range [1, 31], but was $value")
}
object DayOfMonth extends (Int => DayOfMonth) {
  implicit val numericDayOfMonth: Numeric[DayOfMonth] = BiNumeric.numeric[DayOfMonth, Int](_.value, DayOfMonth)
}

sealed abstract class DayOfWeek(val value: Int)  {
  require(value >= 1 && value <= 7, s"DayOfWeek must be in the range [1, 7], but was $value")
}

object DayOfWeek extends (Int => DayOfWeek) {
  case class NotFound(value: Int) extends Exception

  case object Monday extends DayOfWeek(1)
  case object Tuesday extends DayOfWeek(2)
  case object Wednesday extends DayOfWeek(3)
  case object Thursday extends DayOfWeek(4)
  case object Friday extends DayOfWeek(5)
  case object Saturday extends DayOfWeek(6)
  case object Sunday extends DayOfWeek(7)

  val all = List(
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
  )

  val map: Map[Int, DayOfWeek] = all.map(a => a.value -> a).toMap

  override def apply(i: Int): DayOfWeek = {
    if(i < 1 || i > 7)
      throw NotFound(i)
    else
      map(i)
  }
}

case class DayOfYear(value: Int)  {
  require(value >= 1 && value <= 366, s"DayOfYear must be in the range [1, 366], but was $value")
}

case class YearDay(year: Year, dayOfYear: DayOfYear)

case class Date(year: Year, month: Month, dayOfMonth: DayOfMonth){

  def +(duration: Duration): Date = {
    import duration._
    this + (years, months, days)
  }

  private[scalatime] def toDays: Days = {
    val m : Long = (month.value + 9) % 12
    val y : Long = year.value - m / 10
    Days(365 * y + y / 4 - y / 100 + y / 400 + (m * 306 + 5) / 10 + (dayOfMonth.value - 1))
  }

  def +(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0)): Date = {
    import Days.numericDays._

    val (y, m, d) = Date.toDate(toDays + days)

    val newMonth = (m.value - 1 + months.value) % 12 + 1

    val newYear = y.value + years.value + months.value / 12

    Date(Year(if(newMonth < 1) newYear - 1 else newYear), Month(if(newMonth < 1) newMonth + 12 else newMonth), d)
  }

  def isLeapYear = year.isLeapYear


  def -(years: Years = Years(0), months: Months = Months(0), days: Days = Days(0)): Date = {
    this + (-years, -months, -days)
  }

  def -(duration: Duration): Date = {
    import duration._
    this - (years, months, days)
  }

  def < (other: Date): Boolean = Date.DateOrdering.lt(this, other)
  def > (other: Date): Boolean = Date.DateOrdering.gt(this, other)

  def until(other: Date): Days = other.toDays - this.toDays

  def year(year: Year): Date = copy(year = year)
  def month(month: Month): Date = copy(month = month)
  def dayOfMonth(dayOfMonth: DayOfMonth): Date = copy(dayOfMonth = dayOfMonth)

  def dayOfWeek: DayOfWeek = {
    val(y, m) = month.value match {
      case 1 =>
        (year.value - 1, 13)
      case 2 =>
        (year.value - 1, 13)
      case i => (year.value, i)
    }
    val j: Int = y / 100
    val k: Int = y % 100
    val h: Int = (dayOfMonth.value + 13 * (m + 1) / 5 + k + (k / 4) + (j / 4) + (5 * j)) % 7
    DayOfWeek(((h+5) % 7)+1)
  }

  def format(implicit formater: DateFormat): String = formater.format(this)

}

object Date extends ((Year, Month, DayOfMonth) => Date){

  implicit object DateOrdering extends Ordering[Date] {
    override def compare(x: Date, y: Date): Int = Days.numericDays.compare(x.toDays, y.toDays)
  }

  def parse(in: String)(implicit dateParser: DateParser): Try[Date] = dateParser.parse(in)


  private[scalatime] def toDate(g: Days): (Year, Month, DayOfMonth) = {
    var y: Long = (10000*g.value + 14780)/3652425
    var ddd: Long = g.value - (365*y + y/4 - y/100 + y/400)
    if (ddd < 0) {
      y = y - 1
      ddd = g.value - (365*y + y/4 - y/100 + y/400)
    }
    val mi: Long = (100*ddd + 52)/3060
    val mm: Long = (mi + 2)%12 + 1
    y = y + (mi + 2)/12
    val dd: Long = ddd - (mi*306 + 5)/10 + 1
    (Year(y.toInt), Month(mm.toInt), DayOfMonth(dd.toInt))
  }

}

