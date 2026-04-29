/**
 * 
 */
package com.pcwk.controller;

import java.util.List;

import com.pcwk.reservation.domain.ReservationVO;
import com.pcwk.reservation.service.ReservationServiceImpl;
import com.pcwk.reservation.view.ReservationView;
import com.pcwk.theme.domain.ThemeVO;
import com.pcwk.theme.service.ThemeServiceImpl;
import com.pcwk.theme.view.ThemeView;

// 시스템 메인 컨트롤러 클래스 메인 로직은 전부 여기서 돌아감
public class SystemController {

	ThemeView themeView;
	ReservationView reservationView;
	ReservationServiceImpl reservationService;
	ThemeServiceImpl themeService;
	List<ThemeVO> list;
	List<ThemeVO> findList;
	ReservationVO searchParam;
	List<ReservationVO> reservationList;

	/**
	 * 메인컨트롤러 생성자
	 */
	public SystemController() {
		themeView = new ThemeView();
		reservationView = new ReservationView();
		reservationService = new ReservationServiceImpl();
		themeService = new ThemeServiceImpl();

		list = themeService.findAll();
	}

	// 프로그램의 메인 실행 로직을 담당
	public void run() {

		themeView.printTitle();
		
		// 프로그램 전체 반복 실행 (종료 시까지 유지)
		boolean running = true;

		while (running) {
			
			String choice = themeView.mainMenu();
			
			switch (choice) {
			case "1" -> processThemeMenu(); // 테마 조회 서비스로 이동
			case "2" -> processReservationManage(); // 예약 조회 서비스로 이동
			case "0" -> { // run()메서드 종료하여 프로그램 완전 종료
				System.out.println(" [ 프로그램을 종료합니다. ]");
				running = false;
			}
			default -> System.out.println(" ※ 잘못된 입력입니다.");
			}

//			if (running == true) {
//				choice = themeView.inputOnly(); // 메뉴 재출력 없이 입력만
//			}
		}
//		themeView.scClose();
//		reservationView.scClose();
	}

	/**
	 * 1. 테마 조회 관리자
	 */
	void processThemeMenu() {

		boolean running = true;

		while (running) {
			
			String choice = themeView.themeMenu();
			
			switch (choice) {
			case "1" -> { // 전체 목록 보기
				processShowAllThemes();
				running = false;
				
			}
			case "2" -> { // 조건 검색
				processSearchThemes();
				running = false;
			}
			case "0" -> {  return;  } // 뒤로 가기 (메인 메뉴)
			default -> System.out.println(" ※ 잘못된 입력입니다.");
			}
			
		}
		return;
	}

	/**
	 * 테마 1. 전체 목록 보기 관리자
	 */
	void processShowAllThemes() {

		while (true) {

			themeView.showAllThemes(list); // 테마 전체 목록 출력

			// 테마 선택 메서드 호출
			ThemeVO selected = themeView.selectTheme(list);

			if (selected == null) {
				return;
			}

			// 테마 선택후 예약 콘솔 출력
			if (reservationView.confirmReservation()) {
				// 테마 예약 메서드 호출
				processMakeReservation(selected);
				break;
			}
		}
		return;
	}

	/**
	 * 테마 2. 조건 검색 관리자
	 */
	void processSearchThemes() {

		// 조건을 입력받는 메서드
		ThemeVO vo = themeView.searchTheme();

		// 입력받은 조건에 맞는 테마들을 반환받는 메서드
		List<ThemeVO> list = themeService.searchByCondition(vo);

		// 리스트가 비어있다면 검색결과 없음 출력
		if (list.isEmpty()) {
			themeView.printNoSearchResult();
			return;
		}

		while (true) {
			// 조건 검색에 부합하는 테마들을 출력하는 메서드
			themeView.showSearchTheme(list, vo);

			// 조건에 맞는 테마 리스트를 파라미터로 보내고 그 중에서 선택
			ThemeVO selected = themeView.selectTheme(list);

			// 선택한 테마가 없을시 돌아감
			if (selected == null) {
				return;
			}

			// 예약 확인 메서드 출력 및 입력받고 예약 메서드 호출
			if (reservationView.confirmReservation()) {
				processMakeReservation(selected);
				break;
			}
		}
		// 취소를 입력받았을때와 예약완료후 리턴
		return;
	}

