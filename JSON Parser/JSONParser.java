import java.util.*;

@SuppressWarnings("unchecked")
//class to parse JSON string
public class JSONParser {
    private int index = 0;
    
    // main parse method
    public Map<String, Object> parse(String json) {
        if (json == null || json.isEmpty()) {
            return Collections.emptyMap();
        }

        char[] charArray = json.toCharArray();
        return parseObject(charArray);
    }

    //method to parse the object
    private Map<String, Object> parseObject(char[] charArray) {
        Map<String, Object> map = new HashMap<>();

        customTrim(charArray);
        if (charArray[index] == '{') {
            index++;

            while (index < charArray.length && charArray[index] != '}') {
                customTrim(charArray);
                String key = parseString(charArray);
                customTrim(charArray);
                validateColon(':', charArray);
                Object value = parseValue(charArray);
                map.put(key, value);

                customTrim(charArray);
                if (charArray[index] == ',') {
                    index++;
                } else if (charArray[index] != '}') {
                    throw new IllegalArgumentException("Invalid JSON format");
                }
            }

            index++;
            return map;
        } else {
            throw new IllegalArgumentException("Invalid JSON format");
        }
    }
    
    //method to parse the value of the object
    private Object parseValue(char[] charArray) {
        customTrim(charArray);

        if (charArray[index] == '{') {
            return parseObject(charArray);
        } else if (charArray[index] == '[') {
            return parseStringArray(charArray);
        } else if (charArray[index] == '"') {
            return parseString(charArray);
        } else if (Character.isDigit(charArray[index]) || charArray[index] == '-') {
            return parseNumber(charArray);
        } else if (charArray[index] == 't' && charArray[index + 1] == 'r' && charArray[index + 2] == 'u' && charArray[index + 3] == 'e') {
            index += 4;
            return true;
        } else if (charArray[index] == 'f' && charArray[index + 1] == 'a' && charArray[index + 2] == 'l' && charArray[index + 3] == 's' && charArray[index + 4] == 'e') {
            index += 5;
            return false;
        } else if (charArray[index] == 'n' && charArray[index + 1] == 'u' && charArray[index + 2] == 'l' && charArray[index + 3] == 'l') {
            index += 4;
            return null;
        } else {
            throw new IllegalArgumentException("Invalid JSON format");
        }
    }

    //method to parse an array of the object
    private List<String> parseStringArray(char[] charArray) {
        List<String> arrayList = new ArrayList<>();

        customTrim(charArray);
        if (charArray[index] == '[') {
            index++;

            while (index < charArray.length && charArray[index] != ']') {
                customTrim(charArray);
                String element = parseString(charArray);
                arrayList.add(element);

                customTrim(charArray);
                if (charArray[index] == ',') {
                    index++;
                } else if (charArray[index] != ']') {
                    throw new IllegalArgumentException("Invalid JSON format");
                }
            }

            index++;
            return arrayList;
        } else {
            throw new IllegalArgumentException("Invalid JSON format");
        }
    }


    //method to parse string that is inside "" 
    private String parseString(char[] charArray) {
        customTrim(charArray);

        if (charArray[index] == '"') {
            index++;
            StringBuilder sb = new StringBuilder();

            while (index < charArray.length && charArray[index] != '"') {
                if (charArray[index] == '\\') {
                    index++;
                    if (index >= charArray.length) {
                        break;
                    }
                    char escape = charArray[index];
                    switch (escape) {
                        case '"':
                        case '\\':
                        case '/':
                            sb.append(escape);
                            break;
                        case 'b':
                            sb.append('\b');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'u':
                            // Handle Unicode escape sequences if needed
                            // For simplicity, we're not handling these here
                            throw new IllegalArgumentException("Unicode escape not implemented");
                        default:
                            throw new IllegalArgumentException("Invalid escape sequence");
                    }
                } else {
                    sb.append(charArray[index]);
                }
                index++;
            }

            if (index < charArray.length && charArray[index] == '"') {
                index++;
                return sb.toString();
            }
        }

        throw new IllegalArgumentException("Invalid JSON format");
    }

    //method to parse a number inside the object
    private Double parseNumber(char[] charArray) {
        customTrim(charArray);

        int startIndex = index;
        while (index < charArray.length && (Character.isDigit(charArray[index]) || charArray[index] == '.' || charArray[index] == '-' || charArray[index] == 'e' || charArray[index] == 'E')) {
            index++;
        }

        String numberStr = new String(charArray, startIndex, index - startIndex);
        return Double.parseDouble(numberStr);
    }

    //method to check for whitespace and trimming the string
    private void customTrim(char[] charArray) {
        while (index < charArray.length && Character.isWhitespace(charArray[index])) {
            index++;
        }
    }

    //method to validate ":" symbol
    private void validateColon(char expected, char[] charArray) {
        if (index < charArray.length && charArray[index] == expected) {
            index++;
        } else {
            throw new IllegalArgumentException("Does not match with '" + expected + "'");
        }
    }

    public static void main(String[] args) {
        String input = "{\r\n" + //
                "\"debug\" : \"on\",\r\n" + //
                "\"window\" : {\r\n" + //
                "\"title\" : \"sample\",\r\n" + //
                "\"size\": 500\r\n" + //
                "}\r\n" + //
                "}";

        JSONParser parser = new JSONParser();
        Map<String, Object> output;
        try {
            output = parser.parse(input);
            System.out.println(output);
            assert (output.get("debug").equals("on")) : "false";
            assert ((Map<String, Object>)(output.get("window"))).get("title").equals("sample") : "false";
            assert ((Map<String, Object>)(output.get("window"))).get("size").equals(500.0) : "false";
        } catch(IllegalArgumentException e){
            System.out.println(e);
        }
    }
}
