package stupaq.labview;

public abstract class Configuration {
  private static final String PREFIX = "scripting.";
  private static final String TOOLS_PATH = PREFIX + "tools.path";
  private static final String PARSER_CACHE = PREFIX + "parser.cache";

  public static boolean getParserCache() {
    return Boolean.valueOf(System.getProperty(PARSER_CACHE, "false"));
  }

  public static String getToolsPath() {
    return System.getProperty(TOOLS_PATH);
  }
}
