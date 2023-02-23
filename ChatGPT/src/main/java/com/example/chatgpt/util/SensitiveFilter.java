package com.example.chatgpt.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/15 18:20
 * @Version 1.0
 */
public class SensitiveFilter {
    private Map sensitiveWordMap = null;
    public static int minMatchType = 1;
    public static int maxMatchType = 3;
    private static SensitiveFilter instance = null;

    private SensitiveFilter() throws IOException {
        this.sensitiveWordMap = (new SensitiveWordInit()).initKeyWord();
    }

    public static SensitiveFilter getInstance() throws IOException {
        if (null == instance) {
            instance = new SensitiveFilter();
        }

        return instance;
    }

    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet();

        for(int i = 0; i < txt.length(); ++i) {
            int length = this.CheckSensitiveWord(txt, i, matchType);
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;
            }
        }

        return sensitiveWordList;
    }

    public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
        String resultTxt = txt;
        Set<String> set = this.getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;

        for(String replaceString = null; iterator.hasNext(); resultTxt = resultTxt.replaceAll(word, replaceString)) {
            word = (String)iterator.next();
            replaceString = this.getReplaceChars(replaceChar, word.length());
        }

        return resultTxt;
    }

    private String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;

        for(int i = 1; i < length; ++i) {
            resultReplace = resultReplace + replaceChar;
        }

        return resultReplace;
    }

    public int CheckSensitiveWord(String txt, int beginIndex, int matchType) {
        Pattern p = Pattern.compile("[一-龥]");
        Matcher m = p.matcher(txt);
        if (!m.find()) {
            return "1989".equals(txt) ? 2 : 0;
        } else {
            boolean flag = false;
            int matchFlag = 0;
            Map nowMap = this.sensitiveWordMap;

            for(int i = beginIndex; i < txt.length(); ++i) {
                char word = txt.charAt(i);
                nowMap = (Map)nowMap.get(word);
                if (nowMap == null) {
                    break;
                }

                ++matchFlag;
                if ("1".equals(nowMap.get("isEnd"))) {
                    flag = true;
                    if (minMatchType == matchType) {
                        break;
                    }
                }
            }

            if (maxMatchType == matchType && (matchFlag < 2 || !flag)) {
                matchFlag = 0;
            }

            if (minMatchType == matchType && matchFlag < 2 && !flag) {
                matchFlag = 0;
            }

            return matchFlag;
        }
    }
}
