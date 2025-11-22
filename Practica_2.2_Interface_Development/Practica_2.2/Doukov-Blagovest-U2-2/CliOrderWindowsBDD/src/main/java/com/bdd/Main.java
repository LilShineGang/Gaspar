package com.bdd;

/**
 *
 * @author Blagovest Doukov Dimitrova
 * @version 1.0
 */
import java.io.InputStream;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    // App info
    static final String APP = "DanDelilght Restaurant";
    static final String VERSION = "1.0";
    static final String DEVELOPER = "Blagovest Doukov Dimitrova";

    // ANSI codes - Funcionan en Windows Terminal, PowerShell y CMD moderno
    static final String ESC = "\u001B[";
    static final String RESET = ESC + "0m";
    static final String BOLD = ESC + "1m";
    static final String BLINK = ESC + "5m";                 // parpadeo
    static final String FG_GREEN_LIGHT = ESC + "38;5;120m"; // verde claro para tipografía
    static final String FG_ORANGE = ESC + "38;5;215m";      // acento
    static final String FG_RED = ESC + "38;5;196m";         // errores

    // Email validation
    static final Pattern EMAIL = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE);

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Habilitar códigos ANSI en Windows (para CMD antiguo)
        enableWindowsAnsiSupport();

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
            println("1) Tomar pedido completo");
            println("2) Ver pedido completo");
            println("3) Modificar pedido");
            println("A) Acerca de");
            println("X) Salir");
            print("Elige una opción: ");
            String opt = sc.nextLine().trim();

            if (opt.equalsIgnoreCase("1")) {
                order = takeFullOrder();
                pressEnterToContinue();
            } else if (opt.equalsIgnoreCase("2")) {
                if (order == null) {
                    printError("No hay pedido. Usa '1' para crear un pedido."); 
                }else {
                    printOrder(order);
                }
                pressEnterToContinue();
            } else if (opt.equalsIgnoreCase("3")) {
                if (order == null) {
                    printError("No hay pedido para modificar, por favor crea uno primero."); 
                }else {
                    setAnyField(order);
                }
                pressEnterToContinue();
            } else if (opt.equalsIgnoreCase("A")) {
                printAbout();
                pressEnterToContinue();
            } else if (opt.equalsIgnoreCase("X")) {
                farewell();
            } else {
                printError("Opción inválida. Intenta de nuevo.");
                sleep(800);
            }
        }
    }

    // ---------- Startup & utilities ----------
    static void printStartup() {
        System.out.println(BOLD + BLINK + FG_GREEN_LIGHT + "Bienvenido a " + APP + RESET);
        println(FG_ORANGE + "Desarrollador: " + RESET + DEVELOPER);
        println(FG_ORANGE + "Sistema Operativo: " + RESET + System.getProperty("os.name"));
        println(FG_ORANGE + "Fecha y hora: " + RESET + now());
        println(FG_ORANGE + "Nombre del PC: " + RESET + hostName());
        println(FG_ORANGE + "Usuario: " + RESET + System.getProperty("user.name"));
        println("");
        // Mensaje con parpadeo
        System.out.println(BLINK + FG_GREEN_LIGHT + "Presiona cualquier tecla o espera 10 segundos para continuar..." + RESET);
    }

    static void printHeader() {
        println(BOLD + FG_GREEN_LIGHT + "== " + APP + " v" + VERSION + " ==" + RESET);
    }

    static void printAbout() {
        clearScreen();
        printHeader();
        println("");
        println(FG_ORANGE + "Desarrollador: " + RESET + DEVELOPER);
        println(FG_ORANGE + "Aplicación: " + RESET + APP);
        println(FG_ORANGE + "Versión: " + RESET + VERSION);
    }

    static void farewell() {
        // Despedida con parpadeo
        clearScreen();
        System.out.println(BOLD + BLINK + FG_GREEN_LIGHT + "¡Gracias por usar " + APP + "!" + RESET);
        println(FG_ORANGE + "¡Que tengas un buen día!" + RESET);
        sleep(1200);
        clearScreen();
        System.exit(0);
    }

    /**
     * Espera una tecla o timeout. Intenta detectar entrada por
     * System.in.available().
     */
    static void waitForKeyOrTimeout(int seconds) {
        try {
            InputStream in = System.in;
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < seconds * 1000) {
                if (in.available() > 0) {
                    in.read();
                    return;
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            sleep(seconds * 1000);
        }
    }

    /**
     * Habilita soporte ANSI en Windows (para CMD antiguo). En Windows 10+ con
     * Windows Terminal o PowerShell, los códigos ANSI funcionan nativamente.
     */
    static void enableWindowsAnsiSupport() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            try {
                // Intenta habilitar el modo de procesamiento de secuencias virtuales
                new ProcessBuilder("cmd", "/c", "echo", "").inheritIO().start().waitFor();
            } catch (Exception e) {
                // Si falla, los códigos ANSI aún pueden funcionar en terminales modernos
            }
        }
    }

    /**
     * Limpia la pantalla usando el comando apropiado para Windows. Funciona en
     * CMD, PowerShell y Windows Terminal.
     */
    static void clearScreen() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("windows")) {
                // Usa cls en Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Usa clear en Linux/Mac (por compatibilidad)
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback: Secuencia ANSI para limpiar pantalla
            System.out.print(ESC + "2J" + ESC + "H");
            System.out.flush();
        }
    }

    static void println(String s) {
        System.out.println(FG_GREEN_LIGHT + s + RESET);
    }

    static void print(String s) {
        System.out.print(FG_GREEN_LIGHT + s + RESET);
    }

    static void printError(String s) {
        System.out.println(FG_RED + "[ERROR] " + RESET + s);
    }

    static void printSuccess(String s) {
        System.out.println(FG_GREEN_LIGHT + "[OK] " + RESET + s);
    }

    static void pressEnterToContinue() {
        System.out.println();
        System.out.print(FG_GREEN_LIGHT + "Presiona ENTER para continuar..." + RESET);
        sc.nextLine();
    }

    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    static String hostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "desconocido";
        }
    }

    // ---------- Order flows (strict fields only) ----------
    static Order takeFullOrder() {
        Order o = new Order();
        o.dateTime = LocalDateTime.now();

        // Comer en restaurante o para llevar (requerido)
        while (true) {
            print("¿Para comer en el restaurante o para llevar? (R/L): ");
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("R")) {
                o.takeIn = true;
                break;
            }
            if (s.equals("L")) {
                o.takeIn = false;
                break;
            }
            printError("Respuesta inválida. Usa R para restaurante o L para llevar.");
        }

        // Número de mesa (requerido, 0 si es para llevar)
        while (true) {
            print("Número de mesa (0 si es para llevar): ");
            String s = sc.nextLine().trim();
            try {
                int t = Integer.parseInt(s);
                if (t < 0) {
                    printError("El número de mesa no puede ser negativo.");
                    continue;
                }
                if (!o.takeIn && t != 0) {
                    printError("Para llevar debe ser mesa número 0.");
                    continue;
                }
                if (o.takeIn && t == 0) {
                    printError("Para comer en el restaurante debe ser un número > 0.");
                    continue;
                }
                o.tableNumber = t;
                break;
            } catch (NumberFormatException e) {
                printError("Escribe un número entero para la mesa.");
            }
        }

        // Nombre completo del cliente (requerido)
        while (true) {
            print("Nombre completo del cliente: ");
            String n = sc.nextLine().trim();
            if (n.isEmpty()) {
                printError("El nombre es obligatorio.");
            } else {
                o.customerName = n;
                break;
            }
        }

        // Email (validación, puede estar en blanco)
        while (true) {
            print("Email (ENTER si no desea proporcionar email): ");
            String e = sc.nextLine().trim();
            if (e.isEmpty()) {
                o.email = "";
                break;
            }
            if (EMAIL.matcher(e).matches()) {
                o.email = e;
                break;
            }
            printError("Email inválido. Por favor intenta de nuevo o déjalo vacío.");
        }

        // Comentarios del cliente (opcional)
        print("Comentarios del cliente (opcional): ");
        o.comments = sc.nextLine().trim();

        // Total general (requerido)
        while (true) {
            print("Total (€): ");
            String s = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(s);
                if (v < 0) {
                    printError("El total no puede ser negativo.");
                    continue;
                }
                o.grandTotal = round(v);
                break;
            } catch (NumberFormatException ex) {
                printError("Proporciona un número válido para el total.");
            }
        }

        // Método de pago (requerido)
        while (true) {
            print("Método de pago - Efectivo (E) o Tarjeta (T): ");
            String p = sc.nextLine().trim().toUpperCase();
            if (p.equals("E")) {
                o.paymentMethod = "Efectivo";
                break;
            }
            if (p.equals("T")) {
                o.paymentMethod = "Tarjeta";
                break;
            }
            printError("Entrada inválida. Usa E o T.");
        }

        // Propina (ninguna,5,10,15,20) por defecto ninguna
        while (true) {
            print("Propina (0,5,10,15,20) [0]: ");
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                s = "0";
            }
            try {
                int tp = Integer.parseInt(s);
                if (tp == 0 || tp == 5 || tp == 10 || tp == 15 || tp == 20) {
                    o.tipping = tp;
                    o.tipAmount = round(o.grandTotal * tp / 100.0);
                    break;
                }
                printError("Elige entre 0 (por favor no este :( ), 5, 10, 15 o 20.");
            } catch (NumberFormatException ex) {
                printError("Valor inválido para la propina.");
            }
        }

        // Solicitar factura (sí/no, no por defecto)
        while (true) {
            print("¿Desea factura? (S/N) [N]: ");
            String s = sc.nextLine().trim().toUpperCase();
            if (s.isEmpty() || s.equals("N")) {
                o.requestInvoice = false;
                break;
            }
            if (s.equals("S")) {
                o.requestInvoice = true;
                break;
            }
            printError("Entrada inválida. Usa S o N.");
        }

        o.totalWithTip = round(o.grandTotal + o.tipAmount);
        printSuccess("Pedido tomado correctamente.");
        return o;
    }

    static void printOrder(Order o) {
        clearScreen();
        printHeader();
        println("");
        println("Fecha y hora: " + o.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        println("Tipo: " + (o.takeIn ? "En restaurante" : "Para llevar"));
        println("Mesa: " + o.tableNumber);
        println("Cliente: " + o.customerName);
        println("Email: " + (o.email.isEmpty() ? "(vacío)" : o.email));
        println("Comentarios: " + (o.comments.isEmpty() ? "(ninguno)" : o.comments));
        println(String.format("Total: € %.2f", o.grandTotal));
        println("Propina: " + o.tipping + "% (" + String.format("€ %.2f", o.tipAmount) + ")");
        println(String.format("Total con propina: € %.2f", o.totalWithTip));
        println("Pago: " + o.paymentMethod);
        println("Factura solicitada: " + (o.requestInvoice ? "Sí" : "No"));
    }

    static void setAnyField(Order o) {
        while (true) {
            clearScreen();
            printHeader();
            println("");
            println("1) Tipo (actual: " + (o.takeIn ? "Restaurante" : "Para llevar") + ")");
            println("2) Mesa (actual: " + o.tableNumber + ")");
            println("3) Nombre (actual: " + o.customerName + ")");
            println("4) Email (actual: " + (o.email.isEmpty() ? "(Vacío)" : o.email) + ")");
            println("5) Comentarios (actual: " + (o.comments.isEmpty() ? "(Vacío)" : o.comments) + ")");
            println("6) Total (actual: € " + String.format("%.2f", o.grandTotal) + ")");
            println("7) Pago (actual: " + o.paymentMethod + ")");
            println("8) Propina (actual: " + o.tipping + "% )");
            println("9) Factura (actual: " + (o.requestInvoice ? "Sí" : "No") + ")");
            println("0) Volver");
            print("Elige uno para modificar: ");
            String c = sc.nextLine().trim();
            if (c.equals("0")) {
                break;
            }
            switch (c) {
                case "1":
                    print("R para restaurante, L para llevar: ");
                    String t = sc.nextLine().trim().toUpperCase();
                    if (t.equals("R")) {
                        o.takeIn = true;
                    } else if (t.equals("L")) {
                        o.takeIn = false;
                        o.tableNumber = 0;
                    } else {
                        printError("Entrada inválida.");
                    
                    }break;
                case "2":
                    print("Nuevo número de mesa: ");
                    String m = sc.nextLine().trim();
                    try {
                        int mm = Integer.parseInt(m);
                        if (mm < 0) {
                            printError("No puede ser negativo.");
                        } else {
                            o.tableNumber = mm;
                    
                        }} catch (Exception e) {
                        printError("Número inválido.");
                    }
                    break;
                case "3":
                    print("Nuevo nombre completo: ");
                    String nn = sc.nextLine().trim();
                    if (!nn.isEmpty()) {
                        o.customerName = nn;
                    } else {
                        printError("El nombre no puede quedar vacío.");
                    
                    }break;
                case "4":
                    print("Nuevo email (vacío para limpiar): ");
                    String ne = sc.nextLine().trim();
                    if (ne.isEmpty()) {
                        o.email = "";
                    } else if (EMAIL.matcher(ne).matches()) {
                        o.email = ne;
                    } else {
                        printError("Email inválido.");
                    
                    }break;
                case "5":
                    print("Nuevos comentarios: ");
                    o.comments = sc.nextLine().trim();
                    break;
                case "6":
                    print("Nuevo total (€): ");
                    String nt = sc.nextLine().trim();
                    try {
                        double vv = Double.parseDouble(nt);
                        if (vv < 0) {
                            printError("No puede ser negativo.");
                        } else {
                            o.grandTotal = round(vv);
                            o.tipAmount = round(o.grandTotal * o.tipping / 100.0);
                            o.totalWithTip = round(o.grandTotal + o.tipAmount);
                        }
                    } catch (Exception e) {
                        printError("Valor inválido.");
                    }
                    break;
                case "7":
                    print("E para Efectivo, T para Tarjeta: ");
                    String pm = sc.nextLine().trim().toUpperCase();
                    if (pm.equals("E")) {
                        o.paymentMethod = "Efectivo";
                    } else if (pm.equals("T")) {
                        o.paymentMethod = "Tarjeta";
                    } else {
                        printError("Entrada inválida.");
                    
                    }break;
                case "8":
                    print("Propina (0,5,10,15,20): ");
                    String tp = sc.nextLine().trim();
                    try {
                        int tpp = Integer.parseInt(tp);
                        if (tpp == 0 || tpp == 5 || tpp == 10 || tpp == 15 || tpp == 20) {
                            o.tipping = tpp;
                            o.tipAmount = round(o.grandTotal * tpp / 100.0);
                            o.totalWithTip = round(o.grandTotal + o.tipAmount);
                        } else {
                            printError("Opción inválida.");
                    
                        }} catch (Exception e) {
                        printError("Valor inválido.");
                    }
                    break;
                case "9":
                    print("Factura S/N: ");
                    String fi = sc.nextLine().trim().toUpperCase();
                    if (fi.equals("S")) {
                        o.requestInvoice = true;
                    } else if (fi.equals("N") || fi.isEmpty()) {
                        o.requestInvoice = false;
                    } else {
                        printError("Entrada inválida.");
                    
                    }break;
                default:
                    printError("Opción inválida.");
            }
        }
        printSuccess("Cambios guardados.");
    }

    // ---------- Helpers ----------
    static double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
