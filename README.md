![ci](https://api.travis-ci.org/aimilin6688/excel-bean.svg?branch=master)
![maven](https://img.shields.io/maven-central/v/com.aimilin/excel-bean.svg?style=flat-square)

## 简介

- excel-bean 主要功能是javabean与Excel数据行之间的互相转换，普通项目中都会有Excel的导入导出功能，使用该工具可以大大提高工作效率，使开发者不必关系复杂的Excel解析。

## Maven方式使用
```xml
<dependency>
	<groupId>com.aimilin</groupId>
	<artifactId>excel-bean</artifactId>
	<version>{最新版本}</version>
</dependency>
```

## 使用说明

- 有如图Excel表格  

姓名|年龄|生日|语文分数|数学分数|总分|性别
-|-|-|-|-|-|-
张三|18|2016-05-20 10:20:39|100|99|199|男
李四|17|2016-03-20 19:23:16|20.09|87.7|107.79|女
王五|20|2016-01-12 18:33:28|33.5|33.3|66.8|男
测试1|30|2016-01-12 18:33:28|60|95|155|女					
测试2|31|2016-01-13 18:33:28|61.1|80|141.1|男


- 定义JavaBean类型

```java 
public class Student1 {

	// 如果Excel标题和属性名称一样，则可以不使用注解
	private String 姓名;

	@ExlColumn("年龄")
	// 对应Excel标题中的年龄列，以下类似
	private Integer age;

	@ExlColumn("生日")
	// 日期类型，默认格式为 yyyy-MM-dd HH:mm:ss
	private Date birthday;
	@ExlColumn("语文分数")
	private Double chineseScore;
	@ExlColumn("数学分数")
	private Double mathsScore;
	@ExlColumn("总分") // 对应Excel中的公式，既 总分 = 语文分数 + 数学分数
	private Double sumScore;
	
	@ExlTransient //如果使用该注解，则该属性不写入到Excel中
	private String className;//班级名称

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
学生信息列表：[Student1 [姓名=张三, age=18, birthday=Fri May 20 10:20:39 CST 2016, chineseScore=100.0, mathsScore=99.0, sumScore=199.0],  Student1 [姓名=李四, age=17, birthday=Sun Mar 20 19:23:16 CST 2016, chineseScore=20.09, mathsScore=87.7, sumScore=107.79], 
Student1 [姓名=王五, age=20, birthday=Tue Jan 12 18:33:28 CST 2016, chineseScore=33.5, mathsScore=33.3, sumScore=66.8], 
Student1 [姓名=测试1, age=30, birthday=Tue Jan 12 18:33:28 CST 2016, chineseScore=60.0, mathsScore=95.0, sumScore=155.0], 
Student1 [姓名=测试2, age=31, birthday=Wed Jan 13 18:33:28 CST 2016, chineseScore=61.1, mathsScore=80.0, sumScore=141.1]]
```
  
  - 有时候导出Excel数据时会把相应的状态转换成可读的文字，例如数据库中性别存储的是1和2，导出Excel中需要时‘男’和‘女’，这时候可以使用@ExlColumn注解的dictionaries完成类型自动转换。在学生对象中添加如下代码：
```java
// 性别使用字典数据方式，自动转换
@ExlColumn(value = "性别", 
	   dictionaries = {@Dictionary(name = "1", value = "男"), 
			   @Dictionary(name = "2", value = "女"),
			   @Dictionary(name = "0", value = "未知") })
private Integer gender;
```

**使用方式说明,主要有以下几类方法**   
> ExcelUtils.read  
> ExcelUtils.read2List  
> ExcelUtils.read2Map   
> ExcelUtils.write   
> ExcelUtils.write4List   
> ExcelUtils.write4Map   
> 主要的数据类型byte[],InputStream, String filePath等，可以很方便的再List，Map，Bean与Excel文件之间互转



## Contact and communication

- blog：http://my.oschina.net/java2010/blog
- email：aimilin@yeah.net

## License
Please follow the open source protocol [MIT]
