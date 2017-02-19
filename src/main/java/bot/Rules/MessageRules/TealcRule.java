package bot.Rules.MessageRules;

import java.util.Properties;

public class TealcRule extends MessageRule{
    private static final String TEALC_GIF = "http://4.bp.blogspot.com/-TahRr7ackxY/UU38wPEpacI/AAAAAAAALGA/a8DAVIQYLD0/s1600/indeed.gif";

    public TealcRule(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(String msg, String botId) {
        return super.canHandle(msg, botId) && (
                        msg.toLowerCase().contains(" indeed ")
                        || msg.toLowerCase().equals("indeed")
                        || msg.toLowerCase().equals("indeed.")
                        || msg.toLowerCase().contains("indeed ")
                        || msg.toLowerCase().contains(" indeed.")
        );
    }

    public String handle(String msg, String botId) {
        return TEALC_GIF;
    }
}
