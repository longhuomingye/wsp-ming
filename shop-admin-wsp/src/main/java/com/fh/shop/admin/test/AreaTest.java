package com.fh.shop.admin.test;

public class AreaTest extends AreaTest2{

    private Integer id;
    private String name;

    public AreaTest(){
        System.out.println("无参");
    }
    public AreaTest(Integer id){
        System.out.println("一个id");
    }
    public AreaTest(String name){
        System.out.println("一个name");
    }

    public AreaTest(Integer id,String name){
        this.id = id;
        this.name = name;
        System.out.println("两个");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
