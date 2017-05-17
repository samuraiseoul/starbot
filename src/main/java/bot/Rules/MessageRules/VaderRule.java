package bot.Rules.MessageRules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VaderRule extends MessageRule {
    private final String vaderGif;

    public VaderRule(@Value("${VADER}") final String vaderGif) {
        this.vaderGif = vaderGif;
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && (
                        msg.toLowerCase().contains("faith") ||
                        msg.toLowerCase().contains("disturbing")
        );
    }

    public String handle(final String msg, final String botId) {
        return this.vaderGif;
    }
}
