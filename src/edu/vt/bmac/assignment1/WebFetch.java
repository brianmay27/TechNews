package edu.vt.bmac.assignment1;

import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Brian
 *  @version Oct 6, 2013
 */

public class WebFetch
{
    protected ArrayList<EngadgetPage> pages;
    protected ExecutorService thread;
    protected boolean running = false;
    protected ItemAdapter adapter;
    protected MainObserver observer;
    public WebFetch(MainObserver observer) {
        pages = new ArrayList<EngadgetPage>();
        thread = Executors.newFixedThreadPool(1);
        this.observer = observer;
        //this.adapter = adapter;
    }
    public void setAdapter(ItemAdapter adapter) {
        this.adapter = adapter;
    }
    public ArrayList<EngadgetPage> getPages()
    {
        return pages;
    }
    public void resetList() {
        pages.clear();
    }
    public boolean isBusy() {
        return running;
    }
    public void execute(Integer pageNumber) {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            protected Void doInBackground(Integer...pageNum)
            {
                running = true;
                HttpURLConnection connect;
                StringBuilder webpage = new StringBuilder();
                String html;
                try {
                    connect = (HttpURLConnection)new URL("http://www.engadget.com/page/" + pageNum[0].toString() + "/").openConnection();
                    connect.connect();
                    BufferedReader stream = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String line;
                    while((line = stream.readLine()) != null) {
                        webpage.append(line);
                    }
                    html = webpage.toString();
                    String content = html.split("<!-- M:body-posts -->")[1];
                    String articles[] = content.split("</article>", 10);
                    for(String article : articles) {
                        EngadgetPage page = new EngadgetPage(article);
                        pages.add(page);
                        page.run();
                    }
                    thread.shutdown();
                    while (!thread.isTerminated());

                } catch(Exception e) {
                    e.printStackTrace();
                    Log.d("exception", e.getMessage());

                } finally {

                }
                return null;
            }
            protected void onPostExecute(Void nothing) {
                adapter.notifyDataSetChanged();
                observer.ObjectsLoaded();
            }

        };
        task.execute(pageNumber);
    }

}
