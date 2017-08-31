package de.pniehus.odal.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class contains a method to create a deep copy of an object
 * @author Phil Niehus
 *
 */
public class DeepCopy {
	
	/**
	 * This method creates a deep copy of serializeable objects
	 * @param o The object to create a copy of
	 * @return A copy of the object without any references to objects referenced by o
	 * @throws Exception When the give object is not serializable
	 */
	public static Object deepCopy(Object o) throws Exception{
		
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream objOs = new ObjectOutputStream(bos);
		
		objOs.writeObject(o);
		objOs.flush();
		objOs.close();
		bos.close();
		
		byte[] byteRepresentation = bos.toByteArray();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(byteRepresentation);
		Object out = new ObjectInputStream(bis).readObject();
		
		return out;
	}
}
