package onlineShop.core.interfaces;

import onlineShop.models.products.components.BaseComponent;
import onlineShop.models.products.computers.BaseComputer;
import onlineShop.models.products.computers.Computer;
import onlineShop.models.products.computers.DesktopComputer;
import onlineShop.models.products.computers.Laptop;
import onlineShop.models.products.peripherals.*;

import java.util.ArrayList;
import java.util.List;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.ADDED_COMPUTER;
import static onlineShop.common.constants.OutputMessages.ADDED_PERIPHERAL;

public class ControllerImpl implements Controller{
    List<BaseComputer> computers;
    List<BasePeripheral> peripherals;
    List<BaseComponent> components;

    public ControllerImpl() {
        this.computers = new ArrayList<>();
        this.peripherals = new ArrayList<>();
        this.components = new ArrayList<>();
    }

    @Override
    public String addComputer(String computerType, int id, String manufacturer, String model, double price) {
        BaseComputer computer1 = null;
        for (BaseComputer computer : computers) {
            if (computer.getId() == id){
                throw new IllegalArgumentException(EXISTING_COMPUTER_ID);
            }
        }

        if (computerType.equals("DesktopComputer")){
            computer1 = new DesktopComputer(id,manufacturer,model,price);
            computers.add(computer1);
        }else if (computerType.equals("Laptop")){
            computer1 = new Laptop(id,manufacturer,model,price);
            computers.add(computer1);
        }else {
            throw new IllegalArgumentException(INVALID_COMPUTER_TYPE);
        }
        return String.format(ADDED_COMPUTER,id);
    }

    @Override
    public String addPeripheral(int computerId, int id, String peripheralType, String manufacturer,
                                String model, double price, double overallPerformance, String connectionType) {
        BasePeripheral peripheral1 = null;
        for (BasePeripheral peripheral : peripherals) {
            if (peripheral.getId() == id){
                throw new IllegalArgumentException(EXISTING_PERIPHERAL_ID);
            }
        }
        if (peripheralType.equals("Headset")){
            peripheral1 = new Headset(id,manufacturer,model,price,overallPerformance,connectionType);
            addPeripheralInComputer(computers,computerId,peripheral1);
            peripherals.add(peripheral1);

        }else if (peripheralType.equals("Keyboard")){
            peripheral1 = new Keyboard(id,manufacturer,model,price,overallPerformance,connectionType);
            addPeripheralInComputer(computers,computerId,peripheral1);
            peripherals.add(peripheral1);

        }else if (peripheralType.equals("Monitor")){
            peripheral1 = new Monitor(id,manufacturer,model,price,overallPerformance,connectionType);
            addPeripheralInComputer(computers,computerId,peripheral1);
            peripherals.add(peripheral1);
        }else if (peripheralType.equals("Mouse")){
            peripheral1 = new Mouse(id,manufacturer,model,price,overallPerformance,connectionType);
            addPeripheralInComputer(computers,computerId,peripheral1);
            peripherals.add(peripheral1);
        }else {
            throw new IllegalArgumentException(INVALID_PERIPHERAL_TYPE);
        }
        return String.format(ADDED_PERIPHERAL,peripheral1.getClass().getSimpleName(),id, computerId);
    }

    @Override
    public String removePeripheral(String peripheralType, int computerId) {
        return null;
    }

    @Override
    public String addComponent(int computerId, int id, String componentType, String manufacturer,
                               String model, double price, double overallPerformance, int generation) {
        BaseComponent baseComponent = null;
        for (BaseComponent component : components) {
            if (component.getId() == id){
                throw new IllegalArgumentException(EXISTING_COMPONENT_ID);
            }
        }
        if (componentType.equals("Motherboard")){

        }else if (componentType.equals("PowerSupply")){

        }else if (componentType.equals("RandomAccessMemory")){

        }else if (componentType.equals("SolidStateDrive")){

        }else if (componentType.equals("VideoCard")){

        }else {

        }

        return null;
    }

    @Override
    public String removeComponent(String componentType, int computerId) {
        return null;
    }

    @Override
    public String buyComputer(int id) {
        return null;
    }

    @Override
    public String BuyBestComputer(double budget) {
        return null;
    }

    @Override
    public String getComputerData(int id) {
        return null;
    }

    public void addPeripheralInComputer(List<BaseComputer> computers, int id,BasePeripheral basePeripheral){
        Computer computer1 = null;
        for (BaseComputer computer : computers) {
            if (computer.getId() == id){
                      computer1 = computer;
                      computer1.addPeripheral(basePeripheral);
                      break;
            }
        }
        if (computer1 == null){
            throw new  IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
        }

    }
    public void addComponentInComputer(List<BaseComputer> computers, int id,BaseComponent baseComponent){
        Computer computer1 = null;
        for (BaseComputer computer : computers) {
            if (computer.getId() == id){
                computer1 = computer;
                computer1.addComponent(baseComponent);
                break;
            }
        }
        if (computer1 == null){
            throw new  IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
        }

    }
}
