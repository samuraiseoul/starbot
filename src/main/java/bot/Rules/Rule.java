package bot.Rules;

public interface Rule {
    boolean canHandle(String msg, String botId);

    String handle(String msg);
}
