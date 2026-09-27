package co.edu.uptc.view;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int opc=0;
        do{
            String menuPrincipal = """
                    =======MENU PRINCIPAL=======
                    1. Parte Jesus
                    2. Parte Camilo
                    3. Parte Paula
                    4. Salir

                    Seleccione una opcion: """;
            System.out.println(menuPrincipal);
            opc = sc.nextInt();

            switch (opc) {
                case 1:
                    //llamar metodo
                    opc =4;
                    break;
                case 2:
                    //
                    break;
                case 3:
                    //
                    break;
                case 4:
                    System.out.println("Saliendo de la app...");
                    break;
            
                default:
                    System.out.println("Opcion invalida");
                    break;
            }

            
        }while(opc !=4);
        sc.close();
    }

    //submenus y metodos 
}
