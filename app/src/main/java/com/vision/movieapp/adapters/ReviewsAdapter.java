package com.vision.movieapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vision.movieapp.R;
import com.vision.movieapp.models.ReviewComment;

import java.util.List;

/**
 * Created by Vision on 08/11/2016.
 */

public class ReviewsAdapter extends ArrayAdapter<ReviewComment> {

    private Context context = getContext();



    public ReviewsAdapter(Context context, List<ReviewComment> objects) {
        super(context, 0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(context).inflate(R.layout.review_list_item , parent , false);
        }

        ReviewComment currentReviewComment = getItem(position);
        TextView authorName = (TextView) listItemView.findViewById(R.id.author_tv);
        authorName.setText(currentReviewComment.getAuthorName());
        TextView content = (TextView) listItemView.findViewById(R.id.content_tv);
        content.setText(currentReviewComment.getContent());

        return listItemView ;
    }
}
