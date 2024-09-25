package com.example.l1_naomi_yumbato

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var inputDni: AppCompatEditText
    lateinit var inputAlumno: AppCompatEditText
    lateinit var tvCarreras: AppCompatAutoCompleteTextView
    lateinit var groupDescuentos: RadioGroup
    lateinit var btnCalculate: AppCompatButton
    lateinit var btnImprimir: AppCompatButton
    lateinit var lblPension: AppCompatTextView
    lateinit var lblDesc1: AppCompatTextView
    lateinit var lblDesc2: AppCompatTextView
    lateinit var lblDescTotal: AppCompatTextView
    lateinit var lblTotal: AppCompatTextView

    var costCarrera: Float = 0f

    var selectedDescuento: Float = 0f

    var desc01: Float = 0f
    var desc02: Float = 0f
    var totalDesc: Float = 0f
    var total: Float = 0f
    val lstCarreras = arrayOf("Computacion e informatica", "Administracion de redes y comunicaciones", "Administracion y sistemas")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets }

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.tvCarreras)
        btnCalculate = findViewById(R.id.btnCalcular)
        tvCarreras = findViewById(R.id.tvCarreras)
        inputDni = findViewById(R.id.inputDni)
        inputAlumno = findViewById(R.id.inputAlumno)
        groupDescuentos = findViewById(R.id.groupDescuentos)
        lblPension = findViewById(R.id.lblPension)
        lblDesc1 = findViewById(R.id.lblDesc1)
        lblDesc2 = findViewById(R.id.lblDesc2)
        lblTotal = findViewById(R.id.lblTotal)

        lblDescTotal = findViewById(R.id.lblDescTotal)
        btnImprimir = findViewById(R.id.btnImprimir)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lstCarreras)

        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setAdapter(adapter)

        btnCalculate.setOnClickListener() {

            val selectedValue = autoCompleteTextView.text.toString()

            if(inputDni.text.toString().isNotEmpty() && inputAlumno.text.toString().isNotEmpty()){
                    if (verificarCarrera(selectedValue)) {
                        Toast.makeText(this, "Carrera válida: $selectedValue", Toast.LENGTH_SHORT).show()

                        var discountSelected = groupDescuentos.checkedRadioButtonId
                        if(discountSelected == -1){
                            Toast.makeText(this, "Seleccione un descuento", Toast.LENGTH_SHORT).show()

                        }else {
                            val selectedDiscount = findViewById<AppCompatRadioButton>(discountSelected)

                            when (selectedDiscount.id) {
                                R.id.rbDescuento1 -> {
                                    selectedDescuento = 0.05f
                                }

                                R.id.rbDescuento2 -> {
                                    selectedDescuento = 0.1f
                                }

                                R.id.rbDescuento3 -> {
                                    selectedDescuento = 0.12f
                                }
                            }
                            costCarrera = selectedPension()
                            desc01 = calculateDiscount1(costCarrera,selectedDescuento)
                            desc02 = calculateDiscount2()
                            totalDesc = totalDiscount(desc01,desc02)
                            total = totalToPay(costCarrera,totalDesc)

                            lblPension.text = "Pension: $costCarrera"

                            lblDesc1.text = "Descuento 1: S/%.2f".format(desc01)
                            lblDesc2.text = "Descuento 2: S/%.2f".format(desc02)

                            lblDescTotal.text ="T. Desc: S/%.2f".format(totalDesc)
                            lblTotal.text  ="Total: S/%.2f".format(total)
                        }


                    } else {
                        Toast.makeText(this, "Carrera no válida", Toast.LENGTH_SHORT).show()
                    }
            }else{
                Toast.makeText(this, "Llene todo el formulario", Toast.LENGTH_SHORT).show()
            }
        }

        btnImprimir.setOnClickListener(){
            val  intent  = Intent(this, PrintActivity::class.java)
            intent.putExtra("alumno",inputAlumno.text.toString())
            intent.putExtra("dni",inputDni.text.toString())
            intent.putExtra("carrera",tvCarreras.text.toString())
            intent.putExtra("pension",selectedPension().toString())
            intent.putExtra("descuento1",desc01.toString())
            intent.putExtra("descuento2",desc02.toString())
            intent.putExtra("totaldescuento",totalDesc.toString())
            intent.putExtra("totalapagar",total.toString())

            startActivity(intent)
        }
    }

    val arrayCarreras = arrayOf(
        Carrera(0,"Computacion e informatica",780f),
        Carrera(1,"Administracion de redes y comunicaciones",700f),
        Carrera(2,"Administracion y sistemas",640f)
    )

    private fun verificarCarrera(carrera: String): Boolean {
        return lstCarreras.contains(carrera)
    }
    private fun selectedPension() : Float{
        val carreraElegida: Carrera? = arrayCarreras.find { it.nombre == tvCarreras.text.toString() }
        if (carreraElegida != null) {
            return carreraElegida.pension
        } else{
            return 0f
        }
    }
    private fun calculateDiscount1(pension:Float, porcentaje:Float): Float {
        return   pension * porcentaje
    }

    private fun calculateDiscount2(): Float{
        val carreraElegida: Carrera? = arrayCarreras.find { it.nombre == tvCarreras.text.toString() }
        if (carreraElegida != null) {
            if(carreraElegida.id < 2){
                return 50f
            } else{
                return  0f
            }
        } else{
            return 0f
        }
    }
    private fun totalDiscount(desc1:Float ,desc2:Float):Float{
        return desc1 +desc2
    }
    private fun totalToPay(pension:Float, totalDiscount:Float):Float{
        return pension - totalDiscount
    }
}