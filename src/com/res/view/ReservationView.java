/**
 * 파일명: ReservationView.java <br>
 * 설명 : 입력은 다 여기서 받음 <br>
 * 작성자: Hee  <br>
 * 생성일: 2026-04-24 <br>
 */
package com.res.view;

import java.util.Scanner;

/**
 *  
 */
public class ReservationView {

	private Scanner sc = new Scanner(System.in);

	public ReservationView() {
		
	} 
	
	
	
	
	public String inputNumber() {
		System.out.print("전화번호를 입력하세요 >> ");
		return sc.next();
	}
	
	public String inputNewNumber() {
		System.out.print("새 전화번호를 입력하세요 >> ");
		return sc.next();
	}
	
	public String inputName() {
		System.out.print("예약자 이름을 입력하세요 >> ");
		return sc.next();
	}
	
	public String inputNewName() {
		System.out.print("새 예약자 이름을 입력하세요 >> ");
		return sc.next();
	}
	
	
	
}
