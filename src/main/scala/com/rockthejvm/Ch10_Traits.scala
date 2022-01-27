package com.rockthejvm

import com.rockthejvm.Ch6_Objects.Account

object Ch10_Traits extends App {
  println("Chapter 10: Traits")
  /*
  key points
    1. a class can implement any number of traits
    2. traits can require implementing classes to have certain fields, methods, or superclasses
    3. scala traits can provide implementations of methods and fields
    4. when you layer multiple traits, the order matters - the trait whose methods execute first goes
        to the back
   */
  // 10.2 Traits as Interfaces
  trait Logger {
    def log(msg: String) // an abstract method
  }

  class ConsoleLogger extends Logger {
    def log(msg: String) { println(msg) }
  }

  // if you need more than one trait, add the others using the "with" keyword
  // ex: class ConsoleLogger extends Logger with CLoneable with Serializable

  // 10.3 Traits with Concrete Implementations
  trait ConsoleLoggerTrait {
    def log(msg: String) { println(msg) }
  }

  object SavingsAccount extends Account with ConsoleLoggerTrait {
    def withdraw(amount: Double): Unit = {
      if (amount > savingsBalance) log("Insufficient funds.")
      else savingsBalance -= amount
    }
  }

  // 10.4 Objects with Traits
  abstract class AbstractSavingsAccount extends Account with Logger {
    def withdraw(amount: Double): Unit = {
      if (amount > savingsBalance) log("Insufficient funds")
      else savingsBalance -= amount
    }
  }

  val acct = new AbstractSavingsAccount with ConsoleLoggerTrait

  println(s"account id ${acct.id}")

  //10.5 Layered Traits
  // you can add, to a class or an object, multiple traits that invoke each other starting
  // with the last one (useful when you need to transform values in stages)
  trait TimestampLogger extends ConsoleLoggerTrait {
    override def log(msg: String): Unit = {
      super.log(s"${java.time.Instant.now()} $msg")
    }
  }

  trait ShortLogger extends ConsoleLoggerTrait {
    override def log(msg: String): Unit = {
      super.log(
        if (msg.length <= 15) msg else s"${msg.substring(0, 12)}..."
      )
    }
  }

  val acct2 = new Account with TimestampLogger
  acct2.log("message with timestamp")

  // last trait's log method executes first
  val acct3 = new AbstractSavingsAccount with TimestampLogger with ShortLogger
  val acct4 = new AbstractSavingsAccount with ShortLogger with TimestampLogger
  acct3.withdraw(5)
  acct4.withdraw(5)
  // if you want to control which trait's method is invoked you can specify it in brackets
  // super[ConsoleLogger].log(...)
  // must be immediate supertype

  // 10.7 Traits for Rich Interfaces
  trait RichLogger {
    def log(msg: String)
    def info(msg: String) { log(s"INFO: $msg") }
    def warning(msg: String) { log( s"WARN: $msg") }
    def severe(msg: String) { log( s"SEVERE: $msg") }
  }

  class RichSavingsAccount extends Account with RichLogger {
    def log(msg: String) { println(msg) }

    def withdraw(amount: Double): Unit = {
      if (amount > savingsBalance) severe("Insufficient funds")
      else savingsBalance -= amount
    }
  }

  val acct5 = new RichSavingsAccount
  acct5.withdraw(5)

  // 10.9 Abstract Fields in Traits

  // 10.10 Trait Construction Order
  /*
  1. Superclass constructor
  2. Trait constructors (left to right)
  3. Within each trait parents are constructed first
  4. Parents of traits are not reconstructed if multiple traits share same parent
  5. Subclass constructed
   */

  // 10.11 Initializing Trait Fields
  // traits cannot have constructor parameters
  // can use early definition
  /*
  trait FileLogger extends Logger {
    val filename: String // abstract field
    val out = new PrintStream(filename)
    def log(msg: String) { out.println(msg); out.flush() }
  }

  val acct = new { val filename = "myapp.log" } with SavingsAccount with FileLogger
   */

  // 10.12 Traits extending classes
  // a trait can extend a class. That class becomes a superclass of any class mixing in the trait
  // ex LoggedException trait extends the Exception class
  /*
  trait LoggedException extends Exception with ConsoleLogger {
    def log() { log(getMessage()) }
  } // calls superclass Exception's getMessage Method
   */
}
