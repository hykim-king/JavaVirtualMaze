/**
 * 
 */
package com.pcwk.reservation.view;

import java.util.*;

import com.pcwk.cmn.PLogger;
import com.pcwk.reservation.domain.ReservationVO;
import com.pcwk.theme.vo.ThemeVO;

public class ReservationView implements PLogger {
	
	private final Scanner sc ;
	ReservationVO vo;
	

	public ReservationView() {
		sc = new Scanner(System.in);
	}
	
	
	
	 /**
     * 예약 내역 조회 입력
     *
     * 전화번호 기준으로 예약 내역을 조회하기 위해
     * 전화번호만 입력받는다.
     *
     * @return 전화번호가 담긴 ReservationVO
     */
    public ReservationVO inputRetrieve() {

    	printTitle("예약 내역 조회");
    	
        System.out.print("조회할 전화번호 입력:  ");
        String mobileNumber=sc.nextLine();
        System.out.println("조회할 전화번호: "+ mobileNumber);

        
        return new ReservationVO(null, mobileNumber, null , null);
    }

    /**
     * 예약 내역 조회 결과 출력
     * 조회 결과가 있으면 예약 정보를 출력한다.
     */
    public void printInReservationList(List<ReservationVO> reservationList) {
    	
    	for (ReservationVO vo : reservationList) {
    		System.out.println("──────────────────────────────────────────────");
    		System.out.println("예약 내역 출력: "+ vo.toCsv());
    		System.out.println("──────────────────────────────────────────────");
    	}
    	
    	
    }
    /**
     * 예약 내역 조회 결과 출력
     * 조회 결과가 없으면 예약 정보를 출력하지않는다.
     */
    public void printOutReservationList() {
    	System.out.println("예약 내역 없음");      
    }

    
    
    /**
     * 예약 메뉴
     * 예약 내역 출력 후 표시되는 메뉴다.
     *
     * 1. 예약수정
     * 2. 예약삭제
     * 3. 메인메뉴
     *
     * @return 사용자가 선택한 메뉴 번호
     */
    public int reservationMenu() {
 
    	printTitle("예약 메뉴");

    	System.out.println("1. 예약수정");
    	System.out.println("2. 예약삭제");
    	System.out.println("3. 메인메뉴");
        

        return inputNumber("메뉴 선택 : ");
    }

    /**
     * 예약 수정 입력
     *
     * 전화번호로 수정할 예약을 찾고,
     * 수정 가능한 항목은 날짜와 테마만 입력받는다.
     *
     * 현재 ReservationDao.doUpdate()는 mobileNumber 기준으로 기존 예약을 삭제한 뒤
     * 새 ReservationVO를 저장하는 구조다.
     *
     * 따라서 수정용 ReservationVO에는
     * mobileNumber, date, theme 값이 반드시 들어가야 한다.
     *
     * @return 수정 조건과 수정 내용이 담긴 ReservationVO
     */
    public ReservationVO inputUpdateReservation() {

    	printTitle("예약 수정 입력");

    	String name = inputText("이름을 입력하세요 > ");
        String mobileNumber = inputText("수정할 예약 전화번호 입력 > ");
        String date = inputText("변경할 날짜 입력 > ");
        String theme = inputText("변경할 테마 입력 > ");

        return new ReservationVO(name, mobileNumber, date, theme);
        
    }

    
    /**
     * 예약 삭제 입력
     *
     * 현재 조회된 예약을 삭제할지 사용자에게 확인한다.
     *
     * @return true면 삭제 진행 menu=1 , false면 삭제 취소 menu=2
     */
    public boolean inputDeleteReservation() {
        

        printTitle("예약 삭제 입력");

        System.out.println("현재 조회된 예약을 삭제하시겠습니까?");
        System.out.println("1. 삭제");
        System.out.println("2. 취소");

        int menu = inputNumber("선택 : ");

        return menu == 1;
    }

    /**
     * 예약하기 
     *
     * 테마 조회 후 선택한 테마로 예약할지 확인한다.
     *
     * @return true면 예약 진행, false면 예약 취소
     */
    public boolean confirmReservation() {
        

        printTitle("예약 확인");

        System.out.println("선택한 테마로 예약하시겠습니까?");
        System.out.println("1. 예약하기");
        System.out.println("2. 취소");

        int menu = inputNumber("선택 : ");
        
        if(menu == 2)
        {
        	System.out.println("취소 되었습니다 목록으로 돌아갑니다");
        }

        return menu == 1;
    }

