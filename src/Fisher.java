

public class Fisher {

    private int fisherId;
    private String username;
    private String fName;
    private String sName;
    private String fullName;
    private String password;
    private String mobileNo;

    public Fisher(){}

    public Fisher(String username, String password, String fname, String sname,  String mobileNo){
        this.username = username;
        this.fName = fname;
        this.sName = sname;
        this.password = password;
        this.mobileNo = mobileNo;
        this.fullName = fname + " " + sname;
    }

    public Fisher(int fisherId, String username, String password, String fname, String sname,  String mobileNo){
        this.fisherId = fisherId;
        this.username = username;
        this.fName = fname;
        this.sName = sname;
        this.password = password;
        this.mobileNo = mobileNo;
        this.fullName = fname + " " + sname;
    }

    public int getID(){
        return fisherId;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String newUsername){
        username = newUsername;
    }
    public String getFname(){
        return fName;
    }
    public void setFname(String newFname){
        fName = newFname;
    }
    public String getSname(){
        return sName;
    }
    public void setSname(String sname){
        sName = sname;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String pass){
        password = pass;
    }
    public String getMobileNo(){
        return mobileNo;
    }
    public void setMobileNo(String number){
        mobileNo = number;
    }
    public String getFullName()
    {
        return fullName;
    }

    public static void main(String[] args) {
        Fisher james = new Fisher();
        james.setFname("James");
        james.setSname("January");
        james.setUsername("JJanuary123");
        james.setPassword("Epic2012");
        james.setMobileNo("999");
        System.out.println(james);
    }

    @Override
    public String toString() {
        return "Fisher{" +
                "fisherId=" + fisherId +
                ", username='" + username + '\'' +
                ", fName='" + fName + '\'' +
                ", sName='" + sName + '\'' +
                ", password='" + password + '\'' +
                ", mobileNo=" + mobileNo +
                '}';
    }
}
