import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Processor {

    Map<Status, Set<WorkOrder>> workOrders = new HashMap<>();


    public Processor(){

    }

    public static void main(String args[]) {
        Processor processor = new Processor();
        processor.processWorkOrders();

    }


        public void processWorkOrders() {

        while(true) {
            readIt();
            moveIt();
            writeIt();
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        }

        private void moveIt() {
            // move work orders in map from one state to another
            Set<WorkOrder> inProgressOrders = workOrders.get(Status.IN_PROGRESS);
            if(inProgressOrders.size() > 0){
                WorkOrder firstInProgress = inProgressOrders.iterator().next();
                inProgressOrders.remove(firstInProgress);
                firstInProgress.setStatus(Status.DONE);
                Set<WorkOrder> workOrderSet = workOrders.get(Status.DONE);
                workOrderSet.add(firstInProgress);
                workOrders.put(Status.DONE, workOrderSet);
            }
//            System.out.println("After Assigned " + inProgressOrders);

            Set<WorkOrder> assignedOrders = workOrders.get(Status.ASSIGNED);
            if(assignedOrders.size() > 0){
                WorkOrder firstAssigned = assignedOrders.iterator().next();
                assignedOrders.remove(firstAssigned);
                firstAssigned.setStatus(Status.IN_PROGRESS);
                Set<WorkOrder> workOrderSet = workOrders.get(Status.IN_PROGRESS);
                workOrderSet.add(firstAssigned);
                workOrders.put(Status.IN_PROGRESS, workOrderSet);
            }
//            System.out.println("assigned orders" + assignedOrders);

            Set<WorkOrder> initialOrders = workOrders.get(Status.INITIAL);
            if(initialOrders.size() > 0){
                WorkOrder firstInitial = initialOrders.iterator().next();
                initialOrders.remove(firstInitial);
                firstInitial.setStatus(Status.ASSIGNED);
                Set<WorkOrder> workOrderSet = workOrders.get(Status.ASSIGNED);
                workOrderSet.add(firstInitial);
                workOrders.put(Status.ASSIGNED, workOrderSet);
            }
//            System.out.println("Initial stuff " + initialOrders);

        }

        private void writeIt(){
            for (Set<WorkOrder> orders : workOrders.values()) {
                System.out.println(orders.toString());
                for (WorkOrder order :orders) {
                    System.out.println("Order: " + order.getStatus());
                    Creator.writeAsJsonFile(order);
                }
            }
        }

        private void readIt() {
            // read the json files into WorkOrders and put in map
            File[] jsonFiles = findAllJsonFiles();
            String[] jsonStrings = extractJsonStrings(jsonFiles);
            WorkOrder[] workOrdersArray = jsonToObject(jsonStrings);
            workOrders.put(Status.IN_PROGRESS, new HashSet<>());
            workOrders.put(Status.INITIAL, new HashSet<>());
            workOrders.put(Status.ASSIGNED, new HashSet<>());
            workOrders.put(Status.DONE, new HashSet<>());

            for(WorkOrder order: workOrdersArray){
               Status currentStatus =  order.getStatus();
               Set<WorkOrder> currentSet = workOrders.get(currentStatus);
               currentSet.add(order);
               workOrders.put(currentStatus, currentSet);
            }
//            System.out.println(workOrders.get(Status.INITIAL).isEmpty());
        }


    public static File[] findAllJsonFiles(){
        ArrayList<File> jsonFiles = new ArrayList<>();
        File directory = new File(".");
        for(File f : directory.listFiles()){
            if(f.getName().endsWith(".json")){
                jsonFiles.add(f);
            }
        }
        return jsonFiles.toArray(new File[0]);
    }


    private static WorkOrder[] jsonToObject(String[] jsonStrings) {

        List<WorkOrder> workOrders = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for (String jsonString: jsonStrings) {
            try {
                WorkOrder wo = mapper.readValue(jsonString, WorkOrder.class);
                workOrders.add(wo);
            } catch (IOException e){
                System.out.println("Exception thrown");
            }

        }

        return workOrders.toArray(new WorkOrder[0]);

    }



    public static String[] extractJsonStrings (File[] jsonFiles) {

        List<String> jsonStrings = new ArrayList<>();
        for (File file : jsonFiles) {

            try {
                Scanner fileScanner = new Scanner(file);
                String fileContents = fileScanner.nextLine();
                jsonStrings.add(fileContents);
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return jsonStrings.toArray(new String[0]);
    }


}











