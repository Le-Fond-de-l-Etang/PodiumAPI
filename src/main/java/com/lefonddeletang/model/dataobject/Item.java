package com.lefonddeletang.model.dataobject;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * DataObject représentant un élément d'un Podium
 */
@Entity
@Table(name="item")
public class Item {
	/** Identifiant de l'item **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	/** Intitulé de l'item **/
	@Column(name="name")
	private String name;
	/** URL de l'image associée (peut être une chaîne vide) **/
	@Column(name="imageUrl", nullable=true)
	private String imageUrl;
	/** Nombre de points de vote associés à l'item **/
	@Column(name="points", columnDefinition="int default 0")
	private int points;
	/** Identifiant du Podium auquel est lié l'Item **/
	@Column(name="podiumId")
	@JsonIgnore
	private int podiumId;
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return this.imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getPoints() {
		return this.points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getPodiumId() {
		return this.podiumId;
	}
	public void setPodiumId(int podiumId) {
		this.podiumId = podiumId;
	}
}
