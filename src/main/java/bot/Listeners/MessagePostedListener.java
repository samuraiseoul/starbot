package bot.Listeners;

import bot.Rules.Rule;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPersona;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessagePostedListener implements SlackMessagePostedListener{

    private final List<Rule> rules;

    @Autowired
    public MessagePostedListener(final List<Rule> rules) {
        this.rules = rules;
    }

    public void onEvent(final SlackMessagePosted event, final SlackSession session) {
        final SlackChannel channelOnWhichMessageWasPosted = event.getChannel();
        final String messageContent = event.getMessageContent();
        final SlackUser messageSender = event.getSender();
        final SlackPersona self = session.sessionPersona();
        if(self.getId().equals(messageSender.getId())) { return; }

        for (final Rule rule: this.rules) {
            if(rule.canHandle(messageContent, self.getId(), channelOnWhichMessageWasPosted.isDirect()) && !messageSender.isBot()){
                try {
                    session.sendMessage(channelOnWhichMessageWasPosted, rule.handle(messageContent, self.getId()));
                    break;
                } catch(Throwable t) {
                    t.printStackTrace();
                    session.sendMessage(channelOnWhichMessageWasPosted, "Oops sorry, something went wrong, please contact my creator.");
                }
            }
        }
    }
}
