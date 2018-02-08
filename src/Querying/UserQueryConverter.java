package Querying;

import Webserver.ClientQueryObjectAdaptor;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class UserQueryConverter {

    public static Query generateWebQuery(String webQuery)
    {
        JSONObject jsonObject = new JSONObject(ClientQueryObjectAdaptor.convertJson(webQuery));
        Query processedQuery;
        String title = "";
        List<Heading> headings = new ArrayList<>();
        if (jsonObject.has("headings"))
        {
            JSONArray headingObjects = jsonObject.getJSONArray("headings");
            Heading[] headingArray = new Heading[headingObjects.length()];
            title = headingObjects.getJSONArray(0).getString(0);
            if (headingArray.length == 1)
                return new SingleQuery(title);
            for (int i = 1; i < headingObjects.length(); i++)
            {
                JSONArray jarr;
                if ((jarr = headingObjects.optJSONArray(i)) != null)
                {
                    String headingName;
                    if ((headingName = jarr.optString(0)) != null)
                    {
                        Heading h = new Heading(headingName);
                        headingArray[i] = h;
                    }
                }
            }
            for (int i = 1; i < headingObjects.length(); i++)
            {
                JSONArray jarr;
                if ((jarr = headingObjects.optJSONArray(i)) != null)
                {
                    String parentId;
                    if ((parentId = jarr.optString(1)) != null)
                    {
                        int parentIdInt = Integer.parseInt(parentId);
                        if (parentIdInt != 0)
                            headingArray[i].setParent(headingArray[Integer.parseInt(parentId)]);
                    }
                }
            }
            for (int i = 1; i < headingArray.length; i++)
            {
                if (headingArray[i] != null)
                    headings.add(headingArray[i]);
            }
        }
        if (title.equals("")) title = "Failed";
        processedQuery = new ArticleQuery(title, headings);
        if (jsonObject.has("model"))
        {
            String jmodel;
            if ((jmodel = jsonObject.optString("model")) != null)
            {
                processedQuery.setModel(SearchModel.values()[Integer.parseInt(jmodel)]);
            }
        }
        return processedQuery;
    }
    public static Query generateQuery(String rawQuery)
    {
        Query processedQuery;
        JSONObject jsonObject = new JSONObject(rawQuery);
        if (jsonObject.has("headings"))
        {
            List<Heading> headings = new ArrayList<>();
            JSONArray headingObjects = jsonObject.getJSONArray("headings");
            for (int i = 0; i < headingObjects.length(); i++)
            {
                String str;
                JSONObject jobj;
                if ((jobj = headingObjects.optJSONObject(i)) != null)
                {
                    Heading h = new Heading(jobj.keys().next());
                    processHeadingJsonObject(jobj, h);
                    headings.add(h);
                }
                else if ((str = headingObjects.optString(i)) != null)
                    headings.add(new Heading(str));
                else
                    throw new JSONException("Unable to parse query json");
            }
            processedQuery = new ArticleQuery(jsonObject.get("title").toString(), headings);
        }
        else
        {
            processedQuery = new SingleQuery(jsonObject.get("title").toString());
        }

        if (jsonObject.has("model"))
        {
            String modelString = jsonObject.get("model").toString();
            for (SearchModel model : SearchModel.values())
                if (model.name().equals(modelString))
                    processedQuery.setModel(model);
        }
        if (jsonObject.has("engine"))
        {
            String engineString = jsonObject.get("engine").toString();
            for (SearchEngine engine : SearchEngine.values())
                if (engine.name().equals(engineString))
                    processedQuery.setEngine(engine);
        }

        return processedQuery;
    }

    private static void processHeadingJsonArray(JSONArray objects, Heading heading)
    {
        for (int i=0; i < objects.length(); i++)
        {
            String str;
            JSONObject jobj;
            if ((jobj = objects.optJSONObject(i)) != null)
            {
                Heading h = new Heading(jobj.keys().next());
                heading.addSubheading(h);
                processHeadingJsonObject(jobj, h);
            }
            else if ((str = objects.optString(i)) != null)
                heading.addSubheading(new Heading(str));
            else
            {
                throw new JSONException("Unable to parse query json");
            }
        }
    }

    private static void processHeadingJsonObject(JSONObject object, Heading heading)
    {
        JSONObject jobj;
        JSONArray jarr;
        if ((jobj = object.optJSONObject(heading.getName())) != null)
        {
            Heading h = new Heading(jobj.keys().next());
            heading.addSubheading(h);
            processHeadingJsonObject(jobj, h);
        }
        else if((jarr = object.optJSONArray(heading.getName())) != null)
        {
            processHeadingJsonArray(jarr, heading);
        }
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("Please supply a raw query string");
            System.exit(1);
        }
        Query myQuery = generateQuery(args[0]);
        myQuery.display();

    }
}
