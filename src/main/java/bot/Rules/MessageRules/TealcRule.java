package bot.Rules.MessageRules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TealcRule extends MessageRule{
    private final String tealcGif;

    public TealcRule(@Value("${TEALC}") final String tealcGif) {
        this.tealcGif = tealcGif;
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && (
                        msg.toLowerCase().contains(" indeed ")
                        || msg.toLowerCase().equals("indeed")
                        || msg.toLowerCase().equals("indeed.")
                        || msg.toLowerCase().contains("indeed ")
                        || msg.toLowerCase().contains(" indeed.")
        );
    }

    public String handle(final String msg, final String botId) {
        return this.tealcGif;
    }
}
