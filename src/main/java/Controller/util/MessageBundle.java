package Controller.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageBundle {

    private static final String BUNDLE_PATH="localization/messages";

    private ResourceBundle messages;

    public MessageBundle(String languageTag) {
        Locale locale = new Locale(languageTag);
        this.messages = ResourceBundle.getBundle(BUNDLE_PATH, locale);
    }

    public String get(String message) {
        return messages.getString(message);
    }

    public final String get(final String key, final Object... args) {
        return MessageFormat.format(get(key), args);
    }
}
