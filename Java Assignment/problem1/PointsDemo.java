class point{
	int x,y;
	void setPoint(int a, int b){ //rename the getPoint method
		x=a;
		y=b;
	}
}
public class PointsDemo{
	float distance;
	public static void main(String args[]){
		point p1 = new point();
		point p2 = p1;
		point p3 = new point();
		point p4 = new point();
		p1.setPoint(5,10);
		p2.setPoint(15,20);
		p3.setPoint(20,30);
		p4.setPoint(30,40);
		
		System.out.println("X1="+p1.x+" Y1="+p1.y);
		System.out.println("X2="+p2.x+" Y2="+p2.y);
		int dx=p3.x-p4.x;
		int dy=p3.y-p4.y;
		PointsDemo dist= new PointsDemo();			//created an object of calss PointDemo
		dist.distance=(float)Math.sqrt(dx*dx + dy*dy); //typecasting double to float 
		System.out.println("Distance="+ dist.distance);
	}
}