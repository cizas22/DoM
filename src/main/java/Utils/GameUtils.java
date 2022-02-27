package Utils;

import Models.GameDto;
import Models.ItemDto;
import Models.MessageDto;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static Utils.EncryptUtils.UNSUPPORTED_ENCRYPTION;

public class GameUtils {
  public static final String HEALING_POT_ID = "hpot";
  public static final int HEALING_POT_PRICE = 50;
  public static final int MINIMUM_LIVES_TO_HEAL = 2;

  public static List<ItemDto> filterItemsOnlyWeapons(List<ItemDto> list, int golds) {
    return list.stream()
        .filter(item -> !item.getId().equals(HEALING_POT_ID) && golds >= item.getCost())
        .collect(Collectors.toList());
  }

  public static ItemDto getRandomItem(List<ItemDto> list) {
    Random random = new Random();
    return list.get(random.nextInt(list.size()));
  }

  public static MessageDto getHighestRewardMessage(List<MessageDto> messages) {
    return messages.stream()
        .filter(message -> message.getEncrypted() != UNSUPPORTED_ENCRYPTION)
        .max(Comparator.comparing(MessageDto::getReward))
        .orElse(null);
  }

  public static boolean isDead(GameDto gameDto) {
    return gameDto != null && gameDto.getLives() <= 0;
  }
}
