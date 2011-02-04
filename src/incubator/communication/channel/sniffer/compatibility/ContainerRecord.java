package incubator.communication.channel.sniffer.compatibility;

import java.io.Serializable;

//import atg.ontology.*;


/**
 * <p>Title: A-Globe</p>
 * <p>Description: Class used for representing container. Holds container name and its address.
* This class implements Serializable interface.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 */
// TODO: complete the javadoc

public final class ContainerRecord implements Serializable
{
  // This number has to be changed, when Agent's variables are changed
  private static final long serialVersionUID = -7803140422603776936L;

  /**
   * Container name
   */
//  public final String name;

  /**
   * Container address
   */
//  public final Address address;

  /**
   * Hash code cache
   */
//  public final int hash;

  /**
   * Creates instance and field was initialized from the VisibleContainer structure received as a visibility records
   * 
   */
  public ContainerRecord()
//  public ContainerRecord(VisibleContainer p)
  {
//    name= p.getName();
//    address= Address.getContainerAddress(p.getHost(), p.getPort());
//    hash = 29 * name.hashCode() + address.hashCode();
  }

  /**
   * Creates instance and initializes field from the parameters.
   * @param name String
   * 
   */
  public ContainerRecord(String name) {
//  public ContainerRecord(String name,Address address) {
//    this.name = name;
//    this.address = address;
//    hash = 29 * name.hashCode() + address.hashCode();
  }

  /**
   * Tests if this instance is the same as the other instance
   * @param other Object - instance of the other ContainerRecord to which is this compared
   * @return boolean - true iff this is same as other instance
   */
  public boolean equals(Object other)
  {
      return true;
//    if(other instanceof ContainerRecord)
//      return address.equals(((ContainerRecord)other).address);
//    else
//      return false;
  }

  /**
   * Returns string representation of this instance
   * @return String
   */
  public String toString() {
//    return "Name: "+name+" Address: "+address;
          return "Dummy Kontajner na fiktivni adrese";
  }

    public int hashCode()
    {
        return 12345;
//        return hash;
    }
}
