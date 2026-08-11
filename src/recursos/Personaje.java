package recursos;

public class Personaje {

    public String nombre;
    public Pokemon[] equipo;

    public Personaje(String nombre) {

        this.nombre = nombre;
        this.equipo = new Pokemon[3];
    }
}
