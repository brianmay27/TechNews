package edu.vt.bmac.assignment1;

import android.view.View;
import android.view.View;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Adapter;
import eu.erikw.PullToRefreshListView.OnRefreshListener;
import eu.erikw.PullToRefreshListView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ListView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity
    extends Activity implements OnScrollListener, MainObserver
{
    protected PullToRefreshListView view;
    protected WebFetch fetcher;
    ItemAdapter adapter;
    Integer curpage;
    boolean inClick;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        inClick = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        curpage = new Integer(1);
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        view = (PullToRefreshListView)findViewById(R.id.pullToRefreshListView1);
        fetcher = new WebFetch(this);;
        adapter = new ItemAdapter(this, fetcher.getPages(), dpWidth);
        fetcher.setAdapter(adapter);
        view.setAdapter(adapter);
        fetcher.execute(curpage);
        view.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh()
            {
                fetcher.resetList();
                fetcher.execute(curpage = 1);
            }
        });
        view.setOnScrollListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    int visibleItem;
    int visibleCount, itemCount;
    @Override
    public void onScroll(
        AbsListView view,
        int firstVisibleItem,
        int visibleItemCount,
        int totalItemCount)
    {
        this.visibleItem = firstVisibleItem;
        this.visibleCount = visibleItemCount;
        this.itemCount = totalItemCount;

    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        if(scrollState == SCROLL_STATE_IDLE &&
            itemCount - (visibleItem + visibleCount) < 3) {
            fetcher.execute(++curpage);
        }

    }


    @Override
    public void ObjectsLoaded()
    {
        if (view.isRefreshing()) {
            view.onRefreshComplete();
        }

    }



}
