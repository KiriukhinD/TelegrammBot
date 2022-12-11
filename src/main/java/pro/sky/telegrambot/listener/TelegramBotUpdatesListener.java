package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.TelegramBotEntity;
import pro.sky.telegrambot.repository.TelegramBotRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
            long chatId = update.message().chat().id();
            if (Objects.equals(update.message().text(), "/start")) {
                SendResponse response = telegramBot.execute(new SendMessage(chatId, "Hello!"));
            }
            TelegramBotEntity bot = new TelegramBotEntity();
            long idChat = update.message().chat().id();
            String message = update.message().text();
            LocalDateTime dateTime = LocalDateTime.now();
            bot.setIdChat(idChat);
            bot.setMessage(message);
            bot.setDateTime(dateTime);
            telegramBotRepository.save(bot);


        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
