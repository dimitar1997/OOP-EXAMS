package onlineShop.models.products.computers;

import onlineShop.models.products.BaseProduct;
import onlineShop.models.products.components.BaseComponent;
import onlineShop.models.products.components.Component;
import onlineShop.models.products.peripherals.BasePeripheral;
import onlineShop.models.products.peripherals.Peripheral;

import java.util.ArrayList;
import java.util.List;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.COMPUTER_COMPONENTS_TO_STRING;
import static onlineShop.common.constants.OutputMessages.COMPUTER_PERIPHERALS_TO_STRING;

public class BaseComputer extends BaseProduct implements Computer {
    List<Component> components;
    List<Peripheral> peripherals;
    public BaseComputer(int id, String manufacturer, String model, double price, double overallPerformance) {
        super(id, manufacturer, model, price, overallPerformance);
        components = new ArrayList<>();
        peripherals = new ArrayList<>();
    }

    @Override
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public List<Peripheral> getPeripherals() {
        return peripherals;
    }

    @Override
    public void addComponent(Component component) {
        if (components.stream().anyMatch(c -> c.getClass() == component.getClass())){
            throw new IllegalArgumentException(String.format(EXISTING_COMPONENT, component.getClass().getSimpleName()
            ,this.getClass().getSimpleName(), component.getId()));
        }
        components.add(component);
    }

    @Override
    public Component removeComponent(String componentType) {
        Component cp = null;
        if (components == null || components.stream().noneMatch(c -> c.getClass().getSimpleName().equals(componentType))){
            throw new IllegalArgumentException(String.format(NOT_EXISTING_COMPONENT, componentType,
                    this.getClass().getSimpleName(), this.getId()));
        }
       int index = 0;
        for (int i = 0; i < components.size() ; i++) {
            if (components.get(i).getClass().getSimpleName().equals(componentType)){
                index = i;
                break;
            }
        }
        return components.remove(index);
    }

    @Override
    public void addPeripheral(Peripheral peripheral) {
        if (peripherals.stream().anyMatch(c -> c.getClass() == peripheral.getClass())){
            throw new IllegalArgumentException(String.format(EXISTING_PERIPHERAL, peripheral.getClass().getSimpleName()
                    ,this.getClass().getSimpleName(), peripheral.getId()));
        }
        peripherals.add(peripheral);
    }

    @Override
    public Peripheral removePeripheral(String peripheralType) {
        Component cp = null;
        if (peripherals == null || peripherals.stream().noneMatch(c -> c.getClass().getSimpleName().equals(peripheralType))){
            throw new IllegalArgumentException(String.format(NOT_EXISTING_PERIPHERAL, peripheralType,
                    this.getClass().getSimpleName(), this.getId()));
        }
        int index = 0;
        for (int i = 0; i < peripherals.size() ; i++) {
            if (peripherals.get(i).getClass().getSimpleName().equals(peripheralType)){
                index = i;
                break;
            }
        }
        return peripherals.remove(index);
    }

    @Override
    public double getPrice() {
        return components.stream().mapToDouble(Component::getPrice).sum()
                + peripherals.stream().mapToDouble(Peripheral::getPrice).sum();
    }

    @Override
    public double getOverallPerformance() {
        if (components.size() == 0){
            return this.getOverallPerformance();
        }else {
            return components.stream().mapToDouble(Component::getOverallPerformance).average().orElse(0) + super.getOverallPerformance();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(System.lineSeparator());
        sb.append(String.format(COMPUTER_COMPONENTS_TO_STRING,components.size()));
        sb.append(System.lineSeparator());
        for (Component component : components) {
            sb.append("  " + component.toString()).append(System.lineSeparator());
        }
        sb.append(String.format(COMPUTER_PERIPHERALS_TO_STRING,peripherals.size(),getOverallPerformance()))
                .append(System.lineSeparator());
        for (Peripheral peripheral : peripherals) {
            sb.append("  ").append(peripheral.toString()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
