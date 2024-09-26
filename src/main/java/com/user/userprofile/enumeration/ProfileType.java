package com.user.userprofile.enumeration;

public enum ProfileType {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private int code;

    private String description;

    private ProfileType(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCod() {
        return code;
    }

    public void setCod(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ProfileType toEnum(Integer code) {
        if (code == null) {
            return null;
        }
        for (ProfileType tipo : ProfileType.values()) {
            if (code.equals(tipo.getCod())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Id inválido :" + code);
    }

}
