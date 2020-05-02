
# Java8
## Java8 新特性
* 速度更快
    > HashMap数据结构的优化（数组+链表+红黑树）。<br>
    > 底层内存结构：去掉永久区，加入MetaSpace元空间，使用物理内存（更快）。（PremGenSize和MaxPremGenSize被MetaSpaceSize和MaxMetaSpaceSize所取代）
* 代码更少（增加了新的语法 Lambda 表达式）
* 强大的 Stream API
* 便于并行
* 最大化减少空指针异常 Optional

## Lambda表达式
> Lambda 是一个匿名函数，可以把 Lambda 表达式理解为是一段可以传递的代码（将代码像数据一样进行传递）。
基础语法:
* 操作符 "->"
    * 左侧：Lambda 表达式的参数列表
    * 右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体
* 语法格式1：无参数，无返回值。
    ```
    () -> System.out.println("Hello Lambda!");
    ```
* 语法格式2：有一个参数，并且无返回值。
    ```
    (x) -> System.out.println(x)
    ```
* 语法格式3：若只有一个参数，小括号可以省略不写。
    ```
    x -> System.out.println(x)
    ```
* 语法格式4：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句。
    ```
    Comparator<Integer> com = (x, y) -> {
        System.out.println("函数式接口");
        return Integer.compare(x, y);
    };
    ```
* 语法格式5：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写。
    ```
    Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
    ```
* 语法格式6：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出数据类型，即“**类型推断**”。
    ```
    (Integer x, Integer y) -> Integer.compare(x, y);
    ```

## 函数式接口
Lambda 表达式需要“函数式接口”的支持
* 接口中只有一个抽象方法的接口，称为函数式接口。
* 可以使用注解 @FunctionalInterface 修饰（可以检查是否是函数式接口）。

Java8 内置的四大核心函数式接口
* 消费型接口
    ```
    Consumer<T>
        void accept(T t);
    ```
* 供给型接口
    ```
    Supplier<T>
        T get();
    ```
* 函数式接口
    ```
    Function<T, R>
        R apply(T t);
    ```
* 断言型接口
    ```
    Predicate<T>
        boolean test(T t);        
    ```

## 方法引用与构造器引用
## Stream API
## 接口中的默认方法与静态方法
## 新时间日期 API
## 其他新特性
