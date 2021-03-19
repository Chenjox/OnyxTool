package Onyx.Util;

import java.util.ArrayList;
import java.util.List;

public abstract class StringUtils {
    public static int CountCharsInString(String testString, char testChar){
        int occurrences = 0;
        for (int i = 0; i < testString.length(); i++) {
            if(testString.charAt(i)==testChar) occurrences++;
        }
        return occurrences;
    }

    public static List<String> TokenizePatternsInString(String testString, char leftChar, char rightChar){
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < testString.length(); i++) {
            if(testString.charAt(i)==leftChar){
                for (int j = i; j < testString.length(); j++) {
                    if(testString.charAt(j)==rightChar){
                        result.add(testString.substring(i+1, j));
                        i=j;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "(Hallo),(Ich bin das),(und so weiter)";
        System.out.println(TokenizePatternsInString(s,'(',')'));
    }
}
