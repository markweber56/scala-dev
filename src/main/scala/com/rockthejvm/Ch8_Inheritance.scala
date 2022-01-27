package com.rockthejvm

object Ch8_Inheritance extends App {
  println("Chpater 8: Inheritance")

  // EXTENDING A CLASS
  // extend using "extends keyword"
  // you MUST use the override modifier when you override a method which isn't abstract
  // invoke a superclas method with keyword "super"
  /*
  class Employee extends Person {
    var salary = 0.0
    override def toString = s"${getClass.getName}[name=$name]"
  }
   */

  // TYPE CHECKS AND CASTS
  // to test whether an object belongs to a given class use the isInsatnceOf method
  // asInstanceOf converts a reference to a subclass reference
  /*
  if (p.isInstanceOf[Employee]) {
    val s = p.asInstanceOf[Employee] // s has type employee
  }
   */
  // if you want to test if p refers to an Employee object, but not a subclass use
  // p.getClass == classOf[Employee]
  // *** Pattern matching is typically a better alternative ***

  // PROTECTED METHODS AND FIELDS
  // can declare a field or method as protected, such a member is accessible from any subclass, but not from other
  // locations
  // "protected[this]" restricts access to current object

  class Person(name: String, age: Int) {
    val name2 = name
    def description = s"$name, age: $age"
    final def unchangeable = println("I'm a person") // this method cannot be overwritten
  }

  val mark = new Person("Mark", 27)
  println(s"${mark.description}")

  // super class construction
  class Employee(name: String, age: Int, salary: Double) extends
    Person(name, age) {

    override def description = s"${super.description} [salary=$$$salary]"
  }

  val workerMark = new Employee("Mark", 27, 40000)
  println(s"${workerMark.description}")

  // OVERRIDING FIELDS
  class SecretAgent(codeName: String, age: Int) extends Person(codeName, age) {
    override val name2 = "secret"
  }

  // ANONYMOUS SUBCLASSES
  val alien = new Person("Fred", 12) {
    def greeting = "Greetings my name is Fred"
  }

  // ABSTRACT CLASSES
  // can use the "abstract" keyword to denote a class which cannot be instantiated
  abstract class Animal(val name: String) {
    def id: Int // No method body - this is an abstract method
  }
  // don't need "override" keyword when defining an abstract class in a subclass
  class Insect(name: String) extends Animal(name) {
    def id = name.hashCode
  }
  // can also have abstract fields
  abstract class Job {
    val id: Int
    var jobType: String
  }

  class Engineer(val id: Int) extends Job {
    var jobType = "engineering"
  }

  // CONSTRUCTION ORDER AND EARLY DEFINITIONS
  class Creature {
    val range: Int = 10
    val env: Array[Int] = new Array[Int](range)
  }

  class Ant extends Creature {
    override val range = 2
  }

  // above will cause problems because Creature is instantiated before Ant
  // can declare range "lazy" in super class
  // more effecient way is to use the "early definition syntax"
  class Spider extends { override val range = 2} with Creature

}
