package utilities;

public class StringUtils {
	
	private StringUtils() throws IllegalAccessException {
		throw new IllegalAccessException("Clase de utilidad");
	}
	
    public static String trimToEmpty(final String str) {
        return str == null ? "" : str.trim();
    }
    
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }
    
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    
    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static String trimToNull(final String str) {
        final String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }
    
    
}
