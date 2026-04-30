/**
 * 
 */
package com.pcwk.reservation.view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import com.pcwk.cmn.PLogger;
import com.pcwk.reservation.domain.ReservationVO;
import com.pcwk.theme.domain.ThemeVO;

public class ReservationView implements PLogger {

	private final Scanner sc;
	ReservationVO vo;

	public ReservationView() {
		sc = new Scanner(System.in);
	}

	// 스캐너 종료 메서드
	public void scClose() {
		sc.close();
	}

	/**
	 * 예약 내역 조회 전화번호 기준으로 예약 내역을 조회하기 위해 전화번호만 입력받는다.
	 *
	 * @return 전화번호가 담긴 ReservationVO
	 */
	public ReservationVO inputRetrieve() {

		System.out.println("┌─────────────────────────────────────────────────────────┐");
		System.out.println("│		         예약 내역 조회       	 	  │");
		System.out.println("└─────────────────────────────────────────────────────────┘");
		

		System.out.printf("\n전화번호 입력 > ");
		String mobileNumber = sc.nextLine();
		System.out.println();
		printLine();

		return new ReservationVO(null, mobileNumber, null, null);
	}

	/**
	 * 예약 내역 조회 결과 출력 조회 결과가 있으면 예약 정보를 출력한다.
	 */
	public void printInReservationList(List<ReservationVO> reservationList) {

		int count = 1;

		for (ReservationVO vo : reservationList) {

			System.out.println(" [ 예약 " + count + " ]");
			System.out.println();

			System.out.println("  이름 : " + vo.getName());
			System.out.println("  날짜 : " + vo.getDate());
			System.out.println("  테마 : " + vo.getTheme());
			printLine();

			count++;
		}
	}

	/**
	 * 예약 내역 조회 결과 출력 조회 결과가 없으면 예약 정보를 출력하지않는다.
	 */
	public int printOutReservationList() {
		System.out.println("  ※ 예약 내역이 존재하지 않습니다.");
		System.out.println();
	    System.out.println("  1. 다시 입력하기");
	    System.out.println("  2. 메인 메뉴");

	    while (true) {
	    	printLine();
	        System.out.print("선택 > ");
	        
	        try {
	            int menu = Integer.parseInt(sc.nextLine().trim());
	            System.out.println();

	            if (menu == 1 || menu == 2) {
	                return menu;
	            }

	            System.out.println("  ※ 1 또는 2를 입력해주세요.");
	        } catch (NumberFormatException e) {
	            System.out.println("  ※ 숫자를 입력해주세요.");
	        }
	        
	    }
	}

	/**
	 * 예약 메뉴 예약 내역 출력 후 표시되는 메뉴다.
	 *
	 * 1. 예약수정 2. 예약삭제 3. 메인메뉴
	 *
	 * @return 사용자가 선택한 메뉴 번호
	 */
	public int reservationMenu() {

		System.out.println();
		System.out.println("  1. 예약 수정");
		System.out.println("  2. 예약 삭제");
		System.out.println("  3. 메인 메뉴");
		printLine();
		return inputNumber("선택 > ");

	}

	/**
	 * 예약 수정 입력
	 *
	 * 전화번호로 수정할 예약을 찾고, 수정 가능한 항목은 날짜와 테마만 입력받는다.
	 *
	 * 현재 ReservationDao.doUpdate()는 mobileNumber 기준으로 기존 예약을 삭제한 뒤 새 ReservationVO를
	 * 저장하는 구조다.
	 *
	 * 따라서 수정용 ReservationVO에는 mobileNumber, date, theme 값이 반드시 들어가야 한다.
	 *
	 * @return 수정 조건과 수정 내용이 담긴 ReservationVO
	 */
	public ReservationVO inputUpdateReservation(ReservationVO param, List<ThemeVO> listParam) {

		int theme = inputNumber("  변경할 테마 번호 > ");

		String date = "";
		LocalDate today = LocalDate.now();

		while (true) {
			date = inputText("  변경할 날짜 (YYYY-MM-DD) > ");

			try {
				// 1. 입력받은 문자열을 LocalDate로 변환
				LocalDate inputDate = LocalDate.parse(date);

				// 2. isBefore를 사용하여 오늘보다 이전인지 체크
				if (inputDate.isBefore(today)) {
					System.out.println("  ※ 오늘 이전 날짜로 변경 할 수 없습니다. (today: " + today + ") 다시 입력해주세요.");
					continue; // 다시 입력받기
				}

				// 모든 검증 통과 시 루프 종료
				break;

			} catch (DateTimeParseException e) {
				// 날짜 형식이 올바르지 않은 경우 (예: 2026-02-30 등 존재하지 않는 날짜 포함)
				System.out.println("  ※ 날짜 형식이 올바르지 않습니다 다시 입력해주세요.");
			}
		}

		// 수정할 테마 받기
		ThemeVO vo = listParam.get(theme - 1);

		// 수정시 필요한 날짜와 테마 이름만 새로 담고 반환
		ReservationVO updateVo = new ReservationVO(param.getName(), param.getMobileNumber(), date, vo.getThemeName());

		return updateVo;

	}

