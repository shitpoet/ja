//package CalculatorWithFactorty;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Calculator {
    public static void main(String[] args) {
        final String input = "5\n5\n5\n";
    	InputStream in = new ByteArrayInputStream(input.getBytes());
    	System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        //System.out.println("Программа Калькулятор");
        //System.out.println("Выбирите действие:");
        final String[] op = {"1 - Сложение", "2 - Вычитание", "3 - Умножение",
                             "4 - Деление", "5 - числа Фибоначчи."};
        /*System.out.println(Arrays.toString(op)
                .replace("[", "")
                .replace("]", ""));*/

        int operationInput = Integer.parseInt(scanner.nextLine());
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

        //System.out.println("Введите два числа: ");
        double num1 = Double.parseDouble(scanner.nextLine());
        double num2 = Double.parseDouble(scanner.nextLine());

        System.out.println("Результат: " + (new CalculatorUsingFactory()
                .startCalculate(num1, num2, operation)));

    }
}

class CalculatorFactory {

    final static Map<String, Operation> map = new HashMap<>();
    static {
        map.put("Add", new Add());
        //map.put("Divide", new Divide());
        //map.put("Multiply", new Multiply());
        //map.put("Subtract", new Subtract());
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

interface Operation {
    double apply(double a, double b);
}


class Add implements Operation {
    public double apply(double a, double b) {
        return a + b;
    }
}
