package bot;

import bot.Listeners.MessagePostedListener;
import bot.Rules.Rule;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component("bot")
public class Bot {
    private final String botId;

    private final List<Rule> rules;

    @Autowired
    public Bot(@Value("${BOT_ID}") final String botId, List<Rule> rules) {
        this.botId = botId;
        try {
            final InputStream inputStream = new FileInputStream("env.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rules = rules;
    }

    public void start(){
        try {
            final SlackSession session = SlackSessionFactory.createWebSocketSlackSession(this.botId);
            session.addMessagePostedListener(new MessagePostedListener(this.rules));
            session.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
