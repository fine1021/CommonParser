package com.yxkang.android.commonparser.sample;

import com.yxkang.android.commonparser.annotation.MsgItemField;
import com.yxkang.android.commonparser.annotation.MsgListField;

import java.util.List;

/**
 * Created by yexiaokang on 2016/6/22.
 */
public class UserResponse {

    @MsgListField(value = "users")
    @MsgItemField(value = "user")
    private List<User> users;

    @MsgItemField(value = "user")
    private List<User> list;

    @MsgItemField(value = "data")
    private Size size;

    public UserResponse() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "users=" + users +
                ", list=" + list +
                ", size=" + size +
                '}';
    }

    public class User {

        @MsgItemField
        private String name;
        @MsgItemField
        private int age;
        @MsgItemField
        private String sex;

        public User() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "User {" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }
}
