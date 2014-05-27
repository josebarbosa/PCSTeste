/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josebarbosa
 * 
 * A classe rubrica é aquela em que vai conter uma descrição da verba, seu caráter indenizatório e o valor. 
 * Depois será feita uma soma dos valores para poder ser calculada
 */
public class Rubrica {
    
    private double valor;
    private String nomeRubica; 
    private boolean incideImpostoRenda;
    private boolean incidePrevidencia;
    private boolean ehCredito; 
    
    public Rubrica(){
        
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNomeRubica() {
        return nomeRubica;
    }

    public void setNomeRubica(String nomeRubica) {
        this.nomeRubica = nomeRubica;
    }

    public boolean isIncideImpostoRenda() {
        return incideImpostoRenda;
    }

    public void setIncideImpostoRenda(boolean incideImpostoRenda) {
        this.incideImpostoRenda = incideImpostoRenda;
    }

    public boolean isIncidePrevidencia() {
        return incidePrevidencia;
    }

    public void setIncidePrevidencia(boolean incidePrevidencia) {
        this.incidePrevidencia = incidePrevidencia;
    }

    public boolean isEhCredito() {
        return ehCredito;
    }

    public void setEhCredito(boolean ehCredito) {
        this.ehCredito = ehCredito;
    }
    
    
    
}
