package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.TelegramBotEntity;
import pro.sky.telegrambot.repository.TelegramBotRepository;

import java.util.List;

@Service
public class TelegramBotService {
    private final TelegramBotRepository telegramBotRepozitor;

    public TelegramBotService(TelegramBotRepository telegramBotRepozitor) {
        this.telegramBotRepozitor = telegramBotRepozitor;
    }

    public TelegramBotEntity addBot(TelegramBotEntity bot) {
        return telegramBotRepozitor.save(bot);

    }

    public List<TelegramBotEntity> allBot() {
        return telegramBotRepozitor.findAll();

    }

    //@Scheduled(fixedDelay = 1_000L)
    public List<TelegramBotEntity> message() {
        return telegramBotRepozitor.message();

    }


}
