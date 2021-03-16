package Onyx.Util;

public abstract class StringUtils {
    public static int CountCharsInString(String testString, char testChar){
        int occurrences = 0;
        for (int i = 0; i < testString.length(); i++) {
            if(testString.charAt(i)==testChar) occurrences++;
        }
        return occurrences;
    }
}
