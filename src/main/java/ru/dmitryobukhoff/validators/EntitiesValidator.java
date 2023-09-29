package ru.dmitryobukhoff.validators;

import java.util.Arrays;
import java.util.HashSet;

public class EntitiesValidator {

    private final HashSet<Character> signs = new HashSet<>(Arrays.asList('.', ',', '!', '@', ';', '?', '#',' '));

    public boolean isLoginValid(String login){
        if(login.length() < 4 || login.length() > 20)
            return false;
        for(int i = 0; i < login.length(); i++){
            if(signs.contains(login.charAt(i)))
                return false;
        }
        return true;
    }
}
