package stupaq.labview.parsing;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class PrintingVisitor {
  private static final Logger LOGGER = LoggerFactory.getLogger(PrintingVisitor.class);
  private static final Function<Object, String> QUOTE_STRINGS = new Function<Object, String>() {
    @Override
    public String apply(Object input) {
      if (input instanceof String) {
        return "\"" + input + "\"";
      } else {
        return input.toString();
      }
    }
  };

  private PrintingVisitor() {
  }

  @SuppressWarnings("unchecked")
  public static VIElementsVisitor<RuntimeException> create() {
    return (VIElementsVisitor<RuntimeException>) Proxy.newProxyInstance(
        PrintingVisitor.class.getClassLoader(), new Class[]{VIElementsVisitor.class},
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (args == null) {
              LOGGER.trace("{}()", method.getName());
            } else {
              LOGGER.trace("{}({})", method.getName(),
                  Joiner.on(", ").join(Iterables.transform(Arrays.asList(args), QUOTE_STRINGS)));
            }
            return null;
          }
        });
  }
}
