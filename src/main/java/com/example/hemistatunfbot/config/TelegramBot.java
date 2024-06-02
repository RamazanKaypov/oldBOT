package com.example.hemistatunfbot.config;

import com.example.hemistatunfbot.IMAGE.Image;
import com.example.hemistatunfbot.IMAGE.ImageRepository;
import com.example.hemistatunfbot.IMAGE.ImageService;
import com.example.hemistatunfbot.Student.Group;
import com.example.hemistatunfbot.Student.GroupRepository;
import com.example.hemistatunfbot.Student.Student;
import com.example.hemistatunfbot.Student.StudentRepository;
import com.example.hemistatunfbot.util.MessageUtil;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.hemistatunfbot.IMAGE.ImageService.uploadPath;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ImageRepository imageRepository;

//    private ImageService imageService;
//
//    @Autowired
//    public void YourTelegramBot(ImageService imageService) {
//        this.imageService = imageService;
//    }

    //Student student = new Student();
     @Autowired
     BotConfig config;

//    public TelegramBot(BotConfig config) {
//        this.config = config;
//        List<BotCommand> listtoCommands = new ArrayList<>();
//        listtoCommands.add(new BotCommand("/timetable", "расписание"));
//        listtoCommands.add(new BotCommand("/updete_my_group", "Update your group"));
//        listtoCommands.add(new BotCommand("/mydata", "Get your data"));
//        listtoCommands.add(new BotCommand("/help", "info how to use this bot"));
//
//    }

    static final String HELP_Text = """
            This bot.

            Type /start to see a welcome to message

            Type /help to see this message again

            Type /timetable  to see a timetable""";

    static final String ADMIN_text = """
            This is the admin panel.

            Type /groups get all grops

            Type /timetables get all timetable""";

    @Override
    public String getBotUsername() {
        return "https://t.me/HEMIS_TATU_NF_BOT";
    }

    @Override
    public String getBotToken() {
        return "6911870700:AAEsvFQmztuoT2IfjSqHte1rZQvWmmiHkmk";
    }

    boolean createGroup = false;

    String updateGroupName = "";
    boolean updateGroupNameBool = false;

    boolean createPhotoBool = false;

    boolean updateTimetable = false;

    String deleteTimetable="";


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String massageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (createGroup && chatId == config.getOwnerId()) {
                String groupName = update.getMessage().getText();
                if (groupRepository.existsByGroupName(groupName)) {
                    sentMessage(chatId, "This group exists ❌");
                } else {
                    Group group1 = new Group(null, groupName, null);
                    groupRepository.save(group1);
                    sentMessage(chatId, "Added group\uD83D\uDC4C");
                }
                createGroup = false;
            } else if (updateGroupNameBool && chatId == config.getOwnerId()) {
                Group group = groupRepository.findByGroupName(updateGroupName);
                String newName = update.getMessage().getText();
                if (groupRepository.existsByGroupNameAndIdNot(newName, group.getId())) {
                    sentMessage(chatId, "This group exists ❌");
                } else {
                    group.setGroupName(newName);
                    groupRepository.save(group);
                    sentMessage(chatId, "Updated group\uD83D\uDC4C");
                }
                updateGroupNameBool = false;
            } else {

                switch (massageText) {
                    case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        System.out.println("dsfs");
                        break;
                    case "/updete_my_group":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    case "/help":
                        sentMessage(chatId, HELP_Text);
                        break;
                    case "/timetable":
                        Student student1 = studentRepository.findById(chatId).get();
                        Group group = student1.getGroup();
                        Image image = group.getImage();
                        sendPhoto(image, chatId, "Расписание группы:" + group.getGroupName());
                        break;
                    case "/groups":
                        if (chatId == config.getOwnerId()) {
                            getALLGroups(chatId);
                        }
                        break;
                    case "/mydata":
                        Student student2 = studentRepository.findById(chatId).get();
                        String medata = "FirstName: " + student2.getFirstName() + "\n" +
                                "LastName:" + student2.getLastName() + "\n" +
                                "UserName:" + student2.getUserName() + "\n" +
                                "Group Name:" + student2.getGroup().getGroupName() + "\n" +
                                "Timetable:" + "/timetable";
                        sentMessage(chatId, medata);
                        break;
                    case "/admin":
                        if (chatId == config.getOwnerId()) {
                            sentMessage(chatId, ADMIN_text);
                        }
                        break;
                    case "/timetables":
                        if (chatId == config.getOwnerId()) {
                            getAllTimetableImage(chatId);
                        }
                        break;
                    default:
                        registerUser(chatId, update.getMessage());
                }
            }


        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String text = "";
            CallbackQuery ms = update.getCallbackQuery();

            if (callbackData.equals("AddGroup")) {
                text = """
                        Ведите название группы в виде:

                        204/21""";
                createGroup = true;
            } else if (callbackData.startsWith("group_data")) {
                String groupName = callbackData.substring(callbackData.indexOf(":") + 1);
                updateGroupName = groupName;
                Group group = groupRepository.findByGroupName(groupName);
                text = groupName;
                setDeleteAndUpdate(chatId, groupName);
            } else if (callbackData.equals("updateName")) {
                text = """
                        Ведите новое название  группы в виде:

                        204/21""";
                updateGroupNameBool = true;
            } else if (callbackData.equals("delete")) {
                Group group = groupRepository.findByGroupName(updateGroupName);
                groupRepository.deleteById(group.getId());
                text = "Successful deleted!";
            } else if (callbackData.equals("updateRas")) {
                getAllTimetable(chatId);
                updateTimetable = true;
            } else if (callbackData.startsWith("ras_data") && updateTimetable) {
                String rasName = callbackData.substring(callbackData.indexOf(":") + 1);
                Image ras = imageRepository.findByName(rasName);
                Group group = groupRepository.findByGroupName(updateGroupName);
                group.setImage(ras);
                groupRepository.save(group);
                updateTimetable = false;
                text = "Sucessfull updated Ras!";
            } else if (callbackData.equals("AddTimetable")) {
                text = "set photo";
                createPhotoBool = true;
            } else if (callbackData.startsWith("timetable_data")) {
                String timetableName = callbackData.substring(callbackData.indexOf(":") + 1);
                Image image = imageRepository.findByName(timetableName);
                sendPhoto(image, chatId, timetableName);
                setDeleteAndBack(chatId, timetableName);
                deleteTimetable=timetableName;
            } else if (callbackData.equals("delete_timetable")){
                Image byName = imageRepository.findByName(deleteTimetable);
                imageRepository.deleteById(byName.getId());
                text="Timetabble seccessful deleted!";
                deleteTimetable="";
                System.out.println("delete");
            } else if (callbackData.equals("back_timetable")){
                getAllTimetableImage(chatId);
                text="";
            }else if (callbackData.equals("back")){
                getALLGroups(chatId);
            }
            excuteEditMassegeText(text, chatId, messageId);

        }


    }


    private java.io.File downloadFileByUrl(String fileUrl) throws IOException {
        java.io.File tempFile = java.io.File.createTempFile("temp", "");
        FileUtils.copyURLToFile(new URL(fileUrl), tempFile);
        return tempFile;
    }

    private void getALLGroups(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Пожалуйста выберите GROUP!");
        List<Group> list = groupRepository.findAll();
        List<String> collect = list.stream().map(Group::getGroupName).collect(Collectors.toList());
        List<String> callback = collect.stream().map(r -> "group_data:" + r).collect(Collectors.toList());
        InlineKeyboardMarkup keyBoardByRow = MessageUtil.getKeyBoardByRow(2, collect, callback);
        List<List<InlineKeyboardButton>> keyboard = keyBoardByRow.getKeyboard();
        InlineKeyboardButton e1 = new InlineKeyboardButton();
        e1.setText("Add");
        e1.setCallbackData("AddGroup");
        keyboard.add(List.of(e1));
        keyBoardByRow.setKeyboard(keyboard);
        message.setReplyMarkup(keyBoardByRow);

        excuteMassege(message);
    }

    private void getAllTimetable(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Пожалуйста выберите расписание!");

        List<Image> list = imageRepository.findAll();
        List<String> name = list.stream().map(Image::getName).collect(Collectors.toList());
        List<String> callback = name.stream().map(r -> "ras_data:" + r).collect(Collectors.toList());
        InlineKeyboardMarkup keyBoardByRow = MessageUtil.getKeyBoardByRow(2, name, callback);
        List<List<InlineKeyboardButton>> keyboard = keyBoardByRow.getKeyboard();
        keyBoardByRow.setKeyboard(keyboard);
        message.setReplyMarkup(keyBoardByRow);

        excuteMassege(message);
    }

    private void getAllTimetableImage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Пожалуйста выберите расписание!");

        List<Image> list = imageRepository.findAll();
        List<String> name = list.stream().map(Image::getName).collect(Collectors.toList());
        List<String> callback = name.stream().map(r -> "timetable_data:" + r).collect(Collectors.toList());
        InlineKeyboardMarkup keyBoardByRow = MessageUtil.getKeyBoardByRow(2, name, callback);
        List<List<InlineKeyboardButton>> keyboard = keyBoardByRow.getKeyboard();
        InlineKeyboardButton e1 = new InlineKeyboardButton();
        e1.setText("Add");
        e1.setCallbackData("AddTimetable");
        keyboard.add(List.of(e1));
        keyBoardByRow.setKeyboard(keyboard);
        message.setReplyMarkup(keyBoardByRow);

        excuteMassege(message);
    }


    private void setDeleteAndUpdate(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        List<String> list = List.of("Изменить название группы", "Изменить расписание группы", "Удалить", "<<<");
        List<String> callback = List.of("updateName", "updateRas", "delete", "back");
        sendMessage.setReplyMarkup(MessageUtil.getKeyBoardByRow(1, list, callback));
        excuteMassege(sendMessage);
    }

    private void setDeleteAndBack(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        List<String> list = List.of("Удалить", "<<<");
        List<String> callback = List.of("delete_timetable", "back_timetable");
        sendMessage.setReplyMarkup(MessageUtil.getKeyBoardByRow(1, list, callback));
        excuteMassege(sendMessage);
    }

    private void registerUser(long chatId, Message message) {
        var groups = groupRepository.findAll();
        int i = 0;
        for (Group group : groups) {
            if (message.getText().equals(group.getGroupName())) {
                Student student1 = new Student();
                student1.setChatId(chatId);
                student1.setGroup(group);
                String userName = message.getChat().getUserName();
                student1.setUserName(userName.isEmpty() ? "EMPTY" : userName);
                student1.setFirstName(message.getChat().getFirstName());
                String lastName = message.getChat().getLastName();
                student1.setLastName(lastName.isEmpty() ? "EMPTY" : lastName);

                studentRepository.save(student1);
                i++;

            }
        }
        if (i == 0) {
            sentMessage(chatId, "Group not found!!!\uD83D\uDE21");
        } else sentMessage(chatId, "Sucessfull\uD83D\uDE07");

    }

    private void startCommandReceived(long chatId, String name) {

        String answer = EmojiParser.parseToUnicode("Hi, " + name + ":blush:\n\n" +
                "Задайте свою группу в виде:\n\n" +
                ">>>>204/21<<<<");
        log.info("Replied to user " + name);

        sentMessage(chatId, answer);
    }

    private void excuteMassege(SendMessage message) {
        try {
            execute(message);

        } catch (TelegramApiException e) {
            log.error("Errors: " + e.getMessage());
        }
    }

    private void sentMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        excuteMassege(message);
    }

    public void sendPhoto(Image image, Long chatId, String gN) {
        String p = uploadPath + "/" + image.getUploadPath();
        System.out.println(p);
        System.out.println(image.getUploadPath());
        File file = new File(p);
        System.out.println(file.exists());
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(file, image.getName() + ".jpg"));
        sendPhoto.setCaption(gN);


        try {
            execute(sendPhoto);
        } catch (Exception e) {
            e.printStackTrace();
            // Обработайте ошибку отправки фотографии
        }
    }

    private void excuteEditMassegeText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("ERROR:" + e.getMessage());
        }
    }


}

