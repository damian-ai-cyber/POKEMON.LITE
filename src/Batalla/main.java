package Batalla;

import java.util.Scanner;
import recursos.Personaje;
import Seleccion.Seleccion;

public class main {

    static Scanner sc = new Scanner(System.in);

    // Punto de entrada del programa, aqui es donde Java empieza a ejecutar todo
    public static void main(String[] args) {

        boolean JugarDeNuevo = true; // controla si el torneo completo se vuelve a jugar desde cero

        // Ciclo WHILE: se repite mientras JugarDeNuevo sea true.
        // Cada vuelta completa = un torneo completo (seleccion + 2 batallas)
        while (JugarDeNuevo) {

            System.out.println("BIENVENIDO AL TORNEO POKEMON ");

            // Se llama al metodo seleccionar() de la clase Seleccion, que devuelve un arreglo con 3 Personajes
            Personaje[] personajes = Seleccion.seleccionar();
            Personaje jugador = personajes[0]; // el primer elemento del arreglo siempre es el jugador
            Personaje rival1 = personajes[1];  // el segundo rival1
            Personaje rival2 = personajes[2];  // el tercero rival2

            // Primera batalla: jugador contra rival1. batalla() devuelve true si el jugador gano
            boolean ganoPrimera = Batalla.Batalla(jugador, rival1);
            boolean TorneoGanado = false; // Se asume que no ha ganado el torneo todavia

            // Solo si gano la primera batalla, se continua con la segunda
            if (ganoPrimera) {
                Batalla.restaurarEquipo(jugador); // se le regresa toda la vida al equipo del jugador
                System.out.println("Tu equipo ha recuperado toda su vida para la siguiente batalla ");

                // Segunda batalla: jugador contra rival2
                boolean GanoSegunda = Batalla.Batalla(jugador, rival2);
                if (GanoSegunda) {
                    TorneoGanado = true; // solo se gana el torneo completo si se ganaron AMBAS batallas
                }
            }

            // IF-ELSE: segun si se gano o no el torneo completo, se muestra un mensaje distinto
            if (TorneoGanado) {

                System.out.println(" !FELICIDADES HAS GANADO EL TORNEO! ");
                JugarDeNuevo = false; // Se pone en false para que el while de arriba no se repita, y el programa termine
            } else {
                System.out.println("Has perdido el torneo ");
                System.out.println("1. Volver a jugar ");
                System.out.println("2. Salir ");
                System.out.print("Elige una opcion: ");
                int OpcionFinal = sc.nextInt();
                // Si el usuario escribio 1, JugarDeNuevo queda en true (se repite el while).
                // Si escribio cualquier otra cosa (se espera 2), JugarDeNuevo queda en false y el programa termina.
                JugarDeNuevo = (OpcionFinal == 1);
            }
            // Aqui termina una vuelta del while principal
        }

        System.out.println("Gracias por jugar!");

    }
}