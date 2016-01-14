package de.mupitu.vokki.presentation.utils;

import de.mupitu.vokki.business.words.entity.Language;

public class PrimeFacesKeyboardUtils {

    public static String getLayoutTemplateForLanguage(final Language language) {
        switch (language.getName()) {
            case "Polnisch":
                return "ąćęłńóśźż,ĄĆĘŁŃÓŚŹŻ";

            default:
                return null;
        }
    }
}
