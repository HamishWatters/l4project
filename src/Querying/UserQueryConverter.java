package Querying;

import Querying.*;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class UserQueryConverter {

    public static Query generateQuery(String rawQuery)
    {
        Query processedQuery;
        JSONObject jsonObject = new JSONObject(rawQuery);
        if (jsonObject.has("headings"))
        {
            List<String> headings = new ArrayList<>();
            for (Object s: jsonObject.getJSONArray("headings"))
                headings.add(s.toString().toLowerCase());
            processedQuery = new ArticleQuery(jsonObject.get("title").toString().toLowerCase(), headings);
        }
        else
        {
            processedQuery = new SingleQuery(jsonObject.get("title").toString());
        }

        if (jsonObject.has("model"))
        {
            String modelString = jsonObject.get("model").toString();
            for (SearchModel model: SearchModel.values())
                if (model.name().equals(modelString))
                    processedQuery.setModel(model);
        }
        if (jsonObject.has("engine"))
        {
            String engineString = jsonObject.get("engine").toString();
            for (SearchEngine engine: SearchEngine.values())
                if (engine.name().equals(engineString))
                    processedQuery.setEngine(engine);
        }

        return processedQuery;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please supply a raw query string");
            System.exit(1);
        }
        Query myQuery = generateQuery(args[0]);
        myQuery.display();

    }
}
