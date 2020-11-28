package com.shenrubot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

public class HypixelHandler {
    public static String hypixelGuildURL = "https://api.hypixel.net/guild?key=7527b342-6199-4945-bc97-2a2689dd1997&id=5f72342b8ea8c99dcdd19a0a";
    public static String hypixelapi = "73156b08-2705-4f4e-ab2c-a11ed949469e"; //TODO make key more secure
    public static void getGuildInfo(@NotNull MessageReceivedEvent event) throws MalformedURLException {
        URL myURL = new URL(hypixelGuildURL);
        String response = Bot.httpGET(myURL);

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

        //System.out.println("Guild " + name + " have the tag " + tag + " displayed in " + color + " and " + exp + " exp and have " + memberCount + " members");

        //create imbed
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Guild stats for " + name, null);
        //eb.setColor();

        //add data
        Color ecolor;
        try {
            Field field = Class.forName("java.awt.Color").getField(color);
            ecolor = (Color) field.get(null);
        } catch (Exception e) {
            ecolor = null; // Not defined
        }
        eb.setColor(ecolor);
        eb.addField("Guild Tag", tag, true);
        eb.addField("Total GEXP", Integer.toString(exp), true);
        eb.addField("Guild Level", Integer.toString(level), true);
        eb.addField("XP Needed to level up", Integer.toString(EXPneeded), true);
        eb.addField("Total Member Count", Integer.toString(memberCount), true);

        //add a footer
        //Instant instant = Instant.instant();

        String time = Util.getCurrentTime();
        eb.setFooter("Powered by Hypixel API. More guild info at https://u.nu/15cps", null);

        //add a thumbnail
        eb.setThumbnail("https://scontent.fcxh2-1.fna.fbcdn.net/v/t31.0-8/20545497_1421019531316844_3777826206999579994_o.png?_nc_cat=110&ccb=2&_nc_sid=09cbfe&_nc_ohc=5CxwfT6drMsAX9K_Ab7&_nc_ht=scontent.fcxh2-1.fna&oh=c9f46729136c3086328b40eb3dd199ef&oe=5FB70D0E");

        //send it
        event.getTextChannel().sendMessage(eb.build()).queue();
    }

    private static int getLvl(int exp) {
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

    private static int xpNeeded(int exp) {
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
}
