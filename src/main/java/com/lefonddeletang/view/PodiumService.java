package com.lefonddeletang.view;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lefonddeletang.controller.PodiumAction;
import com.lefonddeletang.model.dataobject.Vote;
import com.lefonddeletang.model.dataobject.Podium;

import javax.ws.rs.PathParam;

/**
 * Présentation des services disponibles par HTTP
 */
@Path("/podium")
public class PodiumService {
	/**
	 * Renvoie une réponse HTTP avec la liste des podiums
	 * 
	 * @return Réponse HTTP contenant le JSON des podiums ou un JSON avec un message d'erreur
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPodiumList() {
		Optional<List<Podium>> podiumList = PodiumAction.getPodiumList();
		if(podiumList.isPresent()) {
			return Response.ok(podiumList.get(), MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"No podium were found\"}").build();
		}
	}
	
	/**
	 * Renvoie une réponse HTTP avec un podium d'après son id
	 * 
	 * @param id Identifiant du podium visé
	 * @return Réponse HTTP contenant le JSON du podium ou un JSON avec un message d'erreur
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPodiumById(final @PathParam("id") int id) {
		Optional<Podium> podium = PodiumAction.getPodiumById(id);
		if(podium.isPresent()) {
			return Response.ok(podium.get(), MediaType.APPLICATION_JSON).build();
		} else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"No podium was found with the given id\"}").build();
		}
	}
	
	/**
	 * Soumet un vote pour les Items d'un podium précis et renvoie une réponse HTTP
	 * 
	 * @param vote Vote (entrée en JSON) à soumettre
	 * @param id Identifiant du podium concerné par le vote
	 * @return Réponse HTTP contenant un JSON avec un message de succès ou d'erreur
	 */
	@POST
	@Path("/{id}/vote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response voteForPodium(Vote vote, @PathParam("id") int id) {
		Optional<Boolean> hasVoted = PodiumAction.voteForPodium(id, vote);
		if(!hasVoted.isPresent()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} else if (hasVoted.get().booleanValue()) {
			return Response.status(Response.Status.CREATED).entity("{\"success\":\"The vote was taken into account\"}").build();
		} else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"No podium was found with the given id\"}").build();
		}
	}
	
	/**
	 * Ajoute un podium en base et renvoie une réponse HTTP
	 * 
	 * @param podium Podium (entrée en JSON) à ajouter
	 * @return Réponse HTTP contenant un JSON du Podium créé ou JSON avec un message d'erreur
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPodium(Podium podium) {
		Optional<Podium> addedPodium = PodiumAction.addPodium(podium);
		if (addedPodium.isPresent()) {
			return Response.status(Response.Status.CREATED).entity(addedPodium.get()).build();
		} else {
            return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	/**
	 * Renvoie une réponse HTTP avec la liste des podiums les plus populaires
	 * 
	 * @return Réponse HTTP contenant un JSON de la liste des podium populaires, ou message d'erreur
	 */
	@GET
	@Path("/trending")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrendingPodium() {
		Optional<List<Podium>> podiumList = PodiumAction.getTrendingPodium();
		if(podiumList.isPresent()) {
			return Response.ok(podiumList, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"No podium were found\"}").build();
		}
	}
	
	/**
	 * Renvoie une réponse HTTP avec les podiums correspondant à une recherche
	 * 
	 * @param searchTerm Filtre de recherche
	 * @param limit Limite de podiums dans la recherche (défaut 10)
	 * @param page Pagination (utilisée si tous les résultats ne sont pas affichés, défaut 0)
	 * @return Réponse HTTP contenant un JSON des podiums correspondant à la recherche, ou un message d'erreur
	 */
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchPodium(@QueryParam("searchTerm") String searchTerm, @QueryParam("limit") @DefaultValue("10") int limit, @QueryParam("page") @DefaultValue("0") int page) {
		Optional<List<Podium>> podiumList = PodiumAction.searchPodium(searchTerm, limit, page);
		if(podiumList.isPresent()) {
			return Response.ok(podiumList.get(), MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"No podium were found\"}").build();
		}
	}
	
	/**
	 * Supprime le podium cible et renvoie une réponse HTTP
	 * 
	 * @param id Identifiant du podium à supprimer
	 * @return Réponse HTTP contenant un JSON avec un message de succès ou d'erreur
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePodium(@PathParam("id") int id) {
		boolean isDeleted = PodiumAction.deletePodium(id);
		if (isDeleted) {
			return Response.status(Response.Status.NO_CONTENT).entity("{\"success\":\"The podium was deleted\"}").build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"No podium was found with the given id\"}").build();
		}
	}
}
