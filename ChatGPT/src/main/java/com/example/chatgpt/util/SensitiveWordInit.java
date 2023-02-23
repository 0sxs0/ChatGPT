package com.example.chatgpt.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/15 18:20
 * @Version 1.0
 */
@Configuration
public class SensitiveWordInit {
    private String ENCODING = "UTF-8";

    public SensitiveWordInit() {
    }

    public Map initKeyWord() throws IOException {
        Set<String> wordSet = this.readSensitiveWordFile();
        return this.addSensitiveWordToHashMap(wordSet);
    }

    private Set<String> readSensitiveWordFile() throws IOException {
        Set<String> wordSet = null;
        ClassPathResource classPathResource = new ClassPathResource("test.txt");
        InputStream inputStream = classPathResource.getInputStream();

        try {
            InputStreamReader read = new InputStreamReader(inputStream, this.ENCODING);
            wordSet = new HashSet();
            BufferedReader br = new BufferedReader(read);
            String txt = null;

            while((txt = br.readLine()) != null) {
                wordSet.add(txt);
            }

            br.close();
            read.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return wordSet;
    }

    private Map addSensitiveWordToHashMap(Set<String> wordSet) {
        Map wordMap = new HashMap(wordSet.size());
        Iterator var3 = wordSet.iterator();

        while(var3.hasNext()) {
            String word = (String)var3.next();
            Map nowMap = wordMap;

            for(int i = 0; i < word.length(); ++i) {
                char keyChar = word.charAt(i);
                Object tempMap = ((Map)nowMap).get(keyChar);
                if (tempMap != null) {
                    nowMap = (Map)tempMap;
                } else {
                    Map<String, String> newMap = new HashMap();
                    newMap.put("isEnd", "0");
                    ((Map)nowMap).put(keyChar, newMap);
                    nowMap = newMap;
                }

                if (i == word.length() - 1) {
                    ((Map)nowMap).put("isEnd", "1");
                }
            }
        }

        return wordMap;
    }
}

