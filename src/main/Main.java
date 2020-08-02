package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    static Scanner fScanner;
    
    public static void main(String args[]){
        System.out.println("Welcome to the memory management system! \n ");
        
        try{
            // Get file path, open it and scan it
            String sPath;
            System.out.println("Please write the file path you want to use");
            sPath = scanner.nextLine();
            File myFile = new File(sPath);
            fScanner = new Scanner(myFile);
            System.out.println("Thank you!");

            int option = 99;

            while(option != 1 && option != 2){
                System.out.println("Please choose the memory replacement policy you want to use...\n" +
                        "1.- Least Recently Used\n" +
                        "2.- First In First Out");
                option = scanner.nextInt();
            }

            Simulation simulation;

            if(option == 2){
                simulation = new Simulation(0); //Type FIFO
                System.out.println("Created simulation using FIFO replacement policy.");
            }else {
                simulation = new Simulation(1); //Type LRU
                System.out.println("Created simulation using LRU replacement policy.");
            }


            while(fScanner.hasNextLine()){
                
                // Get and split line
                String sAux = fScanner.nextLine();
                String[] input = sAux.split("\\W+");

                if("P".equals(input[0])){
                    //Assign X amount of bytes to process Z
                    System.out.println("Syntax: Process Size and PID (534 5838 assigns 534 bytes to PID 5838)");
                    int size = Integer.parseInt(input[1]);
                    int processID = Integer.parseInt(input[2]);
                    // Test Input
                    System.out.println("\nINPUT:\n" + input[0] + " " + size + " " + processID + "\n");
                    simulation.createNewProcess(processID, size);
                }

                if("A".equals(input[0])){
                    //Obtain physical address that corresponds to X virtual address of process Z
                    System.out.println("Syntax: Virtual Address, Process ID, M\n" +
                            "Where if M is 0, only the address is returned, if it is 1 it also modifies the address.");
                    //TODO add 0/1 option
                    int addr = Integer.parseInt(input[1]);
                    int processID = Integer.parseInt(input[2]);
                    String modify = input[3];
                    boolean modifyBoolean = modify.equals("1");
                    //Test Input
                    System.out.println("\nINPUT:\n" + input[0] + " " + addr + " " + processID + " " + modify + "\n");
                    
                    System.out.println("The physical address for virtual address " + addr + " from process " + processID +
                            " is " + simulation.returnPhysicalAddress(addr, processID, modifyBoolean));
                }

                if("L".equals(input[0])){
                    //Free page frames occupied by process X
                    int processID = Integer.parseInt(input[1]);
                    //Test Input
                    System.out.println("\nINPUT:\n" + input[0] + " " + processID + "\n");
                    
                    System.out.println("Deleted " + simulation.freeProcessFromMemory(processID) + " frames from PID " + processID);
                }
                
                if("C".equals(input[0])){
                    String[] sArr = sAux.split(" ", 2);
                    System.out.println(sArr[1]);
                }

                /*if("F".equals(input[0])){
                    //Test Input
                    System.out.println("\nINPUT:\n" + input[0] + "\n");
                    return;
                }*/
                
                if("E".equals(input[0])){
                    //Test Input
                    System.out.println("\nINPUT:\n" + input[0] + "\n");
                    return;
                }
                simulation.viewSimulation(); //this is debug output, not prod level
            }
        } catch (FileNotFoundException | NumberFormatException e){}
    }
}
