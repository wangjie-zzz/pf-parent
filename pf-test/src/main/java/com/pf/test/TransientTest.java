package com.pf.test;

import com.pf.util.JacksonsUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;

/**
 * @ClassName : TransientTest
 * @Description :
 * @Author : wangjie
 * @Date: 2020/12/18-16:42
 */
public class TransientTest {

    final static String filename = "D://AATest/transienttest";
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User();
        user.setUserId("admin");
        user.setUserName("wangjie");
        user.setUserAge(24);
        /*文件*/
        /*ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(user);
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        User user1 = (User)ois.readObject();*/

        /*byte[] bytes = JacksonsUtils.writeValueAsBytes(user);
        User user1 = JacksonsUtils.readValue(bytes, User.class);*/



//        System.out.println(user1.toString());
    }
}
