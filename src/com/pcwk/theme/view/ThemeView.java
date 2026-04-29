package com.pcwk.theme.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pcwk.theme.domain.ThemeVO;

public class ThemeView {

	Scanner sc = new Scanner(System.in);
	
	// 스캐너 종료 메서드
	public void scClose() {
		sc.close();
	}

	public void printTitle() {
	    System.out.println("    _                __   ___     _             _   __  __             ");
	    System.out.println(" _ | |__ ___ ____ _  \\ \\ / (_)_ _| |_ _  _ __ _| | |  \\/  |__ _ ______ ");
	    System.out.println("| || / _` \\ V / _` |  \\ V /| | '_|  _| || / _` | | | |\\/| / _` |_ / -_)");
	    System.out.println(" \\__/\\__,_|\\_/\\__,_|   \\_/ |_|_|  \\__|\\_,_\\__,_|_| |_|  |_\\__,_/__\\___|");
	    System.out.println("                                                                        ");
	}
	
	// 메인 메뉴
	public String mainMenu() {
	    printMainMenu();
	    return inputOnly();
	}

	private void printMainMenu() {
		System.out.println("┌─────────────────────────────────────────────────────────┐");
		System.out.println("│		     	  메인 메뉴	        	  │");
		System.out.println("└─────────────────────────────────────────────────────────┘");
		System.out.println("  1. 테마 조회");
		System.out.println("  2. 예약 조회");
		System.out.println("  0. 종료");
	}

	public String inputOnly() {
		printLine();
	    System.out.print("선택 > ");
	    String input = sc.nextLine();
	    System.out.println(); 
	    return input;
	}
	
	// 테마 조회
	public String themeMenu() {
	    printThemeMenu();
	    return inputOnly();
	}
	
	private void printThemeMenu() {
		System.out.println("┌─────────────────────────────────────────────────────────┐");
		System.out.println("│		       	  테마 조회  	      	 	  │");
		System.out.println("└─────────────────────────────────────────────────────────┘");
		System.out.println(" 1. 전체 목록 보기");
		System.out.println(" 2. 조건 검색");
		System.out.println("    └ 장르 / 지역 / 난이도 / 공포도");
		System.out.println("\n 0. 뒤로 가기");
	}

	// 1. 전체 목록 보기
	public void showAllThemes(List<ThemeVO> list) {
		System.out.println(" [ 전체 테마 목록 ]");
		printThemeList(list);
	}
	
	// 테마 리스트 출력 메서드
	public void printThemeList(List<ThemeVO> list) {
		System.out.println("┌────┬──────────────────┬────────┬──────┬────────┬────────┐");
		System.out.println("│번호│ 테마명		│  장르  │ 지역 │ 난이도 │ 공포도 │");
		System.out.println("├────┼──────────────────┼────────┼──────┼────────┼────────┤");
		for (int i = 0; i < list.size(); i++) {
			ThemeVO t = list.get(i);
			System.out.printf("│ %2d │ %s │ %s │ %s │   %s   │  %s  │%n", (i + 1),
					padKorean(t.getThemeName(), 16), padKorean(t.getGenre(), 6), t.getLocal(), t.getLevel(),
					t.getScare());
		}
		System.out.println("└────┴──────────────────┴────────┴──────┴────────┴────────┘\n");
	}
	
	// 한글 패딩 메서드 추가
	String padKorean(String str, int targetWidth) {
		int width = 0;
		for (char c : str.toCharArray()) {
			width += (c >= 0xAC00 && c <= 0xD7A3) ? 2 : 1; // 한글이면 2칸
		}
		int padding = targetWidth - width;
		return str + " ".repeat(Math.max(0, padding));
	}

	// 조건 검색 유효성 검사 + 재입력 메서드
	String inputWithValidation(String prompt, String[] validOptions) {
		while (true) {
			System.out.print(prompt);
			String input = sc.nextLine().trim();

			if (input.isEmpty())
				return input; // 엔터면 조건 없음으로 통과

			for (String valid : validOptions) {
				if (valid.equals(input))
					return input; // 유효한 값이면 통과
			}

			// 유효하지 않은 값이면 오류 메시지 출력 후 재입력
			System.out.println("  ※ 올바르지 않은 입력입니다. (" + String.join(", ", validOptions) + ") 중에서 입력해주세요.");
		}
	}

