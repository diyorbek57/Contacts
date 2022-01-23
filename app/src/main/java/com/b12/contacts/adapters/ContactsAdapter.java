package com.b12.contacts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.b12.contacts.R;
import com.b12.contacts.models.Contact;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEWTYPE_ALPHABET = 1;
    private final int VIEWTYPE_CONTACT = 2;
    Context context;
    ArrayList<Contact> contactArrayList;
    private int row_index = -1;
    OnContactListener onContactListener;

    public ContactsAdapter(Context context, ArrayList<Contact> contactArrayList, OnContactListener onContactListener) {
        this.context = context;
        this.contactArrayList = contactArrayList;
        this.onContactListener = onContactListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_ALPHABET) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alphabet, parent, false);
            return new AlphabetViewHolder(view);
        } else if (viewType == VIEWTYPE_CONTACT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
            return new ContactsViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
            return new ContactsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (holder instanceof AlphabetViewHolder) {
            ((AlphabetViewHolder) holder).alphabet.setText(contactArrayList.get(position).getName());
        } else if (holder instanceof ContactsViewHolder) {
            char letter = contactArrayList.get(position).getName().charAt(0);

            ((ContactsViewHolder) holder).image.setScaleType(ImageView.ScaleType.FIT_XY);
            ((ContactsViewHolder) holder).name.setText(contactArrayList.get(position).getName());
            ((ContactsViewHolder) holder).letter.setText("" + letter);
            ((ContactsViewHolder) holder).image.setImageResource(contactArrayList.get(position).getImage());
            ((ContactsViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    row_index = position;
                    notifyDataSetChanged();
                    onContactListener.onContactClick(contactArrayList.get(position).getName()
                            , contactArrayList.get(position).getNumber(), contactArrayList.get(position).getImage());

                }

            });
            if (row_index == position) {
                ((ContactsViewHolder) holder).cardView.setBackgroundColor(Color.parseColor("#FF494949"));
                ((ContactsViewHolder) holder).name.setTextColor(Color.parseColor("#ffffff"));
            } else {
                ((ContactsViewHolder) holder).cardView.setBackgroundColor(Color.parseColor("#ffffff"));
                ((ContactsViewHolder) holder).name.setTextColor(Color.parseColor("#000000"));
            }


        }
    }


    @Override
    public int getItemViewType(int position) {
        return contactArrayList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    public class AlphabetViewHolder extends RecyclerView.ViewHolder {
        TextView alphabet;

        public AlphabetViewHolder(@NonNull View itemView) {
            super(itemView);
            alphabet = itemView.findViewById(R.id.tv_alphabet_letter);

        }
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView letter, name;
        CardView cardView;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image);
            letter = itemView.findViewById(R.id.tv_letter);
            name = itemView.findViewById(R.id.tv_name);
            cardView = itemView.findViewById(R.id.card_contacts);
        }


    }

    public interface OnContactListener {
        void onContactClick(String name, String letter, int image);
    }
}
