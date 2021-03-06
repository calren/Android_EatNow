package com.caren.eatnow.fragments;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.caren.eatnow.R;
import com.caren.eatnow.activities.SearchActivity;
import com.caren.eatnow.helpers.ImageHelpers;
import com.caren.eatnow.models.YelpBusiness;
import com.caren.eatnow.models.YelpBusinesses;

import java.util.List;

public class BrowseFragment extends Fragment {

    private TextView tvName;
    private ImageView ivPicture;
    private TextView tvDistance;
    private ImageView ivRating;
    private TextView tvRatingCount;
    private TextView tvDescription;
    private TextView tvCity;
    private Button btnYelpLink;

    private YelpBusiness b;
    private int numOfResultToShow;
    View view;

    public static BrowseFragment newInstance(int numOfResult) {
        BrowseFragment fragmentBrowse = new BrowseFragment();
        Bundle args = new Bundle();
        args.putInt("num", numOfResult);
        fragmentBrowse.setArguments(args);
        return fragmentBrowse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numOfResultToShow = getArguments().getInt("num", 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_item, container, false);
        view.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        List<YelpBusiness> results = new YelpBusinesses().getResults();

       if (results.size() > 0) {
           b = results.get(numOfResultToShow);
           setUpInformation();
           setUpListeners();
       } else {
           Intent i = new Intent(getActivity(), SearchActivity.class);
           startActivity(i);
       }

        return view;
    }

    public void setUpListeners() {
        btnYelpLink.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String url = b.getYelpLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void setUpInformation() {
        btnYelpLink = (Button) view.findViewById(R.id.btnYelpLink);

        tvName = (TextView) view.findViewById(R.id.tvName);
        tvName.setText(b.getName());

        ivPicture = (ImageView) view.findViewById(R.id.ivImage);
        new ImageHelpers.DownloadImageTask(ivPicture).execute(b.getImage());

        tvDistance = (TextView) view.findViewById(R.id.tvDistance);
        tvDistance.setText(b.getAddress());

        tvCity = (TextView) view.findViewById(R.id.tvCity);
        tvCity.setText(b.getCity());

        ivRating = (ImageView) view.findViewById(R.id.ivRating);
        new ImageHelpers.DownloadImageTask(ivRating).execute(b.getRating());

        tvRatingCount = (TextView) view.findViewById(R.id.tvNumOfReviews);
        tvRatingCount.setText(b.getNumOfReviews());

        tvDescription = (TextView) view.findViewById(R.id.tvCategory);
        tvDescription.setText(b.getDescription());
    }


}
