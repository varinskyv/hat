package hat.viktorvarinsky.com.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Random;

import hat.viktorvarinsky.com.core.Word;
import hat.viktorvarinsky.com.core.Words;

/**
 * Created by vit on 30.11.14.
 */
public class WordsListGenerator extends AsyncTask<Integer, Integer, ArrayList<String>> {

    private Context context;
    private OnGenerateWordList onGenerateWordList;

    public interface OnGenerateWordList
    {
        public void onWordsListGenerated(ArrayList<String> result);
    }

    public WordsListGenerator(Context context, OnGenerateWordList onGenerateWordList)
    {
        super();

        this.context = context;
        this.onGenerateWordList = onGenerateWordList;
    }

    @Override
    protected ArrayList<String> doInBackground(Integer... params) {
        ArrayList<String> result = new ArrayList<String>();

        Words words = new Words(context);

        if (params[0] == 3)
        {
            for (Word word: words.words)
            {
                result.add(word.word);
            }
        }
        else
        {
            for (Word word: words.words)
            {
                if (word.type == params[0])
                    result.add(word.word);
            }
        }

        if (params[1] > 1)
        {
            while (result.size() > params[1] * 3)
            {
                Random random = new Random();
                int i = random.nextInt(result.size() - 1);
                result.remove(i);
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result)
    {
        if (onGenerateWordList != null)
            onGenerateWordList.onWordsListGenerated(result);
    }

}
