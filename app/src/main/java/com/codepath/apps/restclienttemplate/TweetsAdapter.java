package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    //Pass in the context and list of tweets using the constructor
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    //Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the data at position
        Tweet tweet = tweets.get(position);

        //Bind the tweet with view holder
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    //Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimeStamp;
        TextView tvUserName;
        ImageView ivEmbeddedImage;

        //itemView represents one row in the recycler view = a single tweet
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivEmbeddedImage = itemView.findViewById(R.id.ivEmbeddedImage);
        }

        public void bind(Tweet tweet) {
            tvUserName.setText(tweet.user.name);
            tvBody.setText(tweet.body);
            tvScreenName.setText("@" + tweet.user.screenName);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            String relativeTimeAgo = getRelativeTimeAgo(tweet.createdAt);
            tvTimeStamp.setText(relativeTimeAgo);
            //display embedded image when there exists one
            if(tweet.hasEntities){
                ivEmbeddedImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.entityUrl).into(ivEmbeddedImage);
            }
            else{
                ivEmbeddedImage.setVisibility(View.GONE);
            }
        }
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] num = relativeDate.split(" ");
        relativeDate = num[0] + num[1].charAt(0);
        if (num[1].charAt(0) == '0') {
            relativeDate = "Now";
        }
        return relativeDate;
    }

//    public String getRelativeTimeAgo(String rawJsonDate) {
//        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
//        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
//        sf.setLenient(true);
//
//        try {
//            long time = sf.parse(rawJsonDate).getTime();
//            long now = System.currentTimeMillis();
//
//            final long diff = now - time;
//            if (diff < MINUTE_MILLIS) {
//                return "just now";
//            } else if (diff < 2 * MINUTE_MILLIS) {
//                return "a minute ago";
//            } else if (diff < 50 * MINUTE_MILLIS) {
//                return diff / MINUTE_MILLIS + " m";
//            } else if (diff < 90 * MINUTE_MILLIS) {
//                return "an hour ago";
//            } else if (diff < 24 * HOUR_MILLIS) {
//                return diff / HOUR_MILLIS + " h";
//            } else if (diff < 48 * HOUR_MILLIS) {
//                return "yesterday";
//            } else {
//                return diff / DAY_MILLIS + " d";
//            }
//
//            // Grab the index of the first space in the returned relative data string
//        } catch (ParseException e) {
//            Log.i(TAG, "getRelativeTimeAgo failed");
//            e.printStackTrace();
//        }
//
//        return "";
//    }
}