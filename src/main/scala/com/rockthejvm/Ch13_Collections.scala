package com.rockthejvm

import scala.io.AnsiColor._
import scala.collection.mutable._
// import scala.collection.parallel

object Ch13_Collections extends App {
  println("Chapter 13: Collections")

  /**
    * 1. all collections extend Iterable trait
    * 2. 3 major types are sequences, sets, and maps
    * 3. most are mutable and immutable
    * 4. lists are either empty or has a head and tail which are also lists
    * 5. sets are unordered collections
    * 6. use LinkedHashSet to retain the insertion order or a SortedSet to iterate in sorted order
    */
  // an iterable is any collection that can yield an Iterator with which you can access elements in the collection
  val seq = Seq(5, 4, 3, 2, 1)
  val iter = seq.iterator
  while (iter.hasNext) {
    val next = iter.next()
    println(next)
  }
  /** SEQ
    * ordered sequence of values such as an array or list
    * IndexedSeq allows fast random access through an integer index
    ** SET
    * unordered collection of values
    * in SortedSet elements are always visited in sorted order
    ** Map
    * set of (key, value pairs)
   */
  val colorsIt = Iterable(0xFF, 0xFF00, 0xFF0000)

  // type conversion example
  val coll = Seq(1, 2, 3)
  val set = coll.toSet

  // can compare sequences of the same type
  Seq(1, 2, 3) == (1 to 3)  // true
  Seq(1, 2, 3) == Set(1, 2, 3) // false

  // how can we do useful work with immutable sets?
  // create new sets
  val newSet = set + 12
  println(s"new Size: ${newSet.size}")
  val anotherNewSet = newSet + 1 // 1 is already in the set, return reference to old set
  println(s"new new Size: ${anotherNewSet.size}")

  /** Vector
    * immutable equivalent of an ArrayBuffer: an indexed sequence with fast random access
    * implemented as trees where each node has up to 32 children
    ** Range
    * integer sequence 0, 1, 2, 3 or 10, 20, 30
    * only stores start end and increment
  * */

  /** Lists
    * lists are either Nil (empty) or an object with a head element and a tail that is again a list
    */
  val Digits = List(4, 2) // Digits.head = 4, digits.tail = List(2)
  // :: operator makes a new list from a given head and tail
  val newList = 9 :: List(4, 2) // List(9, 4, 2)

  // often want to use recursion to operate on lists
  // example calculates sum of elements in list
  def sum(lst: List[Int]): Int = {
    if (lst == Nil) 0 else lst.head + sum(lst.tail)
  }
  val newListSum = sum(newList)
  println(s"sum: $newListSum")
  // same function with pattern matching
  def patternMatchSum(lst: List[Int]) : Int = lst match {
    case Nil => 0
    case h :: t => h + sum(t) // :: destructures list, h is lst.head, t i lst.tail
  }
  val lstSumPM = patternMatchSum(newList)
  println(s"pattern match sum: $lstSumPM")
  // don't actually need to do any of this
  val builtInSum = newList.sum
  println(s"built in: $builtInSum")

  /** Sets
    * sets are collections of distinct elements
    * sets do not retain order
    * by default are stored as hashsets in which elements are organized by the value of the hashCode method
    * LinkedHashSet remembers order in which elements were inserted
    * SortedSet - is sorted?
    * */
  val newDigits = Set(1, 7, 2, 9)
  newDigits contains 0 // false
  Set(1, 2) subsetOf newDigits // true
  // union -> &
  val primes = Set(2, 3, 5, 7)
  val union = newDigits | primes
  println(s"union: $union")
  val intersect = newDigits & primes
  println(s"intersect: $intersect")
  val diff = newDigits &~ primes
  println(s"diff: $diff")

  // *** all operators for collections on page 178
  // common methods of Iterable trait on page 180

  // Mapping Functions
  val names = List("Peter", "Paul", "Mary")
  println(names.map(_.toUpperCase))
  def ulcase(s: String) = Vector(s.toUpperCase(), s.toLowerCase())
  println(names.map(ulcase))
  println(names.flatMap(ulcase))

  // transform method is in-place equivalent of map
  // applies to mutable collections and replaces each element with the result of a function
  val buffer = ArrayBuffer("Peter", "Paul", "Mary")
  buffer.transform(_.toUpperCase) // depreciated? use mapInPlace
  println(buffer)

  // collect method works with partial functions, funcitons that may not be defined for all inputs
  val collectTest = "-3+4".collect { case '+' => 1; case '-' => -1} // Vector(-1, 1)

  // groupBy
  val words = List("apple", "ape", "boris", "bengal", "johnson")
  val map = words.groupBy(_.substring(0, 1).toUpperCase)
  println(map)

  // reducing
  val nums = Set(1, 7, 2, 9)
  println(nums.reduceLeft(_ - _)) // ((1 -7) - 2) - 9 => 1 - 7 - 2 - 9 = -17
  println(nums.reduceRight(_ - _ )) // 1 - (7 - (2 - 9)) => 1 - 7 + 2 - 9 = - 13
  // fold left -> allows you to provide initial value
  val foldRes = nums.foldLeft(0)(_ - _) // 0 - 1 - 7 - 2 - 9
  // scanLeft, scanRight combine folding and mapping providing intermediate results
  val scanned = (1 to 10).scanLeft(0)(_ + _) // Vector(0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55)

  // Zipping
  val prices = List(5.0, 20.0, 9.95)
  val quantities = List(12, 10, 1)
  val zipped = prices zip quantities
  println(zipped)
  val costs = zipped.map( p => p._1 * p._2)
  println(costs)
  // zipAll -> can specify defaults
  // zip with index includes index
  println("scala".zipWithIndex)
  println("scala".zipWithIndex.max._2)

  /** Iterators
  while (iter.hasNext)
    do something with iter.next()
  same as
  for (item <- iter)
  see 188 for buffered iter
   **/

  /** Streams
  * immutable alternative to iterator
  * immutable list in which the tail is computed lazily
  **/
  def numsFrom(n: BigInt): LazyList[BigInt] = n #:: numsFrom(n + 1) // #:: constructs a Stream (now LazyList)
  val tenOrMore = numsFrom(10)
  println(tenOrMore.head)
  tenOrMore.tail.tail.tail
  println(tenOrMore.tail.tail.tail.head)
  // stream methods are executed lazily
  val squares = numsFrom(1).map(x => x * x)
  println(squares.head)
  println(squares.take(5).force) // force evaluation
  // can create stream from iterator
  // ex: val words = Source.fromFile("/user/share/dict/words").getLines.toStream

  /** Lazy Views
    * method yields a collection on which methods are applied lazily
  **/
  val palindromicSqaures = (1 to 1000000).view
    .map(x => x * x)
    .filter(x => x.toString == x.toString.reverse)
  // none of these values have been evaluated
  val palindromeString = palindromicSqaures.take(10).mkString(",")
  // enough sqaures are generated until 10 palindromes have been found
  // unlike streams views do not cache values
  println(palindromeString)

  // Scala <-> Java collections page 191

  // Parallel Collections -> this has been removed from the standard lib
  val bigColl = (1 to 10000).toList
}