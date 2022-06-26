package ui.ex1.entity;


import com.google.common.base.Strings;
import io.jmix.core.metamodel.annotation.DatatypeDef;
import io.jmix.core.metamodel.annotation.Ddl;
import io.jmix.core.metamodel.datatype.Datatype;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.Locale;

@DatatypeDef(
        id = "color",
        javaClass = Integer.class
)
@Ddl("int")
public class ColorDatatype implements Datatype<Integer> {

// TODO something meaningful

    private static final String PATTERN = " ^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";

    @Override
    public String format(@Nullable Object value) {
        if (value == null)
            return "#FFFFFF";
        return String.format(String.valueOf(value), PATTERN);
    }

    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public Integer parse(@Nullable String value) throws ParseException {
        if (Strings.isNullOrEmpty(value))
            return null;
        return Integer.parseInt(value);
    }

    @Nullable
    @Override
    public Integer parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }
}