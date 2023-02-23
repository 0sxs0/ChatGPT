package com.example.chatgpt;


import com.example.chatgpt.util.TestUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/16 18:30
 * @Version 1.0
 */
public class test {
    public static void main(String[] args) throws IOException {
        String filename = "E:/PersonalProject/ChatGPT/src/main/resources/test.txt";
        String line;
        ArrayList<String> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> charList = new ArrayList<>();
        ArrayList<String> charList1 = new ArrayList<>();
        System.out.println(list);
        for (int i = 0; i < list.size() ;i++) {
            charList.add(list.get(i));
            if (charList.size() <2 ) {
                for (String str : charList) {
                    char[] chars = str.toCharArray();
                    for (char c : chars) {
                        charList1.add(String.valueOf(c));
                    }
                }
            }
        }

        System.out.println(charList);
        System.out.println(charList1);

        System.out.println("-------------------------------");

        String str = "天气，安定，门口";
        char[] searchChars = {'天', '安', '门'};

        if (new TestUtil().testWord(str,searchChars).equals("含有")){
            System.out.println("敏感词");
        }

        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<List> list4 = new ArrayList<>();
        list2.add(a);
        list2.add(b);
        list4.add(list2);
        list3.add(c);
        list3.add(d);
        list4.add(list3);
        System.out.println(list4);

        char[] charArray = str.toCharArray();

        for (int i = 0; i < list4.size(); i++) {
            for (int j = 0; j < list4.get(i).size(); j++) {
                String str1 = String.join("", list4.get(j));
                System.out.println(str1);
                char[] charArray1 = str1.toCharArray();
            }
        }

        //获取所有关键词
        //把关键词分为单个字
        //查看语句中是否含有由单个字组成的关键词
    }
}
