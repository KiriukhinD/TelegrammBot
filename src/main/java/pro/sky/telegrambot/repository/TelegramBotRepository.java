package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.TelegramBotEntity;

public interface TelegramBotRepository extends JpaRepository<TelegramBotEntity, Long> {


}
