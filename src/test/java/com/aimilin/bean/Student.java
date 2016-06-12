package com.aimilin.bean;

import java.util.Date;

import com.aimilin.annotation.Dictionary;
import com.aimilin.annotation.Row;
import com.aimilin.annotation.Sheet;

@Sheet("学生")
public class Student {

	@Row(index = 0)
	private String 姓名;
	@Row(value = "年龄", index = 5)
	private Integer age;
	@Row(value = "生日", index = 1)
	private Date birthday;
	@Row(value = "语文分数", index = 2)
	private Double chineseScore;
	@Row(value = "数学分数", index = 3)
	private Double mathsScore;
	@Row(value = "总分", index = 4)
	private Double sumScore;

	@Row(value = "性别", index = 6, dictionaries = { @Dictionary(name = "1", value = "男"),
			@Dictionary(name = "2", value = "女"), @Dictionary(name = "0", value = "未知") })
	private Integer gender;

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String get姓名() {
		return 姓名;
	}

	public void set姓名(String 姓名) {
		this.姓名 = 姓名;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getChineseScore() {
		return chineseScore;
	}

	public void setChineseScore(Double chineseScore) {
		this.chineseScore = chineseScore;
	}

	public Double getMathsScore() {
		return mathsScore;
	}

	public void setMathsScore(Double mathsScore) {
		this.mathsScore = mathsScore;
	}

	public Double getSumScore() {
		return sumScore;
	}

	public void setSumScore(Double sumScore) {
		this.sumScore = sumScore;
	}

	@Override
	public String toString() {
		return "Student [姓名=" + 姓名 + ", age=" + age + ", birthday=" + birthday + ", chineseScore=" + chineseScore
				+ ", mathsScore=" + mathsScore + ", sumScore=" + sumScore + ", gender=" + gender + "]";
	}

}
