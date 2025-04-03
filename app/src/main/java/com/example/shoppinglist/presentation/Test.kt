package com.example.shoppinglist.presentation

import java.io.Serializable

class Test {

    val sqrt : (Int) -> Int = { a : Int -> a*a}
    val s : (Int, Int) -> Int  = {a, b -> a*b}
    val outStr : (String) -> Unit = { print(it)}
    val sortArray : (Array<Int>) -> Array<Int> = {it.sortedArray()}

    val t : Any? = Any()
}