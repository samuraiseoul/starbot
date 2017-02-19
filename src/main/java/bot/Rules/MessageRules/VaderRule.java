package bot.Rules.MessageRules;

import java.util.Properties;

public class VaderRule extends MessageRule {
    private static final String VADER_GIF = "http://49.media.tumblr.com/0794b0f2331f54400118a38cec2bdadd/tumblr_o0gmnacvmB1tu6tfso1_500.gif";

    public VaderRule(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(String msg, String botId) {
        return super.canHandle(msg, botId) && (
                        msg.toLowerCase().contains("faith") ||
                        msg.toLowerCase().contains("disturbing")
        );
    }

    public String handle(String msg, String botId) {
        return VADER_GIF;
    }
}
