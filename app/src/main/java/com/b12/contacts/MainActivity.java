package com.b12.contacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.b12.contacts.fragments.ContactInfoFragment;
import com.b12.contacts.fragments.ContactsFragment;

public class MainActivity extends AppCompatActivity implements ContactsFragment.ContactListListener {
    private ContactInfoFragment contactInfoFragment;
    int num = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactInfoFragment = new ContactInfoFragment();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ContactsFragment catFragment = new ContactsFragment();
        ft.replace(R.id.contacts_fragment_container, catFragment);
        ft.replace(R.id.info_fragment_container, contactInfoFragment);
        ft.commit();

    }


    @Override
    public void onContactSend(String name, String letter, int image) {
        if (!name.isEmpty()) {
//            if (!(num == 1)) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//
//                ft.commit();
//                num = 1;
//            }

            contactInfoFragment.displayData(name, letter, image);

        }
    }


}