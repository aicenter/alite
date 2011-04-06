package incubator.communication.channel.sniffer.compatibility;

/**
 * <p>Title: </p>
 * <p>Description: Class loader owner.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Gerstner Laboratory</p>
 *
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 */
// TODO: complete the javadoc

public abstract class ClassLoaderOwner {
    /**
     * Entity's class loader, don't change it
     */
    transient ClassLoader classLoader = null;

    /**
     * Get class loader
     * @return ClassLoader
     */
    public ClassLoader getClassLoader() {
      return classLoader;
    }
}
