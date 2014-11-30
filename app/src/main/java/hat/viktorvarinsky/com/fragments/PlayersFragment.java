package hat.viktorvarinsky.com.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import hat.viktorvarinsky.com.GameActivity;
import hat.viktorvarinsky.com.R;
import hat.viktorvarinsky.com.data.Player;
import hat.viktorvarinsky.com.data.Players;
import hat.viktorvarinsky.com.utils.Constants;
import hat.viktorvarinsky.com.utils.WordsListGenerator;

public class PlayersFragment extends Fragment implements View.OnClickListener, WordsListGenerator.OnGenerateWordList {

    private Activity activity;
    private String[] wordsTypesList;
    private Spinner wordsTypes;
    private EditText playerName;
    private LinearLayout playerContainer;
    private Players players;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players, null);

        activity = getActivity();

        wordsTypesList = activity.getResources().getStringArray(R.array.words_types);
        wordsTypes = (Spinner) view.findViewById(R.id.players_words_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, wordsTypesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordsTypes.setAdapter(adapter);

        playerName = (EditText) view.findViewById(R.id.players_add_text);

        ImageButton addButton = (ImageButton) view.findViewById(R.id.players_add_button);
        addButton.setOnClickListener(this);

        playerContainer = (LinearLayout) view.findViewById(R.id.players_container);

        players = new Players();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.players_add_button)
        {
            if (!playerName.getText().toString().contentEquals(""))
            {
                Player player = new Player();
                player.name = playerName.getText().toString();

                addPlayer(player);
            }

            if (players.playersList.size() > 1)
                ((GameActivity) activity).changeMenuVisible(true);
        }
    }

    private void addPlayer(Player player) {
        if (players.playersList.contains(player))
            return;

        players.playersList.add(player);

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        View view = new View(activity);
        view = View.inflate(activity, R.layout.item_player, null);

        TextView name = (TextView) view.findViewById(R.id.player_name);
        name.setText(playerName.getText());

        ImageButton button = (ImageButton) view.findViewById(R.id.player_delete);
        button.setOnClickListener(new DeletePlayerListener());

        lParams.setMargins(0, 8, 0, 8);

        view.setTag(name.getText());

        playerContainer.addView(view, lParams);
    }

    public void generateWordsList() {
        Integer[] params = new Integer[2];

        params[0] = wordsTypes.getSelectedItemPosition();
        params[1] = players.playersList.size();

        new WordsListGenerator(activity, this).execute(params);
    }

    @Override
    public void onWordsListGenerated(ArrayList<String> result) {
        GameplayFragment gameplayFragment = new GameplayFragment();

        Bundle args = new Bundle();
        args.putSerializable(Constants.PLAYERS_LIST, players);
        args.putStringArrayList(Constants.WORDS, result);

        gameplayFragment.setArguments(args);

        ((GameActivity) activity).changeMenuVisible(false);
        ((GameActivity) activity).changeFragment(gameplayFragment);
    }

    private class DeletePlayerListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

                playerContainer.removeView(playerContainer.findViewWithTag(v.getTag()));

        }

    }
}
