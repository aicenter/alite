package incubator.communication.channel.sniffer.compatibility;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;


/**
 * <p>Title: A-Globe</p>
 * <p>Description: A-globe internal class used for loading classes from correct class loader during deserialization</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 */
// TODO: complete the javadoc

public final class LibraryObjectInputStream
    extends ObjectInputStream {
  /**
   * Class loader used for finding correct classes
   */
  private ClassLoader classLoader;

  /**
   *
   * @param in InputStream - orginal input stream
   * @param classLoader ClassLoader - class loader for resolving classes from input stream
   * @throws IOException - throws if there was some problem during creating new input stream
   */
  public LibraryObjectInputStream(InputStream in, ClassLoader classLoader) throws
      IOException {
    super(in);
    this.classLoader = classLoader;
  }

  /**
   *
   * @param in InputStream - orginal input stream
   * @param classLoaderOwner ClassLoader - class loader owner for resolving classes from input stream
   * @throws IOException - throws if there was some problem during creating new input stream
   */
  public LibraryObjectInputStream(InputStream in,
                                  ClassLoaderOwner classLoaderOwner) throws
      IOException {
    super(in);
    this.classLoader = classLoaderOwner.classLoader;
  }

  /**
   * Set new class loader
   *
   * @param classLoaderOwner ClassLoader
   */
  public void setClassLoader(ClassLoaderOwner classLoaderOwner) {
    this.classLoader = classLoaderOwner.classLoader;
  }

  /**
   * This method ensures, that deserialized classes are searched in the
   * dynamically loaded libraries.
   *
   * @param v ObjectStreamClass
   * @throws IOException
   * @throws ClassNotFoundException
   * @return Class
   */
  protected Class resolveClass(ObjectStreamClass v) throws IOException,
      ClassNotFoundException {
    try {
      return classLoader.loadClass(v.getName());
    }
    catch (ClassNotFoundException ex) {
      throw new IOException("No class definition found: " + v.getName());
    }
  }
}
