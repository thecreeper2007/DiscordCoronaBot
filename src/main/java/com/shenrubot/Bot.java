package com.shenrubot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;



@SuppressWarnings("unchecked")
public class Bot extends ListenerAdapter {


    public static String keyloc = "/Users/the_creeper2007/Desktop/DiscordAutoReply/src/main/java/com/shenrubot/APIkey.txt";

    public static Map<String, String> map = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

    public static String baseURL = "https://api.thevirustracker.com/free-api?countryTotal=";
    public static String hypixelapi = "73156b08-2705-4f4e-ab2c-a11ed949469e"; //TODO make key more secure
    public static String hypixelGuildURL = "https://api.hypixel.net/guild?key=7527b342-6199-4945-bc97-2a2689dd1997&id=5f72342b8ea8c99dcdd19a0a";

    public static String getToken() {
        // pass the path to the file as a parameter

        File file = new File(keyloc);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                return sc.nextLine();
            }
        } catch (FileNotFoundException e) {

            System.out.println("cannot find api key file, Assuning running through heroku");
            return System.getenv("KEY");
        }
        return null;
    }

    public static void main(String[] args) throws LoginException, InterruptedException {

        //init map for country codes
        map = CountryCodes.getMap();

        //login
        try {
            String key = getToken();
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
    public String httpGET(@NotNull URL url) {
        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
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
            return "There have been an IO error";

        }

    }

    public int getLvl(int exp) {
        int level = 0;
        if (exp < 100000) level = 0;
        else if (exp < 250000) level = 1;
        else if (exp < 500000) level = 2;
        else if (exp < 1000000) level = 3;
        else if (exp < 1750000) level = 4;
        else if (exp < 2750000) level = 5;
        else if (exp < 4000000) level = 6;
        else if (exp < 5500000) level = 7;
        else if (exp < 7500000) level = 8;
        else if (exp >= 7500000 && exp < 15000000) level = (int) Math.floor((exp - 7500000) / 2500000) + 9;
        else if (exp >= 15000000) level = (int) Math.floor((exp - 15000000) / 3000000) + 12;
        return level;
    }

    public int xpNeeded(int exp) {
        //Assign a value to xpneeded based on guild.exp value
        int xpneeded = 0;
        if (exp < 100000) xpneeded = 100000 - exp;
        else if (exp < 250000) xpneeded = 250000 - exp;
        else if (exp < 500000) xpneeded = 500000 - exp;
        else if (exp < 1000000) xpneeded = 1000000 - exp;
        else if (exp < 1750000) xpneeded = 1750000 - exp;
        else if (exp < 2750000) xpneeded = 2750000 - exp;
        else if (exp < 4000000) xpneeded = 4000000 - exp;
        else if (exp < 5500000) xpneeded = 5500000 - exp;
        else if (exp < 7500000) xpneeded = 7500000 - exp;
        else if (exp >= 7500001 && exp < 15000000) xpneeded = 15000000 - exp;
        else if (exp >= 15000000) xpneeded = ((int) Math.round((Math.floor(exp / 3000000)) + 1) * 3000000) - exp;
        return xpneeded;
    }
    public void getGuildInfo(@NotNull MessageReceivedEvent event) throws MalformedURLException {
        URL myURL = new URL(hypixelGuildURL);
        String response = httpGET(myURL);

        JSONObject main = new JSONObject(response);


        if (!main.getBoolean("success")) {
            System.out.println("Severe issue with GetGuildInfo, likely that the URL needs updating.");
            return;
        }


        JSONObject guild = main.getJSONObject("guild");

        JSONArray members = guild.getJSONArray("members");

        String name = guild.getString("name");
        String tag = guild.getString("tag");
        int exp = guild.getInt("exp");
        int memberCount = members.length();
        String color = guild.getString("tagColor");
        int level = getLvl(exp);
        int EXPneeded = xpNeeded(exp);

        System.out.println("Guild " + name + " have the tag " + tag + " displayed in " + color + " and " + exp + " exp and have " + memberCount + " members");

        //create imbed
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Guild stats for " + name, null);
        //eb.setColor();

        //add data
        eb.addField("Guild Tag", tag, true);
        eb.addField("Total GEXP", Integer.toString(exp), true);
        eb.addField("Guild Level", Integer.toString(level), true);
        eb.addField("XP Needed to level up", Integer.toString(EXPneeded), true);
        eb.addField("Total Member Count", Integer.toString(memberCount), true);


        //add a footer
        //Instant instant = Instant.instant();

        String time = getCurrentTime();
        eb.setFooter("Powered by Hypixel API, Retrieved at " + time + " UTC", null);

        //add a thumbnail
        eb.setThumbnail("https://scontent.fcxh2-1.fna.fbcdn.net/v/t31.0-8/20545497_1421019531316844_3777826206999579994_o.png?_nc_cat=110&ccb=2&_nc_sid=09cbfe&_nc_ohc=5CxwfT6drMsAX9K_Ab7&_nc_ht=scontent.fcxh2-1.fna&oh=c9f46729136c3086328b40eb3dd199ef&oe=5FB70D0E");

        //send it
        event.getTextChannel().sendMessage(eb.build()).queue();

    }

    //get current timestamp for footer
    public String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public void getCoronaStats(@NotNull MessageReceivedEvent event, @NotNull String msg) {
        boolean isCountry = true;
        StringBuilder req = new StringBuilder();
        req.append(baseURL);
        MessageChannel channel = event.getChannel();
        String data;
        String country;
        try {
            country = msg.substring(8);
        } catch (StringIndexOutOfBoundsException e) {
            country = "ALL";
            isCountry = false;
        }
        if (map.get(country) == null) {
            channel.sendMessage("Please enter a valid country!").queue();
            return;
        }
        country = map.get(country);
        if (!country.equals("ALL")) {
            req.append(country.toUpperCase().trim());
        } else {
            req.setLength(0);
            req.append("https://api.thevirustracker.com/free-api?global=stats");
        }
        URL url;
        try {

            url = new URL(req.toString());
            data = httpGET(url);
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException onMessageRecieved");
            channel.sendMessage("There have been a MalformedURLException.  Try again.").queue();
            return;
        }
        //extract the data
        String title = data.substring(data.indexOf("title") + 8, data.indexOf(",", data.indexOf("title") + 7) - 1);
        String totalCases = data.substring(data.indexOf("total_cases") + 13, data.indexOf(",", data.indexOf("total_cases") + 12));
        String totalRecovered = data.substring(data.indexOf("total_recovered") + 17, data.indexOf(",", data.indexOf("total_recovered")));
        String totalDeaths = data.substring(data.indexOf("total_deaths") + 14, data.indexOf(",", data.indexOf("total_deaths")));
        String totalSeriousCases = data.substring(data.indexOf("total_serious_cases") + 21, data.indexOf(",", data.indexOf("total_serious_cases"))).trim();
        String DangerRanking = data.substring(data.indexOf("total_danger_rank") + 19, data.indexOf("}", data.indexOf("total_danger_rank")));
        String newCases = data.substring(data.indexOf("total_new_cases_today") + 23, data.indexOf(",", data.indexOf("total_new_cases_today")));

        //create embed
        EmbedBuilder eb = new EmbedBuilder();

        if (isCountry) {
            eb.setTitle("Coronavirus Stats for " + title, null);
        } else {
            eb.setTitle("World Coronavirus Stats", null);
        }
        //eb.setColor();

        //add description of the virus from wikipedia
        eb.setDescription("Coronavirus disease 2019 (COVID-19) is an infectious disease caused by severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2). " +
                "The disease was first identified in December 2019 in Wuhan, the capital of China's Hubei province, and has since spread globally.");


        //add data
        eb.addField("Total cases", totalCases, true);
        eb.addField("Total recoveries", totalRecovered, true);
        eb.addField("Total deaths", totalDeaths, true);
        eb.addField("Total serious cases", totalSeriousCases, true);
        eb.addField("New cases", newCases, true);
        if (isCountry) {
            eb.addField("Danger rank", DangerRanking, true);
        }


        //add a footer
        //Instant instant = Instant.instant();

        String time = getCurrentTime();
        eb.setFooter("Live coronavirus stats provided by: https://thevirustracker.com/ Retrieved at: " + time + " UTC", null);

        //add a thumbnail
        eb.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/8/82/SARS-CoV-2_without_background.png");

        //add a image
        //eb.setImage("https://phil.cdc.gov//PHIL_Images/23311/23311_lores.jpg");

        //send it
        channel.sendMessage(eb.build()).queue();

        //print raw debugging data
        System.out.println(httpGET(url));


        //System.out.println(req.toString());
        //channel.sendMessage(req.toString()).queue();
    }
    //My "main" function

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String msg = event.getMessage().getContentDisplay();
        if (msg.startsWith("corona?")) {
            getCoronaStats(event, msg);
        } else if (msg.contains("@Bot") || msg.contains("@CoronaStatsBot")) {
            TextChannel textChannel = event.getTextChannel();
            textChannel.sendMessage("*A Wild bot appears from the background...*").queue();
        } else if (msg.startsWith("!guildinfo")) {
            try {
                getGuildInfo(event);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

}
