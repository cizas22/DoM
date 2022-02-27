package Models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GameDto {
  private String gameId;
  private String shoppingSuccess;
  private int lives;
  private int gold;
  private int level;
  private int score;
  private int highScore;
  private int turn;
}
