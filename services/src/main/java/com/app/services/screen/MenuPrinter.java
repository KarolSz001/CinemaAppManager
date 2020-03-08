package com.app.services.screen;

public class MenuPrinter {


    private final static StringBuilder sb = new StringBuilder();

    public static void startMenu() {
        sb.setLength(0);
        sb.append("----------------WELCOME IN CINEMA_APP_MANAGER--------------------\n");
        sb.append(" 0 - EXIT PROGRAM \n");
        sb.append(" 1 - LOGIN \n");
        sb.append(" 2 - SINGIN \n");
        sb.append("-----------------------------------------------------------------------------\n");
        System.out.println(sb.toString());
    }

    public static void printSaleTicketServiceMenu() {
        sb.setLength(0);
        sb.append("-----------------------------------------------------------------------------\n");
        sb.append(" SALE TICKETS SERVICE MENU \n");
        sb.append(" 0 - COME BACK TO MAIN MENU \n");
        sb.append(" 1 - SALE TICKETS OPERATION \n");
        sb.append(" 2 - SEND ALL SALE TICKETS CUSTOMER HISTORY \n");
        sb.append(" 3 - SEND SALE TICKETS CUSTOMER HISTORY WITH CHOOSE GENRE \n");
        sb.append(" 4 - SEND SALE TICKETS CUSTOMER HISTORY WITH CHOOSE MAX DURATION TIME \n");
        sb.append("-----------------------------------------------------------------------------\n");
        System.out.println(sb.toString());

    }

    public static void printCustomersMenu() {
        sb.setLength(0);
        sb.append("-----------------------------------------------------------------------------\n");
        sb.append(" CUSTOMER MENU \n");
        sb.append(" 0 - COME BACK TO MAIN MENU \n");
        sb.append(" 1 - SHOW ALL CUSTOMERS \n");
        sb.append(" 2 - SHOW CUSTOMER BY ID \n");
        sb.append(" 3 - REMOVE CUSTOMER BY ID \n");
        sb.append(" 4 - CREATE CUSTOMER \n");
        sb.append(" 5 - EDIT CUSTOMER BY ID \n");
        sb.append(" 6 - ADD RANDOM CUSTOMERS \n");
        sb.append(" 7 - SORT CUSTOMER BY SURNAME \n");
        sb.append(" 8 - SELECT CUSTOMER WITH LOYAL_CARDS \n");
        sb.append(" 9 - SORT CUSTOMER BY NUMBER OF WATCHED MOVIES \n");
        sb.append("----------------------------------------------------------------------------- \n");
        System.out.println(sb.toString());
    }

    public static void printMoviesMenu() {
        sb.setLength(0);
        sb.append("----------------------------------------------------------------------------- \n");
        sb.append(" MOVIE MENU ");
        sb.append(" 0 - COME BACK TO MAIN MENU \n");
        sb.append(" 1 - SHOW ALL MOVIES \n");
        sb.append(" 2 - SHOW MOVIES BY ID \n");
        sb.append(" 3 - REMOVE MOVIES BY ID \n");
        sb.append(" 4 - ADD NEW MOVIE \n");
        sb.append(" 5 - SORT MOVIES BY DURATION TIME \n");
        sb.append(" 6 - EDIT MOVIES BY ID \n");
        sb.append("----------------------------------------------------------------------------- \n");
        System.out.println(sb.toString());

    }

    public static void printStatisticMenu() {
        sb.setLength(0);
        sb.append("----------------------------------------------------------------------------- \n");
        sb.append(" STATISTIC MENU \n");
        sb.append(" 0 - COME BACK TO MAIN MENU \n");
        sb.append(" 1 - PRINT STAT FOR MOVIES PRICE -> MIN, MAX, AVR \n");
        sb.append(" 2 - PRINT STAT FOR MOVIES DURATION TIME -> MIN, MAX, AVR\n");
        sb.append(" 3 - PRINT STAT FOR MOVIES GENRE -> CREATE MAP WITH GENRE NAD NUMBER OF MOVIES\n");
        sb.append("----------------------------------------------------------------------------- \n");
        System.out.println(sb.toString());
    }

    public static void printExit() {
        System.out.println(" EXIT APPLICATION - GOODBYE \n");
    }

}
