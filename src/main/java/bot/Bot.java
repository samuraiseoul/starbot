package bot;

import bot.Listeners.MessagePostedListener;
import bot.Rules.DirectMessageRules.XkcdMessageRule;
import bot.Rules.MessageRules.TealcRule;
import bot.Rules.Rule;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Bot {
    public void start(){
        try {
            InputStream inputStream = new FileInputStream("env.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            SlackSession session = SlackSessionFactory.createWebSocketSlackSession(properties.getProperty("BOT_ID"));

            session.addMessagePostedListener(new MessagePostedListener(this.getRules()));
            session.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Rule> getRules(){
        List<Rule> rules = new ArrayList<Rule>();
        rules.addAll(this.getMessageRules());
        rules.addAll(this.getDirectMessageRules());
        return rules;
    }

    private List<Rule> getMessageRules(){
        List<Rule> rules = new ArrayList<Rule>();
        rules.add(new TealcRule());
        return rules;
    }

    private List<Rule> getDirectMessageRules(){
        List<Rule> rules = new ArrayList<Rule>();
        rules.add(new XkcdMessageRule());
        return rules;
    }
}
