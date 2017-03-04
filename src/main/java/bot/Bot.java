package bot;

import bot.Listeners.MessagePostedListener;
import bot.Rules.DirectMessageRules.*;
import bot.Rules.MessageRules.TealcRule;
import bot.Rules.MessageRules.VaderRule;
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
    private Properties properties = new Properties();

    public Bot() {
        try {
            InputStream inputStream = new FileInputStream("env.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try {
            SlackSession session = SlackSessionFactory.createWebSocketSlackSession(properties.getProperty("BOT_ID"));
            session.addMessagePostedListener(new MessagePostedListener(this.getRules()));
            session.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Rule> getRules(){
        List<Rule> rules = new ArrayList<>();
        rules.addAll(this.getMessageRules());
        rules.addAll(this.getDirectMessageRules());
        return rules;
    }

    private List<Rule> getMessageRules(){
        List<Rule> rules = new ArrayList<>();
        rules.add(new TealcRule(properties));
        rules.add(new VaderRule(properties));
        return rules;
    }

    private List<Rule> getDirectMessageRules(){
        List<Rule> rules = new ArrayList<>();
        rules.add(new XkcdMessageRule(properties));
        rules.add(new ImageRule(properties));
        rules.add(new PugRule(properties));
        rules.add(new CorgiRule(properties));
        rules.add(new HelpRule(properties));
        rules.add(new TranslateRule(properties));
        rules.add(new SaveRule(properties));
        return rules;
    }
}
