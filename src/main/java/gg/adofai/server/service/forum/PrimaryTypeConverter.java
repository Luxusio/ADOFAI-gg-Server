package gg.adofai.server.service.forum;

import lombok.NonNull;
import org.springframework.util.NumberUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrimaryTypeConverter {

    public static @NonNull List<String> toStringList(String text)  {
        if (text == null || text.isEmpty()) return List.of("");
        return Arrays.stream(text.split("&"))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static <T extends Number> T safeNumber(Number number, Class<T> type) {
        return number == null ? null : NumberUtils.convertNumberToTargetClass(number, type);
    }

    public static Double safeDouble(Number number) {
        return safeNumber(number, Double.class);
    }

    public static Long safeLong(Number number) {
        return safeNumber(number, Long.class);
    }

    public static Integer safeInteger(Number number) {
        return safeNumber(number, Integer.class);
    }

    public static <T> @NonNull T safeValue(T value, @NonNull T defaultValue) {
        return value == null ? defaultValue : value;
    }

//    @SuppressWarnings("unchecked")
//    public static <T> Class<T> getGenericClass() {
//        Type[] actualTypeArguments = ((ParameterizedType) new ArrayList<T>(0).getClass()
//                .getGenericSuperclass()).getActualTypeArguments();
//        Type clazz = actualTypeArguments[0];
//        return (Class<T>) clazz.getClass();
//    }

}
