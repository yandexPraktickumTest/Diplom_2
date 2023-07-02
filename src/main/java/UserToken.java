public class UserToken
{
    private String authorization;
    private String email;
    private String password;
    private String name;

    public UserToken(String authorization, String email, String password, String name)
    {
        this.authorization = authorization;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserToken(String email, String password, String name)
    {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserToken(String authorization) {this.authorization = authorization;}

    public UserToken(){}

    public String getAuthorization() {return authorization;}

    public void setAuthorization(String authorization) {this.authorization = authorization;}
}
