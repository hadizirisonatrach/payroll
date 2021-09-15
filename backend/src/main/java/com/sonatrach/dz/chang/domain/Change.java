package com.sonatrach.dz.chang.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="PAIE_SYS_CHANG_STR_FCT")
public class Change {

	
	@Column(name="STR")
    private String str;
	@Id
	@Column(name="MATRICULE")
	private String mat;
	@Column(name="NOM_PRENOM")
	private String nom_prenom;
	@Column(name="FONCTION")
	private String fonction;
	@Column(name="CHANG_STR")
	private String chang_str;
	@Column(name="CHANG_FONCTION")
	private String chang_fonction;
	@Column(name="LOCALITE_TRAV")
	private String localit_trav;
	
	public Change() {
		
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getMat() {
		return mat;
	}

	public void setMat(String mat) {
		this.mat = mat;
	}

	public String getNom_prenom() {
		return nom_prenom;
	}

	public void setNom_prenom(String nom_prenom) {
		this.nom_prenom = nom_prenom;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public String getChang_str() {
		return chang_str;
	}

	public void setChang_str(String chang_str) {
		this.chang_str = chang_str;
	}

	public String getChang_fonction() {
		return chang_fonction;
	}

	public void setChang_fonction(String chang_fonction) {
		this.chang_fonction = chang_fonction;
	}

	public String getLocalit_trav() {
		return localit_trav;
	}

	public void setLocalit_trav(String localit_trav) {
		this.localit_trav = localit_trav;
	}
	
	
}
