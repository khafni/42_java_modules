package fr._42;
import fr._42.classes.Sword;

import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Classes: ");
        Menu menu = new Menu();
        for (String s : menu.getClassNames())
            System.out.println(s);
        System.out.println("--------------");
        System.out.println("Enter class name: ");
        System.out.print("-> ");
        String className = scanner.nextLine();
        Class<?> clazz = null;
        try {
            clazz = Class.forName("fr._42.classes." +  className);
//            Field[] fields = clazz.getFields();
//            System.out.println(fields.length);
//            for (Field f : fields)
//                System.out.println(f.getName());
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("class not found");
            return ;
        }
//        System.out.println(clazz.getName());

//        Field[] fields = Arrays.stream(clazz.getDeclaredFields())
//                .filter(field -> Modifier.isPrivate(field.getModifiers())).toArray(Field[]::new);
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("fields:");
        for (Field f : fields)
            System.out.println("  " + f.getType().getSimpleName() + " " + f.getName());
        System.out.println("methods:");
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            System.out.print("  " + method.getName());
            Class<?>[] parameterTypes = method.getParameterTypes();
            StringBuilder paramTypes = new StringBuilder();
            paramTypes.append("(");
            for (int i = 0; i < parameterTypes.length; i++) {
                if (i > 0)
                    paramTypes.append(", ");
                paramTypes.append(parameterTypes[i].getSimpleName());
            }
            paramTypes.append(")");
            System.out.println(paramTypes);
        }
        System.out.println("-----------------");
        System.out.println("Let's create an object.");
        Constructor constructor = Arrays.stream(clazz.getConstructors())
                .filter(constructor1 -> constructor1.getParameters().length > 1).toArray(Constructor[]::new)[0];
        Parameter[] parameters = constructor.getParameters();
        System.out.println(parameters[0].getName());
    }
}