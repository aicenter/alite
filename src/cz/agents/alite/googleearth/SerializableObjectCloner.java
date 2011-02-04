package cz.agents.alite.googleearth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Deep-copy of Serializable objects
 */
public class SerializableObjectCloner {

	public static Object clone(Object original) {
		Object clone = null;
		try {
			// Increased buffer size to speed up writing
			ByteArrayOutputStream bos = new ByteArrayOutputStream(5120);
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(original);
			out.flush();
			out.close();

			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(bos.toByteArray()));
			clone = in.readObject();

			in.close();
			bos.close();

			return clone;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}

		return null;
	}
}