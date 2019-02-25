package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String OPERAND1 = "Operand1";
    private final String OPERATOR_CONTENTS = "Operator Contents";

    private EditText result;
    private EditText newNumber;
    private TextView displayOperator;

    private Double operand1 = null;
    private String pendingOperator;
    private boolean negative = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperator = findViewById(R.id.operator);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button decimal = findViewById(R.id.decimal);

        Button numberSign = findViewById(R.id.numberSign);
        Button add = findViewById(R.id.add);
        Button minus = findViewById(R.id.minus);
        Button multiply = findViewById(R.id.multiply);
        Button divide = findViewById(R.id.divide);
        Button equals = findViewById(R.id.equals);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        decimal.setOnClickListener(listener);
        numberSign.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();

                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperator = op;
                displayOperator.setText(pendingOperator);
            }
        };

        add.setOnClickListener(opListener);
        minus.setOnClickListener(opListener);
        multiply.setOnClickListener(opListener);
        divide.setOnClickListener(opListener);
        equals.setOnClickListener(opListener);

        View.OnClickListener signListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value;
                if (!negative){
                    value = "-" + newNumber.getText().toString();
                    newNumber.setText(value);
                    negative = true;
                } else {
                    value = newNumber.getText().toString().substring(1);
                    newNumber.setText(value);
                    negative = false;
                }
            }
        };

        numberSign.setOnClickListener(signListener);

    }

    private void performOperation(Double value, String op) {


        if (operand1 == null) {
            operand1 = value;
        } else {

            if (pendingOperator.equals("=")) {
                pendingOperator = op;
            }

            switch (pendingOperator) {

                case "=":
                    operand1 = value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "x":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(OPERATOR_CONTENTS, pendingOperator);
        if (operand1 != null){
            outState.putDouble(OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperator = (savedInstanceState.getString(OPERATOR_CONTENTS));
        operand1 = savedInstanceState.getDouble(OPERAND1);
        displayOperator.setText(pendingOperator);

    }
}
