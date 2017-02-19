package bot;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPersona;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Bot {
    public void start(){
        try {
            InputStream inputStream = new FileInputStream("env.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            SlackSession session = SlackSessionFactory.createWebSocketSlackSession(properties.getProperty("BOT_ID"));
            session.addMessagePostedListener(new SlackMessagePostedListener() {
                public void onEvent(SlackMessagePosted slackMessagePosted, SlackSession slackSession) {
                    SlackChannel channelOnWhichMessageWasPosted = slackMessagePosted.getChannel();
                    String messageContent = slackMessagePosted.getMessageContent();
                    SlackUser messageSender = slackMessagePosted.getSender();

                    SlackPersona self = slackSession.sessionPersona();
                    if(self.getId().equals(messageSender.getId())) { return; }

                    String msg = "user: " + messageSender.getRealName()
                            + " on channel: " + channelOnWhichMessageWasPosted
                            + " sent message: " + messageContent
                            + " with subtype: " + slackMessagePosted.getMessageSubType().toString();
                    slackSession.sendMessage(channelOnWhichMessageWasPosted, msg);
                }
            });
            session.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
