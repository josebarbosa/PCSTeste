
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
    
    /*
    Construtor
        */
    public SalarioBean(){
        this.orgao = 1; 
        this.ano = 2012;
        this.cargo = 1; 
        this.padrao = 1;
        this.funcao = 0;
        this.escolaridade = 2;
        this.treinamento = 0;
        this.anuenio = 0;
        this.dependentesIRRF = 0;
        this.optaPlanAssiste = false;
        this.conjugePlanAssiste = false;
        this.filhosPlanAssiste = 0;
        this.paisPlanAssiste = 0;
        this.vpni = 0.0;
        this.gas = false;
        this.projeto = false; 
        this.penosidade = false;
        this.insalubridade = false; 
        this.previdenciaAliquota = 11; 
        this.dependentesAuxilioCreche = 0;
        
    }
    //ano para cálculo da tabela
    private Integer ano;
    private Integer cargo;
    private List<SelectItem> cargos; 
    private String classe;
    private Integer padrao; 
    private List<SelectItem> padroes;
    private Integer funcao;
    private Integer escolaridade;
    private List<SelectItem> escolaridades; 
    private Integer treinamento;
    private List<SelectItem> treinamentos; 
    private Integer anuenio;
    private List<SelectItem> anuenios; 
    private Integer dependentesIRRF;
    private List<SelectItem> dependentesIRRFs;
    private boolean optaPlanAssiste;
    private boolean conjugePlanAssiste;
    private Integer filhosPlanAssiste;
    private List<SelectItem> filhosPlanAssistes;
    private Integer paisPlanAssiste; 
    private List<SelectItem> paisPlanAssistes; 
    private Integer dependentesAuxilioCreche;
    private List<SelectItem> dependentesAuxilioCreches; 
    private double vpni;
    private boolean gas;
    private boolean projeto;
    private boolean penosidade;
    private boolean insalubridade; 
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

    public List<SelectItem> getDependentesAuxilioCreches() {
        if(this.dependentesAuxilioCreches == null){
            this.dependentesAuxilioCreches = new ArrayList<SelectItem>();
            for (Integer x=0; x <= 5; x++) this.dependentesAuxilioCreches.add(new SelectItem(x.toString(), x));
        }
        return dependentesAuxilioCreches;
    }

    public void setDependentesAuxilioCreches(List<SelectItem> dependentesAuxilioCreches) {
        this.dependentesAuxilioCreches = dependentesAuxilioCreches;
    }
    
    public List<SelectItem> getDependentesIRRFs() {
        if(this.dependentesIRRFs == null){
            this.dependentesIRRFs = new ArrayList<SelectItem>();
            for (Integer x=0; x <= 5; x++) this.dependentesIRRFs.add(new SelectItem(x.toString(), x));
        }
        return dependentesIRRFs;
    }

    public void setDependentesIRRFs(List<SelectItem> dependentesIRRFs) {
        this.dependentesIRRFs = dependentesIRRFs;
    }

    public List<SelectItem> getFilhosPlanAssistes() {
        if(this.filhosPlanAssistes == null){
            this.filhosPlanAssistes = new ArrayList<SelectItem>();
            for (Integer x=0; x <= 5; x++) this.filhosPlanAssistes.add(new SelectItem(x.toString(), x));
        }
        return filhosPlanAssistes;
    }

    public void setFilhosPlanAssistes(List<SelectItem> filhosPlanAssistes) {
        this.filhosPlanAssistes = filhosPlanAssistes;
    }

    public List<SelectItem> getPaisPlanAssistes() {
        if(this.paisPlanAssistes == null){
            this.paisPlanAssistes = new ArrayList<SelectItem>();
            for (Integer x=0; x <= 4; x++) this.paisPlanAssistes.add(new SelectItem(x.toString(), x));
        }
        return paisPlanAssistes;
    }

    public void setPaisPlanAssistes(List<SelectItem> paisPlanAssistes) {
        this.paisPlanAssistes = paisPlanAssistes;
    }

    
    
    public List<SelectItem> getTreinamentos() {
        if(this.treinamentos == null){
            this.treinamentos = new ArrayList<SelectItem>();
            this.treinamentos.add(new SelectItem("0% - Menos de 120 horas", 0));
            this.treinamentos.add(new SelectItem("1% - Entre 120 e 240 horas", 1));
            this.treinamentos.add(new SelectItem("2% - Entre 240 e 360 horas", 2));
            this.treinamentos.add(new SelectItem("3% - Acima de 360 horas", 3));
         }
        return treinamentos;
    }

    public void setTreinamentos(List<SelectItem> treinamentos) {
        this.treinamentos = treinamentos;
    }

    public Integer getAnuenio() {
        return anuenio;
    }

    public void setAnuenio(Integer anuenio) {
        this.anuenio = anuenio;
    }

    public List<SelectItem> getAnuenios() {
        if(this.anuenios == null){
            this.anuenios = new ArrayList<SelectItem>();
            for (Integer x=0; x <= 35; x++) this.anuenios.add(new SelectItem(x.toString(), x));
        }
        return anuenios;
    }

    public void setAnuenios(List<SelectItem> anuenios) {
        this.anuenios = anuenios;
    }

    public List<SelectItem> getEscolaridades() {
        if(this.escolaridades == null){
            this.escolaridades = new ArrayList<SelectItem>();
            this.escolaridades.add(new SelectItem("Ensino Médio", 2));
            this.escolaridades.add(new SelectItem("Graduação ", 3));
            this.escolaridades.add(new SelectItem("Especialização - Lato Sensu", 4));
            this.escolaridades.add(new SelectItem("Mestrado - Stricto Sensu", 5));
            this.escolaridades.add(new SelectItem("Doutorado - Stricto Sensu", 6));
         }
        return escolaridades;
    }

    public void setEscolaridades(List<SelectItem> escolaridades) {
        this.escolaridades = escolaridades;
    }

    
    
    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public List<SelectItem> getCargos() {
         if(this.cargos == null){
            this.cargos = new ArrayList<SelectItem>();
            this.cargos.add(new SelectItem("Analista", 2));
            this.cargos.add(new SelectItem("Técnico", 1));
         }
        return cargos;
    }

    public List<SelectItem> getPadroes() {
        if(this.padroes == null){
            this.padroes = new ArrayList<SelectItem>();
            /*
            MPU e Judiciário adotam diferentes padrões de nomeclatura para promoção. No MPU, a mudança de classe (promoção) ocorre entre os níveis 3/4 e 8/9. 
            Já o judiciário manteve a estrutura anterior, em que classes A e B são compostas de 5 padrões (1/5 e 6/10), sendo que a classe C possui apenas três níveis
            TODO
            if(getOrgao()== 1){
                for(Integer x = 1; x <= 13; x++){
                    if(x<=3) this.padroes.add(new SelectItem("A" + x.toString(), x));
                    if(4<=x && x<=8) this.padroes.add(new SelectItem("B" + x.toString(), x));
                    if(9<=x && x<=13) this.padroes.add(new SelectItem("C" + x.toString(), x));
                }
            }else{
                for(Integer x = 1; x <= 13; x++){
                    if(x<=5) this.padroes.add(new SelectItem("A" + x.toString(), x));
                    if(6<=x && x<=10) this.padroes.add(new SelectItem("B" + x.toString(), x));
                    if(11<=x && x<=13) this.padroes.add(new SelectItem("C" + x.toString(), x));
            }       
         }
             */
           for(Integer x = 1; x <= 13; x++) this.padroes.add(new SelectItem(x.toString(), x));
        }
        return padroes;
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

    public boolean isPenosidade() {
        return penosidade;
    }

    public void setPenosidade(boolean penosidade) {
        this.penosidade = penosidade;
    }

    public boolean isInsalubridade() {
        return insalubridade;
    }

    public void setInsalubridade(boolean insalubridade) {
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
