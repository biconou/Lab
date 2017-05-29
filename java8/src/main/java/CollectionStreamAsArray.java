import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by remi on 08/05/17.
 */
public class CollectionStreamAsArray {


    private static class Person {
        String fisrtName;
        String lastName;

        public String getFisrtName() {
            return fisrtName;
        }

        public void setFisrtName(String fisrtName) {
            this.fisrtName = fisrtName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }



    public static void main(String args[]) {

        Collection<Person> persons = new ArrayList<Person>();
        Person jo = new Person();
        jo.setFisrtName("Jo");
        jo.setLastName("Dalton");
        Person luke = new Person();
        luke.setFisrtName("Luke");
        luke.setLastName("Lycky");

        persons.add(jo);
        persons.add(luke);

        String[] firtNames = persons.stream().map(p -> p.getFisrtName()).toArray(String[]::new);

        Arrays.stream(firtNames).forEach(name -> System.out.println(name));

    }
}
