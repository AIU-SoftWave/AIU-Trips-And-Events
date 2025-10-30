
package barista;
import barista.BaristaMachine;
import barista.Factories.ClassicFactory;
import barista.Factories.DietFactory;

public class demo {
    
    public static void main(String[] args) {
        System.out.println("=== Classic Factory ===");
        BaristaMachine machine = new BaristaMachine(new ClassicFactory());
        machine.serveCoffee();
        machine.serveCoffeeWithMilk();
        machine.serveTea();

        System.out.println("\n=== Diet Factory ===");
        // Change factory
        machine = new BaristaMachine(new DietFactory());
        machine.serveCoffee();
        machine.serveCoffeeWithMilk();
        machine.serveTea();
    }
}
