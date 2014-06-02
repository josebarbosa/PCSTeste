
import br.com.josebarbosa.conexao.ConectaMySQL;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@ManagedBean
@ViewScoped
public class SalarioBean implements Serializable{
    
    /*
    * As variáveis do formulário serão inteiros (para representar chaves) ou doubles (para representar valores ou índices), e serão carregadas a partir de listas
    do tipo SelectItem, que conterão strings de identificação. 
    */
    private Integer orgao;
    private List<SelectItem> orgaos;
    
    /*
    Classe SubLista será uma gambiarra para facilitar a implementação do subtable.
    Nela haverá um ano que será um identificador, e uma lista de subrubricas a serem apresentadas. 
    */
    public class Rubrica implements Serializable{
    
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

        public Rubrica(double valor, Integer anoReferencia, String nomeRubrica, boolean incideImpostoRenda, boolean incidePrevidencia, boolean ehCredito) {
            this.valor = valor;
            this.anoReferencia = anoReferencia;
            this.nomeRubrica = nomeRubrica;
            this.incideImpostoRenda = incideImpostoRenda;
            this.incidePrevidencia = incidePrevidencia;
            this.ehCredito = ehCredito;
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
    public class SubLista implements Serializable{
        private Integer anoLista;
        private double salarioBruto;
        private double descontos;
        private double salarioLiquido; 
        private List<Rubrica> sublistaRubricas = new ArrayList<Rubrica>(); ; 
        
        
        public SubLista(){
            
        }

        public SubLista(Integer anoLIsta, List<Rubrica> sublistaRubricas) {
            this.anoLista = anoLista;
            this.salarioBruto = 0;
            this.salarioLiquido = 0;
            this.descontos = 0;
        }

        public double getSalarioBruto() {
            return salarioBruto;
        }

        public void setSalarioBruto(double salarioBruto) {
            this.salarioBruto = salarioBruto;
        }

        public double getDescontos() {
            return descontos;
        }

        public void setDescontos(double descontos) {
            this.descontos = descontos;
        }

        public double getSalarioLiquido() {
            return salarioLiquido;
        }

        public void setSalarioLiquido(double salarioLiquido) {
            this.salarioLiquido = salarioLiquido;
        }

        public Integer getAnoLista() {
            return anoLista;
        }

        public void setAnoLista(Integer anoLista) {
            this.anoLista = anoLista;
        }
       

        public List<Rubrica> getSublistaRubricas() {
            return sublistaRubricas;
        }

        public void setSublistaRubricas(List<Rubrica> sublistaRubricas) {
            this.sublistaRubricas = sublistaRubricas;
        }
        
        
    } 
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
        this.salarioBrutoValor = 0;
        this.salarioLiquidoValor = 0;
        this.baseDeCalculoIRRF = 0;
        this.baseDeCalculoPSS = 0;
        this.rubricas = new ArrayList<>();
        this.sublistas = new ArrayList<>();
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
    private double baseDeCalculoPSS;
    private double baseDeCalculoIRRF; 
    private double salarioBrutoFinal; 
    private double outrosDescontosValor;
    private double impostoDeRendaValor;
    private double descontosTotalValor;
    private double salarioLiquidoValor; 
    
    private List<Rubrica> rubricas;
    List<SubLista> sublistas;
    
    
    //Esta lista conterá todos os valores e depois serão feitos os cálculos com base nas somas
   
    
    /*
    Método para adicionar todas as rubricas numa lista e depois serem retornadas. 
    */
    public void geraTabela() throws SQLException, ClassNotFoundException{
        sublistas = new ArrayList<>();
        SubLista sublista = new SubLista();
        int anoInserir = 2012;
        //chama calcula Bruto para trazer todas as referências salariais
        calculaBruto(); 
        //Agora quebra a lista, para organizá-la de acordo com o ano
        /*for(int x=0; x < 4; x++){
            List<Rubrica> subListaTemp = new ArrayList<>(); 
            for(int y = 0; y < rubricas.size(); y++){
                if((x + anoInserir)==rubricas.get(y).getAnoReferencia()) {
                    subListaTemp.add(rubricas.get(y));
                }
            }
            System.out.println("Passou aqui!!!!!!! " +(x + anoInserir) );
            sublista.setAnoLista(x + anoInserir);
            sublista.setSublistaRubricas(subListaTemp);
            
            
            sublistas.add(sublista);
            
        }
        return sublistas; */
    }
    
    public List<Rubrica> calculaBruto() throws SQLException, ClassNotFoundException{
        //escrever aqui um método que vai adicionando cada verba ao salário.
        //gerar uma linha para cada  verba
        
       
       
                
        for(int x = 0; x <=3; x++){
             //zera a lista, se ela já tiver sido criada
            
            this.ano = 2012 + x; 
            
            rubricas.add(calculaVencimentoBasico());
            rubricas.add(calculaGampu());
            /*
            Avaliar se possui ou não função. 
            Se possuir, não podem ser calculadas verbas de GAS e Gratificação de Projeto
            Já se não possuir, não adiciona à lista, mas também permite as referidas gratificações, se for o caso.            
            */
            if(this.getFuncao()!=0)rubricas.add(calculaFuncao());
            else{
                if(this.isGas()) rubricas.add(calculaGasGae());
                if(this.isProjeto()) rubricas.add(calculaProjeto());
            }
            
            //Calcula o adicional de qualificação, se o servidor tiver graduação maior que a exigida para o cargo e, no caso do judiciário, o servidor técnico possuir mais do que a graduação
            if((this.orgao ==1 && this.escolaridade > this.cargo) || (this.orgao ==2 && this.escolaridade >=4)) rubricas.add(calculaAdicionalQualificacao());
            
            if(this.getTreinamento()!=0) rubricas.add(calculaAdicionalTreinamento());
            if(this.getAnuenio()!=0) rubricas.add(calculaAnuenio());
            if(this.isPenosidade()) rubricas.add(calculaPenosidade());
            if(this.isInsalubridade()) rubricas.add(calculaInsalubridade());
            
            Rubrica vpni = new Rubrica();
            vpni.setNomeRubrica("Vantagens Pessoais");
            vpni.setAnoReferencia(this.getAno());
            vpni.setValor(59.87 + this.getVpni());
            rubricas.add(vpni);
            
            rubricas.add(calculaAuxilioIndenizacoes());
            //zera o salário bruto para fazer a conta
            this.salarioBrutoValor = 0; 
            this.salarioBrutoFinal = 0;
            this.baseDeCalculoIRRF = 0;
            this.baseDeCalculoPSS = 0;
            rubricas.add(calculaBrutoValor());
            rubricas.add(calculaPSS());
            
            Rubrica irrf = new Rubrica();
            irrf.setNomeRubrica("IRRF - Imposto de Renda Retido na Fonte");
            irrf.setAnoReferencia(this.getAno());
            irrf.setIncideImpostoRenda(false);
            irrf.setIncidePrevidencia(false);
            irrf.setValor(arredondar(calculaIRRF(this.getBaseDeCalculoIRRF(), this.getDependentesIRRF())));
            rubricas.add(irrf);
            
            Rubrica salarioLiquido = new Rubrica();
            salarioLiquido.setNomeRubrica("Salário Líquido");
            salarioLiquido.setAnoReferencia(this.getAno());
            salarioLiquido.setIncideImpostoRenda(false);
            salarioLiquido.setIncidePrevidencia(false);
            salarioLiquido.setValor(arredondar(this.getSalarioBrutoFinal() - this.getPrevidenciaValor() - this.getIRRF()));            
            rubricas.add(salarioLiquido);
            
        }
        return rubricas; 
    }
    public double calculaIRRF(double base, Integer dependentes){
        double resposta = 0.0;
        if(dependentes > 0){
            switch(ano){
                case 2012:
                    base = base - dependentes * 164.56;
                    break;
                case 2013:
                    base = base - dependentes * 171.97;
                    break;
                case 2014:
                    base = base - dependentes * 179.71;
                    break;
                case 2015:
                    base = base - dependentes * 187.80;
                    break;
            }
        }
        base = base - this.getPrevidenciaValor();
        resposta = calculaImpostodeRenda(this.getAno(), base);
        
        //Deduz dependentes do Imposto de Renda
        arredondar(resposta);
        this.setIRRF(resposta);
        return resposta;         
    }
    
    public double calculaImpostodeRenda(Integer ano, double base){
        double resposta = 0.0;
        switch(ano){
            case 2012:
                if(base <1637.12) return 0;
                if(base >= 1637.12 && base <= 2453.50) return (base * 0.075 - 122.78);
                if(base >= 2453.51 && base <= 3271.38) return (base * 0.150 - 306.80);
                if(base >= 3271.39 && base <= 4087.65) return (base * 0.225 - 552.15);
                if(base >4087.66) return (base * 0.275 - 756.53);
            case 2013:
                if(base <1710.78) return 0;
                if(base >= 1710.79 && base <= 2563.91) return (base * 0.075 - 128.31);
                if(base >= 2563.92 && base <= 3418.59) return (base * 0.150 - 320.60);
                if(base >= 3418.60 && base <= 4271.59) return (base * 0.225 - 577.00);
                if(base >4271.59) return (base * 0.275 - 790.58);
            case 2014:
                if(base <1787.77) return 0;
                if(base >= 1787.78 && base <= 2679.29) return (base * 0.075 - 134.08);
                if(base >= 2679.30 && base <= 3572.43) return (base * 0.150 - 335.03);
                if(base >= 3572.44 && base <= 4463.81) return (base * 0.225 - 602.96);
                if(base >4463.81) return (base * 0.275 - 826.15);
            case 2015:
                if(base <1868.22) return 0;
                if(base >= 1868.23 && base <= 2799.86) return (base * 0.075 - 140.12);
                if(base >= 2799.87 && base <= 3733.19) return (base * 0.150 - 350.11);
                if(base >= 3733.20 && base <= 4664.68) return (base * 0.225 - 630.10);
                if(base >4464.68) return (base * 0.275 - 863.33);
                 
        }
        return resposta; 
    }
    
    public Rubrica calculaPSS(){
        Rubrica pss = new Rubrica();
        pss.setAnoReferencia(this.getAno());
        pss.setEhCredito(false);
        pss.setIncideImpostoRenda(false);
        pss.setIncidePrevidencia(false);
        Double teto = 0.0;
        Double aliquota = this.getPrevidenciaAliquota();
        
        
        switch(this.getAno()){
                case 2012:
                    teto = 3916.20;
                    break;
                case 2013:
                    teto = 4159.0;
                    break;
                    //Não existe teto definido para 2015. Quando for estipulado, o programa deverá ser atualizado
                default:
                    teto = 4390.24;
                    break;    
        }
        this.baseDeCalculoPSS = 0;
        this.baseDeCalculoIRRF = 0;
        for(int x=(calculaX(this.getAno(), this.rubricas.size())); x < this.rubricas.size(); x++) { 
            if(rubricas.get(x).isIncidePrevidencia()) this.baseDeCalculoPSS = this.baseDeCalculoPSS + rubricas.get(x).getValor();
            System.out.println("Ano: " + this.getAno() + " base PSS " + this.baseDeCalculoPSS);
            if(rubricas.get(x).isIncideImpostoRenda()) this.baseDeCalculoIRRF = this.baseDeCalculoIRRF + rubricas.get(x).getValor();
            
        }
       
        this.setPrevidenciaValor(arredondar((teto * 0.11)+(((this.getBaseDeCalculoPSS()-teto)*aliquota/100))));
        
        /*
        Alíquotas passadas no formulário
        0: Considera apenas teto do regime geral
        7: Equivale a 7,5% de contribuição
        8: Equivale a 8% de contribuição
        8,5%: Equivale a 8,5% de contribuição
        11: contribuição integral
        */
        pss.setNomeRubrica("Previdência");
        pss.setValor(this.getPrevidenciaValor());
        this.setPss(this.getPrevidenciaValor());
        /*
        Aproveita as variáveis para já definir a base de cálculo da previdência para 
        */
        
        return pss; 
        
    }
    
    public Rubrica calculaBrutoValor(){
        Rubrica salarioBruto = new Rubrica();
        salarioBruto.setAnoReferencia(this.getAno());
        //não entrará nos cálculos de imposto de renda ou previdência para evitar duplicidades
        salarioBruto.setIncideImpostoRenda(false);
        salarioBruto.setIncidePrevidencia(false);
        salarioBruto.setNomeRubrica("Salário Bruto: ");    
        this.salarioBrutoValor = 0;
        
        //A função calculaX posiciona para começar na lista de acordo com o ano. Melhorar esta característica futuramente. 
        for(int x=(calculaX(this.getAno(), this.rubricas.size())); x < this.rubricas.size(); x++) { 
            
            this.salarioBrutoValor = this.salarioBrutoValor + rubricas.get(x).getValor();
            if(rubricas.get(x).isIncideImpostoRenda()) this.baseDeCalculoIRRF = this.baseDeCalculoIRRF + rubricas.get(x).getValor();
            if(rubricas.get(x).isIncidePrevidencia()) this.baseDeCalculoPSS = this.baseDeCalculoPSS + rubricas.get(x).getValor();

        }
        salarioBruto.setValor(arredondar(this.salarioBrutoValor));
        this.setSalarioBrutoFinal(0.0);
        this.setSalarioBrutoFinal(this.salarioBrutoValor);
        
        //zera salário bruto para garantir
        this.salarioBrutoValor = 0; 
        
        
        return salarioBruto;                
    }
    
    public Rubrica calculaAuxilioIndenizacoes() throws ClassNotFoundException, SQLException{
        Connection conexao = new ConectaMySQL().conectaUOL();
        String sql = "SELECT valor FROM `auxiliosMPU` WHERE nivel=0 and orgao=? and ano<=? order by ano desc, mes desc limit 1";
        
        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, this.getOrgao());
            stmt.setInt(2, this.getAno());
            ResultSet rs = stmt.executeQuery();

            rs.next();
            this.auxilioAlimentacaoValor = rs.getDouble(1);
            //apenas para teste, imprimir o valor buscado no banco
            rs.close();
            //stmt.execute();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        
        Rubrica auxilioAlimentacao = new Rubrica();
        auxilioAlimentacao.setNomeRubrica("Auxílio Alimentação");
        auxilioAlimentacao.setAnoReferencia(this.getAno());
        auxilioAlimentacao.setValor(this.auxilioAlimentacaoValor);
        auxilioAlimentacao.setIncidePrevidencia(false);
        auxilioAlimentacao.setIncideImpostoRenda(false);
        auxilioAlimentacao.setIncidePrevidencia(false);
        
        if(this.getDependentesAuxilioCreche()!=0){
            sql =  "SELECT valor FROM `auxiliosMPU` WHERE nivel=1 and orgao=? and ano<=? order by ano desc, mes desc limit 1";
        
            try{
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, this.getOrgao());
                stmt.setInt(2, this.getAno());
                ResultSet rs = stmt.executeQuery();

                rs.next();
                this.auxilioCrecheValor = rs.getDouble(1);
                //apenas para teste, imprimir o valor buscado no banco
                rs.close();
                //stmt.execute();
                stmt.close();
                
                auxilioAlimentacao.setNomeRubrica("Indenizações: Alimentação e Creche");
                this.setIndenizacoesValor(this.auxilioAlimentacaoValor + this.auxilioCrecheValor * this.getDependentesAuxilioCreche());
                auxilioAlimentacao.setValor(this.getIndenizacoesValor());
                
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        conexao.close();
        
        return auxilioAlimentacao; 
}
    
    public Rubrica calculaGasGae(){
        Rubrica adicionalTreinamentoValor = new Rubrica();
        adicionalTreinamentoValor.setNomeRubrica("GAS/GAE");
        adicionalTreinamentoValor.setAnoReferencia(this.getAno());
        adicionalTreinamentoValor.setValor(arredondar(0.35 * this.vencimentoBasico));
        
         return adicionalTreinamentoValor;
    }
    public Rubrica calculaPenosidade(){
        Rubrica adicionalTreinamentoValor = new Rubrica();
        adicionalTreinamentoValor.setNomeRubrica("Adicional de Penosidade");
        adicionalTreinamentoValor.setAnoReferencia(this.getAno());
        adicionalTreinamentoValor.setValor(arredondar(0.2 * this.vencimentoBasico));
        
         return adicionalTreinamentoValor;
    }
    public Rubrica calculaInsalubridade(){
        Rubrica adicionalTreinamentoValor = new Rubrica();
        adicionalTreinamentoValor.setNomeRubrica("Adicional de Penosidade");
        adicionalTreinamentoValor.setAnoReferencia(this.getAno());
        adicionalTreinamentoValor.setValor(arredondar(0.1 * this.vencimentoBasico));
        
         return adicionalTreinamentoValor;
    }
    public Rubrica calculaProjeto(){
        Rubrica adicionalTreinamentoValor = new Rubrica();
        adicionalTreinamentoValor.setNomeRubrica("Gratificação de Projeto");
        adicionalTreinamentoValor.setAnoReferencia(this.getAno());
        adicionalTreinamentoValor.setValor(arredondar(0.35 * this.vencimentoBasico));
        
         return adicionalTreinamentoValor;
    }
    public Rubrica calculaAnuenio(){
        Rubrica adicionalTreinamentoValor = new Rubrica();
        adicionalTreinamentoValor.setNomeRubrica("Anuênios");
        adicionalTreinamentoValor.setAnoReferencia(this.getAno());
        adicionalTreinamentoValor.setValor(arredondar(this.getAnuenio() * (this.vencimentoBasico/100)));
        
         return adicionalTreinamentoValor;
    }
    public Rubrica calculaAdicionalTreinamento(){
        Rubrica adicionalTreinamentoValor = new Rubrica();
        adicionalTreinamentoValor.setNomeRubrica("Adicional de Treinamento");
        adicionalTreinamentoValor.setAnoReferencia(this.getAno());
        adicionalTreinamentoValor.setValor(arredondar(this.getTreinamento() * (this.vencimentoBasico/100)));
        
        return adicionalTreinamentoValor;
    }
    
    public Rubrica calculaAdicionalQualificacao(){
        Rubrica adicionalQualificacaoValor = new Rubrica();
        adicionalQualificacaoValor.setNomeRubrica("Adicional de Qualificação");
        adicionalQualificacaoValor.setAnoReferencia(this.getAno());
        switch(this.getEscolaridade()){
            case 3:
                adicionalQualificacaoValor.setValor(arredondar(this.vencimentoBasico * 0.05));
                break;
            case 4:
                 adicionalQualificacaoValor.setValor(arredondar(this.vencimentoBasico * 0.075));
                break;
            case 2014:
                adicionalQualificacaoValor.setValor(arredondar(this.vencimentoBasico * 0.1));
                break;
            default:
                 adicionalQualificacaoValor.setValor(arredondar(this.vencimentoBasico * 0.125));
                break;
        }
        System.out.println(adicionalQualificacaoValor.getValor());
        return adicionalQualificacaoValor;
    }
    /*
    função que busca o cargo e nível de referência no banco de dados e retorna o valor do vencimento básico
    */
    public Rubrica calculaVencimentoBasico() throws SQLException, ClassNotFoundException{
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
        
        Rubrica vb = new Rubrica();
        vb.setNomeRubrica("Vencimento Básico");
        vb.setAnoReferencia(this.getAno());
        vb.setValor(this.vencimentoBasico);
        
        conexao.close();
        
        return vb; 
    }

    public Rubrica calculaGampu(){
        Rubrica gampu = new Rubrica();
        
        if(this.getOrgao()==1) gampu.setNomeRubrica("GAMPU");       
        else gampu.setNomeRubrica("GAJ");
        
        gampu.setAnoReferencia(this.getAno());
        
        switch(this.getAno()){
            case 2012:
                gampu.setValor(arredondar(this.vencimentoBasico * 0.5));
                break;
            case 2013:
                gampu.setValor(arredondar(this.vencimentoBasico * 0.62));
                break;
            case 2014:
                gampu.setValor(arredondar(this.vencimentoBasico * 0.752));
                break;
            default:
                gampu.setValor(arredondar(this.vencimentoBasico * 0.9));
                break;
        }
         System.out.println(gampu.getValor());
        return gampu;
    }
    
    public Rubrica calculaFuncao() throws ClassNotFoundException, SQLException{
        Connection conexao = new ConectaMySQL().conectaUOL();
        String sql = "SELECT valor FROM `funcoesMPU` WHERE nivel=? and orgao=? and ano<=? order by ano desc, mes desc, nivel desc limit 1";
        
        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, this.getFuncao());
            stmt.setInt(2, this.getOrgao());
            stmt.setInt(3, this.getAno());
            ResultSet rs = stmt.executeQuery();

            rs.next();
            this.funcaoValor = rs.getDouble(1);
            //apenas para teste, imprimir o valor buscado no banco
            System.out.println(this.funcaoValor);
            rs.close();
            //stmt.execute();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        
        Rubrica funcao = new Rubrica();
        funcao.setNomeRubrica("Função de Confiança/Cargo em Comissão");
        funcao.setAnoReferencia(this.getAno());
        funcao.setValor(this.funcaoValor);
        funcao.setIncidePrevidencia(false);
        conexao.close();
        
        return funcao; 
    }
       
