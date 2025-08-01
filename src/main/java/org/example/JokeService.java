package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class JokeService {

    public JokeService() {
    }

    public String urlBuilder(){
         return "http://rzhunemogu.ru/RandJSON.aspx?CType=1 ";
    }

    public String getJoke() throws IOException {
        URL url = new URL(urlBuilder());
        HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("Windows-1251"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String responce = stringBuilder.toString();
        bufferedReader.close();
        String marker = "\"content\":\"";
        int index = responce.lastIndexOf(marker);
        int starIndex = index + marker.length();
        int endIndex = responce.indexOf("\"", starIndex);

        String joke = responce.substring(starIndex, endIndex);
        joke= joke.replace("\\n", "\n").replace("\\\"", "\"");
        System.out.println(joke);


        return joke;
    }
}
