package co.edu.uptc.model;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static final String BUNDLE = "i18n.messages";
    private static Locale locale = Locale.forLanguageTag("es");
    private static ResourceBundle bundle = cargar(locale);

    private I18n() {
    }

    private static ResourceBundle cargar(Locale l) {
        return ResourceBundle.getBundle(BUNDLE, l, ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES));
    }

    public static void setIdioma(String codigo) {
        String c = (codigo == null || codigo.isBlank()) ? "es" : codigo.trim().toLowerCase();
        if (!c.equals("es") && !c.equals("en")) {
            c = "es";
        }
        locale = Locale.forLanguageTag(c);
        bundle = cargar(locale);
    }

    public static String getIdioma() {
        return locale.getLanguage();
    }

    public static Locale getLocale() {
        return locale;
    }

    public static String get(String clave, Object... args) {
        if (!bundle.containsKey(clave)) {
            return clave;
        }
        String patron = bundle.getString(clave);
        return args == null || args.length == 0 ? patron : new MessageFormat(patron, locale).format(args);
    }
    
}
