package us.muit.fs.a4i.model.entities;

public class Font extends java.awt.Font{

	private String color;
	
	
	public Font(String name, int size, String color){
		super(name,PLAIN,size);
		//this.name = name;
		//this.size = size;
		this.color = color;
	}

	public String getColor(){
		return this.color;
	}
	/*
	public Font getFont(){
		return null;
	
	}
	
	public String getName(){
		return name;
	}
	
	public int getSize(){
		return size;
	}
	*/
}
