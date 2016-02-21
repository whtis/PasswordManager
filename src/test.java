import java.io.File;

/**
 * Created by ht on 2016/2/20.
 */
public class test {
    public static void main(String[] args) {
        File fileDir = new File("./good");
        File file = new File("./good/hi.w");
        System.out.println(fileDir.getAbsolutePath());
        System.out.println(file.getAbsolutePath());
    }
}
