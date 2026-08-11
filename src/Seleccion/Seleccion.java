package Seleccion;

import java.util.Scanner; // permite leer lo que el usuario escribe en consola
import java.util.Random;  // permite generar numeros al azar
import recursos.recursos;
import recursos.Pokemon;
import recursos.Ataque;
import recursos.Personaje;

public class Seleccion {

    // Metodo que arma toda la seleccion (personaje + equipos) y devuelve
    // un arreglo de 3 Personajes: [jugador, rival1, rival2]
    public static Personaje[] seleccionar() {

        Scanner sc = new Scanner(System.in); // objeto para leer teclado
        Random rand = new Random();          // objeto para generar numeros al azar

        // Se toman los arreglos ya armados en recursos.java, para no repetir esos datos aqui
        Pokemon[] pokemonDisponibles = recursos.Pokemones;
        Ataque[] ataquesDisponibles = recursos.AtquesT;
        int PkDisponibles = 10; // contador: indica cuantos Pokemon del arreglo siguen sin elegir

        // Seleccion de personaje

        // Ahora los nombres se leen directamente del arreglo recursos. Personajes, en vez de escribirlos otra vez aqui.
        String[] Personajes = new String[3];

        // Ciclo for: recorre los 3 objetos Personaje guardados en recursos.Personajes
        // y saca solamente su atributo "nombre" guardandolo en Personajes
        for (int p = 0; p < 3; p++) {
            Personajes[p] = recursos.Personajes[p].nombre;
        }

        System.out.println("Elige tu personaje:");

        int i;
        // Ciclo for: recorre los 3 nombres solo para mostrarlos numerados en pantalla
        for (i = 0; i < 3; i++) {
            System.out.println((i + 1) + ". " + Personajes[i]);
        }

        int PersonajeSelec = sc.nextInt(); // sc.nextInt() detiene el programa hasta que el usuario escriba un numero y presione Enter
        Personaje jugador = new Personaje(Personajes[PersonajeSelec - 1]); // se resta 1 porque los arreglos empiezan en indice 0, no en 1

        // Ciclo FOR: recorre los 3 nombres para separar cuales no fueron elegidos (para armar los rivales)
        String[] Rivales = new String[2];
        int contador = 0; // lleva la cuenta de cuantos rivales ya se guardaron (0 o 1)
        for (int r = 0; r < 3; r++) {
            // Compara si el indice actual "r" es distinto al que el jugador eligio
            if (r != PersonajeSelec - 1) {
                Rivales[contador] = Personajes[r];
                contador++; // avanza al siguiente espacio del arreglo Rivales
            }
        }

        Personaje rival1 = new Personaje(Rivales[0]);
        Personaje rival2 = new Personaje(Rivales[1]);

        // rand.nextBoolean() devuelve true o false al azar (50% de probabilidad cada uno).
        // Si sale true, se intercambian rival1 y rival2 usando una variable temporal (temp) como espacio prestado.
        if (rand.nextBoolean()) {
            Personaje temp = rival1;
            rival1 = rival2;
            rival2 = temp;
        }

        // Seleccion de Pokemon

        System.out.println("Seleccion de equipo Pokemon ");

        for (i = 0; i < 3; i++) {  // Ciclo externo y se repite 3 veces
            System.out.println("Pokemones Disponibles: ");

            // Ciclo for interno y recorre solo los pokemon que siguen disponibles (PkDisponibles)
            for (int j = 0; j < PkDisponibles; j++) {
                System.out.println((j + 1) + ". " + pokemonDisponibles[j].nombre);
            }

            System.out.print("Elige el Pokemon " + (i + 1) + ": ");
            int indice = sc.nextInt() - 1; // se resta 1 por la misma razon que los indices empiezan en 0

            // se repite exactamente 3 veces para darle 3 ataques distintos al Pokemon elegido
            for (int k = 0; k < 3; k++) {
                int indiceAtaque = rand.nextInt(30); // numero al azar entre 0 y 29
                pokemonDisponibles[indice].ataques[k] = ataquesDisponibles[indiceAtaque];
            }

            jugador.equipo[i] = pokemonDisponibles[indice]; // se guarda el Pokemon elegido en el equipo del jugador

            pokemonDisponibles[indice] = pokemonDisponibles[PkDisponibles - 1];
            PkDisponibles--;
        }

        // Muestra el resumen del equipo del jugador

        System.out.println("Elegiste a " + jugador.nombre);
        System.out.println("Tu equipo:");

        // Ciclo for: recorre los 3 Pokemon del equipo del jugador para imprimir su info
        for (int m = 0; m < 3; m++) {
            Pokemon p = jugador.equipo[m]; // variable "p" solo para no escribir jugador.equipo[m] muchas veces
            System.out.println((m + 1) + ". " + p.nombre + " (HP: " + p.HP + " | ATK: " + p.ATK + " | DEF: " + p.DEF + ")");
            System.out.println("   Ataques:");
            // Ciclo for: recorre los 3 ataques de ese Pokemon especifico
            for (int n = 0; n < 3; n++) {
                System.out.println("  - " + p.ataques[n].nombre + " (Daño: " + p.ataques[n].daño + ")");
            }
        }

        // Equipo aleatorio para rival1

        // Ciclo for: se repite 3 veces para armar el equipo completo del rival1
        for (int j = 0; j < 3; j++) {

            int indicePokemon = rand.nextInt(PkDisponibles); // elige un indice al azar SOLO entre los que quedan disponibles

            // Ciclo for: le da 3 ataques al azar, igual que se hizo con el jugador
            for (int k = 0; k < 3; k++) {
                int indiceAtaque = rand.nextInt(30);
                pokemonDisponibles[indicePokemon].ataques[k] = ataquesDisponibles[indiceAtaque];
            }

            rival1.equipo[j] = pokemonDisponibles[indicePokemon];

            // "eliminar" del arreglo que se uso arriba
            pokemonDisponibles[indicePokemon] = pokemonDisponibles[PkDisponibles - 1];
            PkDisponibles--;
        }

        // Equipo aleatorio para rival2

        for (int j = 0; j < 3; j++) {

            int indicePokemon = rand.nextInt(PkDisponibles);

            for (int k = 0; k < 3; k++) {
                int indiceAtaque = rand.nextInt(30);
                pokemonDisponibles[indicePokemon].ataques[k] = ataquesDisponibles[indiceAtaque];
            }

            rival2.equipo[j] = pokemonDisponibles[indicePokemon];

            pokemonDisponibles[indicePokemon] = pokemonDisponibles[PkDisponibles - 1];
            PkDisponibles--;
        }

        System.out.println(rival1.nombre + " recibio un equipo ");
        System.out.println(rival2.nombre + " recibio un equipo ");

        // Muestra el resumen del equipo del rival1

        System.out.println("Equipo de: " + rival1.nombre);

        for (int e = 0; e < 3; e++) {
            Pokemon R1 = rival1.equipo[e];
            System.out.println((e + 1) + ". " + R1.nombre + " (HP: " + R1.HP + " | ATK: " + R1.ATK + " | DEF: " + R1.DEF + ")");
            System.out.println("   Ataques:");
            for (int n = 0; n < 3; n++) {
                System.out.println("  - " + R1.ataques[n].nombre + " (Daño: " + R1.ataques[n].daño + ")");
            }
        }

        // Muestra el resumen del equipo del rival2

        System.out.println("Equipo de: " + rival2.nombre);

        for (int w = 0; w < 3; w++) {
            Pokemon R2 = rival2.equipo[w];
            System.out.println((w + 1) + ". " + R2.nombre + " (HP: " + R2.HP + " | ATK: " + R2.ATK + " | DEF: " + R2.DEF + ")");
            System.out.println("   Ataques:");
            for (int n = 0; n < 3; n++) {
                System.out.println("  - " + R2.ataques[n].nombre + " (Daño: " + R2.ataques[n].daño + ")");
            }

        }

        // Se devuelven los 3 Personajes ya armados dentro de un arreglo para que Main.java los reciba y los use
        return new Personaje[]{jugador, rival1, rival2};
    }
}