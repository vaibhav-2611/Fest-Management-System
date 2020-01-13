import java.util.Arrays;
public class Student implements Comparable<Student>{
	private String firstName;
	private String lastName;
	Student(String f,String l){
		firstName=f;
		lastName=l;
	}
	public int compareTo(Student s){
		int i= firstName.compareTo(s.firstName);
		if(i<0)
			return -1;
		else if(i>0)
			return 1;
		else{
			int j= lastName.compareTo(s.lastName);
			if(j<0)
				return -1;
			else if(j>0)
				return 1;
			else
				return 0;
		}
	}
	public String toString(){
		return (firstName+" "+lastName);
	}
	public static void main(String[] args) {
		Student[] s=new Student[5];
		s[0] =new Student("Aayush","Gupta");
		s[1] =new Student("Yashsvi","Dixit");
		s[2] =new Student("Yashvardhan","Singh");
		s[3] =new Student("Ayush","Sharma");
		s[4] =new Student("Siddharth","Singh");
		Arrays.sort(s);
		for(Student m : s){
			System.out.println(m);
		}
	}
}