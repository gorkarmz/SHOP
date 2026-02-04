/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Logable;

/**
 *
 * @author gorka
 */
public class Employee extends Person implements Logable {

    private final int EMPLOYEE_ID = 123;
    private final String password = "test";

    public Employee(String name) {
        super(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  

  

    @Override
    public boolean login(int user, String password) {
        return this.EMPLOYEE_ID == user && this.password.equals(password);
    }
}
