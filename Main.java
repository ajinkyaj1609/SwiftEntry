import java.util.Scanner;
import java.util.ArrayList;

public class Main
{

    //CREATE INSTANCE VARIABLES HERE. Ensure they are static.
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK_BOLD = "\033[1;30m";

    //May want to create an array of goodbye answers.
    static String[] goodBye = {"Thank you for shopping with" + ANSI_BLACK_BOLD + " Chipotle!" + ANSI_RESET, "Have a good day!", ANSI_BLACK_BOLD + "ChipotleBot" + ANSI_RESET + " out", "Come back soon!"};
    static String[] greetings = {"Hello! My name is"+ ANSI_BLACK_BOLD + " ChipotleBot." + ANSI_RESET, "Hi! I'm" + ANSI_BLACK_BOLD + " ChipotleBot." + ANSI_RESET, "Hey," + ANSI_BLACK_BOLD + " ChipotleBot" + ANSI_RESET + " here.", "wsg fam"};
    static String[] error = {"I'm sorry, but there's been a miscommunication", "I'm sorry, I don't understand.", "I don't understand", "bro wtf you on"};
    static String[] affirmative = {"yes", "yeah", "yea", "sure", "ok", "okay", "affirmative", "I would", "want", "I am", "I do"};
    static String[] negative = {"no", "nah", "naw", "not", "negative", "nay", "never", "nope"};
    static String[] evil = {"how dare you", "You will regret that decision.", "Sleep with one eye open tonight."};


   private static final String[] orderTypes = {"burrito", "bowl", "taco"};
    //creating ingredients
  private static final Food[] ingredients = {
        new Food("white rice", 200, 2.50, true),
        new Food("brown rice", 200, 2.50, true),
        new Food("tortilla", 125, 1.25, true),
        new Food("black beans", 250, 2.50, true),
        new Food("pinto beans", 250, 2.50, true),
        new Food("chicken", 300, 3.00, false),
        new Food("carne asada", 350, 3.50, false),
        new Food("steak", 400, 4.00, false),
        new Food("lettuce", 100, 1.50, true),
        new Food("corn", 125, 1.25, true)
    };


