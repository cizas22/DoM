package Service;

import Models.GameDto;
import Models.ItemDto;
import Models.MessageDto;
import Models.ReputationDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GameService {
  private static final String GAME_START_URL = "https://dragonsofmugloar.com/api/v2/game/start";
  private static final String MESSAGE_ALL_URL = "https://dragonsofmugloar.com/api/v2/%s/messages";
  private static final String SOLVE_MESSAGE_URL = "https://dragonsofmugloar.com/api/v2/%s/solve/%s";
  private static final String STORE_ITEMS_URL = "https://dragonsofmugloar.com/api/v2/%s/shop";
  private static final String BUY_ITEM_URL = "https://dragonsofmugloar.com/api/v2/%s/shop/buy/%s";
  private static final String REPUTATION_URL =
      "https://dragonsofmugloar.com/api/v2/%s/investigate/reputation";

  private final ApiService apiService = new ApiService();
  private final Gson gson = new Gson();

  public GameDto startGame() {
    JsonObject response = apiService.fetchPost(GAME_START_URL, null);
    return gson.fromJson(response, GameDto.class);
  }

  public List<MessageDto> getAllMessages(String gameId) {
    JsonArray response = apiService.fetchGet(pathBuilder(MESSAGE_ALL_URL, gameId));
    Type messageListType = new TypeToken<List<MessageDto>>() {}.getType();
    return gson.fromJson(response, messageListType);
  }

  public GameDto solveMessage(String gameId, String adId) {
    JsonObject response = apiService.fetchPost(pathBuilder(SOLVE_MESSAGE_URL, gameId, adId), null);

    return gson.fromJson(response, GameDto.class);
  }

  public List<ItemDto> getAllItemsInStore(String gameId) {
    JsonArray response = apiService.fetchGet(pathBuilder(STORE_ITEMS_URL, gameId));
    Type itemsListType = new TypeToken<List<ItemDto>>() {}.getType();
    return gson.fromJson(response, itemsListType);
  }

  public GameDto buyItem(String gameId, String itemId) {
    JsonObject response = apiService.fetchPost(pathBuilder(BUY_ITEM_URL, gameId, itemId), null);
    return gson.fromJson(response, GameDto.class);
  }

  public ReputationDto getReputation(String gameId) {
    JsonObject response = apiService.fetchPost(pathBuilder(REPUTATION_URL, gameId), null);
    return gson.fromJson(response, ReputationDto.class);
  }

  private String pathBuilder(String url, String... variables) {
    return String.format(url, variables);
  }
}
