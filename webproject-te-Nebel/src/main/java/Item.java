
public class Item {
	
	public int id;
	public String itemName;
	public int price;
	
	Item(int id, String name, int price) {
		this.id = id;
		this.itemName = name;
		this.price = price;
	}
	
	Item(String name, int price) {
		this.itemName = name;
		this.price = price;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getItemName() {
		return this.itemName;
	}
	
	public void setItemName(String name) {
		this.itemName = name;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "<br>Item: " + this.itemName + "<br> Price: " + this.price + "<br>";
	}
}
