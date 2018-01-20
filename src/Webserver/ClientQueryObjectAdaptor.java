package Webserver;

public class ClientQueryObjectAdaptor {
    public static String convertJson(String json)
    {
        return json.replace("%22"," ").replace("%20", " ");
    }
}
