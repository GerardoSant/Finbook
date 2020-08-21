package SparkApp;


public class Main {

    public static void main(String[] args) {
        SparkApp sparkApp = new SparkApp();
        sparkApp.start();
    }

    public String reverseString(final String str){
        if (str==null){
            throw new IllegalArgumentException("String null");
        }
        String result =str;
        if (str.length()>1){
            result = str.charAt(str.length()-1) + reverseString(str.substring(0, str.length()-1));
        }
        return result;
    }

    public static String reverseWords(String input) {
        String[] arr = input.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = arr.length - 1; i >= 0; --i) {
            sb.append(arr[i]);
            sb.append(" ");
        }
        return sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
    }

    public static void reverse(int[] array, int index){
        array[index] += array[array.length - index -1];
        array[array.length - index -1] = array[index] - array[array.length - index -1];
        array[index] -= array[array.length - index -1];
        if (index <= array.length / 2 -1){
            reverse(array,++index);
        }
    }
}
