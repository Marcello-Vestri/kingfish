package it.marx.kingfisher.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import it.marx.kingfisher.exceptions.ConversionException;

public enum GameTypeEnum {
    MAIN_GAME("0", 0),
    DLC_ADDON("1", 1),
    EXPANSION("2", 2),
    BUNDLE("3", 3),
    STANDALONE_EXPANSION("4", 4),
    MOD("5", 5),
    EPISODE("6", 6),
    SEASON("7", 7),
    REMAKE("8", 8),
    REMASTER("9", 9),
    EXPANDED_GAME("10", 10),
    PORT("11", 11),
    FORK("12", 12),
    PACK("13", 13),
    UPDATE("14", 14);

    public final String code;
    public final int codeInt;

    GameTypeEnum(String code, int codeInt) {
        this.code = code;
        this.codeInt = codeInt;
    }

    @JsonCreator
    public static GameTypeEnum fromValue(String value) {
        for (GameTypeEnum type : GameTypeEnum.values()) {
            if (type.code.equals(value)) {
                return type;
            }
        }
        throw new ConversionException(String.format("Errore nel convertire il tipo %s", value));
    }
}
