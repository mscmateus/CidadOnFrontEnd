package cidadon.controlador;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.bean.*;

import br.ufac.cidadon.entidades.*;
import br.ufac.cidadon.repositorios.*;
import javafx.scene.input.DataFormat;

@ManagedBean(name="usuariocontrolador")
@SessionScoped
public class UsuarioControlador {
	private UsuarioRepositorio ur;
	private ProblemaRepositorio pr;
	private AgraveRepositorio ar;
	private DenunciaRepositorio dr;
	private Usuario usuario;
	private Usuario usuarioAlterado;
	private String login = "";
	private String senha = "";
	
	public UsuarioControlador() {
		ur =new UsuarioRepositorio();
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
	public Usuario getUsuarioAlterado() {
		return usuarioAlterado;
	}
	public void setUsuarioAlterado(Usuario usuarioAlterado) {
		this.usuarioAlterado = usuarioAlterado;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Usuario getUsuario(){
		return usuario;
	}
	//Para inclussão
	public String novo() {
		usuario = new Usuario();
		return "comumPages/criarContaDeUsuario";
	}
	public String confirmaSenha() {
		if(usuario.getSenha().equals(senha)   
				&& (ur.recuperarPorNomeUsuario(usuario.getNomeUsuario()).size()<1) 
				&& (ur.recuperarPorEmail(usuario.getEmail()).size()<1)) {
			return "cadastroDeEndereco";
		}else {
			return "null";
		}
	}
	public String incluir() {
		SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy" );
		formato.format(usuario.getDataNascimento());
		ur.adicionar(usuario);
		return "confirmacaoCriacaoDeConta";
	}
	//Para edição
	public String editar() {
		usuarioAlterado = usuario;
		senha = usuario.getSenha();
		return "usuarioPages/alterarContaDeUsuario";
	}
	public String confirmaSenhaAlteracao() {
		if(usuarioAlterado.getSenha().equals(senha)
				&& (ur.recuperarPorNomeUsuario(usuario.getNomeUsuario()).size()<1) 
				&& (ur.recuperarPorEmail(usuario.getEmail()).size()<1)) {
			return "cadastroDeEndereco";
		}else {
			return "null";
		}
	}
	public String atualizar() {
		usuario = usuarioAlterado;
		SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy" );
		formato.format(usuario.getDataNascimento());
		ur.atualizar(usuario);
		usuarioAlterado=null;
		login = "";
		senha = "";
		return "confirmacaoAlteracao";
	}
	//Fazer exclusão
	public String excluir() {
		usuarioAlterado = usuario;
		
		List<Denuncia> denuncias;
		List<Agrave> agraves;
		List<Problema> problemas;
		
		pr = new ProblemaRepositorio();
		ar = new AgraveRepositorio();
		dr = new DenunciaRepositorio();
		//Remover todas as denuncias realizadas pelo usuario
		denuncias = dr.recuperarTodosPorUsuario(usuarioAlterado.getIdentificador());
		for(Denuncia d: denuncias) {
			dr.remover(d);
		}
		//Remover todos os agraves do usuario
		agraves = ar.recuperarTodosPorUsuario(usuarioAlterado.getIdentificador());
		for(Agrave a: agraves) {
			ar.remover(a);
		}
		//Remover todos os agraves e denuncias dos problemas do usuario
		problemas = pr.recuperarProblemasPorUsuario(usuarioAlterado.getIdentificador());
		for(Problema p: problemas) {
			denuncias = dr.recuperarTodosPorProblema(p.getIdentificador());
			for(Denuncia d: denuncias) {
				dr.remover(d);
			}
			agraves = ar.recuperarTodosPorProblema(p.getIdentificador());
			for(Agrave a: agraves) {
				ar.remover(a);
			}
			//Remove todos os problemas do usuario
			pr.remover(p);
		}
		SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy" );
		formato.format(usuarioAlterado.getDataNascimento());
		ur.remover(ur.recuperar(usuarioAlterado.getIdentificador()));
		usuario =null;
		usuarioAlterado=null;
		login = "";
		senha = "";
		return "//index";
	}
	public String logout() {
		usuario = null;
		usuarioAlterado=null;
		login = "";
		senha = "";
		return "/index";
	}
	public String fazLogin() {
		List<Usuario> u  = ur.recuperarPorEmail(login);
		if(0<u.size()) {
			if(u.get(0).getSenha().equals(senha)) {
				usuario=u.get(0);
				SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy" );
				formato.format(usuario.getDataNascimento());
				return "/userPages/gerenciadorDeAcoes";
			}
		}
		usuario = null;
		usuarioAlterado=null;
		login = "";
		senha = "";
		return "null";
	}
}
