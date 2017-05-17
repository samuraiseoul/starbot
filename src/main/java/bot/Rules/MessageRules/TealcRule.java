package bot.Rules.MessageRules;

import org.springframework.stereotype.Component;

@Component
public class TealcRule extends MessageRule{
    private static final String TEALC_GIF = "http://4.bp.blogspot.com/-TahRr7ackxY/UU38wPEpacI/AAAAAAAALGA/a8DAVIQYLD0/s1600/indeed.gif";

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
        return TEALC_GIF;
    }
}
