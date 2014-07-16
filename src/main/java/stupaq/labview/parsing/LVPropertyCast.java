package stupaq.labview.parsing;

import com.google.common.base.Optional;
import com.google.common.base.Verify;

import com.ni.labview.ClusterType;
import com.ni.labview.LVDataTypeRaw;
import com.ni.labview.StringType;

import stupaq.labview.UID;

public interface LVPropertyCast<T> {
  public static final LVPropertyCast<String> castRaw = new LVPropertyCast<String>() {
    @Override
    public String get(Object value) {
      return ((LVDataTypeRaw) value).getVal();
    }
  };
  public static final LVPropertyCast<String> castString = new LVPropertyCast<String>() {
    @Override
    public String get(Object value) {
      return ((StringType) value).getVal();
    }
  };
  public static final LVPropertyCast<Optional<String>> castLabel =
      new LVPropertyCast<Optional<String>>() {
        @Override
        public Optional<String> get(Object value) {
          String label = castString.get(value);
          return label.isEmpty() ? Optional.<String>absent() : Optional.of(label);
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
  public static final LVPropertyCast<Boolean> castBoolean = new LVPropertyCast<Boolean>() {
    @Override
    public Boolean get(Object value) {
      return Boolean.parseBoolean(castRaw.get(value));
    }
  };
  public static final LVPropertyCast<Optional<UID>> castOwner =
      new LVPropertyCast<Optional<UID>>() {
        @Override
        public Optional<UID> get(Object value) {
          ClusterType cluster = (ClusterType) value;
          Verify.verify(cluster.getNumElts() == 2);
          boolean isGObject = castBoolean.get(cluster.getI8OrI16OrI32().get(0));
          UID uid = castUID.get(cluster.getI8OrI16OrI32().get(1));
          return isGObject ? Optional.of(uid) : Optional.<UID>absent();
        }
      };

  public T get(Object value);
}
