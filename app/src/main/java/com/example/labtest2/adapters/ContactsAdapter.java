package com.example.labtest2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labtest2.R;
import com.example.labtest2.activities.MainActivity;
import com.example.labtest2.activities.Update;
import com.example.labtest2.models.ContactsModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ContactsModel> contacts;
    Context context;

    public ContactsAdapter(Context context, List<ContactsModel> contacts) {
        this.contacts = contacts;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContactsModel contact = contacts.get(position);
        ((MyViewHolder) holder).name.setText(contact.getFirstName() + " " + contact.getLastName());
        ((MyViewHolder) holder).phoneNumber.setText(contact.getPhoneNumber());
        ((MyViewHolder) holder).address.setText(contact.getAddress());
        ((MyViewHolder) holder).container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Update.class)
                        .putExtra("FirstName", contact.getFirstName())
                        .putExtra("LastName", contact.getLastName())
                        .putExtra("Address", contact.getAddress())
                        .putExtra("PhoneNumber", contact.getPhoneNumber()));

            }
        });


        ((MyViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactsModel item = contacts.get(position);
                removeItem(position);
                ((MainActivity) context).itemDeleted(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, phoneNumber, address;
        ConstraintLayout container;
        ImageView delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            address = itemView.findViewById(R.id.address);
            delete = itemView.findViewById(R.id.delete);
            container = itemView.findViewById(R.id.container);
        }
    }

    public void removeItem(int position) {
        contacts.remove(position);
        notifyItemRemoved(position);
    }

    public void filterList(ArrayList<ContactsModel> filteredList) {
        contacts = filteredList;
        notifyDataSetChanged();
    }

}