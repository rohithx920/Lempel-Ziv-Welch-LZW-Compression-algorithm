/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class Decoder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String location = "C:\\Users\\Rohith\\Downloads\\EncDec\\src\\encdec\\input.lzw";
          String location = args[0];
          int bit_length = Integer.parseInt(args[1]);
          double maxsize = Math.pow(2, bit_length);

        File f = new File(location);
        try {
            FileReader fileReader = new FileReader(f);
            //BufferedReader br = new BufferedReader(fileReader);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-16BE"));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                //System.out.println(line);
            }
            reader.close();
            //System.out.println("Hi ::"+ Decoder.Decoder(Decoder.decimalList(location)));
            createFile(location,Decoder.Decoder(Decoder.decimalList(location),maxsize));
            
        } catch (Exception e) {
            Logger.getLogger(Encoder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
     //Decoder method

    public static String Decoder(List<Integer> compressed,double maxsize) {
        // Build the dictionary.
        int default_table_size = 256;
        Map<Integer, String> table = new HashMap<Integer, String>();
        for (int i = 0; i < 256; i++) {
            table.put(i, "" + (char) i);
        }

        String string = "" + (char) (int) compressed.remove(0);
        StringBuffer result = new StringBuffer(string);
        for (int k : compressed) {
            String new_string="";
            if (table.containsKey(k)) {
                new_string = table.get(k);
            } 
           else{
            if(table.size()<maxsize)
                new_string = string + string.charAt(0);
            } 
            
            result.append(new_string);		

            // Add string+entry[0] to the dictionary.
            table.put(default_table_size++, string + new_string.charAt(0));

            string = new_string;
        }
        String str = result.toString();

        return str;

    }

    public static List<Integer> decimalList(String location) throws IOException, FileNotFoundException {

        String thisLine = "";
        List<Integer> decimalList = new ArrayList<Integer>();
        BufferedReader reader = null;
        int c;
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(location)), "UTF-16BE"));
            while ((c = reader.read()) != -1) {
                char character = (char) c;
                decimalList.add((int) character);
            }
        return decimalList;
    }
    public static void createFile(String location, String decode) throws IOException,FileNotFoundException
    {
        String fileLocation=location.substring(0,location.lastIndexOf("."))+"_decoded.txt";
        OutputStream outputStream = new FileOutputStream(fileLocation);
        Writer outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write(decode);

        //for printing decode at console 
        
        System.out.println(decode);
        outputStreamWriter.flush();
        outputStreamWriter.close();	
        

 }
}
