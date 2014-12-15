package com.anuj.coursera.project.dailyselfie.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anuj.coursera.project.dailyselfie.R;
import com.anuj.coursera.project.dailyselfie.adapters.DailySelfiesAdapter;
import com.anuj.coursera.project.dailyselfie.models.DailySelfieRecord;

/**
 * Created by akulkarni on 029-29-11-14.
 */
public class DailySelfiesFragment extends ListFragment
{
    private DailySelfieRecord selectedSelfie;
    private DailySelfiesAdapter mDailySelfiesAdapter;

    public DailySelfiesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set custom adapter
        setListAdapter(mDailySelfiesAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDailySelfiesAdapter = new DailySelfiesAdapter(getActivity(), null, 0);
    }

    @Override
    public void onResume() {
        super.onResume();

        mDailySelfiesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy () {
        mDailySelfiesAdapter.freeResources();

        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //LAUNCH SELFIE DETAILS FRAGMENT
        selectedSelfie = (DailySelfieRecord) mDailySelfiesAdapter.getItem(position);

        if(selectedSelfie != null) {
            DailySelfieDetailsFragment detailsFragment = new DailySelfieDetailsFragment(selectedSelfie);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.container, detailsFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    /**
     * Public Methods
     */

    public void addSelfie(DailySelfieRecord newSelfie) {
        if(mDailySelfiesAdapter != null) {
            mDailySelfiesAdapter.addSelfie(newSelfie);
        }
    }

    public void deleteSelectedSelfie() {
        if(mDailySelfiesAdapter != null) {
            mDailySelfiesAdapter.deleteSelfie(selectedSelfie);
        }
    }

    public void deleteAllSelfies() {
        if(mDailySelfiesAdapter != null) {
            mDailySelfiesAdapter.deleteAllSelfies();
        }
    }
}