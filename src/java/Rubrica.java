
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

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
@ManagedBean
@ViewScoped
public class Rubrica {
    
    private double valor; 
    private Integer anoReferencia; 
    private String nomeRubrica; 
    private boolean incideImpostoRenda;
    private boolean incidePrevidencia;
    private boolean ehCredito; 
    
    public Rubrica(){
        this.incideImpostoRenda = true;
        this.incidePrevidencia = true;
        this.ehCredito = true; 
    }
    
    public Integer getAnoReferencia() {
        return anoReferencia;
    }

    public void setAnoReferencia(Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }




    public String getNomeRubrica() {
        return nomeRubrica;
    }

    public void setNomeRubrica(String nomeRubrica) {
        this.nomeRubrica = nomeRubrica;
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
