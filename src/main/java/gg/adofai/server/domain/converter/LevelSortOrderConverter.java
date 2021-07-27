package gg.adofai.server.domain.converter;

import gg.adofai.server.domain.vo.LevelSortOrder;
import org.springframework.core.convert.converter.Converter;
import reactor.util.annotation.NonNullApi;

public class LevelSortOrderConverter implements Converter<String, LevelSortOrder> {
    @Override
    public LevelSortOrder convert(String source) {
        return LevelSortOrder.valueOf(source);
    }
}
