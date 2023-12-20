package Homework.HM_004;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Homework {

    public static void main(String[] args) {
        // Box<String> strings = new Box<>(); // не должно компилироваться
        // Bound mismatch: The type String is not a valid substitute for the bounded parameter <T extends Homework.Fruit> of the type Homework.Box<T>
        // Cannot infer type arguments for Box<>

        System.out.println("--------");
        Box<Orange> oranges = new Box<>();
        oranges.add(new Orange(1));
        System.out.println(oranges.getWeight()); // 1
        oranges.add(new Orange(2));
        System.out.println(oranges.getWeight()); // 3
        

        System.out.println("--------");
        Box<Apple> apples = new Box<>();
        // apples.add(new Orange(3)); // не должно компилироваться! 
        // The method add(Homework.Apple) in the type Homework.Box<Homework.Apple> is not applicable for the arguments (Homework.Orange)

        apples.add(new GoldenApple(5));
    
        Box<GoldenApple> goldenApples = new Box<>();
        goldenApples.add(new GoldenApple(5));
        // goldenApples.add(new Apple(3)); // не должно компилироваться!
        // The method add(Homework.GoldenApple) in the type Homework.Box<Homework.GoldenApple> is not applicable for the arguments (Homework.Apple)

        // oranges.move(apples); // не должно компилироваться!
        // The method move(Homework.Box<? super Homework.Orange>) in the type Homework.Box<Homework.Orange> is not applicable for the arguments

        goldenApples.move(apples);
        // apples.move(goldenApples); // не должно компилироваться!
        // The method move(Homework.Box<? super Homework.Apple>) in the type Homework.Box<Homework.Apple> is not applicable for the arguments
        
        Box<Orange> newOranges = new Box<>();
        oranges.move(newOranges);
        System.out.println(oranges.getWeight()); // 0 после пересыпания
        System.out.println(newOranges.getWeight()); // 3 после пересыпания
        
        
        for (Orange o: oranges) { // цикл компилируется, но не запускатся, потому oranges - пустой
          System.out.println("Проверка вывода: " + o.getWeight());
        }
        
        System.out.println("--------");
        for (Orange o: newOranges) { // цикл компилируется, и запускается
          // Должно вывести 1 3 (или 3 1) - порядок неважен
          System.out.println(o.getWeight());
        }
        
        System.out.println("--------");
        for (Apple a: apples) { // цикл компилируется, и запускается
          // Должно вывести 5 5
          System.out.println(a.getWeight());
        }
      }
    
      static class Box<T extends Fruit> implements Iterable<T> {
        private List<T> fruits = new ArrayList<>();

        public void add(T fruit) {
            fruits.add(fruit);
        }

        public int getWeight() {
            int totalWeight = 0;
            for (T fruit : fruits) {
                totalWeight += fruit.getWeight();
            }
            return totalWeight;
        }

        public void move(Box<? super T> destinationBox) {
            destinationBox.fruits.addAll(this.fruits);
            this.fruits.clear();
        }

        @Override
        public Iterator<T> iterator() {
            return fruits.iterator();
        }
    }
    
      static class Fruit {
        private final int weight;
    
        public Fruit(int weight) {
          this.weight = weight;
        }
    
        public int getWeight() {
          return weight;
        }
      }
    
      static class Orange extends Fruit {
        public Orange(int weight) {
          super(weight);
        }
      }
    
      static class Apple extends Fruit {
        public Apple(int weight) {
          super(weight);
        }
      }
    
      static class GoldenApple extends Apple {
        public GoldenApple(int weight) {
          super(weight);
        }
      }
}
