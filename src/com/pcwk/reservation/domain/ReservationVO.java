/**
 * 
 */
package com.pcwk.reservation.domain;

import com.pcwk.cmn.DTO;
import com.pcwk.reservation.dao.ReservationDao;

/**
 * 
 */
public class ReservationVO extends DTO {
	
	private String name;
	private String mobileNumber;
	private String date;
	private String themeName;
	ReservationDao dao;
	
	public ReservationVO(String name, String mobileNumber, String date, String theme) {
		super();
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.date = date;
		this.themeName = theme;
		
	}
	
	
	public String toCsv() {
		return String.format("%s,%s,%s,%s", 
				name, mobileNumber, date, themeName);
		
	}
	
	public String toPrint() {
		return String.format("%-10s|%-12s|%-15s|%-15s", 
				name, mobileNumber, date, themeName);
	}
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return themeName;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		this.themeName = theme;
	}

	@Override
	public String toString() {
		return "ReservationVO [name=" + name + ", mobileNumber=" + mobileNumber + ", date=" + date + ", theme=" + themeName
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
