import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface Modifiable {
	/* 
	 * Won't be passing database connection for now, we have no hibernate and no spring to do so
	 * Just files and pojos 
	 */
	
	public ArrayList<Student> createStudentsFromStream (String fileName) throws FileNotFoundException;
	
	public void writeToFile (int fileSuffix) throws IOException;
	
	public void modifyFile (String fileName, String fieldToModify, String newValue);
}
