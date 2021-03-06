
import java.io.Serializable;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import br.com.josebarbosa.conexao.ConectaMySQL;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josebarbosa
 */
@ManagedBean
@SessionScoped
public class FolhaBean implements Serializable{
    private List<SelectItem> planos;
    private List<SelectItem> padroes;
    private List<SelectItem> escolaridades;
    private List<SelectItem> treinamentos; 
    private List<Rubrica> rubricas; 
    private List<Rubrica> creditos;
    private List<Rubrica> debitos;
    
    private Integer plano;
    private Integer padrao; 
    private Integer cargo;
    private Integer escolaridade;
    private Integer treinamento;
    private Integer funcao;
    private Integer auxilioCrecheDependentes;
    private Integer impostoDeRendaDependentes; 
    private Integer planAssisteFilhosDependentes;
    private Integer anuenioAliquota;
    private Integer anoTabela; 
    
    private double funcaoValor;
    private double treinamentoValor;
    private double escolaridadeValor;
    private double vencimentoBasicoValor;
    private double gasValor;
    private double projetoValor;
    private double penosidadeValor; 
    private double anuenioValor; 
    private double planAssisteValor;
    private double auxilioAlimentacaoValor;
    private double auxilioCrecheValor; 
    private double vpniValor; 
    private double salarioBrutoValor;
    private double descontosValor;
    private double salarioLiquidoValor;
    private double basePSSValor;
    private double baseIRRFValor;
    private double pssAliquota;
    private double irrfAliquota; 
    private double pssValor;
    private double irrfValor;
    
    private boolean possuiFuncao;
    private boolean possuiGas;
    private boolean possuiProjeto;
    private boolean possuiPenosidade;
    private boolean possuiPlanAssiste; 
    
    private Rubrica rubrica;
    
    //Construtor
    public FolhaBean(){
        this.planos = new ArrayList<>();
        this.padroes = new ArrayList<>();
        this.treinamentos = new ArrayList<>();
        this.escolaridades = new ArrayList<>();
        this.plano = 2;
        this.padrao = 1;
        this.pssAliquota = 11;
        this.escolaridade = 2;
        this.anoTabela = Calendar.getInstance().get(Calendar.YEAR);
        this.treinamento = 0; 
        this.cargo = 2;
        this.pssAliquota = 11; 
        this.salarioBrutoValor = 0.0;
        this.basePSSValor = 0.0;
        this.pssValor = 0.0;
        this.baseIRRFValor = 0.0;
        this.anuenioAliquota = 0; 
        this.auxilioCrecheDependentes = 0;
        this.baseIRRFValor = 0;
        this.impostoDeRendaDependentes = 0;
        this.salarioLiquidoValor = 0;
        this.descontosValor = 0;
        
    }


    public Rubrica getRubrica() {
        return rubrica;
    }

