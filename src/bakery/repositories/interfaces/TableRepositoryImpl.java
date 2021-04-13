package bakery.repositories.interfaces;

import bakery.entities.tables.interfaces.BaseTable;
import bakery.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TableRepositoryImpl<T extends BaseTable> implements TableRepository<Table> {
    Collection<Table> tables;

    public TableRepositoryImpl() {
        this.tables = new ArrayList<>();;
    }

    @Override
    public Collection<Table> getAll() {
        return Collections.unmodifiableCollection(tables);
    }

    @Override
    public void add(Table table) {
        tables.add(table);
    }

    @Override
    public Table getByNumber(int number) {
        Table table = null;
        for (Table table1 : tables) {
            if (table1.getTableNumber() == number) {
                table = table1;
            }
        }
        return table;
    }
}
