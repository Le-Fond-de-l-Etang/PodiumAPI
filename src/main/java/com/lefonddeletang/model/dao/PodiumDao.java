package com.lefonddeletang.model.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.lefonddeletang.model.dataobject.Item;
import com.lefonddeletang.model.dataobject.Podium;
import com.lefonddeletang.model.util.HibernateUtil;

/**
 * Interaction avec la base de données
 */
@SuppressWarnings("unchecked")
public class PodiumDao {
	/**
	 * Requête un Podium précis
	 * 
	 * @param id Identifiant du Podium
	 * @return Podium (peut être null)
	 */
	public Podium getPodiumById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Podium podium = (Podium) session.get(Podium.class, id);
		if (podium != null) {
			podium.setItems((List<Item>) session.createCriteria(Item.class).add(Restrictions.eq("podiumId", podium.getId())).list());
		}
		return podium;
	}
	
	/**
	 * Insert un nouveau Podium
	 * 
	 * @param podium Podium à insérer
	 */
	public void addPodium(Podium podium) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		session.save(podium);
		session.getTransaction().commit();
	}
	
	/**
	 * Met à jour un Podium
	 * 
	 * @param podium Podium mis à jour
	 */
	public void updatePodium(Podium podium) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		session.saveOrUpdate(podium);
		session.getTransaction().commit();
	}
	
	/**
	 * Requête la liste des Podium
	 * 
	 * @return Liste des Podium
	 */
	public List<Podium> getPodiumList() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Podium> podiumList = (List<Podium>) session.createCriteria(Podium.class).list();
		
		Iterator<Podium> it = podiumList.iterator();
		while (it.hasNext()) {
			Podium podium = it.next();
			podium.setItems((List<Item>) session.createCriteria(Item.class).add(Restrictions.eq("podiumId", podium.getId())).list());
		}
		return podiumList;
	}
	
	/**
	 * Requête la liste des Podium lesp lus populaires
	 * 
	 * @param limit Quantité de Podium à renvoyer
	 * @return Liste des Podium populaires
	 */
	public List<Podium> getTrendingPodiumList(int limit) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("FROM Podium ORDER BY numberOfVotes");
		query.setFirstResult(0);
		query.setMaxResults(limit);
		List<Podium> podiumList = (List<Podium>) query.list();
		
		Iterator<Podium> it = podiumList.iterator();
		while (it.hasNext()) {
			Podium podium = it.next();
			List<Item> items = (List<Item>) session.createCriteria(Item.class).add(Restrictions.eq("podiumId", podium.getId())).list();
			podium.setItems(items);
		}
		return podiumList;
	}
	
	/**
	 * Requête la liste des Podium correspondant à une recherche
	 * 
	 * @param searchTerm Filtre à appliquer
	 * @param limit Nombre max de Podium à renvoyer
	 * @param page Pagination parmi les résultats
	 * @return Liste des Podium respectant le filtre
	 */
	public List<Podium> getSearchPodiumList(String searchTerm, int limit, int page) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("FROM Podium WHERE name LIKE :search");
		query.setParameter("search", "%"+searchTerm+"%");
		query.setFirstResult(limit*page);
		query.setMaxResults(limit);
		List<Podium> podiumList = (List<Podium>) query.list();
		
		Iterator<Podium> it = podiumList.iterator();
		while (it.hasNext()) {
			Podium podium = it.next();
			List<Item> items = (List<Item>) session.createCriteria(Item.class).add(Restrictions.eq("podiumId", podium.getId())).list();
			podium.setItems(items);
		}
		return podiumList;
	}
	
	/**
	 * Supprime un Podium
	 * 
	 * @param id Identifiant du Podium
	 */
	public void deletePodium(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		Podium podium = (Podium) session.get(Podium.class, id);
		session.delete(podium);
		session.getTransaction().commit();
	}
}
