public class DynamicArray{
	private int a[];
	private int size=0;
	DynamicArray(){
		a=new int[10];
	}
	//add(int x): Adds a given element to the end of the existing array.
	public void add(int x){
		if(a.length==size){
			int b[]=new int[2*a.length];
			for (int i=0;i<size ;i++ ) {
				b[i]=a[i];
			}
			a=null;
			a=b;
		}
		a[size]=x;
		size++;
	}
	//size(): Returns the number of elements currently stored in the array
	public int size(){
		return size;
	}
	//remove(): Removes an element, if any, from the end of the array.
	public void remove() {
		if((a.length/2)>=size){
			int b[]=new int[a.length/2];
			for (int i=0;i<size;i++) {
				b[i]=a[i];
			}
			a=null;
			a=b;
		}
		if(size >0)
			size--;
	}
	//print(): Prints all the elements currently stored in the array.
	public void print(){
		for (int i=0;i<size;i++ ) {
			System.out.println(a[i]);
		}
	} 
	//this gives length of array allocated
	public int length(){
		return a.length;
	}
	//main
	public static void main(String[] args) {
		DynamicArray d=new DynamicArray();
		System.out.println("ADDING");
		for (int i=0;i<25;i++ ){
			d.add(i);
			System.out.println("adding "+(i)+"\tnumber of elements "+d.size()+" \tarray length :"+d.length());			
		}
		System.out.println("REMOVING");
		for (int i=0;i<25;i++ ){
			d.remove();
			System.out.println("removing "+(i)+"\tnumber of elements "+d.size()+" \tarray length :"+d.length());
		}
	}
}