package com.example.hemistatunfbot;

import com.example.hemistatunfbot.IMAGE.ImageService;
import com.example.hemistatunfbot.config.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.generics.TelegramBot;

@SpringBootApplication
public class HemisTatuNfBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(HemisTatuNfBotApplication.class, args);
    }

//    @Bean
//    public TelegramBot telegramBot(BotConfig botConfig, ImageService imageService) {
//        TelegramBot telegramBot = new TelegramBot(botConfig, imageService) {
//            @Override
//            public String getBotUsername() {
//                return null;
//            }
//
//            @Override
//            public String getBotToken() {
//                return null;
//            }
//        };
//        return telegramBot;
//    }

}
