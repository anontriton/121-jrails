package jrails;

import java.util.List;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Model {

    private static final String DB_FILE = "jrailsdb.txt";
    private static int newId = 1; // tracks next unique id to new instance
    private int id; // holds unique id of current object

    public Model() {
        this.id = 0; // uninitialized object has id = 0
    }

    public void save() {
        /* this is an instance of the current model */
        if (this.id == 0) {
            this.id = newId++;
            addRecord();
        } else {
            updateRecord();
        }
    }

    public int id() {
        return this.id;
    }

    public static <T> T find(Class<T> c, int id) {
        List<T> records = all(c);

        for (T record : records) {
            try {
                // get id field
                Field idField = Model.class.getDeclaredField("id");
                idField.setAccessible(true);
                // compare id of current record with specified id
                if ((int) idField.get(record) == id) {
                    return record;
                }
            } catch (Exception e) {
                throw new RuntimeException("error accessing id field", e);
            }
        }

        return null;
    }

    public static <T> List<T> all(Class<T> c) {
        List<T> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // split each line into array of strings separated by commas
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]); // first elem is id

                if (fields[1].equals(c.getName())) {
                    T instance = c.getDeclaredConstructor().newInstance();
                    // access "id" and make it accessible, set its value to parsed id from file
                    Field idField = Model.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(instance, id);

                    // iterate over all fields and check if annotated with @Column and set value based on its type
                    Field[] modelFields = c.getFields();
                    for (int i = 0; i < modelFields.length; i++) {
                        Field field = modelFields[i];
                        if (field.isAnnotationPresent(Column.class)) {
                            field.setAccessible(true);

                            String value = fields[i + 2]; // first two fields are id and clas name
                            if (field.getType() == int.class) {
                                field.set(instance, Integer.parseInt(value));
                            } else if (field.getType() == boolean.class) {
                                field.set(instance, Boolean.parseBoolean(value));
                            } else {
                                field.set(instance, value.equals("null") ? null : value);
                            }
                        }
                    }
                    records.add(instance);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("error reading database", e);
        }

        return records;
    }


    public void destroy() {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(this.id + "," + this.getClass().getName())) {
                    lines.add(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("error reading database", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("error writing database", e);
        }
    }

    public static void reset() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE))) {
            writer.write("");
            newId = 1; // reset id counter
        } catch (IOException e) {
            throw new RuntimeException("error resetting database", e);
        }
    }

    // appends serialized representation of instance to the db file
    private void addRecord() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE, true))) {
            writer.write(serialize());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("error writing database", e);
        }
    }

    // reads file, replaces old record with updated one, and writes the file back
    private void updateRecord() {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(this.id + "," + this.getClass().getName())) {
                    lines.add(serialize());
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("error reading database", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("error writing database", e);
        }
    }

    // converts current instance into string representation for stage in db file
    private String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id).append(",");
        sb.append(this.getClass().getName());

        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    sb.append(",").append(value == null ? "null" : value.toString());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("error serializing field", e);
                }
            }
        }
        return sb.toString();
    }
}
