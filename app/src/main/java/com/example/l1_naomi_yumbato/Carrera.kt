package com.example.l1_naomi_yumbato

data class Carrera(
    val id:Int,
    val nombre:String,
    val pension: Float
){
    override fun toString(): String {
        return nombre
    }
}
