package cidadon.controlador;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.*;

import br.ufac.cidadon.entidades.*;
import br.ufac.cidadon.repositorios.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.input.DataFormat;

@ManagedBean(name="problemacontrolador")
@SessionScoped
public class ProblemaControlador {
	private UsuarioRepositorio ur;
	private TipoDeProblemaRepositorio tpr;
	private ProblemaRepositorio pr;
	private List<Problema> problemas;
	private Problema problema;
	private String tipoDeProblemaCodigo = "0";
	private String idDeBusca="";
	private String idDeSelecao="";
	private String latitude="",longitude="";
	
	public ProblemaControlador() {
		ur = new UsuarioRepositorio();
		tpr = new TipoDeProblemaRepositorio();
		pr = new ProblemaRepositorio();
	}
	
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getIdDeSelecao() {
		return idDeSelecao;
	}

	public void setIdDeSelecao(String idDeSelecao) {
		this.idDeSelecao = idDeSelecao;
	}

	public List<Problema> getProblemas() {
		return problemas;
	}
	public void setProblemas(List<Problema> problemas) {
		this.problemas = problemas;
	}
	public Problema getProblema() {
		return problema;
	}
	public void setProblema(Problema problema) {
		this.problema = problema;
	}
	public String getIdDeBusca() {
		return idDeBusca;
	}
	public void setIdDeBusca(String idDeBusca) {
		this.idDeBusca = idDeBusca;
	}
	
	public String getTipoDeProblemaCodigo() {
		return tipoDeProblemaCodigo;
	}

	public void setTipoDeProblemaCodigo(String tipoDeProblemaCodigo) {
		this.tipoDeProblemaCodigo = tipoDeProblemaCodigo;
	}

	public String pesquisa() {
		if(idDeBusca.equals("") && tipoDeProblemaCodigo.equals("0")) {
			problemas = pr.recuperarTodos();
		}else {
			if(idDeBusca.equals("") && !tipoDeProblemaCodigo.equals("0")) {
				problemas = pr.recuperarProblemasPorTipo(Integer.parseInt(tipoDeProblemaCodigo));
			}else {
				if(!idDeBusca.equals("") && tipoDeProblemaCodigo.equals("0")) {
					problemas = new ArrayList<Problema>();
					problemas.add(pr.recuperar(Long.parseLong(idDeBusca)));
				}else {
					problemas = pr.recuperarProblemasPorTipoEId(Integer.parseInt(tipoDeProblemaCodigo),Long.parseLong(idDeBusca));
				}
			}
		}
		return "null";
	}
	public String recuperaTodosOsProblemas() {
		problemas = pr.recuperarTodos();
		return "null";
	}
	public String problemasParaMapa() {
		problema = new Problema();
		pesquisa();
		String a = "<script>";
		for(Problema p : problemas) {
			a += "fazPonto({lat: "+p.getLatitude()+",lng: "+p.getLongitude()+"},'"+p.getIdentificador()+"',"+problemas.indexOf(p)+",'"+p.getIdentificador()+".png');\n";
		}
		a+="</script>";
		return a;
	}
	public String problemasUsuarioParaMapa(Usuario u){
		problema = new Problema();
		problemas = pr.recuperarProblemasPorUsuario(u.getIdentificador());
		String a = "<script>";
		for(Problema p : problemas) {
			a += "fazTodosPontos({lat: "+p.getLatitude()+",lng: "+p.getLongitude()+"},'"+p.getIdentificador()+"',"+problemas.lastIndexOf(p)+",'\"+p.getIdentificador()+\".png');\n";
		}
		a+="</script>";
		return a;
	}
	//Deslocamento instanciados
	public void instanciaProblema() {
		problema = new Problema();
	}
	public String irGerenciar() {
		problema = new Problema();
		return "problemaPages/gerenciarProblemas";
	}
	public String irConsultarMapaOff() {
		problema = new Problema();
		return "comumPages/consultarMapaDeProblemas";
	}
	
	//Para inclusão
	public String novo() {
		problema = new Problema();
		problema.setLatitude(Double.parseDouble(latitude));
		problema.setLongitude(Double.parseDouble(longitude));
		problema.setDataCriacao(new Date());
		SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy" );
		formato.format(problema.getDataCriacao());
		return "incluirProblema";
	}
	public String incluir(long userid) {
		problema.setUsuario(ur.recuperar(userid));
		TipoDeProblema tipo = tpr.recuperar(Integer.parseInt(tipoDeProblemaCodigo));
		problema.setTipoDeProblema(tipo);
		problema.setResolvido(false);
		pr.adicionar(problema);
		problema = new Problema();
		return "confirmacao";
	}
	//Para edição
	public String editar() {
		problema = pr.recuperar(Long.parseLong(idDeSelecao));
		SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy" );
		formato.format(problema.getDataCriacao());
		return "editarProblema";
	}
	public String atualizar() {
		problema.setTipoDeProblema(tpr.recuperar(Integer.parseInt(tipoDeProblemaCodigo)));
		pr.atualizar(problema);
		problema = new Problema();
		return "confirmacao";
	}
	//Para exclusão
	public String exluir() {
		problema = pr.recuperar(Long.parseLong(idDeSelecao));
		tipoDeProblemaCodigo = String.valueOf(problema.getTipoDeProblema().getIndentificador());
		SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy" );
		formato.format(problema.getDataCriacao());
		return "excluirProblema";
	}
	public String remover() {
		pr.remover(problema);
		problema = new Problema();
		return "confirmacao";
	}
	//Para para o carregamento das informações
	public String pegarInformacoesProblema() {
		problema = pr.recuperar(Long.parseLong(idDeSelecao));
		SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy" );
		formato.format(problema.getDataCriacao());
		return "inforProblema";
	}
}
