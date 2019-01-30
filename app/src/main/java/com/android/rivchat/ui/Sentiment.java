package com.android.rivchat.ui;

import android.widget.Toast;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public class Sentiment {

    public String SentimentAnalysis(final String msg) throws JSONException {

          /*Scanner scan = new Scanner(msg);
            String sentences = scan.nextLine();
            String[] count_words = sentences.split(" ");
            Integer tokens = count_words.length;*/


            //String text = "i am not in the right mood to talk to you";
            NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding("2018-03-16", "6cc3f45a-65ba-4d67-84c1-999e5306609e", "qKx8eIfysxSb");

            EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
                    .emotion(true)
                    .sentiment(true)
                    .limit(2)
                    .build();

            KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
                    .emotion(true)
                    .sentiment(true)
                    .limit(2)
                    .build();

            Features features = new Features.Builder()
                    .entities(entitiesOptions)
                    .keywords(keywordsOptions)
                    .build();

            AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                    .text(msg)
                    .features(features)
                    .build();

            AnalysisResults response = service
                    .analyze(parameters)
                    .execute();


            JSONObject results = new JSONObject(String.valueOf(response));
            JSONArray items = results.getJSONArray("keywords");
            JSONObject item = items.getJSONObject(0);
            JSONObject snippet = item.getJSONObject("emotion");

            Double anger = snippet.getDouble("anger");
            Double disgust = snippet.getDouble("disgust");
            Double fear = snippet.getDouble("fear");
            Double joy = snippet.getDouble("joy");
            Double sadness = snippet.getDouble("sadness");

            Double arr[] = {0.0, anger, disgust, fear, joy, sadness};
            String EmoArr[] = {"Neutral", "Angry", "Disgust", "Fear", "Happy", "Sad"};
            Double max = arr[0];
            String Emo = EmoArr[0];


    //        if (tokens > 6) {
                for (int i = 1; i < arr.length; i++) {
                    if (arr[i] > max) {
                        max = arr[i];
                        Emo = EmoArr[i];
                    }
                }
    //        } else {
    //            Emo = EmoArr[0];
    //        }


            return Emo;


        }


    }

