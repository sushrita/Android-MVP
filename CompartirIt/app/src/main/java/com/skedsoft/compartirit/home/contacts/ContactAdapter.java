package com.skedsoft.compartirit.home.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.skedsoft.compartirit.R;
import com.skedsoft.compartirit.data.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 15-Oct-16.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ItemViewHolder> {
    private List<Contact> contacts;
    private Mode choiceMode;
    private OnContactSelection onContactSelection;

    public ContactAdapter(List<Contact> contacts, Mode choiceMode) {
        this.contacts = contacts;
        this.choiceMode=choiceMode;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, null, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Contact contact = contacts.get(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
        if (choiceMode==Mode.MULTIPLE) {
            holder.selection.setChecked(contact.isSelected());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contact.setSelected(!contact.isSelected());
                    notifyItemChanged(holder.getAdapterPosition());
                    if (onContactSelection != null)
                        onContactSelection.onContactSelection(contact);
                }
            });
        }
    }

    public List<Contact> getSelectedContacts() {
        List<Contact> selectedContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.isSelected())
                selectedContacts.add(contact);
        }
        return selectedContacts;
    }

    /**
     * Returns the number of contacts the contacts list
     *
     * @return <code>contacts.size()</code>
     */
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public OnContactSelection getOnContactSelection() {
        return onContactSelection;
    }

    public void setOnContactSelection(OnContactSelection onContactSelection) {
        this.onContactSelection = onContactSelection;
    }

    /**
     * Class which holds the view item for a contact
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView email;
        private CheckBox selection;

        ItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            selection = (CheckBox) itemView.findViewById(R.id.checkBox);
            selection.setVisibility(choiceMode==Mode.MULTIPLE?View.VISIBLE:View.GONE);
        }
    }

    public enum Mode{
        MULTIPLE,NONE
    }
}
