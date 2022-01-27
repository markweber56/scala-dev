package com.rockthejvm
import scala.collection.mutable.ArrayBuffer

object Ch3_Arrays extends App {
  println("Chapter 3: Arrays")

  // FIXED LENGTH ARRAYS
  // new array with 10 integers all initialized to 0
  val nums = new Array[Int](10)

  // new array with ten elements all intitalized with null
  val a = new Array[String](10)

  // array with two elements with type inferred
  val s = Array("Hello", "World")

  // use () to access elements
  // note that while s is a val we can change elements
  s(0) = "Goodbye"
  println(s"s(0): ${s(0)}")

  // VARIABLE LENGTH ARRAYS
  val b = ArrayBuffer[Int]()
  println(s"new array buffer: $b")
  println(s"array buffer length: ${b.length}")

  // add an element to the end of the buffer
  b += 2
  println(s"array buffer with added element: $b")
  println(s"element 0 of array buffer ${b(0)}")

  b += (1, 2, 3, 4)
  println(s"append multiple elements: $b")

  b ++= Array(8, 13, 21)
  println(s"appended array: $b")

  b.trimEnd(5)
  println(s"trimmed array: $b")

  // insert a 6 at index 2
  b.insert(2, 6)
  println(s"insert a 6 at 2: $b")

  // remove multiple elements
  b.remove(2, 2)
  println(s"remove some elements: $b")

  // convert buffer to array with b.toArray
  val bArray = b.toArray
  println(s"b class type ${b.getClass}")
  println(s"bArray class type ${bArray.getClass}")
  // convert array to buffer with b.toBuffer
  bArray.toBuffer

  // TRAVERSING ARRAYS AND ARRAY BUFFERS
  val c = Array(1, 2, 3, 4, 5, 6)
  for (i <- 0 until c.length)
      println(s"$i: ${c(i)}")

  // traverse by twos
  println("\nby twos")
  for (i <- 0 until c.length by 2)
    println(s"$i: ${c(i)}")

  // backwards
  println("\nbackwards")
  for (i <- c.length - 1 to 0 by -1)
    println(s"$i: ${c(i)}")

  // same as
  println("\nbackwards using indices reversed")
  for (i <- c.indices.reverse)
      println(s"$i: ${c(i)}")

  // if you don't need the index
  println("\ntraversing without indices")
  for (v <- c)
    println(v)

  // TRANSFORMING ARRAYS
  val cTransformed = for (v <- c) yield 2 * v
  println("\ntransformed array")
  for (i <- 0 until cTransformed.length)
    println(s"$i: ${cTransformed(i)}")

  // add a guard
  val cGuarded = for (v <- c if v % 2 == 0) yield 2 * v
  println("\nguarded array")
  for (i <- 0 until cGuarded.length)
    println(s"$i: ${cGuarded(i)}")

  // alternatively
  val cFilter = c.filter(_ % 2 == 0).map(2 * _)
  println("\ntransform using filter and map")
  for (i <- 0 until cFilter.length)
    println(s"$i: ${cFilter(i)}")

  // COMMON ALGORITHMS
  val aSum = Array(1, 7, 2, 9).sum
  println(s"the array sum is: $aSum")

  val arrMax = Array("mary", "had", "a", "little", "lamb").max
  println(s"the array max is: $arrMax")

  val unsorted = Array(1, 7, 3, 2, 4)
  val sortedArr = unsorted.sorted
  println(s"the sorted array is ${sortedArr(0)}")

  // can also supply a comparison function
  val cDescending = c.sortWith(_ > _)
  println(s"sorted using sortWith ${cDescending(0)}")

  // concat to string
  println(c.mkString(" and "))

  val greaterThan3 = c.filter(_ > 3).length
  println(s"$greaterThan3 elements are greater than 3")
}
