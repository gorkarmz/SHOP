/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Payable;



/**
 *
 * @author gorka
 */
public class Client extends Person implements Payable {
    private int memberId;
    private Amount balance;
    
  
    private final int MEMBER_ID = 456;
    private final Amount BALANCE = new Amount(50);
    
    public Client (String name){
        super(name);
        this.balance = this.BALANCE;
        this.memberId = this.MEMBER_ID;
    }
    
    public Amount getBalance(){
        return this.balance;
    }
    
    public int getId(){
        return this.memberId;
    }

    @Override
    public boolean pay(Amount amount) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
