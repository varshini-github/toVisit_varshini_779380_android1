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

import static com.example.labtest2.activities.MainActivity.KEY_id;
import static com.example.labtest2.activities.MainActivity.KEY_personAddress;
import static com.example.labtest2.activities.MainActivity.KEY_personFirstName;
import static com.example.labtest2.activities.MainActivity.KEY_personLastName;
import static com.example.labtest2.activities.MainActivity.KEY_personPhoneNumber;

public class AddContact extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;

    private TextInputLayout txtFirstName, txtLastName, txtPhoneNumber, txtAddress;
    private ImageView backBtn;
    private TextView personNumbers;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtAddress = findViewById(R.id.txtAddress);
        backBtn = findViewById(R.id.back_btn);
        personNumbers = findViewById(R.id.txtNumbers);
        saveBtn = findViewById(R.id.saveBtn);


        sqLiteDatabase = this.openOrCreateDatabase("Persons", MODE_PRIVATE, null);

        personNumbers = findViewById(R.id.txtNumbers);


        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Contact", null);
        cursor.moveToFirst();

        if (cursor != null && cursor.getCount() != 0) {
            personNumbers.setText(cursor.getCount() + "");
        }
        cursor.close();

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
                    ContactsModel personModel = new ContactsModel();
                    personModel.setFirstName(txtFirstName.getEditText().getText().toString());
                    personModel.setFirstName(txtLastName.getEditText().getText().toString());
                    personModel.setFirstName(txtPhoneNumber.getEditText().getText().toString());
                    personModel.setFirstName(txtAddress.getEditText().getText().toString());

                    sqLiteDatabase.execSQL("INSERT INTO Contact ("
                            + KEY_personFirstName + ","
                            + KEY_personLastName + " ,"
                            + KEY_personPhoneNumber + " ,"
                            + KEY_personAddress + ") VALUES (" +
                            "'" + txtFirstName.getEditText().getText().toString() + "'," +
                            "'" + txtLastName.getEditText().getText().toString() + "'," +
                            "'" + txtPhoneNumber.getEditText().getText().toString() + "'," +
                            "'" + txtAddress.getEditText().getText().toString() + "');");
                    Toast.makeText(AddContact.this, "Contact added successfully.", Toast.LENGTH_SHORT).show();
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