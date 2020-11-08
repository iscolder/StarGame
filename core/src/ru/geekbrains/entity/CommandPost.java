package ru.geekbrains.entity;

public class CommandPost {

    private final static int HP = 400;
    private int hp;

    public CommandPost() {
        this.hp = HP;
    }

    public void damage(int damage) {
        hp -= damage;
    }

    public void heal(int heal) {
        hp += heal;
    }

    public boolean destroyed() {
        return hp <= 0;
    }

    public int getHp() {
        return hp;
    }

    public void respawn() {
        hp = HP;
    }

}
