package cz.agents.alite.configurator;



/**
 * Interface for configurator implementation
 * @author Michal Stolba
 *
 */
public interface ConfiguratorInterface {
	
	/**
	 * Set root folder for the configuration files
	 * @param root Root folder
	 */
	public void setRoot(String root);
	
	/**
	 * Load a config file and merge with current configuration
	 * @param filename
	 */
	public void loadConfigMerge(String filename);
	
	/**
	 * Load a config file and replace current configuration
	 * @param filename
	 */
	public void loadConfigReplace(String filename);
	
	/**
	 * Get specified param
	 * @param name Param name
	 * @return Param value
	 */
	public Object getParam(String name);

}