       public static void main(String[] args)
       {
        Scanner in = new Scanner (System.in);//Creates scanner object.
        Scanner in2 = new Scanner (System.in);//Creates a second scanner object.
        String phase = "start";
        boolean active = true;
        int calories = 0;
        double price = 0.0;
        String name = "";
        String orderName = "";
        ArrayList<String> order = new ArrayList<>();
        int ordCount = 0;
        boolean isVegetarian = false;
        while (active)
        {
                switch (phase) {
                        case "start": {
                                System.out.println(randMessage(greetings));
                                System.out.println("Note that you can type 'exit' at any time to close the program.");
                                System.out.println("Would you like to order something?");
                                boolean loop = true;
                                while (loop == true) {
                                        String userResp = in.nextLine(); //in.nextLine() uses the scanner object to get the user's responnse as a String
                                        switch (interpretResponse(userResp.toLowerCase(), "yesOrNo")) {
                                                case "y":
                                                        loop = false;
                                                        System.out.println("Great!");
                                                        phase = "veg";
                                                        break;
                                                case "n":
                                                        loop = false;
                                                        System.out.println(ANSI_RED + randMessage(evil) + ANSI_RESET);
                                                        phase = "end";
                                                        break;
                                                case "exit":
                                                        loop = false;
                                                        phase = "end";
                                                        break;
                                                default: 
                                                        System.out.println(randMessage(error));
                                                        System.out.print(" Would you like to order something?");
                                                        break;
                                        }
                                }
                        break;
                        }
                        case "veg": {
                                System.out.println("Are you a vegetarian?");
                                String resp = in.nextLine();
                                switch(interpretResponse(resp.toLowerCase(), "yesOrNo")) {
                                        case "y": {
                                                System.out.println("Don't worry, we have vegetarian options!");
                                                isVegetarian = true;
                                                phase = "order";
                                                break;
                                        }
                                        case "n": {
                                                System.out.println("Gotcha.");
                                                isVegetarian = false;
                                                phase = "order";
                                                break;
                                        }
                                        case "exit": {
                                                phase = "end";
                                                break;
                                        }
                                        default: {
                                                System.out.println(randMessage(error));
                                        } 
                                }
                                break;
                        }
                        
                        case "order": {
                                ArrayList<Food> orderIngredients = new ArrayList<>();
                                Scanner scanr1 = new Scanner (System.in);
                                Scanner scanr2 = new Scanner (System.in);
                                System.out.println("Your options are: ");
                                for(int i = 0; i < orderTypes.length; i++){
                                        System.out.println(orderTypes[i]);
                                }
                                System.out.println("What would you like to order?");
                                String orderType = scanr1.nextLine();
                                orderName = interpretResponse(orderType, "menuItems");
                                if (interpretResponse(orderType.toLowerCase(), "menuItems").equals("ERROR")) {
                                        System.out.println("I'm sorry, but that's not on the menu. Please order something on the menu.");
                                }
                                else if (interpretResponse(orderType.toLowerCase(), "menuItems").equals("ERROR: MULTIPLE")) {
                                        System.out.println("Order one item at a time, please.");
                                }
                                else if (interpretResponse(orderType.toLowerCase(), "menuItems").equals("exit")) {
                                        phase = "end";
                                        break;
                                } 
                                else {
                                        
                                        boolean ingredientCheck = true;
                                        while(ingredientCheck){
                                                System.out.println("These are your ingredient choices");
                                                System.out.println("Type 'done' when you have finished selecting ingredients.");
                                                for(Food ingredient : ingredients){
                                                        if (isVegetarian) {
                                                                if (ingredient.getIsVeg() == true) System.out.println(ingredient.getName());
                                                        }
                                                        else System.out.println(ingredient.getName());
                                                }
                                                
                                                System.out.println("What ingredients would you like?");
                                                String userIngredient = scanr2.nextLine();
                                                if(userIngredient.toLowerCase().contains("done")){
                                                        ingredientCheck = false;
                                                        break;
                                                }
                                                
                                                if(userIngredient.toLowerCase().contains("exit")) {
                                                        phase = "end";
                                                        break;
                                                }

                                                Boolean isInMenu = false;
                                                for(Food ingredient : ingredients){
                                                        if(userIngredient.toLowerCase().contains(ingredient.getName())){
                                                                isInMenu = true;
                                                                orderIngredients.add(ingredient);
                                                                System.out.println(ingredient.getName() + " has been added to your order.");
                                                        }
                                                }
                                                 
                                                if(!isInMenu){
                                                        System.out.println("That is not a valid ingredient");
                                                }
                                        }
                                        
                                        name += "a " + orderName + " with: ";
                                        for(int i = 0; i < orderIngredients.size(); i++){
                                                calories += orderIngredients.get(i).getCalories();
                                        }

                                        for(int i = 0; i < orderIngredients.size(); i++){
                                                price += orderIngredients.get(i).getPrice();
                                        }
                                        
                                        if (orderIngredients.size() > 1) {
                                                for(int i = 0; i < orderIngredients.size() - 1; i++){
                                                        name += orderIngredients.get(i).getName() + ", ";
                                                }
                                                name += "and " + orderIngredients.getLast().getName();
                                        }
                                        else name += orderIngredients.getFirst().getName();
                                        order.add(ordCount, name);

                                        Scanner scanr3 = new Scanner (System.in);
                                        boolean loop = true;
                                        if (phase == "end") loop = false;
                                        while (loop) {
                                                System.out.println("You Ordered:");
                                                for(String o : order){ System.out.println(o); };
                                                System.out.println("Would you like to know the 'price', the 'calories', 'both', or 'neither' of your order?");
                                                String finalResponse = scanr3.nextLine().toLowerCase();
                                                loop = false;
                                                switch (finalResponse) {
                                                case "price":
                                                        System.out.println("Total Price: $" + price);
                                                        break;
                                                case "calories":
                                                        System.out.println("Total Calories: " + calories);
                                                        break;
                                                case "both":
                                                        System.out.println("Total Price: $" + price);
                                                        System.out.println("Total Calories: " + calories);
                                                        break;
                                                case "neither":
                                                        phase = "end";
                                                        scanr1.close();
                                                        scanr2.close();
                                                        scanr3.close();
                                                        break;
                                                case "exit":
                                                        phase = "end";
                                                        scanr1.close();
                                                        scanr2.close();
                                                        scanr3.close();
                                                        break;
                                                default:
                                                        System.out.println(randMessage(error));
                                                        System.out.println(" Please type 'price', 'calories', 'both', or 'neither'."); loop = true; break;
                                                }
                                        }
                                        if (phase != "end") {
                                                boolean loopEnd = true;
                                                while (loopEnd) {
                                                System.out.println("Would you like to order something else?");
                                                String resp = scanr1.nextLine();
                                                loopEnd = false;
                                                switch (interpretResponse(resp.toLowerCase(), "yesOrNo")) {
                                                        case "n": phase = "end"; break;
                                                        case "y": System.out.println("Got it."); name = ""; ordCount++; break;
                                                        default: System.out.println("how did you get this far and"); System.out.println("still fail to enter a valid response? "); loopEnd = true; break;
                                                }
                                                }
                                        }



                                        
                                }
                        break;
                        }
                        case "end": {
                                System.out.println(randMessage(goodBye));
                                active = false;
                                in.close();
                                in2.close();
                        break;
                        }
                }
        } 
        

        //You will need to make sure your chatbot continues looping until it sees certain keywords from the user such as "bye","goodbye"...
        // do you need a while loop or a for loop?
          
        //Consider: How do I check what the user says and compare it to my keywords? What methods do I have?
             
       }

        public static String randMessage(String[] msg) {
                int max = msg.length;
                int i = (int) (Math.random() * max);
                return msg[i];
        }

        public static String interpretResponse(String resp, String type) {
                String item = "";
                boolean hasY = false;
                int yCount = 0;
                boolean hasN = false;
                int nCount = 0;
                int orderCount = 0;
                if (resp.equals("exit")) return "exit";
                else switch(type) {
                        case "yesOrNo": {
                                for (String y : affirmative) { //does it contain an affirmative?
                                        int index = resp.indexOf(y.toLowerCase());
                                        if (index != -1){ hasY = true; yCount++; }
                                        else if (resp.equals("y")) hasY = true;
                                }
                                for (String n : negative) { //does it contain a negative?
                                        int index = resp.indexOf(n.toLowerCase());
                                        if (index != -1){ hasN = true; nCount++; }
                                        else if (resp.equals("n")) hasN = true;
                                }
                                if (hasY == hasN) {
                                        if (yCount > nCount) return "y";
                                        else if (nCount > yCount) return "n";
                                        if(resp.toLowerCase().equals("I would not".toLowerCase())) return "n";
                                        else return "ERROR";
                                }
                                else {
                                        if (hasY) return "y";
                                        else return "n";
                                }
                        }
                        case "menuItems": {
                                for (String order : orderTypes) {
                                        int index = resp.indexOf(order);
                                        if (index != -1){ orderCount++; item = order; }
                                }
                                if (orderCount > 1) item = "ERROR: MULTIPLE";
                                else if (orderCount == 0) item = "ERROR";
                                return item;
                        }
                }
               

                return "ERROR";
        }

      

}