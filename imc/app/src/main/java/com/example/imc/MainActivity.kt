package com.example.imc

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun calculateIMC(weight: Double, height: Double): Double {
        return weight / (height.pow(2) / 10000);
    }

    private fun validateFields(name: String, weight: Double, height: Double): Boolean {
        return when {
            name.isBlank() -> {
                Toast.makeText(this, "Insira seu nome", Toast.LENGTH_SHORT).show();
                false;
            }
            weight <= 0 -> {
                Toast.makeText(this, "Insira um peso válido", Toast.LENGTH_SHORT).show();
                false;
            }
            height <= 0 -> {
                Toast.makeText(this, "Insira uma altura válida", Toast.LENGTH_SHORT).show();
                false;
            }
            else -> true;
        }
    }

    private fun addTextToContainer(container: LinearLayout, text: String, color: Int) {
        val textView = TextView(this);
        textView.text = text;
        textView.setTextColor(color);
        container.addView(textView);
    }

    fun calcular(v: View) {

        val button1 = findViewById<Button>(R.id.btnCalcular);
        val button2 = findViewById<Button>(R.id.btnRegistrar);

        val name = findViewById<EditText>(R.id.etNome).text.toString().trim();
        val weight = findViewById<EditText>(R.id.etPeso).text.toString().toDouble();
        val height = findViewById<EditText>(R.id.etAltura).text.toString().toDouble();

        if (!validateFields(name, weight, height)) {
            return;
        }

        val imc = calculateIMC(weight, height);
        val today = formatDate(Date());

        val resContainer = findViewById<LinearLayout>(R.id.registrosContainer)

        val text = "$today - $name - ${String.format("%.1f", imc)} IMC"
        addTextToContainer(resContainer, text, Color.BLACK)

        changeButtons(button1, button2, "calculate");
    }

    private fun imcCalc(imc: Double): String {

        val result = when {
            imc < 18.5 -> "MAGREZA"
            imc < 25.0 -> "NORMAL"
            imc < 30.0 -> "SOBREPESO"
            imc < 40.0 -> "OBESIDADE"
            else -> "OBESIDADE GRAVE"
        }

        return result;
    }

    private fun formatDate(date: Date): String {
        val formatDate = SimpleDateFormat("dd/MM/yyyy");
        return formatDate.format(date);
    }

    private fun changeButtons(b1: Button, b2: Button, action: String) {
        when (action) {
            "calculate" -> {
                b1.visibility = View.GONE
                b2.visibility = View.VISIBLE
            }
            "register" -> {
                b1.visibility = View.VISIBLE
                b2.visibility = View.GONE
            }
            else -> {
                // Ação desconhecida
            }
        }
    }

    fun register(v: View) {
        val button1 = findViewById<Button>(R.id.btnCalcular);
        val button2 = findViewById<Button>(R.id.btnRegistrar);

        val name = findViewById<EditText>(R.id.etNome).text.toString().trim();
        val weight = findViewById<EditText>(R.id.etPeso).text.toString().toDouble();
        val height = findViewById<EditText>(R.id.etAltura).text.toString().toDouble();

        if (!validateFields(name, weight, height)) {
            return;
        }

        val imc = calculateIMC(weight, height);

        val imcRange = imcCalc(imc);

        val resContainer: LinearLayout = findViewById(R.id.registerContainer);
        val res = TextView(this);

        res.text = "$name, seu é de IMC ${String.format("%.2f", imc)}.\n " +
                "o valor é considerado $imcRange";
        res.textSize = 24f;
        res.setTextColor(Color.BLACK);
        res.gravity = Gravity.CENTER;

        if (resContainer.childCount > 0) {
            resContainer.removeViewAt(0);
        }
        resContainer.addView(res);

        changeButtons(button1, button2, "register");
    }
}