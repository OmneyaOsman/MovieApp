package com.vision.movieapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vision.movieapp.R;
import com.vision.movieapp.adapters.ReviewsAdapter;
import com.vision.movieapp.models.Trailer;
import com.vision.movieapp.utils.ParseJSON;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vision on 1/26/16.
 */
public class FragmentDialog extends DialogFragment {


    private ListView listView;
    private static final String TAG = "Alert Dialog";
//    private String mUrl ;

//    public void setURL (String mUrl){
//        this.mUrl = mUrl ;
//    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Reviews");


        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.dialog_fragment, null);
        listView = (ListView) view.findViewById(R.id.extras_list_view);

        sendRequest();

        builder.setView(view);
        Dialog dialog = builder.create();
        return dialog;
    }


    private void sendRequest() {

        Bundle args = getArguments();
        String mUrl = args.getString("mURL");
        final String path = args.getString("path");

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (path.equals("reviews")) {
                    showReviewsJSON(response);
                } else if (path.equals("videos")) {
                    showTrailersJSON(response);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue request = Volley.newRequestQueue(getActivity());
        request.add(jsonObject);
    }


    private void showReviewsJSON(JSONObject response) {
        ReviewsAdapter adapter = new ReviewsAdapter(getActivity(), ParseJSON.extractComments(response));
        listView.setAdapter(adapter);
        listView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


    }

    private void showTrailersJSON(JSONObject response) {

        ArrayList<String> trailersTitle = new ArrayList<>();
        final ArrayList<String> keysTitle = new ArrayList<>();

        for (Trailer trailer : ParseJSON.extractKeys(response)) {

            trailersTitle.add(trailer.getName());
            keysTitle.add(trailer.getKey());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.trailers_list_item,
                R.id.trailer_title,
                trailersTitle);

        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String key = keysTitle.get(position);
                watchYoutubeVideo(key);



            }
        });

    }

    public  void watchYoutubeVideo(String key){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

}
