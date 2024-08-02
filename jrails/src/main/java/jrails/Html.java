package jrails;

public class Html {
    private final String content;

    public Html(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }

    public Html seq(Html h) {
        return new Html(this.content + h.toString());
    }

    public Html br() {
        return new Html(this.content + "<br/>");
    }

    public Html t(Object o) {
        return new Html(this.content + o.toString());
    }

    public Html p(Html child) {
        return new Html(this.content + "<p>" + child.toString() + "</p>");
    }

    public Html div(Html child) {
        return new Html(this.content + "<div>" + child.toString() + "</div>");
    }

    public Html strong(Html child) {
        return new Html(this.content + "<strong>" + child.toString() + "</strong>");
    }

    public Html h1(Html child) {
        return new Html(this.content + "<h1>" + child.toString() + "</h1>");
    }

    public Html tr(Html child) {
        return new Html(this.content + "<tr>" + child.toString() + "</tr>");
    }

    public Html th(Html child) {
        return new Html(this.content + "<th>" + child.toString() + "</th>");
    }

    public Html td(Html child) {
        return new Html(this.content + "<td>" + child.toString() + "</td>");
    }

    public Html table(Html child) {
        return new Html(this.content + "<table>" + child.toString() + "</table>");
    }

    public Html thead(Html child) {
        return new Html(this.content + "<thead>" + child.toString() + "</thead>");
    }

    public Html tbody(Html child) {
        return new Html(this.content + "<tbody>" + child.toString() + "</tbody>");
    }

    public Html textarea(String name, Html child) {
        return new Html(this.content + "<textarea name=\"" + name + "\">" + child.toString() + "</textarea>");
    }

    public Html link_to(String text, String url) {
        return new Html(this.content + "<a href=\"" + url + "\">" + text + "</a>");
    }

    public Html form(String action, Html child) {
        return new Html(this.content + "<form action=\"" + action + "\" accept-charset=\"UTF-8\" method=\"post\">" + child.toString() + "</form>");
    }

    public Html submit(String value) {
        return new Html(this.content + "<input type=\"submit\" value=\"" + value + "\"/>");
    }
}