//package CalculatorWithFactorty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        System.out.println("Программа Калькулятор");
        System.out.println("Выбирите действие:");
        final String[] op = {"1 - Сложение", "2 - Вычитание", "3 - Умножение",
                             "4 - Деление", "5 - числа Фибоначчи."};
        System.out.println(Arrays.toString(op)
                .replace("[", "")
                .replace("]", ""));

        int operationInput = Integer.parseInt(new Scanner(System.in).nextLine());
        String operation = null;

        final Map<Integer, String> operationMap = new HashMap<>();
        operationMap.put(1, "Add");
        operationMap.put(2, "Subtract");
        operationMap.put(3, "Multiply");
        operationMap.put(4, "Divide");
        operationMap.put(5, "Fibonacci");

        for (Map.Entry<Integer, String> entry : operationMap.entrySet())
            if (entry.getKey() == operationInput) {
                operation = entry.getValue();
                break;
            }

        System.out.println("Введите два числа: ");
        double num1 = Double.parseDouble(new Scanner(System.in).nextLine());
        double num2 = Double.parseDouble(new Scanner(System.in).nextLine());

        System.out.println("Результат: " + (new CalculatorUsingFactory()
                .startCalculate(num1, num2, operation)));

    }
}
/*
---------------------

package CalculatorWithFactorty;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class CalculatorFactory {

    final static Map<String, Operation> map = new HashMap<>();
    static {
        map.put("Add", new Add());
        map.put("Divide", new Divide());
        map.put("Multiply", new Multiply());
        map.put("Subtract", new Subtract());
    }

    public static Optional<Operation> getOperation(String operator) {
        return Optional.ofNullable(map.get(operator));

    }
}

class CalculatorUsingFactory {
    public double startCalculate(double a, double b, String operator) {
        Operation targetOperation = CalculatorFactory
                .getOperation(operator)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Operator"));
        return targetOperation.apply(a, b);
    }
}

---------------------
package CalculatorWithFactorty;

public interface Operation {
    double apply(double a, double b);
}

---------------------
package CalculatorWithFactorty;

public class Add implements Operation {
    public double apply(double a, double b) {
        return a + b;
    }
}
---------------------






























import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
// Comparer
// default public
public class NewBook {
    privte String title;
    boolean secondHand;
    double price;
// https://www.baeldung.com/java-replace-if-statements
    https://www.baeldung.com/java-replace-if-statements

    HTML CSS
        <script src='....'>

        Web
    	js
        node.js
        TypeScript
        []
    	{}
        console.log('hello, world')

        ML
        data type
        {}
        []
        {}
        ()
    	[i for i in range(10)]
        print 'hello'
        print('hello') // python 3



    You are given a hash table where the key is a course code,
    and the value is a list of all the course codes that
    are prerequisites for the key. Return a valid
    ordering in which we can complete the courses.
    If no such ordering exists, return NULL.

Example:
{
  'CSC300': ['CSC100', 'CSC200'],
  'CSC200': ['CSC100'],
  'CSC100': []
}

 This input should return the order that we need to take these courses:
 ['CSC100', 'CSC200', 'CSCS300']

Here's your starting point:

def courses_to_take(course_to_prereqs):
  # Fill this in.

courses = {
  'CSC300': ['CSC100', 'CSC200'],
  'CSC200': ['CSC100'],
  'CSC100': []
}
    HashMap <String, ArrayList> course = new Hashmap<>();
    course.put("CS1", new ArrayList(...));

print courses_to_take(courses)
# ['CSC100', 'CSC200', 'CSC300']









    //char[]
    String str = new String("Hello"); // == equals
    String str2 = new String(new char[] {'H', 'e', 'l', 'l', 'o'});  // + internalization
    String str3 = "Hello";
    String str4 = "Hello";
    String str5 = "world";
    String str6 = "world!";

    NewBook() {                    // замена строки 9
        this.title = new String("Gone With The Wind");
        this.secondHand = new Boolean(false);
        this.price = new Double(9.5);
    }
//    NewBook( String title, boolean secondHand, double price) {
//        this.title = title;
//        this.secondHand = secondHand;
//        this.price = price;
//    }

    public String toString() {
        return title + ", new: " + !secondHand + ", price: " + price;
    }

    public static void main(String[] args) {             // замена строки 19
        NewBook b = new NewBook();
        b.title = "Gone With The Wind";
        b.secondHand = false;
        b.price = 9.5;

        System.out.print(b);
    }
}






/*
// static
// stack
// dynamic (heap)

class Rextester {
    static String str = "Hello world!";
    static String str2 = new String("Hello world!");

    static {
	    String str = "Hello world";
        int i = 123;
    }

    public static void main(String[] args) {
        System.out.println(str == str2);
    }
}
*/



