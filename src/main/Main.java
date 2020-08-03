package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);        // Scanner for user input
    static Scanner fScanner;                                // Scanner for files
    static Simulation simulation;                           // Simulation object
    
    public static void main(String args[]){
        
        LinkedList<Process> PrList = new LinkedList<>();    // ArrayList to have registers of every process
        
        //Print welcome message
        System.out.println("Welcome to the memory management system! \n ");
        
        try{
            // Get file path, open it and scan it
            String sPath;
            System.out.println("Please write the file path you want to use");
            sPath = scanner.nextLine();
            File myFile = new File(sPath);
            fScanner = new Scanner(myFile);
            System.out.println("Thank you!");
            
            //Ask for memory replacement policy
            int option = 99;
            while(option != 1 && option != 2){
                System.out.println("Please choose the memory replacement policy you want to use...\n" +
                        "1.- Least Recently Used\n" +
                        "2.- First In First Out");
                option = scanner.nextInt();
            }
           
            //Configure simulation
            if(option == 2){
                simulation = new Simulation(0); //Type FIFO
                System.out.println("Created simulation using FIFO replacement policy.");
            }else {
                simulation = new Simulation(1); //Type LRU
                System.out.println("Created simulation using LRU replacement policy.");
            }
            
            //Attend petitions from file
            while(fScanner.hasNextLine()){
                
                // Get and split line
                String sAux = fScanner.nextLine();
                String[] input = sAux.split("\\W+");
                
                if(null != input[0])switch (input[0]) {
                    case "P":{
                        //Assign X amount of bytes to process Z
                        System.out.println("Syntax: Process Size and PID (534 5838 assigns 534 bytes to PID 5838)");
                        int size = Integer.parseInt(input[1]);
                        int processID = Integer.parseInt(input[2]);
                        //Display Input
                        System.out.println("\nPetition: " + input[0] + "\nBytes: " + size + "\nProcess ID: " + processID + "\n");
                        //Attend petition
                        simulation.createNewProcess(processID, size, PrList);
                        break;
                    }
                    case "A":{
                        //Obtain physical address that corresponds to X virtual address of process Z
                        System.out.println("Syntax: Virtual Address, Process ID, M\n" +
                                "Where if M is 0, only the address is returned, if it is 1 it also modifies the address.");
                        //TODO add 0/1 option
                        int addr = Integer.parseInt(input[1]);
                        int processID = Integer.parseInt(input[2]);
                        String modify = input[3];
                        boolean modifyBoolean = modify.equals("1");
                        //Display Input
                        System.out.println("\nPetition: " + input[0] + "\nVirtual Address: " + addr + "\nProcess ID: " + processID + "\nModify: " + modify + "\n");
                        //Attend petition
                        System.out.println("The physical address for virtual address " + addr + " from process " + processID +
                                " is " + simulation.returnPhysicalAddress(addr, processID, modifyBoolean));
                        break;
                    }
                    case "L":{
                        //Free page frames occupied by process X
                        int processID = Integer.parseInt(input[1]);
                        //Display Input
                        System.out.println("\nPetition: " + input[0] + "\nProcess ID: " + processID + "\n");
                        //Attend petition
                        System.out.println("Deleted " + simulation.freeProcessFromMemory(processID) + " frames from PID " + processID);
                        break;
                    }
                    case "C":
                        //Split petition ID from comment
                        String[] sArr = sAux.split(" ", 2);
                        //Display Input and comment
                        System.out.println("\nPetition: " + input[0] + "\nComment: " + sArr[1] + "\n");
                        break;
                    case "F":
                        //Display Input
                        System.out.println("\nPetition: " + input[0] + "\nLoading stats...\n");
                        int iAc = 0;   //Int to acumulate every turnaround
                        //Get stats from every proces
                        for(Process p : PrList){
                            System.out.println("Process: " + p.getProcessId() + 
                                    "\tTurnAround = " + (simulation.getSystemTimestamp() - 
                                    p.getTimecreated()));
                            //Acumulate turnaround
                            iAc += simulation.getSystemTimestamp() - p.getTimecreated();
                        }
                        //Display general simulation stats
                        System.out.println("\nTurnAround Prom = " + 
                                iAc / PrList.size() + "\nPage Faults = 0" + 
                                "\nNumber of Swaps In made = " + simulation.getSwapsIn() + 
                                "\nNumber of Swaps Out made = " + simulation.getSwapsOut() + "\n");
                        break;
                    case "E":
                        // Display Input
                        System.out.println("\nPetition: " + input[0] + "\nBye bye!");           
                        return;
                    default:
                        break;
                }
                // simulation.viewSimulation(); //this is debug output, not prod level
            }
        } catch (FileNotFoundException | NumberFormatException e){
            System.out.println("File with that name does not exist!");
        }
    }    

    public static Simulation getSimulation() {
        return simulation;
    }
}