	/**
	 * 예약 삭제 입력
	 *
	 * 현재 조회된 예약을 삭제할지 사용자에게 확인한다.
	 *
	 * @return true면 삭제 진행 menu=1 , false면 삭제 취소 menu=2
	 */
	public boolean inputDeleteReservation() {

		System.out.println("현재 조회된 예약을 삭제하시겠습니까?");
		System.out.println("1. 삭제");
		System.out.println("2. 취소");

		int menu = inputNumber("선택 : ");

		return menu == 1;
	}

	public boolean inputUpdateReservation() {

		while (true) {
			System.out.print("\n  해당 예약을 수정하시겠습니까? (Y/N) > ");
			String answer = sc.nextLine().trim();

			if (answer.equalsIgnoreCase("Y")) {
				System.out.println();
				printLine();
				return true;
			} else if (answer.equalsIgnoreCase("N")) {
				System.out.println("  취소되었습니다. 메인메뉴로 돌아갑니다.");
				return false;
			} else {
				System.out.println("  Y 또는 N만 입력해주세요.");
			}
		}
	}

	/**
	 * 예약하기
	 *
	 * 테마 조회 후 선택한 테마로 예약할지 확인한다.
	 *
	 * @return true면 예약 진행, false면 예약 취소
	 */
	public boolean confirmReservation() {

		while (true) {
			System.out.print("\n  선택한 테마로 예약하시겠습니까? (Y/N) > ");
			String answer = sc.nextLine().trim();

			System.out.println();
			printLine();
			
			if (answer.equalsIgnoreCase("Y")) {
				return true;
			} else if (answer.equalsIgnoreCase("N")) {
				System.out.println("  취소되었습니다. 목록으로 돌아갑니다.");
				return false;
			} else {
				System.out.println("  Y 또는 N만 입력해주세요.");
			}
		}

	}

	public ReservationVO updateSelectTheme(List<ReservationVO> reservation) {
		
		System.out.println();
		printLine();
		System.out.println(" [예약 수정]\n");
		int input = inputNumber("  수정할 예약의 테마 번호 입력 > ");
		
		if(input < 0 || input > reservation.size())
		{
			System.out.println("  1 ~ " + reservation.size() + "사이 번호를 입력하세요");
			input = inputNumber("  수정할 예약의 테마 번호 입력 > ");
		}

		printLine();

		return reservation.get(input - 1);

	}

	public ReservationVO deleteDeleteTheme(List<ReservationVO> reservation) {
		
		printInReservationList(reservation);

		int input = inputNumber(" 삭제할 예약의 번호 입력 >  ");

		return reservation.get(input - 1);

	}

	/**
	 * 예약 정보 입력
	 *
	 * 예약하기를 선택한 경우 이름, 전화번호, 날짜, 테마를 입력받는다.
	 *
	 * @return 예약 정보가 담긴 ReservationVO
	 */
	public ReservationVO inputReservation(ThemeVO selectedTheme) {
		
		System.out.println(" [ 예약 정보 입력 ]\n");

		String name = inputText("  이름 > ");
		String mobileNumber = inputText("  전화번호 > ");

		String date = "";
		LocalDate today = LocalDate.now();

		while (true) {
			date = inputText("  예약 날짜 (YYYY-MM-DD) > ");

			try {
				// 1. 입력받은 문자열을 LocalDate로 변환
				LocalDate inputDate = LocalDate.parse(date);

				// 2. isBefore를 사용하여 오늘보다 이전인지 체크
				if (inputDate.isBefore(today)) {
					System.out.println("  ※ 오늘 (" + today + ") 이전 날짜는 예약할 수 없습니다. 다시 입력해주세요.");
					continue; // 다시 입력받기
				}

				break; // 모든 검증 통과 시 루프 종료

			} catch (DateTimeParseException e) {
				// 날짜 형식이 올바르지 않은 경우 (예: 2026-02-30 등 존재하지 않는 날짜 포함)
				System.out.println("  ※ 날짜 형식이 올바르지 않습니다 다시 입력해주세요.");
			}
		}

		printLine();
		String theme = selectedTheme.getThemeName();
		System.out.println(" [ 입력한 예약 정보 ]");
		System.out.println("  " + theme + " / " + name + " / " + mobileNumber + " / " + date + "\n");

		return new ReservationVO(name, mobileNumber, date, theme);
	}

