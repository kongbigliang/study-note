
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
* 可以使用注解 `@FunctionalInterface` 修饰（可以检查是否是函数式接口）。

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

其他内置接口：

函数式接口  | 参数类型  | 返回类型  | 用途
------------- | ------------- | ------------- | -------------
`BiFunction<T, U, R>`  | T, U  | R  | 对类型为 T, U 参数应用操作，返回 R 类型的结果。包含方法为R apply(T t, U u);
`UnaryOperator<T>(Function子接口)`  | T  | T  | 对类型为T的对象进行一元运算，并返回T类型的结果。包含方法为T apply(T t);
`BinaryOperator<T>(BiFunction 子接口)`  | T, T  | T  | 对类型为T的对象进行二元运算，并返回T类型的结果。包含方法为T apply(T t1, T t2);
`BiConsumer<T, U>`  | T, U  | void  | 对类型为T, U 参数应用操作。包含方法为void accept(T t, U u)
`ToIntFunction<T>`  | T  | int  | 计算 int 值的函数
`ToLongFunction<T>`  | T  | long  |计算 long 值的函数
`ToDoubleFunction<T>`  | T  | double  |计算 double 值的函数
`IntFunction<R>`  | int  | R  | 参数为 int 类型的函数
`LongFunction<R>`  | long  | R  | 参数为 long 类型的函数
`DoubleFunction<R>`  | double  | R  | 参数为 double 类型的函数


## 方法引用与构造器引用
### 方法引用
若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
* 语法格式1： 对象的引用 :: 实例方法名
* 语法格式2： 类名 :: 静态方法名
* 语法格式3： 类名 :: 实例方法名

注意：
* 方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
* 若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName


### 构造器引用
构造器的参数列表，需要与函数式接口中参数列表保持一致！
* 类名 :: new

### 数组引用
* 类型[] :: new

## Stream API
Stream API 的操作步骤：
* 创建 Stream

* 中间操作
    > 多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作，<br>
    > 否则中间操作不会执行任何的处理！而在终止操作时一次性全部处理，称为“惰性求值”。
    
    **筛选与切片**
    
    方法 | 描述
    ------------- | -------------
    `filter(Predicate p)`  | 接收 Lambda ， 从流中排除某些元素。
    `distinct()`  | 筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素。
    `limit(long maxSize)`  | 截断流，使其元素不超过给定数量。
    `skip(long n)`  | 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补。
    
    **映射**
    
    方法 | 描述
    ------------- | -------------
    `map(Function f)`  | 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
    `mapToDouble(ToDoubleFunction f)`  | 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。
    `mapToInt(ToIntFunction f)`  | 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。
    `mapToLong(ToLongFunction f)`  | 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。
    `flatMap(Function f)`  | 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
    
    **排序**
    
    方法 | 描述
    ------------- | -------------
    `sorted()`  | 产生一个新流，其中按自然顺序排序。
    `sorted(Comparator comp)`  | 产生一个新流，其中按比较器顺序排序。
    
* 终止操作(终端操作)
    > 终端操作会从流的流水线生成结果。
    > 其结果可以是任何不是流的值，例如：List、Integer，甚至是 void 。
    
    **查找与匹配**
    
    方法 | 描述
    ------------- | -------------
    `allMatch(Predicate p)`  | 检查是否匹配所有元素。
    `anyMatch(Predicate p)`  | 检查是否至少匹配一个元素。
    `noneMatch(Predicate p)`  | 检查是否没有匹配所有元素。
    `findFirst()`  | 返回第一个元素。
    `findAny()`  | 返回当前流中的任意元素。
    `count()`  | 返回流中元素总数。
    `max(Comparator c)`  | 返回流中最大值。
    `min(Comparator c)`  | 返回流中最小值。
    `forEach(Consumer c)`  | 内部迭代。
    
    **归约**
    
    方法 | 描述
    ------------- | -------------
    `reduce(T iden, BinaryOperator b)`  | 可以将流中元素反复结合起来，得到一个值；返回 T。
    `reduce(BinaryOperator b)`  | 可以将流中元素反复结合起来，得到一个值；返回 Optional<T>。
    
    **收集**
    
    方法 | 描述
    ------------- | -------------
    `collect(Collector c)`  | 将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法。

## 接口中的默认方法与静态方法
## 新时间日期 API
## 其他新特性
