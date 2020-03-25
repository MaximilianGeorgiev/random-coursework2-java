import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class StudentGroup implements Modifiable {
	private ArrayList<Student> students;
	private LinkedHashMap<Integer, ArrayList<Student>> boundaries;
	
	/*
	 * Somehow students should be evenly distrubited between following boundaries:
	 * 0-IP1
	 * IP1-IP2
	 * IP2-IP3
	 * IP3-IP4
	 * 
	 * We don't need to store the upper boundary since it is self expalanatory and for the sake of ease of use
	 * we will only store lower boundary as key
	 * 
	 * Key: <Lower Boundary> 
	 * Value: List of the students
	 */
	
	public StudentGroup(String fileName, int iP1, int iP2, int iP3, int iP4) throws IOException {
		this.setStudents(createStudentsFromStream(fileName));
		this.setBoundaries(iP1, iP2, iP3, iP4);
	}
	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
	
	public LinkedHashMap<Integer, ArrayList<Student>> getBoundaries(){
		return this.boundaries;
	}
	
	private void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
	
	private void setBoundaries(int iP1, int iP2, int iP3, int iP4) {
		if (!(iP1 < iP2 && iP2 < iP3 && iP3 < iP4)) {
			System.out.println("Initialization of boundaries failed. \n");
			return;
		}
		
		this.boundaries = new LinkedHashMap<>();
		this.getBoundaries().put(0, new ArrayList<Student>());
		this.getBoundaries().put(iP1, new ArrayList<Student>());
		this.getBoundaries().put(iP2, new ArrayList<Student>());
		this.getBoundaries().put(iP3, new ArrayList<Student>());	
		
		for (Student student : this.getStudents()) {
			if (student.getPoints() >= 0 && student.getPoints() <= iP1) {
				this.getBoundaries().get(0).add(student);
			} else if (student.getPoints() > iP1 && student.getPoints() <= iP2) {
				this.getBoundaries().get(iP1).add(student);
			} else if (student.getPoints() > iP2 && student.getPoints() <= iP3) {
				this.getBoundaries().get(iP2).add(student);
			} else if (student.getPoints() > iP3 && student.getPoints() <= iP4) {
				this.getBoundaries().get(iP3).add(student);
			}
		}
	}
	
	@Override
	public ArrayList<Student> createStudentsFromStream (String fileName) throws FileNotFoundException {
		Scanner sc;
		
		/*
		 * Reading from file and console combined. In main we will handle the input and let the
		 * user know that if they provide "console" that means that we will read from the console
		 * rather than a file. Scanner works both with system stream and files so why make separate functions
		 */
		
		if (fileName.equalsIgnoreCase("console")) {
			sc = new Scanner(System.in);
		} else {
			try {
			File file = new File(fileName);
			sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
			    e.printStackTrace();
			    return null;
			}
		}
		
		ArrayList<Student> list = new ArrayList<>();
		
		while (sc.hasNextLine()) {	
			if (sc.nextLine().equalsIgnoreCase("end")) {
				break;
			}
			
			String[] input = sc.nextLine().split(" ");
			list.add(new Student(input[0], Integer.parseInt(input[1])));
		}
		
		sc.close();
		return list;
	}
	
	@Override
	public void writeToFile(int fileSuffix) throws IOException {
		/*
		 * Rather than opening an existing file I decided to return a File. 
		 * Every instance of StudentGroup will be able to create a new file listing
		 * all the students in that instance. Figured it would be better than having a 
		 * common file where all the instances will write its data
		 */
		try {
		      FileWriter myWriter = new FileWriter(new String("StudentGroup_" + fileSuffix)); //StudentGroup_03 for example
		     
		      for (Student student : this.getStudents()) {
		    	  myWriter.write(student.getPIN() + " " + student.getPoints() + "\n");
		      }
		      
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

	@Override
	public void modifyFile(String fileName, String fieldToModify, String newValue) {
		ArrayList<String> lines = new ArrayList<String>();
		String lineToAdd = " ";
		
		/*
		 * We iterate through the whole file and store every line in an array
		 *  If the line contains the field we are searching for, we modify it with 'newValue'
		 *  and store it in the lines arraylist. Then we flush everything, close the file
		 *  and write all the contents again.
		 *  
		 *  Not a big fan of reading and writing at the same time myself, a little bit memory consuming
		 *  but couldn't care less
		 */
				
		try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((lineToAdd = bufferedReader.readLine()) != null) {
                if (lineToAdd.contains(fieldToModify)) {
                	lineToAdd = lineToAdd.replace(fieldToModify, newValue);
               }
                lines.add(lineToAdd);
             }
            fileReader.close();
            bufferedReader.close();
		
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileWriter);
            for(String line : lines) {
                 out.write(line);
            }
            
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	@Override
	public String toString() {
		String output = " ";
		
		for (Student st : this.getStudents()) {
			output += st.toString();
		}
		
		return output;
	}
}
