package cz.agents.alite.configurator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


/**
 * Static class used to retrieve configuration. Instantiates one instance of given {@link ConfiguratorInterface} implementation. Provide utility methods to safely convert parameter types.
 * @author Michal Stolba
 *
 */
public class Configurator {

    private static final Logger LOGGER = Logger.getLogger(Configurator.class);

    private static ConfiguratorInterface config;
    private static boolean initialized = false;

    /**
     * Initialization - load configuration files
     * @param args Configuration files
     */
    public static <C extends ConfiguratorInterface> void init(String[] args,String rootDir, Class<C> clazz){
        init(Arrays.asList(args),rootDir,clazz);

    }


    /**
     * Initialization - load configuration files
     * @param args Configuration files
     */
    public static <C extends ConfiguratorInterface> void init(List<String> args,String rootDir, Class<C> clazz){
    	if(!initialized){
            try {
				config = clazz.newInstance();
				initialized = true;
			} catch (InstantiationException e) {
				LOGGER.error("Configurator initialization error!",e);
			} catch (IllegalAccessException e) {
				LOGGER.error("Configurator initialization error!",e);
			}
            
        }

        if(initialized){
        	config.setRoot(rootDir);
	        boolean replace = true;
	        for(String s : args){
	            if(replace){
	                config.loadConfigReplace(s);
	                replace = false;
	            }else{
	                config.loadConfigMerge(s);
	            }
	        }
        }


    }

    

    /**
     * Get param of generic type
     * @param name Param name
     * @return Param value
     */
    public static Object getParam(String name){
        if(!initialized){
            LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            return null;
        }

        return config.getParam(name);
    }

    /**
     * Get param of generic type, if such param does not exist, return default value
     * @param name Param name
     * @param defaultValue Default value if param does not exist
     * @return Param value
     */
    public static Object getParam(String name, Object defaultValue){
        if(!initialized){
            LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            return defaultValue;
        }
        if(config.getParam(name) == null){
            return defaultValue;
        }else{
            return config.getParam(name);
        }
    }

    public static String getParamString(String name, String defaultValue){
        if(!initialized){
            LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            return defaultValue;
        }
        if(config.getParam(name) == null){
            return defaultValue;
        }else{
            return (String)config.getParam(name);
        }
    }


    public static Boolean getParamBool(String name, Boolean defaultValue){
        if(!initialized){
            LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            return defaultValue;
        }

        if(config.getParam(name) == null){
            return defaultValue;
        }else{
            return (Boolean)config.getParam(name);
        }
    }

    public static Double getParamDouble(String name, Double defaultValue){
        if(!initialized){
            LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            return defaultValue;
        }
        if(config.getParam(name) == null){
            return defaultValue;
        }else{
            return (Double)config.getParam(name);
        }
    }

    public static Integer getParamInt(String name, Integer defaultValue){
        if(!initialized){
            LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            return defaultValue;
        }
        if(config.getParam(name) == null){
            return defaultValue;
        }else{
            return (Integer)config.getParam(name);
        }
    }

    /**
     * Get parameter in form of Map, should be Map;keyClass, valueClass&gt;
     * @param name
     * @return
     */
    public static <K,V> Map<K,V> getParamMap(String name, Class<K> keyClass, Class<V> valueClass){
        if(!initialized || config.getParam(name) == null){
            if (!initialized) {
                LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            }
            return new HashMap<K,V>();
        }

        // prepare empty typed map
        Map<K,V> typedMap = new HashMap<K, V>();

        // check if the param is a map
        Object paramObject = config.getParam(name);
        if (paramObject instanceof Map) {
            Map<?,?> paramMap = (Map<?,?>) paramObject;

            // go through all the entries of the map and dynamically cast them into the new typed map
            for (Entry<?, ?> entry : paramMap.entrySet()) {
                typedMap.put(keyClass.cast(entry.getKey()), valueClass.cast(entry.getValue()));
            }
        } else {
            throw new ClassCastException("Used getParamMap for non-Map parameter!");
        }

        return typedMap;
    }

    /**
     * Get parameter in form of untyped Map
     * @param name
     * @return
     */
    public static Map<?,?> getParamMap(String name) {
        if(!initialized || config.getParam(name) == null){
            if (!initialized) {
                LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            }
            return new HashMap<Object,Object>();
        }

        return (Map<?,?>) config.getParam(name);
    }


