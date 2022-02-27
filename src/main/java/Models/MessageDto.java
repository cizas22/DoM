package Models;

import lombok.Data;

@Data
public class MessageDto {
  private String adId;
  private String message;
  private String reward;
  private int expiresIn;
  private int encrypted;
}
