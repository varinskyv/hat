package hat.viktorvarinsky.com.core;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import hat.viktorvarinsky.com.utils.Constants;

public class Words implements Serializable {

    public int count = 0;
    public ArrayList<Word> words = new ArrayList<Word>();

    public Words(Context context) {
        String json = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.WORDS, "");

        if (json.contentEquals(""))
            json = getStringFromAssetFile(context);

        Words words = new Gson().fromJson(json, Words.class);

        this.count = words.count;
        this.words = words.words;

        words = null;
    }

    private String getStringFromAssetFile(Context context)
    {
        StringBuilder total = new StringBuilder();

        try {
            InputStream inputStream = context.getAssets().open("words.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));

            String line = "";
            while ((line = reader.readLine()) != null) {
                total.append(line);
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return total.toString();
    }

}
