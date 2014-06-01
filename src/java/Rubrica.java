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
        this.anoReferencia = 2012;
        this.nomeRubrica = "";
        this.valor = 0.0; 
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
