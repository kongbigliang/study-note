# Jacob 
> 是Java-COM Bridge的缩写。<br>
> 它在Java与微软的COM组件之间构建百一座桥梁。<br>
> 使用Jacob自带的DLL动态链接库，并通过JNI的方式实现了在Java平台上对COM程序的调用。

下载地址：https://sourceforge.net/projects/jacob-project/

使用说明：
1. 把dll文件放在%JAVA_HOME%\bin下（注意系统是32位还是64位），也可以放在C:\Windows\System32下，如果是64位应该放在C:\Windows\SysWOW64 下。**建议放在jdk的bin目录下**
2. 如果是在eclipse下开发，需要重新引入jdk（Preference/Java/Installed JREs）
3. 开发时将jacab.jar包放在项目lib下并add到libraries中即可。

测试demo：com.kongbig.doc.demo01.Jacob

---

# poi
> HWPFDocument处理doc <br>
> XWPFDocument处理docx 

使用说明：
1. 处理了word文档中复选框的问题，利用搜狗输入法的符号："□"、"☑"
2. HWPFDocument替换文本的方式是全文遍历来替换，所以替换的key不能有包含关系，例如：shopId包含单词shop。
3. XWPFDocument不能替换时，应输出全部XWPFParagraph的值来排查问题。

测试demo：com.kongbig.doc.demo02.HwpfDoc、com.kongbig.doc.demo02.XwpfDocx 

--- 

# poi-tl
http://deepoove.com/poi-tl/
