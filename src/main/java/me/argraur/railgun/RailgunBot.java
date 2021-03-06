/*
 * Copyright (C) 2020 Arseniy Graur
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.argraur.railgun;

import me.argraur.railgun.apis.KitsuAPI;
import me.argraur.railgun.apis.SauceNAOAPI;
import me.argraur.railgun.apis.TraceMoeAPI;
import me.argraur.railgun.apis.UrbanDictionaryAPI;
import me.argraur.railgun.handlers.CommandHandler;
import me.argraur.railgun.handlers.MuteHandler;
import me.argraur.railgun.helpers.ColorHelper;
import me.argraur.railgun.helpers.ConfigReader;
import me.argraur.railgun.helpers.IgnoreHelper;
import me.argraur.railgun.helpers.PrefixHelper;
import me.argraur.railgun.helpers.GiphyHelper;

import me.argraur.railgun.listeners.MessageListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;

public class RailgunBot {
    public JDA discordBot;
    public static String COMMAND_PREFIX;
    public static HashMap<String, String> channels = new HashMap<>();

    // Define base helpers
    public static ConfigReader configReader;
    public static IgnoreHelper ignoreHelper;
    public static GiphyHelper giphyHelper;
    public static ColorHelper colorHelper;
    public static PrefixHelper prefixHelper;
    
    // Define main command handlers
    public static MessageListener messageListener;
    public static CommandHandler commandHandler;
    public static MuteHandler muteHandler;

    // Define additional API classes
    public static KitsuAPI kitsuApi;
    public static UrbanDictionaryAPI urbanDictionaryApi;
    public static SauceNAOAPI sauceNAOApi;
    public static TraceMoeAPI traceMoeApi;

    /**
     * Sets command prefix by reading from config.
     */
    private static void setCommandPrefix() {
        COMMAND_PREFIX = readConfig("command_prefix");
        System.out.println("Set command prefix to: " + COMMAND_PREFIX);
    }

    /**
     * Function for configuring the bot.
     * Sets activity and creates default message listener.
     * @return Returns JDABuilder with token and set activity.
     */
    private JDABuilder configureBot() {
        messageListener = new MessageListener();
        JDABuilder jb = new JDABuilder(readConfig("token"));
        jb.setCompression(Compression.NONE);
        jb.setActivity(Activity.playing(readConfig("activity")));
        jb.addEventListeners(messageListener);
        return jb;
    }

    /**
     * Default constructor for the Railgun bot.
     */
    private RailgunBot() {
        try {
            System.out.println("Entering Discord Bot build state!");
            discordBot = configureBot().build();
        } catch (LoginException e) {
            System.exit(1);
        }
    }

    /**
     *
     * @param config Key to search for in config.
     * @return Returns value for given config from config.properties
     */
    public static String readConfig(String config) {
        return configReader.getValue(config);
    }

    /**
     * Init function.
     */
    public static void init() {
        try {
            configReader = new ConfigReader();
            setCommandPrefix();
            ignoreHelper = new IgnoreHelper();
            giphyHelper = new GiphyHelper(readConfig("giphy"));
            colorHelper = new ColorHelper();
            prefixHelper = new PrefixHelper();
            commandHandler = new CommandHandler();
            muteHandler = new MuteHandler();
            kitsuApi = new KitsuAPI();
            urbanDictionaryApi = new UrbanDictionaryAPI();
            sauceNAOApi = new SauceNAOAPI();
            traceMoeApi = new TraceMoeAPI();
        } catch (Exception e) {
            System.out.println("[RailgunBot] Init failed: " + e.getStackTrace());
            System.exit(1);
        }
        new RailgunBot();
    }

    /**
     * Main function
     * @param args Standard function argument.
     */
    public static void main(String[] args) {
        System.out.println("RailgunBot: START!");
        init();
    }

    /**
     * Error function.
     */
    public static void error(Object o) {
        System.out.println("[RailgunBot] Received error call from " + o.getClass().toString());
        System.exit(1);
    }

    public static HashMap<String, String> getChannels() {
        return channels;
    }
}
