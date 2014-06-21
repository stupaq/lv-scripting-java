package stupaq.labview;

public class VIErrorException extends Exception {
  public VIErrorException(int errCode, String errSource) {
    super("VIError:" + errCode + ": " + errSource);
  }
}
