package com.example.harshad.rotaract;

/**
 * Created by Harshad on 1/8/2018.
 */

public class User {

   private String name,email,password,enrollmentno,mobile,branch,semester;

    public User() {
    }

    public User(String name, String email, String password, String enrollmentno, String mobile,String branch,String semester) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enrollmentno = enrollmentno;
        this.mobile = mobile;
        this.branch=branch;
        this.semester=semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnrollmentno() {
        return enrollmentno;
    }

    public void setEnrollmentno(String enrollmentno) {
        this.enrollmentno = enrollmentno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
