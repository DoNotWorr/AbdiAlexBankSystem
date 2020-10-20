package org.example;


/**
 * @Author Abdi
 *
 */

import java.util.Scanner;

public class SingletonInput {
    private  static SingletonInput instance = null;

    public Scanner scanner = new Scanner(System.in);

    private SingletonInput(){

    }
    public static SingletonInput getInstance() {
        if (instance == null) {

            instance =  new SingletonInput();
        }
        return instance;
    }

}

