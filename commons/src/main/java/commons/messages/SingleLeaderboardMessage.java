package commons.messages;

import commons.game.HighScore;
import java.util.List;

public class SingleLeaderboardMessage extends Message {
        private List<HighScore> scores;

        @SuppressWarnings("unused")
        private SingleLeaderboardMessage() {
            // for object mapper
        }

        public SingleLeaderboardMessage(List<HighScore> list) {
            this.scores = list;
        }

        public List<HighScore> getList() {
            return scores;
        }
}
