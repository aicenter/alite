package incubator.classloader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Custom class loader allowing to handle separate contexts in one run.
 * It maintains loading of the classes on the classpath. 
 * You can limit the usage for the classes starting with defined prefix (@see MyClassLoader(String[] prefixList, String[] ignoreList))
 * You can also make the exceptions by using defined ignoreList (@see MyClassLoader(String[] prefixList, String[] ignoreList))
 * Others are loaded by the system classloader.
 * Classes are loaded once per @MyClassLoader instance.
 * 
 * Usage:
 *  new MyClassLoader().loadClass(CreatorClass).newInstance();
 * or:
 *  String[] prefixList = null;
 *  String[] ignoreList = {cz.agents.alite.creator.Creator};
 *  new MyClassLoader(prefixList, ignoreList).loadClass(CreatorClass).newInstance();
 * 
 * Note: signature of a class loaded by custom loaders differs
 * The following code throw an casting exception:
 * 
 *  Foo foo = new MyClassLoader().loadClass(Foo).newInstance();
 * 
 * This situation can be avoided by using an interface or superclass of Foo loaded by the 
 * parent classloader (i.e. placed to the ignoreList). Note all the subclasses of the 
 * ignored class will be loaded also by the parent classloader.
 *  
 * 
 * TODO: optional extension to support class name filter (load custom classes/packages) only
 * TODO: handle .jar files
 * 
 * @author Jiri Vokrinek
 */
public class MyClassLoader extends ClassLoader {

    private ArrayList<String> myLoadedClasses = new ArrayList<String>();
    private static final ArrayList<String> paths = new ArrayList<String>();
    private final String[] prefixList;
    private final String[] ignoreList;
    private final boolean prefixing;
    private final boolean ignoring;

    static {
        String property = System.getProperty("java.class.path");
        Scanner scanner = new Scanner(property);
        scanner.useDelimiter(File.pathSeparator);
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (!next.endsWith("" + File.separatorChar)) {
                next = next + File.separatorChar;
            }
            paths.add(next);
        }
    }

    public MyClassLoader(String[] prefixList, String[] ignoreList) {
        this.prefixList = prefixList;
        prefixing = (prefixList != null);
        this.ignoreList = ignoreList;
        ignoring = (ignoreList != null);
    }

    public MyClassLoader() {
        super();
        this.prefixList = null;
        this.ignoreList = null;
        prefixing = false;
        ignoring = false;
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        //allready loaded?
        if (myLoadedClasses.contains(name)) {
            return loadByParent(name);
        }
        //prefix test
        if (prefixing) {
            boolean isPrefix = false;
            for (String prefix : prefixList) {
                if (name.startsWith(prefix)) {
                    isPrefix = true;
                    continue;
                }
            }
            if (!isPrefix) {
                return loadByParent(name);
            }
        }

        //ignoreList 
        if (ignoring) {
            for (String clazz : ignoreList) {
                if (name.equals(clazz)) {
                    return loadByParent(name);
                }
            }
        }

        // Create a file exsisting on classpaths
        File f = resolveName(name);

        // not found on classpath - try parrent classloader
        if (f == null) {
            return loadByParent(name);
        }

        try {
            // Get size of class file
            int size = (int) f.length();
            // Reserve space to read
            byte classData[] = new byte[size];
            // Get stream to read from
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            // Read in data
            dis.readFully(classData);
            // close stream
            dis.close();

            myLoadedClasses.add(name);
            return defineClass(name,
                    classData, 0, classData.length);

        } catch (MalformedURLException e) {
        } catch (IOException e) {
            throw new ClassNotFoundException("Error reading file: " + f.getAbsolutePath());
        }
        return null;
    }

    private Class<?> loadByParent(String name) throws ClassNotFoundException {
        Class<?> loadClass = super.loadClass(name);
        myLoadedClasses.add(name);
        return loadClass;
    }

    private File resolveName(String name) {
        for (String path : paths) {
            String filename = path + name.replace('.', File.separatorChar) + ".class";
            File f = new File(filename);
            if (f.exists()) {
                return f;
            }
        }
        return null;
    }
}
