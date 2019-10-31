package com.fh.shop.admin.test;

public class AreaTest2 {

    private String name;

    public AreaTest2(){
        System.out.println("父类无参");
    }
    public AreaTest2(String name){
        System.out.println("父类有参");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
