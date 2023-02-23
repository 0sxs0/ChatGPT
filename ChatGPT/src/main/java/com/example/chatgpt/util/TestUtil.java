package com.example.chatgpt.util;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/16 18:46
 * @Version 1.0
 */
public class TestUtil {
    public String testWord(String word,char[] username){
        String str = word;
        char[] searchChars = username;

        boolean found = false;

        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < searchChars.length; j++) {
                if (str.charAt(i) == searchChars[j]) {
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        if (found) {
            return "含有";
        } else {
            return "不含有";
        }
    }
}
