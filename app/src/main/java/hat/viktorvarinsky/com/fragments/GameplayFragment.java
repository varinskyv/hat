package hat.viktorvarinsky.com.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import hat.viktorvarinsky.com.R;
import hat.viktorvarinsky.com.data.Players;
import hat.viktorvarinsky.com.utils.Constants;

public class GameplayFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private Players players;
    private ArrayList<String> wordsList;
    private ArrayList<String> wordsInHat = new ArrayList<String>();
    private TextView playerName;
    private TextView playerScore;
    private TextView timerText;
    private ImageView hat;
    private TextView wordText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gameplay, null);

        activity = getActivity();

        Bundle args = getArguments();
        players = (Players) args.getSerializable(Constants.PLAYERS_LIST);
        wordsList = (ArrayList<String>) args.getStringArrayList(Constants.WORDS);
        wordsInHat.addAll(wordsList);

        playerName = (TextView) view.findViewById(R.id.game_player_name);
        playerScore = (TextView) view.findViewById(R.id.game_player_score);
        timerText = (TextView) view.findViewById(R.id.game_timer);
        wordText = (TextView) view.findViewById(R.id.game_word);

        playerName.setText(players.playersList.get(0).name);
        playerScore.setText(String.valueOf(players.playersList.get(0).score));

        hat = (ImageView) view.findViewById(R.id.game_hat);
        hat.setOnClickListener(this);

        wordText.setText("");

        new AlertDialog.Builder(activity)
                .setTitle("Инструкция")
                .setMessage("Для начала отсчета времени нажмите на шляпу. Для подтверждения отгаданного слова нажмите на шляпу")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (wordsInHat.size() > 0)
        {
            int i = 0;
            Random random = new Random();
            if (wordsInHat.size() != 1)
                i = random.nextInt(wordsInHat.size() - 1);
            wordText.setText(wordsInHat.get(i).toString());
            wordsInHat.remove(i);
        }
    }
}
