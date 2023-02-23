package com.example.chatgpt.controller;

import com.example.chatgpt.dto.ResponseModel;
import com.example.chatgpt.interceptor.UserLimiter;
import com.example.chatgpt.service.UserService;
import com.example.chatgpt.util.SensitiveFilter;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/15 18:16
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping({"message"})
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    private static final int NUM_STRINGS = 50;
    private static final int LENGTH = 10;
    public static List<Object> list = new ArrayList();
    public static List<Object> test = new ArrayList();
    public static int i = -1;

    @Autowired
    private ChatgptService chatgptService;
    @Autowired
    private UserService userService;
    private int num = 1500;

    public Controller() {
    }

    @GetMapping({"/send"})
    public ResponseModel send(HttpServletRequest request, @RequestParam String message) throws IOException {
        SensitiveFilter filter = SensitiveFilter.getInstance();
        int n = filter.CheckSensitiveWord(message, 0, 1);
        if (n > 0) {
            return ResponseModel.fail("存在敏感词，请重新输入");
        } else {
            String requestId = UUID.randomUUID().toString();
            if (!StringUtils.hasText(message)) {
                return ResponseModel.fail("message can not be blank");
            } else {
                try {
                    if (message.indexOf("阚佩祯") != -1){
                        return ResponseModel.success("阚佩祯是傻狗");
                    }
                    if (message.indexOf("孙鑫山") != -1 || message.indexOf("孙家岭") != -1){
                        return ResponseModel.success("阚佩祯是傻狗");
                    }
                    String responseMessage = this.chatgptService.sendMessage(message);
                    this.reduce(message);
                    int indexToRemove = 1;
                    String Message = responseMessage.substring(indexToRemove + 1);
                    return ResponseModel.success(Message);
                } catch (Exception e) {
                    log.error("requestId {}, ip {}, error", requestId, request.getRemoteHost(), e);
                    String errorString = e.getMessage();
                    System.out.println(errorString.length());
                    if (errorString.length() == 153){
                        return ResponseModel.IOErrorPOST("POST请求时出现I/O错误。对此很抱歉！");
                    }
                    if (errorString.length() == 233){
                        return ResponseModel.TooManyRequests("太多请求：服务器在处理您的请求时出错。对此很抱歉！");
                    }
                    if (errorString.length() == 551 || errorString.length() == 406){
                        return ResponseModel.TooManyRequests("太多请求：服务器在处理您的请求时出错。对此很抱歉！");
                    }
                    return ResponseModel.fail(e.getMessage());
                }
            }
        }
    }

    @GetMapping({"reduce"})
    public int reduce(String message) {
        if (message != null) {
            --this.num;
            return this.num;
        } else {
            return this.num;
        }
    }

    @RequestMapping({"login"})
    public int login(int DetectionPage) {
        long time = 900000L;
        if (DetectionPage == 0 && list.size() < 50) {
            String randomString = UUID.randomUUID().toString().substring(0, 10);
            list.add(randomString);
            this.createAccount(randomString, time);
            ++i;
        }

        Object user = list.get(i);
        String randomString = list.toString();
        if (test.size() == list.size() && DetectionPage != 0) {
            System.out.println("用户正在执行刷新操作");
            System.out.println("-------------------------");
            return list.size();
        } else {
            test.clear();
            test.addAll(list);
            boolean isAdded = UserLimiter.addUser(randomString);
            if (isAdded) {
                System.out.println("用户 " + user + " 成功加入");
                System.out.println("现在所有用户：" + randomString);
                System.out.println("-------------------------");
                return list.size();
            } else {
                System.out.println("在线用户数量已达到最大值， 暂时无法加入");
                System.out.println("-------------------------");
                return list.size() + 1;
            }
        }
    }

    @GetMapping({"logout"})
    public void logout(int member) {
        if (member >= 3) {
            List<String> stringSet = this.userService.getList();
            UserLimiter.removeUser((String)stringSet.get(member));
            stringSet.remove(member);
            System.out.println(stringSet);
        }

    }

    public void createAccount(String username, long lifespan) {
        new Account(username, lifespan);
    }

    public void jian() {
        list.remove(0);
        System.out.println("过时：" + list.size());
    }

    private class Account {
        private String username;
        private Date creationTime;
        private long lifespan;
        public Account(String username, final long lifespan) {
            this.username = username;
            this.creationTime = new Date();
            this.lifespan = lifespan;
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    if ((new Date()).getTime() - Account.this.creationTime.getTime() > lifespan) {
                        jian();
                        --i;
                        timer.cancel();
                    }

                }
            }, 0L, 1000L);
        }
    }
}
