package it.marx.kingfisher.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import it.marx.kingfisher.exceptions.ConversionException;

public enum GameStatusEnum {

    RELEASED("0", 0),
    ALPHA("2", 2),
    BETA("3", 3),
    EARLY_ACCESS("4", 4),
    OFFLINE("5", 5),
    CANCELLED("6", 6),
    RUMORED("7", 7),
    DELISTED("8", 8);

    public final String code;
    public final int codeInt;

    GameStatusEnum(String code, int codeInt) {
        this.code = code;
        this.codeInt = codeInt;
    }

    public int getCodeInt() {
        return this.codeInt;
    }

    @JsonCreator
    public static GameStatusEnum fromCode(String code) {
        for (GameStatusEnum status : GameStatusEnum.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new ConversionException(String.format("Errore nel convertire lo status %s", code));
    }

}
