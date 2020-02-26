package com.app.model.enums;

import java.util.Random;

public enum Genre {

    ACTION, HORROR, FANTASY, DRAMA, COMEDY;

    Genre() {
    }

    public static Genre getRandomGenre() {
        int size = Genre.values().length;
        return Genre.values()[new Random().nextInt(size)];
    }
}



