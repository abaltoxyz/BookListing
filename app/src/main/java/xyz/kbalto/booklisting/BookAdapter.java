package xyz.kbalto.booklisting;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kevin on 19/10/2016.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books   is the list of books, which is the data source of the adapter
     */
    public BookAdapter(Activity context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Return the formatted rating string showing 1 decimal place (i.e. "3.2")
     * from a decimal rating value.
     */
    private String formatRating(double rating) {
        DecimalFormat ratingFormat = new DecimalFormat("0.0");
        return ratingFormat.format(rating);
    }

    /**
     * Returns a list item view that displays information about the book
     * at the given position in the list of books.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final Book currentBook = getItem(position);
        String title = currentBook.getTitle();
        String author = currentBook.getAuthor();
        String description = currentBook.getDescription();
        String rating = formatRating(currentBook.getRating());
        Bitmap thumbnail = currentBook.getBitmap();

        TextView titleView = (TextView) listItemView.findViewById(R.id.list_title);
        titleView.setText(title);

        TextView authorView = (TextView) listItemView.findViewById(R.id.list_author);
        authorView.setText(author);

        TextView descriptionView = (TextView) listItemView.findViewById(R.id.list_description);
        descriptionView.setText(description);

        TextView ratingView = (TextView) listItemView.findViewById(R.id.list_rating);
        ratingView.setText(rating);

        ImageView thumbnailView = (ImageView) listItemView.findViewById(R.id.list_thumbnail);
        //TODO set image from URL String
        thumbnailView.setImageBitmap(thumbnail);

        return listItemView;
    }
}
