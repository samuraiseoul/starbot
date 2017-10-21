package bot.Rules.MessageRules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SealRule extends MessageRule{
    private final String sealGif;

    public SealRule(@Value("${SEAL}") final String sealGif) {
        this.sealGif = sealGif;
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && (
                msg.toLowerCase().contains(" gay ")
                        || msg.toLowerCase().equals("gay")
                        || msg.toLowerCase().equals("gay.")
                        || msg.toLowerCase().contains("gay ")
                        || msg.toLowerCase().contains(" gay.")
        );
    }

    public String handle(final String msg, final String botId) {
        return this.sealGif;
    }
}
