package Mingo.WeirdItems.Candy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {

    List<Candy> candies = new ArrayList<>();

    public Bag() {
        this.candies.add(new Candy("Candy", CandyType.ORANGE));
        this.candies.add(new Candy("Candy", CandyType.MAGENTA));
        this.candies.add(new Candy("Candy", CandyType.LIGHT_BLUE));
        this.candies.add(new Candy("Candy", CandyType.YELLOW));
        this.candies.add(new Candy("Candy", CandyType.LIME));
        this.candies.add(new Candy("Candy", CandyType.PINK));
        this.candies.add(new Candy("Candy", CandyType.GREY));
        this.candies.add(new Candy("Candy", CandyType.LIGHT_GREY));
        this.candies.add(new Candy("Candy", CandyType.CYAN));
        this.candies.add(new Candy("Candy", CandyType.PURPLE));
        this.candies.add(new Candy("Candy", CandyType.BLUE));
        this.candies.add(new Candy("Chocolate", CandyType.BROWN));
        this.candies.add(new Candy("Candy", CandyType.GREEN));
        this.candies.add(new Candy("Candy", CandyType.RED));
    }

    public Candy getRandomCandy() {
        Random rand = new Random();
        return this.candies.get(rand.nextInt(this.candies.size()));
    }
    public List<Candy> getCandies() { return this.candies; }
}
