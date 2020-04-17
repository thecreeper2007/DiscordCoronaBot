package com.shenrubot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {
    public static String key = "NzAwODMwMTI5MjY5NjM3MjMw.XpopHA.wlNTRwROa0_BonvKEvVd3AWICOY";
    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = new JDABuilder(key).build();

        jda.awaitReady();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.isFromType(ChannelType.PRIVATE){
            System.out.println("Message Recieved!");
        }
    }
}
