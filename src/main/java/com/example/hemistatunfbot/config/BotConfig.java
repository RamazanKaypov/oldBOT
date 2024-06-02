package com.example.hemistatunfbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("application.properties")
public class BotConfig {

    private long ownerId=1;
    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;

}

