package cidadon.controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.swing.ImageIcon;

import org.primefaces.model.UploadedFile;

import br.ufac.cidadon.entidades.Problema;
import br.ufac.cidadon.entidades.TipoDeProblema;
import br.ufac.cidadon.repositorios.TipoDeProblemaRepositorio;

@ManagedBean(name="tipoDeProblemaControlador")
@SessionScoped 
public class TipoDeProblemaControlador {
	private TipoDeProblemaRepositorio tpr;
	private TipoDeProblema tipoDeProblema;
	private int tipoDeProblemaCodigo;
	private List<TipoDeProblema> tiposDeProblemas;
	private UploadedFile iconUp;
	public TipoDeProblemaControlador() {
		tpr = new TipoDeProblemaRepositorio();
	}

//	public void recuperaImagems() throws IOException {
//		File folder = new File("/home/mateus/git/CidadOnFrontEnd/CidadOnWeb/WebContent/resources/img/icones");
//		if (folder.isDirectory()) {
//			File[] sun = folder.listFiles();
//			for (File toDelete : sun) {
//				toDelete.delete();
//			}
//		}
//		List <TipoDeProblema> imagens = tpr.recuperarTodos();
//		FileOutputStream fos;
//		File file;
//		for(TipoDeProblema tdp : imagens) {
//			System.out.println(tdp.getTitulo());
//			file = new File("/home/mateus/git/CidadOnFrontEnd/CidadOnWeb/WebContent/resources/img/icones/"+tdp.getIndentificador()+".png");
//			fos = new FileOutputStream(file);
//			fos.write(tdp.getIcone());
//			fos.close();	
//		}
//	}
	
	public UploadedFile getIconUp() {
		return iconUp;
	}

	public void setIconUp(UploadedFile iconUp) {
		this.iconUp = iconUp;
	}

	public TipoDeProblemaRepositorio getTpr() {
		return tpr;
	}
	public TipoDeProblema getTipoDeProblema() {
		return tipoDeProblema;
	}
	public void setTipoDeProblema(TipoDeProblema tipoDetipoDeProblema) throws IOException {
		this.tipoDeProblema = tipoDetipoDeProblema;
	}
	public List<TipoDeProblema> getTiposDeProblemas() {
		tipoDeProblemaCodigo=0;
		tipoDeProblema = null;
		tiposDeProblemas=tpr.recuperarTodos();
		return tiposDeProblemas;
	}
	public void setTiposDeProblemas(List<TipoDeProblema> tiposDeProblemas) {
		this.tiposDeProblemas = tiposDeProblemas;
	}
	public int getTipoDeProblemaCodigo() {
		return tipoDeProblemaCodigo;
	}
	public void setTipoDeProblemaCodigo(int tipoDeProblemaCodigo) throws IOException {
		tipoDeProblema = tpr.recuperar(tipoDeProblemaCodigo);
		this.tipoDeProblemaCodigo = tipoDeProblemaCodigo;
	}
	//Para inclusão
	public String novo() {
		tipoDeProblema = new TipoDeProblema();
		return "incluirTipoDeProblema";
	}
	public String incluir() {
		tipoDeProblema.setIcone(iconUp.getContents());
		tpr.adicionar(tipoDeProblema);
		tipoDeProblema = null;
		return "confirmacao";
	}
	//Para edição
	public String editar(TipoDeProblema tdp) {
		tipoDeProblema = tdp;
		return "editarTipoDeProblema";
	}
	public String atualizar() {
		System.out.println(iconUp.getFileName());
		tipoDeProblema.setIcone(iconUp.getContents());
		tpr.atualizar(tipoDeProblema);
		tipoDeProblema = null;
		return "confirmacao";
	}
	//Para exclusão
	public String excluir(TipoDeProblema tdp) {
		tipoDeProblema = tdp;
		return "excluirTipoDeProblema";
	}
	public String remover() {
		tpr.remover(tipoDeProblema);
		tipoDeProblema = null;
		return "confirmacao";
	}
}
