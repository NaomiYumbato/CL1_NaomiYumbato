package com.example.l1_naomi_yumbato

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

class PrintActivity : AppCompatActivity(){
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)

        val tvContenido:AppCompatTextView = findViewById(R.id.tvContenido)

        val alumno = intent.getStringExtra("alumno")
        val dni = intent.getStringExtra("dni")
        val carrera = intent.getStringExtra("carrera")
        val pension = intent.getStringExtra("pension")
        val desc1 = intent.getStringExtra("descuento1")
        val desc2 = intent.getStringExtra("descuento2")
        val totalDesc = intent.getStringExtra("totaldescuento")
        val totalPag = intent.getStringExtra("totalapagar")

        tvContenido.text = """
            Nombre: $alumno
            DNI: $dni
            Carrera: $carrera
            Pensión: $pension
            Descuento 1: $desc1
            Descuento 2: $desc2
            Total Descuento: $totalDesc
            Total a Pagar: $totalPag
        """.trimIndent()
    }
}