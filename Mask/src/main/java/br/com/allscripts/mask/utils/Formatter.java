package br.com.allscripts.mask.utils;

import java.util.HashMap;

import br.com.allscripts.mask.MaskPattern;

public class Formatter {

    /**
     * String pattern that represents the mask used by {@link #Format(String)} method
     * All mask characters should have been registered with RegisterPattern
     */
    protected String mask;

    /**
     * Used to register all available patterns
     * The Key is used to map all available characters mask
     */
    private HashMap<String, MaskPattern> PatternMap = new HashMap<>();

    /**
     * Constructor
     */
    public Formatter(String mask) {
        this.mask = mask;

        RegisterPattern("0", new MaskPattern("\\p{Digit}"));
        RegisterPattern("A", new MaskPattern("\\p{Alpha}"));
        RegisterPattern("l", new MaskPattern("\\p{Lower}"));
        RegisterPattern("U", new MaskPattern("\\p{Upper}"));
        RegisterPattern("*", new MaskPattern("\\p{Alnum}"));
    }

    /**
     * Register a {@link MaskPattern} using specified mask key
     *
     * @param Key     String key that represents the mask
     * @param Pattern {@link MaskPattern} that will be used to validate the mask
     */
    private void RegisterPattern(String Key, MaskPattern Pattern) {
        PatternMap.put(Key, Pattern);
    }

    /**
     * Format the string according to the mask pattern
     * Removes every character after the last valid pattern mask
     *
     * @param NewText String to Format
     * @return Formatted string
     */
    protected String Format(String NewText) {
        if (NewText == null || "".equals(NewText)) {
            return "";
        }

        int NewTextOffset = 0;
        StringBuilder result = new StringBuilder();

        // Count all characters after a valid pattern
        // that is not a Pattern to remove from the final result
        int numOfLiterals = 0;

        // Traverse mask length looking for a match pattern
        for (int l = 0; l < mask.length(); l++) {

            // Traverse the mask pattern from l concatenating each
            // character to build the pattern and look for match
            MaskPattern patternFound = null;
            StringBuilder Buffer = new StringBuilder();
            for (int i = l; i < mask.length(); i++) {
                Buffer = Buffer.append(mask.charAt(i));

                String pattern = Buffer.toString();
                if (PatternMap.containsKey(pattern)) {
                    patternFound = PatternMap.get(pattern);

                    // Buffer = new StringBuilder();

                    // Looks for the index of a char inside NewText that matches the current
                    // patternFound pattern.
                    int nextIndex = IndexOfValidChar(patternFound, NewText, NewTextOffset);
                    if (nextIndex >= 0 && nextIndex < NewText.length()) {
                        // Adds 1 to the current position offset
                        NewTextOffset = nextIndex + 1;
                        // Get the valid character
                        char nextChar = NewText.charAt(nextIndex);

                        result.append(patternFound.Transform(nextChar));
                        numOfLiterals = 0;

                        // If there aren't any character left
                        if (NewTextOffset >= NewText.length())
                            return result.toString();
                    }
                    // If NewText reaches the end
                    else {
                        return (numOfLiterals > 0) ? result.substring(0, result.length() - numOfLiterals) : result.toString();
                    }

                    // Adds the search offset as this pattern
                    // has already been used
                    l += (pattern.length() - 1);

                    break;
                }
            }

            // If no valid char was found, it is a literal character.
            if (patternFound == null) {
                result.append(mask.charAt(l));
                numOfLiterals++;
            }
        }

        return (numOfLiterals > 0) ? result.substring(0, result.length() - numOfLiterals) : result.toString();
    }

    /**
     * Look for the first valid index inside @text that matches the patternMaskChar
     *
     * @param PatternMaskChar {@link MaskPattern} that will be used to validate text
     * @param Text            String used to validate
     * @param OffSet          Offset number for the next valid index
     * @return Index of the first valid match
     */
    private int IndexOfValidChar(MaskPattern PatternMaskChar, String Text, int OffSet) {
        for (int i = OffSet; i < Text.length(); i++) {
            char character = Text.charAt(i);
            if (PatternMaskChar.IsValid(Text, character)) {
                return i;
            }
        }
        return -1;
    }
}
