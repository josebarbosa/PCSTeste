
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josebarbosa
 */

public class Rubrica implements Serializable{
    private String nome;
    private double valor;
    private boolean incidePrevidencia;
    private boolean incideImpostodeRenda;
    private boolean credito; 
    
    public Rubrica(){
        
    }

    public Rubrica(String nome, double valor, boolean incidePrevidencia, boolean incideImpostodeRenda, boolean credito) {
        this.nome = nome;
        this.valor = valor;
        this.incidePrevidencia = incidePrevidencia;
        this.incideImpostodeRenda = incideImpostodeRenda;
        this.credito = credito;
    }
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isIncidePrevidencia() {
        return incidePrevidencia;
    }

    public void setIncidePrevidencia(boolean incidePrevidencia) {
        this.incidePrevidencia = incidePrevidencia;
    }

    public boolean isIncideImpostodeRenda() {
        return incideImpostodeRenda;
    }

    public void setIncideImpostodeRenda(boolean incideImpostodeRenda) {
        this.incideImpostodeRenda = incideImpostodeRenda;
    }

    public boolean isCredito() {
        return credito;
    }

    public void setCredito(boolean credito) {
        this.credito = credito;
    }
    
    
    
}
