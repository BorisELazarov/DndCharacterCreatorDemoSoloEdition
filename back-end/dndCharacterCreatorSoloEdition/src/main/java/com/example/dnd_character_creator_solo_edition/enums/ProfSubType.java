package com.example.dnd_character_creator_solo_edition.enums;

public enum ProfSubType {
    NONE(ProfType.NONE),

    ARTISAN(ProfType.TOOL),
    GAMING(ProfType.TOOL),
    MUSICAL(ProfType.TOOL),
    VEHICLE(ProfType.TOOL),
    MISCELLANEOUS(ProfType.TOOL),

    LIGHT(ProfType.ARMOR),
    MEDIUM(ProfType.ARMOR),
    HEAVY(ProfType.ARMOR),

    SIMPLE(ProfType.WEAPON),
    MARTIAL(ProfType.WEAPON);

    private ProfType type;

    ProfSubType(ProfType type) {
        this.type = type;
    }

    public ProfType getType() {
        return type;
    }
}
