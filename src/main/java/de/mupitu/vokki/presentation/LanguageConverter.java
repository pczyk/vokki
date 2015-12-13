package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.words.boundary.LanguageManager;
import de.mupitu.vokki.business.words.entity.Language;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

@Named(value = "languageConverter")
public class LanguageConverter implements Converter {

    @EJB
    LanguageManager languageManager;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null) {
            return null;
        }
        
        try {
            final long id = Long.parseLong(value);
            
            return languageManager.findById(id);
        } catch(final NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(!(value instanceof Language)) {
            return null;
        }
        
        final Language language = (Language) value;
        
        return language.getId() + "";
    }
    
}
