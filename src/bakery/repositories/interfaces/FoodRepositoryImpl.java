package bakery.repositories.interfaces;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.bakedFoods.interfaces.BaseFood;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FoodRepositoryImpl<F extends BaseFood> implements FoodRepository<BakedFood>{
    private Collection<BakedFood> bakedFoods;

    public FoodRepositoryImpl(){
        bakedFoods = new ArrayList<>();
    }

    @Override
    public BakedFood getByName(String name) {
        BakedFood nameFood = null;
        for (BakedFood bakedFood : bakedFoods) {
            if (bakedFood.getName().equals(name)){
                nameFood = bakedFood;
            }
        }
        return nameFood;
    }

    @Override
    public Collection<BakedFood> getAll() {
        return Collections.unmodifiableCollection(bakedFoods);
    }

    @Override
    public void add(BakedFood bakedFood) {
        bakedFoods.add(bakedFood);
    }
}
