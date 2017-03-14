/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rohith
 */
public class Encoder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //String fileName="input.txt";
        String cmdArgFileName=args[0];
//        File f = new File("C:\\Users\\Rohith\\Downloads\\EncDec\\src\\encdec\\input.txt");
           File f = new File(cmdArgFileName);
        try {
            FileReader fileReader = new FileReader(f);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
                //System.out.println(line);
            }
            int bit_length = Integer.parseInt(args[1]);
            double maxsize = Math.pow(2, bit_length);
	    List<Integer> compresslist=Encoder.compress(sb.toString(), maxsize);
            System.out.println(compresslist);
            Encoder.UTF16(compresslist,cmdArgFileName);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(Encoder.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static List<Integer> compress(String uncompressed, double maxsize) {
        // Build the table
        int tablesize = 256;
        //mapping key and value using Hashmap
        Map<String, Integer> table = new HashMap<String, Integer>();
        //Adding all the ASCII characters
        for (int i = 0; i < 256; i++) {
            table.put("" + (char) i, i);
        }
        String present_string = "";
        List<Integer> result = new ArrayList<Integer>();
        //Reading character by character
        for (char next_string : uncompressed.toCharArray()) {
            String k = present_string + next_string;
            //checking whether k exists in table or not
            if (table.containsKey(k)) {
                present_string = k;
            } else //checking whether tablesize is less than maxsize
            {
                if (table.size() < maxsize) {
                    //System.out.println(p);
                    result.add(table.get(present_string));
                    table.put(k, tablesize++);
                    present_string = "" + next_string;
                }
            }
        }
        if (!present_string.equals("")) {
            result.add(table.get(present_string));
        }
        //returning the result list
        return result;

    }

    public static void UTF16(List<Integer> mylist, String location) throws IOException, FileNotFoundException {
        String fileLocation=location.substring(0,location.lastIndexOf("."))+".lzw";
        OutputStream outputStream = new FileOutputStream(fileLocation);
        Writer outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16BE");
        for(int i:mylist)
        outputStreamWriter.write(i);
        outputStreamWriter.flush();
        outputStreamWriter.close();

    }

}
