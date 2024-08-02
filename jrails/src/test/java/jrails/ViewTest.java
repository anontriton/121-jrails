package jrails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ViewTest {

    @Test
    public void empty() {
        assertThat(View.empty().toString(), isEmptyString());
    }

    @Test
    public void testBr() {
        assertThat(View.br().toString(), is("<br/>"));
    }

    @Test
    public void testP() {
        assertThat(View.p(new Html("Hello")).toString(), is("<p>Hello</p>"));
    }

    @Test
    public void testDiv() {
        assertThat(View.div(new Html("Content")).toString(), is("<div>Content</div>"));
    }

    @Test
    public void testStrong() {
        assertThat(View.strong(new Html("Bold")).toString(), is("<strong>Bold</strong>"));
    }

    @Test
    public void testH1() {
        assertThat(View.h1(new Html("Header")).toString(), is("<h1>Header</h1>"));
    }

    @Test
    public void testTr() {
        assertThat(View.tr(new Html("Row")).toString(), is("<tr>Row</tr>"));
    }

    @Test
    public void testTh() {
        assertThat(View.th(new Html("Header")).toString(), is("<th>Header</th>"));
    }

    @Test
    public void testTd() {
        assertThat(View.td(new Html("Data")).toString(), is("<td>Data</td>"));
    }

    @Test
    public void testTable() {
        assertThat(View.table(new Html("Table content")).toString(), is("<table>Table content</table>"));
    }

    @Test
    public void testThead() {
        assertThat(View.thead(new Html("Table head")).toString(), is("<thead>Table head</thead>"));
    }

    @Test
    public void testTbody() {
        assertThat(View.tbody(new Html("Table body")).toString(), is("<tbody>Table body</tbody>"));
    }

    @Test
    public void testTextarea() {
        assertThat(View.textarea("textAreaName", new Html("Text content")).toString(), is("<textarea name=\"textAreaName\">Text content</textarea>"));
    }

    @Test
    public void testLinkTo() {
        assertThat(View.link_to("Click here", "/home").toString(), is("<a href=\"/home\">Click here</a>"));
    }

    @Test
    public void testForm() {
        assertThat(View.form("/submit", new Html("Form content")).toString(), is("<form action=\"/submit\" accept-charset=\"UTF-8\" method=\"post\">Form content</form>"));
    }

    @Test
    public void testSubmit() {
        assertThat(View.submit("Save").toString(), is("<input type=\"submit\" value=\"Save\"/>"));
    }
}