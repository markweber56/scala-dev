package com.rockthejvm

object Ch11_Operators extends App {
  println("Chapter 11: Operators")

  val res = 5 * 12
  println(s"res: $res")

  // can define operators in classes
  // ex: redefine "*" in class Fraction
  // (n1 / d1)x(n2 / d2)=(n1n2 / d1d2 )
  class Fraction(n: Int, d: Int) {
    var num = n
    var den = d
    def *(other: Fraction) = new Fraction(num * other.num, den * other.den)
    def value = s"$num/$den"
    def reduce(): Unit = {
      for (i <- num to 2 by -1) {
        if (num % i == 0 && den % i == 0) {
          num = num / i
          den = den / i
          reduce()
        }
      }
    }
  }
  val f1 = new Fraction(4, 6)
  val f2 = new Fraction(3, 20)
  val prod = f1 * f2
  prod.reduce()
  println(s"products value: ${prod.value}")
  // println(s"product: ${prod.value}")

  // companion objects allow us to instantiate classes without "new"
  object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)
  }

  val prod2 = Fraction(1, 3) * Fraction(3, 7)
  println(s"products value ${prod2.value}")
}
