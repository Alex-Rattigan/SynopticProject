import java.sql.Date;

public class Job
{
    private int id;
    private String fishType;
    private int amountKg;
    private double payPerKg;
    private Date dateCreated;
    private Date dateDue;
    private String description;
    private boolean isCompleted;

    Job(String fishType, int amountKg, double payPerKg, Date dateCreated, Date dateDue, String description, boolean isCompleted)
    {
        this.fishType = fishType;
        this.amountKg = amountKg;
        this.payPerKg = payPerKg;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public int getId()
    {
        return id;
    }

    public String getFishType()
    {
        return fishType;
    }

    public void setFishType(String fishType)
    {
        this.fishType = fishType;
    }

    public int getAmountKg()
    {
        return amountKg;
    }

    public void setAmountKg(int amountKg)
    {
        this.amountKg = amountKg;
    }

    public double getPayPerKg()
    {
        return payPerKg;
    }

    public void setPayPerKg(double payPerKg)
    {
        this.payPerKg = payPerKg;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted)
    {
        this.isCompleted = isCompleted;
    }

    public String toString()
    {
        return "FISH TYPE = " + fishType + ", AMOUNT IN KG = " + amountKg + ", PAY PER KG = " + payPerKg +
        ", DATE SET = " + dateCreated + ", DATE DUE = " + dateDue + ", COMPLETED? = " + false;
    }

    public static void main(String[] args)
    {
        Job job = new Job("fish", 5, 4.0, Date.valueOf("2021-06-04"), Date.valueOf("2021-06-07"), "fuel and bait",false);

        System.out.println(job.toString());
    }
}
