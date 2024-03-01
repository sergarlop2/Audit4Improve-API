package us.muit.fs.a4i.model.entities;
import java.awt.Color;

public class Font {
	
	private Color color;
	private java.awt.Font font;
	/**
	 * Todos los valores por defecto
	 */
	public Font() {
		this.color = Color.black;
		this.font = new java.awt.Font ("Serif", java.awt.Font.PLAIN , 10);
	}
	/**
	 * Personalizando el color
	 * @param color color
	 */
	public Font (String color) {
		this.font = new java.awt.Font ("Serif", java.awt.Font.PLAIN , 10);
		switch(color){
		case "red","rojo":
			this.color=Color.red;
			break;
		case "green","verde":
			this.color=Color.green;
			break;
		case "blue","azul":
			this.color=Color.blue;
		    break;
		default:
			this.color=Color.black;
		}			
	}
	/**
	 * Todos los Valores personalizados salvo el estilo
	 * @param family tipo de letra	
	 * @param size tamaño
	 * @param color color
	 */
	public Font (String family, int size, String color) {
		this.font=new java.awt.Font (family, java.awt.Font.PLAIN , size);
		switch(color){
		case "red","rojo":
			this.color=Color.red;
			break;
		case "green","verde":
			this.color=Color.green;
			break;
		case "blue","azul":
			this.color=Color.blue;
		    break;
		default:
			this.color=Color.black;
		}			
	}
	/**
	 * Todos los Valores personalizados
	 * @param family tipo de letra
	 * @param format formato
	 * @param size tamaño
	 * @param color color
	 */
	public Font (String family, int style, int size, String color) {
		this.font=new java.awt.Font (family, style , size);
		switch(color){
		case "red","rojo":
			this.color=Color.red;
			break;
		case "green","verde":
			this.color=Color.green;
			break;
		case "blue","azul":
			this.color=Color.blue;
		    break;
		default:
			this.color=Color.black;
		}			
	}

	public Color getColor() {
		return color;
	}
	public java.awt.Font getFont() {
		return font;
	}
}