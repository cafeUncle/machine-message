package com.bjfl.galaxymessage.json;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonStrToBeanTest {

    //json字符串-简单对象型
    private static final String JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";

    //json字符串-数组类型
    private static final String JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";

    //复杂格式json字符串
    private static final String COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";

    /**
     * json字符串-简单对象到JavaBean之间的转换
     *
     * 必须有setter方法
     */
    @Test
    public void testJSONStrToJavaBeanObj() {

        // 第一种方式,使用TypeReference<T>类,由于其构造方法使用protected进行修饰,故创建其子类
        Student student = JSONObject.parseObject(JSON_OBJ_STR, new TypeReference<Student>() {});

        // 第二种方式,使用Gson的思想
        Student student2 = JSONObject.parseObject(JSON_OBJ_STR, Student.class);

        System.out.println(student);
        System.out.println(student2);
    }

    /**
     * JavaBean到json字符串-简单对象的转换
     */
    @Test
    public void testJavaBeanObjToJSONStr() {

        Student student = new Student("lily", 12);
        String jsonString = JSONObject.toJSONString(student);
        System.out.println(jsonString);
    }


    /**
     * json字符串-数组类型到JavaBean_List的转换
     */
    @Test
    public void testJSONStrToJavaBeanList() {

        //第一种方式,使用TypeReference<T>类,由于其构造方法使用protected进行修饰,故创建其子类
        List<Student> studentList = JSONArray.parseObject(JSON_ARRAY_STR, new TypeReference<ArrayList<Student>>() {});
        System.out.println("studentList:  " + studentList);

        //第二种方式,使用Gson的思想
        List<Student> studentList1 = JSONArray.parseArray(JSON_ARRAY_STR, Student.class);
        System.out.println("studentList1:  " + studentList1);

    }

    /**
     * JavaBean_List到json字符串-数组类型的转换
     */
    @Test
    public void testJavaBeanListToJSONStr() {

        Student student = new Student("lily", 12);
        Student student2 = new Student("lucy", 15);

        List<Student> students = new ArrayList<Student>();
        students.add(student);
        students.add(student2);

        String jsonString = JSONArray.toJSONString(students);
        System.out.println(jsonString);

    }

    /**
     * 复杂json格式字符串到JavaBean_obj的转换
     */
    @Test
    public void testComplexJSONStrToJavaBean(){

        //第一种方式,使用TypeReference<T>类,由于其构造方法使用protected进行修饰,故创建其子类
        Teacher teacher = JSONObject.parseObject(COMPLEX_JSON_STR, new TypeReference<Teacher>() {});
        System.out.println(teacher);

        //第二种方式,使用Gson思想
        Teacher teacher1 = JSONObject.parseObject(COMPLEX_JSON_STR, Teacher.class);
        System.out.println(teacher1);
    }

    /**
     * 复杂JavaBean_obj到json格式字符串的转换
     */
    @Test
    public void testJavaBeanToComplexJSONStr(){

        //已知复杂JavaBean_obj
        Teacher teacher = JSONObject.parseObject(COMPLEX_JSON_STR, new TypeReference<Teacher>() {});
        String jsonString = JSONObject.toJSONString(teacher);
        System.out.println(jsonString);
    }

}
