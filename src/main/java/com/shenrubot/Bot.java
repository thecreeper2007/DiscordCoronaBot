package com.shenrubot;
//https://pastebin.com/03sVdXUa

//jda Does anyone know how to use the Hypixel API through Java GET for Guild information? It is so poorly documented...

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

//import java.time.Instant;
//java.*
//net
//exceptions



@SuppressWarnings("unchecked")
public class Bot extends ListenerAdapter {


    public static String keyloc = "/Users/the_creeper2007/Desktop/DiscordAutoReply/src/main/java/com/shenrubot/APIkey.txt";

    public static Map<String, String> map = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

    public static String baseURL = "https://api.thevirustracker.com/free-api?countryTotal=";
    public static String hypixelapi = "73156b08-2705-4f4e-ab2c-a11ed949469e"; //TODO make key more secure
    public static String hypixelGuildURL = "https://api.hypixel.net/guild?key=7527b342-6199-4945-bc97-2a2689dd1997&id=5f72342b8ea8c99dcdd19a0a";
    public static void CountryCodes() {
        map.put("ALL", "ALL");
        map.put("Andorra", "AD");
        map.put("United Arab Emirates", "AE");
        map.put("Afghanistan", "AF");
        map.put("Antigua And Barbuda", "AG");
        map.put("Anguilla", "AI");
        map.put("Albania", "AL");
        map.put("Armenia", "AM");
        map.put("Netherlands Antilles", "AN");
        map.put("Angola", "AO");
        map.put("Antarctica", "AQ");
        map.put("Argentina", "AR");
        map.put("American Samoa", "AS");
        map.put("Austria", "AT");
        map.put("Australia", "AU");
        map.put("Aruba", "AW");
        map.put("Azerbaidjan", "AZ");
        map.put("Bosnia-Herzegovina", "BA");
        map.put("Barbados", "BB");
        map.put("Bangladesh", "BD");
        map.put("Belgium", "BE");
        map.put("Burkina Faso", "BF");
        map.put("Bulgaria", "BG");
        map.put("Bahrain", "BH");
        map.put("Burundi", "BI");
        map.put("Benin", "BJ");
        map.put("Bermuda", "BM");
        map.put("Brunei Darussalam", "BN");
        map.put("Bolivia", "BO");
        map.put("Brazil", "BR");
        map.put("Bahamas", "BS");
        map.put("Bhutan", "BT");
        map.put("Bouvet Island", "BV");
        map.put("Botswana", "BW");
        map.put("Belarus", "BY");
        map.put("Belize", "BZ");
        map.put("Canada", "CA");
        map.put("CocosIslands", "CC");
        map.put("Central African Republic", "CF");
        map.put("Congo, The Democratic Republic Of The", "CD");
        map.put("Congo", "CG");
        map.put("Switzerland", "CH");
        map.put("Ivory Coast (Cote D'Ivoire)", "CI");
        map.put("Cook Islands", "CK");
        map.put("Chile", "CL");
        map.put("Cameroon", "CM");
        map.put("China", "CN");
        map.put("Colombia", "CO");
        map.put("Costa Rica", "CR");
        map.put("Former Czechoslovakia", "CS");
        map.put("Cuba", "CU");
        map.put("Cape Verde", "CV");
        map.put("Christmas Island", "CX");
        map.put("Cyprus", "CY");
        map.put("Czech Republic", "CZ");
        map.put("Germany", "DE");
        map.put("Djibouti", "DJ");
        map.put("Denmark", "DK");
        map.put("Dominica", "DM");
        map.put("Dominican Republic", "DO");
        map.put("Algeria", "DZ");
        map.put("Ecuador", "EC");
        map.put("Estonia", "EE");
        map.put("Egypt", "EG");
        map.put("Western Sahara", "EH");
        map.put("Eritrea", "ER");
        map.put("Spain", "ES");
        map.put("Ethiopia", "ET");
        map.put("Finland", "FI");
        map.put("Fiji", "FJ");
        map.put("Falkland Islands", "FK");
        map.put("Micronesia", "FM");
        map.put("Faroe Islands", "FO");
        map.put("France", "FR");
        map.put("France Europe Territory", "FX");
        map.put("Gabon", "GA");
        map.put("Great Britain", "UK");
        map.put("Grenada", "GD");
        map.put("Georgia", "GE");
        map.put("French Guyana", "GF");
        map.put("Ghana", "GH");
        map.put("Gibraltar", "GI");
        map.put("Greenland", "GL");
        map.put("Gambia", "GM");
        map.put("Guinea", "GN");
        map.put("Guadeloupe (French)", "GP");
        map.put("Equatorial Guinea", "GQ");
        map.put("Greece", "GR");
        map.put("S. Georgia & S. Sandwich Isls.", "GS");
        map.put("Guatemala", "GT");
        map.put("Guam (USA)", "GU");
        map.put("Guinea Bissau", "GW");
        map.put("Guyana", "GY");
        map.put("Hong Kong", "HK");
        map.put("Heard And McDonald Islands", "HM");
        map.put("Honduras", "HN");
        map.put("Croatia", "HR");
        map.put("Haiti", "HT");
        map.put("Hungary", "HU");
        map.put("Indonesia", "ID");
        map.put("Ireland", "IE");
        map.put("Israel", "IL");
        map.put("India", "IN");
        map.put("British Indian Ocean Territory", "IO");
        map.put("Iraq", "IQ");
        map.put("Iran", "IR");
        map.put("Iceland", "IS");
        map.put("Italy", "IT");
        map.put("Jamaica", "JM");
        map.put("Jordan", "JO");
        map.put("Japan", "JP");
        map.put("Kenya", "KE");
        map.put("Kyrgyz Republic", "KG");
        map.put("Cambodia, Kingdom Of", "KH");
        map.put("Kiribati", "KI");
        map.put("Comoros", "KM");
        map.put("Saint Kitts & Nevis Anguilla", "KN");
        map.put("North Korea", "KP");
        map.put("South Korea", "KR");
        map.put("Kuwait", "KW");
        map.put("Cayman Islands", "KY");
        map.put("Kazakhstan", "KZ");
        map.put("Laos", "LA");
        map.put("Lebanon", "LB");
        map.put("Saint Lucia", "LC");
        map.put("Liechtenstein", "LI");
        map.put("Sri Lanka", "LK");
        map.put("Liberia", "LR");
        map.put("Lesotho", "LS");
        map.put("Lithuania", "LT");
        map.put("Luxembourg", "LU");
        map.put("Latvia", "LV");
        map.put("Libya", "LY");
        map.put("Morocco", "MA");
        map.put("Monaco", "MC");
        map.put("Moldavia", "MD");
        map.put("Madagascar", "MG");
        map.put("Marshall Islands", "MH");
        map.put("Macedonia", "MK");
        map.put("Mali", "ML");
        map.put("Myanmar", "MM");
        map.put("Mongolia", "MN");
        map.put("Macau", "MO");
        map.put("Northern Mariana Islands", "MP");
        map.put("Martinique", "MQ");
        map.put("Mauritania", "MR");
        map.put("Montserrat", "MS");
        map.put("Malta", "MT");
        map.put("Mauritius", "MU");
        map.put("Maldives", "MV");
        map.put("Malawi", "MW");
        map.put("Mexico", "MX");
        map.put("Malaysia", "MY");
        map.put("Mozambique", "MZ");
        map.put("Namibia", "NA");
        map.put("New Caledonia", "NC");
        map.put("Niger", "NE");
        map.put("Norfolk Island", "NF");
        map.put("Nigeria", "NG");
        map.put("Nicaragua", "NI");
        map.put("Netherlands", "NL");
        map.put("Norway", "NO");
        map.put("Nepal", "NP");
        map.put("Nauru", "NR");
        //map.put("Neutral Zone", "NT");
        map.put("Niue", "NU");
        map.put("New Zealand", "NZ");
        map.put("Oman", "OM");
        map.put("Panama", "PA");
        map.put("Peru", "PE");
        map.put("Polynesia (French)", "PF");
        map.put("Papua New Guinea", "PG");
        map.put("Philippines", "PH");
        map.put("Pakistan", "PK");
        map.put("Poland", "PL");
        map.put("Saint Pierre And Miquelon", "PM");
        map.put("Pitcairn Island", "PN");
        map.put("Puerto Rico", "PR");
        map.put("Portugal", "PT");
        map.put("Palau", "PW");
        map.put("Paraguay", "PY");
        map.put("Qatar", "QA");
        map.put("Reunion (French)", "RE");
        map.put("Romania", "RO");
        map.put("Russian Federation", "RU");
        map.put("Rwanda", "RW");
        map.put("Saudi Arabia", "SA");
        map.put("Solomon Islands", "SB");
        map.put("Seychelles", "SC");
        map.put("Sudan", "SD");
        map.put("Sweden", "SE");
        map.put("Singapore", "SG");
        map.put("Saint Helena", "SH");
        map.put("Slovenia", "SI");
        map.put("Svalbard And Jan Mayen Islands", "SJ");
        map.put("Slovak Republic", "SK");
        map.put("Sierra Leone", "SL");
        map.put("San Marino", "SM");
        map.put("Senegal", "SN");
        map.put("Somalia", "SO");
        map.put("Suriname", "SR");
        map.put("Saint Tome", "ST");
        map.put("Former USSR", "SU");
        map.put("El Salvador", "SV");
        map.put("Syria", "SY");
        map.put("Swaziland", "SZ");
        map.put("Turks And Caicos Islands", "TC");
        map.put("Chad", "TD");
        map.put("French Southern Territories", "TF");
        map.put("Togo", "TG");
        map.put("Thailand", "TH");
        map.put("Tadjikistan", "TJ");
        map.put("Tokelau", "TK");
        map.put("Turkmenistan", "TM");
        map.put("Tunisia", "TN");
        map.put("Tonga", "TO");
        map.put("East Timor", "TP");
        map.put("Turkey", "TR");
        map.put("Trinidad And Tobago", "TT");
        map.put("Tuvalu", "TV");
        map.put("Taiwan", "TW");
        map.put("Tanzania", "TZ");
        map.put("Ukraine", "UA");
        map.put("Uganda", "UG");
        map.put("United Kingdom", "UK");
        map.put("USA Minor Outlying Islands", "UM");
        map.put("United States", "US");
        map.put("Uruguay", "UY");
        map.put("Uzbekistan", "UZ");
        map.put("Holy See (Vatican City State)", "VA");
        map.put("Saint Vincent & Grenadines", "VC");
        map.put("Venezuela", "VE");
        map.put("Virgin Islands (British)", "VG");
        map.put("Virgin Islands (USA)", "VI");
        map.put("Vietnam", "VN");
        map.put("Vanuatu", "VU");
        map.put("Wallis And Futuna Islands", "WF");
        map.put("Samoa", "WS");
        map.put("Yemen", "YE");
        map.put("Mayotte", "YT");
        map.put("Yugoslavia", "YU");
        map.put("South Africa", "ZA");
        map.put("Zambia", "ZM");
        map.put("Zaire", "ZR");
        map.put("Zimbabwe", "ZW");

        //alternative names
        map.put("US", "US");
        map.put("USA", "US");
    }

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
        CountryCodes();

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

    public void getGuildInfo(MessageReceivedEvent event) throws MalformedURLException {
        URL myURL = new URL(hypixelGuildURL);
        String response = httpGET(myURL);
        System.out.println(response);

        JSONObject obj = new JSONObject(response);
        System.out.println(obj.getString("name"));


        EmbedBuilder eb = new EmbedBuilder();

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