	/**
	 * 테마 예약 메서드
	 * 
	 * @param selected 선택한 테마정보
	 */
	void processMakeReservation(ThemeVO selected) {
		
		ReservationVO vo;
		
		// 예약 정보 입력 메서드
		vo = reservationView.inputReservation(selected);
		
		// 예약 확정 메서드 및 예약 결과 메서드
		reservationView.printReservationResult(reservationService.saveReservation(vo));

		// 예약 후 메뉴 출력 및 입력을 받음
		int input = reservationView.afterReservationMenu();

		// 입력받은 값에 따라 메뉴로 돌아가거나 내역 조회
		if (input == 1) {
			return;
		} else if (input == 2) {
			// 내역 조회
//			ReservationVO searchParam = reservationView.inputRetrieve();
//			List<ReservationVO> reservationList = reservationService.selectReservation(searchParam);
//			reservationView.printInReservationList(reservationList);
			
			processReservationManage();
		}
		
		return;
	}

	/**
	 * 예약관리 전체 흐름
	 *
	 * 흐름: 1. 예약 내역 조회 입력 2. 전화번호 기준 예약 존재 여부 확인 3. 예약 없으면 안내 후 메인메뉴 복귀 4. 예약 있으면 예약
	 * 내역 출력 5. 예약 메뉴 출력 6. 예약수정 / 예약삭제 / 메인메뉴 처리
	 * isSearching : 예약 조회시 전화번호 입력받고 예약내역이 없을시 재입력받기위한 용도
	 * 				 예약 내역이 있을경우 내역 출력후 반복문 종료
	 */
	private void processReservationManage() {
		boolean isSearching = true;

		while(isSearching)
		{
			// 1. View에서 전화번호 입력받기
			searchParam = reservationView.inputRetrieve();

			// 2. Service에서 전화번호 기준 예약 존재 여부 확인
			boolean existsReservation = reservationService.isExistsMobileNumber(searchParam);

			if(true == existsReservation)
			{
				// 4. 예약 내역이 있으면 조회 결과를 받아 출력
				reservationList = reservationService.selectReservation(searchParam);
				reservationView.printInReservationList(reservationList);

				// 5. 예약 내역 출력 후 예약 메뉴 처리
				processReservationMenu();
						
				isSearching = false;
			}else // 3. 예약 내역이 없으면
			{
				int menu = reservationView.printOutReservationList();
				
				if(menu == 2)
				{
					return;
				}
			}	
		}
	}

	/**
	 * 예약 내역 출력 후 뜨는 예약 메뉴 처리
	 *
	 * 1. 예약수정 2. 예약삭제 3. 메인메뉴
	 */
	private void processReservationMenu() {

		//boolean isRunning = true;
		
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
	 * 흐름: 1. 수정 여부 확인 2. View에서 수정할 예약건 선택 후, 변경 날짜, 변경 테마 입력 3. Service에서 수정 처리 4.
	 * 수정 결과 출력 5. 성공 시 예약 완료 출력 6. 메인메뉴 / 예약내역조회 선택
	 */

	private void processUpdateReservation() {

		// 수정할 예약건 선택
		ReservationVO vo = reservationView.updateSelectTheme(reservationList);

		// 수정 여부 확인
		boolean updateConfirm = reservationView.inputUpdateReservation();

		if (false == updateConfirm) {
			System.out.println("\n  예약 수정을 취소했습니다.");
			return;
		}

		// 예약 수정 테마 출력
		themeView.showAllThemes(list);
		
		// 예약 수정 입력
		ReservationVO updateVo = reservationView.inputUpdateReservation(vo, list);

		// 변경전 예약 정보와 변경후 예약 정보 둘다 인자로 보냄
		int flag = reservationService.updateReservation(vo, updateVo);

		// 결과 출력
		reservationView.printUpdateResult(flag, updateVo);

		processAfterReservationMenu();
	}

	/**
	 * 예약 삭제 처리
	 *
	 * 흐름: 1. 삭제 여부 확인 2. 동의하면 삭제할 예약건 선택 3. Service에서 삭제 처리 4. 삭제 결과 출력 5. 메인메뉴 복귀
	 */
	private void processDeleteReservation() {

		// 삭제할 예약건 선택하고 받아오기
		ReservationVO vo = reservationView.deleteDeleteTheme(reservationList);

		// 삭제 여부 확인
		boolean deleteConfirm = reservationView.inputDeleteReservation();

		if (false == deleteConfirm) {
			System.out.println("예약 삭제를 취소했습니다.");
			return;
		}

		int flag = reservationService.deleteReservation(vo);

		// 삭제 결과
		reservationView.printDeleteResult(flag);
	}

	/**
	 * 예약 또는 예약 수정 완료 후 이동 메뉴 처리
	 *
	 * 1. 메인메뉴 2. 예약내역조회
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

	/**
	 * 구분선 출력
	 */
	private void printLine() {
		System.out.println("───────────────────────────────────────────────────────────");
	}

}
