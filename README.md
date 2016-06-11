##简介

- excel-bean 主要功能是javabean与Excel数据行之间的互相转换，普通项目中都会有Excel的导入导出功能，使用该工具可以大大提高工作效率，使开发者不必关系复杂的Excel解析。

##使用说明

- 有如图Excel表格
![有如图Excel表格](http://upload.dingshops.com/dengta_mobile/upload/d4/2016/06/11/20160611214930.png)

- 定义JavaBean类型
	```java
public class Student1 {

	// 如果Excel标题和属性名称一样，则可以不使用注解
	private String 姓名;

	@Row("年龄")
	// 对应Excel标题中的年龄列，以下类似
	private Integer age;

	@Row("生日")
	// 日期类型，默认格式为 yyyy-MM-dd HH:mm:ss
	private Date birthday;
	@Row("语文分数")
	private Double chineseScore;
	@Row("数学分数")
	private Double mathsScore;
	@Row("总分") // 对应Excel中的公式，既 总分 = 语文分数 + 数学分数
	private Double sumScore;

	//省略getter 和setter 方法

	@Override
	public String toString() {
		return "Student1 [姓名=" + 姓名 + ", age=" + age + ", birthday=" + birthday + ", chineseScore=" + chineseScore
				+ ", mathsScore=" + mathsScore + ", sumScore=" + sumScore + "]";
	}
}
	```

- 调用ExcelUtils 方法 将Excel转换成JavaBean对象列表
	```java
	@Test
	public void testRead() {
		List<Student1> stu1List = ExcelUtils.read(excel2003, Student1.class);
		log.debug("学生信息列表：" + stu1List);
	}
	```
	
- 输出以下结果：

  ```tex
  	学生信息列表：[Student1 [姓名=张三, age=18, birthday=Fri May 20 10:20:39 CST 2016, chineseScore=100.0, mathsScore=99.0, sumScore=199.0], Student1 [姓名=李四, age=17, birthday=Sun Mar 20 19:23:16 CST 2016, chineseScore=20.09, mathsScore=87.7, sumScore=107.79], Student1 [姓名=王五, age=20, birthday=Tue Jan 12 18:33:28 CST 2016, chineseScore=33.5, mathsScore=33.3, sumScore=66.8], Student1 [姓名=测试1, age=30, birthday=Tue Jan 12 18:33:28 CST 2016, chineseScore=60.0, mathsScore=95.0, sumScore=155.0], Student1 [姓名=测试2, age=31, birthday=Wed Jan 13 18:33:28 CST 2016, chineseScore=61.1, mathsScore=80.0, sumScore=141.1]]
  ```

##Contact and communication

- blog：http://my.oschina.net/java2010/blog
- email：aimilin@yeah.net

##License
Please follow the open source protocol [MIT]
