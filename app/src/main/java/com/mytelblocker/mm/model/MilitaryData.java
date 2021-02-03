package com.mytelblocker.mm.model;

public class MilitaryData {
    private String englishName, burmeseName;

    public MilitaryData(String englishName, String burmeseName) {
        this.englishName = englishName;
        this.burmeseName = burmeseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getBurmeseName() {
        return burmeseName;
    }

}
