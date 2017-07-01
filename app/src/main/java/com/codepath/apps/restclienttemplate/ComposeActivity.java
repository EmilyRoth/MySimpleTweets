package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    EditText et_simple;
    TwitterClient client;
    Button btPost;
    Tweet tweet;
    TextView tvCount;
    String handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        et_simple = (EditText) findViewById(R.id.et_simple);
        btPost = (Button) findViewById(R.id.btPost);
        client = TwitterApp.getRestClient();
        tvCount = (TextView) findViewById(R.id.tvCount);
        if(getIntent().getStringExtra("tvHandle") != null){
            handle ="@"+ getIntent().getStringExtra("tvHandle");
        }
        else
            handle = "";

        et_simple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = 140-s.length();
                tvCount.setText(Integer.toString(count));
                if(count<0){
                    tvCount.setTextColor(Color.RED);
                } else
                    tvCount.setTextColor(Color.GRAY);

            }

        });
        et_simple.setText(handle);

    }//on create


    public void onSubmit(View v) {
        String body = et_simple.getText().toString();

        client.sendTweet(body, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    tweet = Tweet.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("tweet", Parcels.wrap(tweet));
                // ints work too
                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        // Prepare data intent

    }

}//class
