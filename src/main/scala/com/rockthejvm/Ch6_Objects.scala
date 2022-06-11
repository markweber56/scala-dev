package com.rockthejvm

object Ch6_Objects extends App {
  println("Chapter 6: Objects")

  /*
  - use objects for singletons and utility methods
  - a class can have a companion object with the same name
  - objects can extend classes or traits
  - The apply method of an object is usually for constructing new instances of the companion class
  - to avoid the "main" method, use an object that extends the App trait
  - you can implement enumerations by extending the Enumeration object
   */

  // scala has no static methods or fields, instead you use the object construct
  object Accounts {
    private var lastNumber = 0
    def newUniqueNumber() = { lastNumber += 1; lastNumber }
  }
  // note: if an object is never used, its constructor is never called
  // object is much like a class, but you cannot provide constructor parameters

  // COMPANION OBJECTS
  // scala's way of providing static methods for a class
  class Account {
    val id = Account.newUniqueNumber()
    var savingsBalance = 0.0
    private var balance = 0.0
    def deposit(amount: Double) { balance += amount}
    def checkBalance: Double = balance
  }

  object Account {
    private var lastNumber = 0
    private def newUniqueNumber() = { lastNumber += 1; lastNumber}
  }
  // class and companion object can access each other's private features

  // APPLY METHOD
  val marksAccount = new Account
  marksAccount.deposit(37.5)
  println(s"mark has ${marksAccount.checkBalance} in his account")

  // ENUMERATIONS
  object TrafficLightColor extends Enumeration {
    val Red, Yellow, Green = Value
    /* shortcut for
    val red = Value
    val yellow = Value
    val green = Value
     */
  }
  println("\ntraffic light colors")
  for (c <- TrafficLightColor.values) println(s"${c.id}: $c")

  println(s"${TrafficLightColor(0)}")
  println(s"${TrafficLightColor.withName("Red")}")

  // MORE ENUMERATIONS
  object Fingers extends Enumeration {
    type Finger = Value

    val Thumb = Value(1, "Thumb Finger")
    val Index = Value(2, "Pointer Finger")
  }

  assert(Fingers.Thumb == Fingers.withName("Thumb Finger"))
  println(s"thumb: ${Fingers.Thumb.toString}")


}
