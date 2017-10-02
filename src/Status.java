public enum Status {
    INITIAL,
    ASSIGNED ,
    IN_PROGRESS,
    DONE;

    public static Status[] getAllStatus(){
        return new Status[] { Status.INITIAL, Status.IN_PROGRESS, Status.ASSIGNED, Status.DONE};
    }


}
