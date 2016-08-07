package com.aimilin.bean;

import java.util.Date;

import com.aimilin.annotation.Dictionary;
import com.aimilin.annotation.Column;
import com.aimilin.annotation.Sheet;

@Sheet("学生")
public class Student {

	@Column(value = "姓名")
	private String name;
	@Column(value = "年龄")
	private Integer age;
	@Column(value = "生日")
	private Date birthday;
	@Column(value = "语文分数")
	private Double chineseScore;
	@Column(value = "数学分数")
	private Double mathsScore;
	@Column(value = "总分")
	private Double sumScore;

	@Column(value = "性别", index = 6, dictionaries = { @Dictionary(name = "1", value = "男"), @Dictionary(name = "2", value = "女"),
			@Dictionary(name = "0", value = "未知") })
	private Integer gender;

	@Column(value = "状态")
	private Integer state;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "Student [name=" + name + ", age=" + age + ", birthday=" + birthday + ", chineseScore=" + chineseScore
				+ ", mathsScore=" + mathsScore + ", sumScore=" + sumScore + ", gender=" + gender + ", state=" + state + "]";
	}

}
