
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
import java.sql.ResultSet;
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
    
    private double funcaoValor;
    private double treinamentoValor;
    private double escolaridadeValor;
    private double vencimentoBasicoValor;
    private double gasValor;
    private double projetoValor;
    private double penosidadeValor; 
    private double salarioBrutoValor;
    private double descontosValor;
    private double salarioLiquidoValor;
    private double basePSSValor;
    private double baseIRRFValor;
    private double pssAliquota;
    private double irrfAliquota; 
    
    
    private boolean possuiFuncao;
    private boolean possuiGas;
    private boolean possuiProjeto;
    private boolean possuiPenosidade;
    private boolean possuiPlanAssiste; 
    
    
    
    
    
    public FolhaBean(){
        this.planos = new ArrayList<>();
        this.padroes = new ArrayList<>();
        this.treinamentos = new ArrayList<>();
        this.escolaridades = new ArrayList<>();
        this.plano = 2;
        this.padrao = 1;
        this.pssAliquota = 11;
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
        System.out.println("Passou aqui, cargo: " + this.getCargo());
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
    
}
