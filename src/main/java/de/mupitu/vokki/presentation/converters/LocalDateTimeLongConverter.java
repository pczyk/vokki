package de.mupitu.vokki.presentation.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("localDateTimeLongConverter")
public class LocalDateTimeLongConverter implements Converter {

    private final static String PATTERN = "dd. MMMM yyyy HH:mm:ss";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN, Locale.GERMANY);

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        try {
            return LocalDateTime.parse(value);
        } catch (final DateTimeParseException e) {
            throw new ConverterException(e);
        }
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        if(value == null) {
            return null;
        }
        
        if(!(value instanceof LocalDateTime)) {
            return null;
        }
        
        final LocalDateTime date = (LocalDateTime) value;
        
        return date.format(formatter);
    }

}
