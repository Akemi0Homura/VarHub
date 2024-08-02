# Varhub

这是一个实现java中全局变量的轻量级程序，它可以让你以非常低的成本存储和获取变量信息。

This is a lightweight program that implements global variables in java, which allows you to store and get variable information at a very low cost.

并且它支持高并发

And it supports high concurrency

下面是一个最简单的示例：

Here is an example of the simplest one:

```java
public class SimpleBoxtest {
    public static void main(String[] args) {
        Stockpile box= SimpleBox.getSimpleBox();
        box.putObject("a1",10);

        test temp=new test();
        temp.test1();
    }
}

class test {
    Stockpile box= SimpleBox.getSimpleBox();
    void test1(){
        //运行时这里会打印整数型10
        //The runtime here prints the integer type 10
        System.out.println(box.getObject("a1", Integer.class));
    }
}

```

如果你感兴趣，想使用这个VarHub，那么请一定要阅读 快速入门 文件夹的教程

If you're interested in using this VarHub, then be sure to read the Quick Start folder tutorial!

源代码jar包的注释是使用中文撰写

The source code jar package comments are written in Chinese!However, the tutorials are available in English and take only five minutes to read, and the internal implementation is not difficult, so I'm sure you'll be able to read the source code and use it even if you don't have it.

如果有任何好的建议或者功能或者bug都欢迎在这发布

If there are any good suggestions or features or bugs feel free to post them here!
