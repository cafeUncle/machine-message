package com.bjfl.galaxymessage.json;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BeanToJsonTest {

    //json字符串-简单对象型
    private static final String JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";

    //json字符串-数组类型
    private static final String JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";

    //复杂格式json字符串
    private static final String COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";

    /**
     * 简单JavaBean_obj到json对象的转换
     */
    @Test
    public void testJavaBeanToJSONObject(){

        //已知简单JavaBean_obj
        Student student = new Student("lily", 12);

        //方式一
        String jsonString = JSONObject.toJSONString(student);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println(jsonObject);

        //方式二
        JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(student);
        System.out.println(jsonObject1);
    }

    /**
     * 简单json对象到JavaBean_obj的转换
     */
    @Test
    public void testJSONObjectToJavaBean(){

        //已知简单json对象
        JSONObject jsonObject = JSONObject.parseObject(JSON_OBJ_STR);

        //第一种方式,使用TypeReference<T>类,由于其构造方法使用protected进行修饰,故创建其子类
        Student student = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Student>() {});
        System.out.println(student);

        //第二种方式,使用Gson的思想
        Student student1 = JSONObject.parseObject(jsonObject.toJSONString(), Student.class);
        System.out.println(student1);
    }

    /**
     * JavaList到JsonArray的转换
     */
    @Test
    public void testJavaListToJsonArray() {

        //已知JavaList
        Student student = new Student("lily", 12);
        Student studenttwo = new Student("lucy", 15);

        List<Student> students = new ArrayList<Student>();
        students.add(student);
        students.add(studenttwo);

        //方式一
        String jsonString = JSONArray.toJSONString(students);
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        System.out.println(jsonArray);

        //方式二
        JSONArray jsonArray1 = (JSONArray) JSONArray.toJSON(students);
        System.out.println(jsonArray1);
    }

    /**
     * JsonArray到JavaList的转换
     */
    @Test
    public void testJsonArrayToJavaList() {

        //已知JsonArray
        JSONArray jsonArray = JSONArray.parseArray(JSON_ARRAY_STR);

        //第一种方式,使用TypeReference<T>类,由于其构造方法使用protected进行修饰,故创建其子类
        ArrayList<Student> students = JSONArray.parseObject(jsonArray.toJSONString(),
                new TypeReference<ArrayList<Student>>() {});

        System.out.println(students);

        //第二种方式,使用Gson的思想
        List<Student> students1 = JSONArray.parseArray(jsonArray.toJSONString(), Student.class);
        System.out.println(students1);
    }

    /**
     * 复杂JavaBean_obj到json对象的转换
     */
    @Test
    public void testComplexJavaBeanToJSONObject() {

        //已知复杂JavaBean_obj
        Student student = new Student("lily", 12);
        Student studenttwo = new Student("lucy", 15);

        List<Student> students = new ArrayList<Student>();
        students.add(student);
        students.add(studenttwo);
        Course course = new Course("english", 1270);

        Teacher teacher = new Teacher("crystall", 27, course, students);

        //方式一
        String jsonString = JSONObject.toJSONString(teacher);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println(jsonObject);

        //方式二
        JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(teacher);
        System.out.println(jsonObject1);

    }

    /**
     * 复杂json对象到JavaBean_obj的转换
     */
    @Test
    public void testComplexJSONObjectToJavaBean() {

        //已知复杂json对象
        JSONObject jsonObject = JSONObject.parseObject(COMPLEX_JSON_STR);

        //第一种方式,使用TypeReference<T>类,由于其构造方法使用protected进行修饰,故创建其子类
        Teacher teacher = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Teacher>() {});
        System.out.println(teacher);

        //第二种方式,使用Gson的思想
        Teacher teacher1 = JSONObject.parseObject(jsonObject.toJSONString(), Teacher.class);
        System.out.println(teacher1);
    }
}
