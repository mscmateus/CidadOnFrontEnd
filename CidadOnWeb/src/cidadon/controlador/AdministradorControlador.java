package cidadon.controlador;
import java.util.List;

import javax.faces.bean.*;

import br.ufac.cidadon.entidades.*;
import br.ufac.cidadon.repositorios.*;

@ManagedBean(name="administradorcontrolador")
@SessionScoped
public class AdministradorControlador {
	private AdministradorRepositorio ur;
	private Administrador administrador;
	private Administrador administradorCriacao;
	private Administrador administradorAlterado;
	private String login = "";
	private String senha = "";
	
	
	public AdministradorControlador() {
		ur =new AdministradorRepositorio();
	}
	
	public Administrador getAdministradorCriacao() {
		return administradorCriacao;
	}

	public void setAdministradorCriacao(Administrador administradorCriacao) {
		this.administradorCriacao = administradorCriacao;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Administrador getAdministrador(){
		return administrador;
	}
	public String fazLogin() {
		List<Administrador> u  = ur.recuperarPorEmail(login);
		if(0<u.size()) {
			if(u.get(0).getSenha().equals(senha)) {
				administrador=u.get(0);
				return "gerenciadorDeAcoes";
			}
		}
		administrador = null;
		return "null";
	}
	public String logout() {
		administrador = null;
		return "autenticarAdministrador";
	}
	public String novo() {
		administradorCriacao = new Administrador();
		return "criarContaDeAdministrador";
	}
	public String confirmaSenha() {
		if(administradorCriacao.getSenha().equals(senha)  
				&& (ur.recuperarPorNomeAdministrador(administradorCriacao.getNomeAdministrador()).size()<1) 
				&& (ur.recuperarPorEmail(administradorCriacao.getEmail()).size()<1)) {
			ur.adicionar(administradorCriacao);
			administradorCriacao = null;
			return "confirmacao";
		}else {
			return "null";
		}
	}
	public String excluir() {
		ur.remover(administrador);
		administrador =null;
		return "confirmacao";
	}
	public String editar() {
		administradorAlterado = administrador;
		return "editarContaDeAdministrador";
	}
	public String confirmaSenhaAlteracao() {
		if(administrador.getSenha().equals(senha)) {
			administrador = administradorAlterado;
			ur.atualizar(administrador);
			return "confirmacao";
		}else {
			return "null";
		}
	}
}