/*






























import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {
    static double num1 = 0;
    static double num2 = 0;
    static String operation;

    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator();
        calculator.startCalc();
    }


    public void startCalc() {

        CalculatorInterface.input();

        switch (operation) {
            case "+" : {
                CalculatorInterface.printResult(new Addition().add(num1,num2));
            }
            break;
            case "-" : {
                CalculatorInterface.printResult(new Subtraction().subtract((int)num1,(int)num2));
            }
            break;
            case "*" : {
                CalculatorInterface.printResult(new Multiplication().multiply(num1,num2));
            }
            break;
            case "exp" : {
                CalculatorInterface.printResult(new Exponentiation().exponentiate((int)num1,num2));
            }
            break;
            case "fact" : {
                CalculatorInterface.printResult(new Factorial().factorize((int) num1));
            }
            break;
            case "fib" : {
                CalculatorInterface.printResult(new Fibonachi().findFibonachi((int) num1));
            }
            break;
        }
    }
    }

import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculatorInterface {
    static void input() {

        System.out.println("Выберите операцию: +, -, *, возведение в степень exp, вычисление факториала fact, вычисление числа Фибоначчи fib");
        Scanner in = new Scanner(System.in);
        Calculator.operation = in.nextLine();
        try {
            switch (Calculator.operation) {
                case ("+"):
                case ("*"): {
                    System.out.println("Введите два числа:");
                    Calculator.num1 = in.nextDouble();
                    Calculator.num2 = in.nextDouble();
                }
                break;
                case ("-"): {
                    System.out.println("Введите два числа:");
                    Calculator.num1 = in.nextInt();
                    Calculator.num2 = in.nextInt();
                }
                break;
                case ("exp"): {
                    System.out.println("Введите показатель:");
                    Calculator.num1 = in.nextInt();
                    System.out.println("Введите основание:");
                    Calculator.num2 = in.nextDouble();
                }
                break;
                case ("fact"):
                case ("fib"): {
                    System.out.println("Введите число:");
                    Calculator.num1 = in.nextInt();
                }
                break;
                default: {
                    System.out.println("Вы ввели некорректную операцию. Попробуйте еще раз;)");
                    input();
                }
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Вы ввели число в некорректном формате. Попробуйте еще раз;)");
            input();
        }
        catch (InputMismatchException e) {
            System.out.println("Вы ввели число в некорректном формате. Попробуйте еще раз!;)");
            input();
        }
    }

    static void printResult(double result) {
        System.out.println("Результат вычисления: " + result);
    }
}


public class Addition {

    public double add(double num1, double num2) {

        return (num1 + num2);
    }

}

public class Exponentiation {


    public double exponentiate(int num1, double num2) {

            double result = 1;

            for (int i = 0; i < num1; i++) {
                result *= num2;
            }

            return result;
    }
}

public class Factorial {

    public double factorize(int num1){

            double result = 1;

            for (int i = num1; i > 1; i--) {
                result *= i;
            }

            return result;

    }
}

public class Fibonachi {

    public double findFibonachi(int num1) {

            int a = 0;
            int b = 1;

            for (int i = 2; i <= num1; ++i) {
                int next = a + b;
                a = b;
                b = next;
            }
            return (double) b;

    }
}

public class Multiplication {


    public double multiply(double num1, double num2)  {

        return (num1 * num2);
    }
}

public class Subtraction {

    public double subtract (int num1, int num2) {
             return (num1 - num2);
    }
}
































import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;
// HTML, CSS
// <header>
//   <h1>Site name</h1>
//   <div>Contact</div>
// </header>
//
// header {
//   display: flex;
// }
// h1 {
//    color: red;
//    width: 100%;
// }
// div {
// }
// @media max-width: 768p {
//   header {
//     display: block;
//   }
// }
// []               {}
// []  ()   {}      {}
class Rextester {
	public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 10, 1, 2, 2, 3, 10, 5, 5, 4, 3, 3));
        System.out.println("ArrayList with duplicates: " + list);
        // Java API, Apache Commons, Rect + Spring Framework DB, JUnit, etc
        List<Integer> newList = list.stream().distinct().collect(Collectors.toList());
        System.out.println("ArrayList with duplicates removed: " + newList);
        //List<Integer> newList2 = newList.stream().sorted().collect(Collectors.toList());
        Collections.sort(newList);
        System.out.println("Sorted array without duplicates: " + newList);
        Collections.sort(newList, Collections.reverseOrder());
        System.out.println("Reverse order sorted array without duplicates: " + newList);
    }
}



/*
//import java.util.Arrays;
import java.util.*;

class Rextester {
    /* We have four Candidates with name as 'John',
      'Johnny', 'jamie', 'jackie'.
       The votes in String array are as per the
       votes casted. Print the name of candidates
       received Max vote. *//*
    public static void findWinner(String votes[]) {
        // Insert all votes in a hashmap
        // key - hashable
        // value - any object
        //
        // key1 -> value1
        // key2 -> value2
        //
        Map<String, Integer> map =
                new HashMap<String, Integer>();
        for (String str : votes) {
			if (map.containsKey(str))
                map.put(str, map.get(str) + 1);
            else
                map.put(str, 1);
        }

        //System.out.println(map);

        // Traverse through map to find the candidate
        // with maximum votes.
        int maxValueInMap = 0;
        String winner = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer val = entry.getValue();
            System.out.println(key + ": " + val);
            if (val > maxValueInMap) {
                maxValueInMap = val;
                winner = key;
            }
            // If there is a tie, pick lexicographically
            // smaller.
            // a
            // aa
            // john
            // johnny
            // -1  john < johnny
            // 0 - ==
            // 1   john > johnny
            else if (val == maxValueInMap &&
                    winner.compareTo(key) > 0)
                winner = key;
        }
        System.out.println(winner);
    }

    // Driver code
    public static void main(String[] args) {
        String[] votes = {"john", "johnny", "jackie",
                "johnny", "john", "jackie",
                "jamie", "jamie", "john",
                "johnny", "jamie", "johnny",
                "john"};

        findWinner(votes);
    }
}
//*/





