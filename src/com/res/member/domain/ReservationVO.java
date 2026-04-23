/**
 * 파일명: ReservationVO.java <br>
 * 설명 : 예약정보 관리 <br>
 * 작성자: Hee  <br>
 * 생성일: 2026-04-24 <br>
 */
package com.res.member.domain;

import com.pcwk.cmn.DTO;

/**
 *  예약 정보 관리 Value Object
 */
public class ReservationVO extends DTO {

	private String name; // 예약자 이름
	private String number; // 예약자 전화번호
	private String reservationId; // 예약한 테마 번호
	private String date; // 예약 날짜
	
	
	
	
	public ReservationVO(String name, String number, String reservationId, String date) {
		super();
		this.name = name;
		this.number = number;
		this.reservationId = reservationId;
		this.date = date;
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
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}
	/**
	 * @param reservationId the reservationId to set
	 */
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
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


	@Override
	public String toString() {
		return "ReservationVO [name=" + name + ", number=" + number + ", reservationId=" + reservationId + ", date="
				+ date + ", toString()=" + super.toString() + "]";
	}
	
}
