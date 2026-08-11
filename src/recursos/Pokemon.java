package recursos;

public class Pokemon {

    public String nombre;
    public int MaxHp;
    public int HP;
    public int ATK;
    public int DEF;
    public Ataque[] ataques;

    Pokemon(String nombre, int MaxHP, int HP, int ATK, int DEF) {
        this.nombre = nombre;    //el this sirve para guardar el nombre de una variable en una clase para que no se confuda el programa
        this.MaxHp = MaxHP;
        this.HP = HP;
        this.ATK = ATK;
        this.DEF = DEF;
        this.ataques = new Ataque[3];
    }
}
