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
    private int intermediaryId;
    private String intermediaryName;
    private String fisherName;

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

    Job(int id, String fishType, int amountKg, double payPerKg, Date dateCreated, Date dateDue, String description, boolean isCompleted)
    {
        this.id = id;
        this.fishType = fishType;
        this.amountKg = amountKg;
        this.payPerKg = payPerKg;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    Job(int id, String fishType, int amountKg, double payPerKg, Date dateCreated, Date dateDue, String description, boolean isCompleted, int intermediaryId, Integer fisherId)
    {
        this.id = id;
        this.fishType = fishType;
        this.amountKg = amountKg;
        this.payPerKg = payPerKg;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.description = description;
        this.isCompleted = isCompleted;
        this.intermediaryName = DatabaseController.selectIntermediaryRecord(intermediaryId).getFname() + " " + DatabaseController.selectIntermediaryRecord(intermediaryId).getSname();
        this.fisherName = DatabaseController.selectFisherRecord(fisherId).getFname() + " " + DatabaseController.selectIntermediaryRecord(fisherId).getSname();

    }

    Job(int id, String fishType, int amountKg, double payPerKg, Date dateCreated, Date dateDue, String description, boolean isCompleted, int intermediaryId)
    {
        this.id = id;
        this.fishType = fishType;
        this.amountKg = amountKg;
        this.payPerKg = payPerKg;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.description = description;
        this.isCompleted = isCompleted;
        this.intermediaryId = intermediaryId;
        this.intermediaryName = DatabaseController.selectIntermediaryRecord(intermediaryId).getFname() + " " + DatabaseController.selectIntermediaryRecord(intermediaryId).getSname();
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

    public Date getDateDue()
    {
        return dateDue;
    }

    public void setDateDue(Date dateDue)
    {
        this.dateDue = dateDue;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted)
    {
        this.isCompleted = isCompleted;
    }

    public String getIntermediaryName()
    {
        return intermediaryName;
    }

    public int getIntermediaryId()
    {
        return intermediaryId;
    }

    public void setFisherName(String fisherName){this.fisherName = fisherName;}

    public String getFisherName()
    {
        return fisherName;
    }

    public String toString()
    {
        return "ID = " + id + ", FISH TYPE = " + fishType + ", AMOUNT IN KG = " + amountKg + ", PAY PER KG = " + payPerKg +
        ", DATE SET = " + dateCreated + ", DATE DUE = " + dateDue + ", COMPLETED? = " + false;
    }

    public static void main(String[] args)
    {
        Job job = new Job("fish", 5, 4.0, Date.valueOf("2021-06-04"), Date.valueOf("2021-06-07"), "fuel and bait",false);

        System.out.println(job.toString());
    }
}
