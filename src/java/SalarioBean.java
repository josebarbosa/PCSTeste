
import br.com.josebarbosa.conexao.ConectaMySQL;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        this.cargo = 2; 
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
    
    /*
    Aqui ficarão as variáveis que serão os valores das verbas salariais, que serão apresentadas na página.
    */
    private double vencimentoBasico;
    private double gampuValor;
    private double funcaoValor;
    private double formacaoAcademicaValor;
    private double treinamentosValor;
    private double anueniosValor; 
    private double previdenciaValor; 
    private double planAssisteTitularValor;
    private double planAssisteConjugeValor;
    private double planAssisteFilhosValor;
    private double planAssistePaisValor; 
    private double auxilioAlimentacaoValor;
    private double auxilioCrecheValor; 
    private double vpiValor = 59.87;
    private double vpniValor; 
    private double gasValor;
    private double projetoValor;
    private double penosidadeValor;
    private double insalubridadeValor; 
    private double indenizacoesValor;
    private double salarioBrutoValor;
    private double outrosDescontosValor;
    private double impostoDeRendaValor;
    private double descontosTotalValor;
    private double salarioLiquidoValor; 

    public double getVencimentoBasico() {
        return vencimentoBasico;
    }

    public void setVencimentoBasico(double vencimentoBasico) {
        this.vencimentoBasico = vencimentoBasico;
    }

    public double getGampuValor() {
        return gampuValor;
    }

    public void setGampuValor(double gampuValor) {
        this.gampuValor = gampuValor;
    }

    public double getFuncaoValor() {
        return funcaoValor;
    }

    public void setFuncaoValor(double funcaoValor) {
        this.funcaoValor = funcaoValor;
    }

    public double getFormacaoAcademicaValor() {
        return formacaoAcademicaValor;
    }

    public void setFormacaoAcademicaValor(double formacaoAcademicaValor) {
        this.formacaoAcademicaValor = formacaoAcademicaValor;
    }

    public double getTreinamentosValor() {
        return treinamentosValor;
    }

    public void setTreinamentosValor(double treinamentosValor) {
        this.treinamentosValor = treinamentosValor;
    }

    public double getAnueniosValor() {
        return anueniosValor;
    }

    public void setAnueniosValor(double anueniosValor) {
        this.anueniosValor = anueniosValor;
    }

    public double getPrevidenciaValor() {
        return previdenciaValor;
    }

    public void setPrevidenciaValor(double previdenciaValor) {
        this.previdenciaValor = previdenciaValor;
    }

    public double getPlanAssisteTitularValor() {
        return planAssisteTitularValor;
    }

    public void setPlanAssisteTitularValor(double planAssisteTitularValor) {
        this.planAssisteTitularValor = planAssisteTitularValor;
    }

    public double getPlanAssisteConjugeValor() {
        return planAssisteConjugeValor;
    }

    public void setPlanAssisteConjugeValor(double planAssisteConjugeValor) {
        this.planAssisteConjugeValor = planAssisteConjugeValor;
    }

    public double getPlanAssisteFilhosValor() {
        return planAssisteFilhosValor;
    }

    public void setPlanAssisteFilhosValor(double planAssisteFilhosValor) {
        this.planAssisteFilhosValor = planAssisteFilhosValor;
    }

    public double getPlanAssistePaisValor() {
        return planAssistePaisValor;
    }

    public void setPlanAssistePaisValor(double planAssistePaisValor) {
        this.planAssistePaisValor = planAssistePaisValor;
    }

    public double getAuxilioAlimentacaoValor() {
        return auxilioAlimentacaoValor;
    }

    public void setAuxilioAlimentacaoValor(double auxilioAlimentacaoValor) {
        this.auxilioAlimentacaoValor = auxilioAlimentacaoValor;
    }

    public double getAuxilioCrecheValor() {
        return auxilioCrecheValor;
    }

    public void setAuxilioCrecheValor(double auxilioCrecheValor) {
        this.auxilioCrecheValor = auxilioCrecheValor;
    }

    public double getVpiValor() {
        return vpiValor;
    }

    public void setVpiValor(double vpiValor) {
        this.vpiValor = vpiValor;
    }

    public double getVpniValor() {
        return vpniValor;
    }

    public void setVpniValor(double vpniValor) {
        this.vpniValor = vpniValor;
    }

    public double getGasValor() {
        return gasValor;
    }

    public void setGasValor(double gasValor) {
        this.gasValor = gasValor;
    }

    public double getProjetoValor() {
        return projetoValor;
    }

    public void setProjetoValor(double projetoValor) {
        this.projetoValor = projetoValor;
    }

    public double getPenosidadeValor() {
        return penosidadeValor;
    }

    public void setPenosidadeValor(double penosidadeValor) {
        this.penosidadeValor = penosidadeValor;
    }

    public double getInsalubridadeValor() {
        return insalubridadeValor;
    }

    public void setInsalubridadeValor(double insalubridadeValor) {
        this.insalubridadeValor = insalubridadeValor;
    }

    public double getIndenizacoesValor() {
        return indenizacoesValor;
    }

    public void setIndenizacoesValor(double indenizacoesValor) {
        this.indenizacoesValor = indenizacoesValor;
    }

    public double getSalarioBrutoValor() {
        return salarioBrutoValor;
    }

    public void setSalarioBrutoValor(double salarioBrutoValor) {
        this.salarioBrutoValor = salarioBrutoValor;
    }

    public double getOutrosDescontosValor() {
        return outrosDescontosValor;
    }

    public void setOutrosDescontosValor(double outrosDescontosValor) {
        this.outrosDescontosValor = outrosDescontosValor;
    }

    public double getImpostoDeRendaValor() {
        return impostoDeRendaValor;
    }

    public void setImpostoDeRendaValor(double impostoDeRendaValor) {
        this.impostoDeRendaValor = impostoDeRendaValor;
    }

    public double getDescontosTotalValor() {
        return descontosTotalValor;
    }

    public void setDescontosTotalValor(double descontosTotalValor) {
        this.descontosTotalValor = descontosTotalValor;
    }

    public double getSalarioLiquidoValor() {
        return salarioLiquidoValor;
    }

    public void setSalarioLiquidoValor(double salarioLiquidoValor) {
        this.salarioLiquidoValor = salarioLiquidoValor;
    }
    
    
    //Esta lista conterá todos os valores e depois serão feitos os cálculos com base nas somas
    private List<Rubrica> rubricas; 
    
    /*
    Método para adicionar todas as rubricas numa lista e depois serem retornadas. 
    */
    public List<Rubrica> calculaBruto(){
        //escrever aqui um método que vai adicionando cada verba ao salário.
        //gerar uma linha para cada  verba
        return rubricas; 
    }
    /*
    função que busca o cargo e nível de referência no banco de dados e retorna o valor do vencimento básico
    */
    public double calculaVencimentoBasico() throws SQLException, ClassNotFoundException{
        //Faz a conexão no banco e procura na tabela vencimento pelo 
        
        /*
        exemplo de sql que ilustra o funcionamenot
        SELECT valor FROM `vencimentosMPU` WHERE id_escolaridade=2 and nivel<=9 and ano<=2013 order by ano desc, mes desc, nivel desc limit 1; 
        */
        Connection conexao = new ConectaMySQL().conectaUOL();
        String sql = "SELECT valor FROM `vencimentosMPU` WHERE id_escolaridade=? and nivel<=? and ano<=? order by ano desc, mes desc, nivel desc limit 1";
        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, this.getCargo());
            stmt.setInt(2, this.getPadrao());
            stmt.setInt(3, this.getAno());
            ResultSet rs = stmt.executeQuery();

            rs.next();
            this.vencimentoBasico = rs.getDouble(1);
            //apenas para teste, imprimir o valor buscado no banco
            System.out.println(this.getVencimentoBasico());
            rs.close();
            //stmt.execute();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        
        // executa um select
        
        conexao.close();
        
        return vencimentoBasico; 
    }

    /*
    Conexão de banco de dados??
    */
       
    public List<Rubrica> getRubricas() {
        return rubricas;
    }

    public void setRubricas(List<Rubrica> rubricas) {
        this.rubricas = rubricas;
    }

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
            this.cargos.add(new SelectItem("Analista", 3));
            this.cargos.add(new SelectItem("Técnico", 2));
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
           for(Integer x = 1; x <= 15; x++) this.padroes.add(new SelectItem(x.toString(), x));
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
            this.previdenciaAliquotas.add(new SelectItem("Funpresp: 7,5%", 7));
            this.previdenciaAliquotas.add(new SelectItem("Funpresp: 8,0%", 8));
            this.previdenciaAliquotas.add(new SelectItem("Funpresp: 8,5%", 9));
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
