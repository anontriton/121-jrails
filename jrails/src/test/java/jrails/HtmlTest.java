package jrails;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HtmlTest {

    private Html html;

    @Before
    public void setUp() throws Exception {
        html = new Html("");
    }

    @Test
    public void empty() {
        assertThat(View.empty().toString(), isEmptyString());
    }

    @Test
    public void testBr() {
        html = View.br();
        assertThat(html.toString(), is("<br/>"));
    }

    @Test
    public void testP() {
        html = View.p(new Html("Hello"));
        assertThat(html.toString(), is("<p>Hello</p>"));
    }

    @Test
    public void testDiv() {
        html = View.div(new Html("Content"));
        assertThat(html.toString(), is("<div>Content</div>"));
    }

    @Test
    public void testStrong() {
        html = View.strong(new Html("Bold"));
        assertThat(html.toString(), is("<strong>Bold</strong>"));
    }

    @Test
    public void testH1() {
        html = View.h1(new Html("Header"));
        assertThat(html.toString(), is("<h1>Header</h1>"));
    }

    @Test
    public void testLinkTo() {
        html = View.link_to("Click here", "/home");
        assertThat(html.toString(), is("<a href=\"/home\">Click here</a>"));
    }

    @Test
    public void testForm() {
        html = View.form("/submit", new Html("Form content"));
        assertThat(html.toString(), is("<form action=\"/submit\" accept-charset=\"UTF-8\" method=\"post\">Form content</form>"));
    }

    @Test
    public void testSubmit() {
        html = View.submit("Save");
        assertThat(html.toString(), is("<input type=\"submit\" value=\"Save\"/>"));
    }
}
