import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * @ClassName: Test
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/16 01:34
 */
public class Test {
    interface Shape {
        void draw();
    }

    static class Circle implements Shape {
        public void draw() {
            System.out.println("Drawing a circle");
        }
    }

    static class Rectangle implements Shape {
        public void draw() {
            System.out.println("Drawing a rectangle");
        }
    }

    static void drawShape(Shape shape) {
        shape.draw();
    }

    public static void main(String[] args) {
        Shape circle = new Circle();
        Shape rectangle = new Rectangle();

        drawShape(circle);    // 多分派，依赖于对象类型和方法参数类型
        drawShape(rectangle);  // 多分派，依赖于对象类型和方法参数类型
    }

}
