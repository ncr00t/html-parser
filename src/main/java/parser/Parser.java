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
    private CoursesStorage coursesStorage;

    public Parser(String url) {
        this.url = url;
        coursesStorage = new CoursesStorage();
    }

    private Document getPage(String url) throws IOException {
        return Jsoup.connect(url).
                     validateTLSCertificates(false).
                     get();
    }

    public void parsing() throws IOException {
        Document pageWithCourses = getPage(url);
        Elements liTags = pageWithCourses.
                            getElementsByAttributeValue("class","courses-list--item" +
                                                         " _hparent ms2_product");

        liTags.forEach(liTag -> {
            Element aTag = liTag.child(0);
            String urlOnCourse = aTag.attr("href");

            Element imgTag = aTag.child(0);
            String courseTitle = imgTag.attr("alt");

            try {
                Document pageWithCurrentCourse = getPage("http://ntschool.ru/" + urlOnCourse);
                Elements divTags = pageWithCurrentCourse.select("div.course1-ticket1--box-newPrice");
                int price = Integer.parseInt(getTagValueWithoutSpaces(divTags));
                Course course = new Course();
                course.setTitle(courseTitle);
                course.setUrl(urlOnCourse);
                course.setPrice(price);
                coursesStorage.addCourse(course);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        coursesStorage.printAllCourses();
    }

    public String getTagValueWithoutSpaces(Elements tag){
        String tagValue = tag.text();
        return tagValue.replace(" ", "");
    }
}

