package edu.unh.cs.treccar.read_data;

import edu.unh.cs.treccar.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class TrecCarCborToTrecCollection {

    public static void main(String[] args) throws Exception {
        System.setProperty("file.encoding", "UTF-8");


        final String paragraphsFile = args[1];
        final FileInputStream fileInputStream2 = new FileInputStream(new File(paragraphsFile));
        try {
            FileWriter fw = new FileWriter("newparagraphcorpus");
            for (Data.Paragraph p : DeserializeData.iterableParagraphs(fileInputStream2)) {
                fw.write("<DOC>\n");
                fw.write("<DOCNO>");
                fw.write(p.getParaId());
                fw.write("</DOCNO>\n");
                fw.write(p.getTextOnly().replace("%20"," "));
                fw.write("</DOC>\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
