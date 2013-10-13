package edu.vt.bmac.assignment1;

import android.view.Display;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.zip.Inflater;
import java.util.ArrayList;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Brian
 *  @version Oct 6, 2013
 */

public class ItemAdapter extends BaseAdapter
{
    Context a;
    ArrayList<EngadgetPage> pages;
    Display display;
    TextView v;
    TextView t;
    ImageView i ;
    float width;

    private static LayoutInflater inflater=null;
    public ItemAdapter(Context a, ArrayList<EngadgetPage> pages, float width) {
        this.a = a;
        this.pages = pages;
        inflater = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.width = width;
    }
    @Override
    public int getCount()
    {
        //return 10;
        return pages.size() + 1;
    }

    @Override
    public Object getItem(int position)
    {
        return pages.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item, parent, false);

        }
        view.setId(position);
        i =  (ImageView)view.findViewById(R.id.imageView1);
        t =(TextView)view.findViewById(R.id.textView2);
        v = (TextView)view.findViewById(R.id.textView1);
        if (position >= pages.size()) {
            v.setText("Loading additional posts");
            v.setTextSize(15);
            t.setText("We are currently fetching words from the interwebz! Please wait for the magic.");
            i.setVisibility(2);
        } else {
        i.setImageBitmap(pages.get(position).getImage());
        String content = pages.get(position).getContent();
        v.setText(pages.get(position).getName());
        t.setText(pages.get(position).getContent());
        }
        return view;
    }

}