    /**
     * 예약 정보 입력
     *
     * 예약하기를 선택한 경우
     * 이름, 전화번호, 날짜, 테마를 입력받는다.
     *
     * @return 예약 정보가 담긴 ReservationVO
     */
    public ReservationVO inputReservation(ThemeVO selectedTheme) {
        

        printTitle("예약 정보 입력");

        String name = inputText("이름 입력 > ");
        String mobileNumber = inputText("전화번호 입력 > ");
        String date = inputText("예약 날짜 입력 > ");
        String theme = selectedTheme.getThemeName();

        return new ReservationVO(name, mobileNumber, date, theme);
    }

    /**
     * 예약 정보 결과 출력
     *
     * ReservationService.saveReservation() 기준:
     * flag == 1 : 예약 성공
     * flag == 2 : 이미 예약된 날짜/테마
     * 그 외      : 예약 실패
     *
     * @param flag Service에서 반환한 처리 결과
     */
    public void printReservationResult(int flag) {
        

        printTitle("예약 정보 결과");

        if (flag == 1) {
        	System.out.println("예약이 완료되었습니다.");
        } else if (flag == 2) {
        	System.out.println("이미 예약된 날짜와 테마입니다.");
        } else {
        	System.out.println("예약에 실패했습니다.");
        }
    }

    /**
     * 예약 수정 결과 출력
     *
     * ReservationService.updateReservation() 기준:
     * flag == 3 : 수정 성공
     * flag == 2 : 예약 없음
     * 그 외      : 수정 실패
     *
     * @param flag Service에서 반환한 처리 결과
     */
    public void printUpdateResult(int flag) {
        
        printTitle("예약 수정 결과");

        if (flag == 3) {
        	System.out.println("예약 수정이 완료되었습니다.");
        } else if (flag == 2) {
        	System.out.println("해당 전화번호의 예약 내역이 없습니다.");
        } else {
        	System.out.println("예약 수정에 실패했습니다.");
        }
    }

    /**
     * 예약 삭제 결과 출력
     *
     * ReservationService.deleteReservation() 기준:
     * flag == 1 : 삭제 성공
     * flag == 2 : 예약 없음
     * 그 외      : 삭제 실패
     *
     * @param flag Service에서 반환한 처리 결과
     */
    public void printDeleteResult(int flag) {
        

        printTitle("예약 삭제 결과");

        if (flag == 1) {
        	System.out.println("예약 삭제가 완료되었습니다.");
        } else if (flag == 2) {
        	System.out.println("해당 전화번호의 예약 내역이 없습니다.");
        } else {
        	System.out.println("예약 삭제에 실패했습니다.");
        }
    }

    /**
     * 예약 완료 안내 출력
     */
    public void printReserveComplete() {

        printTitle("예약 완료");
        
        System.out.print("예약 처리가 완료되었습니다.");
    }

    /**
     * 예약 완료 또는 예약 수정 완료 후 이동 메뉴
     *
     * 1. 메인메뉴
     * 2. 예약내역조회
     *
     * @return 사용자가 선택한 메뉴 번호
     */
    public int afterReservationMenu() {
        

        printTitle("다음 메뉴 선택");

        System.out.println("1. 메인메뉴");
        System.out.println("2. 예약내역조회");

        return inputNumber("메뉴 선택 > ");
    }

    

    /**
     * 숫자 입력 전용 메서드
     *
     * 메뉴 선택처럼 숫자가 필요한 경우 사용한다.
     * 숫자가 아닌 값을 입력하면 다시 입력받는다.
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
                System.out.println("숫자 입력 성공 - "+ number);
                return number;
            } catch (NumberFormatException e) {
            	System.out.println("숫자 입력 실패 - input: "+ input);
            	System.out.println("숫자만 입력해주세요.");
            }
        }
    }

    /**
     * 문자열 입력 전용 메서드
     *
     * 이름, 전화번호, 날짜, 테마처럼 문자열이 필요한 경우 사용한다.
     * 빈 값은 허용하지 않는다.
     *
     * @param message 입력 안내 문구
     * @return 입력받은 문자열
     */
    private String inputText(String message) {
        while (true) {
        	System.out.print(message);

            String input = sc.nextLine();

            if (input.isEmpty()) {
            	System.out.println("빈 문자열 입력");
            	System.out.println("값을 입력해주세요.");
                continue;
            }

            System.out.println("문자열 입력 성공 - "+ input);
            return input;
        }
    }

    /**
     * 화면 제목 출력
     *
     * @param title 화면 제목
     */
    private void printTitle(String title) {
        System.out.println();
        printLine();
        System.out.println(title);
        printLine();
    }

    /**
     * 구분선 출력
     */
    private void printLine() {
    	System.out.println("================================");
    }
	
	
	
	
	

}
