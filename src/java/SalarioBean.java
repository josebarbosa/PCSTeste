
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 */
@ManagedBean(name="salarioBean")
@ViewScoped
public class SalarioBean implements Serializable{
    
    /*
    * As variáveis do formulário serão inteiros (para representar chaves) ou doubles (para representar valores ou índices), e serão carregadas a partir de listas
    do tipo SelectItem, que conterão strings de identificação. 
    */
    private Integer orgao;
    private List<SelectItem> orgaos;
    
    //ano para cálculo da tabela
    private Integer ano;
    private Integer cargo;
    private List<SelectItem> cargos; 
    private String classe;
    private Integer padrao; 
    private Integer funcao;
    private Integer escolaridade;
    private Integer treinamento;
    private Integer anuenios;
    private Integer dependentesIRRF;
    private boolean optaPlanAssiste;
    private boolean conjugePlanAssiste;
    private Integer filhosPlanAssiste;
    private Integer paisPlanAssiste; 
    private Integer dependentesAuxilioCreche;
    private double vpni;
    private boolean gas;
    private boolean projeto;
    private double penosidade;
    private double insalubridade; 
    private double previdenciaAliquota;
    private List<SelectItem> previdenciaAliquotas; 
    
    //valores fixos
    private double gampu;
    private double auxilioAlimentacao;
    
    //descontos
    private double pss;
    private double IRRF;
    private double planAssiste;
    //valor que permite que servidores coloquem valores genéricos (como consignações), para terem melhor idéia do valor do real salário líquido. 
    private double outrosDescontos; 

    
    
    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public List<SelectItem> getCargos() {
         if(this.cargos == null){
            this.cargos = new ArrayList<SelectItem>();
            this.cargos.add(new SelectItem("Analista)", 2));
            this.cargos.add(new SelectItem("Técnico", 1));
         }
        return cargos;
    }

    public void setCargos(List<SelectItem> cargos) {
        this.cargos = cargos;
    }
    public boolean isOptaPlanAssiste() {
        return optaPlanAssiste;
    }

    public void setOptaPlanAssiste(boolean optaPlanAssiste) {
        this.optaPlanAssiste = optaPlanAssiste;
    }

    public boolean isConjugePlanAssiste() {
        return conjugePlanAssiste;
    }

    public void setConjugePlanAssiste(boolean conjugePlanAssiste) {
        this.conjugePlanAssiste = conjugePlanAssiste;
    }

    public Integer getFilhosPlanAssiste() {
        return filhosPlanAssiste;
    }

    public void setFilhosPlanAssiste(Integer filhosPlanAssiste) {
        this.filhosPlanAssiste = filhosPlanAssiste;
    }

    public Integer getPaisPlanAssiste() {
        return paisPlanAssiste;
    }

    public void setPaisPlanAssiste(Integer paisPlanAssiste) {
        this.paisPlanAssiste = paisPlanAssiste;
    }
    
    public Integer getOrgao() {
        return orgao;
    }

    public void setOrgao(Integer orgao) {
        this.orgao = orgao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getPadrao() {
        return padrao;
    }

    public void setPadrao(Integer padrao) {
        this.padrao = padrao;
    }

    public Integer getFuncao() {
        return funcao;
    }

    public void setFuncao(Integer funcao) {
        this.funcao = funcao;
    }

    public Integer getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Integer escolaridade) {
        this.escolaridade = escolaridade;
    }

    public Integer getTreinamento() {
        return treinamento;
    }

    public void setTreinamento(Integer treinamento) {
        this.treinamento = treinamento;
    }

    public Integer getAnuenios() {
        return anuenios;
    }

    public void setAnuenios(Integer anuenios) {
        this.anuenios = anuenios;
    }

    public Integer getDependentesIRRF() {
        return dependentesIRRF;
    }

    public void setDependentesIRRF(Integer dependentesIRRF) {
        this.dependentesIRRF = dependentesIRRF;
    }

    public Integer getDependentesAuxilioCreche() {
        return dependentesAuxilioCreche;
    }

    public void setDependentesAuxilioCreche(Integer dependentesAuxilioCreche) {
        this.dependentesAuxilioCreche = dependentesAuxilioCreche;
    }

    public double getVpni() {
        return vpni;
    }

    public void setVpni(double vpni) {
        this.vpni = vpni;
    }

    public boolean isGas() {
        return gas;
    }

    public void setGas(boolean gas) {
        this.gas = gas;
    }

    public boolean isProjeto() {
        return projeto;
    }

    public void setProjeto(boolean projeto) {
        this.projeto = projeto;
    }

    public double getPenosidade() {
        return penosidade;
    }

    public void setPenosidade(double penosidade) {
        this.penosidade = penosidade;
    }

    public double getInsalubridade() {
        return insalubridade;
    }

    public void setInsalubridade(double insalubridade) {
        this.insalubridade = insalubridade;
    }

    public double getPrevidenciaAliquota() {
        return previdenciaAliquota;
    }

    public void setPrevidenciaAliquota(double previdenciaAliquota) {
        this.previdenciaAliquota = previdenciaAliquota;
    }

    public List<SelectItem> getPrevidenciaAliquotas() {
        if(this.previdenciaAliquotas == null){
            this.previdenciaAliquotas = new ArrayList<SelectItem>();
            this.previdenciaAliquotas.add(new SelectItem("Regime Próprio", 11));
            this.previdenciaAliquotas.add(new SelectItem("Não optante Funpresp", 0));
            this.previdenciaAliquotas.add(new SelectItem("Funpresp: 7,5%", 7.5));
            this.previdenciaAliquotas.add(new SelectItem("Funpresp: 8,0%", 8));
            this.previdenciaAliquotas.add(new SelectItem("Funpresp: 8,5%", 8.5));
        }
        return previdenciaAliquotas;
    }

    public void setPrevidenciaAliquotas(List<SelectItem> previdenciaAliquotas) {
        this.previdenciaAliquotas = previdenciaAliquotas;
    }

    public double getGampu() {
        return gampu;
    }

    public void setGampu(double gampu) {
        this.gampu = gampu;
    }

    public double getAuxilioAlimentacao() {
        return auxilioAlimentacao;
    }

    public void setAuxilioAlimentacao(double auxilioAlimentacao) {
        this.auxilioAlimentacao = auxilioAlimentacao;
    }

    public double getPss() {
        return pss;
    }

    public void setPss(double pss) {
        this.pss = pss;
    }

    public double getIRRF() {
        return IRRF;
    }

    public void setIRRF(double IRRF) {
        this.IRRF = IRRF;
    }

    public double getPlanAssiste() {
        return planAssiste;
    }

    public void setPlanAssiste(double planAssiste) {
        this.planAssiste = planAssiste;
    }

    public double getOutrosDescontos() {
        return outrosDescontos;
    }

    public void setOutrosDescontos(double outrosDescontos) {
        this.outrosDescontos = outrosDescontos;
    }

    

    public void setOrgaos(List<SelectItem> orgaos) {
        this.orgaos = orgaos;
    }
    
    /**
     *
     */
    public List<SelectItem> getOrgaos(){
        if(this.orgaos == null){
            this.orgaos = new ArrayList<SelectItem>();
            this.orgaos.add(new SelectItem("Judiciário", 2));
            this.orgaos.add(new SelectItem("MPU", 1));
        }
        return orgaos; 
    }
    
}
