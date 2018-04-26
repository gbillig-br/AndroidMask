package br.com.allscripts.mask;

import java.util.regex.Pattern;

public class MaskPattern {

    private Pattern p;
    private String pattern;

    public MaskPattern(String pattern) {
        this.p = Pattern.compile(pattern);
        this.pattern = pattern;
    }

    private String GetPattern() {
        return pattern;
    }

    public void SetPattern(String pattern) {
        this.p = Pattern.compile(pattern);
        this.pattern = pattern;
    }

    public boolean IsValid(String Current, char character) {
        return p.matcher(String.valueOf(character)).matches();
    }

    public String Transform(char c) {
        return String.valueOf(c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof MaskPattern))
            return false;

        MaskPattern lhs = (MaskPattern) o;
        return pattern.equals(lhs.GetPattern());
    }

    @Override
    public int hashCode() {
        return 33 * pattern.hashCode();
    }
}
