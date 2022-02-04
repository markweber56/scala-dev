package com.rockthejvm

import scala.collection.mutable._

object ch13_exercises extends App {
  println("terst")


  val word = "Mississippi"

  def letterIndices(word: String): Map[Char, Set[Int]] = {
    val letterIdx = Map[Char, Set[Int]]()
    word.zipWithIndex.map {
      case (element, index) =>
        letterIdx(element) = letterIdx.getOrElse(element, Set(index)) + index
    }
    letterIdx
  }

  val missIdx = letterIndices(word)
  println(missIdx)
}
