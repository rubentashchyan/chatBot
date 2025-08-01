package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MyBot extends TelegramLongPollingBot {

   private final  Set <Long> awaitingCityInput =new HashSet<>();


    @Override
    public String getBotUsername() {
        return "EggmontBot";
    }

    @Override
    public String getBotToken() {
        return "7710315425:AAFlAxkbjIlzeCQ8M0RE_WfNnFZngUGLyNk";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            String userInput = message.getText();
            SendMessage reply = new SendMessage();
            reply.setChatId(String.valueOf(chatId));
            String replyMessage;
            if (awaitingCityInput.contains(chatId)) {
                CityService cityService = new CityService();
                try {
                    replyMessage= cityService.processCity(userInput);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                reply.setText(replyMessage);
                awaitingCityInput.remove(chatId);
            }
            if (message.getText().equals("/start")) {
                replyMessage= "привет, я бот, меня зовут Эггмонт Роботник";

                reply.setText(replyMessage);
                reply.setReplyMarkup(getMainKeyboard());


            } else if (message.getText().equals("/help")) {

                reply.setChatId(String.valueOf(chatId));
                 replyMessage = "Доступные команды:\n"
                        + "/start — Начать работу с ботом\n"
                        + "/help — Помощь и список команд\n"
                        + "/weather — Узнать погоду\n"
                        + "/joke — Получить случайную шутку\n"
                        + "/quote — Мотивирующая цитата дня";

                reply.setText(replyMessage);
                reply.setReplyMarkup(getMainKeyboard());

            } else if (message.getText().equals("/weather")) {
                reply.setChatId(String.valueOf(chatId));
                replyMessage = "Выберите ваш город";
                reply.setText(replyMessage);

                awaitingCityInput.add(chatId);




            } else if (message.getText().equals("/joke")) {
                reply.setChatId(String.valueOf(chatId));
                JokeService service= new JokeService();
                try {
                    replyMessage = service.getJoke();
                    reply.setText(replyMessage);
                    reply.setReplyMarkup(getMainKeyboard());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (message.getText().equals("/quote")) {
                reply.setChatId(String.valueOf(chatId));
                QuoteService service= new QuoteService();
                try {
                    replyMessage=service.getQuote();
                    reply.setText(replyMessage);
                    reply.setReplyMarkup(getMainKeyboard());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            try {
                execute(reply);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private ReplyKeyboardMarkup getMainKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/start");
        row.add("/help");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("/weather");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("/joke");
        row3.add("/quote");

        rows.add(row);
        rows.add(row2);
        rows.add(row3);
        keyboard.setKeyboard(rows);
        return keyboard;
    }
}
