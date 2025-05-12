public class Item {
    private String type; 
    private boolean veg;
    private double price;
    private int calories;
    private Food[] ingredients;

    public Item(String t, boolean v, double p, int c, Food[] i) {
        this.type = t;
        this.veg = v;
        this.price = p;
        this.calories = c;
        this.ingredients = i;
    }

    public String getType(){
        return this.type;
    }

    public Boolean getVeg(){
        return this.veg;
    }

    public double getPrice(){
        return this.price;
    }

    public int getCalories(){
        return this.calories;
    }

    public Food[] getIngredients(){
        return this.ingredients;
    }

    public String toString(){
        return "Order type:" + this.type + "Veg?" + this.veg + "Price: " 
        + this.price + "Calories: " + this.calories + "Ingredients: " 
        + this.ingredients;
    }

}
