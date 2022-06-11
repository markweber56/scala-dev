package com.rockthejvm

import scala.math._
import scala.collection.JavaConverters._

object Ch14_PatterMatching_CaseClasses extends App {
  var sign = 4
  var ch: Char = 'h'

  ch match {
    case '+' => sign = 1
    case '-' => sign = -1
    case _ => sign = 0
  }
  println(s"ch: $ch")
  println(s"sign: $sign")

  // simplify to
  sign = ch match {
    case '+' => 1
    case '-' => -1
    case _ => 0
  }

  //use | to separate multiple alternatives
  val prefix = "0"
  val sign2 = prefix match {
    case "0" | "0x" | "0xx" => 5
    case _ => 7
  }
  println(s"sign2: $sign2")

  // guards
  var digit = 0
  ch match {
    case _ if Character.isDigit(ch) => digit = Character.digit(ch, 10)
    case '+' => sign = 1
    case '-' => sign = -1
    case _ => sign = 0
  }
  // top one is guard clause, can be any boolean

  // if the case keyword if follwed by a variable name then the match expression is assigned to that variable ??
  // don't understand this one
  val i = 7
  var test = '4'
  i match {
    case '+' => 1
    case '-' => -1
    case test => digit = Character.digit(test, 10)
  }
  println(s"test: $test")
  println(s"digit: $digit")

  // Type Patterns
  // you can match on the type of an expression
  var a = 5
  def convertToInt(x: Any): Int = x match {
    case x: Int => x
    case s: String => Integer.parseInt(s)
    case _: BigInt => Int.MaxValue
    case _ => 0
  }
  println(s"ret: ${convertToInt(a)}")
  println(s"ret: ${convertToInt("32")}")

  def arrayMatch(arr: Array[Int]): String = arr match {
    case Array(0) => "0" // match array containing 0
    case Array(x, y) => s"$x $y" // matches any array with two elements and binds variables x, y to the elements
    case Array(0, _*) => "0 ..." //matches any array starting with zero
    // can bind something to the rest like - case Array(x, rest @ _*) => rest.min
    case _ => "something else"
  }
  val arr1 = Array(0)
  val arr2 = Array(1, 2)
  val arr3 = Array(0, 1, 2, 3)
  println(arrayMatch(arr1))
  println(arrayMatch(arr2))
  println(arrayMatch(arr3))

  // tuples
  def tupleMatch(tup: (Int, Int)): String = tup match {
    case (0, _) => "0 ...."
    case (y, 0) => s"$y 0"
    case _ => "neither is 0"
  }
  println(tupleMatch(0,3))
  println(tupleMatch(5, 0))
  println(tupleMatch(3, 4))

  // Extractors with regex
  val pattern = "([0-9]+) ([a-z\\s]+)".r
  def patternExtractor(str: String): (Int, String) = str match {
    case pattern(num, item) => (num.toInt, s"item: $item")
    case _ => (0, "mismatch")
  }
  println(patternExtractor("3 nights at the hotel"))

  // defining multiple variables
  val (q, r) = BigInt(10) /% 3
  println(s"q: $q")
  println(s"r: $r")

  val arr4 = Array(1, 2, 3, 4, 5, 6)
  val Array(first, second, rest @ _*) = arr4
  println(first)
  println(second)
  println(rest)

  // 14.8 Patterns in for Expressions
  // how to traverse a map
  for ((k,v ) <- System.getProperties.asScala)
    println(s"$k -> $v")

  // in a for loop match failures are silently ignored
  // the following only prints keys with empty values
  println("\nEmpty Properties:")
  for ((k, "") <- System.getProperties.asScala)
    print(k)

  // can also use a guard
  println("\n64 guard:")
  for ((k, v) <- System.getProperties.asScala if v == "64")
    print(k)

