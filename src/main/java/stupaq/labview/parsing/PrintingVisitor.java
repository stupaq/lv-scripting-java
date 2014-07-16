package stupaq.labview.parsing;

import com.google.common.base.Joiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PrintingVisitor {
  private static final Logger LOGGER = LoggerFactory.getLogger(PrintingVisitor.class);

  private PrintingVisitor() {
  }

  public static HierarchyVisitor create() {
    return (HierarchyVisitor) Proxy.newProxyInstance(PrintingVisitor.class.getClassLoader(),
        new Class[]{HierarchyVisitor.class}, new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            LOGGER.trace("{}({})", method.getName(), Joiner.on(", ").join(args));
            return null;
          }
        });
  }
}
