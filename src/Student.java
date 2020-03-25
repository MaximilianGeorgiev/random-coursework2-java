
public class Student implements Comparable<Student> {
	private String PIN; // fakulteten nomer
	int points;
	
	Student(){
		this.PIN = "18621814";
		this.points = 5;
	}
	
	Student(Student st){
		this.setPIN(st.getPIN());
		this.setPoints(st.getPoints());
	}
	
	Student(String pin, int points){
		this.setPIN(pin);
		this.setPoints(points);
	}
	
	public void setPIN (String pin) {
		this.PIN = pin;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public String getPIN() {
		return this.PIN;
	}
	
	public int getPoints() {
		return this.points;
	}
	

	@Override
	public int compareTo(Student st) {
		return (int)Integer.compare(this.getPoints(), st.getPoints());
	}
	
	@Override
	public boolean equals(Object st0) {
		/*checking whether object is the same while still implementing the comparable methods
		 we are checking whether this object is really an instance of the class we are comparing
		 we try whether the passed class is null, we try to cast it and finally we compare by points (per task)
		*/
		
		if (st0 == null){
			return false;
		}
		
		if (!Student.class.isAssignableFrom(st0.getClass())) {
            return false;
        }
		
		final Student student = (Student) st0;
		
		return this.getPoints() == student.getPoints();
	}
	
	@Override
	public String toString() {
		return this.getPIN() + " " + this.getPoints() + "\n";
	}
}
