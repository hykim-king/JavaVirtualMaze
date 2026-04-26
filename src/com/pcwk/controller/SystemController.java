/**
 * 
 */
package com.pcwk.controller;

import java.util.List;

import com.pcwk.reservation.domain.ReservationVO;
import com.pcwk.reservation.service.ReservationService;
import com.pcwk.reservation.view.ReservationView;
import com.pcwk.theme.service.ThemeService;
import com.pcwk.theme.view.ThemeView;
import VO.ThemeVO;

/**
 * 
 */
public class SystemController {
	
	ThemeView themeView;
	ReservationView reservationView;
	ReservationService reservationService;
	ThemeService themeService;
	List<ThemeVO> list;
	List<ThemeVO> findList;
	
	public SystemController() {
		themeView = new ThemeView();
		reservationView = new ReservationView();
		reservationService = new ReservationService();
		themeService = new ThemeService();
		
		list = themeService.findAll();
	}
	
	public void run() {
		
		while(true) {
			String resChoice;
			String choice = themeView.mainMenu();
			
			switch (choice) {
			case "1" -> {
				resChoice = processThemeMenu();
				if(resChoice == "1")
				{
					processShowAllThemes();
				} else if(resChoice == "2")
				{
					//themeView.searchTheme();
				} else if(resChoice == "0")
				{
					continue;
				}			
				
			}
			case "2" -> {
				
			}
			case "0" -> System.out.println("종료합니다.");
			
			default -> System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	// 1. 테마 메뉴 관리자
	String processThemeMenu() {
	    while(true) {
	        // View에서 입력받은 번호를 choice에 저장
	        String choice = themeView.themeMenu();
	        
	        switch(choice) {
	            case "1":
	                processShowAllThemes(); // 0을 눌러 return되면 다시 이 while문의 처음으로 옴
	                break;
	            case "2":
	                processSearchThemes(); 
	                break;
	            case "0":
	                return choice; // 메인 메뉴로 완전히 돌아감
	        }
	    }
	}
	
	void processShowAllThemes() {
		
		while(true)
		{	
			themeView.showAllTheme(list);
			ThemeVO selected = themeView.selectTheme(list);
			
			if(selected == null)
			{
				return;
			}
			
			
			if(reservationView.confirmReservation())
			{
				processMakeReservation(selected);
			} else
			{
				return;
			}
		}
		}
	
	void processMakeReservation(ThemeVO selected)
	{
		ReservationVO vo;
		vo = reservationView.inputReservation(selected);
		reservationView.printReservationResult(reservationService.saveReservation(vo));
		
	}
	
	void processSearchThemes() {
		
		ThemeVO vo = themeView.searchTheme();
		
		List<ThemeVO> list = themeService.searchByCondition(vo);
		
		themeView.showSearchTheme(list);
		
	}
	
	

}
