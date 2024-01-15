package main.models;

public class CreateOrder {
 private String [] ingredients;

    public CreateOrder(String[] ingredients) {
        ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        ingredients = ingredients;
    }
}