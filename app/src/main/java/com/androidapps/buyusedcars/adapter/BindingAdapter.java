package com.androidapps.buyusedcars.adapter;

import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapps.buyusedcars.R;
import com.squareup.picasso.Picasso;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("android:imageUrl")

    public static void loadImage(ImageView imageView, String url) {
        Picasso.get().load(url).placeholder(R.drawable.audicar).into(imageView);


    }


    @androidx.databinding.BindingAdapter("android:accidentDetail")

    public static void accidentText(TextView textView, int noOfOwner) {

        if (noOfOwner == 1) {
            textView.setText("  No Accident");
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_black_24dp, 0, 0, 0);
        } else {
            textView.setText("  Accident Reported");
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0);
        }

    }

    @androidx.databinding.BindingAdapter("android:noOfOwnerIcon")
    public static void noOfOwnerText(TextView textView, int noOfOwner) {
        if (noOfOwner == 1) {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_one_person, 0, 0, 0);
        } else
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_two_person, 0, 0, 0);

    }


    @androidx.databinding.BindingAdapter("android:entries")
    public static void setEntries(AppCompatSpinner spinner, int entries) {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(spinner.getContext(), entries, android.R.layout.simple_list_item_1);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }


    @androidx.databinding.BindingAdapter("android:setOnItemSelectedListener")
    public static void setSelectedItemPositionListener(AppCompatSpinner view, AdapterView.OnItemSelectedListener listener) {
        view.setOnItemSelectedListener(listener);

    }

    @androidx.databinding.BindingAdapter("android:value")
    public static void setValue(NumberPicker view, int value) {
        if (view.getValue() != value) {
            view.setValue(value);
        }
    }


    @androidx.databinding.BindingAdapter(value = {"android:documentKey", "android:wishListliveData", "android:heartChecked"}, requireAll = true)
    public static void setWishList(ImageButton button, String documentKey, MutableLiveData<String> wishList, MutableLiveData<Boolean> isHeartChecked) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("setWishList: ", documentKey);

                Log.d("setWishList: ", String.valueOf(isHeartChecked));
                isHeartChecked.setValue(true);
                wishList.setValue(documentKey);

            }
        });


    }

    @androidx.databinding.BindingAdapter(value = {"android:setsrc"})
    public static void setBackgroundColorForHeart(ImageButton button, boolean isheartChecked) {
        button.setBackground(null);
        if (isheartChecked) {

            button.setImageResource(R.drawable.ic_heart_checked);

//           button.setImageTintList(ColorStateList.valueOf(Color.parseColor("#D81B60")));

        } else button.setImageResource(R.drawable.ic_heart_unchecked);

    }

    @androidx.databinding.BindingAdapter("android:setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @androidx.databinding.BindingAdapter("textChangedListener")
    public static void bindTextWatcher(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }

}
