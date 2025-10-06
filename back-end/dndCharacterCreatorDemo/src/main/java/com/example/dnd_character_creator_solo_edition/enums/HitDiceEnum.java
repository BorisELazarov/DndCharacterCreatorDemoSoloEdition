package com.example.dnd_character_creator_solo_edition.enums;

public enum HitDiceEnum {
    NONE(""),
    D6("D6"),
    D8("D8"),
    D10("D10"),
    D12("D12");

    public final String title;

    HitDiceEnum(String title) {
        this.title =title;
    }
}
