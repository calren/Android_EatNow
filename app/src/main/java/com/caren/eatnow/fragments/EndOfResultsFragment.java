package com.caren.eatnow.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caren.eatnow.R;

public class EndOfResultsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end, container, false);
        view.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        return view;
    }

}
