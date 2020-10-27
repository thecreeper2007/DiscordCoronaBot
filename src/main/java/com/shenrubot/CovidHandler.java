package com.shenrubot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

public class CovidHandler {
    public static Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static String baseURL = "https://api.thevirustracker.com/free-api?countryTotal=";
    public static void getCoronaStats(@NotNull MessageReceivedEvent event, @NotNull String msg) {
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
            data = Bot.httpGET(url);
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

        String time = Util.getCurrentTime();
        eb.setFooter("Live coronavirus stats provided by: https://thevirustracker.com/ Retrieved at: " + time + " UTC", null);

        //add a thumbnail
        eb.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/8/82/SARS-CoV-2_without_background.png");

        //add a image
        //eb.setImage("https://phil.cdc.gov//PHIL_Images/23311/23311_lores.jpg");

        //send it
        channel.sendMessage(eb.build()).queue();

        //print raw debugging data
        System.out.println(Bot.httpGET(url));


        //System.out.println(req.toString());
        //channel.sendMessage(req.toString()).queue();
    }
}
