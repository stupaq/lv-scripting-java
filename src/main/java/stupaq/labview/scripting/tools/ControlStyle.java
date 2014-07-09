package stupaq.labview.scripting.tools;

import com.jacob.com.Variant;

import static stupaq.labview.scripting.tools.DataRepresentation.*;

public enum ControlStyle {
  VARIANT(3310, UNKNOWN),
  BOOLEAN(21002, UNKNOWN),
  NUMERIC_EXT(21003, EXT),
  NUMERIC_DBL(21003, DBL),
  NUMERIC_SGL(21003, SGL),
  NUMERIC_I32(21003, I32),
  NUMERIC_I16(21003, I16),
  NUMERIC_I8(21003, I8),
  NUMERIC_U32(21003, U32),
  NUMERIC_U16(21003, U16),
  NUMERIC_U8(21003, U8),
  NUMERIC_CXT(21003, CXT),
  NUMERIC_CDB(21003, CDB),
  NUMERIC_CSG(21003, CSG),
  NUMERIC_I64(21003, I64),
  NUMERIC_U64(21003, U64),
  NUMERIC_FXP(21003, FXP),
  STRING(21701, UNKNOWN);

  private final int style;
  private final DataRepresentation representation;

  private ControlStyle(int style, DataRepresentation representation) {
    this.style = style;
    this.representation = representation;
  }

  public Variant style() {
    return new Variant(style);
  }

  public Variant representation() {
    return representation.toVariant();
  }
}
