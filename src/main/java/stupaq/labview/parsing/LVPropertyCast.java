package stupaq.labview.parsing;

import com.ni.labview.LVDataTypeRaw;
import com.ni.labview.StringType;

import stupaq.labview.UID;

public interface LVPropertyCast<T> {
  public static final LVPropertyCast<String> castString = new LVPropertyCast<String>() {
    @Override
    public String get(Object value) {
      return ((StringType) value).getVal();
    }
  };
  public static final LVPropertyCast<Integer> castInteger = new LVPropertyCast<Integer>() {
    @Override
    public Integer get(Object value) {
      return Integer.valueOf(((LVDataTypeRaw) value).getVal());
    }
  };
  public static final LVPropertyCast<UID> castUID = new LVPropertyCast<UID>() {
    @Override
    public UID get(Object value) {
      return new UID(castInteger.get(value));
    }
  };

  public T get(Object value);
}
