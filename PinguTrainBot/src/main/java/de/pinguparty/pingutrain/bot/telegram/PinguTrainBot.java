package de.pinguparty.pingutrain.bot.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pinguparty.pingutrain.bot.domain.Location;
import de.pinguparty.pingutrain.bot.domain.ReceivedMessage;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;

@Component
public class PinguTrainBot extends TelegramLongPollingBot {

    @Value("${pingutrain.bot.telegram.token}")
    private String botToken;

    @Value("${pingutrain.bot.telegram.username}")
    private String botUsername;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue updatesQueue;

    @Autowired
    private Queue actionsQueue;

    private ObjectMapper objectMapper = new ObjectMapper();

    public PinguTrainBot() {
        objectMapper.findAndRegisterModules();
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update == null) {
            return;
        }
        if (!update.hasMessage()) {
            return;
        }

        Message message = update.getMessage();
        ReceivedMessage textMessage = new ReceivedMessage()
                .setChatID(message.getChatId().toString())
                .setUserID(message.getFrom().getId().toString())
                .setTimestamp(Instant.ofEpochSecond(message.getDate()))
                .setUserName(message.getFrom().getLastName())
                .setFirstName(message.getFrom().getFirstName())
                .setLastName(message.getFrom().getLastName());

        if (message.hasText()) {
            textMessage.setText(message.getText());
        }
        if(message.hasLocation()) {
            textMessage.setLocation(new Location().setLatitude(message.getLocation().getLatitude()).setLongitude(message.getLocation().getLongitude()));
        }

        try {
            String jsonString = objectMapper.writeValueAsString(textMessage);
            rabbitTemplate.convertAndSend(updatesQueue.getName(), jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /*if(update.hasMessage() && update.getMessage().hasText()) {
            messageDispatcher.dispatch(this, update.getMessage());
        }*/
        /*
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            SendLocation location = new SendLocation();
            location.setChatId(update.getMessage().getChatId().toString());
            //48.76105426898909, 9.162404302236942
            location.setLatitude(48.76105426898909);
            location.setLongitude(9.162404302236942);
            location.setHorizontalAccuracy(0.5);
            location.setLivePeriod(10000);
            location.setHeading(199);
            EditMessageLiveLocation editMessageLiveLocation = new EditMessageLiveLocation();
            SendDice sendDice = new SendDice();
            sendDice.setChatId(update.getMessage().getChatId().toString());
            sendDice.setEmoji("\uD83C\uDFB2");
            SendSticker sendSticker = new SendSticker();
            sendSticker.setSticker(new InputFile("CAACAgIAAxkBAAMcYoaZRxsRKdAZc-eXWLh3tt8OCUIAAo0DAAJHFWgJkKVpKPX6Bk4kBA"));
            sendSticker.setChatId(update.getMessage().getChatId().toString());

            HttpSender httpSender = new HttpSender();
            String response = null;
            try {
                response = httpSender.sendGET("https://www3.vvs.de/mngvvs/VELOC?CoordSystem=WGS84&LineID=j22-vvs-10006-H-&ModCode=0&ModCode=1&ModCode=3&ModCode=5&SpEncId=0&coordOutputFormat=EPSG:4326&outputFormat=rapidJSON&ts=1652988844993");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText(response);

            try {
                /*Message x = execute(location); // Call method to send the message
                Thread.sleep(5000);
                editMessageLiveLocation.setMessageId(x.getMessageId());
                editMessageLiveLocation.setChatId(update.getMessage().getChatId().toString());
                double longitude = Math.random() * 60;
                double latitude = Math.random() * 60;
                editMessageLiveLocation.setLatitude(latitude);
                editMessageLiveLocation.setLongitude(longitude);
                execute(editMessageLiveLocation);
                execute(sendMessage);
            } catch (TelegramApiException e) {
                eprintStackTrace();
            }
        }*/
    }
}
