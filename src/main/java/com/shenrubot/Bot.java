package com.shenrubot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;

public class Bot extends ListenerAdapter {
    public static String keyloc = "/Users/the_creeper2007/Desktop/DiscordAutoReply/src/main/java/com/shenrubot/APIkey.txt";
    public static String WolframURL = "http://api.wolframalpha.com/v1/simple?appid=7J6V33-YHP7EXYTVG&i=";

    public static void main(String[] args) {

        //init map for country codes
        CovidHandler.map = CountryCodes.getMap();

        //login
        try {
            String key = Util.getToken(keyloc);
            JDA jda = JDABuilder.createDefault(key).addEventListeners(new Bot()).build();
            jda.awaitReady();
            //set game

            //jda.getPresence().setActivity(Activity.watching("COVID-19 | Corona?"));
            jda.getPresence().setActivity(Activity.watching("Maintnance Mode"));

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

    public static void getHTTPImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        String fileName = url.getFile();
        String destName = "/Users/the_creeper2007/Desktop/DiscordAutoReply/src/main/java/com/shenrubot/img.gif";
        System.out.println(destName);

        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destName);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
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
        } else if (msg.contains("ez")) {
            //System.out.println("recieved ez");
            TextChannel chan = event.getTextChannel();
            User user = event.getAuthor();
            String userid = user.getId();
            Message Msg = event.getMessage();
            Msg.delete().queue();
            chan.sendMessage("<@" + userid + "> *said:* " + EzCommands.getEzMessage()).queue();
        } else if (msg.startsWith("!lookup")) {

            String trimmedMsg = msg.substring(7);
            trimmedMsg = trimmedMsg.trim();
            StringBuilder builder = new StringBuilder(WolframURL);
            System.out.println(builder.toString());

            @Deprecated
            String encodedURL = URLEncoder.encode(trimmedMsg);
            System.out.println(encodedURL);
            builder.append(encodedURL);
            System.out.println(builder.toString());
            URL url = null;
            EmbedBuilder embed = new EmbedBuilder();
            TextChannel channel = event.getTextChannel();
            try {
                url = new URL(builder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String strout = httpGET(url);
            if (strout.equals("There has been an IO error")) {
                File file = new File("/Users/the_creeper2007/Desktop/DiscordAutoReply/src/main/java/com/shenrubot/nooutput.gif");
                embed.setImage("attachment://calc.gif").setDescription(trimmedMsg).setFooter("Powered by WolframAlpha");
                channel.sendFile(file).embed(embed.build()).queue();
                //exits
            } else {
                try {
                    getHTTPImage(builder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!strout.equals("There has been an IO error")) {

                File file = new File("/Users/the_creeper2007/Desktop/DiscordAutoReply/src/main/java/com/shenrubot/img.gif");
                embed.setImage("attachment://calc.gif").setDescription(trimmedMsg).setFooter("Powered by WolframAlpha.");
                channel.sendFile(file).embed(embed.build()).queue();
            }
        }
    }
}
