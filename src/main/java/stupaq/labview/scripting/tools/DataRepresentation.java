package stupaq.labview.scripting.tools;

import com.jacob.com.Variant;
import com.jacob.extensions.ActiveXType;

public enum DataRepresentation implements ActiveXType {
  UNKNOWN(-1),
  EXT(0),
  DBL(1),
  SGL(2),
  I32(3),
  I16(4),
  I8(5),
  U32(6),
  U16(7),
  U8(8),
  CXT(9),
  CDB(10),
  CSG(11),
  I64(12),
  U64(13),
  FXP(14);

  private final byte representation;

  private DataRepresentation(int representation) {
    this.representation = (byte) representation;
  }

  @Override
  public Variant toVariant() {
    return new Variant(representation);
  }
}
