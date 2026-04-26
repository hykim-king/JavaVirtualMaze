package Dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import VO.ThemeVO;

public class ThemeDao {

    private List<ThemeVO> themeList = new ArrayList<>();

    public ThemeDao(String csvPath) {
        loadFromCSV(csvPath);
    }

    // CSV 파일에서 테마 데이터 로드
    private void loadFromCSV(String csvPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line = "";
			int i = 1;
            while ((line = br.readLine()) != null) {
                if (i == 1) {  // header skip
                    i++;
                    continue; 
                }
                String[] strArray = line.split(",");
                if (strArray.length == 5) {
                    themeList.add(new ThemeVO(
                        strArray[0].trim(), //trim() -> 공백문자오류제거
                        strArray[1].trim(),
                        strArray[2].trim(),
                        strArray[3].trim(),
                        strArray[4].trim()
                    ));
                }
            }
            System.out.println("[LOG] 테마 데이터 로드 완료: " + themeList.size() + "개");
        } catch (IOException e) {
            System.out.println("[ERROR] CSV 파일을 불러올 수 없습니다: " + csvPath);
        }
    }

    // 전체 목록 조회
    public List<ThemeVO> findAll() {
        return themeList;
    }

    // 조건 검색 (빈 값이면 해당 조건 무시)
    public List<ThemeVO> findByCondition(ThemeVO condition) {
        List<ThemeVO> result = new ArrayList<>();
        for (ThemeVO theme : themeList) {
            boolean match = true;
            if (condition.getGenre() != null && !condition.getGenre().isEmpty()
                && !theme.getGenre().equals(condition.getGenre())) match = false;
            if (condition.getLocal() != null && !condition.getLocal().isEmpty()
                && !theme.getLocal().equals(condition.getLocal())) match = false;
            if (condition.getLevel() != null && !condition.getLevel().isEmpty()
                && !theme.getLevel().equals(condition.getLevel())) match = false;
            if (condition.getScare() != null && !condition.getScare().isEmpty()
                && !theme.getScare().equals(condition.getScare())) match = false;
            if (match) result.add(theme);
        }
        return result;
    }

    // 테마명으로 단건 조회
    public ThemeVO findByName(String themeName) {
        for (ThemeVO theme : themeList) {
            if (theme.getThemeName().equals(themeName)) return theme;
        }
        return null;
    }
}
