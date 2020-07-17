package com.example.labtest2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.labtest2.R;
import com.example.labtest2.adapters.ContactsAdapter;
import com.example.labtest2.models.ContactsModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    TextView txtNumbers;

    public static final String KEY_id = "person_id";
    public static final String KEY_personFirstName = "person_first_name";
    public static final String KEY_personLastName = "person_last_name";
    public static final String KEY_personPhoneNumber = "person_phone_number";
    public static final String KEY_personAddress = "person_address";
    ArrayList<String> phoneNumbers, firstNames, lastNames, address;
    RecyclerView recyclerView;
    ArrayList<ContactsModel> contacts;
    ImageView backBtn;
    Button addBtn;
    EditText search;
    ContactsAdapter contactsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        search = findViewById(R.id.search);

        backBtn = findViewById(R.id.back_btn);
        addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddContact.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void filter(String text) {
        if (contactsAdapter != null) {
            ArrayList<ContactsModel> filteredList = new ArrayList<>();

            for (ContactsModel item : contacts) {
                if (item.getPhoneNumber().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            contactsAdapter.filterList(filteredList);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchContacts();
    }

    public void fetchContacts() {
        phoneNumbers = new ArrayList<>();
        firstNames = new ArrayList<>();
        lastNames = new ArrayList<>();
        address = new ArrayList<>();
        contacts = new ArrayList<>();

        sqLiteDatabase = this.openOrCreateDatabase("Persons", MODE_PRIVATE, null);

        txtNumbers = findViewById(R.id.txtNumbers);

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Contact " +
                "(" + KEY_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_personFirstName + " TEXT,"
                + KEY_personLastName + " TEXT,"
                + KEY_personPhoneNumber + " TEXT,"
                + KEY_personAddress + " TEXT"
                + ");");

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Contact", null);
        cursor.moveToFirst();

        if (cursor != null && cursor.getCount() != 0) {
            txtNumbers.setText(cursor.getCount() + "");
            do {
                address.add(cursor.getString(cursor.getColumnIndex(KEY_personAddress)));
                phoneNumbers.add(cursor.getString(cursor.getColumnIndex(KEY_personPhoneNumber)));
                firstNames.add(cursor.getString(cursor.getColumnIndex(KEY_personFirstName)));
                lastNames.add(cursor.getString(cursor.getColumnIndex(KEY_personLastName)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        for (int i = 0; i < firstNames.size(); i++) {
            contacts.add(new ContactsModel(firstNames.get(i), lastNames.get(i), phoneNumbers.get(i), address.get(i)));
        }


        if (firstNames.size() > 0) {
            contactsAdapter = new ContactsAdapter(this, contacts);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(contactsAdapter);
        }
    }

    public void itemDeleted(ContactsModel item) {
        sqLiteDatabase.execSQL("DELETE FROM Contact WHERE " + KEY_personPhoneNumber + " = '" + item.getPhoneNumber() + "'");
        int noOfContacts = Integer.parseInt(txtNumbers.getText().toString()) - 1;
        txtNumbers.setText(noOfContacts + "");
    }
}