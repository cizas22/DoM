package Utils;

import java.util.Base64;

public class EncryptUtils {
  public static final int UNSUPPORTED_ENCRYPTION = 2;
  public static final int BASE64_ENCRYPTION = 1;

  public static String atob(String value) {
    return new String(Base64.getDecoder().decode(value));
  }
}
