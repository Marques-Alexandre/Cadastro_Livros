package org.libertas.rest;

import java.io.BufferedOutputStream;
import java.io.IOException;

import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.libertas.dao.LivroDaoHibernate;
import org.libertas.model.Livro;
import org.libertas.model.Response;

import com.google.gson.Gson;

/**
 * Servlet implementation class LivroServlet
 */
@WebServlet(urlPatterns={"/livro/*"} , name="livro")
public class LivroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private void enviaResposta(HttpServletResponse response, String json, int codigo) throws
	IOException {

	response.addHeader("Content-Type", "application/json; charset=UTF-8");
	response.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5501");
	response.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");	
	response.setStatus(codigo);

	BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

	out.write(json.getBytes("UTF-8"));

	out.close();
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivroDaoHibernate ldao = new LivroDaoHibernate();
		Gson gson = new Gson();
		int id = 0;
		
		if(request.getPathInfo() != null) {
			String info = request.getPathInfo().replace("/", "");
			id = Integer.parseInt(info);
		}
		if (id > 0) {
			enviaResposta(response, gson.toJson(ldao.consultar(id)), 200);
		} else {
			enviaResposta(response, gson.toJson(ldao.listar()), 200);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// inserir
		LivroDaoHibernate ldao = new LivroDaoHibernate();
		Gson gson = new Gson();
		String json = request.getReader().lines().collect(Collectors.joining());
		Livro l = gson.fromJson(json, Livro.class);
		ldao.inserir(l);
		enviaResposta(response, gson.toJson(new Response(true, "Registro inserido")), 201);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//alterar
		LivroDaoHibernate ldao = new LivroDaoHibernate();
		Gson gson = new Gson();
		int id = 0;
	
		if (request.getPathInfo()!=null) {
		String info = request.getPathInfo().replace("/", "");
		id = Integer.parseInt(info);
	
		}
	
		String json = request.getReader().lines().collect(Collectors.joining());
		Livro l = gson.fromJson(json, Livro.class);
		l.setIdlivro(id);
		ldao.alterar(l);
		enviaResposta(response, gson.toJson(new Response(true, "Registro alterado")), 200);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Excluir
		LivroDaoHibernate ldao = new LivroDaoHibernate();
		Gson gson = new Gson();
		int id = 0;
		
		if (request.getPathInfo()!= null) {
			String info = request.getPathInfo().replace("/", "");
			id = Integer.parseInt(info);
		}
		
		Livro l = new Livro();
		l.setIdlivro(id);
		ldao.excluir(l);
		enviaResposta(response, gson.toJson(new Response(true, "Registro excluido")), 200);
	}
	
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		enviaResposta(response, gson.toJson(new Response(true, "Options")), 200);
	}
	

}
