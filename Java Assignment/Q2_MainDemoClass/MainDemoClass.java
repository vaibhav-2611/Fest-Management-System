class Person{
	protected String name;
	protected int age;
	Person(String s,int a){
		this.name=s;
		this.age=a;
	}
	public void display(){
		System.out.print("\t\tname:\t"+name+"\n\t\tage:\t"+age+"\n");
	}
}
class Student extends Person{
	protected String roll;
	protected String branch;
	public void display(){
		super.display();
		System.out.print("\t\troll:\t"+roll+"\n\t\tbranch:\t"+branch+"\n");
	}
	Student(String name,int age,String roll,String branch){
		super(name,age);
		this.roll=roll;
		this.branch=branch;
	}
}
class Employee extends Person{
	protected int ecNo;
	protected String doj;
	public void display(){
		super.display();
		System.out.print("\t\tecNo:\t"+ecNo+"\n\t\tdoj:\t"+doj+"\n");
	}
	Employee(String name,int age,int ecNo,String doj){
		super(name,age);
		this.ecNo=ecNo;
		this.doj=doj;
	}
}
class Staff extends Employee{
	protected String designation;
	public void display(){
		super.display();
		System.out.print("\t\tdesignation:\t"+designation+"\n");
	}
	Staff(String name,int age,int ecNo,String doj, String designation){
		super(name,age,ecNo,doj);
		this.designation=designation;
	}
}

class Faculty extends Employee{
	protected String designation;
	public void display(){
		super.display(); 
		System.out.print("\t\tdesignation:\t"+designation+"\n");
	}
	Faculty(String name,int age,int ecNo,String doj, String designation){
		super(name,age,ecNo,doj);
		this.designation=designation;
	}
}
public class MainDemoClass {
	public static void main(String[] args) {
		Person p =new Person("Jack",20);
		Student stud = new Student("Micky",19,"16CS10000","Computer Science and Engineering");
		Employee e = new Employee("Kumar",35,3500,"01-01-2018");
		Staff s = new Staff("Sharma",30,2001,"01-01-2017","Technical");
		Faculty f = new Faculty("Mukherjee",50,1001,"01-01-2000","Assistant Professor");
		
		System.out.println("Person:");
		p.display();
		System.out.println("Student:");
		stud.display();
		System.out.println("Employee:");
		e.display();
		System.out.println("Staff:");
		s.display();
		System.out.println("Faculty:");
		f.display();
	}
}