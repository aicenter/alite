/*
 * SnifferAddress.java
 *
 * Created on January 17, 2007, 12:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package incubator.communication.channel.sniffer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author vokrinek
 */
public class SnifferAddress {
    
    /**
     * Creates a new instance of SnifferAddress
     */
    public SnifferAddress() {
        
    }
    
    public static String getAddress() {
        BufferedReader reader = null;
        String rtrn = null;
        try {
            reader = new BufferedReader(new FileReader("sniferaddress.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            rtrn = reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return rtrn;
      }
    
}
