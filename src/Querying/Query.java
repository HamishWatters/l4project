package Querying;

import java.util.List;

public abstract class Query {
    protected static final SearchModel DEFAULT_MODEL = SearchModel.DLH13;
    protected static final SearchEngine DEFAULT_ENGINE = SearchEngine.TERRIER;

    private static Long nextQueryId = 0L;
    private long queryId;
    private SearchModel model;
    private SearchEngine engine;
    private long timestamp;

    protected Query()
    {
        this.model = DEFAULT_MODEL;
        this.engine = DEFAULT_ENGINE;
        this.queryId = nextQueryId++;
        this.timestamp = System.currentTimeMillis();
    }

    public abstract void display();
    public abstract List<Heading> getHeadings();
    public abstract String getTitle();

    public long getQueryId()
    {
        return this.queryId;
    }
    public SearchModel getModel()
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

    public void setModel(SearchModel model)
    {
        this.model = model;
    }
    public void setEngine(SearchEngine engine)
    {
        this.engine = engine;
    }


}
