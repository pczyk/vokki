package de.mupitu.vokki.presentation.utils;

import de.mupitu.vokki.business.words.entity.Language;

public class PrimeFacesKeyboardUtils {

    /**
     * Returns the layout for a PrimeFaces &lt;p:keyboard&gt; element based on a
     * specified <code>language</code>. The layout contains the special
     * characters for that very language.
     *
     * @param language
     * @return special characters for <code>language</code>
     */
    public static String getLayoutTemplateForLanguage(final Language language) {
        switch (language.getName()) {
            case "Polnisch":
                return "ąćęłńóśźż,ĄĆĘŁŃÓŚŹŻ";

            default:
                return null;
        }
    }
}
