package workbook2.lab2;

/**
 * Создать программу, которая может приветствовать пользователя на трех языках: английском, русском, белорусском.
 * Требуемый язык и страна должны сообщаться программе в качестве входных параметров.
 * Если входные параметры отсутствуют, программа должна выдавать сообщение на английском.
 */
public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.getMenu();
    }
}
