package bot.Rules.DirectMessageRules;

import bot.Helpers.GoogleImageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImageRule extends DirectMessageRule {
    private final GoogleImageHelper googleImageHelper;

    @Autowired
    public ImageRule(final GoogleImageHelper googleImageHelper) {
        this.googleImageHelper = googleImageHelper;
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && msg.contains("image me");
    }

    @Override
    public String handle(final String msg, final String botId) {
        try {
            return this.googleImageHelper.search(super.handle(msg, botId).replace("image me", "").trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