    public void setRubrica(Rubrica rubrica) {
        this.rubrica = rubrica;
    }
    
    
    public void atualizaContraCheque() throws ClassNotFoundException, SQLException{
        creditos = new ArrayList<>();
        debitos = new ArrayList<>();
        calculaVencimentoBasico();
        rubrica = new Rubrica("Vencimento Básico - Padrão: " + this.getPadrao(), this.getVencimentoBasicoValor(), true, true, true);
        creditos.add(rubrica);
        //Adiciona a gaj, conforme o ano
        rubrica = new Rubrica("GAMPU ", calculaGAJ(this.getAnoTabela()), true, true, true);
        creditos.add(rubrica);
        //Adiciona a VPI de R$ 59,87
        rubrica = new Rubrica("VPI - Lei 10.698/2003", 59.87, true, true, true);
        creditos.add(rubrica);
        
        if(this.isPossuiFuncao()){
            rubrica = new Rubrica("Função/Cargo em Comissão", calculaFuncao(), false, true, true);
            creditos.add(rubrica);
        }
        
        if(this.isPossuiGas()){
            rubrica = new Rubrica("Gratificação de Atividade de Segurança", 
                    arredondar(this.getVencimentoBasicoValor() * 0.35), true, true, true);
            creditos.add(rubrica);
        }
        
        if(this.vpniValor != 0.0){
            rubrica = new Rubrica("VPNI - Vantagem Pessoal Nominalmente Identificável", 
                    this.getVpniValor(), true, true, true);
            /*
            Caso o usuário equivocadamente lance o valor da VPI (R$ 59,87), faz um alerta de
            que este valor já foi informado
            */
            if(this.getVpniValor()==59.87){
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Aviso", 
                        "Observe que o valor de R$ 59,87 referente à VPI do reajuste de 2003 já está considerada. Possui também realmente este valor de VPNI?"
                                + "Caso não possua, deixe em branco. "));
    
            }
            creditos.add(rubrica);
        }
        
        if(this.isPossuiPenosidade()){
            rubrica = new Rubrica("Adicional de Penosidade", 
                    arredondar(this.getVencimentoBasicoValor() * 0.20), false, true, true);
            creditos.add(rubrica);
        }
        
        if(this.anuenioAliquota > 0){
            rubrica = new Rubrica("Anuênios", 
                    arredondar(this.getVencimentoBasicoValor() * this.getAnuenioAliquota()/100), true, true, true);
            creditos.add(rubrica);
        }
        
        //Calcula adicional de qualificação, se superior ao requisito do cargo:
        if(this.getCargo() < this.getEscolaridade()){
            rubrica = new Rubrica("Adicional de Qualificação", calculaQualificacao(), true, true, true);
            creditos.add(rubrica);
        }
        
        //Calcula adicional de treinamento, se tiver horas
        if(this.getTreinamento() != 0){
            for(int i=0; i < this.getTreinamento(); i++){
                rubrica = new Rubrica("Adicional de Treinamento (1%)", arredondar(this.getVencimentoBasicoValor() * 0.01), false, true, true);
                creditos.add(rubrica);
            }
        }
        
        //auxilio alimentação
        rubrica = new Rubrica("Auxílio Alimentação", calculaAuxilioAlimentacao(), false, false, true);
        creditos.add(rubrica);
        
        //auxílio creche
        if(this.getAuxilioCrecheDependentes()>0){
            rubrica = new Rubrica("Auxílio Creche", calculaAuxilioCreche(), false, false, true);
            creditos.add(rubrica);
        }
        /*
        Cálculo dos débitos
        */
        //Após calcular todos os créditos, os primeiros cálculos consistem em apurar o salário bruto, e depois as bases de cálculo para PSS e IRRF, nesta ordem
        //(o valor do PSS interfere na base de cálculo do IRRF)
        this.setSalarioBrutoValor(calculaBruto());
        this.setBasePSSValor(calculaBasePSS());
        this.setPssValor(calculaPSS(this.getPssAliquota()));
        this.setBaseIRRFValor(calculaBaseIRRF());
        
        //zera a lista de débitos antes de iniciá-la
        debitos = new ArrayList<>();
        rubrica = new Rubrica("PSS - Contribuição Previdenciária", this.getPssValor(), false, false, false);
        debitos.add(rubrica);
        rubrica = new Rubrica("IRRF - Imposto de Renda", calculaIRRF(), false, false, false); 
        debitos.add(rubrica); 
        
        //fecha a tabela atualizando as últimas informações: total de descontos e salário líquido
        this.setIrrfValor(calculaIRRF());
        this.setDescontosValor(arredondar(this.getIrrfValor() + this.getPssValor()));
        this.setSalarioLiquidoValor(arredondar(this.getSalarioBrutoValor() - this.getDescontosValor()));
        
    }
    private double calculaIRRF(){
        double resposta = 0.0;
        //início copia uolhost php
        if(this.getAnoTabela() == 2012){
            this.baseIRRFValor = this.baseIRRFValor - this.getImpostoDeRendaDependentes() * 164.56;
            if(this.baseIRRFValor < 1637.11) return 0;
            if(1637.12 <= this.baseIRRFValor && this.baseIRRFValor <= 2453.50) return arredondar((this.baseIRRFValor * 0.075 - 122.78));
            if(2453.51 <= this.baseIRRFValor && this.baseIRRFValor <= 3271.38) return arredondar((this.baseIRRFValor * 0.150 - 306.80));
            if(3271.39 <= this.baseIRRFValor && this.baseIRRFValor <= 4087.65) return arredondar((this.baseIRRFValor * 0.225 - 552.15));
            return arredondar(this.baseIRRFValor * 0.275 - 756.53 );
        }
        //calcula imposto do ano de 2013
        if(this.getAnoTabela() == 2013){
            this.baseIRRFValor = this.baseIRRFValor - this.getImpostoDeRendaDependentes() * 171.97;
            if(this.baseIRRFValor < 1710.78) return 0;
                if(1710.79 <= this.baseIRRFValor && this.baseIRRFValor <= 2563.91) return arredondar((this.baseIRRFValor * 0.075 - 128.31));
                if(2563.92 <= this.baseIRRFValor && this.baseIRRFValor <= 3418.59) return arredondar((this.baseIRRFValor * 0.15 - 320.60));
                if(3418.60 <= this.baseIRRFValor && this.baseIRRFValor <= 4271.59) return arredondar((this.baseIRRFValor * 0.225 - 577));
                return arredondar((this.baseIRRFValor * 0.275 - 790.58 ));
        }


        //calcula imposto de 2014 . 
       if(this.getAnoTabela() == 2014){
            this.baseIRRFValor = this.baseIRRFValor - this.getImpostoDeRendaDependentes() * 179.71;
            if(this.baseIRRFValor < 1787.77) return 0;
            if(1787.78 <= this.baseIRRFValor && this.baseIRRFValor <= 2679.29) return arredondar((this.baseIRRFValor * 0.075 - 134.08));
            if(2679.30 <= this.baseIRRFValor && this.baseIRRFValor <= 3572.43) return arredondar((this.baseIRRFValor * 0.150 - 335.03));
            if(3572.44 <= this.baseIRRFValor && this.baseIRRFValor <= 4463.81) return arredondar((this.baseIRRFValor * 0.225 - 602.96));
            return arredondar((this.baseIRRFValor * 0.275 - 826.15));
        }
        
        //Considera uma correçao de 4,5% na tabela, embora ainda nao tenha sido formalmente adotada
        //Calcula o ano de 2015
        if(this.getAnoTabela() == 2015){
            this.baseIRRFValor = this.baseIRRFValor - this.getImpostoDeRendaDependentes() * 187.8;
            if(this.baseIRRFValor <= 1868.22) return 0;
            if(1868.23 <= this.baseIRRFValor && this.baseIRRFValor <= 2799.86) return arredondar((this.baseIRRFValor * 0.075 - 140.12));
            if(2799.87 <= this.baseIRRFValor && this.baseIRRFValor <= 3733.19) return arredondar((this.baseIRRFValor * 0.150 - 350.11));
            if(3733.2 <= this.baseIRRFValor && this.baseIRRFValor <= 4664.68) return arredondar((this.baseIRRFValor * 0.225 - 630.1));
            return arredondar((this.baseIRRFValor * 0.275 - 863.33));
        }
        //termino copia do uolhost php
        return resposta; 
    }
    private double calculaBaseIRRF(){
        //Esta função não vai considerar a dedução de dependentes, apenas calculará os valores sujeitos à tributação descontada a contribuição previdenciária
        double resposta = 0; 
        for(int i=0; i < creditos.size(); i++){
            if(creditos.get(i).isIncideImpostodeRenda())resposta += creditos.get(i).getValor();
        }
        return resposta - this.getPssValor();
    }
    private double calculaPSS(double aliquota){
        double teto = 0.0; 
        double aliquotaCalculo = 0.0;
        switch(this.getAnoTabela()){
            case 2014:
                teto = 4390.24;
                break;
            case 2013: 
                teto = 4159.00;
                break;
            case 2012: 
                teto = 3916.20;
                break;
            default:
                teto = 4390.24;
                break;
        }
        //calcula a aliquota acima do teto do regime geral
        if(aliquota == 0) aliquotaCalculo = 0;
        if(aliquota == 11) aliquotaCalculo = 0.11;
        if(aliquota > 0 && aliquota != 11) aliquotaCalculo = (8+aliquota)/200; 
        if(this.getBasePSSValor() < teto) return arredondar(this.getBasePSSValor()*0.11);
        else return arredondar((this.getBasePSSValor() - teto) * aliquotaCalculo + teto * 0.11);  
    }
    private double calculaBasePSS(){
        //zera o valor do salário bruto para percorrer a lista
        double resposta = 0; 
        for(int i=0; i < creditos.size(); i++){
            if(creditos.get(i).isIncidePrevidencia())resposta += creditos.get(i).getValor();
        }
        return resposta;
    
    }
    private double calculaBruto(){
        //zera o valor do salário bruto para percorrer a lista
        double resposta = 0; 
        for(int i=0; i < creditos.size(); i++){
            resposta += creditos.get(i).getValor();
        }
        return resposta;
    }
    private double calculaQualificacao(){
        switch(this.getEscolaridade()){
            case 3:
                return arredondar(this.getVencimentoBasicoValor() * 0.05);
            case 4:
                return arredondar(this.getVencimentoBasicoValor() * 0.075);
            case 5:
                return arredondar(this.getVencimentoBasicoValor() * 0.10);
            case 6:
                return arredondar(this.getVencimentoBasicoValor() * 0.125);
        }
        return 0; 
    }
    public double calculaGAJ(Integer ano){
        double gaj;
        switch(ano){
            case 2013:
                gaj = 0.62;
                break;
            case 2014:
                gaj = 0.752;
                break;
            case 2015:
                gaj = 0.9;
                break;
            default: 
                gaj = 0.5;
                break;            
        }
        return arredondar(this.getVencimentoBasicoValor() * gaj); 
    }
    
    
    public Integer getAnoTabela() {
        return anoTabela;
    }

    public void setAnoTabela(Integer anoTabela) {
        this.anoTabela = anoTabela;
    }

    
    public Integer getAnuenioAliquota() {
        return anuenioAliquota;
    }

    public void setAnuenioAliquota(Integer anuenioAliquota) {
        this.anuenioAliquota = anuenioAliquota;
    }

    public double getAnuenioValor() {
        return anuenioValor;
    }

    public void setAnuenioValor(double anuenioValor) {
        this.anuenioValor = anuenioValor;
    }

    public Integer getImpostoDeRendaDependentes() {
        return impostoDeRendaDependentes;
    }

    public void setImpostoDeRendaDependentes(Integer impostoDeRendaDependentes) {
        this.impostoDeRendaDependentes = impostoDeRendaDependentes;
    }

    public double getPssValor() {
        return pssValor;
    }

    public void setPssValor(double pssValor) {
        this.pssValor = pssValor;
    }

    public double getIrrfValor() {
        return irrfValor;
    }

    public void setIrrfValor(double irrfValor) {
        this.irrfValor = irrfValor;
    }
    

    public Integer getAuxilioCrecheDependentes() {
        return auxilioCrecheDependentes;
    }

    public void setAuxilioCrecheDependentes(Integer auxilioCrecheDependentes) {
        this.auxilioCrecheDependentes = auxilioCrecheDependentes;
    }

    public Integer getPlanAssisteFilhosDependentes() {
        return planAssisteFilhosDependentes;
    }

    public void setPlanAssisteFilhosDependentes(Integer planAssisteFilhosDependentes) {
        this.planAssisteFilhosDependentes = planAssisteFilhosDependentes;
    }

    public double getPlanAssisteValor() {
        return planAssisteValor;
    }

    public void setPlanAssisteValor(double planAssisteValor) {
        this.planAssisteValor = planAssisteValor;
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

    public double getVpniValor() {
        return vpniValor;
    }

    public void setVpniValor(double vpniValor) {
        this.vpniValor = vpniValor;
    }
    
    public List<Rubrica> getRubricas() {
        return rubricas;
    }

    public void setRubricas(List<Rubrica> rubricas) {
        this.rubricas = rubricas;
    }

    public List<Rubrica> getCreditos() {
        return creditos;
    }

    public void setCreditos(List<Rubrica> creditos) {
        this.creditos = creditos;
    }

    public List<Rubrica> getDebitos() {
        return debitos;
    }

    public void setDebitos(List<Rubrica> debitos) {
        this.debitos = debitos;
    }

    public Integer getFuncao() {
        return funcao;
    }

    public void setFuncao(Integer funcao) {
        this.funcao = funcao;
    }

    public double getFuncaoValor() {
        return funcaoValor;
    }

    public void setFuncaoValor(double funcaoValor) {
        this.funcaoValor = funcaoValor;
    }

    public double getTreinamentoValor() {
        return treinamentoValor;
    }

    public void setTreinamentoValor(double treinamentoValor) {
        this.treinamentoValor = treinamentoValor;
    }

    public double getEscolaridadeValor() {
        return escolaridadeValor;
    }

    public void setEscolaridadeValor(double escolaridadeValor) {
        this.escolaridadeValor = escolaridadeValor;
    }

    public double getVencimentoBasicoValor() {
        return vencimentoBasicoValor;
    }

    public void setVencimentoBasicoValor(double vencimentoBasicoValor) {
        this.vencimentoBasicoValor = vencimentoBasicoValor;
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

    public double getSalarioBrutoValor() {
        return salarioBrutoValor;
    }

    public void setSalarioBrutoValor(double salarioBrutoValor) {
        this.salarioBrutoValor = salarioBrutoValor;
    }

    public double getDescontosValor() {
        return descontosValor;
    }

    public void setDescontosValor(double descontosValor) {
        this.descontosValor = descontosValor;
    }

    public double getSalarioLiquidoValor() {
        return salarioLiquidoValor;
    }

    public void setSalarioLiquidoValor(double salarioLiquidoValor) {
        this.salarioLiquidoValor = salarioLiquidoValor;
    }

    public double getBasePSSValor() {
        return basePSSValor;
    }

    public void setBasePSSValor(double basePSSValor) {
        this.basePSSValor = basePSSValor;
    }

    public double getBaseIRRFValor() {
        return baseIRRFValor;
    }

    public void setBaseIRRFValor(double baseIRRFValor) {
        this.baseIRRFValor = baseIRRFValor;
    }

    public double getPssAliquota() {
        return pssAliquota;
    }

    public void setPssAliquota(double pssAliquota) {
        this.pssAliquota = pssAliquota;
    }

    public double getIrrfAliquota() {
        return irrfAliquota;
    }

    public void setIrrfAliquota(double irrfAliquota) {
        this.irrfAliquota = irrfAliquota;
    }

    public boolean isPossuiFuncao() {
        return possuiFuncao;
    }

    public void setPossuiFuncao(boolean possuiFuncao) {
        this.possuiFuncao = possuiFuncao;
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Atenção", "As funções não são cumulativas com certas gratificações. Fique atento às mudanças no formulário."));
    }

    public boolean isPossuiGas() {
        return possuiGas;
    }

    public void setPossuiGas(boolean possuiGas) {
        this.possuiGas = possuiGas;
    }

    public boolean isPossuiProjeto() {
        return possuiProjeto;
    }

    public void setPossuiProjeto(boolean possuiProjeto) {
        this.possuiProjeto = possuiProjeto;
    }

    public boolean isPossuiPenosidade() {
        return possuiPenosidade;
    }

    public void setPossuiPenosidade(boolean possuiPenosidade) {
        this.possuiPenosidade = possuiPenosidade;
    }

    public boolean isPossuiPlanAssiste() {
        return possuiPlanAssiste;
    }

    public void setPossuiPlanAssiste(boolean possuiPlanAssiste) {
        this.possuiPlanAssiste = possuiPlanAssiste;
    }

    public List<SelectItem> getEscolaridades() {
        return escolaridades;
    }

    public void setEscolaridades(List<SelectItem> escolaridades) {
        this.escolaridades = escolaridades;
    }

    public List<SelectItem> getTreinamentos() {
        return treinamentos;
    }

    public void setTreinamentos(List<SelectItem> treinamentos) {
        this.treinamentos = treinamentos;
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

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    
    public Integer getPlano() {
        return plano;
    }

    public void setPlano(Integer plano) {
        this.plano = plano;
    }

    public Integer getPadrao() {
        return padrao;
    }

    public void setPadrao(Integer padrao) {
        this.padrao = padrao;
    }
    
    

    public List<SelectItem> getPlanos() {
        return planos;
    }

    public void setPlanos(List<SelectItem> planos) {
        this.planos = planos;
    }

    public List<SelectItem> getPadroes() {
        return padroes;
    }

    public void setPadroes(List<SelectItem> padroes) {
        this.padroes = padroes;
    }
    
    public List<SelectItem> buscaPlanos() throws SQLException, ClassNotFoundException{
        planos = new ArrayList<>();
        //String sql = "select padrao from MPUvencimentos where plano=? group by padrao;";
        String sql = "select chave,nomeplano from MPUplanos order by vigenciatermino , iniciovigencia ";
        
        Connection con = new ConectaMySQL().conectaUOL();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        ResultSet rs;
        rs = pstmt.executeQuery();
        
        while(rs.next()){
            SelectItem item = new SelectItem(rs.getString("nomeplano"),rs.getInt("chave"));
            planos.add(item);
            
        }
        con.close();
        rs.close();
        pstmt.close();
        
        
        return planos; 
    }
    public List<SelectItem> buscaPadroes() throws SQLException, ClassNotFoundException{
        padroes = new ArrayList<>();
        String sql = "select padrao from MPUvencimentos where plano=? group by padrao;";
        
        
        Connection con = new ConectaMySQL().conectaUOL();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, this.getPlano());
        ResultSet rs;
        rs = pstmt.executeQuery();
        
        while(rs.next()){
            SelectItem item = new SelectItem(rs.getString("padrao").toString(),rs.getInt("padrao"));
            padroes.add(item);
            
        }
        
        con.close();
        rs.close();
        pstmt.close();
        
        return padroes; 
    }
    public List<SelectItem> buscaEscolaridades(){
        treinamentos = new ArrayList<>();
        SelectItem item;
        if(this.getCargo() == 2){
            
            item = new SelectItem("Ensino Médio", 2);
            treinamentos.add(item);
        }
        item = new SelectItem("Ensino Superior", 3);
        treinamentos.add(item);
        item = new SelectItem("Especialização (lato sensu)", 4);
        treinamentos.add(item);
        item = new SelectItem("Mestrado (stricto sensu)", 5);
        treinamentos.add(item);
        item = new SelectItem("Doutorado (stricto sensu)", 6);
        treinamentos.add(item);
            
        return treinamentos;
    }
    public List<SelectItem> buscaTreinamentos(){
        treinamentos = new ArrayList<>();
        SelectItem item;
        
        item = new SelectItem("0%", 0);
        treinamentos.add(item);
        item = new SelectItem("1% - 120 horas", 1);
        treinamentos.add(item);
        item = new SelectItem("2% - 240 horas", 2);
        treinamentos.add(item);
        item = new SelectItem("3% - 360 horas", 3);
        treinamentos.add(item);
            
        return treinamentos;
    }
    public boolean validaProjeto(){
        if(this.getCargo()==3 && this.possuiFuncao ==false)return true;
        return false; 
    }
    public List<SelectItem> previdenciaBuscarAliquotas(){
        List<SelectItem> aliquotas = new ArrayList<>();
        SelectItem item;
        
        item = new SelectItem("Regime Próprio (antigo)", 11);
        aliquotas.add(item);
        item = new SelectItem("Regime Geral - Não optante Funpresp", 0);
        aliquotas.add(item);
        item = new SelectItem("Funpresp 7,5%", 7);
        aliquotas.add(item);
        item = new SelectItem("Funpresp 8%", 8);
        aliquotas.add(item);
        item = new SelectItem("Funpresp 8,5%", 9);
        aliquotas.add(item);
        
        return aliquotas; 
    }
    
    public List<SelectItem> buscarAnuenios(){
        List<SelectItem> anuenios = new ArrayList<>();
        SelectItem item;
        for(int x=0; x <= 35; x++){            
            item = new SelectItem(Integer.toString(x)+"%", x);
            anuenios.add(item);
        }
        return anuenios;
    }

    private void calculaVencimentoBasico() throws ClassNotFoundException, SQLException {
        //Busca o valor do vencimento no banco de dados
        
        String sql = "SELECT vencimento FROM `MPUvencimentos` WHERE "
                + "escolaridade=? and padrao=? and plano=? and anovigencia <= ? order by anovigencia desc limit 1; ";
        
        
        Connection con = new ConectaMySQL().conectaUOL();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, this.getCargo());
        pstmt.setInt(2,this.getPadrao());
        pstmt.setInt(3, this.getPlano());
        pstmt.setInt(4, this.getAnoTabela());
        
        ResultSet rs;
        rs = pstmt.executeQuery();
        
        rs.next();
        this.setVencimentoBasicoValor(rs.getDouble("vencimento"));
        con.close();
        rs.close();
        pstmt.close();
                
    }
    
    public String segundaPagina() throws ClassNotFoundException, SQLException {
        //Chama uma "prévia" de contra-cheque
        //Até implementar o plano de 2006 (mais trabalhoso), vai permitir cálculos apenas dos planos vigentes.
       
        if(this.getPlano() > 1){
            //atualiza o construtor do ano caso o plano escolhido seja 3
            if(this.getPlano() == 3) this.setAnoTabela(2015);
            atualizaContraCheque();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Prévia calculada", 
                    "Abaixo, você já pode visualizar uma prévia do salário \"mínimo\" de um servidor do MPU pelas informações previamente prestadas. \n Para maiores detalhes, preencha agora detalhes sobre funções, escolaridade, treinamentos e gratificações. "));
            return "welcomePrimefaces";
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Plano indisponível", "No momento só está disponível a simulação referente à Lei 12773/2012, que reajusta a GAJ para 90%!"));
            return "index";
        }
        
    }
    
    public double arredondar(double valor){
        BigDecimal valorArredondado = new BigDecimal(valor).setScale(2, RoundingMode.UP);
        return valorArredondado.doubleValue();
    }
    
    public List<SelectItem> buscarAnos(){
        SelectItem item; 
        List<SelectItem> anos = new ArrayList<>();
        switch(this.getPlano()){
            case 1:
                for(int x = 2006; x <= 2012; x++){
                     item = new SelectItem(Integer.toString(x), x);
                     anos.add(item);
                }
                return anos; 
            case 2:
                for(int x = 2013; x <= 2015; x++){
                     item = new SelectItem(Integer.toString(x), x);
                     anos.add(item);
                }
                return anos; 
            case 3:
                for(int x = 2015; x <= 2015; x++){
                     item = new SelectItem(Integer.toString(x), x);
                     anos.add(item);
                }
                return anos; 
        }
        return anos;
    }

    private double calculaAuxilioAlimentacao() throws SQLException, ClassNotFoundException {
        String sql = "SELECT valor FROM `MPUauxilios` WHERE "
                + "ano <= ? and nivel=0 and orgao=1 order by ano desc, mes desc limit 1; ";
        
        
        Connection con = new ConectaMySQL().conectaUOL();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, this.getAnoTabela());
        
        ResultSet rs;
        rs = pstmt.executeQuery();
        
        rs.next();
        this.setAuxilioAlimentacaoValor(rs.getDouble("valor"));
        con.close();
        rs.close();
        pstmt.close();
        
        return this.getAuxilioAlimentacaoValor();
    }
    public void aumentaAno() throws ClassNotFoundException, SQLException{
        if(this.getAnoTabela() < 2015 && this.getPlano() == 2) this.setAnoTabela(this.getAnoTabela() + 1);
        else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro", 
                        "Não é possível aumentar. "));
        }
        atualizaContraCheque();
    }
    public void diminuiAno() throws ClassNotFoundException, SQLException{
        if(this.getAnoTabela() > 2013 && this.getPlano() == 2) this.setAnoTabela(this.getAnoTabela() - 1);
        else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro", 
                        "Não é possível diminuir. "));
        }
        atualizaContraCheque();
    }
    public void aumentaPadrao() throws ClassNotFoundException, SQLException{
        if(this.getPadrao() < 13) this.setPadrao(this.getPadrao() + 1);
        else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro", 
                        "Não é possível aumentar. "));
        }
        atualizaContraCheque();
    }
    public void diminuiPadrao() throws ClassNotFoundException, SQLException{
        if(this.getPadrao() > 1) this.setPadrao(this.getPadrao() - 1);
        else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro", 
                        "Não é possível diminuir. "));
        }
        atualizaContraCheque();
    }

    private double calculaFuncao() throws ClassNotFoundException, SQLException {
        /*
        TODO
        Caso haja um parcelamento no reajuste das funções, este algoritmo deve mudar. 
        A consulta sql está bastante simples porque há apenas um valor de função para um nível num determinado ano, o que não é a realidade 
        quando houve a implementação da lei 11415, por exemplo.
        */
        String sql = "SELECT valor FROM `MPUfuncoes` WHERE "
                + "nivel = ? and plano=? limit 1; ";
        
        
        Connection con = new ConectaMySQL().conectaUOL();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, this.getFuncao());
        pstmt.setInt(2, this.getPlano());
        ResultSet rs;
        rs = pstmt.executeQuery();
        
        rs.next();
        this.setFuncaoValor(rs.getDouble("valor"));
        con.close();
        rs.close();
        pstmt.close();
        
        return this.getFuncaoValor();
    }
    
    private double calculaAuxilioCreche() throws SQLException, ClassNotFoundException {
        String sql = "SELECT valor FROM `MPUauxilios` WHERE "
                + "ano <= ? and nivel=1 and orgao=1 order by ano desc, mes desc limit 1; ";
        
        
        Connection con = new ConectaMySQL().conectaUOL();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, this.getAnoTabela());
        
        ResultSet rs;
        rs = pstmt.executeQuery();
        
        rs.next();
        this.setAuxilioCrecheValor((rs.getDouble("valor")*this.getAuxilioCrecheDependentes()));
        con.close();
        rs.close();
        pstmt.close();
        
        return this.getAuxilioCrecheValor();
    }
    
}
