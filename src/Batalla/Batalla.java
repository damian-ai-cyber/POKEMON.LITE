import java.util.Scanner;
import java.util.Random;
import recursos.Personaje;
import recursos.Pokemon;
import recursos.Ataque;

public class Batalla {

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    // Desarrolla la batalla por turnos. Devuelve true si gana el jugador.

    public static boolean Batalla (Personaje jugador, Personaje rival){

            System.out.println("\n=====================================");
            System.out.println("   " + jugador.nombre + " VS " + rival.nombre);
            System.out.println("=====================================");

            // La batalla continua mientras ambos tengan al menos un Pokemon vivo
            while (true) {

                int pJugador = buscarActivo(jugador);
                int pRival = buscarActivo(rival);

                // La batalla finaliza cuando uno de los entrenadores ya no tiene Pokemon disponibles
                if (pJugador == -1 || pRival == -1) {
                    break;
                }

                // Se muestra el Pokemon activo de cada entrenador
                System.out.println("\n" + jugador.nombre + ": " + jugador.equipo[pJugador].nombre + " (HP: " + jugador.equipo[pJugador].HP + ")");
                System.out.println(rival.nombre + ": " + rival.equipo[pRival].nombre + " (HP: " + rival.equipo[pRival].HP + ")");

                // ── El jugador elige uno de sus 3 ataques disponibles ──
                System.out.println("\nAtaques de " + jugador.equipo[pJugador].nombre + ":");
                for (int i = 0; i < 3; i++) {
                    System.out.println((i + 1) + ". " + jugador.equipo[pJugador].ataques[i].nombre + " (Daño: " + jugador.equipo[pJugador].ataques[i].daño + ")");
                }
                System.out.print("Elige un ataque: ");
                int opcion = sc.nextInt();
                Ataque ataqueJugador = jugador.equipo[pJugador].ataques[opcion - 1];

                // Formula: Daño Final = Daño del Ataque + ATK del Pokemon - DEF del Pokemon rival
                int danio = ataqueJugador.daño + jugador.equipo[pJugador].ATK - rival.equipo[pRival].DEF;
                if (danio < 0) danio = 0; // si el resultado es menor que 0, se considera como 0

                // Se actualiza la vida del Pokemon defensor
                rival.equipo[pRival].HP = rival.equipo[pRival].HP - danio;
                if (rival.equipo[pRival].HP < 0) {
                    rival.equipo[pRival].HP = 0;
                }

                // Se muestra en consola el resultado del turno
                System.out.println(jugador.equipo[pJugador].nombre + " usa " + ataqueJugador.nombre + "!");
                System.out.println(rival.equipo[pRival].nombre + " recibe " + danio + " puntos de daño.");
                System.out.println("HP restante de " + rival.equipo[pRival].nombre + ": " + rival.equipo[pRival].HP);

                // Cuando un Pokemon llega a 0 HP, es derrotado y se selecciona otro
                if (rival.equipo[pRival].HP <= 0) {
                    System.out.println(rival.equipo[pRival].nombre + " ha sido derrotado!");
                    int siguienteRival = buscarActivo(rival);
                    if (siguienteRival != -1) {
                        System.out.println(rival.nombre + " envia a " + rival.equipo[siguienteRival].nombre);
                    }
                    continue; // se salta el turno del rival porque ya perdio su Pokemon
                }

                // ── El rival elige un ataque de manera aleatoria ──
                int indiceAtaqueRival = rand.nextInt(3);
                Ataque ataqueRival = rival.equipo[pRival].ataques[indiceAtaqueRival];

                int danioRival = ataqueRival.daño + rival.equipo[pRival].ATK - jugador.equipo[pJugador].DEF;
                if (danioRival < 0) {
                    danioRival = 0;
                }
                jugador.equipo[pJugador].HP -= danioRival;
                if (jugador.equipo[pJugador].HP < 0) {
                    jugador.equipo[pJugador].HP = 0;
                }

                System.out.println("\nTurno de " + rival.nombre + ".");
                System.out.println(rival.equipo[pRival].nombre + " usa " + ataqueRival.nombre + "!");
                System.out.println(jugador.equipo[pJugador].nombre + " recibe " + danioRival + " puntos de daño.");
                System.out.println("HP restante de " + jugador.equipo[pJugador].nombre + ": " + jugador.equipo[pJugador].HP);

                // Si el Pokemon del jugador fue derrotado, se le pide elegir manualmente otro
                if (jugador.equipo[pJugador].HP <= 0) {
                    System.out.println(jugador.equipo[pJugador].nombre + " ha sido derrotado!");
                    elegirSiguientePokemon(jugador);
                }
            }

            // Gana la pelea el entrenador que logre derrotar a todos los Pokemon de su rival
            boolean ganoJugador = buscarActivo(jugador) != -1;

            System.out.println("\n--- Fin de la batalla ---");
            if (ganoJugador) {
                System.out.println(jugador.nombre + " ha ganado la batalla contra " + rival.nombre + "!");
            } else {
                System.out.println(rival.nombre + " ha ganado la batalla.");
            }

            return ganoJugador;
        }

        // Busca el primer Pokemon vivo del equipo, o -1 si no queda ninguno
        static int buscarActivo (Personaje p){
            for (int i = 0; i < 3; i++) {
                if (p.equipo[i].HP > 0) {
                    return i;
                }
            }
            return -1;
        }

        // Le permite al jugador elegir manualmente su siguiente Pokemon vivo
        static void elegirSiguientePokemon (Personaje jugador){
            int siguiente = buscarActivo(jugador);
            if (siguiente == -1) {
                return; // ya no queda ninguno vivo
            }

            System.out.println("Elige tu siguiente Pokemon:");
            int opciones = 0;
            int[] indicesVivos = new int[3];

            for (int i = 0; i < 3; i++) {
                if (jugador.equipo[i].HP > 0) {
                    System.out.println((opciones + 1) + ". " + jugador.equipo[i].nombre + " (HP: " + jugador.equipo[i].HP + ")");
                    indicesVivos[opciones] = i;
                    opciones++;
                }
            }

            System.out.print("Pokemon: ");
            int opcion = sc.nextInt();
            int indiceElegido = indicesVivos[opcion - 1];

            // Se mueve el elegido a la primera posicion para que buscarActivo lo encuentre primero
            Pokemon temp = jugador.equipo[0];
            jugador.equipo[0] = jugador.equipo[indiceElegido];
            jugador.equipo[indiceElegido] = temp;
        }

        // Restaura la vida de todo el equipo (se usa entre la 1ra y 2da batalla)
        public static void restaurarEquipo (Personaje p){
            for (int i = 0; i < 3; i++) {
                p.equipo[i].HP = p.equipo[i].MaxHp;
            }
        }
    }