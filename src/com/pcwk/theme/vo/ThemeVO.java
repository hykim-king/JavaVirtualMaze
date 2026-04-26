package com.pcwk.theme.vo;

public class ThemeVO {
    private String themeName;
    private String genre;   // 장르
    private String local;   // 지역
    private String level;   // 난이도
    private String scare;   // 공포도

    public ThemeVO() {}

    public ThemeVO(String themeName, String genre, String local, String level, String scare) {
        this.themeName = themeName;
        this.genre = genre;
        this.local = local;
        this.level = level;
        this.scare = scare;
    }

    public String getThemeName() { return themeName; }
    public void setThemeName(String themeName) { this.themeName = themeName; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getScare() { return scare; }
    public void setScare(String scare) { this.scare = scare; }

    @Override
    public String toString() {
        return "[테마명: " + themeName + " │ 장르: " + genre +
               " │ 지역: " + local + " │ 난이도: " + level + " │ 공포도: " + scare + "]";
    }
}
