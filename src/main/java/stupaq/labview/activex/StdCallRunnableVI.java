package stupaq.labview.activex;

import com.google.common.base.Preconditions;

import com.jacob.com.SafeArray;
import com.jacob.com.Variant;
import com.jacob.extensions.ActiveXType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

import static java.util.Arrays.asList;

public class StdCallRunnableVI extends ActiveXRunnableVI {
  private static final int ARGS_INDEX = 0;
  private static final int ERR_STATUS_INDEX = 1;
  private static final int ERR_CODE_INDEX = 2;
  private static final int ERR_SOURCE_INDEX = 3;
  private static final int RETURN_INDEX = 4;
  private static final List<String> CONTROL_LIST =
      asList("__args", "__errStatus", "__errCode", "__errSource", "__return");
  private static final Logger LOGGER = LoggerFactory.getLogger(StdCallRunnableVI.class);

  public StdCallRunnableVI(ActiveXApplication application, VIPath viPath) {
    super(application, viPath);
  }

  public Variant stdCall(Variant... args) throws VIErrorException {
    if (LOGGER.isTraceEnabled()) {
      StringBuilder message = new StringBuilder(getVIPath().getBaseName());
      boolean isFirst = true;
      for (Variant arg : args) {
        message.append(isFirst ? "(" : ", ").append(arg.toJavaObject());
        isFirst = false;
      }
      message.append(')');
      LOGGER.trace(message.toString());
    }
    SafeArray args1 = new SafeArray(Variant.VariantVariant, args.length);
    args1.fromVariantArray(args);
    SafeArray returned = call(CONTROL_LIST, args1);
    boolean errStatus = returned.getBoolean(ERR_STATUS_INDEX);
    if (errStatus) {
      int errCode = returned.getInt(ERR_CODE_INDEX);
      String errSource = returned.getString(ERR_SOURCE_INDEX);
      throw new VIErrorException(errCode, errSource);
    }
    return returned.getVariant(RETURN_INDEX);
  }

  public Variant stdCall(Object... args) throws VIErrorException {
    Variant[] args1 = new Variant[args.length];
    int index = 0;
    for (Object arg : args) {
      Preconditions.checkNotNull(arg);
      if (arg instanceof Variant) {
        args1[index++] = (Variant) arg;
      } else if (arg instanceof ActiveXType) {
        args1[index++] = ((ActiveXType) arg).toVariant();
      } else {
        args1[index++] = new Variant(arg);
      }
    }
    return stdCall(args1);
  }
}
