package com.simple.server;

import com.simple.server.config.Configuration;
import com.simple.server.config.ConfigurationManager;
import com.simple.server.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class HttpSever {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpSever.class);

    public static  void main (String[] args) {


        LOGGER.info("Server Starting ...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf =  ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using WebRoot: " + conf.getWebroot());

        ServerListenerThread serverListenerThread;

        try {
            serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
