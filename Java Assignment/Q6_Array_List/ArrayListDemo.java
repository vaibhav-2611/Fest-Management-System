import java.util.ArrayList;
public class ArrayListDemo{
	public static void main(String[] args) {
		
		//Create an ArrayList to store Integer types
		ArrayList<Integer> alist = new ArrayList<Integer>();
		
		//Using a for loop, store the squares of the first 10 natural numbers in the list so created. Use the add() method of ArrayList for this purpose.
		for (int i=1;i<=10 ;i++ ) {
			alist.add(i*i);
		}

		//Once again, use a for loop to print all the elements
		System.out.println("printing array: ");		
		for (int i=0;i<10;i++ ) {
			System.out.println(alist.get(i));
		}

		//Print the size of the list (you should not count the items individually)
		System.out.println("size: "+alist.size());

		//remove all the elements from the list using a single method cal
		alist.removeAll(alist);

		//Print whether or not the list is empty using only a single method call
		if(alist.size()==0)
			System.out.println("List is empty");
		else
			System.out.println("List is NOT empty");

	}

}