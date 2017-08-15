package parser;

import courseDescription.Course;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import storage.CoursesStorage;

import java.io.IOException;

/**
 * Class for parsing courses
 */
public class Parser {
    private String url;
    private Course course;
    private CoursesStorage coursesStorage;

    public Parser(String url) {
        this.url = url;
        course = new Course();
        coursesStorage = new CoursesStorage();
    }

    private Document getPageWithCourses() throws IOException {
        return Jsoup.connect(url).get();
    }

    public void parsing() throws IOException {
        Document pageWithCourses = getPageWithCourses();
        Elements liTags = pageWithCourses.getElementsByAttributeValue("li","courses-list--item _hparent ms2_product");

        liTags.forEach(liTag -> {
            Element aTag = liTag.child(0);
            String urlOnCourse = aTag.attr("href");
            course.setUrl(urlOnCourse);
        });
    }
}

