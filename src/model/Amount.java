/*

* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license

* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template

*/

package model;
 
public class Amount {

    private double value;

    private final String currency = "euro";
 
    public Amount(double valor) {

        this.value = valor;

    }
 
    public double getValue() {

        return value;

    }
 
    public void setValue(double valor) {

        this.value = valor;

    }
 
    public String getCurrency() {

        return currency;

    }
 
    @Override

    public String toString() {

        return value + " " + currency;

    }

}
 