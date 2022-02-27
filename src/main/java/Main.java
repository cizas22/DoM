import Models.GameDto;
import Models.ItemDto;
import Models.MessageDto;
import Service.GameService;

import java.util.List;

import static Utils.EncryptUtils.BASE64_ENCRYPTION;
import static Utils.EncryptUtils.atob;
import static Utils.GameUtils.*;

public class Main {

  public static void main(String[] args) {
    var gameService = new GameService();
    var gameObject = gameService.startGame();
    System.out.println(gameObject);
    GameDto solve;
    GameDto lastSolved = null;
    int deathCounter = 0;
    int boughtLivesCounter = 0;
    do {
      List<MessageDto> messages = gameService.getAllMessages(gameObject.getGameId());
      MessageDto highestMessageScore = getHighestRewardMessage(messages);

      System.out.println(highestMessageScore);
      solve = gameService.solveMessage(gameObject.getGameId(), highestMessageScore.getAdId());

      if (highestMessageScore.getEncrypted() == BASE64_ENCRYPTION) {
        solve =
            gameService.solveMessage(gameObject.getGameId(), atob(highestMessageScore.getAdId()));
      }

      if (lastSolved != null && lastSolved.getLives() > solve.getLives()) {
        deathCounter++;
      }

      if (solve != null && solve.getGold() >= HEALING_POT_PRICE && deathCounter >= 1
          || solve.getLives() <= MINIMUM_LIVES_TO_HEAL && solve.getGold() >= HEALING_POT_PRICE) {
        gameService.buyItem(gameObject.getGameId(), HEALING_POT_ID);
        System.out.println("BOUGHT: HEALING POT");
        deathCounter = 0;
        boughtLivesCounter++;
      }

      if (boughtLivesCounter >= 2 && !isDead(solve)) {
        List<ItemDto> items = gameService.getAllItemsInStore(gameObject.getGameId());
        List<ItemDto> itemsWithWeapons = filterItemsOnlyWeapons(items, solve.getGold());
        if (itemsWithWeapons.size() >= 1) {
          ItemDto randomWeapon = getRandomItem(itemsWithWeapons);
          System.out.println("BOUGHT: " + randomWeapon);
          gameService.buyItem(gameObject.getGameId(), randomWeapon.getId());
        }
        boughtLivesCounter = 0;
      }
      System.out.println(solve);

      lastSolved = solve;

    } while (!isDead(solve));

    System.out.println("GAME OVER");
  }
}
