package com.rockthejvm

object Ch4_Maps_and_Tuples extends App {
// object ch4 {
//  def Main(args: Array[String]) = {
    println("Chapter 4: Maps and Tuples")

    // construct an immutable map
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)

    // mutable map
    val mutableScores = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)

    // in order to start with an empty map, you must suppy type parameter
    val emptyScores = scala.collection.mutable.Map[String, Int]()

    // above same as
    val parenthesesMap = Map(("alice", 10), ("bob", 3), ("cindy", 8))

    // ACCESSING VALUES
    val bobScore = scores("Bob")

    // note: an exception will be thrown if the map does not contain the key
    // workaround
    val maybeBobScore = if (scores.contains("Bob")) scores("Bob") else 0
    // same as
    val getBobScore = scores.getOrElse("Bob", 0)
    // map.get(key) returns an Option object

    // given an immutable map you can turn it into a map with a fixed default value for keys which are not present
    val scores1 = scores.withDefaultValue(0)
    val zeldaScore1 = scores1("Zelda")
    println(s"zelda's score is $zeldaScore1")
    // can also give it a function
    val scores2 = scores.withDefault(_.length)
    println(s"zelda's score2 is ${scores2("zelda")}")

    // UPDATING VALUES
    mutableScores("Bob") = 10
    // add a key-value pair
    mutableScores("Fred") = 7
    // can add += to update with multiple associations
    mutableScores += ("Bob" -> 11, "Fred" -> 6)
    // use -= to remove a key value pair
    mutableScores -= "Alice"

    // can't update immutable maps but you can create a new map with desired updates
    val newScores = scores + ("Bob" -> 7, "Fred" -> 10)

    // ITERATING OVER MAPS
    for ((k, v) <- scores)
      println(s"$k: $v")

    // if you want only the keys
    val scoreKeys = scores.keySet
    // only values
    val scoreValues = scores.values

    // SORTED MAPS
    // keys in sorted order
    val sortedScores = scala.collection.mutable.SortedMap("Alice" -> 10,
      "Fred" -> 7, "Bob" -> 3)
    // order of insertion
    val linkedHashMap = scala.collection.mutable.LinkedHashMap("January" -> 1,
      "February" -> 2, "March" -> 3)

    // TUPLES - aggregates of values of different types
    val tuple1 = (1, 3.14, "Fred")
    // access tuple values with underscore (._1, ._2, ..)
    val tuple1val = tuple1._3
    println(s"the third type val is $tuple1val")

    // breaking out tuples (useful for functions which return more than one value
    val (first, second, _) = tuple1
    println(s"the second value is $second")

    // ZIPPING
    val symbols = Array("<", "-", ">")
    val counts = Array(2, 10, 2)
    val pairs = symbols.zip(counts)
    for ((s, n) <- pairs) print(s * n)
}
