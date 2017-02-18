package newage.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class FileProperties extends Properties{
	private static final long serialVersionUID = 1L;

	public FileProperties(String fileName) throws FileNotFoundException{
		super();
		InputStream inputStream = null;
		
		if (StringUtils.isNotEmpty(fileName)) {
			inputStream = new FileInputStream(fileName);
		}
		
		if (inputStream != null) {
			try {
				load(inputStream);
			} catch (IOException ex) {
				new Exception("Can't load properties from the " + fileName);
			}
		}
	}
}
