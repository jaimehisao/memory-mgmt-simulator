package main;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]){
        System.out.println("Welcome to the memory management system! \n ");

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


        while(true){
            System.out.println("Choose one of the following options...");
            char input = 'Z';
            while(input != 'P' && input != 'A' && input != 'L' && input != 'F'){
                System.out.println("P -> Assign a process to memory\n" +
                        "A -> Get a the physical address of a virtual address or modify it's value\n" +
                        "L -> Free up memory of a given process\n" +
                        "F -> Exit the simulation");
                input = scanner.next().charAt(0);
            }

            if(input == 'P'){
                //Assign X amount of bytes to process Z
                System.out.println("Syntax: Process Size and PID (534 5838 assigns 534 bytes to PID 5838)");
                int size = scanner.nextInt();
                int processID = scanner.nextInt();
                simulation.createNewProcess(processID, size);
            }

            if(input == 'A'){
                //Obtain physical address that corresponds to X virtual address of process Z
                System.out.println("Syntax: Virtual Address, Process ID, M\n" +
                        "Where if M is 0, only the address is returned, if it is 1 it also modifies the address.");
                //TODO add 0/1 option
                int addr = scanner.nextInt();
                int processID = scanner.nextInt();
                String modify = scanner.next();
                boolean modifyBoolean = modify.equals("1");
                System.out.println("The physical address for virtual address " + addr + " from process " + processID +
                        " is " + simulation.returnPhysicalAddress(addr, processID, modifyBoolean));
            }

            if(input == 'L'){
                //Free page frames occupied by process X
                int processID = scanner.nextInt();
                System.out.println("Deleted " + simulation.freeProcessFromMemory(processID) + " frames from PID " + processID);
            }

            if(input == 'F'){
                return;
            }
            simulation.viewSimulation(); //this is debug output, not prod level
        }



    }


}
