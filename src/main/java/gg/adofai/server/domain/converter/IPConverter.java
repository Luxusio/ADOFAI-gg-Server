package gg.adofai.server.domain.converter;

public class IPConverter {

    public static String convertString(long ip) {
        return String.valueOf((ip / 16777216) & 0xff) + '.' +
                ((ip / 65536) & 0xff) + '.' +
                ((ip / 256) & 0xff) + '.' +
                (ip & 0xff);
    }

    public static long convertLong(String ip) {
        if (ip != null && !ip.isEmpty()) {
            String[] args = ip.split("\\.");
            return 16777216 * Long.parseLong(args[0]) +
                    65536 * Long.parseLong(args[1]) +
                    256 * Long.parseLong(args[2]) +
                    Long.parseLong(args[3]);
        }
        return 0;
    }

}
