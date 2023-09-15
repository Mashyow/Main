package arabian;

import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);

        while (true) {
            String expression = scn.nextLine();
            String result = calc(expression);
            System.out.println(result);

            }

        }


    public static String calc(String input) throws Exception {
        Main main = new Main();
        Rim converter = main.new Rim();
        String[] parts = input.trim().split(" ");


        if (parts.length >= 5) {
            throw new Exception("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        if (parts.length != 3) {
            throw new Exception("т.к. строка не является математической операцией");
        }



        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];



        boolean operand1IsRim = operand1.matches("[IVXLCDM]+");
        boolean operand2IsRim = operand2.matches("[IVXLCDM]+");
        if ((!operand1IsRim && operand2IsRim)||(operand1IsRim && !operand2IsRim)) {
            throw new Exception("т.к. используются одновременно разные системы счисления");
        }

        int size1, size2;
        boolean isRim = false;

        try {
            size1 = Integer.parseInt(operand1);
            size2 = Integer.parseInt(operand2);
        } catch (NumberFormatException e) {
            size1 = converter.rimToInt(operand1);
            size2 = converter.rimToInt(operand2);
            isRim = true;
        }


        if ((size1 < 1 || size1 > 10) || (size2 < 1 || size2 > 10)) {
            throw new Exception("Операнды должны быть в диапазоне от 1 до 10");
        }
        int result = 0;
        switch (operator) {
            case "+":
                result = size1 + size2;
                break;
            case "-":
                result = size1 - size2;
                break;
            case "*":
                result = size1 * size2;
                break;
            case "/":
                result = size1 / size2;
                break;
        }

        if (isRim) {
            if (result <= 0) {
                throw new Exception("т.к. в римской системе нет отрицательных чисел");
            }
            return converter.intToRim(result);

        } else {
            return String.valueOf(result);



        }

    }




    class Rim {
        TreeMap<Character, Integer> rimKeyMap = new TreeMap<>();
        TreeMap<Integer, String> arabKeyMap = new TreeMap<>();

        public Rim() {
            rimKeyMap.put('I', 1);
            rimKeyMap.put('V', 5);
            rimKeyMap.put('X', 10);
            rimKeyMap.put('L', 50);
            rimKeyMap.put('C', 100);
            rimKeyMap.put('D', 500);
            rimKeyMap.put('M', 1000);

            arabKeyMap.put(1000, "M");
            arabKeyMap.put(900, "CM");
            arabKeyMap.put(500, "D");
            arabKeyMap.put(400, "CD");
            arabKeyMap.put(100, "C");
            arabKeyMap.put(90, "XC");
            arabKeyMap.put(50, "L");
            arabKeyMap.put(40, "XL");
            arabKeyMap.put(10, "X");
            arabKeyMap.put(9, "IX");
            arabKeyMap.put(5, "V");
            arabKeyMap.put(4, "IV");
            arabKeyMap.put(1, "I");
        }



        public String intToRim(int number) {
            String rim = "";
            Integer arabKey;
            do {
                arabKey = arabKeyMap.floorKey(number);
                rim += arabKeyMap.get(arabKey);
                number -= arabKey;
            } while (number != 0);

            return rim;
        }


        public int rimToInt(String s) {
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arab;
            int result = rimKeyMap.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arab = rimKeyMap.get(arr[i]);

                if (arab < rimKeyMap.get(arr[i + 1])) {
                    result -= arab;
                } else {
                    result += arab;
                }
            }
            return result;
        }
    }


}