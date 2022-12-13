package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.TelegramBotEntity;
import pro.sky.telegrambot.repository.TelegramBotRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final TelegramBotRepository telegramBotRepository;

    public TelegramBotUpdatesListener(TelegramBotRepository telegramBotRepository) {
        this.telegramBotRepository = telegramBotRepository;
    }

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            TelegramBotEntity bot = new TelegramBotEntity();

            long idChat = update.message().chat().id();
            String message = update.message().text();
            String messageFinal;

            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
            Matcher matcher = pattern.matcher(message);

            String dateReposit = null;
            String messageRepozit;
            if (matcher.matches()) {
                dateReposit = matcher.group(1);
                messageRepozit = matcher.group(3);
                messageFinal = messageRepozit;
                SendResponse response = telegramBot.execute(new SendMessage(idChat, "корректные даные"));
            } else {
                logger.warn("Incorrect message [no correct]");
                if (message != dateReposit) {
                    SendResponse response = telegramBot.execute(new SendMessage(idChat, "не коректные данные"));
                }
                return;
            }

            LocalDateTime dateTime = LocalDateTime.parse(dateReposit, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            bot.setIdChat(idChat);
            bot.setMessage(messageFinal);
            bot.setDateTime(dateTime);
            telegramBotRepository.save(bot);


            if (Objects.equals(update.message().text(), "/start")) {
                SendResponse response = telegramBot.execute(new SendMessage(idChat, "Hello!"));
            }


        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void otpravka() {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<TelegramBotEntity> repozit = telegramBotRepository.findByDateTime(time);
        System.out.println(time);

        for (TelegramBotEntity bot : repozit) {
            System.out.println(bot);
            if (bot != null) {
                logger.info("Notification with id = {} was sent", bot.getId());
                SendResponse response = telegramBot.execute(new SendMessage(bot.getIdChat(), "отправка из другого метода!"));

            }
        }

    }


}
