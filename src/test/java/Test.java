import java.net.URL;
import java.util.Enumeration;

/**
 * @ClassName: test
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/08/03 19:37
 */
public class Test {
    public static void main(String[] args) throws Exception{
        String name = "java/sql/Array.class";
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(name);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println(url.toString());
        }
    }
}