import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();

        Function<String, Object[]> translator = x -> {
            Object[] dictionary = Arrays.stream(x.split(" "))
                    .toArray();
            Arrays.sort(dictionary);
            return dictionary;
        };

        Object[] result = translator.apply(message);

        for (Object word : result) {
            System.out.println(word);
        }
    }
}


