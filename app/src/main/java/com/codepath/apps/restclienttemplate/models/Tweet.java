package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public long  id;
    public boolean hasEntities;
    public String entityUrl;
    public User user;

    // empty constructor needed by the Parceler library
    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.hasEntities = false;
        //user is of type User, but .getJSONObject returns JSONObject,
        // so we need to convert to User object by making a method fromJson in User class
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        JSONObject entity = jsonObject.getJSONObject("entities");
        if(entity.has("media")){
            JSONArray mediaArray = entity.getJSONArray("media");
            if(mediaArray != null){
                tweet.hasEntities = true;
                tweet.entityUrl = mediaArray.getJSONObject(0).getString("media_url_https");
            }
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
