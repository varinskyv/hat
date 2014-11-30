package hat.viktorvarinsky.com.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vit on 30.11.14.
 */
public class Player implements Serializable {

    public String name = "";
    public int score = 0;

    public boolean equals(Object o) {
        if (!(o instanceof Player))
            return false;

        boolean result = false;
        Player player = (Player) o;
        if (this.name.contentEquals(player.name) &&
                this.score == player.score)
        {
            result = true;
        }

        return result;
    }

}
