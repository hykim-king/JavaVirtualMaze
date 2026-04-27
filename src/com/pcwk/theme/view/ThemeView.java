package com.pcwk.theme.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pcwk.theme.domain.ThemeVO;

public class ThemeView {

	Scanner sc = new Scanner(System.in);

	// 메인 메뉴
	public String mainMenu() {
		while (true) {
			System.out.println("\n========== 메인 메뉴 ==========");
			System.out.println("1. 테마 조회");
			System.out.println("2. 예약 조회");
			System.out.println("0. 종료");
			System.out.print("선택 >> ");
			String input =  sc.nextLine().trim();
			
			switch (input) {
			case "1":
			case "2":
			case "0":
				return input;
			default:
				System.out.println("\n잘못된 입력입니다. 1, 2, 0 중에서 선택해주세요");
			}
		}
	}

	// 테마 조회
	public String themeMenu() {
		while (true) {
			System.out.println("\n-- 테마 조회 --");
			System.out.println("1. 전체 목록 보기");
			System.out.println("2. 검색 조건 선택 (장르, 지역, 난이도, 공포도)");
			System.out.println("0. 메인 메뉴로");
			System.out.print("선택 >> ");
			
			String input = sc.nextLine().trim();

			switch (input) {
			case "1":
			case "2":
			case "0":
				return input;
			default:
				System.out.println("\n잘못된 입력입니다. 1, 2, 0 중에서 선택해주세요");
			}
		}
	}

//	 전체 목록 출력 후 선택
	public void showAllThemes(List<ThemeVO> list) {
		System.out.println("\n[전체 테마 목록]");
		printThemeList(list);
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
		System.out.println("\n[검색 조건 입력] (없으면 엔터)");
		ThemeVO condition = new ThemeVO();

		condition.setGenre(inputWithValidation("* 장르 (공포, 추리, 판타지): ", new String[] { "공포", "추리", "판타지" }));
		condition.setLocal(inputWithValidation("* 지역 (서울, 경기, 그 외): ", new String[] { "서울", "경기", "그 외" }));
		condition.setLevel(inputWithValidation("* 난이도 (상, 중, 하): ", new String[] { "상", "중", "하" }));
		condition.setScare(inputWithValidation("* 공포도 (높음, 낮음): ", new String[] { "높음", "낮음" }));

		return condition;
	}
	
	
	public void showSearchTheme(List<ThemeVO> list, ThemeVO condition) {
		
		System.out.println("\n[검색 결과]");
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
		System.out.println("* " + String.join(", ", conditions));
		System.out.println();
		
		// 조건에 맞는 테마들을 담은 리스트를 출력할 메서드 호출
		printThemeList(list);
		
	}
	
	public void printNoSearchResult() {
        System.out.println("\n검색 결과가 없습니다.");
        return; // 테마 조회 메뉴로 복귀
	}

	// 전체 테마 목록 출력 및 조건 검색 테마 리스트 출력 메서드
	public void printThemeList(List<ThemeVO> list) {
		System.out.println("---------------------------------------------------------------------------------");
		for (int i = 0; i < list.size(); i++) {
			ThemeVO t = list.get(i);
			System.out.printf("%2d. %s │ 장르: %s │ 지역: %-4s │ 난이도: %-2s │ 공포도: %s%n", (i + 1),
					padKorean(t.getThemeName(), 16), padKorean(t.getGenre(), 7), t.getLocal(), t.getLevel(),
					t.getScare());
		}
		System.out.println("---------------------------------------------------------------------------------");
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

	// 테마 선택
	public ThemeVO selectTheme(List<ThemeVO> list) {
		while(true)
		{
			System.out.println("\n [테마 선택]");
			System.out.print("\n테마를 선택하세요 (번호, 0=돌아가기): ");
			ThemeVO selected;
			String input = sc.nextLine().trim();
			// 0 입력시 돌아감
			if (input.equals("0"))
				return null;

			try {
				// 선택한 테마 번호를 인덱스에 맞게 -1 한 값을 idx에 넣어줌
				int idx = Integer.parseInt(input) - 1;
				if (idx >= 0 && idx < list.size()) {
					// 리스트에 담겨있는 테마를 인덱스번호로 찾아 selected에 넣음
					selected = list.get(idx);
					System.out.println("\n[선택한 테마]");
					System.out.printf("  테마명: %s │ 장르: %s │ 지역: %s │ 난이도: %s │ 공포도: %s%n", list.get(idx).getThemeName(),
						list.get(idx).getGenre(), list.get(idx).getLocal(), list.get(idx).getLevel(), list.get(idx).getScare());
					return selected;
				} else {
					System.out.println("올바른 번호를 입력해주세요. (1 ~ " + list.size() + " 사이)");
				}
			} catch (NumberFormatException e) {
				System.out.println("테마에 해당하는 번호를 입력해주세요.");
			}
		}
	}

}
