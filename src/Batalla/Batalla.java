package Batalla;

import java.util.Scanner;
import java.util.Random;
import recursos.Personaje;
import recursos.Pokemon;
import recursos.Ataque;

public class Batalla {

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    // Metodo principal: desarrolla la batalla por turnos entre jugador y rival
    // Recibe los dos Personajes que van a pelear
    // Devuelve un boolean: true si gano el jugador, false si gano el rival
    public static boolean Batalla(Personaje jugador, Personaje rival) {

        // Muestra como un anuncio de quienes van a pelear
        System.out.println(jugador.nombre + " VS " + rival.nombre);

        // Ciclo WHILE(true): se repite indefinidamente "bucle infinito", y la unica forma de salir
        // de el es con el "break". Cada vuelta del while = un turno de la batalla
        while (true) {

            // Busca el indice del primer Pokemon vivo del jugador dentro de su equipo
            int pJugador = buscarActivo(jugador);
            // Busca el indice del primer Pokemon vivo del rival dentro de su equipo
            int pRival = buscarActivo(rival);

            // Si alguno de los dos ya no tiene ningun Pokemon vivo (buscarActivo devolvio -1),
            // se rompe el ciclo while y termina la batalla
            if (pJugador == -1 || pRival == -1) {
                break; // Corta el while(true) inmediatamente, salta hasta despues del ciclo
            }

            // Se muestra el Pokemon activo de cada entrenador con su vida actual
            System.out.println(jugador.nombre + ": " + jugador.equipo[pJugador].nombre + " HP: " + jugador.equipo[pJugador].HP);
            System.out.println(rival.nombre + ": " + rival.equipo[pRival].nombre + " HP: " + rival.equipo[pRival].HP);

            // Turno del jugador
            System.out.println("Ataques de " + jugador.equipo[pJugador].nombre + ":");
            // Ciclo for: muestra los 3 ataques disponibles del Pokemon activo del jugador, numerados del 1 al 3
            for (int i = 0; i < 3; i++) {
                // i+1 porque se le muestra al jugador 1,2,3 en vez de 0,1,2
                System.out.println((i + 1) + ". " + jugador.equipo[pJugador].ataques[i].nombre + " Daño: " + jugador.equipo[pJugador].ataques[i].daño);
            }
            System.out.print("Elige un ataque: ");
            int opcion = sc.nextInt(); // lee el numero que el jugador escribe (1, 2 o 3)
            // Se resta 1 porque el arreglo de ataques usa indices 0,1,2 y el jugador escribie 1,2,3
            Ataque ataqueJugador = jugador.equipo[pJugador].ataques[opcion - 1];

            // Formula del daño: Daño base del Ataque + ATK del atacante - DEF del rival
            int daño = ataqueJugador.daño + jugador.equipo[pJugador].ATK - rival.equipo[pRival].DEF;
            // Si el resultado da negativo (defensa muy alta), se corrige a 0, nunca hay daño negativo
            if (daño < 0) daño = 0;

            // Se resta el daño calculado a la vida del Pokemon rival
            rival.equipo[pRival].HP = rival.equipo[pRival].HP - daño;
            // Por seguridad, si de todos modos quedara negativo, se fuerza a 0 (nunca HP negativo)
            if (rival.equipo[pRival].HP < 0) {
                rival.equipo[pRival].HP = 0;
            }

            // Se informa en la consola lo que paso en este turno
            System.out.println(jugador.equipo[pJugador].nombre + " usa " + ataqueJugador.nombre + "!");
            System.out.println(rival.equipo[pRival].nombre + " recibe " + daño + " puntos de daño.");
            System.out.println("HP restante de " + rival.equipo[pRival].nombre + ": " + rival.equipo[pRival].HP);

            // Revisa si el Pokemon rival quedo en 0 HP o menos (fue derrotado)
            if (rival.equipo[pRival].HP <= 0) {
                System.out.println(rival.equipo[pRival].nombre + " ha sido derrotado!");
                int siguienteRival = buscarActivo(rival); // busca si el rival tiene otro Pokemon vivo
                if (siguienteRival != -1) {
                    // Solo se avisa que envia al siguiente; no se pregunta nada porque el rival es la maquina
                    System.out.println(rival.nombre + " envia a " + rival.equipo[siguienteRival].nombre);
                }
                // CONTINUE: salta el resto del codigo de esta vuelta del while y empieza la siguiente vuelta
                // directo desde el inicio. Se usa para que el rival NO ataque este turno, ya que su Pokemon murio.
                continue;
            }

            // Turno del rival
            int indiceAtaqueRival = rand.nextInt(3); // numero al azar entre 0 y 2 (tiene 3 ataques)
            Ataque ataqueRival = rival.equipo[pRival].ataques[indiceAtaqueRival];

            // Misma formula de daño, pero ahora atacando el rival al jugador
            int dañoRival = ataqueRival.daño + rival.equipo[pRival].ATK - jugador.equipo[pJugador].DEF;
            if (dañoRival < 0) {
                dañoRival = 0;
            }
            jugador.equipo[pJugador].HP -= dañoRival; // forma corta de escribir "HP = HP - dañoRival"
            if (jugador.equipo[pJugador].HP < 0) {
                jugador.equipo[pJugador].HP = 0; // nunca HP negativo
            }

            System.out.println("Turno de " + rival.nombre);
            System.out.println(rival.equipo[pRival].nombre + " usa " + ataqueRival.nombre + "!");
            System.out.println(jugador.equipo[pJugador].nombre + " recibe " + dañoRival + " puntos de daño");
            System.out.println("HP restante de " + jugador.equipo[pJugador].nombre + ": " + jugador.equipo[pJugador].HP);

            // Revisa si el Pokemon del jugador quedo en 0 HP o menos
            if (jugador.equipo[pJugador].HP <= 0) {
                System.out.println(jugador.equipo[pJugador].nombre + " ha sido derrotado!");
                // Como es el jugador quien perdio su Pokemon, se le pregunta cual usar despues
                // (a diferencia del rival, que no elige nada, solo se avisa)
                elegirSiguientePokemon(jugador);
            }

            // Aqui termina una vuelta del while, si nadie se quedo sin Pokemon, vuelve a repetirse el turno
        }

        // Cuando el while termina (por el break), se revisa quien gano,
        // si buscarActivo(jugador) no devuelve -1, es porque el jugador si tiene Pokemones vivos, entonces gana el jugador
        boolean ganoJugador = buscarActivo(jugador) != -1;

        System.out.println("Fin de la batalla ");
        // IF-ELSE: segun el resultado, se muestra un mensaje distinto
        if (ganoJugador) {
            System.out.println(jugador.nombre + " ha ganado la batalla contra " + rival.nombre + "!");
        } else {
            System.out.println(rival.nombre + " ha ganado la batalla");
        }

        return ganoJugador; // el metodo devuelve true o false, para que Main sepa si continuar al torneo
    }

