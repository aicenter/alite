/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.agents.alite.googleearth;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;

/**
 * Not used in this demo.
 *
 * @author Petr Benda
 */
public class KmzDecoder {

    public static final void copyInputStream(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }
        out.flush();
        in.close();
        out.close();
    }

    public static void decode(String fromFile, String toFile) {
        try {
            FileInputStream rsp = new FileInputStream(fromFile);
            ZipInputStream zis = new ZipInputStream(rsp);
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                    toFile));
            zis.getNextEntry();
            copyInputStream(zis, dos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
