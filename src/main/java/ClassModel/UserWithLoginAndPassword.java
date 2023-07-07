package ClassModel;

public class UserWithLoginAndPassword
{
    private String name;
    private String password;


    public UserWithLoginAndPassword(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public UserWithLoginAndPassword(String password) {this.password = password;}
    public UserWithLoginAndPassword(){}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

}
