package com.example.android.simpleadapterexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View v){
        if (quantity ==100){
            Toast.makeText(this,"You can order more than 100 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        TextView viewQuantity = (TextView) findViewById(R.id.View_Quantity);
        viewQuantity.setText(String.format("%d", quantity));

    }

    public void decrement(View v) {
        if (quantity == 0) {
            Toast.makeText(this, R.string.least_quantity_to_order, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    public void sumbitOrder(View view){
        CheckBox whippedCreamBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean includeWhippedCream = whippedCreamBox.isChecked( );

        //Log.v("MainActivity", "whippd cream checked" + includeWhippedCream);

        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean includeChocolate = chocolateCheckBox.isChecked();

        EditText customerName = findViewById(R.id.add_customer_name);
        String name = customerName.getText().toString();

        dislayMessage(createOrderSummary(includeWhippedCream, includeChocolate, name));

       // Intent intent = new Intent(Intent.ACTION_VIEW);
      //  intent.setData(Uri.parse("geo:47.6, -122.4"));
      //  if (intent.resolveActivity(getPackageManager())!= null) {
       //     startActivity(intent);
       // }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "coffee order" + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(includeWhippedCream, includeChocolate, name));
        if (intent.resolveActivity(getPackageManager())!= null) {
            startActivity(intent);
        }

    }

    public int calculatePrice(int price,boolean whippedCreamOrderd, boolean chocolateOrdered ){
        if (whippedCreamOrderd) {
        price +=1;
        }

        if (chocolateOrdered) {
        price +=2;
        }
        return price * quantity;

    }
    public String createOrderSummary(boolean addwhippedCream,boolean addChocolate, String customerName){
        String summaryOrder = getString(R.string.name,customerName);
        summaryOrder += "\n"+ getString(R.string.include_whipped_cream)  +addwhippedCream;
        summaryOrder += "\n"+ getString(R.string.include_chocolate)  +addChocolate;
        summaryOrder += "\n"+ getString(R.string.quantity_ordered) +quantity;
        summaryOrder +="\n"+ getString(R.string.total_price, NumberFormat.getCurrencyInstance().format(calculatePrice(5, addwhippedCream, addChocolate)));
        summaryOrder += "\n"+ getString(R.string.thank_you);
        return  summaryOrder;
    }

    public void displayQuantity(int numberofCoffee){
        TextView viewQuantity = (TextView) findViewById(R.id.View_Quantity);
        viewQuantity.setText(String.valueOf(numberofCoffee));
    }

    public void dislayMessage(String message){
        TextView viewPrice = (TextView) findViewById(R.id.Order_Summary);
        viewPrice.setText(String.valueOf(message));
    }
}
