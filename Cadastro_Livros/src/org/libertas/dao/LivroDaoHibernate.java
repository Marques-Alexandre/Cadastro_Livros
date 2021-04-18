package org.libertas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.libertas.model.Livro;

public class LivroDaoHibernate {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConexaoHibernate");
	private static EntityManager em = emf.createEntityManager();
	
	public void inserir(Livro l) {
		em.getTransaction().begin();
		em.persist(l);
		em.getTransaction().commit();
	}
	public void alterar(Livro l) {
		em.getTransaction().begin();
		em.merge(l);
		em.getTransaction().commit();
	}
	public void excluir(Livro l) {
		em.getTransaction().begin();
		em.remove(em.merge(l));
		em.getTransaction().commit();
		//em.remove(l);
	}
	public Livro consultar(int id) {
		Livro l = em.find(Livro.class, id);
		return l;
	}
	
	public List<Livro> listar() {
		Query query = em.createQuery("SELECT l FROM Livro l");
		List<Livro> lista = (List<Livro>) query.getResultList();
		
		return lista;
	}

}
