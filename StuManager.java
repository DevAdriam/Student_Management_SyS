import java.util.Scanner;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public  class StuManager {

    public static void main(String[] args) {

        int selection ;
        String  filePath =  "./StudentList/studentList.txt";
        StuManager HR  = new StuManager();

        System.out.println("======== Welcome To Student Manager Room ========");
        System.out.println("What can I do for you today?");

        System.out.println("1. Add Student");
        System.out.println("2. Edit Student");
        System.out.println("3. Delete Student");
        System.out.println("4. View Student Detail");
        System.out.println("5. Show Student List");
        System.out.println("6. Exit");

       
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Plase Choose Step : ");
        selection = scanner.nextInt();

        if(selection == 1){
            Student newStudent = new Student();

            System.out.print("==========Register Options============ \n 1.Phone\n 2.Email \n");
            System.out.print("Enter Register Type : ");
            int registerType = scanner.nextInt();
            if(registerType == 1){
                System.out.print("Enter Student Phone : ");
                newStudent.phone = scanner.next();
            }else if(registerType == 2){
                System.out.print("Enter Student Email : ");
                newStudent.email = scanner.next();
            }

            Boolean UserExist = HR.userExistCheck(newStudent.email, newStudent.phone, filePath,registerType);
            System.out.println(UserExist);

            if(UserExist){
                System.out.println("Email or phone already exist");
                scanner.close();
                return;
            }

            // add student
            System.out.print("Enter Student Name : ");
            newStudent.name = scanner.next();

            System.out.print("Enter Student age : ");
            newStudent.age = scanner.nextInt();

            System.out.print("Enter Student address : ");
            newStudent.address = scanner.next();

            newStudent.studentInfo();

            List<String> studentInfo = Arrays.asList(newStudent.id,newStudent.name, String.valueOf(newStudent.age), newStudent.address, newStudent.phone, newStudent.email);

            System.out.println(studentInfo);

            writeFile(filePath, studentInfo);

        }else if (selection == 2){
            System.out.print("Enter Student Name or Phone : ");
            String search = scanner.next();
            // edit 
            try {
                List<String> lines = Files.readAllLines(Paths.get(filePath));

                for(int i = 0 ; i < lines.size(); i++){
                    if(lines.get(i).equals(search)){
                        System.out.println("User found");
                        System.out.println("ID: " + lines.get(i));
                        System.out.println("Name: " + lines.get(i+1));
                        System.out.println("Age: " + lines.get(i+2));
                        System.out.println("City: " + lines.get(i+3));
                        System.out.println("Phone: " + lines.get(i+4));
                        System.out.println("Extra: " + lines.get(i+5));

                        System.out.println("Update Options\n");

                        System.out.println("1. Name\n");
                        System.out.println("2. Age\n");
                        System.out.println("3. Address\n");
                        System.out.println("4. Phone\n");
                        System.out.println("5. Email\n");

                        System.out.print("Enter Update Option : ");
                        int updateOption = scanner.nextInt();

                        if (updateOption == 1) {
                            System.out.print("Enter Student Name : ");
                            String newName = scanner.next();
                            lines.set(i+1, newName);
                            writeFile(filePath, lines);
                        }else if (updateOption == 2) {
                            System.out.print("Enter Student Age : ");
                            String newAge = scanner.next();
                            lines.set(i+2, newAge);
                            writeFile(filePath, lines);
                        }else if (updateOption == 3) {
                            System.out.print("Enter Student Address : ");
                            String newAddress = scanner.next();
                            lines.set(i+3, newAddress);
                            writeFile(filePath, lines);
                        }else if (updateOption == 4) {
                            System.out.print("Enter Student Phone : ");
                            String newPhone = scanner.next();
                            lines.set(i+4, newPhone);
                            writeFile(filePath, lines);
                        }else if (updateOption == 5) {
                            System.out.print("Enter Student Email : ");
                            String newEmail = scanner.next();
                            lines.set(i+5, newEmail);
                            writeFile(filePath, lines);
                        }

                    }
                }

             
            } catch (IOException e) {
                System.err.println("An IOException was caught!");
                e.printStackTrace();
            }
            
        }else if (selection == 3){
            // delete student
        }else if (selection == 4){
            System.out.print("Enter Student Name or Phone : ");
            String search = scanner.next();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath)); 
            int fieldSize = 6; // The number of fields in each record
            for (int i = 0; i < lines.size(); i += fieldSize) {
                if (lines.get(i+1).equals(search) || lines.get(i+4).equals(search)) {
                    System.out.println("User found:");
                    System.out.println("ID: " + lines.get(i));
                    System.out.println("Name: " + lines.get(i+1));
                    System.out.println("Age: " + lines.get(i+2));
                    System.out.println("City: " + lines.get(i+3));
                    System.out.println("Phone: " + lines.get(i+4));
                    System.out.println("Extra: " + lines.get(i+5));
                    scanner.close();
                    return;
                }
            }
            System.out.println("User not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

        }else if (selection == 5){

        System.out.print("This section need to authenticate, Please enter your password : ");
        String password = scanner.next();
        System.out.println("Authenticating ....");

        if(password.equals("helloworld")){
            System.out.println("Authenticated successfully!");
            System.out.println("Showing student list ....");
            readFile(filePath);
        }else{
            System.out.print(password);
            System.out.println("Authenticated failed!");    
        }
        
        }else if(selection == 6){
            scanner.close();
        }
    };


    static void writeFile(String filePath ,List<String> content){
          try {
            Path path = Paths.get(filePath);
            Files.write(path, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("File written successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void readFile(String path){
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            if(lines.size() == 0){
                System.out.println("No student found");
            }
            for (String line : lines) { 
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("An IOException was caught!");
            e.printStackTrace();
        }
    }

    Boolean userExistCheck(String email , String phone , String filePath,int registerType ){
        // check if user exist
        Boolean userExistBoolean = false;
      try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) { 
                if(registerType == 1){
                    if(line.trim().contains(phone.trim())){
                        userExistBoolean = true;
                        break;
                    } else {
                        userExistBoolean = false;
                    }
                }else if(registerType == 2){
                    userExistBoolean = line.contains(email);
                    break;
                }
            }

            System.out.println(userExistBoolean);
            return userExistBoolean;
        } catch (IOException e) {
            return false;
        }
    }

}

class Student {
    String id  = UUID.randomUUID().toString();
    String name;
    String phone;
    String email;
    String address;
    int age;


    public void studentInfo(){
        System.out.println("Student ID : " + id);
        System.out.println("Student Name : " + name);
        System.out.println("Student Age : " + age);  
        System.out.println("Student Address : " + address);
    }

}
