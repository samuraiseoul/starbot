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

    private List<Rule> rules;

    public MessagePostedListener(List<Rule> rules) {
        this.rules = rules;
    }

    public void onEvent(SlackMessagePosted event, SlackSession session) {
        SlackChannel channelOnWhichMessageWasPosted = event.getChannel();
        String messageContent = event.getMessageContent();
        SlackUser messageSender = event.getSender();
        SlackPersona self = session.sessionPersona();
        if(self.getId().equals(messageSender.getId())) { return; }

        for (Rule rule: this.rules) {
            if(rule.canHandle(messageContent, self.getId())){
                session.sendMessage(channelOnWhichMessageWasPosted, rule.handle(messageContent));
            }
        }
    }
}
