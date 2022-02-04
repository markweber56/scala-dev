package com.rockthejvm
import scala.math._

object Ch12_Higher_Order_Functions extends App{
  println("Chapter 10: Higher-Order Functions")

  // in Scala, functions are first class citizens
  val num = 3.14
  val fun = ceil _ // _ indicates that you really meant the function and didn't forget to supply args

  val f: (String, Int) => Char = _.charAt(_)
  // only difference is f is a variable containing a function rather than a fixed function
  val testString = "abcde"
  println(s"${f(testString, 2)}")

  // give fun to another function
  val arr = Array(31.4, 1.7, 2.0).map(fun)
  println(s"${arr(1)}")

  // 12.3 Functions with Function Parameters
  def valueAtOneQuarter(f: (Double) => Double) = f(0.25)
  val ceiledQuarter = valueAtOneQuarter(ceil _)
  val sqrtQuarter = valueAtOneQuarter(sqrt _)
  // valueAtOneQuarter is a higher order function because it takes a function as a param

  // higher order functions can also produce functions
  def mulBy(factor: Double) = (x: Double) => factor * x
  val quintuple = mulBy(5)
  println(quintuple(5))

  val trippled = valueAtOneQuarter(x => 3 * x)
  // same as
  val trippled2 = valueAtOneQuarter(3 * _)

  // examples
  (1 to 9).map("*" * _).foreach(println _)

  val multip = (1 to 9).reduceLeft(_ * _)
  println(multip)
  val sortedWords = "Mary had  a litle lamb".split(" ").sortWith(_.length < _.length)

  // Currying: turning a function which takes two arguments into a function which takes 1
  val mul = (x: Int, y: Int) => x * y
  val mulOneAtaTime = (x: Int) => ((y: Int) => x * y)
  mul(6, 7)
  //sameAs
  mulOneAtaTime(6)(7)

  // who cares?
  // sometimes we can use currying for a method parameter so that the type inferencer has more information
  // "corresponds" method compares whether two sequences are the same under some comparsion criterion
  val a = Array("Hello", "World")
  val b = Array("hello", "world")
  a.corresponds(b)(_.equalsIgnoreCase(_))
  // def corresponds[B](that: Seq[B])(p: (A, B) => Boolean): Boolean

  // Control Abstractions
  // we can model a sequence of statements as a function with no parameters or return value
  // this function runs some code in a thread
  def runInThread(block: => Unit): Unit = { // omitting () before =>
    new Thread {
      override def run() {
        block
      }
    }.start()
  }
  runInThread { println("Hi"); Thread.sleep(1000); println("Bye")}

  // function that works like while but with inverted condition
  def until(condition: => Boolean)(block: => Unit): Unit = {
    if (!condition) {
      block
      until(condition)(block)
    }
  }
  var x = 10
  until (x == 0) {
    x -= 1
    println(x)
  }

}