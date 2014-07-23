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

public final class TracingVisitor {
  private static final Logger LOGGER = LoggerFactory.getLogger(TracingVisitor.class);
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

  private TracingVisitor() {
  }

  @SuppressWarnings("unchecked")
  public static VIElementsVisitor<NeverThrownException> create() {
    return (VIElementsVisitor<NeverThrownException>) Proxy.newProxyInstance(
        TracingVisitor.class.getClassLoader(), new Class[]{VIElementsVisitor.class},
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (LOGGER.isTraceEnabled()) {
              if (args == null) {
                LOGGER.trace("{}()", method.getName());
              } else {
                LOGGER.trace("{}({})", method.getName(),
                    Joiner.on(", ").join(Iterables.transform(Arrays.asList(args), QUOTE_STRINGS)));
              }
            }
            return null;
          }
        });
  }
}