    public List<Rubrica> getRubricas() {
        return rubricas;
    }
    
    //Função para arredondar os números em duas casas decimais
    public double arredondar(double teste){
        BigDecimal bd = new BigDecimal(teste).setScale(2, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
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

    public double getSalarioBrutoFinal() {
        return salarioBrutoFinal;
    }

    public void setSalarioBrutoFinal(double salarioBrutoFinal) {
        this.salarioBrutoFinal = salarioBrutoFinal;
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
    
    public List<SubLista> getSublistas() {
        return sublistas;
    }

    public void setSublistas(List<SubLista> sublistas) {
        this.sublistas = sublistas;
    }

    public boolean isConjugePlanAssiste() {
        return conjugePlanAssiste;
    }

    public void setConjugePlanAssiste(boolean conjugePlanAssiste) {
        this.conjugePlanAssiste = conjugePlanAssiste;
    }

    public double getBaseDeCalculoPSS() {
        return baseDeCalculoPSS;
    }

    public void setBaseDeCalculoPSS(double baseDeCalculoPSS) {
        this.baseDeCalculoPSS = baseDeCalculoPSS;
    }

    public double getBaseDeCalculoIRRF() {
        return baseDeCalculoIRRF;
    }

    public void setBaseDeCalculoIRRF(double baseDeCalculoIRRF) {
        this.baseDeCalculoIRRF = baseDeCalculoIRRF;
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

    private int calculaX(Integer ano, Integer tamanhoLista) {
        //Problema: a lista cresce em 4 a partir de 2013. 
        switch (ano){
            case 2012: 
                return 0;
            case 2013:
                return tamanhoLista / 2 + 2;
            case 2014:
                return ( 2 * (tamanhoLista /3 + 1));
            case 2015:
                return 3 * (tamanhoLista/4 + 1);
        }
        return 0; 
                    
    }
    
    
    
}
