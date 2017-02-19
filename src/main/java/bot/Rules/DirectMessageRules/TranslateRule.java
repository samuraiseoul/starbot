package bot.Rules.DirectMessageRules;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

import java.util.*;

public class TranslateRule extends DirectMessageRule {
    public TranslateRule(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(String msg, String botId) {
        return super.canHandle(msg, botId) && msg.contains("translate");
    }

    @Override
    public String handle(String msg, String botId) {
        List<String> options = new LinkedList<>(Arrays.asList(super.handle(msg, botId).toLowerCase().replace("translate", "").trim().split(" ")));
        if(options.size() < 3){
            return "Invalid format, please enter in format of: translate {source} {target} {query}!";
        }
        String source = this.convertToIso639(options.remove(0));
        String target = this.convertToIso639(options.remove(0));
        String query = String.join(" ", options);
        Translate translator = TranslateOptions.newBuilder().setApiKey(properties.getProperty("GOOGLE_KEY")).build().getService();
        return translator.translate(query, Translate.TranslateOption.sourceLanguage(source), Translate.TranslateOption.targetLanguage(target)).getTranslatedText();
    }

    private String convertToIso639(String language){
        if(language.length() == 2) { return language; }
        for(Locale locale : Locale.getAvailableLocales()) {
            if(language.toLowerCase().equals(locale.getDisplayLanguage().toLowerCase())){
                return locale.getISO3Language();
            }
        }
        return null;
    }
}
