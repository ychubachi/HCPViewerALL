package clib.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CCopyUtil {

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T copyDeep(T obj) {
		try {
			if (obj == null) {
				return null;
			}
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(obj);
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut
					.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			return (T) in.readObject();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
