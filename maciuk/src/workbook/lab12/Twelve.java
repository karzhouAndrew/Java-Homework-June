package workbook.lab12;

/**
 * Created by Asus on 21.02.2015.
 */
public class Twelve {
    public static void main(String[] args) {
        int array[] = {3, 9, 7, 9, 8, 9, 4, 5, 5, 0};
        int n = array[0];
        for (int i = 0; i < array.length; i++) {
            if (n < array[i]) {
                n = array[i];
            }
        }
        for (int j = 0; j < array.length; j++)
            if (n == array[j]) {
                System.out.println("Номер ячейки с максимальной оценкой" + j);
            }
    }
}