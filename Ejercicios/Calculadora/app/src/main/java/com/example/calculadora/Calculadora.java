package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Calculadora extends AppCompatActivity {

    protected TextView operacionActual, resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operacionActual = findViewById(R.id.operacionActual);
        resultado = findViewById(R.id.resultado);
        operacionActual.setText("");
        resultado.setText("");

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                operacionActual.append(b.getText().toString());
            }
        };

        findViewById(R.id.boton0).setOnClickListener(Listener);
        findViewById(R.id.boton1).setOnClickListener(Listener);
        findViewById(R.id.boton2).setOnClickListener(Listener);
        findViewById(R.id.boton3).setOnClickListener(Listener);
        findViewById(R.id.boton4).setOnClickListener(Listener);
        findViewById(R.id.boton5).setOnClickListener(Listener);
        findViewById(R.id.boton6).setOnClickListener(Listener);
        findViewById(R.id.boton7).setOnClickListener(Listener);
        findViewById(R.id.boton8).setOnClickListener(Listener);
        findViewById(R.id.boton9).setOnClickListener(Listener);

        findViewById(R.id.botonLimpiar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacionActual.setText("");
                resultado.setText("");
            }
        });

        findViewById(R.id.botonSumar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoActual = operacionActual.getText().toString();
                if(Character.isDigit(textoActual.charAt(textoActual.length() - 1))){
                    if(!resultado.getText().toString().isEmpty()){
                        operacionActual.setText(resultado.getText() + "+");
                        resultado.setText("");
                    } else {
                        operacionActual.setText(operacionActual.getText() + "+");
                    }
                }
            }
        });

        findViewById(R.id.botonRestar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoActual = operacionActual.getText().toString();
                if(Character.isDigit(textoActual.charAt(textoActual.length() - 1))){
                    if(!resultado.getText().toString().isEmpty()){
                        operacionActual.setText(resultado.getText() + "-");
                        resultado.setText("");
                    } else {
                        operacionActual.setText(operacionActual.getText() + "-");
                    }
                }
            }
        });

        findViewById(R.id.botonDividir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoActual = operacionActual.getText().toString();
                if(Character.isDigit(textoActual.charAt(textoActual.length() - 1))){
                    if(!resultado.getText().toString().isEmpty()){
                        operacionActual.setText(resultado.getText() + "/");
                        resultado.setText("");
                    } else {
                        operacionActual.setText(operacionActual.getText() + "/");
                    }
                }
            }
        });

        findViewById(R.id.botonMultiplicar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoActual = operacionActual.getText().toString();
                if(Character.isDigit(textoActual.charAt(textoActual.length() - 1))){
                    if(!resultado.getText().toString().isEmpty()){
                        operacionActual.setText(resultado.getText() + "x");
                        resultado.setText("");
                    } else {
                        operacionActual.setText(operacionActual.getText() + "x");
                    }
                }
            }
        });


        findViewById(R.id.botonIgual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String operacion = operacionActual.getText().toString();
                    String res = calcularOperacion(operacion);
                    resultado.setText(String.valueOf(res));
                } catch (Exception e) {
                    Toast.makeText(Calculadora.this, "No se puede hacer la operacion", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.botonDecimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoActual = operacionActual.getText().toString();

                // Evita que haya dos puntos seguidos en el mismo número
                // Buscamos desde el último operador hasta el final
                int ultimoOperadorIndex = Math.max(
                        Math.max(textoActual.lastIndexOf("+"), textoActual.lastIndexOf("-")),
                        Math.max(textoActual.lastIndexOf("*"), textoActual.lastIndexOf("/"))
                );

                String ultimoNumero = textoActual.substring(ultimoOperadorIndex + 1);

                // Si el último número ya contiene un punto, no añadimos otro
                if (!ultimoNumero.contains(".")) {
                    operacionActual.append(".");
                }
            }
        });
    }

    public static String calcularOperacion(String operacion) {
        operacion = operacion.replace("x", "*");
        double resultado = 0.0;
        char operador = '+'; // operador anterior
        String numero = "";

        for (int i = 0; i < operacion.length(); i++) {
            char c = operacion.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                numero += c;
            } else {
                // caso especial: número negativo
                if (numero.isEmpty() && c == '-' && (i == 0 || "+-*/".indexOf(operacion.charAt(i-1)) != -1)) {
                    numero += c; // añade el signo al número
                    continue;
                }

                // convertir número y aplicar operador anterior
                double n = Double.parseDouble(numero);
                switch (operador) {
                    case '+': resultado += n; break;
                    case '-': resultado -= n; break;
                    case '*': resultado *= n; break;
                    case '/': resultado /= n; break;
                }

                operador = c;
                numero = "";
            }
        }

        // último número
        if (!numero.isEmpty()) {
            double n = Double.parseDouble(numero);
            switch (operador) {
                case '+': resultado += n; break;
                case '-': resultado -= n; break;
                case '*': resultado *= n; break;
                case '/': resultado /= n; break;
            }
        }

        // formateo del resultado
        if (resultado == (int) resultado) {
            return String.valueOf((int) resultado);
        } else {
            String resStr = String.valueOf(resultado);
            if (resStr.length() > 6) {
                resStr = String.format("%.5f", resultado);
                while(resStr.endsWith("0")) resStr = resStr.substring(0, resStr.length()-1);
                if(resStr.endsWith(".")) resStr = resStr.substring(0, resStr.length()-1);
            }
            return resStr;
        }
    }
}