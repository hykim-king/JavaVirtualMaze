/**
 * 파일명: EscapeRoomMain.java <br>
 * 설명 :   <br>
 * 작성자: Hee  <br>
 * 생성일: 2026-04-23 <br>
 */
package com.pcwk.main;

import java.util.Scanner;

/**
 *  일단 대충 틀만 잡기 수정 가능
 */
public class EscapeRoomMain {

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		
		while(true) {
			// 메뉴 선택
			System.out.println("\n===== 방탈출 시스템 =====");
			System.out.println("1. 테마 조회");
			System.out.println("2. 예약 조회");
			System.out.println("3. 종료");
			System.out.print("선택 >> ");
			
			// 메뉴 번호 입력받기
			int select = sc.nextInt();
			
			switch(select)
			{
			//case 1 -> 
			
			default -> System.out.println("번호를 다시 입력하세요");
			}
		}
		
		
//		sc.close();
	}

}
