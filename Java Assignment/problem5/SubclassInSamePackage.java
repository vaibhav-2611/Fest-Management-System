class BaseClass{
	public int x=10;
	private int y=10;
	protected int z=10;
	int a=10; // implicit default access modifier
	public int getX(){
		return x;
	}
	public void setX(int x){
		this.x=x;
	}
	public int getY(){			//changed private to public so that it becomes accessible form main
		return y;
	}
	public void setY(int y){	//changed private to public so that it becomes accessible form main
		this.y=y;
	}
	protected int getZ(){
		return z;
	}
	protected void setZ(int z){
		this.z=z;
	}
	int getA(){
		return a;
	}
	void setA(int a){
		this.a=a;
	}
}
public class SubclassInSamePackage extends BaseClass{
	public static void main(String[] args){
		BaseClass rr= new BaseClass();
		rr.z=0;
		SubclassInSamePackage subClassObj = new SubclassInSamePackage();
		//Access MOdifiers - Public
		System.out.println("The value of x is :"+subClassObj.x);
		subClassObj.setX(20);
		System.out.println("The value of x is :"+subClassObj.x);
		System.out.println("The value of y is :"+subClassObj.getY()); //changed y to getY() because y is private member and cant be accessed outside the class.
		subClassObj.setY(20);
		System.out.println("The value of y is :"+subClassObj.getY()); //changed y to getY() because y is private member and cant be accessed outside the class.
		//Access MOdifiers - Protected
		System.out.println("The value of z is :"+subClassObj.z);
		subClassObj.setZ(30);
		System.out.println("The value of z is :"+subClassObj.z);
		//Access MOdifiers - Default
		System.out.println("The value of x is :"+subClassObj.a);
		subClassObj.setA(20);
		System.out.println("value of x is :"+ subClassObj.a);
	}
}