	// 조건을 입력받고 그 값을 condition에 담아 반환
	public ThemeVO searchTheme() {

		printLine();
		System.out.println(" [ 검색 조건 입력 ]  ※ 입력하지 않으면 전체 조건으로 조회\n");
		ThemeVO condition = new ThemeVO();

		condition.setGenre(inputWithValidation("  장르 (공포, 추리, 판타지) > ", new String[] { "공포", "추리", "판타지" }));
		condition.setLocal(inputWithValidation("  지역 (서울, 경기, 그 외) > ", new String[] { "서울", "경기", "그 외" }));
		condition.setLevel(inputWithValidation("  난이도 (상, 중, 하)  > ", new String[] { "상", "중", "하" }));
		condition.setScare(inputWithValidation("  공포도 (높음, 낮음)  > ", new String[] { "높음", "낮음" }));
		printLine();
		
		return condition;
	}
	
	public void showSearchTheme(List<ThemeVO> list, ThemeVO condition) {
		
		System.out.println(" [ 테마 검색 결과 ] \n");
		
		// 입력했던 조건을 String리스트에 담아서 출력하는 용도
		List<String> conditions = new ArrayList<>();
		
		// 비어있는 조건은 넘어가고 입력받은 조건만 리스트에 담음
		if (!condition.getGenre().isEmpty())
			conditions.add("장르: " + condition.getGenre());
		if (!condition.getLocal().isEmpty())
			conditions.add("지역: " + condition.getLocal());
		if (!condition.getLevel().isEmpty())
			conditions.add("난이도: " + condition.getLevel());
		if (!condition.getScare().isEmpty())
			conditions.add("공포도: " + condition.getScare());
		
		// 입력한 조건 출력
		if (conditions.isEmpty()) {
	        System.out.println("  검색 조건 (전체)");
	    } else {
	        System.out.println("  검색 조건 (" + String.join(" / ", conditions) + ")");
	    }
		
	    System.out.println();
		
		// 조건에 맞는 테마들을 담은 리스트를 출력할 메서드 호출
		printThemeList(list);
		
	}
	
	public void printNoSearchResult() {
        System.out.println("\n  ※ 검색 결과가 없습니다.");
        return; // 테마 조회 메뉴로 복귀
	}

	// 테마 선택
	public ThemeVO selectTheme(List<ThemeVO> list) {
	    
	    ThemeVO selected = null; // 루프 밖에서 선언
	    
	    printLine();
	    System.out.println(" [ 테마 예약 ] ※ 0. 뒤로가기\n");
        
	    while (true) {
	        
	        System.out.print(" 테마 번호 입력 > ");
	        String input = sc.nextLine().trim();
	        System.out.println();
	        
	        // case 0. 뒤로가기
	        if (input.equals("0"))
	        	return null;
	        
	        // case 1. 숫자가 아닌 입력
	        int idx;
	        try {
	            idx = Integer.parseInt(input) - 1;
	        } catch (NumberFormatException e) {
	            System.out.println("  ※ 숫자를 입력해주세요.");
	            continue; // 루프 처음으로
	        }
	        
	        // case 2. 범위를 벗어난 번호
	        if (idx < 0 || idx >= list.size()) {
	            System.out.println("  ※ 1 ~ " + list.size() + " 사이의 번호를 입력해주세요.");
	            continue; // 루프 처음으로
	        }
	        
	        // case 3. 정상 입력 - 여기까지 오면 유효한 값
	        selected = list.get(idx);
	        
	        printLine();
	        System.out.println(" [ 선택한 테마 ]\n");
	        System.out.println("  테마명 : " + selected.getThemeName());
	        System.out.println("  장르   : " + selected.getGenre());
	        System.out.println("  지역   : " + selected.getLocal());
	        System.out.println("  난이도 : " + selected.getLevel());
	        System.out.println("  공포도 : " + selected.getScare());
	        printLine();
	        
	        return selected;
	    }
	}
	
	/**
	 * 구분선 출력
	 */
	private void printLine() {
		System.out.println("───────────────────────────────────────────────────────────");
	}

}
