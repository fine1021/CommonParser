package com.yxkang.android.commonparser.sample;

import com.yxkang.android.commonparser.annotation.MsgItemField;
import com.yxkang.android.commonparser.annotation.MsgListField;
import com.yxkang.android.xmlparser.annotation.Attribute;
import com.yxkang.android.xmlparser.annotation.Document;
import com.yxkang.android.xmlparser.annotation.Element;
import com.yxkang.android.xmlparser.annotation.ElementList;
import com.yxkang.android.xmlparser.annotation.Namespace;
import com.yxkang.android.xmlparser.annotation.NamespaceList;

import java.util.List;

/**
 * Created by yexiaokang on 2016/6/22.
 */
@Document(name = "root")
@NamespaceList({@Namespace(namespaceURI = "http://www.w3.org/TR/html4/", prefix = "h"), @Namespace(namespaceURI = "http://www.w3schools.com/furniture", prefix = "f")})
public class UserResponse {

    @ElementList(name = "users")
    @Element(name = "user")
    @MsgListField(value = "users")
    @MsgItemField(value = "user")
    private List<User> users;

    @ElementList(name = "user")
    @MsgListField(value = "user")
    private List<User> list;

    @Element(name = "data", itemName = "resolution")
    @MsgItemField(value = "data")
    private Size size;

    @Namespace(prefix = "xsl", requiredPrefix = true)
    @Element(name = "table")
    private String value;

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

    @Namespace(prefix = "h", requiredPrefix = true)
    public static class User extends _User {


    }

    private static class _User {

        @Attribute
        private int id;

        @Element
        @MsgItemField
        private String name;
        @Element
        @MsgItemField
        private int age;
        @Element
        @MsgItemField
        private String sex;

        public _User() {
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
            return "_User {" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }
}
