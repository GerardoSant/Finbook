package Model.User;

public class User {
    private String companyName;
    private String companyRFC;

    public User(String companyName, String companyRFC) {
        this.companyName = companyName;
        this.companyRFC = companyRFC;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyRFC() {
        return companyRFC;
    }
}
