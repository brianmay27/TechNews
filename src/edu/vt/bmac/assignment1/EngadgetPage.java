package edu.vt.bmac.assignment1;

import android.util.Log;
import android.R.dimen;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Brian
 *  @version Oct 6, 2013
 */

public class EngadgetPage implements Runnable
{
    protected String name;
    private Bitmap image;
    protected String imageURL;
    protected String content;
    protected String url;
    protected String html;
    private static final String contentBegin = "<div class=\"copy\" itemprop=\"text\">";
    private static final String contentEnd = "<p class=\"read-more\">";
    private static final String nameBegin = "<h2 class=\"h2\" itemprop=\"headline\"><a itemprop=\"url\" href=([^>]*)>";
    private static final String nameEnd = "</a>";
    private static final String urlRegex = ".*<a itemprop=\"url\" href=\"([^>]*)\">.*";
    private static final String imageRegex = ".*<article itemscope itemtype=\"http://schema.org/BlogPosting\" id=\"post-\\d+\" class=\"post[^\"]*\" role=\"article\" data-image=\"([^>]*)\">.*";
    public EngadgetPage(String httpArticle) {
        html = httpArticle;
    }
    @Override
    public void run()
    {
        String htmlContent = html.substring(html.indexOf(contentBegin) + contentBegin.length(),
            html.indexOf(contentEnd));
        content = htmlContent.replaceAll("((<[^>]*>)|(</[^>]*>))", "");
        Pattern p = Pattern.compile(urlRegex);
        Matcher m = p.matcher(html);
        m.matches();
        url = m.group(1);
        String htmlName = html.split(nameBegin)[1];
        htmlName = htmlName.split(nameEnd, 2)[0];
        name = htmlName.replaceAll("((<[^>]*>)|(</[^>]*>))", "");
        p = Pattern.compile(imageRegex);
        m = p.matcher(html);
        m.matches();
        imageURL = m.group(1);
        //Log.d("image", imageURL);
        try {
            HttpURLConnection image = (HttpURLConnection)new URL(imageURL).openConnection();
            image.connect();
            InputStream reader = image.getInputStream();
            this.image= BitmapFactory.decodeStream(reader);
        } catch(Exception e) {
            e.printStackTrace();
            Log.d("exception", "failed to load page");
        }

    }
    public String getName()
    {
        return name;
    }
    public String getImageURL()
    {
        return imageURL;
    }
    public String getContent()
    {
        return content;
    }
    public String getUrl()
    {
        return url;
    }
    public Bitmap getImage() {
        return image;
    }
}
