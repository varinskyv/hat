package hat.viktorvarinsky.com;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import hat.viktorvarinsky.com.data.Words;
import hat.viktorvarinsky.com.fragments.PlayersFragment;


public class GameActivity extends ActionBarActivity {

    private MenuItem startMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        PlayersFragment playersFragment = new PlayersFragment();
        changeFragment(playersFragment);
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game, menu);
        startMenu = menu.getItem(0);
        startMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_play) {
            PlayersFragment pf = (PlayersFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);
            pf.generateWordsList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeMenuVisible(boolean param) {
        startMenu.setVisible(param);
    }

}
