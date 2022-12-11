package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.entity.TelegramBotEntity;

import java.util.List;

public interface TelegramBotRepository extends JpaRepository<TelegramBotEntity, Long> {
    @Query(value = "select * from telegram_bot_entity d where d.date_time = current_timestamp", nativeQuery = true)
    List<TelegramBotEntity> message();

}
