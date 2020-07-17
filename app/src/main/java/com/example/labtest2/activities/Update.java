package com.example.labtest2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labtest2.R;
import com.example.labtest2.models.ContactsModel;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.labtest2.activities.MainActivity.KEY_personAddress;
import static com.example.labtest2.activities.MainActivity.KEY_personFirstName;
import static com.example.labtest2.activities.MainActivity.KEY_personLastName;
import static com.example.labtest2.activities.MainActivity.KEY_personPhoneNumber;

public class Update extends AppCompatActivity {

    String firstName, lastName, address, phoneNumber;

    SQLiteDatabase sqLiteDatabase;

    private TextInputLayout txtFirstName, txtLastName, txtPhoneNumber, txtAddress;
    private ImageView backBtn;
    private TextView personNumbers;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        firstName = getIntent().getExtras().getString("FirstName");
        lastName = getIntent().getExtras().getString("LastName");
        address = getIntent().getExtras().getString("Address");
        phoneNumber = getIntent().getExtras().getString("PhoneNumber");


        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtAddress = findViewById(R.id.txtAddress);
        backBtn = findViewById(R.id.back_btn);
        personNumbers = findViewById(R.id.txtNumbers);
        saveBtn = findViewById(R.id.updateBtn);


        sqLiteDatabase = this.openOrCreateDatabase("Persons", MODE_PRIVATE, null);

        personNumbers = findViewById(R.id.txtNumbers);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Contact", null);
        cursor.moveToFirst();

        if (cursor != null && cursor.getCount() != 0) {
            personNumbers.setText(cursor.getCount() + "");
        }
        cursor.close();

        txtFirstName.getEditText().setText(firstName);
        txtLastName.getEditText().setText(lastName);
        txtAddress.getEditText().setText(address);
        txtPhoneNumber.getEditText().setText(phoneNumber);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidate()) {
                    System.out.println("Clicked: " + "UPDATE Contact Set "
                            + KEY_personFirstName + " = '" + txtFirstName.getEditText().getText().toString() + "',"
                            + KEY_personLastName + " = '" + txtLastName.getEditText().getText().toString() + "',"
                            + KEY_personPhoneNumber + " = '" + txtPhoneNumber.getEditText().getText().toString() + "',"
                            + KEY_personAddress + " = '" + txtAddress.getEditText().getText().toString() + "'" +
                            " WHERE " + KEY_personPhoneNumber + " = " + phoneNumber + ";");
                    sqLiteDatabase.execSQL("UPDATE Contact SET "
                            + KEY_personFirstName + " = '" + txtFirstName.getEditText().getText().toString() + "', "
                            + KEY_personLastName + " = '" + txtLastName.getEditText().getText().toString() + "', "
                            + KEY_personPhoneNumber + " = '" + txtPhoneNumber.getEditText().getText().toString() + "', "
                            + KEY_personAddress + " = '" + txtAddress.getEditText().getText().toString() + "'" +
                            " WHERE " + KEY_personPhoneNumber + " = " + "'" + phoneNumber + "';");
                    Toast.makeText(Update.this, "Contact updated successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    private boolean checkValidate() {
        String _first_name = txtFirstName.getEditText().getText().toString();
        String _last_name = txtLastName.getEditText().getText().toString();
        String _phone_number = txtPhoneNumber.getEditText().getText().toString();
        String _address = txtAddress.getEditText().getText().toString();

        if (TextUtils.isEmpty(_first_name)) {
            Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(_last_name)) {
            Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(_phone_number)) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(_address)) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}