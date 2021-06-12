import java.sql.Date;
import java.text.SimpleDateFormat;

// References: http://tutorials.jenkov.com/java-date-time/parsing-formatting-dates.html

public class Job
{
    private int id;
    private String fishType;
    private int amountKg;
    private double payPerKg;
    private Date dateCreated;
    private Date dateDue;
    private String formattedDueDate;
    private String description;
    private boolean isCompleted;
    private int intermediaryId;
    private String intermediaryName;
    private String fisherName;

    public Job(String fishType, int amountKg, double payPerKg, Date dateCreated, Date dateDue, String description, boolean isCompleted)
    {
        this.fishType = fishType;
        this.amountKg = amountKg;
        this.payPerKg = payPerKg;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.description = description;
        this.isCompleted = isCompleted;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.formattedDueDate = format.format(dateDue);
    }

    public Job(int id, String fishType, int amountKg, double payPerKg, Date dateCreated, Date dateDue, String description, boolean isCompleted)
    {
        this.id = id;
        this.fishType = fishType;
        this.amountKg = amountKg;
        this.payPerKg = payPerKg;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.description = description;
        this.isCompleted = isCompleted;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.formattedDueDate = format.format(dateDue);
    }

    public Job(int id, String fishType, int amountKg, double payPerKg, Date dateCreated, Date dateDue, String description, boolean isCompleted, int intermediaryId, Integer fisherId)
    {
        this.id = id;
        this.fishType = fishType;
        this.amountKg = amountKg;
        this.payPerKg = payPerKg;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.description = description;
        this.isCompleted = isCompleted;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.formattedDueDate = format.format(dateDue);

        this.intermediaryName = CSVController.selectIntermediaryRecord(intermediaryId).getFname() + " " + CSVController.selectIntermediaryRecord(intermediaryId).getSname();
        this.fisherName = CSVController.selectFisherRecord(fisherId).getFname() + " " + CSVController.selectIntermediaryRecord(fisherId).getSname();

    }

    public Job(int id, String fishType, int amountKg, double payPerKg, Date dateCreated, Date dateDue, String description, boolean isCompleted, int intermediaryId)
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

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.formattedDueDate = format.format(dateDue);

        this.intermediaryName = CSVController.selectIntermediaryRecord(intermediaryId).getFname() + " " + CSVController.selectIntermediaryRecord(intermediaryId).getSname();
    }

    public Job (Job job, int intermediaryId, Integer fisherId) {

        this.id = job.getId();
        this.fishType = job.getFishType();
        this.amountKg = job.getAmountKg();
        this.payPerKg = job.getPayPerKg();
        this.dateCreated = job.getDateCreated();
        this.dateDue = job.getDateDue();
        this.description = job.getDescription();
        this.isCompleted = job.isCompleted();
        this.intermediaryId = intermediaryId;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.formattedDueDate = format.format(this.dateDue);

        this.intermediaryName = CSVController.selectIntermediaryRecord(intermediaryId).getFname() + " " + CSVController.selectIntermediaryRecord(intermediaryId).getSname();
        this.fisherName = CSVController.selectFisherRecord(fisherId).getFname() + " " + CSVController.selectIntermediaryRecord(fisherId).getSname();

    }

    public Job (Job job, int intermediaryId) {

        this.id = job.getId();
        this.fishType = job.getFishType();
        this.amountKg = job.getAmountKg();
        this.payPerKg = job.getPayPerKg();
        this.dateCreated = job.getDateCreated();
        this.dateDue = job.getDateDue();
        this.description = job.getDescription();
        this.isCompleted = job.isCompleted();
        this.intermediaryId = intermediaryId;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.formattedDueDate = format.format(this.dateDue);

        this.intermediaryName = CSVController.selectIntermediaryRecord(intermediaryId).getFname() + " " + CSVController.selectIntermediaryRecord(intermediaryId).getSname();

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

    public String getFormattedDueDate()
    {
        return formattedDueDate;
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
