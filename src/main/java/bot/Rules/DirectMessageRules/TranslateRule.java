package bot.Rules.DirectMessageRules;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TranslateRule extends AbstractDirectMessageRule {
    private final String googleKey;

    public TranslateRule(@Value("${GOOGLE_KEY}") final String googleKey) {
        this.googleKey = googleKey;
    }

    @Override
    public boolean canHandle(final String msg, final String botId, final boolean isDirect) {
        return super.canHandle(msg, botId, isDirect) && msg.contains("translate");
    }

    @Override
    public String handle(final String msg, final String botId, SlackSession session) {
        final List<String> options = new ArrayList<>(Arrays.asList(super.handle(msg, botId, session).toLowerCase().replace("translate", "").trim().split(" ")));
        if(options.size() < 3){
            return "Invalid format, please enter in format of: translate {source} {target} {query}!";
        }
        final String source = this.convertToIso639(options.remove(0));
        final String target = this.convertToIso639(options.remove(0));
        final String query = String.join(" ", options);
        final Translate translator = TranslateOptions.newBuilder().setApiKey(this.googleKey).build().getService();
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
