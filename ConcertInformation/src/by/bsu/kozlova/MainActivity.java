package by.bsu.kozlova;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private XMLManager manager = new XMLManager();
    private ArrayList<Event> events;
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        items = new ArrayList<String>();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchView1);
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener( ) {

            public boolean onQueryTextChange( String newText ) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                manager.createXML(query);
                events = manager.parseXML();
                paintList();
                events.clear();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void paintList () {
        mainListView = (ListView) findViewById(R.id.mainListView);
        items.clear();
        Log.w("+++", items.toString());
        if(!events.isEmpty())
            for(Event e :events) {
                items.add(e.toString());
            }
        else
            items.add("No concert");
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow,items);
        mainListView.setAdapter(listAdapter);
    }
}

