package task1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

  public static void main(String[] args) {
    try {
      Class clazz = new HelloClassLoader().findClass("Hello");
      Method methodHello = clazz.getMethod("hello");
      methodHello.invoke(clazz.newInstance());
    } catch (ClassNotFoundException | NoSuchMethodException
        | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Class<?> findClass(String name) throws ClassNotFoundException {
    byte[] bytes = getBytes();
    return defineClass(name, bytes, 0, bytes.length);
  }

  private static byte[] getBytes() {
    byte[] bytes = new byte[0];
    try {
      FileInputStream fileInputStream = new FileInputStream(new File("./src/task1/Hello.xlass"));
      bytes = new byte[fileInputStream.available()];
      fileInputStream.read(bytes);
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (int i=0; i<bytes.length; i++) {
      bytes[i] = (byte) (255 - bytes[i]);
    }
    return bytes;
  }
}
