package com.simple.server.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.simple.server.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private  ConfigurationManager () {

    }

    public static ConfigurationManager getInstance() {

        if(myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }

        return myConfigurationManager;
    }

    /**
     * Is it load a config file.
     *
     * @param filePath path to config file.
     */
    public void loadConfigurationFile(String filePath)  {
        FileReader reader  = null;
        StringBuffer sBuff = new StringBuffer();
        try {
            reader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }

        int i;

        try {
            while ((i = reader.read()) != -1) {
                sBuff.append((char) i);
            }
        } catch (IOException e) {
            throw  new HttpConfigurationException(e);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sBuff.toString());
        } catch (IOException e) {
            throw  new HttpConfigurationException("Error parsing the configuration file !!!", e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (IOException e) {
            throw  new HttpConfigurationException("Error parsing the configuration file internal !!!", e);
        }

    }

    /**
     * Returns the current configuration.
     */
    public Configuration getCurrentConfiguration() {
        if(myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No current configuration set !!! ");
        }
        return  myCurrentConfiguration;
    }
}
