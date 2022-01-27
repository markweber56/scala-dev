package com.rockthejvm
import scala.collection.mutable.ArrayBuffer

object Ch5_Classes extends App {
  println("Chapter 5: Classes")

  class Counter {
    private var value = 0 // you must initialize the field
    def increment() { value += 1}
    def current = value
  }

  val myCounter = new Counter // or Counter()
  myCounter.increment()
  println(s"counter val: ${myCounter.current}")
  // best practice to use parentheses when calling a method which mutates object state and no () for one which
  // accesses object state

  // every class's attributes come with getters and setters
  // we can redefine these
  class Person {
    private var privateAge = 0

    def age = privateAge
    def age_=(newValue: Int): Unit = {
      if (newValue > privateAge) privateAge = newValue; // can't get younger
    }
  }

  val jim = new Person
  jim.age = 10
  val jimsAge = jim.age
  println(s"jim is $jimsAge years old")

  // if the field is private, the getter and setter are private
  // if the field is a val, only a getter is generated

  // note: a method can access the private fields of all objects of its class
  /*
  class Counter {
    private var value = 0
    def increment() { value += 1}

    def isLess(other: Counter) = value < other.value
  }
  */

  // object private
  // private[this] var value = 0

  // AUXILIARY CONSTRUCTORS
  // auxiliary constructors are called "this"
  // each auxiliary constructor must start with a call to a previously defined auxiliary constructor or the primary constructor
  class Animal {
    private var name = ""
    private var age = 0

    def this(name: String) { // An auxiliary constructor
      this() // Calls primary constructor
      this.name = name
    }

    def this(name: String, age: Int) { // Another auxiliary constructor
      this(name)
      this.age = age
    }
  }

  val a1 = new Animal // Primary constructor
  val a2 = new Animal("thumper") // First auxiliary constructor
  val a3 = new Animal("thumper", 13) // second auxiliary constructor

  // NESTED CLASSES
  class Network {
    class Member(val name: String) {
      val contacts = new ArrayBuffer[Member]
    }

    private val members = new ArrayBuffer[Member]

    def join(name: String) = {
      val m = new Member(name)
      members += m
      m
    }
  }
  // two networks
  val chatter = new Network
  val myFace = new Network
  // chatter.Member and myFace.member are different classes
  val fred = chatter.join("Fred")
  val wilma = chatter.join("Wilma")
  fred.contacts += wilma // OK
  val barney = myFace.join("Barney") // has type myFace.Member
  // fred.contacts += barney // can't do this - myFace.member ~= chatter.Member type

  // ways around this
  // 1. move member to the Network companion object
  /*
  object Network {
    class Member(val name: String) {
      val contacts = new ArrayBuffer[Member]
    }
  }
   */

  // use a type projection Network#Member which means "a Member of any Network"
  /*
  class Network {
    class Member(val name: String) {
      val contacts = new ArrayBuffer[Network#Member]
    }
  }
   */

  // accessing parent class from inside child
  class Group(val name: String) { outer =>

    class Member(val name: String) {
      def description = s"$name inside ${outer.name}"
    }

    private val members = new ArrayBuffer[Member]

    def join(name: String) = {
      val m = new Member(name)
      members += m
      m
    }
  }

  val group1 = new Group("Mike's group")
  val mike = group1.join("mike jones")

  println(s"${mike.description}")
}
