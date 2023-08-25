package com.roommate.roommate.user.domain;

import com.roommate.roommate.exception.CustomException;
import lombok.Getter;

import static com.roommate.roommate.exception.ExceptionCode.*;

public enum Role {
    ROLE_USER('U'),
    ROLE_ADMIN('A'),
    ROLE_SYS('S');

    @Getter
    Character type;

    Role(Character type) {
        this.type = type;
    }

    public static Role validateEnum(String role) {
        try {
            return Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ROLE_CHARACTER_INVALID);
        } catch (NullPointerException e) {
            throw new CustomException(FIELD_REQUIRED);
        }
    }

    public static String convertEnum(Character type) {
        String role = null;
        switch (type) {
            case 'U':
                role = String.valueOf(ROLE_USER);
                break;
            case 'S':
                role = String.valueOf(ROLE_SYS);
                break;
            case 'A':
                role = String.valueOf(ROLE_ADMIN);
                break;
            default:
                throw new CustomException(SERVER_ERROR);
        }
        return role;
    }

}
