package Batalla;

import java.util.Scanner;
import recursos.Personaje;
import Seleccion.Seleccion;

public class main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean jugarDeNuevo = true;

        while (jugarDeNuevo) {

            System.out.println("========================================");
            System.out.println("   BIENVENIDO AL TORNEO POKEMON");
            System.out.println("========================================");

            Personaje[] personajes = Seleccion.seleccionar();
            Personaje jugador = personajes[0];
            Personaje rival1 = personajes[1];
            Personaje rival2 = personajes[2];

            boolean ganoPrimera = Batalla.batalla(jugador, rival1);
            boolean torneoGanado = false;

            if (ganoPrimera) {
                Batalla.restaurarEquipo(jugador);
                System.out.println("\nTu equipo ha recuperado toda su vida para la siguiente batalla.");

                boolean ganoSegunda = Batalla.batalla(jugador, rival2);
                if (ganoSegunda) {
                    torneoGanado = true;
                }
            }

            if (torneoGanado) {
                System.out.println("\n****************************************");
                System.out.println("   FELICIDADES! HAS GANADO EL TORNEO!");
                System.out.println("****************************************");
                jugarDeNuevo = false;
            } else {
                System.out.println("\nHas perdido el torneo.");
                System.out.println("1. Volver a jugar");
                System.out.println("2. Salir");
                System.out.print("Elige una opcion: ");
                int opcionFinal = sc.nextInt();
                jugarDeNuevo = (opcionFinal == 1);
            }
        }

        System.out.println("\nGracias por jugar!");
    }
}