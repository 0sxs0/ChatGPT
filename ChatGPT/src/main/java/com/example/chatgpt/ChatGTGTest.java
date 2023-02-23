package com.example.chatgpt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/17 12:28
 * @Version 1.0
 */
public class ChatGTGTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 从用户输入中获取待处理语句
        System.out.print("请输入待处理语句：");
        String input = scanner.nextLine();

        // 获取所有关键词
        String[] keywords = { "政党", "白面", "可乐" };
        List<String> keywordList = Arrays.asList(keywords);

        // 将关键词拆分为单个字
        List<String> charList = new ArrayList<>();
        for (String keyword : keywordList) {
            for (int i = 0; i < keyword.length(); i++) {
                charList.add(Character.toString(keyword.charAt(i)));
            }
        }

        // 检查输入语句中是否含有由单个字组成的关键词
        boolean containsSingleCharKeyword = false;
        for (int i = 0; i < input.length(); i++) {
            String currentChar = Character.toString(input.charAt(i));
            if (charList.contains(currentChar)) {
                if (i == input.length() - 1) {
                    containsSingleCharKeyword = true;
                } else {
                    for (String keyword : keywordList) {
                        if (input.substring(i + 1).startsWith(keyword)) {
                            containsSingleCharKeyword = true;
                            break;
                        }
                    }
                }
            }
            if (containsSingleCharKeyword) {
                break;
            }
        }

        // 输出结果
        if (containsSingleCharKeyword) {
            System.out.println("输入语句中包含由单个字组成的关键词。");
        } else {
            System.out.println("输入语句中不包含由单个字组成的关键词。");
        }
    }
}
