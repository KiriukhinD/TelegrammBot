package pro.sky.telegrambot.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TelegramBotEntity {
    @Id
    @GeneratedValue
    private long id;
    private long idChat;
    private String message;

    private LocalDateTime dateTime;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String name) {
        this.message = name;
    }


    public long getIdChat() {
        return idChat;
    }

    public void setIdChat(long idChat) {
        this.idChat = idChat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramBotEntity that = (TelegramBotEntity) o;
        return id == that.id && idChat == that.idChat && Objects.equals(message, that.message) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TelegramBotEntity{" +
                "id=" + id +
                ", idChat=" + idChat +
                ", message='" + message + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
