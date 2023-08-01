package br.com.andre.utils;

import java.lang.reflect.Field;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

public class ConfigInjectionInitializer {

    public static void initialize(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ConfigInjectYaml.class)) {
                ConfigInjectYaml annotation = field.getAnnotation(ConfigInjectYaml.class);
                String key = annotation.value();

                if (!key.isEmpty()) {
                    String value = getValueFromYaml(key);
                    if (value != null) {
                        field.setAccessible(true);
                        try {
                            if (field.getType().isAssignableFrom(int.class)) {
                                field.setInt(object, Integer.parseInt(value));
                            } else if (field.getType().isAssignableFrom(double.class)) {
                                field.setDouble(object, Double.parseDouble(value));
                            } else if (field.getType().isAssignableFrom(float.class)) {
                                field.setFloat(object, Float.parseFloat(value));
                            } else if (field.getType().isAssignableFrom(long.class)) {
                                field.setLong(object, Long.parseLong(value));
                            } else if (field.getType().isAssignableFrom(short.class)) {
                                field.setShort(object, Short.parseShort(value));
                            } else if (field.getType().isAssignableFrom(boolean.class)) {
                                field.setBoolean(object, Boolean.parseBoolean(value));
                            } else if (field.getType().isAssignableFrom(String.class)) {
                                System.out.println("Setting value: " + value);
                                field.set(object, value);
                                System.out.println("Value set: " + field.get(object));
                            } else {
                                throw new IllegalArgumentException("Type not supported: " + field.getType());
                            }
                        } catch (IllegalAccessException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static String getValueFromYaml(String key) {
        Properties properties = new Properties();

        try (InputStreamReader inputStream = new InputStreamReader(new FileInputStream("src/main/resources/application.yaml"))) {
            properties.load(inputStream);

            String[] keys = key.split("\\.");
            for (String k : keys) {
                Object value = ((Map<?, ?>) properties).get(k);
                if (value == null) {
                    System.out.println("Key not found: " + key);
                    return null;
                } else if (value instanceof Map) {
                    properties = (Properties) value;
                } else {
                    System.out.println("Key found: " + key + " = " + value);
                    return value.toString();
                }
            }

            return properties.getProperty("");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading YAML file.");
            return null;
        }
    }
}
