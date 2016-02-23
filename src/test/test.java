package test;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by ht on 2016/2/20.
 */
public class test {

    public static void main(String[] args) {
        char c = 'b';

//        System.out.println(generateChar(c, 58, 64));
//        System.out.println(generateChar(c, 91, 96));
        System.out.println(generateChar(c, 123, 126));
//        System.out.println(generateChar(c, 33, 47));

    }

    private static char generateChar(char aChar, int low, int high) {
        char c = 'A';
        int a = aChar;
        if (a >= low && a <= high) {
            c = (char) a;
            return c;
        } else if (a < low) {
            return generateChar((char) (a + 2), low, high);
        } else if (a > high) {
            return generateChar((char) (a - 2), low, high);
        }
        return c;
    }

}