public class Food {
    private String name;
    private int calories;
    private double price;
    private boolean isVeg;

    public Food(String n, int c, double p, boolean iV){
        this.name = n;
        this.calories = c;
        this.price = p;
        this.isVeg = iV;
    }

    public String getName(){
        return this.name;
    }

    public int getCalories(){
        return this.calories;
    }

    public double getPrice(){
        return this.price;
    }

    public boolean getIsVeg(){
        return this.isVeg;
    }
}
