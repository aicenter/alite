package incubator.communication.channel.sniffer.compatibility;

import java.io.File;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
//import aglobe.container.task.*;

/**
 * <p>Title: A-Globe</p>
 * <p>Description: Some static function to help operate with files and <code>FileChoosers</code></p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 *
 */

public class FileUtils {
  /**
   * XML file extension
   */
  public static final String XML = "xml";

  /**
   * GIF file extension
   */
  public static final String GIF = "gif";

  /**
   * JPG file extension
   */
  public static final String JPG = "jpg";

  /**
   * JPEG file extension
   */
  public static final String JPEG = "jpeg";

  /**
   * JAR file extension
   */
  public static final String JAR = "jar";

  /**
   * TXT file extension
   */
  public static final String TXT = "txt";

  /**
   * FileUtils cannot be created
   */
  private FileUtils() {
  }

  /**
   * Return the extension of the file.
   * @param f The file.
   * @return The extension (without the '.' character), or an empty <code>String</code>.
   */
  public static String getExtension(File f) {
    return getExtension(f.getName());
  }

  /**
   * Return the extension of the file.
   * @param name The file name.
   * @return The extension (without the '.' character), or an empty <code>String</code>.
   */
  public static String getExtension(String name) {
    String ext = "";
    int i = name.lastIndexOf('.');

    if (i > 0 && i < name.length() - 1) {
      ext = name.substring(i + 1).toLowerCase();
    }
    return ext;
  }

  /**
   * Checks whether the file has the extension. Extension is case insensitive.
   * @param f The file.
   * @param ext  The required extension.
   * @return <code>true</code> iff the file has the extension <code>ext</code>.
   */
  public static boolean hasExtension(File f, String ext) {
    return getExtension(f.getName()).equalsIgnoreCase(ext);
  }

  /**
   * Checks whether the file has the extension. Extension is case insensitive.
   * @param name The file name.
   * @param ext  The required extension.
   * @return <code>true</code> iff the file has the extension <code>ext</code>.
   */
  public static boolean hasExtension(String name, String ext) {
    return getExtension(name).equalsIgnoreCase(ext);
  }

  /**
   * Removes the extension from the file name. The last dot ('.') character
   * in the name is considered as extension separator. Dot on the first position
   * does not separate an extension (e.g. filename <code>".forward"</code> has
   * no extension.
   * @param name The file name.
   * @return The filename with an extension removed.
   */
  public static String removeExtension(String name) {
    int dot = name.lastIndexOf('.');
    if (dot > 0)
      return name.substring(0, dot);
    else
      return name;
  }

  /**
   * Checks, it the name has the required extension, if not, the extension is
   * added. Extensions are case insensitive, always converted to lowercase.
   * The method does not remove the current extension, therefore calling
   * <pre>
   *   String s= ensureExtension("file.jar", "xml");
   * </pre>
   * Results in <code>s</code> containing <code>"file.jar.xml"</code>.
   * @param name The file name (might include the path).
   * @param ext The required extension.
   * @return The filename with extension.
   */
  public static String ensureExtension(String name, String ext) {
    String e = ext.toLowerCase();

    if (name.toLowerCase().endsWith("." + e))
      return name;
    else
      return name + "." + e;
  }

  /**
   * Checks, it the name has the required extension, if not, the extension is
   * added. Extensions are case insensitive, always converted to lowercase.
   * The method does not remove the current extension, therefore calling
   * <pre>
   *   String s= ensureExtension("file.jar", "xml");
   * </pre>
   * Results in <code>s</code> containing <code>"file.jar.xml"</code>.
   * @param f The file.
   * @param ext The required extension.
   * @return New File with extension ensured.
   */
  public static File ensureExtension(File f, String ext) {
    String e = ext.toLowerCase();
    String name = f.getPath().toLowerCase();

    if (name.endsWith("." + e))
      return new File(f.getPath());
    else
      return new File(f.getPath() + "." + e);
  }

  /**
   * The simple file filter to use with <code>JFileChooser</code>. It uses only
   * the extension fo filter files. It could recognize one or more extension.
   */
  public static class FileFilter
      extends javax.swing.filechooser.FileFilter implements Serializable {
    // This number has to be changed, when Agent's variables are changed
    private static final long serialVersionUID = -780314042272324565L;

    private final Set extensions;
    private final String desc;

    /**
     * Multiple extension filter. The description has to be provided by the user.
     * @param extensions The array of valid extension.
     * @param description The filter description.
     */
    @SuppressWarnings(value={"unchecked"}) 
    public FileFilter(String[] extensions, String description) {
      this.extensions = new TreeSet();
      for (int i = 0; i < extensions.length; i++)
        this.extensions.add(extensions[i].toLowerCase());
      desc = description;
    }

    /**
     * Single extension filter.
     * @param extension The valid extension.
     * @param description The filter description.
     */
    @SuppressWarnings(value={"unchecked"}) 
    public FileFilter(String extension, String description) {
      extensions = new TreeSet();
      extensions.add(extension.toLowerCase());
      desc = description;
    }

    /**
     * Single extension filter. The description in the form of
     * '<code>File *.ext</code> is generated.
     * @param extension The valid extension.
     */
    @SuppressWarnings(value={"unchecked"}) 
    public FileFilter(String extension) {
      extensions = new TreeSet();
      extensions.add(extension.toLowerCase());
      desc = "Files *." + extension;
    }

    /**
     * Test if file f is valid under the filter. Directory is valid every time.
     * @param f File
     * @return boolean - true iff file is valid under the filter
     */
    public boolean accept(File f) {
      return f.isDirectory() || extensions.contains(getExtension(f));
    }

    /**
     * Get Description of the filter
     * @return String
     */
    public String getDescription() {
      return desc;
    }
  }
}