/*


---------------------------------------------------------------------

import java.util.*;

public class ElectoralVotingBallot {
    /* We have four Candidates with name as 'John',
      'Johnny', 'jamie', 'jackie'.
       The votes in String array are as per the
       votes casted. Print the name of candidates
       received Max vote. *
    public static void findWinner(String votes[]) {
        // Insert all votes in a hashmap
        Map<String, Integer> map =
                new HashMap<String, Integer>();
        for (String str : votes) {
            if (map.keySet().contains(str))
                map.put(str, map.get(str) + 1);
            else
                map.put(str, 1);
        }

        // Traverse through map to find the candidate
        // with maximum votes.
        int maxValueInMap = 0;
        String winner = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer val = entry.getValue();
            if (val > maxValueInMap) {
                maxValueInMap = val;
                winner = key;
            }
            // If there is a tie, pick lexicographically
            // smaller.
            else if (val == maxValueInMap &&
                    winner.compareTo(key) > 0)
                winner = key;
        }
        System.out.println(winner);
    }

    // Driver code
    public static void main(String[] args) {
        String[] votes = {"john", "johnny", "jackie",
                "johnny", "john", "jackie",
                "jamie", "jamie", "john",
                "johnny", "jamie", "johnny",
                "john"};

        findWinner(votes);
    }
}




























/*

import java.util.*;
import java.lang.*;
import java.io.*;

class Rextester {
    public static void main(String[] args) throws NumberFormatException {
        //String s = "abcdef,";

        final String input = "5 5 5";
    	InputStream in = new ByteArrayInputStream(input.getBytes());
    	System.setIn(in);

        System.out.println("ПРОГРАММА КАЛЬКУЛЯТОР");
        Scanner scanner = new Scanner(System.in);
        StringBuilder takeOp = new StringBuilder("Выбирите действие -> ");
        final String[] ops = {"Сложение", "Вычитание", "Умножение",
                              "Деление", "Степень ", "Факториал",
                              "Числа Фибоначчи"};
        final int n = ops.length;
        if (ops[n - 1].endsWith(",")) {
            ops[6] = ops[6].substring(0, ops[6].length() - 1);
        }
        for (int i = 0; i < n; i ++) {
            boolean last = (i == n - 1);
            if (!last) {
            	takeOp.append((i + 1) + ": " + ops[i] + ", ");
            } else {
            	takeOp.append((i + 1) + ": " + ops[i]);
            }
        }
		String s = takeOp.toString();
        /*if (s.endsWith(", ")) {
            s = s.substring(0, s.length() - 2);
        }*//*
        System.out.println(s);
        String res = "Результат: ";
        int choose = scanner.nextInt();
        if (choose <= 0 && choose >= 6) throw new NumberFormatException("error: Неправильный ввод");
        if (choose >= 1 && choose <= 5) {
            System.out.println("Введите два числа");
            System.out.print("Первое число: ");
            double num1 = scanner.nextDouble();
            System.out.print("Второе число: ");
            double num2 = scanner.nextDouble();
        }
    }
}
/*if (path.endWith(",")) {
    int i = s.length() - 1;
	path = path.substring(0, i);
*/




