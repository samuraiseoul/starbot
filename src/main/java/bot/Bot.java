package bot;

import bot.Listeners.MessagePostedListener;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("bot")
public class Bot {
    private final String botId;

    private final MessagePostedListener messagePostedListener;

    @Autowired
    public Bot(@Value("${BOT_ID}") final String botId, MessagePostedListener messagePostedListener) {
        this.botId = botId;
        this.messagePostedListener = messagePostedListener;
    }

    public void start(){
        try {
            final SlackSession session = SlackSessionFactory.createWebSocketSlackSession(this.botId);
            session.addMessagePostedListener(this.messagePostedListener);
            session.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
