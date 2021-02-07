package pet.taskplanner.mapper;

import pet.taskplanner.entity.Entity;
import pet.taskplanner.entity.annotation.Column;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lelay
 * @since 06.02.2021
 */
public class EntityMapper {

    public <T extends Entity> T map(ResultSet rs, Class<T> entityClass) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!rs.next()) {
            return null;
        }

        Map<String, Field> fieldNameToColumnName = Stream.of(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toMap(
                        f -> f.getName().toLowerCase(),
                        f -> f
                ));

        Map<String, Method> fieldNameToSetter = Stream.of(entityClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("set")) //gets only setters
                .filter(method -> fieldNameToColumnName.containsKey(method.getName().substring(3).toLowerCase())) //remote 'set' from method name
                .collect(Collectors.toMap(
                        m -> m.getName().substring(3).toLowerCase(),
                        m -> m
                ));

        T entityInstance = entityClass.getConstructor().newInstance();
        for (var entry : fieldNameToColumnName.entrySet()) {
            String fieldName = entry.getKey();
            Field field = entry.getValue();
            Method method = fieldNameToSetter.get(fieldName);

            method.invoke(
                    entityInstance,
                    readField(
                            rs,
                            field.getAnnotation(Column.class).value(),
                            field.getType().getSimpleName()
                    )
            );
        }

        return entityInstance;
    }

    private Object readField(ResultSet rs, String fName, String fType) throws SQLException {
        if (fType.equals(Long.class.getSimpleName())) {
            return rs.getLong(fName);
        } else if (fType.equals(Integer.class.getSimpleName())) {
            return rs.getInt(fName);
        } else if (fType.equals(String.class.getSimpleName())) {
            return rs.getString(fName);
        } else if (fType.equals(Boolean.class.getSimpleName())) {
            return rs.getBoolean(fName);
        } else {
            return rs.getObject(fName);
        }
    }
}
