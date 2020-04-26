package com.shenrubot;
//https://pastebin.com/03sVdXUa
//jda

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

//util
//net
//exceptions

public class Bot extends ListenerAdapter {
    public static String key = "NzAwODMwMTI5MjY5NjM3MjMw.XpopHA.wlNTRwROa0_BonvKEvVd3AWICOY";
    public static String baseURL = "https://api.thevirustracker.com/free-api?countryTotal=";
    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = new JDABuilder(key).addEventListeners(new Bot()).build();

        jda.awaitReady();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String req = baseURL;
        System.out.println("Message pinged14!");
        String msg = event.getMessage().getContentDisplay();
        if (msg.startsWith("corona?")) {
            String country = msg.substring(7, 9);
            string req = baseURL + country;
            MessageChannel channel = event.getChannel();

            channel.sendMessage("");
        }
    }
}
