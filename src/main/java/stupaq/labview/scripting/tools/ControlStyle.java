package stupaq.labview.scripting.tools;

import com.jacob.com.Variant;

public enum ControlStyle {
  VARIANT(3310, DataRepresentation.UNKNOWN),
  BOOLEAN(21002, DataRepresentation.UNKNOWN),
  NUMERIC_EXT(21003, DataRepresentation.EXT),
  NUMERIC_DBL(21003, DataRepresentation.DBL),
  NUMERIC_SGL(21003, DataRepresentation.SGL),
  NUMERIC_I32(21003, DataRepresentation.I32),
  NUMERIC_I16(21003, DataRepresentation.I16),
  NUMERIC_I8(21003, DataRepresentation.I8),
  NUMERIC_U32(21003, DataRepresentation.U32),
  NUMERIC_U16(21003, DataRepresentation.U16),
  NUMERIC_U8(21003, DataRepresentation.U8),
  NUMERIC_CXT(21003, DataRepresentation.CXT),
  NUMERIC_CDB(21003, DataRepresentation.CDB),
  NUMERIC_CSG(21003, DataRepresentation.CSG),
  NUMERIC_I64(21003, DataRepresentation.I64),
  NUMERIC_U64(21003, DataRepresentation.U64),
  NUMERIC_FXP(21003, DataRepresentation.FXP),
  STRING(21701, DataRepresentation.UNKNOWN);

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
