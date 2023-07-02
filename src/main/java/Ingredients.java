import java.util.ArrayList;

public class Ingredients
{
    private ArrayList<String> ingredients;
    private String authorization;

    public Ingredients(ArrayList<String> ingredients, String authorization)
    {
        this.ingredients = ingredients;
        this.authorization = authorization;
    }

    public Ingredients(String authorization) {
        this.authorization = authorization;
    }

    public Ingredients(){}

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {this.ingredients = ingredients;}

    public String getAuthorization() {return authorization;}

    public void setAuthorization(String authorization) {this.authorization = authorization;}
}
