package com.rockthejvm
import scala.math._

object Ch2 extends App {
  println("Chapter 2")

  // if statements return values
  var x = 3
  val test = if (x > 0) 1 else -1

  // every expression should have some value
  // null in scala is the Unit represented by ()
  val nothing = if (x < 0) 1 else ()
  println(s"nothing's class: " + nothing.getClass)

  var r = 2
  // can execute multiple statements on one line if separated by a semicolon
  if ( x > 0) { r = r * x; x -= 1}

  // block expression, value is result of last expression
  // val distance = { val dx = x - x0; val dy = y - y0; sqrt(dx * dx + dy * dy)

  // format strings with prefix letter f
  val name = "mark"
  val age = 27
  println(f"Hello $name! In six moths you'll be ${age + 0.5}%3.2f years old")

  // loops
  // while loops
  while (x > 0) {
    r = r * x
    x -= 1
  }

  // for loops
  for (i <- 1 to x)
    r = r * i

  // traversing strings
  var sum = 0
  for (i <- 0 to name.length - 1)
    sum += name(i)
  // same as
  sum = 0
  for (ch <- "Mark") sum += ch

  // note scala has no "break" or "continue" statements

  // advanced for loops
  for (i <- 1 to 3; j <- 1 to 3 if i !=j) print(f"${10 * i + j}%3d")

  // when body of for loop starts with "yield", the loop constructs a collection of values, one
  // for each iteration
  val vect = for (i <- 1 to 5) yield i % 3
  println(f"vector: $vect")
  // yields vector(1, 2, 0, 1, 2)

  // functions
  def abs(x: Double) = if (x >= 0) x else -x

  def fac(n: Int) = {
    var r = 1
    for (i <- 1 to n) r = r * i
    r
  }

  // with recursive functions, you must specify the return type
  def fac2(n: Int): Int = if (n <= 0) 1 else n * fac(n - 1)

  // same func multiple lines
  def fac3(n: Int): Int = {
    if (n <= 0) 1
    else n * fac(n - 1)
  }

  val facResult = fac2(7)
  println(f"factorial 7: ${facResult}")
  println(f"factorial 6: ${fac3(6)}")

  // can provide default values
  def decorate(str: String, left: String = "[", right: String = "]") =
    left + str + right

  decorate("hello")  // "[hello]"
  decorate("hello", "<<<", ">>>") // "<<<hello>>>"
  decorate(left="-", str="hello", right="+") // "-hello+"

  // define functions with variable number of arguments
  def sum(args: Int*) = {
    var result = 0
    for (arg <- args) result += arg
    result
  }

  var tot = sum(5, 6, 9, 10)
  println(s"\nthe total is $tot")

  // PROCEURES
  // a procedure is a function which returns no value
  def box(s: String): Unit = {
    val border = "-" * (s.length + 2)
    print(f"$border%n|$s|%n$border%n")
  }
  box("I'm in a box")

  // LAZY VALUES
  // note: when a val is declared as lazy, its initialization is deferred until it is access for the first time
  // ex:
  // lazy val words = scala.io.Source.fromFile("/usr/share/dict/words").mkString
  // if the program never accesses "words" the file is never opened

  // EXCEPTIONS
  x = 2
  if (x > 0) { sqrt(x)
  } else throw new IllegalArgumentException("x cannot be negative")
  // the first branch has type Double, the second has type Nothing, so expression has type Double

}
