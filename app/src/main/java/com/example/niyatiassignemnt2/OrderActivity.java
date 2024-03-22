package com.example.niyatiassignemnt2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private Spinner spSize;
    private RadioGroup rgBase, rgSpiceLevel, rgDelivery;
    private EditText etNumberOfPizzas;

    // Topping checkboxes
    private CheckBox cbPepperoni, cbMushroom, cbOnion, cbSausage, cbBacon, cbExtraCheese, cbBlackOlives, cbGreenPepper;

    // Side checkboxes
    private CheckBox cbGarlicDip, cbRanchDip, cbBlueCheeseDip, cbBuffaloSauce, cbPepsi;
    private EditText etName, etAddress, etPhone;

    private FloatingActionButton btnReset;
    private ExtendedFloatingActionButton btnOrder;

    private LinearLayout llAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        spSize = findViewById(R.id.spSize);
        rgBase = findViewById(R.id.rgBase);
        etNumberOfPizzas = findViewById(R.id.etNumberOfPizzas);
        cbPepperoni = findViewById(R.id.cbPepperoni);
        cbMushroom = findViewById(R.id.cbMushroom);
        cbOnion = findViewById(R.id.cbOnion);
        cbSausage = findViewById(R.id.cbSausage);
        cbBacon = findViewById(R.id.cbBacon);
        cbExtraCheese = findViewById(R.id.cbExtraCheese);
        cbBlackOlives = findViewById(R.id.cbBlackOlives);
        cbGreenPepper = findViewById(R.id.cbGreenPepper);
        cbGarlicDip = findViewById(R.id.cbGarlicDip);
        cbRanchDip = findViewById(R.id.cbRanchDip);
        cbBlueCheeseDip = findViewById(R.id.cbBlueCheeseDip);
        cbBuffaloSauce = findViewById(R.id.cbBuffaloSauce);
        cbPepsi = findViewById(R.id.cbPepsi);
        rgSpiceLevel = findViewById(R.id.rgSpiceLevel);
        rgDelivery = findViewById(R.id.rgDelivery);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        btnReset = findViewById(R.id.btnReset);
        btnOrder = findViewById(R.id.btnOrder);
        llAddress = findViewById(R.id.llAddress);


        // if delivery is selected show address field
        rgDelivery.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbDelivery) {
                llAddress.setVisibility(View.VISIBLE);
            } else {
                llAddress.setVisibility(View.GONE);
            }
        });

        String[] sizes = new String[]{"Small", "Medium", "Large"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSize.setAdapter(adapter);

        btnReset.setOnClickListener(v -> resetViews());
        btnOrder.setOnClickListener(v -> order());
    }

    private void resetViews() {
        spSize.setSelection(0);
        rgBase.clearCheck();
        etNumberOfPizzas.setText("");
        cbPepperoni.setChecked(false);
        cbMushroom.setChecked(false);
        cbOnion.setChecked(false);
        cbSausage.setChecked(false);
        cbBacon.setChecked(false);
        cbExtraCheese.setChecked(false);
        cbBlackOlives.setChecked(false);
        cbGreenPepper.setChecked(false);
        cbGarlicDip.setChecked(false);
        cbRanchDip.setChecked(false);
        cbBlueCheeseDip.setChecked(false);
        cbBuffaloSauce.setChecked(false);
        cbPepsi.setChecked(false);
        rgSpiceLevel.clearCheck();
        rgDelivery.clearCheck();
        etName.setText("");
        etAddress.setText("");
        etPhone.setText("");
    }

    public void order() {

        boolean error = false;
        ArrayList<String> externalErrors = new ArrayList<>();

        String size = spSize.getSelectedItem().toString();

        String base = "";
        if (rgBase.getCheckedRadioButtonId() != -1) {
            base = ((Button) findViewById(rgBase.getCheckedRadioButtonId())).getText().toString();
        } else {
            error = true;
            externalErrors.add("Please select a base");
        }

        String numberOfPizzas = etNumberOfPizzas.getText().toString();
        if (numberOfPizzas.isEmpty()) {
            error = true;
            etNumberOfPizzas.setError("Please enter number of pizzas");
        }

        ArrayList<String> toppings = new ArrayList<>();
        if (cbPepperoni.isChecked()) {
            toppings.add(cbPepperoni.getText().toString());
        }
        if (cbMushroom.isChecked()) {
            toppings.add(cbMushroom.getText().toString());
        }
        if (cbOnion.isChecked()) {
            toppings.add(cbOnion.getText().toString());
        }
        if (cbSausage.isChecked()) {
            toppings.add(cbSausage.getText().toString());
        }
        if (cbBacon.isChecked()) {
            toppings.add(cbBacon.getText().toString());
        }
        if (cbExtraCheese.isChecked()) {
            toppings.add(cbExtraCheese.getText().toString());
        }
        if (cbBlackOlives.isChecked()) {
            toppings.add(cbBlackOlives.getText().toString());
        }
        if (cbGreenPepper.isChecked()) {
            toppings.add(cbGreenPepper.getText().toString());
        }

        if (toppings.isEmpty()) {
            error = true;
            externalErrors.add("Please select at least one topping");
        }

        String spiceLevel = "";
        if (rgSpiceLevel.getCheckedRadioButtonId() != -1) {
            spiceLevel = ((Button) findViewById(rgSpiceLevel.getCheckedRadioButtonId())).getText().toString();
        } else {
            error = true;
            externalErrors.add("Please select a spice level");
        }

        ArrayList<String> sides = new ArrayList<>();
        if (cbGarlicDip.isChecked()) {
            sides.add(cbGarlicDip.getText().toString());
        }
        if (cbRanchDip.isChecked()) {
            sides.add(cbRanchDip.getText().toString());
        }
        if (cbBlueCheeseDip.isChecked()) {
            sides.add(cbBlueCheeseDip.getText().toString());
        }
        if (cbBuffaloSauce.isChecked()) {
            sides.add(cbBuffaloSauce.getText().toString());
        }
        if (cbPepsi.isChecked()) {
            sides.add(cbPepsi.getText().toString());
        }
        if (sides.isEmpty()) {
            error = true;
            externalErrors.add("Please select at least one side");
        }

        String delivery = "";
        if (rgDelivery.getCheckedRadioButtonId() != -1) {
            delivery = ((Button) findViewById(rgDelivery.getCheckedRadioButtonId())).getText().toString();
        } else {
            error = true;
            externalErrors.add("Please select a delivery option");
        }

        String name = etName.getText().toString();
        if (name.isEmpty()) {
            error = true;
            etName.setError("Please enter your name");
        }

        String address = etAddress.getText().toString();
        if (delivery.equals("Delivery") && address.isEmpty()) {
            error = true;
            etAddress.setError("Please enter your address");
        }

        String phone = etPhone.getText().toString();
        if (phone.isEmpty()) {
            error = true;
            etPhone.setError("Please enter your phone number");
        }

        if (!externalErrors.isEmpty()){
            StringBuilder errorMessage = new StringBuilder();
            for (String externalError : externalErrors) {
                errorMessage.append(externalError).append("\n");
            }

            // Show error message in a alert dialog
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(errorMessage.toString())
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        }

        if (error) {
            return;
        }

        String message = "Order Summary\n\n";
        message += "Size: " + size + "\n";
        message += "Base: " + base + "\n";
        message += "Number of pizzas: " + numberOfPizzas + "\n";
        message += "Toppings: " + String.join(", ", toppings) + "\n";
        message += "Spice level: " + spiceLevel + "\n";
        message += "Sides: " + String.join(", ", sides) + "\n";
        message += "Delivery: " + delivery + "\n";
        message += "Name: " + name + "\n";
        if (!address.isEmpty())
            message += "Address: " + address + "\n";
        message += "Phone: " + phone + "\n";

        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);

    }

}