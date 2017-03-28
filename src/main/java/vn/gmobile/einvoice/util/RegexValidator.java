package vn.gmobile.einvoice.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator
{

    public RegexValidator()
    {
    }

    public static boolean validate(String pattern, String value)
    {
        if(value == null)
            return false;
        if(pattern == null)
        {
            return true;
        } else
        {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            return m.matches();
        }
    }
}