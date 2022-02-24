package router;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRouting {
    private static final Pattern PARAMETER_PATTERN = Pattern.compile("(:[a-zA-Z].+)");

    public static void main(String[] args) {
        Pattern route = Pattern.compile("\\/foo\\/\\D.+\\/bar\\/");
        System.out.println(route.matcher("/foo/food/bar/").matches());

        //System.out.println(PARAMETER_PATTERN.matcher("/foo/{bar}/").group());

        final String parameterTemplate = "/foo/:id/";
        List<String> parameterNames = new ArrayList<>();
        final Matcher matcher = PARAMETER_PATTERN.matcher(parameterTemplate);

        while (matcher.find()) {
            if(matcher.groupCount()==1) {
                final String group = matcher.group(1);
                System.out.println((matcher.group(1)));
                if(group.length()>2) {
                    parameterNames.add(group.substring(1, group.length() - 1));
                } else {
                    parameterNames.add(group);
                }
            }
        }
        Pattern pattern = Pattern.compile(Pattern.quote(matcher.replaceAll("_____PARAM_____")).replace("_____PARAM_____", "\\E([^/]*)\\Q"));
        System.out.println(parameterNames);

    }
}
