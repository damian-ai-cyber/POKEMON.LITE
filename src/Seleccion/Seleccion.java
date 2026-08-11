import recursos.*;

import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        // ── Se usan los arreglos que ya tienes en recursos ──
        Pokemon[] pokemonDisponibles = recursos.Pokemones;
        Ataque[] ataquesDisponibles = recursos.AtquesT;
        int cantidadDisponibles = 10; // cuantos pokemon quedan sin elegir

        // ── El jugador elige su personaje ──
        String[] nombresPersonajes = {"Maduro", "Amlo", "Rojo"};
        System.out.println("Elige tu personaje:");
        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + ". " + nombresPersonajes[i]);
        }
        int opcionPersonaje = sc.nextInt();
        Personaje jugador = new Personaje(nombresPersonajes[opcionPersonaje - 1]);

