package com.caren.eatnow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.caren.eatnow.R;
import com.caren.eatnow.activities.SearchActivity;

public class EndOfResultsFragment extends Fragment {

    Button btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end, container, false);
        view.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        btnSearch = (Button) view.findViewById(R.id.btnNewSearch);

        setUpListeners();

        return view;
    }

    private void setUpListeners() {
        btnSearch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });
    }

}
