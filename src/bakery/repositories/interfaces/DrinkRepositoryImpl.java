package bakery.repositories.interfaces;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.BaseDrink;
import bakery.entities.drinks.interfaces.Drink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DrinkRepositoryImpl<D extends BaseDrink> implements DrinkRepository<Drink>{
    private Collection<Drink> drinks;

    public DrinkRepositoryImpl() {
        this.drinks = new ArrayList<>();;
    }

    @Override
    public Drink getByNameAndBrand(String drinkName, String drinkBrand) {
        Drink drink = null;
        for (Drink drink1: drinks){
            if (drink1.getName().equals(drinkName) || drink1.getBrand().equals(drinkBrand)){
                drink = drink1;
            }
        }
        return drink;
    }

    @Override
    public Collection<Drink> getAll() {
        return Collections.unmodifiableCollection(drinks);
    }

    @Override
    public void add(Drink drink) {
    drinks.add(drink);
    }
}