/*import java.util.*;
import java.lang.*;
import java.io.*;

class Rextester {
    public static void main(String[] args) {
        String[] names = {"john", "johnny", "jackie",
                          "johnny", "john", "jackie",
                          "jamie",  "jamie", "john",
                          "mike", "kim", "mike",
                          "johnny","john", "johnny"};
        //Comparator<String> stringLengthComparator = new StringLengthSort();
        for(String str : names){
            System.out.println(str);
        }
        Arrays.sort(names, Rextester::compare);
        System.out.println(" SORTED: ");
        for(String str : names){
            System.out.println(str);
        }
    }

	static int compare(String a, String b) {
        int n = a.length();
        int m = b.length();
        //n.compareTo(m);
        int c = Integer.compare(n, m);
        if (c != 0) {
            return c;
        } else {
            return a.compareTo(b);
        }
    }
}

john
johnny
jackie
johnny
john
jackie
jamie
jamie
john
mike
kim
mike
johnny
john
johnny
 SORTED:
kim
john
john
john
mike
mike
john
jamie
jamie
johnny
jackie
johnny
jackie
johnny
johnny









/*
import java.util.*;
import java.lang.*;
import java.io.*;

class Rextester {
    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 3, 3, 3, 4, 5, 7, 7, 7, 7, 7, 10};
        System.out.println(Arrays.toString(
            RemoveDuplicates.remove(nums)
        ));
    }
}

class RemoveDuplicates {
    public static int[] remove(int[] nums) {
        final int n = nums.length;
        if (nums.length == 0) return nums;
        int[] a = new int[n];
        int i = 0;
        a[0] = nums[0]; // 1 2 3 4 5 7 10
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != a[i]) {
                i++;
                a[i] = nums[j];
            }
        }
        System.out.println(Arrays.toString(a));
        int[] b; /* = new int[i + 1];
        /*for (int j = 0; j < i + 1; j++) {
            b[j] = a[j];
            System.out.println(Arrays.toString(b));
        }*//*
        //System.arraycopy(a, 0, b, i+1);
        b = Arrays.copyOfRange(a, 0, i + 1);
        return b;
    }
}
*/

