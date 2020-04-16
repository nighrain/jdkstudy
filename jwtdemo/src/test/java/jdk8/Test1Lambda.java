package jdk8;

import jdk8.lambda.Trader;
import jdk8.lambda.Transaction;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *    
 * Title         [title]
 * Author:       [..]
 * CreateDate:   [2019-09-26--15:15]  @_@ ~~
 * Version:      [v1.0]
 * Description:  [..]
 * <p>
 * 参考
 * 版权声明：本文为CSDN博主「蒙牛真好」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/nullbull/article/details/81234250
 * </p>
 *  
 */
public class Test1Lambda {
/*
1 找出2011年发生的所有交易，并按交易额排序
2 交易员在哪些不同的城市工作过
3 查找所有来自剑桥的交易员，并按姓名排序
4 返回所有交易员的姓名字符串，并按字母顺序排序
5 有没有交易员在米兰工作的？
6 打印生活在剑桥的交易员的所有交易额
7 所有交易中，最高的交易额是多少
8 找到交易额最小的交易
 */

    private Trader raoul = new Trader("Raoul拉乌尔", "Cambridge剑桥");
    private Trader mario = new Trader("Mario马里奥", "Milan米兰");
    private Trader alan = new Trader("Alan艾伦", "Cambridge剑桥");
    private Trader brian = new Trader("Brian布莱恩", "Cambridge剑桥");

    private List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    //1 找出2011年发生的所有交易，并按交易额排序
    @Test
    public void lambda1() {
        List<Object> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
//        tr2011.forEach(System.out::println);
        System.out.println(tr2011);
    }

    //2 交易员在哪些不同的城市工作过
    @Test
    public void lambda2() {
//        transactions.stream()
//                .map(new Function<Transaction, Object>() {
//                    @Override
//                    public Object apply(Transaction transaction) {
//                        return transaction.getTrader().getCity();
//                    }
//                })
//                .distinct()
//                .forEach(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) {
//                        System.out.println(o);
//                    }
//                });
        System.out.println("=======");
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(cities);
    }

    //3 查找所有来自剑桥的交易员，并按姓名排序
    @Test
    public void lambda3() {
//        transactions.stream()
//                .map(Transaction::getTrader)
//                .filter(t -> "Cambridge剑桥".equals(t.getCity()))
//                .sorted(Comparator.comparing(Trader::getName))
//                .forEach(System.out::println);
//        //todo 什么时候 :: 什么时候() -> {} 方法引用和 lambda 的区别
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> "Cambridge剑桥".equals(t.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(traders);

    }

    //4 返回所有交易员的姓名字符串，并按字母顺序排序
    @Test
    public void lambda4() {
//        transactions.stream()
//                .map(t -> t.getTrader().getName())
//                .distinct()
//                .sorted()
//                .forEach(System.out::println);
        String reduce = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + "-" + n2);
        System.out.println(reduce);
    }

    //5 有没有交易员在米兰工作的？
    @Test
    public void lambda5() {
//        List<Trader> collect = transactions.stream()
//                .map(Transaction::getTrader)
//                .distinct()
//                .filter(t -> "Milan米兰".equals(t.getCity()))
//                .collect(Collectors.toList());
//        collect.forEach(System.out::println);
        boolean milanBased = transactions.stream()
                .anyMatch(t -> "Milan米兰".equals(t.getTrader().getCity()));
        System.out.println(milanBased);
    }
//6 打印生活在剑桥的交易员的所有交易额
    @Test
    public void lambda6(){
        transactions.stream()
                .filter(t -> "Cambridge剑桥".equals(t.getTrader().getCity()))
                .forEach(System.out::println);
    }
    //更新所有交易，以便米兰的交易员被安排到剑桥。
    @Test
    public void lambda66(){
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> "Milan米兰".equals(t.getCity()))
                .forEach(t -> t.setCity("Cambridge剑桥"));
        System.out.println(transactions);
        transactions.forEach(System.out::println);
    }
//7 所有交易中，最高的交易额是多少
    @Test
    public void lambda7(){
//        transactions.stream()
//                .max(t -> t.getValue())
//                .map(Transaction::getValue)

        Integer highestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Integer::max);
        System.out.println(highestValue);
    }
//8 找到交易额最小的交易 最低
    @Test
    public void lambda8(){
        Integer highestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Integer::max);

        Integer lowestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(highestValue, Integer::min);

        System.out.println(lowestValue);
    }


}

///https://blog.csdn.net/nullbull/article/details/81234250
class PuttingIntoPractice{
    public static void main(String ...args){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );


        // Query 1: Find all transactions from year 2011 and sort them by value (small to high).
        //
        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(tr2011);

        // Query 2: What are all the unique cities where the traders work?
        List<String> cities =
                transactions.stream()
                        .map(transaction -> transaction.getTrader().getCity())
                        .distinct()
                        .collect(Collectors.toList());
        System.out.println(cities);

        // Query 3: Find all traders from Cambridge and sort them by name.

        List<Trader> traders =
                transactions.stream()
                        .map(Transaction::getTrader)
                        .filter(trader -> trader.getCity().equals("Cambridge"))
                        .distinct()
                        .sorted(Comparator.comparing(Trader::getName))
                        .collect(Collectors.toList());
        System.out.println(traders);


        // Query 4: Return a string of all traders’ names sorted alphabetically.

        String traderStr =
                transactions.stream()
                        .map(transaction -> transaction.getTrader().getName())
                        .distinct()
                        .sorted()
                        .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(traderStr);

        // Query 5: Are there any trader based in Milan?

        boolean milanBased =
                transactions.stream()
                        .anyMatch(transaction -> transaction.getTrader()
                                .getCity()
                                .equals("Milan")
                        );
        System.out.println(milanBased);


        // Query 6: Update all transactions so that the traders from Milan are set to Cambridge.
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Milan"))
                .forEach(trader -> trader.setCity("Cambridge"));
        System.out.println(transactions);


        // Query 7: What's the highest value in all the transactions?
        int highestValue =
                transactions.stream()
                        .map(Transaction::getValue)
                        .reduce(0, Integer::max);
        System.out.println(highestValue);
    }
}
