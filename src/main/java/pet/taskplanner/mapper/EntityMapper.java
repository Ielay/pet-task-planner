package pet.taskplanner.mapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pet.taskplanner.entity.Entity;
import pet.taskplanner.entity.annotation.Column;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ResultSet parsing and mapping it to some entity using the entity class annotations (using reflection api)
 * to determine the correct names of columns at runtime
 *
 * @author lelay
 * @since 06.02.2021
 */
public class EntityMapper {

    public <T extends Entity> @NotNull List<T> mapList(ResultSet rs, Class<T> entityClass) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> entityList = new ArrayList<>();
        while (rs.next()) {
            entityList.add(map(rs, entityClass));
        }

        return entityList;
    }

    public <T extends Entity> @Nullable T map(ResultSet rs, Class<T> entityClass) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (rs.isBeforeFirst()) {
            if (!rs.next()) {
                return null;
            }
        }

        Map<String, Field> fieldNameToColumnName = Stream.of(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toMap(
                        f -> f.getName().toLowerCase(),
                        f -> f
                ));

        Map<String, Method> fieldNameToSetter = Stream.of(entityClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("set")) //gets only setters
                .filter(method -> fieldNameToColumnName.containsKey(method.getName().substring(3).toLowerCase())) //remove 'set' from method name
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
        } else if (fType.equals(OffsetDateTime.class.getSimpleName())) {
            return rs.getObject(fName, OffsetDateTime.class);
        } else {
            System.out.println("Default: field converting to Object");
            return rs.getObject(fName);
        }
    }
}
