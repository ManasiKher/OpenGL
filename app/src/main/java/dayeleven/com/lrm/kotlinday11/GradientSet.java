package dayeleven.com.lrm.kotlinday11;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import static android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM;

/**
 * Created by manasi on 19/2/18.
 */

public class GradientSet extends RemoteViews {


    public GradientSet(String packageName, int layoutId) {
        super(packageName, layoutId);
    }

    public View apply(Context context, ViewGroup parent) {
        View result = super.apply(context, parent);

        //your code
        int[] colors = new int[]{R.color.red,R.color.green};
        GradientDrawable gd = new GradientDrawable(TOP_BOTTOM, colors);
        result.setBackgroundDrawable(gd);
        //end of your code

        return result;
    }

}
