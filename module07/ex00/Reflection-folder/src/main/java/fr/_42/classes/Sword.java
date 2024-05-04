package fr._42.classes;

public class Sword {
    private String name;
    private Integer attackDamage;

    public Sword() {
        name = "default sword";
        attackDamage = 1;
    }

    public Sword(String name, Integer attackDamage) {
        this.name = name;
        this.attackDamage = attackDamage;
    }

    // toString() method
    @Override
    public String toString() {
        return "Sword{name='" + name + "', attackDamage=" + attackDamage + "}";
    }

    // Method to print a message when swinging the sword
    public void swing() {
        System.out.println(name + " swings with " + attackDamage + " attack damage!");
    }
}
