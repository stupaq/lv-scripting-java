package stupaq.labview;

public class VIErrorException extends RuntimeException {
  public VIErrorException(int errCode, String errSource) {
    super("VIError:" + errCode + ": " + errSource);
  }
}
