import java.util.*;

public class StackDemo{
	public static void main(String[] args) {
		Stack<String> stack = new Stack<String>(); //delcalred what datatype stack will store 
		stack.push("10");	//changed integer to string
		stack.push("a");
		System.out.println("The contents of Stack is"+stack);
		System.out.println("The size of an stack is"+stack.size());
		System.out.println("The number poped out is"+stack.pop());
		System.out.println("The number poped out is"+stack.pop());
		System.out.println("The contents of stack is"+stack);
		System.out.println("The size of an Stack is"+stack.size());	
	}
}