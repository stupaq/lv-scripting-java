package stupaq.labview.parsing;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Verify;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;

import com.ni.labview.ArrayType;
import com.ni.labview.ClusterType;
import com.ni.labview.LVDataTypeRaw;
import com.ni.labview.StringType;

import java.util.List;
import java.util.Map;

import stupaq.labview.UID;
import stupaq.labview.VIPath;

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
      String rep = castRaw.get(value);
      return rep.isEmpty() ? 0 : Integer.valueOf(rep);
    }
  };
  public static final LVPropertyCast<Double> castDouble = new LVPropertyCast<Double>() {
    @Override
    public Double get(Object value) {
      String rep = castRaw.get(value);
      return rep.isEmpty() ? 0.0 : Double.valueOf(rep);
    }
  };
  public static final LVPropertyCast<UID> castUID = new LVPropertyCast<UID>() {
    @Override
    public UID get(Object value) {
      return new UID(castInteger.get(value));
    }
  };
  public static final LVPropertyCast<List<Object>> castList = new LVPropertyCast<List<Object>>() {
    @Override
    public List<Object> get(Object value) {
      return ((ArrayType) value).getI8OrI16OrI32();
    }
  };
  public static final LVPropertyCast<List<UID>> castListUID = new LVPropertyCast<List<UID>>() {
    @Override
    public List<UID> get(Object value) {
      return FluentIterable.from(castList.get(value)).transform(new Function<Object, UID>() {
        @Override
        public UID apply(Object input) {
          return castUID.get(input);
        }
      }).toList();
    }
  };
  public static final LVPropertyCast<Boolean> castBoolean = new LVPropertyCast<Boolean>() {
    @Override
    public Boolean get(Object value) {
      return Boolean.parseBoolean(castRaw.get(value)) || castInteger.get(value) != 0;
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
  public static final LVPropertyCast<Map<String, Object>> castMapFromStrings =
      new LVPropertyCast<Map<String, Object>>() {
        @Override
        public Map<String, Object> get(Object value) {
          Map<String, Object> map = Maps.newHashMap();
          for (Object object : castList.get(value)) {
            ClusterType cluster = (ClusterType) object;
            map.put(castString.get(cluster.getI8OrI16OrI32().get(0)),
                castDouble.get(cluster.getI8OrI16OrI32().get(1)));
          }
          return map;
        }
      };
  public static final LVPropertyCast<VIPath> castVIPath = new LVPropertyCast<VIPath>() {
    @Override
    public VIPath get(Object value) {
      return new VIPath(castRaw.get(value));
    }
  };

  public T get(Object value);
}
