package bot.Rules.DirectMessageRules;

import bot.Helpers.GoogleImageHelper;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImageRule extends AbstractDirectMessageRule {
    private final GoogleImageHelper googleImageHelper;

    @Autowired
    public ImageRule(final GoogleImageHelper googleImageHelper) {
        this.googleImageHelper = googleImageHelper;
    }

    @Override
    public boolean canHandle(final String msg, final String botId, final boolean isDirect) {
        return super.canHandle(msg, botId, isDirect) && msg.contains("image me");
    }

    @Override
    public String handle(final String msg, final String botId, SlackSession session) {
        try {
            return this.googleImageHelper.search(super.handle(msg, botId, session).replace("image me", "").trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
