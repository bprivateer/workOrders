import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Creator {

    public static void main(String[] args) {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }

        public void createWorkOrder() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter a Description for a work order");
            String description = scanner.nextLine();

            System.out.println("Enter a sender name");
            String nameOfSender= scanner.nextLine();


            //create work orders
            WorkOrder order = new WorkOrder();
            order.setDescription(description);
            order.setSenderName(nameOfSender);
            order.setStatus(Status.INITIAL);
            writeAsJsonFile(order);

        }

        public void createWorkOrders(){
            Scanner scanner = new Scanner(System.in);
            boolean shouldContinue = true;
            while (shouldContinue) {
                createWorkOrder();
                System.out.println("Would you like to continue?");
                System.out.println("0: NO");
                System.out.println("Any other number: continue");
                int input = scanner.nextInt();
                shouldContinue = input != 0;
            }

        }





    public static void writeAsJsonFile(WorkOrder order) {
        try {
            String fileName = order.getId() + ".json";
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(fileName), order);
            String json = mapper.writeValueAsString(order);
        } catch (IOException e) {
            System.out.println("error" + e);
            e.printStackTrace();
        }

    }
}




