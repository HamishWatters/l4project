package Querying;

import Results.Result;

import java.util.ArrayList;
import java.util.List;

public abstract class Query {
    protected static final SearchModel DEFAULT_MODEL = SearchModel.BM25;
    protected static final SearchEngine DEFAULT_ENGINE = SearchEngine.TERRIER;

    private static Long nextQueryId = 0L;
    private long queryId;
    private SearchModel model;
    private SearchEngine engine;
    private long timestamp;
    private List<Result> results;

    protected Query()
    {
        this.model = DEFAULT_MODEL;
        this.engine = DEFAULT_ENGINE;
        this.queryId = nextQueryId++;
        this.timestamp = System.currentTimeMillis();
        this.results = new ArrayList<>();
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
    public List<Result> getResults() {return this.results;}

    public void setModel(SearchModel model)
    {
        this.model = model;
    }
    public void setEngine(SearchEngine engine)
    {
        this.engine = engine;
    }

    public void addResult(Result result)
    {
        this.results.add(result);
    }


}
