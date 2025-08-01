package org.example;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class QuoteService {

    public QuoteService() {
    }

    public String UrlBuilder(){
        return "https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=ru";
    }

    public String getQuote() throws IOException {
        URL url = new URL(UrlBuilder());
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        String response = stringBuilder.toString();
        JSONObject jsonObject = new JSONObject(response);
        String reply = jsonObject.getString("quoteText");
        System.out.println(reply);

        return reply;
    }
}
