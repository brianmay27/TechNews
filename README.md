TechNews
========

TechNews is a simple application that is able to grab and process news from engadget.
It has the ability to refresh its information by pulling down the listView as well as dynamicly load more posts from 
the site as needed. The application has a few threads that get run, One fetches and processes the html and sends it to 
another thread to process as well as pull the image from the site. 

The software uses RefreshListView which is provided under the apachie license and is used to allow the user to update
the view to contain the latest information from engadget. The underlying view has a imageview to display the image 
and two text fields. The first provides the title while the other displays the content. The view expands based on how 
long the content is.

The program will also grab more data as the user scrolls down. This allows the user to continue to recieve more data
when they need.
