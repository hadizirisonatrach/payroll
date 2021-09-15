package com.sonatrach.dz.newpaie.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="PAIE_NEWPAIE")
public class NewPaie {
	
	@Id
	@Column(name="MAT")
	private String mat;
	@Column(name= "REF_DATE")
	private String ref;
	@Column(name="STR")
	private String str;
	@Column(name="NOM")
	private String nom;
	@Column(name="CODE_FCT")
	private String codefct;
	@Column(name="ECHELLE")
	private String echelle;
	@Column(name="ECHELON")
	private String echelon;
	@Column(name="MT_SB")
	private BigDecimal mtsb;
	@Column(name="MT_ECHLON")
	private BigDecimal mtechelon;
	@Column(name="MT_PRS")
	private BigDecimal mtpers;
	@Column(name="MT_AI07")
	private BigDecimal mtai07;
	@Column(name="MT_AI08")
	private BigDecimal mtai08;
	@Column(name="MT_AI09")
	private BigDecimal mtai09;
	@Column(name="MT_AI10")
	private BigDecimal mtai10;
	@Column(name="MT_AI11")
	private BigDecimal mtai11;
	@Column(name="MT_AI12")
	private BigDecimal mtai12;
	@Column(name="MT_AI13")
	private BigDecimal mtai13;
	@Column(name="MT_AI14")
	private BigDecimal mtai14;
	@Column(name="MT_AI15")
	private BigDecimal mtai15;
	@Column(name="MT_AI16")
	private BigDecimal mtai16;
	@Column(name="MT_AI17")
	private BigDecimal mtai17;
	@Column(name="MT_AI18")
	private BigDecimal mtai18;
	@Column(name="MT_AI19")
	private BigDecimal mtai19;
	@Column(name="TX_REVAL")
	private BigDecimal txreval;
	@Column(name="MT_REVAL")
	private BigDecimal mtreval;
	@Column(name="TX_PRI")
	private BigDecimal txpri;
	@Column(name="TX_PRC")
	private BigDecimal txprc;
	@Column(name="MT_PRI")
	private BigDecimal mtpri;
	@Column(name="MT_PRC")
	private BigDecimal mtprc;
	@Column(name="MT_IAG")
	private BigDecimal mtiag;
	@Column(name="NOUV_SAL")
	private BigDecimal nouvsal;
	
	public NewPaie() {
		
	}

	public String getMat() {
		return mat;
	}

	public void setMat(String mat) {
		this.mat = mat;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCodefct() {
		return codefct;
	}

	public void setCodefct(String codefct) {
		this.codefct = codefct;
	}

	public String getEchelle() {
		return echelle;
	}

	public void setEchelle(String echelle) {
		this.echelle = echelle;
	}

	public String getEchelon() {
		return echelon;
	}

	public void setEchelon(String echelon) {
		this.echelon = echelon;
	}

	

	public BigDecimal getMtsb() {
		return mtsb;
	}

	public void setMtsb(BigDecimal mtsb) {
		this.mtsb = mtsb;
	}

	public BigDecimal getMtechelon() {
		return mtechelon;
	}

	public void setMtechelon(BigDecimal mtechelon) {
		this.mtechelon = mtechelon;
	}

	public BigDecimal getMtpers() {
		return mtpers;
	}

	public void setMtpers(BigDecimal mtpers) {
		this.mtpers = mtpers;
	}

	public BigDecimal getMtai07() {
		return mtai07;
	}

	public void setMtai07(BigDecimal mtai07) {
		this.mtai07 = mtai07;
	}

	public BigDecimal getMtai08() {
		return mtai08;
	}

	public void setMtai08(BigDecimal mtai08) {
		this.mtai08 = mtai08;
	}

	public BigDecimal getMtai09() {
		return mtai09;
	}

	public void setMtai09(BigDecimal mtai09) {
		this.mtai09 = mtai09;
	}

	public BigDecimal getMtai10() {
		return mtai10;
	}

	public void setMtai10(BigDecimal mtai10) {
		this.mtai10 = mtai10;
	}

	public BigDecimal getMtai11() {
		return mtai11;
	}

	public void setMtai11(BigDecimal mtai11) {
		this.mtai11 = mtai11;
	}

	public BigDecimal getMtai12() {
		return mtai12;
	}

	public void setMtai12(BigDecimal mtai12) {
		this.mtai12 = mtai12;
	}

	public BigDecimal getMtai13() {
		return mtai13;
	}

	public void setMtai13(BigDecimal mtai13) {
		this.mtai13 = mtai13;
	}

	public BigDecimal getMtai14() {
		return mtai14;
	}

	public void setMtai14(BigDecimal mtai14) {
		this.mtai14 = mtai14;
	}

	public BigDecimal getMtai15() {
		return mtai15;
	}

	public void setMtai15(BigDecimal mtai15) {
		this.mtai15 = mtai15;
	}

	public BigDecimal getMtai16() {
		return mtai16;
	}

	public void setMtai16(BigDecimal mtai16) {
		this.mtai16 = mtai16;
	}

	public BigDecimal getMtai17() {
		return mtai17;
	}

	public void setMtai17(BigDecimal mtai17) {
		this.mtai17 = mtai17;
	}

	public BigDecimal getMtai18() {
		return mtai18;
	}

	public void setMtai18(BigDecimal mtai18) {
		this.mtai18 = mtai18;
	}

	public BigDecimal getTxreval() {
		return txreval;
	}

	public void setTxreval(BigDecimal txreval) {
		this.txreval = txreval;
	}

	public BigDecimal getMtreval() {
		return mtreval;
	}

	public void setMtreval(BigDecimal mtreval) {
		this.mtreval = mtreval;
	}

	public BigDecimal getTxpri() {
		return txpri;
	}

	public void setTxpri(BigDecimal txpri) {
		this.txpri = txpri;
	}

	public BigDecimal getTxprc() {
		return txprc;
	}

	public void setTxprc(BigDecimal txprc) {
		this.txprc = txprc;
	}

	public BigDecimal getMtpri() {
		return mtpri;
	}

	public void setMtpri(BigDecimal mtpri) {
		this.mtpri = mtpri;
	}

	public BigDecimal getMtprc() {
		return mtprc;
	}

	public void setMtprc(BigDecimal mtprc) {
		this.mtprc = mtprc;
	}

	public BigDecimal getMtiag() {
		return mtiag;
	}

	public void setMtiag(BigDecimal mtiag) {
		this.mtiag = mtiag;
	}

	public BigDecimal getNouvsal() {
		return nouvsal;
	}

	public void setNouvsal(BigDecimal nouvsal) {
		this.nouvsal = nouvsal;
	}

	public BigDecimal getMtai19() {
		return mtai19;
	}

	public void setMtai19(BigDecimal mtai19) {
		this.mtai19 = mtai19;
	}

	
	
	

	
	



}
