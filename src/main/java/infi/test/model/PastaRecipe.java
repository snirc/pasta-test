package infi.test.model;

public class PastaRecipe {

	private String type;
	private String howToCookIt;
	private String image;
	private double price;
	private Object ingredients;
	
	

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHowToCookIt() {
		return howToCookIt;
	}
	public void setHowToCookIt(String howToCookIt) {
		this.howToCookIt = howToCookIt;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Object getIngredients() {
		return ingredients;
	}
	public void setIngredients(Object ingredients) {
		this.ingredients = ingredients;
	}
}
