package com.shenrubot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {
    public static String key = "NzAwODMwMTI5MjY5NjM3MjMw.XpopHA.wlNTRwROa0_BonvKEvVd3AWICOY";
    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = new JDABuilder(key).addEventListeners(new Bot()).build();

        jda.awaitReady();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        System.out.println("Message Recieved!");
        if (!event.getAuthor().isBot()) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue();
        }
    }
}
