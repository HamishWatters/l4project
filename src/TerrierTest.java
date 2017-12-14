import Querying.Query;
import Querying.QueryCoordinator;
import Querying.UserQueryConverter;
import Results.ResultHandler;

import java.io.File;

public class TerrierTest {
    public static void main(String[] args) {

        if (args.length != 1)
        {
            System.out.println("Usage: TerrierTest <locationofqueryjson>");
            System.exit(1);
        }
        Query q = UserQueryConverter.generateQuery(args[0]);
        QueryCoordinator coordinator = new QueryCoordinator();
        coordinator.executeQuery(q);
        ResultHandler handler = new ResultHandler(q);
        handler.getBestDocumentNoDuplicates();
        handler.writeResults(new File("out/results.txt"));

    }
}
