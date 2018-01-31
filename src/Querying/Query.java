package Querying;

import java.util.List;

public abstract class Query {
    private static final SearchModel DEFAULT_MODEL = SearchModel.DLH13;
    private static final SearchEngine DEFAULT_ENGINE = SearchEngine.TERRIER;

    private static Long nextQueryId = 0L;
    private String queryId;
    private SearchModel model;
    private SearchEngine engine;
    private long timestamp;

    protected Query()
    {
        this.model = DEFAULT_MODEL;
        this.engine = DEFAULT_ENGINE;
        this.timestamp = System.currentTimeMillis();
    }

    public abstract void display();
    public abstract List<Heading> getHeadings();
    public abstract String getTitle();

    public String getQueryId()
    {
        return this.queryId;
    }
    
    protected void makeId()
    {
    	this.queryId = getTitle() + String.valueOf(nextQueryId++);
    }
    SearchModel getModel()
    {
        return this.model;
    }
    public SearchEngine getEngine()
    {
        return this.engine;
    }
    public Long getTimestamp()
    {
        return this.timestamp;
    }

    void setModel(SearchModel model)
    {
        this.model = model;
    }
    void setEngine(SearchEngine engine)
    {
        this.engine = engine;
    }


}
