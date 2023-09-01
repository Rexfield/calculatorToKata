import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    private static final Map<String, Integer> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put("I", 1);
        ROMAN_NUMERALS.put("II", 2);
        ROMAN_NUMERALS.put("III", 3);
        ROMAN_NUMERALS.put("IV", 4);
        ROMAN_NUMERALS.put("V", 5);
        ROMAN_NUMERALS.put("VI", 6);
        ROMAN_NUMERALS.put("VII", 7);
        ROMAN_NUMERALS.put("VIII", 8);
        ROMAN_NUMERALS.put("IX", 9);
        ROMAN_NUMERALS.put("X", 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите выражение (или 'exit' для выхода): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Программа завершена.");
                break;
            }

            String result = calc(input);
            System.out.println("Результат: " + result);
        }

        scanner.close();
    }

    static String calc(String input) {
        try {
            String[] tokens = input.split(" ");
            String operand1Str = tokens[0];
            String operator = tokens[1];
            String operand2Str = tokens[2];

            boolean isOperand1Roman = isRoman(operand1Str);
            boolean isOperand2Roman = isRoman(operand2Str);

            if (isOperand1Roman != isOperand2Roman) {
                throw new IllegalArgumentException("Числа разных типов");
            }

            int operand1 = parseNumber(operand1Str);
            int operand2 = parseNumber(operand2Str);

            if (operand1 > 10 || operand1 < 1 || operand2 > 10 || operand2 < 1) {
                throw new IllegalArgumentException("Числа должны быть от 1 до 10");
            }

            int result;
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 == 0) {
                        return "Ошибка: деление на ноль!";
                    }
                    result = operand1 / operand2;
                    break;
                default:
                    throw new IllegalArgumentException("Недопустимая операция");
            }

            if (isOperand1Roman) {
                return arabicToRoman(result);
            } else {
                return String.valueOf(result);
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    static int parseNumber(String numStr) {
        try {
            return Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            Integer romanValue = ROMAN_NUMERALS.get(numStr);
            if (romanValue == null) {
                throw new IllegalArgumentException("Неверный формат числа");
            }
            return romanValue;
        }
    }

    static boolean isRoman(String str) {
        return ROMAN_NUMERALS.containsKey(str);
    }

    static String arabicToRoman(int number) {
        if (number <= 0 || number > 100) {
            throw new IllegalArgumentException("Число должно быть от 1 до 100");
        }

        String[] romanNumerals = { "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

        int[] arabicValues = { 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder result = new StringBuilder();
        int index = 0;

        while (number > 0) {
            if (number >= arabicValues[index]) {
                result.append(romanNumerals[index]);
                number -= arabicValues[index];
            } else {
                index++;
            }
        }

        return result.toString();
    }
}