package cidadon.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.*;

import br.ufac.cidadon.entidades.*;
import br.ufac.cidadon.repositorios.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.input.DataFormat;

@ManagedBean(name="denunciacontrolador")
@SessionScoped
public class DenunciaControlador {
	DenunciaRepositorio dr;
	ProblemaRepositorio pr;
	UsuarioRepositorio ur;
	private int [] vetAvaliacao = {1,2,3,4,5};
	private Denuncia denuncia;
	private List<Denuncia> denuncias;
	private List<Denuncia> denunciasNot;
	
	public DenunciaControlador() {
		dr = new DenunciaRepositorio();
		pr = new ProblemaRepositorio();
		ur = new UsuarioRepositorio();
	}
	
	public List<Denuncia> getDenunciasNot() {
		return denunciasNot;
	}

	public void setDenunciasNot(List<Denuncia> DenunciasNot) {
		this.denunciasNot = DenunciasNot;
	}

	public int[] getVetAvaliacao() {
		return vetAvaliacao;
	}
	public Denuncia getDenuncia() {
		return denuncia;
	}
	public void setDenuncia(Denuncia Denuncia) {
		this.denuncia = Denuncia;
	}
	public List<Denuncia> getDenuncias() {
		return denuncias;
	}
	public void setDenuncias(List<Denuncia> Denuncias) {
		this.denuncias = Denuncias;
	}
	public void denunciasPorUsuario(long userCodigo, long problemaCodigo) {
		denuncia = new Denuncia();
		denuncias = dr.recuperarTodosPorUsuarioeProblema(userCodigo,problemaCodigo);
	}
	public void denunciasPorNotUsuario(long userCodigo, long problemaCodigo) {
		denuncia = new Denuncia();
		denunciasNot = dr.recuperarTodosPorNotUsuarioeProblema(userCodigo,problemaCodigo);
	}
	//Para inclusão
	public String novo(long userCodigo, long problemaCodigo) {
		denuncia = new Denuncia();
		denuncia.setUsuario(ur.recuperar(userCodigo));
		denuncia.setProblema(pr.recuperar(problemaCodigo));
		return "denunciaPages/incluirDenuncia";
	}
	public String incluir() {
		dr.adicionar(denuncia);
		denuncia = new Denuncia();
		return "confirmacao";
	}
	//Para edição
	public String editar(Denuncia denuncia) {
		this.denuncia = denuncia;
		return "denunciaPages/editarDenuncia";
	}
	public String atualizar() {
		dr.atualizar(denuncia);
		denuncia = new Denuncia();
		return "confirmacao";
	}
	//Para exclusão
	public String exluir(Denuncia denuncia) {
		this.denuncia = denuncia;
		return "denunciaPages/excluirDenuncia";
	}
	public String remover() {
		dr.remover(denuncia);
		denuncia = new Denuncia();
		return "confirmacao";
	}
}
