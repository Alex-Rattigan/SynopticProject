public class Intermediary {


    private int intermediaryId;
    private String username;
    private String fName;
    private String sName;
    private String password;
    private String mobileNo;

    public Intermediary(){}

    public Intermediary(String username, String fname, String sname, String password, String mobileNo){
        this.username = username;
        this.fName = fname;
        this.sName = sname;
        this.password = password;
        this.mobileNo = mobileNo;
    }

    public int getID(){
        return intermediaryId;
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

    public static void main(String[] args) {
        Intermediary David = new Intermediary();
        David.setFname("James");
        David.setSname("January");
        David.setUsername("JJanuary123");
        David.setPassword("Epic2012");
        David.setMobileNo("999");
        System.out.println(David);
    }

    @Override
    public String toString() {
        return "Fisher{" +
                "fisherId=" + intermediaryId +
                ", username='" + username + '\'' +
                ", fName='" + fName + '\'' +
                ", sName='" + sName + '\'' +
                ", password='" + password + '\'' +
                ", mobileNo=" + mobileNo +
                '}';
    }

}
