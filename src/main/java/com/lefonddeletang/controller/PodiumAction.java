package com.lefonddeletang.controller;

import java.util.List;
import java.util.Optional;

import com.lefonddeletang.model.dao.PodiumDao;
import com.lefonddeletang.model.dataobject.Item;
import com.lefonddeletang.model.dataobject.Vote;
import com.lefonddeletang.model.dataobject.Podium;

/**
 * Traitement des actions requêtées par l'API
 */
public abstract class PodiumAction {
	/** Instance permettant d'interagir avec la base de données **/
	private static PodiumDao dao = new PodiumDao();
	
	/**
	 * Renvoie la liste des Podium
	 * 
	 * @return Liste (optionnelle) des Podium
	 */
	public static Optional<List<Podium>> getPodiumList() {
		try {
			List<Podium> podiumList = dao.getPodiumList();
			return Optional.ofNullable(podiumList);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Renvoie un Podium précis
	 * 
	 * @param id Identifiant du Podium voulu
	 * @return Podium (optionnel)
	 */
	public static Optional<Podium> getPodiumById(int id) {
		try {
			Podium podium = dao.getPodiumById(id);
			return Optional.ofNullable(podium);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Soumet un vote pour un Podium
	 * 
	 * @param id Identifiant du Podium concerné
	 * @param vote Vote soumis
	 * @return Boléen (optionnel) indiquant le succès de l'action
	 */
	public static Optional<Boolean> voteForPodium(int id, Vote vote) {
		try {
			Podium podium = dao.getPodiumById(id);
			if (podium == null) {
				return Optional.ofNullable(false);
			}
			List<Item> items = podium.getItems();
			int[] itemsId = vote.getOrder();
			for (int i = 0; i < itemsId.length; i++) {
				for (Item item : items) {
					if (item.getId() == itemsId[i]) {
						int bonus = (i == 0) ? 2 : (i == 1) ? 1 : 0;
						item.setPoints(item.getPoints()+bonus);
					}
				}
			}
			podium.setNumberOfVotes(podium.getNumberOfVotes()+3);
			dao.updatePodium(podium);
			return Optional.ofNullable(true);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Ajoute un nouveau Podium
	 * 
	 * @param podium Podium à ajouter
	 * @return Podium (optionnel) ajouté
	 */
	public static Optional<Podium> addPodium(Podium podium) {
		try {
			PodiumDao dao = new PodiumDao();
			dao.addPodium(podium);
			return Optional.ofNullable(podium);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Renvoie la liste des Podium les plus populaires
	 * 
	 * @return Liste (optionnelle) des Podiums populaires, limitée à 3
	 */
	public static Optional<List<Podium>> getTrendingPodium() {
		try {
			PodiumDao dao = new PodiumDao();
			List<Podium> podiumList = dao.getTrendingPodiumList(3);
			return Optional.ofNullable(podiumList);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Renvoie la liste des Podium correspondant à une recherche
	 * 
	 * @param filter Filtre à appliquer
	 * @param limit Quantité max de Podium à renvoyer
	 * @param page Pagination
	 * @return Liste (optionnelle) des Podium correspondant à la recherche
	 */
	public static Optional<List<Podium>> searchPodium(String filter, int limit, int page) {
		try {
			List<Podium> podiumList = dao.getSearchPodiumList(filter, limit, page);
			return Optional.ofNullable(podiumList);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Supprime un Podium existant
	 * 
	 * @param id Identifiant du Podium à supprimer
	 * @return Booléen indiquant le succès de la suppression
	 */
	public static boolean deletePodium(int id) {
		try {
			Podium podium = dao.getPodiumById(id);
			if (podium != null) {
				dao.deletePodium(id);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
