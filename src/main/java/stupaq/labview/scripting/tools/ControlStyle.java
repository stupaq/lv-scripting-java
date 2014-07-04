package stupaq.labview.scripting.tools;

import com.jacob.com.Variant;

public enum ControlStyle {
  VARIANT(3310, -1),
  BOOLEAN(21002, -1),
  NUMERIC_EXT(21003, 0),
  NUMERIC_DBL(21003, 1),
  NUMERIC_SGL(21003, 2),
  NUMERIC_I32(21003, 3),
  NUMERIC_I16(21003, 4),
  NUMERIC_I8(21003, 5),
  NUMERIC_U32(21003, 6),
  NUMERIC_U16(21003, 7),
  NUMERIC_U8(21003, 8),
  NUMERIC_CXT(21003, 9),
  NUMERIC_CDB(21003, 10),
  NUMERIC_CSG(21003, 11),
  NUMERIC_I64(21003, 12),
  NUMERIC_U64(21003, 13),
  NUMERIC_FXP(21003, 14),
  STRING(21071, -1);

  private final int style;
  private final byte representation;

  ControlStyle(int style, int representation) {
    this.style = style;
    this.representation = (byte) representation;
  }

  public Variant style() {
    return new Variant(style);
  }

  public Variant representation() {
    return new Variant(representation);
  }
}
