public class WorkOrder {
    private int id;
    private String description;
    private String senderName;
    private Status status;
    private static int nextId = 0;


    public WorkOrder() {
        this.id = getNextId();
    }



    public WorkOrder(String description, String senderName, Status status) {
        this.id = getNextId();
        this.description = description;
        this.senderName = senderName;
        this.status = status;
    }

    private static int getNextId(){
        return nextId ++;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getSenderName() {
        return senderName;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static void setNextId(int nextId) {
        WorkOrder.nextId = nextId;
    }
}
