package workbook2.lab7;

/**
 * Напишите программу, которая будет разбирать xml файл, сделанный в предыдущих заданиях
 * с помощью DOM, и выводить его на экран в текстовом виде. Каждая точка должна выводиться
 * на отдельной строке в виде двух чисел, разделенных запятой, при этом должна выводиться
 * единица измерения. Например: 10px, 30px
 */
public class Load {
    public static void main(String[] args) {
        DOMParser domParser = new DOMParser();
        domParser.parseXML();
    }
}
