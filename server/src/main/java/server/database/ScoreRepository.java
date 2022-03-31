package server.database;

import commons.game.HighScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<HighScore, Long> {
}
