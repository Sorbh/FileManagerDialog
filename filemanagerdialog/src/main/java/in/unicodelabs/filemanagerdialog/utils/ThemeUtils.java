package in.unicodelabs.filemanagerdialog.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import in.unicodelabs.filemanagerdialog.R;

import static android.graphics.Color.parseColor;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
/**
 * Created by saurabh on 27/2/18.
 */

public class ThemeUtils {
    // material_deep_teal_500
    static final int FALLBACK_COLOR = parseColor("#009688");

    static boolean isAtLeastL() {
        return SDK_INT >= LOLLIPOP;
    }

    @TargetApi(LOLLIPOP)
    public static int resolveAccentColor(Context context) {
        Resources.Theme theme = context.getTheme();

        // on Lollipop, grab system colorAccent attribute
        // pre-Lollipop, grab AppCompat colorAccent attribute
        // finally, check for custom mp_colorAccent attribute
        int attr = isAtLeastL() ? android.R.attr.colorAccent : R.attr.colorAccent;
        TypedArray typedArray = theme.obtainStyledAttributes(new int[] { attr, R.attr.colorAccent });

        int accentColor = typedArray.getColor(0, FALLBACK_COLOR);
        accentColor = typedArray.getColor(1, accentColor);
        typedArray.recycle();

        return accentColor;
    }
}
