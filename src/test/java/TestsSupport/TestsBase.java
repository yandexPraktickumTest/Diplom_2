package TestsSupport;

import org.junit.Before;

public class TestsBase
{
   private Object userForLogIn;
   private Object userForLogInWithInvalidEmail;
   private Object userForLogInWithInvalidPassword;
   private Object userForLogInWithInvalidEmailAndPassword;
   private Object userForRegistration;
   private Object alreadyExistingUserForRegistration;
   private Object userWithNameAndPassword;
   private Object userWithNameAndEmail;
   private Object userWIthTokenByRegistration;
   private Object userWithTokenByLogIn;
   private Object UserWithNullToken;
   private Object ingredients;
   private Object ingredientsWithInvalidIngredients;
   private Object IngredientsWithOutIngredients;
   private Object IngredientsWithOutToken;

    public Object getUserForLogIn() {return userForLogIn;}

    public void setUserForLogIn(Object userForLogIn) {this.userForLogIn = userForLogIn;}

    public Object getUserForLogInWithInvalidEmail() {return userForLogInWithInvalidEmail;}

    public void setUserForLogInWithInvalidEmail(Object userForLogInWithInvalidEmail) {this.userForLogInWithInvalidEmail = userForLogInWithInvalidEmail;}

    public Object getUserForLogInWithInvalidPassword() {return userForLogInWithInvalidPassword;}

    public void setUserForLogInWithInvalidPassword(Object userForLogInWithInvalidPassword) {this.userForLogInWithInvalidPassword = userForLogInWithInvalidPassword;}

    public Object getUserForLogInWithInvalidEmailAndPassword() {return userForLogInWithInvalidEmailAndPassword;}

    public void setUserForLogInWithInvalidEmailAndPassword(Object userForLogInWithInvalidEmailAndPassword)
    {this.userForLogInWithInvalidEmailAndPassword = userForLogInWithInvalidEmailAndPassword;}

    public Object getUserForRegistration() {return userForRegistration;}

    public void setUserForRegistration(Object userForRegistration) {this.userForRegistration = userForRegistration;}

    public Object getAlreadyExistingUserForRegistration() {return alreadyExistingUserForRegistration;}

    public void setAlreadyExistingUserForRegistration(Object alreadyExistingUserForRegistration)
    {this.alreadyExistingUserForRegistration = alreadyExistingUserForRegistration;}

    public Object getUserWithNameAndPassword() {return userWithNameAndPassword;}

    public void setUserWithNameAndPassword(Object userWithNameAndPassword) {this.userWithNameAndPassword = userWithNameAndPassword;}

    public Object getUserWithNameAndEmail() {return userWithNameAndEmail;}

    public void setUserWithNameAndEmail(Object userWithNameAndEmail) {this.userWithNameAndEmail = userWithNameAndEmail;}

    public Object getUserWIthTokenByRegistration() {return userWIthTokenByRegistration;}

    public void setUserWIthTokenByRegistration(Object userWIthTokenByRegistration) {this.userWIthTokenByRegistration = userWIthTokenByRegistration;}

    public Object getUserWithTokenByLogIn() {return userWithTokenByLogIn;}

    public void setUserWithTokenByLogIn(Object userWithTokenByLogIn) {this.userWithTokenByLogIn = userWithTokenByLogIn;}

    public Object getUserWithNullToken() {return UserWithNullToken;}

    public void setUserWithNullToken(Object userWithNullToken) {UserWithNullToken = userWithNullToken;}

    public Object getIngredients() {return ingredients;}

    public void setIngredients(Object ingredients) {this.ingredients = ingredients;}

    public Object getIngredientsWithInvalidIngredients() {return ingredientsWithInvalidIngredients;}

    public void setIngredientsWithInvalidIngredients(Object ingredientsWithInvalidIngredients) {this.ingredientsWithInvalidIngredients = ingredientsWithInvalidIngredients;}

    public Object getIngredientsWithOutIngredients() {return IngredientsWithOutIngredients;}

    public void setIngredientsWithOutIngredients(Object ingredientsWithOutIngredients) {IngredientsWithOutIngredients = ingredientsWithOutIngredients;}

    public Object getIngredientsWithOutToken() {return IngredientsWithOutToken;}

    public void setIngredientsWithOutToken(Object ingredientsWithOutToken) {IngredientsWithOutToken = ingredientsWithOutToken;}


}
