package bot.Rules.MessageRules;

import java.util.Properties;

public class VaderRule extends MessageRule {
    private static final String VADER_GIF = "http://49.media.tumblr.com/0794b0f2331f54400118a38cec2bdadd/tumblr_o0gmnacvmB1tu6tfso1_500.gif";

    public VaderRule(final Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && (
                        msg.toLowerCase().contains("faith") ||
                        msg.toLowerCase().contains("disturbing")
        );
    }

    public String handle(final String msg, final String botId) {
        return VADER_GIF;
    }
}
