package tech.sperlikoliver.and_kitchen.Model.Retrofit.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {

    private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

    public static String removeTags(String string){
        if (string == null || string.length() == 0){
            return string;
        } else{
            Matcher m = REMOVE_TAGS.matcher(string);
            return m.replaceAll("");
        }
    }
}
