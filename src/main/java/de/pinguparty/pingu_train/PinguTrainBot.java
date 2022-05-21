package de.pinguparty.pingu_train;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pinguparty.pingu_train.domain.ReceivedMessage;
import de.pinguparty.pingu_train.exception.MessageSendFailException;
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

    @Value("${pingu_train.bot.username}")
    private String botUsername;

    @Value("${pingu_train.bot.token}")
    private String botToken;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

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
        if (message.hasText()) {
            ReceivedMessage textMessage =  new ReceivedMessage()
                    .setText(message.getText())
                    .setChatID(message.getChatId().toString())
                    .setUserName(message.getFrom().getLastName())
                    .setUserID(message.getFrom().getId().toString())
                    .setTimestamp(Instant.ofEpochMilli(message.getDate()));

            try {
                String jsonString = objectMapper.writeValueAsString(textMessage);
                rabbitTemplate.convertAndSend(queue.getName(), jsonString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
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

    public Integer sendTextMessage(String chatID, String message) throws MessageSendFailException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);
        try {
            return execute(sendMessage).getMessageId();
        } catch (Exception e) {
            throw new MessageSendFailException();
        }
    }
}
