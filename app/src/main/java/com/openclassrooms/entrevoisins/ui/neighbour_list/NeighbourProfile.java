package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.entrevoisins.R.drawable;
import static com.openclassrooms.entrevoisins.R.id;
import static com.openclassrooms.entrevoisins.R.layout;


public class NeighbourProfile extends AppCompatActivity{



    @BindView(id.aboutMeText)
    public TextView mTextViewAboutMe;
    @BindView(id.name_in_description)
    public TextView mTextViewNameInDescription;
    @BindView(id.name_on_picture)
    public TextView mTextViewNameOnPicture;
    @BindView(id.profile_picture)
    public ImageView mProfilePicture;
    @BindView(id.favoriteButton)
    public FloatingActionButton mFloatingActionButtonFavoriteButton;
    @BindView(id.address)
    public TextView mTextViewAddress;
    @BindView(id.phoneNumber)
    public TextView mTextViewPhoneNumber;
    @BindView(id.webAddress)
    public TextView mTextViewWebAddress;
    @BindView(id.back_arrow)
    public ImageView mImageViewBack;

    Neighbour neighbour;
    NeighbourApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_neighbour_profile);

        ButterKnife.bind(this);

        //Get the neighbor information

        mApiService = DI.getNeighbourApiService();
        Intent intent = getIntent();
        neighbour = intent.getParcelableExtra("neighbour");
        Glide.with(this)
                .load(neighbour.getAvatarUrl())
                .into(mProfilePicture);
        mTextViewNameOnPicture.setText(neighbour.getName());
        mTextViewNameInDescription.setText(neighbour.getName());
        mTextViewWebAddress.setText("www.facebook.fr/" + neighbour.getName());
        mTextViewAddress.setText(neighbour.getAddress());
        mTextViewAboutMe.setText(neighbour.getAboutMe());
        mTextViewPhoneNumber.setText(neighbour.getPhoneNumber());

        setFavoriteButton();



    // Back button finish the activity when clicked
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeighbourProfile.this.finish();
            }
        });

        //Favorite button change to yellow star when is clicked
        mFloatingActionButtonFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService.changeStatusNeighbour(neighbour);
                neighbour.setFavorite(!neighbour.isFavorite());
                setFavoriteButton();
                }
        });

    }

    public void setFavoriteButton () {
        Log.i("trace", ""+neighbour.isFavorite());
        if (neighbour.isFavorite()) {
            mFloatingActionButtonFavoriteButton.setImageResource(drawable.ic_star_yellow_24dp);
        }
        else {
            mFloatingActionButtonFavoriteButton.setImageResource(drawable.ic_star_border_yellow_24dp);
        }
    }


}