package xyz.kbalto.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private String mRequestUrl = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private String mQuery = "";
    private ListView bookListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.no_items_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);
        Log.i("onCreate", "onCreate");

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network.
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.

            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for the bundle.
            // Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(1, null, MainActivity.this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet);
        }
        final EditText editText = (EditText) findViewById(R.id.query_text);


        View searchIcon = findViewById(R.id.button_search);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the EditText and update the mQuery value.
                mQuery = editText.getText().toString().replaceAll(" ", "+");
                // If it's empty don't proceed.
                if (mQuery.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nothing to search", Toast.LENGTH_SHORT).show();
                }
                // Update the mRequestUrl value with the new mQuery.
                mRequestUrl = mRequestUrl + mQuery + "&maxResults=15";
                Log.i("onQueryTextSubmit", "mRequestUrl value is: " + mRequestUrl);
                // Restart the loader.
                getLoaderManager().restartLoader(1, null, MainActivity.this);
                Log.i("onClick", "loader restarted");
                // Try to make the progress bar appear again (not working)
                View progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);
                // Update mRequestUrl back to its original value. TODO: change it to global variable somehow.
                mRequestUrl = "https://www.googleapis.com/books/v1/volumes?q=";
                // This is what makes the ListView update with new info.
                mAdapter = new BookAdapter(MainActivity.this, new ArrayList<Book>());
                bookListView.setAdapter(mAdapter);
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new Loader for this URL.
        Log.i("onCreateLoader", "loader created");
        return new BookLoader(this, mRequestUrl);
    }

    /**
     * Clears or adds list to the adapter.
     * If no content is found, sets empty state view after the background thread is finished.
     *
     */
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.i("onLoadFinished", "loader finished");
        // If there is a valid list of Books, add them to the adapter for the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
        mEmptyStateTextView.setText(R.string.no_books);
        mEmptyStateTextView.setVisibility(VISIBLE);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(GONE);
    }

    /**
     * Resets the loader so we can clear out existing data.
     *
     */
    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.i("onLoaderReset", "loader reset");

    }
}