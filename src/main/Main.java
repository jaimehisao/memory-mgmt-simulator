package main;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]){
        System.out.println("Welcome to the memory management system! \n ");

        int option = 99;

        while(option != 0 && option != 1){
            System.out.println("Please choose the memory replacement policy you want to use...\n" +
                    "1.- Least Recently Used\n" +
                    "2.- First In First Out");
                option = scanner.nextInt();
        }

        Simulation simulation;

        if(option == 0){
            simulation = new Simulation(0); //Type FIFO
            System.out.println("Created simulation using FIFO replacement policy.");
        }else {
            simulation = new Simulation(1); //Type LRU
            System.out.println("Created simulation using LRU replacement policy.");
        }


        while(true){
            System.out.println("Choose one of the following options...");
            char input = scanner.next().charAt(0);
            if(input == 'P'){
                //Assign X amount of bytes to process Z
                int size = scanner.nextInt();
                int processID = scanner.nextInt();
                simulation.createProcess(processID, size);
            }

            if(input == 'A'){
                //Obtain physical address that corresponds to X virtual address of process Z
            }

            if(input == 'L'){
                //Free page frames occupied by process X
            }

            if(input == 'F'){
                return;
            }
            simulation.viewSimulation();
        }



    }


}
