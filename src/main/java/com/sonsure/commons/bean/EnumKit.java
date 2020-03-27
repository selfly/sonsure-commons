package com.sonsure.commons.bean;

import com.sonsure.commons.enums.IEnum;
import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EnumKit {

    private static final Class<?>[] EMPTY_CLASSES = new Class[]{};

    private static final Object[] EMPTY_OBJECT = new Object[]{};

    private static final ReflectionFactory REFLECTION_FACTORY = ReflectionFactory.getReflectionFactory();

    /**
     * Add an enum instance to the enum class given as argument
     *
     * @param <T>      the type of the enum (implicit)
     * @param enumType the class of the enum to be modified
     * @param enumName the name of the new enum instance to be added to the class.
     */
    public static <T extends Enum<?>> void addEnum(Class<T> enumType, String enumName) {
        addEnum(enumType, enumName, EMPTY_CLASSES, EMPTY_OBJECT);
    }


    /**
     * Add an enum instance to the enum class given as argument
     *
     * @param <T>             the type of the enum (implicit)
     * @param enumType        the class of the enum to be modified
     * @param enumName        the name of the new enum instance to be added to the class.
     * @param enumFileTypes   the enum file types
     * @param enumFieldValues the enum field values
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<?>> void addEnum(Class<T> enumType, String enumName, Class<?>[] enumFileTypes, Object[] enumFieldValues) {

        try {
            // previousValues
            List<T> values = getEnumValues(enumType);

            // build new enum
            T newValue = makeEnum(enumType, // The target enum class
                    enumName, // THE NEW ENUM INSTANCE TO BE DYNAMICALLY ADDED
                    values.size(),
                    enumFileTypes, // can be used to pass values to the enum constructor
                    enumFieldValues); // can be used to pass values to the enum constructor

            // add new value
            values.add(newValue);

            updateEnumValues(enumType, values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T extends IEnum> void deleteIEnumByCode(Class<T> enumType, String code) {
        List<T> enumValues = getEnumValues(enumType);
        Iterator<T> iterator = enumValues.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next.getCode().equals(code)) {
                iterator.remove();
            }
        }
        try {
            updateEnumValues(enumType, enumValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T extends Enum<?>> void deleteEnum(Class<T> enumType, String enumName) {
        // previousValues
        List<T> values = getEnumValues(enumType);
        Iterator<T> iterator = values.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next.name().equals(enumName)) {
                iterator.remove();
            }
        }
        try {
            updateEnumValues(enumType, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> List<T> getEnumValues(Class<T> enumClass) {
        if (!Enum.class.isAssignableFrom(enumClass)) {
            throw new RuntimeException("class " + enumClass + " is not an instance of Enum");
        }
        return new ArrayList<T>(Arrays.asList(enumClass.getEnumConstants()));
    }

    private static <T> void updateEnumValues(Class<T> enumClass, List<T> values) throws NoSuchFieldException, IllegalAccessException {
        Field valuesField = enumClass.getDeclaredField("$VALUES");

        setFailSafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumClass, 0)));
        cleanEnumCache(enumClass);
    }

    private static <T> T makeEnum(Class<T> enumClass, String value, int ordinal,
                                  Class<?>[] additionalTypes, Object[] additionalValues) throws NoSuchMethodException, InvocationTargetException, InstantiationException {
        Object[] params = new Object[additionalValues.length + 2];
        params[0] = value;
        params[1] = ordinal;
        System.arraycopy(additionalValues, 0, params, 2, additionalValues.length);
        return enumClass.cast(getConstructorAccessor(enumClass, additionalTypes).newInstance(params));
    }

    private static ConstructorAccessor getConstructorAccessor(Class<?> enumClass,
                                                              Class<?>[] additionalParameterTypes) throws NoSuchMethodException {
        Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
        parameterTypes[0] = String.class;
        parameterTypes[1] = int.class;
        System.arraycopy(additionalParameterTypes, 0,
                parameterTypes, 2, additionalParameterTypes.length);
        return REFLECTION_FACTORY.newConstructorAccessor(enumClass.getDeclaredConstructor(parameterTypes));
    }

    private static void setFailSafeFieldValue(Field field, Object target, Object value)
            throws NoSuchFieldException, IllegalAccessException {

        // let's make the field accessible
        field.setAccessible(true);

        // next we change the modifier in the Field instance to
        // not be final anymore, thus tricking reflection into
        // letting us modify the static final field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);

        // blank out the final bit in the modifiers int
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);

        FieldAccessor fa = REFLECTION_FACTORY.newFieldAccessor(field, false);
        fa.set(target, value);
    }

    private static void cleanEnumCache(Class<?> enumClass)
            throws NoSuchFieldException, IllegalAccessException {
        blankField(enumClass, "enumConstantDirectory"); // Sun (Oracle?!?) JDK 1.5/6
        blankField(enumClass, "enumConstants"); // IBM JDK
    }

    private static void blankField(Class<?> enumClass, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        for (Field field : Class.class.getDeclaredFields()) {
            if (field.getName().contains(fieldName)) {
                AccessibleObject.setAccessible(new Field[]{field}, true);
                setFailSafeFieldValue(field, enumClass, null);
                break;
            }
        }
    }
}
