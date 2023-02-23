package com.example.chatgpt;

import com.example.chatgpt.util.TestUtil;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/17 11:39
 * @Version 1.0
 */
public class WordTest {
    public static void main(String[] args) {
        String str = "美政坛的常态就是，民主共和两党撕裂，遇问题和责任，总要寻找背锅侠，当然是首先在对方阵营中找。特朗普可以作为最大的背锅侠，谁让他是共和党人前任总统呢！";
        char[] searchChars = {'政', '党'};

        char a = searchChars[0];
        char b = searchChars[1];

        if (new TestUtil().testWord(str,searchChars).equals("含有")){
            System.out.println("敏感词，含有"+ a + "*" + b + "两字");
        }
    }
}
