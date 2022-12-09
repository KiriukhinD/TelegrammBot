package pro.sky.telegrambot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.entity.TelegramBotEntity;
import pro.sky.telegrambot.service.TelegramBotService;

import java.util.List;

@RestController
public class TelegramBotController {

    private final TelegramBotService telegramBotService;

    public TelegramBotController(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @PostMapping("/addBot")
    public TelegramBotEntity addBot(TelegramBotEntity bot) {
        return telegramBotService.addBot(bot);
    }

    @GetMapping("/allBot")
    public List<TelegramBotEntity> allBot() {
        return telegramBotService.allBot();
    }

}