    /**
     * Get parameter in form of List&lt;elementClass&gt;
     * @param name
     * @return
     */
    public static <E> List<E> getParamList(String name, Class<E> elementClass) {
        if(!initialized || config.getParam(name) == null) {
            if (!initialized) {
                LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            }
            return new LinkedList<E>();
        }

        // prepare empty typed list
        List<E> typedList = new LinkedList<E>();

        // check if the param is a list
        Object paramObject = config.getParam(name);
        if (paramObject instanceof List) {
            List<?> paramList = (List<?>) paramObject;

            // go through all the elements of the list and dynamically cast them into the new typed list
            for (Object paramElementObject : paramList) {
                typedList.add(elementClass.cast(paramElementObject));
            }
        } else {
            throw new ClassCastException("Used getParamList for non-List parameter!");
        }

        return typedList;
    }

    /**
     * Get parameter in form of untyped List
     * @param name
     * @return
     */
    public static List<?> getParamList(String name) {
        if(!initialized || config.getParam(name) == null){
            if (!initialized) {
                LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            }
            return new LinkedList<Object>();
        }

        return (List<?>) config.getParam(name);
    }


    /**
     * Get parameter in form of List of Maps&lt;keyClass, valueClass&gt;
     * @param name
     * @return
     */
    public static <K,V> List<Map<K,V>> getParamListOfMaps(String name, Class<K> keyClass, Class<V> valueClass) {
        if(!initialized || config.getParam(name) == null){
            if (!initialized) {
                LOGGER.warn("CONFIGURATOR NOT INITIALIZED!");
            }
            return new LinkedList<Map<K,V>>();
        }

        // prepare empty typed list
        List<Map<K,V>> typedList = new LinkedList<Map<K,V>>();

        // check if the param is a list
        Object paramObject = config.getParam(name);
        if (paramObject instanceof List) {
            List<?> paramList = (List<?>) paramObject;

            // go through all the elements of the list and check if they are a map
            for (Object paramMapObject : paramList) {
                if (paramMapObject instanceof Map) {
                    // prepare empty typed map
                    LinkedHashMap<K,V> paramMap = new LinkedHashMap<K,V>();

                    // go through all the entries of current the map and dynamically cast them into a new typed map
                    for (Entry<?, ?> entry : ((Map<?,?>) paramMapObject).entrySet()) {
                        paramMap.put(keyClass.cast(entry.getKey()), valueClass.cast(entry.getValue()));
                    }

                    // add the new typed map into the typed list
                    typedList.add(paramMap);
                } else {
                    throw new ClassCastException("Used getParamListOfMaps for non-Map sub-parameter!");
                }
            }
        } else {
            throw new ClassCastException("Used getParamListOfMaps for non-List parameter!");
        }

        return typedList;
    }
    
    
    
    /**
     * Safely cast Object to a Map, should be Map;keyClass, valueClass&gt;
     * @param name
     * @return
     */
    public static <K,V> Map<K,V> safelyCastObjectToMap(Object obj, Class<K> keyClass, Class<V> valueClass){
        
    	if(obj == null){
    		return null;
    	}
    	
        // prepare empty typed map
        Map<K,V> typedMap = new HashMap<K, V>();

        // check if the param is a map
        if (obj instanceof Map) {
            Map<?,?> paramMap = (Map<?,?>) obj;

            // go through all the entries of the map and dynamically cast them into the new typed map
            for (Entry<?, ?> entry : paramMap.entrySet()) {
                typedMap.put(keyClass.cast(entry.getKey()), valueClass.cast(entry.getValue()));
            }
        } else {
            throw new ClassCastException("Used getParamMap for non-Map parameter!");
        }

        return typedMap;
    }
    
    
    /**
     * Safely cast Object to a List&lt;elementClass&gt;
     * @param name
     * @return
     */
    public static <E> List<E> safelyCastObjectToList(Object paramObject, Class<E> elementClass) {
        

        // prepare empty typed list
        List<E> typedList = new LinkedList<E>();

        // check if the param is a list
        if (paramObject instanceof List) {
            List<?> paramList = (List<?>) paramObject;

            // go through all the elements of the list and dynamically cast them into the new typed list
            for (Object paramElementObject : paramList) {
                typedList.add(elementClass.cast(paramElementObject));
            }
        } else {
            throw new ClassCastException("Used getParamList for non-List parameter!");
        }

        return typedList;
    }

}
