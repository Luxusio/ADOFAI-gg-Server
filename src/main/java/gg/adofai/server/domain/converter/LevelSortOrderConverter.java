package gg.adofai.server.domain.converter;

import gg.adofai.server.domain.vo.level.LevelSortOrder;
import org.springframework.core.convert.converter.Converter;

public class LevelSortOrderConverter implements Converter<String, LevelSortOrder> {
    @Override
    public LevelSortOrder convert(String source) {
        return LevelSortOrder.valueOf(source);
    }
}
