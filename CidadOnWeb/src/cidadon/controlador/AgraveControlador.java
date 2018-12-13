package cidadon.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.*;

import br.ufac.cidadon.entidades.*;
import br.ufac.cidadon.repositorios.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.input.DataFormat;

@ManagedBean(name="agravecontrolador")
@SessionScoped
public class AgraveControlador {
	AgraveRepositorio ar;
	ProblemaRepositorio pr;
	UsuarioRepositorio ur;
	private int [] vetAvaliacao = {1,2,3,4,5};
	private Agrave agrave;
	private List<Agrave> agraves;
	private List<Agrave> agravesNot;
	
	public AgraveControlador() {
		ar = new AgraveRepositorio();
		pr = new ProblemaRepositorio();
		ur = new UsuarioRepositorio();
	}
	
	public List<Agrave> getAgravesNot() {
		return agravesNot;
	}

	public void setAgravesNot(List<Agrave> agravesNot) {
		this.agravesNot = agravesNot;
	}
	public int[] getVetAvaliacao() {
		return vetAvaliacao;
	}
	public Agrave getAgrave() {
		return agrave;
	}
	public void setAgrave(Agrave agrave) {
		this.agrave = agrave;
	}
	public List<Agrave> getAgraves() {
		return agraves;
	}
	public void setAgraves(List<Agrave> agraves) {
		this.agraves = agraves;
	}
	public void agravesPorUsuario(long userCodigo, long problemaCodigo) {
		agrave = new Agrave();
		agraves = ar.recuperarTodosPorUsuarioeProblema(userCodigo,problemaCodigo);
	}
	public void agravesPorNotUsuario(long userCodigo, long problemaCodigo) {
		agrave = new Agrave();
		agravesNot = ar.recuperarTodosPorNotUsuarioeProblema(userCodigo,problemaCodigo);
	}
	//Para inclusão
	public String novo(long userCodigo, long problemaCodigo) {
		agrave = new Agrave();
		agrave.setUsuario(ur.recuperar(userCodigo));
		agrave.setProblema(pr.recuperar(problemaCodigo));
		return "agravePages/incluirAgrave";
	}
	public String incluir() {
		ar.adicionar(agrave);
		agrave = new Agrave();
		return "confirmacao";
	}
	//Para edição
	public String editar(Agrave agrave) {
		this.agrave = agrave;
		return "agravePages/editarAgrave";
	}
	public String atualizar() {
		ar.atualizar(agrave);
		agrave = new Agrave();
		return "confirmacao";
	}
	//Para exclusão
	public String exluir(Agrave agrave) {
		this.agrave = agrave;
		agrave = new Agrave();
		return "agravePages/excluirAgrave";
	}
	public String remover() {
		ar.remover(agrave);
		agrave = new Agrave();
		return "confirmacao";
	}
}
