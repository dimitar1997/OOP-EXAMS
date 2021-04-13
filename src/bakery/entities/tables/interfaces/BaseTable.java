package bakery.entities.tables.interfaces;

import bakery.common.enums.TableTYpe;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.Drink;

import java.util.ArrayList;
import java.util.Collection;

import static bakery.common.ExceptionMessages.INVALID_NUMBER_OF_PEOPLE;
import static bakery.common.ExceptionMessages.INVALID_TABLE_CAPACITY;

public class BaseTable implements Table {
    private Collection<BakedFood> foodOrders;
    private Collection<Drink> drinkOrders;
    private int tableNumber;
    private int capacity;
    private int numberOfPeople;
    private double pricePerPerson;
    private boolean isReserved;
    private double price;



    public BaseTable(int tableNumber, int capacity, double pricePerPerson) {
        setTableNumber(tableNumber);
        setCapacity(capacity);
        setPricePerPerson(pricePerPerson);
        foodOrders = new ArrayList<>();
        drinkOrders = new ArrayList<>();

    }



    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(INVALID_TABLE_CAPACITY);
        }
        this.capacity = capacity;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    @Override
    public int getTableNumber() {
        return this.tableNumber;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getNumberOfPeople() {
        return this.numberOfPeople;
    }

    @Override
    public double getPricePerPerson() {
        return this.pricePerPerson;
    }

    @Override
    public boolean isReserved() {
        if (numberOfPeople == 0){
            return false;
        }
        return true;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void reserve(int numberOfPeople) {
        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException(INVALID_NUMBER_OF_PEOPLE);
        }
        this.numberOfPeople = numberOfPeople;

    }

    @Override
    public void orderFood(BakedFood food) {
        foodOrders.add(food);
    }

    @Override
    public void orderDrink(Drink drink) {
        drinkOrders.add(drink);
    }

    @Override
    public double getBill() {
        double sum = 0;

        sum = foodOrders.stream().mapToDouble(k -> (double) k.getPrice()).sum() +
                drinkOrders.stream().mapToDouble(v -> (double) v.getPrice()).sum() +
                this.getPricePerPerson() * this.numberOfPeople;
        return sum;
    }

    @Override
    public void clear() {
        foodOrders.clear();
        drinkOrders.clear();
        price = 0;
        numberOfPeople = 0;

    }

    @Override
    public String getFreeTableInfo() {
        String type = null;
        if (this.getPricePerPerson() == 2.50){
            type = TableTYpe.InsideTable.name();
        }else if (this.getPricePerPerson() == 3.50){
            type = TableTYpe.OutsideTable.name();
        }
        return String.format("Table: %d%n" +
                "Type: %s%n" +
                "Capacity: %d%n" +
                "Price per Person: %.2f",this.tableNumber, type ,this.capacity,this.pricePerPerson);
    }
}
