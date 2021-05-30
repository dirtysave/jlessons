package lesson6;

import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainClass {
    public static void main(String[] args) {

        // задание 1
        String[] listWords1 = new String[] {  "Маша", "Вова", "Петя", "Вова", "Коля", "Вова"};
        Stream<String> stream = Arrays.stream(listWords1);

        stream
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEach(entry -> System.out.println(entry.getKey() + "=" + entry.getValue()));

        System.out.println("---------------------------------");
        // задание 2

        Worker[] workers = new Worker[] {
                new Worker("Петрович", 65, 250000.0),
                new Worker("Иваныч", 58, 30000.0),
                new Worker("Петруччо", 25, 15000.0),
                new Worker("Елена Прекрасная", 18, 79000.0)
        };


        Stream<Worker> workStream = Arrays.stream(workers);
        System.out.println(String.format("Средняя зарплата сотрудника=%f", workStream.collect(Collectors.averagingDouble(w -> w.getSalary()))));
        System.out.println("---------------------------------");

        // Задание 3
        int workersCount = 2;
        System.out.print(String.format("%d самых старых сотрудников зовут: ", workersCount));

                Arrays.stream(workers)
                    .sorted((item1, item2) ->  item2.getAge() - item1.getAge())
                    .limit(workersCount)
                    .forEach(entry -> System.out.print(entry.getName() + ", "));
        System.out.println("\n---------------------------------");

        // Задание =4
        String news = "На восстановление Дачи со слонами в Самаре выделяется 17,4 млн рублей Министерство культуры РФ ищет подрядчика на выполнение этих работ Все работы нужно завершить до 31 августа 2022 года Об этом говорится в документах, размещенных на портале госзакупок Всего на восстановление известной в Самаре дачи купца Константина Головкина на улице Советской Армии, 296 выделяется 70 млн рублей Из них на противоаварийные работы пойдет 16,3 млн, 1,1 млн – на реставрационные Как мы писали ранее, работы планируется начать уже в 2021 году Объект культурного наследия федерального значения после восстановления станет местом размещения школы молодых архитекторов К слову, все строения, скульптуры на территории легендарной Дачи со слонами в Самаре взяты под государственную охрану Соответствующий приказ опубликовало Управление госохраны объектов культурного наследия Самарской области Изменять исторический облик этого знакового для истории региона объекта запрещено Официальное название памятника – «Комплекс дачи Головкина» Архитектурный шедевр в стиле модерн был построен в 1908-1909 гг по личному проекту известного самарского купца, путешественника, археолога Константина Головкина После революции в доме размещался госпиталь, детский сад, затем клуб В начале августа стало известно, что принято решение о передаче комплекса по адресу ул Советской армии, 296 Самарскому техническому университету В настоящее время готовится проект реставрации";
        Stream<String> strStream = Arrays.stream(news.split(" "));

         String result = strStream
                .filter(s -> s.length() > 5)
                .distinct()
                .collect(Collectors.joining(" "));

        System.out.println(result);
        System.out.println("---------------------------------");

        // Задание 5
        IntStream iStream = IntStream.rangeClosed(100, 200);

        int sum = iStream
                .filter(i -> i % 2  == 0)
                .sum();
        System.out.println(String.format("Сумма массива = %d", sum));
        System.out.println("---------------------------------");

        // Задание 6
        int stringsLength = Arrays.stream(new String[] { "as1", "dd", "12345"})
                //Arrays.stream(news.split(" "))
                .map(x -> x.length())
                .collect(Collectors.summingInt(value -> value));

        System.out.println(String.format("Сумма длины строк = %d", stringsLength));
        System.out.println("---------------------------------");

        // Задание 7
        Arrays.stream(new String[] { "as1", "dd", "12345", "0wASDEFH"})
                .sorted((item1, item2) -> item1.compareTo(item2))
                .limit(3)
                .forEach(x -> System.out.println(x));
    }
}
