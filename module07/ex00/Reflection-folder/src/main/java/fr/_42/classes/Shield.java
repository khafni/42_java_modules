package fr._42.classes;

public class Shield {
    private String name;
    private Integer defenseValue;

    public Shield() {
        name = "default shield";
        defenseValue = 1;
    }

    public Shield(String name, Integer defenseValue) {
        this.name = name;
        this.defenseValue = defenseValue;
    }

    @Override
    public String toString() {
        return "Shield{name='" + name + "', defenseValue=" + defenseValue + "}";
    }

    public void blockAttack() {
        System.out.println(name + " blocks an attack, reducing damage by " + defenseValue + " points!");
    }
}
