package edu.sdccd.cisc190;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class PositiveFloatMatcher implements Matcher<String> {
    @Override
    public boolean matches(Object o) {
        float f;
        try {
            f = Float.parseFloat(o.toString());
        } catch(NumberFormatException e) {
            return false;
        }
        return f > 0;
    }

    @Override
    public void describeMismatch(Object o, Description description) {

    }

    @Override
    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

    }

    @Override
    public void describeTo(Description description) {

    }
}