	/**
	 * 예약 정보 결과 출력
	 *
	 * ReservationService.saveReservation() 기준: flag == 1 : 예약 성공 flag == 2 : 이미 예약된
	 * 날짜/테마 그 외 : 예약 실패
	 *
	 * @param flag Service에서 반환한 처리 결과
	 */
	public void printReservationResult(int flag) {

		if (flag == 1) {
			System.out.println("  예약이 완료되었습니다!");
		} else if (flag == 2) {
			System.out.println("  ※ 이미 예약된 날짜와 테마입니다.");
		} else {
			System.out.println("  ※ 예약에 실패했습니다.");
		}
		
		printLine();
	}

	/**
	 * 예약 수정 결과 출력
	 *
	 * ReservationService.updateReservation() 기준: flag == 3 : 수정 성공 flag == 2 : 예약
	 * 없음 그 외 : 수정 실패
	 *
	 * @param flag Service에서 반환한 처리 결과
	 */
	public void printUpdateResult(int flag, ReservationVO updateVo) {

		printLine();
		System.out.println();
		
		if (flag == 3) {
			
			System.out.println("  예약 수정이 완료되었습니다.\n");
			System.out.println(updateVo.toPrint() + "\n");
			printLine();
		} else if (flag == 2) {
			System.out.println("  ※ 해당 전화번호의 예약 내역이 없습니다.");
		} else {
			System.out.println("  ※ 예약 수정에 실패했습니다.");
		}
	}

	/**
	 * 예약 삭제 결과 출력
	 *
	 * ReservationService.deleteReservation() 기준: flag == 1 : 삭제 성공 flag == 2 : 예약
	 * 없음 그 외 : 삭제 실패
	 *
	 * @param flag Service에서 반환한 처리 결과
	 */
	public void printDeleteResult(int flag) {

		if (flag == 1) {
			System.out.println("  예약 삭제가 완료되었습니다.");
		} else if (flag == 2) {
			System.out.println("  ※ 해당 전화번호의 예약 내역이 없습니다.");
		} else {
			System.out.println("  ※ 예약 삭제에 실패했습니다.");
		}
	}

	/**
	 * 예약 완료 안내 출력
	 */
	public void printReserveComplete() {

		System.out.print("  예약 처리가 완료되었습니다.");
	}

	/**
	 * 예약 완료 또는 예약 수정 완료 후 이동 메뉴
	 *
	 * 1. 메인메뉴 2. 예약내역조회
	 *
	 * @return 사용자가 선택한 메뉴 번호
	 */
	public int afterReservationMenu() {

		System.out.println("\n  1. 메인 메뉴");
		System.out.println("  2. 예약 내역 조회");
		printLine();
		return inputNumber("선택 > ");
	}

	/**
	 * 숫자 입력 전용 메서드
	 *
	 * 메뉴 선택처럼 숫자가 필요한 경우 사용한다. 숫자가 아닌 값을 입력하면 다시 입력받는다.
	 *
	 * @param message 입력 안내 문구
	 * @return 숫자로 변환된 입력값
	 */
	private int inputNumber(String message) {
		while (true) {
			System.out.print(message);

			String input = sc.nextLine().trim();

			try {
				int number = Integer.parseInt(input);

				return number;
			} catch (NumberFormatException e) {
				System.out.println("  숫자만 입력해주세요.");
			}
		}
	}

	/**
	 * 문자열 입력 전용 메서드
	 *
	 * 이름, 전화번호, 날짜, 테마처럼 문자열이 필요한 경우 사용한다. 빈 값은 허용하지 않는다.
	 *
	 * @param message 입력 안내 문구
	 * @return 입력받은 문자열
	 */
	private String inputText(String message) {
		while (true) {
			System.out.print(message);

			String input = sc.nextLine();

			if (input.isEmpty()) {
				System.out.println("  빈 문자열 입력");
				System.out.println("  값을 입력해주세요.");
				continue;
			}

			return input;
		}
	}

	/**
	 * 구분선 출력
	 */
	private void printLine() {
		System.out.println("───────────────────────────────────────────────────────────");
	}

}
