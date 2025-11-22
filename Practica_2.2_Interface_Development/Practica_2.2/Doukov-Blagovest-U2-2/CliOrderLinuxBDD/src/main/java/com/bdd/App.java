package com.bdd;

/**
 * 
 * 
 * @author Tu Nombre Completo
 * @version 1.0
 *
 * 
 */

import java.io.InputStream;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.bdd.Order;

public class App {
    // App info
    static final String APP = "DanDelilght Restaurant";
    static final String VERSION = "1.0";
    static final String DEVELOPER = "Blagovest Doukov Dimitrova";

    // ANSI codes
    static final String ESC = "\u001B[";
    static final String RESET = ESC + "0m";
    static final String BOLD = ESC + "1m";
    static final String BLINK = ESC + "5m";                 // parpadeo
    static final String FG_GREEN_LIGHT = ESC + "38;5;120m"; // light green for typography
    static final String FG_ORANGE = ESC + "38;5;215m";      // accent
    static final String FG_RED = ESC + "38;5;196m";         // errors

    // Email validation
    static final Pattern EMAIL = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE);

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        clearScreen();
        printStartup();
        waitForKeyOrTimeout(10);
        clearScreen();
        mainLoop();
    }

    // ---------- Main menu ----------
    static void mainLoop() {
        Order order = null;
        while (true) {
            clearScreen();
            printHeader();
            println("1) Take full order");
            println("2) Get full order");
            println("3) Modify order");
            println("A) About");
            println("X) Salir");
            print("Choose one option: ");
            String opt = sc.nextLine().trim();

            if (opt.equalsIgnoreCase("1")) {
                order = takeFullOrder();
                pressEnterToContinue();
            } else if (opt.equalsIgnoreCase("2")) {
                if (order == null) printError("There's no order. Use '1' to make a order.");
                else printOrder(order);
                pressEnterToContinue();
            } else if (opt.equalsIgnoreCase("3")) {
                if (order == null) printError("There's no order to modify, please create one first.");
                else setAnyField(order);
                pressEnterToContinue();
            } else if (opt.equalsIgnoreCase("A")) {
                printAbout();
                pressEnterToContinue();
            } else if (opt.equalsIgnoreCase("X")) {
                farewell();
            } else {
                printError("Invalid option. Try again.");
                sleep(800);
            }
        }
    }

    // ---------- Startup & utilities ----------
    static void printStartup() {
        System.out.println(BOLD + BLINK + FG_GREEN_LIGHT + "Welcome to " + APP + RESET);
        println(FG_ORANGE + "Developer: " + RESET + DEVELOPER);
        println(FG_ORANGE + "Operative System: " + RESET + System.getProperty("os.name"));
        println(FG_ORANGE + "Time and date: " + RESET + now());
        println(FG_ORANGE + "Name of the pc: " + RESET + hostName());
        println(FG_ORANGE + "User: " + RESET + System.getProperty("user.name"));
        println("");
        // Msg with blinking
        System.out.println(BLINK + FG_GREEN_LIGHT + "Press any key or wait 10 seconds to continue..." + RESET);
    }

    static void printHeader() {
        println(BOLD + FG_GREEN_LIGHT + "== " + APP + " v" + VERSION + " ==" + RESET);
    }

    static void printAbout() {
        clearScreen();
        printHeader();
        println("");
        println(FG_ORANGE + "Developer: " + RESET + DEVELOPER);
        println(FG_ORANGE + "Aplication: " + RESET + APP);
        println(FG_ORANGE + "Version: " + RESET + VERSION);
    }

    static void farewell() {
        // Despedida con parpadeo
        clearScreen();
        System.out.println(BOLD + BLINK + FG_GREEN_LIGHT + "Thank you for using " + APP + "!" + RESET);
        println(FG_ORANGE + "¡Have a nice day!" + RESET);
        sleep(1200);
        clearScreen();
        System.exit(0);
    }

    /**
     * Espera una tecla o timeout.
     * Intenta detectar entrada por System.in.available().
     */
    static void waitForKeyOrTimeout(int seconds) {
        try {
            InputStream in = System.in;
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < seconds * 1000) {
                if (in.available() > 0) { in.read(); return; }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            sleep(seconds * 1000);
        }
    }

    // Limpia la pantalla
    static void clearScreen() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("windows")) {
                // Intenta cls en Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Intenta clear en linux
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Secuencia ANSI
            System.out.print(ESC + "2J" + ESC + "H");
            System.out.flush();
        }
    }

    static void println(String s) { System.out.println(FG_GREEN_LIGHT + s + RESET); }
    static void print(String s) { System.out.print(FG_GREEN_LIGHT + s + RESET); }
    static void printError(String s) { System.out.println(FG_RED + "[ERROR] " + RESET + s); }
    static void printSuccess(String s) { System.out.println(FG_GREEN_LIGHT + "[OK] " + RESET + s); }
    static void pressEnterToContinue() { System.out.println(); System.out.print(FG_GREEN_LIGHT + "Press ENTER to continue..." + RESET); sc.nextLine(); }
    static void sleep(int ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }

    static String now() { return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); }
    static String hostName() { try { return InetAddress.getLocalHost().getHostName(); } catch (Exception e) { return "unknown"; } }

    // ---------- Order flows (strict fields only) ----------
    static Order takeFullOrder() {
        Order o = new Order();
        o.dateTime = LocalDateTime.now();

        // Take in restaurant or takeaway (required)
        while (true) {
            print("To eat in the restaurant or to takeaway? (R/T): ");
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("R")) { o.takeIn = true; break; }
            if (s.equals("T")) { o.takeIn = false; break; }
            printError("Invalid response. Use R to restaurant or T to takeaway.");
        }

        // Table number (required, 0 if takeaway)
        while (true) {
            print("Number of table (0 if takeaway): ");
            String s = sc.nextLine().trim();
            try {
                int t = Integer.parseInt(s);
                if (t < 0) { printError("The number of the table can't be negative."); continue; }
                if (!o.takeIn && t != 0) { printError("Takeaway have to be table number 0."); continue; }
                if (o.takeIn && t == 0) { printError("To eat in the restaurant have to be a number > 0."); continue; }
                o.tableNumber = t; break;
            } catch (NumberFormatException e) { printError("Type a full number for the table."); }
        }

        // Full customer name (required)
        while (true) {
            print("Client's full name: ");
            String n = sc.nextLine().trim();
            if (n.isEmpty()) printError("The name is obligatory."); else { o.customerName = n; break; }
        }

        // Email (check, blank permitted)
        while (true) {
            print("Email (ENTER if you don't want to give a email): ");
            String e = sc.nextLine().trim();
            if (e.isEmpty()) { o.email = ""; break; }
            if (EMAIL.matcher(e).matches()) { o.email = e; break; }
            printError("Invalid email. Please try again or leave it empty.");
        }

        // Customer comments (optional)
        print("Comentaries of the customer(optional): ");
        o.comments = sc.nextLine().trim();

        // Grand Total (required)
        while (true) {
            print("Total (€): ");
            String s = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(s);
                if (v < 0) { printError("The total can't be empty."); continue; }
                o.grandTotal = round(v); break;
            } catch (NumberFormatException ex) { printError("Give a valid number for the total."); }
        }

        // Payment method (required)
        while (true) {
            print("Payment method - Cash (C) o Card (T): ");
            String p = sc.nextLine().trim().toUpperCase();
            if (p.equals("C")) { o.paymentMethod = "Cash"; break; }
            if (p.equals("T")) { o.paymentMethod = "Card"; break; }
            printError("Invalid entry. Use C o T.");
        }

        // Tipping (none,5,10,15,20) default none
        while (true) {
            print("Tip (0,5,10,15,20) [0]: ");
            String s = sc.nextLine().trim(); if (s.isEmpty()) s = "0";
            try {
                int tp = Integer.parseInt(s);
                if (tp==0||tp==5||tp==10||tp==15||tp==20) { o.tipping = tp; o.tipAmount = round(o.grandTotal * tp / 100.0); break; }
                printError("Choose between 0(please not this one :( )),5,10,15 o 20.");
            } catch (NumberFormatException ex) { printError("Invalid value for the tip."); }
        }

        // Request invoice (yes/no, no by default)
        while (true) {
            print("Do you want invoice? (Y/N) [N]: ");
            String s = sc.nextLine().trim().toUpperCase();
            if (s.isEmpty() || s.equals("N")) { o.requestInvoice = false; break; }
            if (s.equals("Y")) { o.requestInvoice = true; break; }
            printError("Invalid entry. Use Y o N.");
        }

        o.totalWithTip = round(o.grandTotal + o.tipAmount);
        printSuccess("Order taked correctly.");
        return o;
    }

    static void printOrder(Order o) {
        clearScreen();
        printHeader();
        println("");
        println("Time and date: " + o.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        println("Type: " + (o.takeIn?"In restaurant":"To take away"));
        println("Table: " + o.tableNumber);
        println("Client: " + o.customerName);
        println("Email: " + (o.email.isEmpty()?"(empty)":o.email));
        println("Comentaries: " + (o.comments.isEmpty()?"(none)":o.comments));
        println(String.format("Total: € %.2f", o.grandTotal));
        println("Tip: " + o.tipping + "% (" + String.format("€ %.2f", o.tipAmount) + ")");
        println(String.format("Total with tip: € %.2f", o.totalWithTip));
        println("Payment: " + o.paymentMethod);
        println("Invoice wanted: " + (o.requestInvoice?"Yes":"No"));
    }

    static void setAnyField(Order o) {
        while (true) {
            clearScreen(); printHeader(); println("");
            println("1) Type (actual: " + (o.takeIn?"Restaurant":"Takeaway") + ")");
            println("2) Table (actual: " + o.tableNumber + ")");
            println("3) Name (actual: " + o.customerName + ")");
            println("4) Email (actual: " + (o.email.isEmpty()?"(Empty)":o.email) + ")");
            println("5) Comentaries (actual: " + (o.comments.isEmpty()?"(Empty)":o.comments) + ")");
            println("6) Total (actual: € " + String.format("%.2f", o.grandTotal) + ")");
            println("7) Payment (actual: " + o.paymentMethod + ")");
            println("8) Tip (actual: " + o.tipping + "% )");
            println("9) Invoice (actual: " + (o.requestInvoice?"Yes":"No") + ")");
            println("0) Back");
            print("Choose one to modify: ");
            String c = sc.nextLine().trim();
            if (c.equals("0")) break;
            switch (c) {
                case "1":
                    print("R to restaurant, T to takeaway: "); String t=sc.nextLine().trim().toUpperCase(); if (t.equals("R")) { o.takeIn=true; } else if (t.equals("T")) { o.takeIn=false; o.tableNumber=0; } else printError("Entrada inválida."); break;
                case "2":
                    print("New number of table: "); String m=sc.nextLine().trim(); try { int mm=Integer.parseInt(m); if (mm<0) printError("No puede ser negativo."); else o.tableNumber=mm; } catch (Exception e) { printError("Número inválido."); } break;
                case "3":
                    print("New full name: "); String nn=sc.nextLine().trim(); if (!nn.isEmpty()) o.customerName=nn; else printError("El nombre no puede quedar vacío."); break;
                case "4":
                    print("New email (empty to clean): "); String ne=sc.nextLine().trim(); if (ne.isEmpty()) o.email=""; else if (EMAIL.matcher(ne).matches()) o.email=ne; else printError("Email inválido."); break;
                case "5":
                    print("New comentaries: "); o.comments=sc.nextLine().trim(); break;
                case "6":
                    print("New total (€): "); String nt=sc.nextLine().trim(); try { double vv=Double.parseDouble(nt); if (vv<0) printError("No puede ser negativo."); else { o.grandTotal=round(vv); o.tipAmount=round(o.grandTotal*o.tipping/100.0); o.totalWithTip=round(o.grandTotal+o.tipAmount);} } catch (Exception e) { printError("Valor inválido."); } break;
                case "7":
                    print("C to Cash, T para Card: "); String pm=sc.nextLine().trim().toUpperCase(); if (pm.equals("C")) o.paymentMethod="Efectivo"; else if (pm.equals("T")) o.paymentMethod="Tarjeta"; else printError("Entrada inválida."); break;
                case "8":
                    print("Tip (0,5,10,15,20): "); String tp=sc.nextLine().trim(); try { int tpp=Integer.parseInt(tp); if (tpp==0||tpp==5||tpp==10||tpp==15||tpp==20) { o.tipping=tpp; o.tipAmount=round(o.grandTotal*tpp/100.0); o.totalWithTip=round(o.grandTotal+o.tipAmount);} else printError("Opción inválida."); } catch (Exception e) { printError("Valor inválido."); } break;
                case "9":
                    print("Invoice Y/N: "); String fi=sc.nextLine().trim().toUpperCase(); if (fi.equals("S")) o.requestInvoice=true; else if (fi.equals("N")||fi.isEmpty()) o.requestInvoice=false; else printError("Entrada inválida."); break;
                default: printError("Invalid option.");
            }
        }
        printSuccess("Changes saved.");
    }

    // ---------- Helpers ----------
    static double round(double v) { return Math.round(v*100.0)/100.0; }

}
