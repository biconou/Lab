import bsh.Interpreter;

/**
 * Created by remi on 22/01/2016.
 */
public class TestBeanShell {

    public static void main(String args[]) {
        try {

          Interpreter interpreter = new Interpreter();
          int value = 1;
          String expression = "result = "+value+"+1";
          interpreter.eval(expression);
          System.out.println(interpreter.get("result"));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
