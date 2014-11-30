package hat.viktorvarinsky.com;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import hat.viktorvarinsky.com.utils.Constants;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private RelativeLayout splashScreen;
    private Button start;
    private Button addWords;
    private TextView hiScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashScreen = (RelativeLayout) findViewById(R.id.splash_screen);
        splashScreen.setVisibility(View.VISIBLE);

        startSplashScreen();

        start = (Button) findViewById(R.id.main_start);
        addWords = (Button) findViewById(R.id.main_add_words);

        start.setOnClickListener(this);
        addWords.setOnClickListener(this);

        hiScore = (TextView) findViewById(R.id.main_hi_score_text);

        getHiScore();
    }

    private void startSplashScreen() {
        final Timer myTimer = new Timer();
        final Handler uiHandler = new Handler();
        myTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                uiHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        splashScreen.setVisibility(View.GONE);
                    }

                });

                myTimer.cancel();
            }

        }, 5L * 1000, 1);
    }

    private void getHiScore() {
        String hiScoreName = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).getString(Constants.HI_SCORE_NAME, "");
        int hiScoreValue = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).getInt(Constants.HI_SCORE_VALUE, 0);

        String hiScoreText = "";
        if (hiScoreName.contentEquals("") ||
                hiScoreValue == 0)
        {
            hiScoreText = getString(R.string.main_no_hi_score);
        }
        else
        {
            hiScoreText = String.format("%s = %d", hiScoreName, hiScoreValue);
        }

        hiScore.setText(hiScoreText);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId())
        {
            case R.id.main_start:
                intent = new Intent(this, GameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                startActivity(intent);
                break;

            case R.id.main_add_words:
                intent = new Intent(this, AddWordsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                startActivity(intent);
                break;
        }
    }
}
