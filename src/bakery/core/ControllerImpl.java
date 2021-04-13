package bakery.core;

import bakery.common.ExceptionMessages;
import bakery.common.OutputMessages;
import bakery.common.enums.BakedFoodType;
import bakery.common.enums.DrinkType;
import bakery.common.enums.TableTYpe;
import bakery.core.interfaces.Controller;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.bakedFoods.interfaces.BaseFood;
import bakery.entities.bakedFoods.interfaces.Bread;
import bakery.entities.bakedFoods.interfaces.Cake;
import bakery.entities.drinks.interfaces.BaseDrink;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.drinks.interfaces.Tea;
import bakery.entities.drinks.interfaces.Water;
import bakery.entities.tables.interfaces.BaseTable;
import bakery.entities.tables.interfaces.InsideTable;
import bakery.entities.tables.interfaces.OutsideTable;
import bakery.entities.tables.interfaces.Table;
import bakery.repositories.interfaces.*;

import java.util.ArrayList;
import java.util.List;

import static bakery.common.ExceptionMessages.FOOD_OR_DRINK_EXIST;
import static bakery.common.ExceptionMessages.TABLE_EXIST;
import static bakery.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private Repository<Drink> drinkRepository;
    private Repository<BakedFood> foodRepository;
    private Repository<Table> tableRepository;
    private double totalIncome;


    public ControllerImpl(FoodRepository<BakedFood> foodRepository, DrinkRepository<Drink> drinkRepository, TableRepository<Table> tableRepository) {
        this.drinkRepository = drinkRepository;
        this.foodRepository = foodRepository;
        this.tableRepository = tableRepository;
    }


    @Override
    public String addFood(String type, String name, double price) {

        BaseFood baseFood;
        for (BakedFood food : foodRepository.getAll()) {
            if (food.getName().equals(name)) {
                throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST, type, name));
            }
        }
        if (type.equals(BakedFoodType.Bread.name())) {
            baseFood = new Bread(name, price);
            foodRepository.add(baseFood);
        } else if (type.equals(BakedFoodType.Cake.name())) {
            baseFood = new Cake(name, price);
            foodRepository.add(baseFood);
        }

        return String.format(FOOD_ADDED, name, type);
    }

    @Override
    public String addDrink(String type, String name, int portion, String brand) {
        BaseDrink baseDrink;
        for (Drink drink : drinkRepository.getAll()) {
            if (drink.getName().equals(name)) {
                throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST, type, name));
            }
        }
        if (type.equals(DrinkType.Tea.name())) {
            baseDrink = new Tea(name, portion, brand);
            drinkRepository.add(baseDrink);
        } else if (type.equals(DrinkType.Water.name())) {
            baseDrink = new Water(name, portion, brand);
            drinkRepository.add(baseDrink);
        }
        return String.format(DRINK_ADDED, name, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table table;
        for (Table table1 : tableRepository.getAll()) {
            if (table1.getTableNumber() == tableNumber) {
                throw new IllegalArgumentException(String.format(TABLE_EXIST, tableNumber));
            }
        }
        if (type.equals(TableTYpe.InsideTable.name())) {
            table = new InsideTable(tableNumber, capacity);
            tableRepository.add(table);
        } else if (type.equals(TableTYpe.OutsideTable.name())) {
            table = new OutsideTable(tableNumber, capacity);
            tableRepository.add(table);
        }
        return String.format(TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserveTable(int numberOfPeople) {
        Table tableR = null;
        for (Table table : tableRepository.getAll()) {
            if (table.isReserved() || table.getCapacity() < numberOfPeople) {
                continue;
            } else {
                table.reserve(numberOfPeople);
                tableR = table;
                break;
            }

        }
        if (tableR != null) {
            return String.format(TABLE_RESERVED, tableR.getTableNumber(), numberOfPeople);
        } else {
            return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }

    }

    @Override
    public String orderFood(int tableNumber, String foodName) {
        Table table = null;
        BakedFood bakedFood = null;
        boolean checkFood = false;
        boolean checkTable = false;
        for (BakedFood food : foodRepository.getAll()) {
            if (food.getName().equals(foodName)) {
                bakedFood = food;
                checkFood = true;
                break;
            }
        }
        for (Table table1 : tableRepository.getAll()) {
            if (table1.getTableNumber() == tableNumber) {
                table = table1;
                checkTable = true;
                if (bakedFood != null) {
                    table1.orderFood(bakedFood);
                }
                break;
            }
        }
        if (checkFood || checkTable) {
            if (table == null || !table.isReserved()) {
                return String.format(WRONG_TABLE_NUMBER, tableNumber);
            } else if (bakedFood == null) {
                return String.format(NONE_EXISTENT_FOOD, foodName);
            }
        }
        return String.format(FOOD_ORDER_SUCCESSFUL, tableNumber, foodName);
    }

    @Override
    public String orderDrink(int tableNumber, String drinkName, String drinkBrand) {
        Table table = null;
        Drink drink1 = null;
        boolean checkDrink = false;
        boolean checkTable = false;
        for (Drink drink : drinkRepository.getAll()) {
            if (drink.getName().equals(drinkName)) {
                drink1 = drink;
                checkDrink = true;
                break;
            }
        }
        for (Table table1 : tableRepository.getAll()) {
            if (table1.getTableNumber() == tableNumber) {
                table = table1;
                checkTable = true;
                if (drink1 != null) {
                    table1.orderDrink(drink1);
                }
                break;
            }
        }
        if (checkDrink || checkTable) {
            if (table == null) {
                return String.format(WRONG_TABLE_NUMBER, tableNumber);
            } else if (drink1 == null) {
                return String.format(NON_EXISTENT_DRINK, drinkName, drinkBrand);
            }
        }
        return String.format(DRINK_ORDER_SUCCESSFUL, tableNumber, drinkName, drinkBrand);

    }

    @Override
    public String leaveTable(int tableNumber) {
        Table table = null;
        for (Table table1 : tableRepository.getAll()) {
            if (table1.getTableNumber() == tableNumber) {
                table = table1;
            }
        }
        assert table != null;
        totalIncome = totalIncome + table.getBill();
        return String.format(BILL, tableNumber, table.getBill());
    }

    @Override
    public String getFreeTablesInfo() {
        StringBuilder sb =  new StringBuilder();
        for (Table table : tableRepository.getAll()) {
            if (!table.isReserved()) {
                sb.append(table.getFreeTableInfo());
            }
            sb.append(System.lineSeparator());

        }

        return sb.toString().trim();
    }

    @Override
    public String getTotalIncome() {
        return String.format(TOTAL_INCOME, totalIncome);
    }
}
