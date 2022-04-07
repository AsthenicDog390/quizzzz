package client.comparators;

import commons.Player;

import java.util.Comparator;

public class ScoreComparator implements Comparator {

    /**
     * Comparator made for comparing two players scores by giving the players.
     * @param o1 - Object representing the first player to be compared.
     * @param o2 - Object representing the first player to be compared.
     * @return -> 0 If the scores are identical
     *         -> 1 If the second player has a bigger score
     *         -> -1 If the first player has a bigger score
     */
    public int compare(Object o1, Object o2) {
        Player p1 = (Player) o1;
        Player p2 = (Player) o2;
        if(p1.getScore() == p2.getScore()){
            return 0;
        }else if(p1.getScore() < p2.getScore()){
            return 1;
        }else{
            return -1;
        }
    }
}