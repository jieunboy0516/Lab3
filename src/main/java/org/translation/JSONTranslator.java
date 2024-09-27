package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class

    private static final String ALPHA3_KEY = "alpha3";
    private JSONArray jsonArray;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            jsonArray = new JSONArray(jsonString);

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> languages = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject countryObj = jsonArray.getJSONObject(i);
            if (countryObj.getString(ALPHA3_KEY).equalsIgnoreCase(country)) {
                for (String key : countryObj.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !ALPHA3_KEY.equals(key)) {
                        languages.add(key);
                    }
                }
                break;
            }
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> countries = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject countryObj = jsonArray.getJSONObject(i);
            countries.add(countryObj.getString(ALPHA3_KEY));
        }
        return countries;
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject countryObj = jsonArray.getJSONObject(i);
            if (countryObj.getString(ALPHA3_KEY).equalsIgnoreCase(country)) {
                return countryObj.optString(language, null);
            }
        }
        return null;
    }
}
