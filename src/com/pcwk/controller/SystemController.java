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
import com.pcwk.theme.vo.ThemeVO;

/**
 * 시스템 메인 컨트롤러 클래스
 * 메인 로직은 전부 여기서 돌아감
 */
public class SystemController {
	
	ThemeView themeView;
	ReservationView reservationView;
	ReservationService reservationService;
	ThemeService themeService;
	List<ThemeVO> list;
	List<ThemeVO> findList;
	
	
	/**
	 * 메인컨트롤러 생성자
	 */
	public SystemController() {
		themeView = new ThemeView();
		reservationView = new ReservationView();
		reservationService = new ReservationService();
		themeService = new ThemeService();
		
		list = themeService.findAll();
	}
	
	/**
	 * 프로그램의 메인 실행 로직을 담당함
	 */
	public void run() {
		// 프로그램 전체 반복 실행 (종료 시까지 유지)
		while(true) {
			// 메인 메뉴 화면 출력메서드 호출 후 값 입력 받기
			String choice = themeView.mainMenu();
			
			switch (choice) {
			// 테마 조회 서비스로 이동
			case "1" -> processThemeMenu();
			// 예약 조회 서비스로 이동
			case "2" -> processReservationManage();
			case "0" -> 
			{
				// run()메서드 종료하여 프로그램 완전 종료
				System.out.println("종료합니다.");
				return;
			}
			
			default -> System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	// 1. 테마 메뉴 관리자
	void processThemeMenu() {
	    while(true) {
	        // View에서 입력받은 번호를 choice에 저장
	        String choice = themeView.themeMenu();
	        
	        switch(choice) {
	            case "1":
	                processShowAllThemes(); // 0을 눌러 return되면 다시 이 while문의 처음으로 옴
	                return;
	            case "2":
	                processSearchThemes(); 
	                return;
	            case "0":
	                return; // 메인 메뉴로 완전히 돌아감
	        }
	    }
	}
	
	/**
	 * 테마 전체 목록 출력
	 */
	void processShowAllThemes() {
		
		while(true)
		{	
			// 테마 전체 목록 출력
			themeView.showAllThemes(list);
			// 테마 선택 메서드 호출
			ThemeVO selected = themeView.selectTheme(list);
			
			if(selected == null)
			{
				return;
			}
			
			// 테마 선택후 예약 콘솔 출력
			if(reservationView.confirmReservation())
			{
				// 테마 예약 메서드 호출
				processMakeReservation(selected);
				break;
			}
		}
		return;
	}
	
	/**
	 * 테마 예약 메서드
	 * @param selected 선택한 테마정보
	 */
	void processMakeReservation(ThemeVO selected)
	{
		ReservationVO vo;
		// 예약 정보 입력 메서드
		vo = reservationView.inputReservation(selected);
		// 예약 확정 메서드 및 예약 결과 메서드
		reservationView.printReservationResult(reservationService.saveReservation(vo));
		
		// 예약 후 메뉴 출력 및 입력을 받음
		int input = reservationView.afterReservationMenu();
		
		// 입력받은 값에 따라 메뉴로 돌아가거나 내역 조회
		if(input == 1)
		{
			return;
		} else if(input == 2)
		{
			// 내역 조회
			ReservationVO searchParam = reservationView.inputRetrieve();
			List<ReservationVO> reservationList = reservationService.selectReservation(searchParam);
			reservationView.printInReservationList(reservationList);
		}
		return;
	}
	
	// 조건 검색 메서드
	void processSearchThemes() {
		
		// 조건을 입력받는 메서드
		ThemeVO vo = themeView.searchTheme();
		
		// 입력받은 조건에 맞는 테마들을 반환받는 메서드
		List<ThemeVO> list = themeService.searchByCondition(vo);
		
		// 리스트가 비어있다면 검색결과 없음 출력
		if(list.isEmpty())
		{
			themeView.printNoSearchResult();
			return;
		}
		
		while(true) {
			// 조건 검색에 	부합하는 테마들을 출력하는 메서드
			themeView.showSearchTheme(list, vo);
			
			// 조건에 맞는 테마 리스트를 파라미터로 보내고 그 중에서 선택
			ThemeVO selected = themeView.selectTheme(list);
			
			// 선택한 테마가 없을시 돌아감
			if(selected == null)
			{
				return;
			}
			
			// 예약 확인 메서드 출력 및 입력받고 예약 메서드 호출
			if(reservationView.confirmReservation())
			{
				processMakeReservation(selected);
				break;
			}
		}
		// 취소를 입력받았을때와 예약완료후 리턴
		return;
	}
	
	   /**
  * 예약관리 전체 흐름
  *
  * 흐름:
  * 1. 예약 내역 조회 입력
  * 2. 전화번호 기준 예약 존재 여부 확인
  * 3. 예약 없으면 안내 후 메인메뉴 복귀
  * 4. 예약 있으면 예약 내역 출력
  * 5. 예약 메뉴 출력
  * 6. 예약수정 / 예약삭제 / 메인메뉴 처리
  */
	
    private void processReservationManage() {

        // 1. View에서 전화번호 입력받기
    	ReservationVO searchParam = reservationView.inputRetrieve();

        // 2. Service에서 전화번호 기준 예약 존재 여부 확인
        boolean existsReservation = reservationService.isExistsMobileNumber(searchParam);

        // 3. 예약 내역이 없으면 안내 후 메인메뉴로 복귀
        if (false == existsReservation) {
            reservationView.printOutReservationList();
            return;
        }

        // 4. 예약 내역이 있으면 조회 결과를 받아 출력
        List<ReservationVO> reservationList = reservationService.selectReservation(searchParam);
        reservationView.printInReservationList(reservationList);

        // 5. 예약 내역 출력 후 예약 메뉴 처리
        processReservationMenu();
    }
    
    /**
     * 예약 내역 출력 후 뜨는 예약 메뉴 처리
     *
     * 1. 예약수정
     * 2. 예약삭제
     * 3. 메인메뉴
     */
    
    private void processReservationMenu() {

        while (true) {
            int menu = reservationView.reservationMenu();

            switch (menu) {
                case 1 -> {
                    processUpdateReservation();
                    return;
                }

                case 2 -> {
                    processDeleteReservation();
                    return;
                }

                case 3 -> {
                    return;
                }

                default -> {
                    System.out.println("잘못된 입력입니다.");
                }
            }
        }
    }
    
    /**
     * 예약 수정 처리
     *
     * 흐름:
     * 1. View에서 수정할 전화번호, 변경 날짜, 변경 테마 입력
     * 2. Service에서 수정 처리
     * 3. 수정 결과 출력
     * 4. 성공 시 예약 완료 출력
     * 5. 메인메뉴 / 예약내역조회 선택
     */
    
    private void processUpdateReservation() {

        // View는 입력만 담당한다.
        ReservationVO updateParam = reservationView.inputUpdateReservation();

        // Service는 예약 존재 확인 + 수정 + 파일 저장을 담당한다.
        int flag = reservationService.updateReservation(updateParam);

        // View는 결과 출력만 담당한다.
        reservationView.printUpdateResult(flag);

        if (flag == 3) {
            reservationView.printReserveComplete();
        }

        processAfterReservationMenu();
    }
    
    /**
     * 예약 삭제 처리
     *
     * 흐름:
     * 1. 삭제 여부 확인
     * 2. 동의하면 삭제할 전화번호 입력
     * 3. Service에서 삭제 처리
     * 4. 삭제 결과 출력
     * 5. 메인메뉴 복귀
     */
    
    private void processDeleteReservation() {

        boolean deleteConfirm = reservationView.inputDeleteReservation();

        if (false ==deleteConfirm) {
            System.out.println("예약 삭제를 취소했습니다.");
            return;
        }

        // 현재 ReservationView에는 삭제할 전화번호만 따로 입력받는 메서드가 없으므로
        // 예약 내역 조회 입력 메서드를 재사용한다.
        ReservationVO deleteParam = reservationView.inputRetrieve();

        int flag = reservationService.deleteReservation(deleteParam);

        reservationView.printDeleteResult(flag);
    }
    
    /**
     * 예약 또는 예약 수정 완료 후 이동 메뉴 처리
     *
     * 1. 메인메뉴
     * 2. 예약내역조회
     */
    
    private void processAfterReservationMenu() {

        while (true) {
            int menu = reservationView.afterReservationMenu();

            switch (menu) {
                case 1 -> {
                    return;
                }

                case 2 -> {
                    processReservationManage();
                    return;
                }

                default -> {
                    System.out.println("잘못된 입력입니다.");
                }
            }
        }
    }   

}
