package utils;

import java.util.UUID;

/**
 * @author 陈希冉
 * @version 1.0
 * 演示
 */
public class UuidUtil {
    public static String getHashCode() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "");
        return uuidStr;
    }
}
