package messenjava;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Odin
 */
public class TxtWriterReader {

    public static void write(String filename, String text) {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(filename, "UTF-8");
        } catch (Exception f) {
            f.printStackTrace();
        }
        writer.println(text);
        writer.close();
    }

    public static String read(String filename) {
        String content = "";
        try {
            content = new Scanner(new File("filename")).useDelimiter("\\Z").next();
        } catch (Exception f) {
            f.printStackTrace();
        }
        
        return content;
    }
    
    public static void testMe() {
        String testText = "this is my text";
        
        write("testfile.txt", testText);
        String readText = read("testfile.txt");
        
        if(readText.equals(testText)) {
            System.out.println("Txtwriterreader seems to work!");
        }
        else {
            System.out.println(testText + " not equal to: " + readText);
        }
    }

}
