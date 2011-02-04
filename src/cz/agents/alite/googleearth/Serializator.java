/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.agents.alite.googleearth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Petr Benda
 */
public class Serializator {

    // public static final String PATH = "src/com/google/earth/kml/v22";
    // public static final String PATH =
    // "src/oasis/names/tc/ciq/xsdschema/xal/v22";
    public static final String PATH = "src/org/w3/_2005/atom";
    public static final String FILTER = ".java";

    public static final String CLASS = "class";
    public static final String ENUM = "enum";
    public static final String EXTENDS = "extends";
    public static final String IMPLEMENTS_JAVA_IO_SERIALIZABLE = "implements java.io.Serializable";
    public static final String TEMPORARY_EXTENSION = ".tmp";

    public static void makeSerializableTMP(String fileName) {

        File inputFile = new File(PATH + "/" + fileName);
        File output = new File(PATH + "/" + fileName + TEMPORARY_EXTENSION);

        String contents="";

        try {
            // use buffering, reading one line at a time
            // FileReader always assumes default encoding is OK!
            BufferedReader inputRreader = new BufferedReader(new FileReader(
                    inputFile));
            Writer outputWriter = new BufferedWriter(new FileWriter(output));

            try {
                String line = null; // not declared within while loop
                String className = fileName.substring(0, fileName.indexOf("."));
                boolean seeNextLine = false;

                while ((line = inputRreader.readLine()) != null) {

                    if (seeNextLine) {
                        if (line.contains(EXTENDS)) {
                            String[] splittedLine = line.split(" ");
                            if (splittedLine[splittedLine.length - 2]
                                    .equals(EXTENDS)) {

                                String newLine = line + " "
                                        + IMPLEMENTS_JAVA_IO_SERIALIZABLE;
                                line = newLine;
                            }
                            seeNextLine = false;
                        }
                    }

                    if (line.contains(className)) {

                        String[] words = line.split(" ");
                        if (!line.contains(EXTENDS)
                                && ((line.contains(CLASS)) || (line
                                        .contains(ENUM)))
                                && (words[words.length - 1].equals("{"))) {
                            String newLine = line.substring(0,
                                    line.length() - 2);
                            newLine += " " + IMPLEMENTS_JAVA_IO_SERIALIZABLE
                                    + " {";
                            line = newLine;
                        }
                        if (!line.contains(EXTENDS)
                                && ((line.contains(CLASS)) || (line
                                        .contains(ENUM)))
                                && (!words[words.length - 1].equals("{"))) {
                            seeNextLine = true;
                        }
                    }

                    contents += line;
                    contents += System.getProperty("line.separator");
                }

                outputWriter.write(contents);

            } finally {
                inputRreader.close();
                outputWriter.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void replaceOriginalFileWithTMP(String fileName) {
        File fileToDelete = new File(PATH + "/" + fileName);
        fileToDelete.delete();
        File fileToRename = new File(PATH + "/" + fileName
                + TEMPORARY_EXTENSION);
        fileToRename.renameTo(new File(PATH + "/" + fileName));
    }

    public static void main(String[] args) {
        // File dir = new File(PATH);
        //
        // String[] children = dir.list();
        // if (children == null) {
        // } else {
        // for (int i = 0; i < children.length; i++) {
        // String filename = children[i];
        // if (filename.contains(FILTER)) {
        // makeSerializableTMP(filename);
        // replaceOriginalFileWithTMP(filename);
        //
        // }
        // }
        // }
        //
        //
    }
}
