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
    private final String mainUrl = "http://ntschool.ru/";
    private CoursesStorage coursesStorage;

    public Parser() {
        coursesStorage = new CoursesStorage();
    }

    private Document getPage(String url) throws IOException {
        return Jsoup.connect(url).
                     validateTLSCertificates(false).
                     get();
    }

    public void parsing() throws IOException {
        String dirWithCourses = "kursyi/";

        Document pageWithCourses = getPage(mainUrl + dirWithCourses);
        Elements liTags = pageWithCourses.
                            getElementsByAttributeValue("class","courses-list--item" +
                                                         " _hparent ms2_product");

        liTags.forEach(liTag -> {
            Element aTag = liTag.child(0);
            String urlOnCurrentCourse = aTag.attr("href");

            Element imgTag = aTag.child(0);
            String courseTitle = imgTag.attr("alt");
            Course course = new Course();
            course.setTitle(courseTitle);
            course.setUrl(mainUrl + urlOnCurrentCourse);
            try {
                course.setPrice(getPriceForCourse(urlOnCurrentCourse));
            } catch (IOException e) {
                e.printStackTrace();
            }
            coursesStorage.addCourse(course);
        });
        coursesStorage.printAllCourses();
        System.out.println( "Количество курсов: " + coursesStorage.getAmountCourses());
    }

    private String getTagValueWithoutSpaces(Elements tag){
        String tagValue = tag.text();
        return tagValue.replace(" ", "");
    }

    private int getPriceForCourse(String urlOnCurrentCourse) throws IOException {
        Document pageWithCurrentCourse = getPage( mainUrl + urlOnCurrentCourse);
        String cssQueryPrice = "div.course1-ticket1--box-newPrice";
        Elements divTags = pageWithCurrentCourse.select(cssQueryPrice);
        int price = Integer.parseInt(getTagValueWithoutSpaces(divTags));
        return price;
    }
}

