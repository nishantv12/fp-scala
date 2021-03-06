package day1

import scala.Option
import scala.language.reflectiveCalls

object CreatingTypes {
  //Class in scala

  //Mostly we would avoid using class and use case classes
  //We can have multiple constructors using `def this` method,
  // but we will be using smart constructors to make/create our objects
  //A simple class
  class Address1(
      street: String,
      city: String,
      postalCode: String,
      state: String
  )
  //how to create object
  val add1 = new Address1("street", "city", "1234", "state")
  //lets make the fields available by using val
  //all the fields are private
  //this wont work add1.street
  class Address2(
      val street: String,
      val city: String = "test",
      val postalCode: String,
      val state: String
  )
  val add2 = new Address2("street", "city", "1234", "state")
  //named fields and default values assigned
  val add22 = new Address2(street = "street", postalCode = "1234", state = "state")
  //now the fields are public and immutable
  val s = add2.street

  //not recommended in FP, just to get familiar with scala syntax
  class Address3(
      var street: String,
      var city: String,
      var postalCode: String,
      var state: String
  )
  val add3 = new Address3("street", "city", "1234", "state")
  //now the fields are public and mutable
  add3.street = "new Street"

  /*
   * Class created does not have some boilerplate code which is given free when you create a case class
   * */

  case class Address4(
      street: String,
      city: String,
      postalCode: String,
      state: String
  )

  val add4 = Address4("street11", "city", "1234", "state") //syntactic sugar for apply
  val add5 = Address4.apply("street", "city", "1234", "state")
  /*
  1. apply
  2. unapply
  3. equals and hashcode
  4. canEqual
  5. It makes the object serializable
   */
  add4.canEqual("string") //false
  add4.canEqual(add5) //true

  println(add4.toString)
  println(add4.productArity)
  println(add4.productElement(0))
  println(add4.productElementName(0))
  println(add4.productElementNames)
  println(Address4.curried)
  println(Address4.tupled)

  val Some((s1, s2, s3, s4)) = Address4.unapply(add4)
  //we can also give default values while creating class or a case class
  //We can also add access modifiers inorder to limit the access of constructors, functions, values
  //for more details on access modifiers please refer the link https://www.jesperdj.com/2016/01/08/scala-access-modifiers-and-qualifiers-in-detail/

  //Tuples can also be used to create state /data
  //Tuple is nothing but a case class with syntactic sugar and extra swap function

  val t2 = (1, "second") // syntactic sugar for tuple
  val t22 = Tuple2(1,"second")
  //we can access tuple values with _{number} field names given by the tuple case class
  val fst  = t2._1
  val snd = t2._2
  t2.swap
  val t3 = (3, "three", 1.2d)

  //object is like a factory class
  object singletonObject {
    //all your functions
    def someFunc(t: Int): Int = t
  }
  singletonObject.someFunc(23)
  //case object is same as object except it give things mentioned below for free
  /*
   * 1. toString
   * 2. equals, hashcode, canEqual
   * 3. some product functions which we will never use
   * 4. Most important it make it serialization
   * */

  //Traits
  //As of now you can think of traits as java interface
  trait Account
  object CheckingAccount extends Account
  object SavingAccount extends Account

  //also we can have some abstract behaviour behaviour
  trait Switchable {
    //default implementation
    def toggleSwitch(currentState: Boolean): Boolean = !currentState
  }
  case class Fan(state: Boolean, speed: Int) extends Switchable {
    override def toggleSwitch(currentState: Boolean): Boolean =
      if (speed == 0) false else state && currentState
  }
  object Bulb extends Switchable

  //we can also have sealed traits
  sealed trait PhoneType
  case object HomePhone extends PhoneType
  case object WorkPhone extends PhoneType
  case object Other extends PhoneType

  //Enumeration in scala
  // Define a new enumeration with a type alias and work with the full set of enumerated values
  //https://www.scala-lang.org/api/current/scala/Enumeration.html
  object WeekDay extends Enumeration {
    type WeekDay = Value
    val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
  }
  import WeekDay._

  def isWorkingDay(d: WeekDay): Boolean = !(d == Sat || d == Sun)

  //structural type
  def func(stc:{def f:Int => Int; def x:Int => Int}):Int = stc.f(2)

  def func1[A,B](stc:{def f:A => B}):Int = ???

  class Someclass1 {
    def f:Int => Int = _ * 12
  }

  class Someclass {
    def f:Int => Int = _ * 12
    def x:Int => Int = _ * 3
  }
  func(new Someclass)
  func1(new Someclass1)
}
