/**
 * @ClassName: StaticTest
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/16 22:45
 */
public class StaticTest {
    class testLoader extends ClassLoader{
        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            return super.findClass(name);
        }
    }
}