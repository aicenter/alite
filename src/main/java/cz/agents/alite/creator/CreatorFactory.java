package cz.agents.alite.creator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@link CreatorFactory} dynamically instantiates a particular {@link Creator}
 * according to the application arguments.
 *
 * The application arguments are passed into the instantiated creator instantly
 * after its instantiation by the init() method. It means, the first creator's
 * argument is its class type as a string.
 *
 *
 * @author Antonin Komenda
 */
public class CreatorFactory {

    public static Creator createCreator(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Creator must be instantiated with at least Creator class name as a first parameter!");
        }

        try {
            Creator creator = (Creator) Class.forName(args[0]).newInstance();
            creator.init(args);
            return creator;
        } catch (Exception ex) {
            Logger.getLogger(CreatorFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
