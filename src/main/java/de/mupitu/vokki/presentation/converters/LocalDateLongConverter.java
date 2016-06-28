package de.mupitu.vokki.presentation.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("localDateLongConverter")
public class LocalDateLongConverter implements Converter {

    private final static String PATTERN = "dd. MMMM yyyy";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN, Locale.GERMANY);

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        try {
            return LocalDate.parse(value);
        } catch (final DateTimeParseException e) {
            throw new ConverterException(e);
        }
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        if(value == null) {
            return null;
        }
        
        if(!(value instanceof LocalDate)) {
            return null;
        }
        
        final LocalDate date = (LocalDate) value;
        
        return date.format(formatter);
    }

}
