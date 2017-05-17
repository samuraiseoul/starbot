import bot.Bot;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class StarBot {
    public static void main(final String[] args){

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
                BotConfig.class);

        final Bot starbot = (Bot)context.getBean("bot");
        starbot.start();
    }
}
