package com.b12.contacts.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b12.contacts.R;
import com.b12.contacts.adapters.ContactsAdapter;
import com.b12.contacts.models.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class ContactsFragment extends Fragment implements ContactsAdapter.OnContactListener {
    private ArrayList<Contact> contactArrayList;
    private RecyclerView recyclerView;
    private ContactsAdapter contactsAdapter;
    private ContactListListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_layout, container, false);

        contactArrayList = new ArrayList<>();
        inits(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactsAdapter = new ContactsAdapter(getContext(), contactArrayList, this::onContactClick);
        recyclerView.setAdapter(contactsAdapter);

        return view;
    }



    private void inits(View view) {
        recyclerView = view.findViewById(R.id.rv_contacts);
        contactArrayList = readContact();
        contactArrayList = sortContacts(contactArrayList);
        contactArrayList = addAlphabets(contactArrayList);

    }

    private ArrayList<Contact> readContact() {
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, sort);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                @SuppressLint("Range") Uri uriNumber = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                @SuppressLint("Range") String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";
                Cursor phoneCursor = getActivity().getContentResolver().query(uriNumber, null, selection, new String[]{id}, null);
                if (phoneCursor.moveToNext()) {
                    @SuppressLint("Range") String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    final TypedArray imgs = getResources().obtainTypedArray(R.array.random_images);
                    final Random rand = new Random();
                    final int rndInt = rand.nextInt(imgs.length());
                    final int resID = imgs.getResourceId(rndInt, 0);
                    Contact contact = new Contact(name, number, resID, -1);
                    contactArrayList.add(contact);
                    phoneCursor.close();
                }
            }
            cursor.close();
        }
        return contactArrayList;
    }

    private ArrayList<Contact> sortContacts(ArrayList<Contact> contactArrayList) {
        Collections.sort(contactArrayList, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.getName().compareTo(t1.getName());
            }
        });
        return contactArrayList;
    }

    public ArrayList<Contact> addAlphabets(ArrayList<Contact> contacts) {
        int i;
        ArrayList<Contact> list = new ArrayList<>();
        Contact contact = new Contact();
        contact.setName(String.valueOf(contacts.get(0).getName().charAt(0)));
        contact.setViewType(1);
        list.add(contact);
        for (i = 0; i < contacts.size() - 1; i++) {
            Contact contact1 = new Contact();
            char name1 = contacts.get(i).getName().charAt(0);
            char name2 = contacts.get(i + 1).getName().charAt(0);
            if (name1 == name2) {
                contacts.get(i).setViewType(2);
                list.add(contacts.get(i));
            } else {
                contacts.get(i).setViewType(2);
                list.add(contacts.get(i));
                contact1.setName(String.valueOf(name2));
                contact1.setViewType(1);
                list.add(contact1);
            }

        }
        contacts.get(i).setViewType(2);
        list.add(list.get(i));
        return list;

    }

    @Override
    public void onContactClick(String name, String letter, int image) {
        listener.onContactSend(name, letter, image);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactListListener) {
            listener = (ContactListListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface ContactListListener {
        void onContactSend(String name, String letter, int image);
    }
}