  // 14.9 Case Classes
  abstract class Amount
  case class Dollar(value: Double) extends Amount
  case class Currency(value: Double, unit: String) extends Amount
  // can also have case objects for singletons
  case object Nothing extends Amount
  val amt = new Currency(25, "yen")

  def currencyMatcher(amt: Amount) = amt match {
    case Dollar(v) => s"$$$v"
    case Currency(_, u) => s"oh no I got a $u"
    case Nothing => ""
  }
  val res = currencyMatcher(amt)
  println(s"\n$res")


  trait Animal
  case class Dog(name: String) extends Animal
  case class Cat(name: String) extends Animal
  case object Woodpecker extends Animal

  def determineType(x: Animal): String = x match {
    case Dog(moniker) => "Got a Dog, name = " + moniker
    case _:Cat => "Got a Cat (ignoring the name)"
    case Woodpecker => "That was a Woodpecker"
    case _ => "That was something else"
  }
  println(determineType(new Dog("Rocky")))
  println(determineType(new Cat("Rusty the Cat")))
  println(determineType(Woodpecker))

  // 14.10 The copy Method and Named Parameters
  // the copy method of a case class makes a new object with the same values as an existing one
  val amt2 = Currency(29.95, "EUR")
  val price = amt2.copy()
  val price2 = amt2.copy(value = 19.95)
  val price3 = amt2.copy(unit = "CHF")

  // 14.11 Infix Notation in case Clauses
  val lst = List(1, 2, 4, 5)
  lst match { case h :: t => println(s"head: $h, tail: $t")}
  // same as case ::(h, t) which calls ::.unapply(lst)

  // 14.12 Matching Nested Structures
  abstract class Item
  case class Article(description: String, price: Double) extends Item
  case class Bundle(description: String, discount: Double, items: Item*) extends Item

  // easy to specify nested objects w/o needing new
  val bund = Bundle("Father's day special", 20.0,
    Article("scala for the impatient", 39.95),
    Bundle("Anchor Distillery Sampler", 10.0),
    Article("Old Potrero Straight Rye Whiskey", 79.95),
    Article("Junipero Gin", 32.95))

  // patterns can match specific nestings
  // case Bundle(_, _, Article(descr, _), _*) => ...
  // binds descr to the description of the first article in a bundle

  // can bind a nested value to variable with the @ notation
  // case Bundle(_, _, art @ Article(_, _), rest @ _*) => ...

  // calculate price of a bundle
  def priceCalc(it: Item): Double = it match {
    case Article(_, p) => p
    case Bundle(_, disc, its @ _*) => its.map(priceCalc).sum - disc
  }
  val bundlePrice = priceCalc(bund)
  println(s"bundle price: $bundlePrice")

  // 14.14 Sealed Classes
  // sealed abstract class amount
  // all subclasses of the sealed class must be defined in the same file as amount
  // all subclasses are known at compile time enabling the compiler to check pattern clauses for completeness

  // 14.16 The Option Type
  // Some("Fred") is an Option[String]
  // get method for a map is an option
  val scoreMap = Map(
    "alice" -> 6,
    "Peter" -> 4
  )
  val aliceScore = scoreMap.get("alice")
  aliceScore match {
    case Some(score) => println(s"score: $score")
    case None => println("No Score")
  }
  // less tedious to do
  if (aliceScore.isEmpty) println("No Score") else println(aliceScore.get)
  // even less
  println(aliceScore.getOrElse("No Score"))

  // 14.17 Partial Functions
  // a set of case clauses enclosed in braces is a partial function - a function which may not be defined for all inputs
  // partialFunction[A, B] A is the parameter type, B is the return type
  val f: PartialFunction[Char, Int] = { case '+' => 1; case '-' => -1 }
  f('-') // Calls f.apply('-'), returns -1
  f.isDefinedAt('0') // false
  // f('0') throws MatchErro
  "-3+4".collect { case '+' => 1 ; case '-' => -1 } // Vector(-1, 1)
}