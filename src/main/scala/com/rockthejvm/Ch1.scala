package com.rockthejvm
import scala.math._

object Ch1 extends App{
  println("Chapter 1")

  // vals are constants, you should use these unless var is NECESSARY
  val answer = 8 * 5 + 2
  println(s"the answer is $answer")

  // can change a var
  var changeable = 0
  changeable = 1

  // don't need to specify type but can if you need to
  val greeting: String = null

  // can declare multiple values on the same line
  val xmax, ymax = 100
  val greeting2, message: String = null

  // note: base types (byte, char, short, int, long, float, and double) are classes in Scala
  // there is no distinction, between primitive types and classes in scala
  1.toString() // yields string "1"
  1.to(10) // Yields range (1, 2, 3, ... 10)
  // same as
  1 to 10

  // note scala has no ++, -- operators, use +=1 -=1
  var x = 5
  x += 1 // yield 6

  val big: BigInt = 1234567890
  val divRem = big /% 15

  // in scala, methods which take no args do not require parentheses
  val sorted = "bonjour".toSeq.sorted
  println(s"the sorted sequence is $sorted")

  // see import statement at the top, in Scala, _ is the wildcard
  val sqrt2 = sqrt(2)

  // select ith letter
  val s = "hello"
  val letter = s(4)
  println(s"the letter is $letter")

  // lookinto richint, richdouble, stingops

  // return the max of the two values
  val higher = 10 max 2
  println(s"the higher of the two values is $higher")

  // use big int for large numbers
  scala.math.pow(2, 1024) // yields infinity
  BigInt(2).pow(1024) // yields 2**1024

  // random
  val randomBigInt = BigInt.probablePrime(100, scala.util.Random)
  val randomString = randomBigInt.toString(36)
  println(s"the random string is $randomString")

  // some additional string methods
  val take1 = randomString.take(1)
  println(s"take 1: $take1")
  println(s"the random string is $randomString")
  val takeRight = randomString.takeRight(1)

  println(s"take right: $takeRight")
  println(s"the random string is $randomString")
  val droppedString = randomString.drop(1)
  println("dropped 1")
  println(s"the random string after drop is $droppedString")
  val dropRightString = randomString.dropRight(1)
  println(s"the dropped right string is $dropRightString")

}
