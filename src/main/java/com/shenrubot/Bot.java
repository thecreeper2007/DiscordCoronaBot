package com.shenrubot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Bot extends ListenerAdapter {
    public static String keyloc = "/Users/the_creeper2007/Desktop/DiscordAutoReply/src/main/java/com/shenrubot/APIkey.txt";

    public static void main(String[] args) {

        //init map for country codes
        CovidHandler.map = CountryCodes.getMap();

        //login
        try {
            String key = Util.getToken(keyloc);
            JDA jda = new JDABuilder(key).addEventListeners(new Bot()).build();
            jda.awaitReady();
            //set game
            jda.getPresence().setActivity(Activity.watching("COVID-19 | Corona?"));
        } catch (Exception e) {
            System.err.println("Cannot login to (or start) Discord. is the API key correct?");
            System.exit(1);
        }
    }

    //make http get request to get up-to-date coronavirus stats
    public static String httpGET(@NotNull URL url) {
        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (ProtocolException e) {
            e.printStackTrace();
            System.err.println("ProtocolException cought");
            return "There have been a networking protocol exception";
        } catch (IOException i) {
            i.printStackTrace();
            System.err.println("IOException cought");
            return "There has been an IO error";
        }
    }

    //My "main" function

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String msg = event.getMessage().getContentDisplay();
        if (msg.startsWith("corona?")) {
            CovidHandler.getCoronaStats(event, msg);
        } else if (msg.contains("@Bot") || msg.contains("@CoronaStatsBot")) {
            TextChannel textChannel = event.getTextChannel();
            textChannel.sendMessage("*A Wild bot appears from the background...*").queue();
        } else if (msg.startsWith("!guildinfo")) {
            try {
                HypixelHandler.getGuildInfo(event);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
