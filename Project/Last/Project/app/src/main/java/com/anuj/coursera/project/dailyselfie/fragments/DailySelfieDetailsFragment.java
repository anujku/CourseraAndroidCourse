package com.anuj.coursera.project.dailyselfie.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anuj.coursera.project.dailyselfie.R;
import com.anuj.coursera.project.dailyselfie.helpers.DailySelfiesPictureHelper;
import com.anuj.coursera.project.dailyselfie.models.DailySelfieRecord;

/**
 * Created by akulkarni on 029-29-11-14.
 */

public class DailySelfieDetailsFragment extends Fragment
{
    private DailySelfieRecord mSelfie;

    public DailySelfieDetailsFragment(DailySelfieRecord selfie) {
        mSelfie = selfie;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Display options menu
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.daily_selfie_details_fragment, container, false);

        ImageView imageView = (ImageView)view.findViewById(R.id.selfie_details_picture_image_view);
        imageView.setImageBitmap(DailySelfiesPictureHelper.getScaledBitmap(mSelfie.getPicturePath(), 0, 0));

        // Set title bar
        getActivity().getActionBar().setTitle(mSelfie.getName());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Remove items of the DailySelfiesFragment
        menu.clear();

        // Inflate selfie details mnu
        inflater.inflate(R.menu.daily_selfie_details_menu, menu);
    }
}