//package com.example.hemistatunfbot;
//
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TEST {
//    else if (update.hasCallbackQuery()){
//
//        String callbackData=update.getCallbackQuery().getData();
//        long messageId = update.getCallbackQuery().getMessage().getMessageId();
//        long chatId=update.getCallbackQuery().getMessage().getChatId();
//        CallbackQuery ms=update.getCallbackQuery();
//        if (repository.findById(ms.getMessage().getChatId()).isEmpty()){
//
//            var chat=ms.getFrom();
//
//            student.setChatId(chatId);
//            student.setFirstName(chat.getFirstName());
//            student.setLastName(chat.getLastName());
//            student.setUserName(chat.getUserName());
//            student.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
//
//        }
//        if (callbackData.equals(KI_BUTTON)){
//            student.setFacultet("KI");
//            String text="KI";
//            excuteEditMassegeText(text,chatId,messageId);
//            choiceLanguage(chatId);
//        } else if (callbackData.equals(TELECOM_BUTTON)){
//            String text="TElecom";
//            student.setFacultet("telecom");
//            excuteEditMassegeText(text,chatId,messageId);
//            choiceLanguage(chatId);
//        }else if (callbackData.equals(RUS_Button)){
//            student.setLangueg("rus");
//            String text ="Rus";
//            excuteEditMassegeText(text,chatId,messageId);
//            choiceKurs(chatId);
//        }else if (callbackData.equals(QQ_Button)){
//            student.setLangueg("QQ");
//            String text ="QQ";
//            excuteEditMassegeText(text,chatId,messageId);
//            choiceKurs(chatId);
//        }else if (callbackData.equals(UZB_Button)){
//            student.setLangueg("UZB");
//            String text ="UZB";
//            excuteEditMassegeText(text,chatId,messageId);
//            choiceKurs(chatId);
//        }else if (callbackData.equals(ONE_Button)){
//            student.setKurs("1");
//            String text="1";
//            excuteEditMassegeText(text,chatId,messageId);
//            choiceNapravlenie(chatId);
//        }else if (callbackData.equals(TOW_Button)){
//            student.setKurs("2");
//            String text="2";
//            excuteEditMassegeText(text,chatId,messageId);
//            choiceNapravlenie(chatId);
//        }else if (callbackData.equals(THREE_Button)){
//            student.setKurs("3");
//            String text="3";
//            excuteEditMassegeText(text,chatId,messageId);
//            choiceNapravlenie(chatId);
//        }else if (callbackData.equals(FOUR_Button)){
//            student.setKurs("4");
//            String text="4";
//            excuteEditMassegeText(text,chatId,messageId);
//            choiceNapravlenie(chatId);
//        }
//
//        repository.save(student);
//        log.info("User saved: "+ student);;
//    }
//
//    private void registerUser(Message ms) {
//
//        if (repository.findById(ms.getChatId()).isEmpty()){
//
//            var chatId=ms.getChatId();
//            var chat=ms.getChat();
//
//            student.setChatId(chatId);
//            student.setFirstName(chat.getFirstName());
//            student.setLastName(chat.getLastName());
//            student.setUserName(chat.getUserName());
//            student.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
//
//            repository.save(student);
//            log.info("User saved: "+ student);
//        }
//    }
//    private void regiser(long chatId) {
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText("Пожалуйста выберите факультет!\n\n" +
//                "Iltimas facultet saylan'!\n\n" +
//                "Iltimos facultent saylang!");
//
//        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
//        List<InlineKeyboardButton> InLine = new ArrayList<>();
//
//        var KiButton = new InlineKeyboardButton();
//        KiButton.setText("KI");
//        KiButton.setCallbackData(KI_BUTTON);
//
//        var TelecomButton = new InlineKeyboardButton();
//        TelecomButton.setText("Telecom");
//        TelecomButton.setCallbackData(TELECOM_BUTTON);
//
//        InLine.add(KiButton);
//        InLine.add(TelecomButton);
//
//        rowsInLine.add(InLine);
//
//        markupInLine.setKeyboard(rowsInLine);
//
//        message.setReplyMarkup(markupInLine);
//
//        excuteMassege(message);
//    }
//
//    private void choiceLanguage(long chatId){
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText("Пожалуйста выберите язык!\n\n" +
//                "Iltimas til saylan'!\n\n" +
//                "Iltimos til saylang!");
//        System.out.println("65");
//        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
//        List<InlineKeyboardButton> InLine = new ArrayList<>();
//
//        var rusButton =new InlineKeyboardButton();
//        rusButton.setText("Рус");
//        rusButton.setCallbackData(RUS_Button);
//
//        var qqButton =new InlineKeyboardButton();
//        qqButton.setText("QQ");
//        qqButton.setCallbackData(QQ_Button);
//
//        var uzbButton =new InlineKeyboardButton();
//        uzbButton.setText("Uzb");
//        uzbButton.setCallbackData(UZB_Button);
//
//        InLine.add(rusButton);
//        InLine.add(qqButton);
//        InLine.add(uzbButton);
//
//        rowsInLine.add(InLine);
//
//        markupInLine.setKeyboard(rowsInLine);
//
//        message.setReplyMarkup(markupInLine);
//
//        excuteMassege(message);
//
//    }
//
//    private void  choiceKurs(long chatId){
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText("Пожалуйста выберите Курс!\n\n" +
//                "Iltimas Kurs saylan'!\n\n" +
//                "Iltimos kurs saylang!");
//
//        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
//        List<InlineKeyboardButton> InLine = new ArrayList<>();
//
//        var one =new InlineKeyboardButton();
//        one.setText("1");
//        one.setCallbackData(ONE_Button);
//
//        var tow =new InlineKeyboardButton();
//        tow.setText("2");
//        tow.setCallbackData(TOW_Button);
//
//        var three =new InlineKeyboardButton();
//        three.setText("3");
//        three.setCallbackData(THREE_Button);
//
//        var four=new InlineKeyboardButton();
//        four.setText("4");
//        four.setCallbackData(FOUR_Button);
//
//        InLine.add(one);
//        InLine.add(tow);
//        InLine.add(three);
//        InLine.add(four);
//
//        rowsInLine.add(InLine);
//
//        markupInLine.setKeyboard(rowsInLine);
//
//        message.setReplyMarkup(markupInLine);
//
//        excuteMassege(message);
//    }
//
//
//
//    private void  choiceNapravlenie(long chatId){
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText("Пожалуйста выберите Направление!\n\n" +
//                "Iltimas Jonelis saylan'!\n\n" +
//                "Iltimos Yonalish saylang!");
//
//        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
//        List<InlineKeyboardButton> InLine = new ArrayList<>();
//
//        var diButton =new  InlineKeyboardButton();
//        diButton.setText("DI");
//        diButton.setCallbackData(DI_BUTTON);
//
//        var kiButton =new InlineKeyboardButton();
//        kiButton.setText("KI");
//        kiButton.setCallbackData(KI_BUTTON);
//
//        var atButton=new InlineKeyboardButton();
//        atButton.setText("AT");
//        atButton.setCallbackData(AT_BUTTON);
//
//        InLine.add(kiButton);
//        InLine.add(diButton);
//        InLine.add(atButton);
//
//        rowsInLine.add(InLine);
//
//        markupInLine.setKeyboard(rowsInLine);
//
//        excuteMassege(message);
//    }
//    final String KI_BUTTON="KI_BUTTON";
//
//    final String TELECOM_BUTTON="TELECOM_BUTTON";
//
//    final String RUS_Button ="RUS_BUTTON";
//
//    final String QQ_Button="QQ_BUTTON";
//
//    final String UZB_Button="UZB_BUTTON";
//
//    final String ONE_Button ="ONE_BUTTON";
//
//    final String TOW_Button ="TOW_BUTTON";
//
//    final String THREE_Button ="THREE_BUTTON";
//
//    final String FOUR_Button ="FOUR_BUTTON";
//
//    final String DI_BUTTON="DI_BUTTON";
//
//    final String AT_BUTTON="AT_BUTTON";
//}
