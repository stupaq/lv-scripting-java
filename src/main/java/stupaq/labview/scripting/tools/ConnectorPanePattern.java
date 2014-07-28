package stupaq.labview.scripting.tools;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedMap;

import com.jacob.com.Variant;
import com.jacob.extensions.ActiveXType;

import java.util.Map.Entry;

public enum ConnectorPanePattern implements ActiveXType {
  P4800(4800),
  P4801(4801),
  P4802(4802),
  P4805(4805),
  P4807(4807),
  P4810(4810),
  P4811(4811),
  P4812(4812),
  P4813(4813),
  P4815(4815),
  P4826(4826),
  P4829(4829),
  P4833(4833),
  P4834(4834),
  P4835(4835);
  private static final ImmutableSortedMap<Integer, ConnectorPanePattern> countToPattern =
      ImmutableSortedMap.<Integer, ConnectorPanePattern>naturalOrder()
          .put(1, P4800)
          .put(2, P4801)
          .put(3, P4802)
          .put(4, P4805)
          .put(5, P4807)
          .put(6, P4810)
          .put(7, P4811)
          .put(8, P4812)
          .put(9, P4813)
          .put(10, P4826)
          .put(11, P4829)
          .put(12, P4815)
          .put(16, P4833)
          .put(20, P4834)
          .put(28, P4835)
          .build();

  public static final int DO_NOT_CONNECT = -1;
  private final int pattern;

  private ConnectorPanePattern(int pattern) {
    this.pattern = pattern;
  }

  public static ConnectorPanePattern choosePattern(int connectorsCount) {
    Entry<Integer, ConnectorPanePattern> safeChoice = countToPattern.ceilingEntry(connectorsCount);
    Preconditions.checkArgument(safeChoice != null);
    return safeChoice.getValue();
  }

  @Override
  public Variant toVariant() {
    return new Variant(pattern);
  }
}
