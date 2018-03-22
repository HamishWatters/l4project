package Evaluator;

import org.terrier.evaluation.TrecEvalEvaluation;

public class Evaluate {
    public static void main(String[] args)
    {
        if (args.length < 1 || args.length > 2)
        {
            System.out.println("Usage: Evaluate qrelfile [topicfile] ");
            System.exit(1);
        }

        TrecEvalEvaluation eval = new TrecEvalEvaluation(args[0]);
    }
}
