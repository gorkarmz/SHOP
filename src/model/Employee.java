/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gorka
 */
public class Employee extends Person {
    
    private final int employeeld = 123;
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
    public String toString() {
        return "Employee{" + "employeeld=" + employeeld + ", password=" + password + '}';
    }
    


    
}
