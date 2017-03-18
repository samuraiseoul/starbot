package bot.Listeners;

import bot.Rules.Rule;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPersona;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

import java.util.List;

public class MessagePostedListener implements SlackMessagePostedListener{

    private final List<Rule> rules;

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
            if(rule.canHandle(messageContent, self.getId()) && !messageSender.isBot()){
                try {
                    session.sendMessage(channelOnWhichMessageWasPosted, rule.handle(messageContent, self.getId()));
                } catch(Throwable t) {
                    t.printStackTrace();
                    session.sendMessage(channelOnWhichMessageWasPosted, "Oops sorry, something went wrong, please contact my creator.");
                }
            }
        }
    }
}