    // Metodo que busca dentro del equipo de un Personaje cual es el primer Pokemon con vida.
    // Se usa tanto para el jugador como para el rival (recibe cualquier Personaje "p" como parametro).
    // Devuelve el indice del primer Pokemon vivo, o -1 si ninguno tiene vida.
    static int buscarActivo(Personaje p) {
        // Ciclo for: recorre las 3 posiciones del equipo (0, 1, 2)
        for (int i = 0; i < 3; i++) {
            // si ese Pokemon tiene HP mayor a 0, es el que esta "activo" (peleando)
            if (p.equipo[i].HP > 0) {
                return i; // el metodo termina aqui (return corta la ejecucion) y devuelve la posicion encontrada
            }
        }
        return -1; // si el for termino sin encontrar ninguno vivo, se devuelve -1 como señal de "no hay ninguno"
    }

    // Le permite al jugador elegir manualmente cual de sus Pokemon vivos usar
    // despues de que el actual (el de la posicion 0) muere,
    // no devuelve nada (void): modifica directamente el equipo del jugador
    static void elegirSiguientePokemon(Personaje jugador) {
        int siguiente = buscarActivo(jugador);
        // Si ya no queda ningun Pokemon vivo, no tiene sentido preguntar nada, se sale del metodo
        if (siguiente == -1) {
            return; // return sin valor porque el metodo es "void"
        }

        System.out.println("Elige tu siguiente Pokemon: ");
        int opciones = 0; // cuenta cuantos Pokemones vivos se van mostrando
        int[] indicesVivos = new int[3]; // guarda en que posiciones del equipo estan esos Pokemones vivos

        // Ciclo for: recorre el equipo completo buscando cuales estan vivos
        for (int i = 0; i < 3; i++) {
            if (jugador.equipo[i].HP > 0) {
                // Se muestra con numeracion mas simple de entender (opciones+1) aunque la posicion real sea "i"
                System.out.println((opciones + 1) + ". " + jugador.equipo[i].nombre + " (HP: " + jugador.equipo[i].HP + ")");
                indicesVivos[opciones] = i; // se guarda la posicion real "i" en la lista de vivos
                opciones++; // avanza el contador para el siguiente Pokemon vivo que se encuentre
            }
        }

        System.out.print("Pokemon: ");
        int opcion = sc.nextInt(); // lee el numero que eligio el jugador
        // Traduce el numero elegido por el usuario (opcion-1) a la posicion dentro del equipo
        int indiceElegido = indicesVivos[opcion - 1];

        // Se intercambia el Pokemon elegido con el que esta en la posicion 0,
        // asi la proxima vez que se llame buscarActivo(), lo va a encontrar primero automaticamente
        Pokemon temp = jugador.equipo[0];                  // 1. se guarda temporalmente el que esta en la posicion 0
        jugador.equipo[0] = jugador.equipo[indiceElegido];  // 2. el elegido pasa a ocupar la posicion 0
        jugador.equipo[indiceElegido] = temp;               // 3. el que estaba en 0 va a la posicion que dejo el elegido
    }

    // Restaura la vida de TODO el equipo de un Personaje al maximo (MaxHp).
    // Se usa, por ejemplo, entre la primera y la segunda batalla del torneo
    public static void restaurarEquipo(Personaje p) {
        // Ciclo for: recorre los 3 Pokemon del equipo y a cada uno le regresa su HP al valor MaxHp original
        for (int i = 0; i < 3; i++) {
            p.equipo[i].HP = p.equipo[i].MaxHp;
        }
    }
}