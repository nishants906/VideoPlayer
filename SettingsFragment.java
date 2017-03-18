package first.com.movie_player;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

/**
 * Created by Test on 2/18/2017.
 */
public class SettingsFragment extends Fragment {

    RelativeLayout themeCard;
    ImageView themeColorImg;

    MovieList homeActivity;


    SettingsFragmentCallbackListener mCallback;

    ImageView backBtn;
    TextView fragTitle;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public interface SettingsFragmentCallbackListener {
        void onColorChanged();

        void onAlbumArtBackgroundChangedVisibility(int visibility);

        void onAboutClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeActivity = (MovieList) context;
        try {
            mCallback = (SettingsFragmentCallbackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn = (ImageView) view.findViewById(R.id.settings_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        themeCard = (RelativeLayout) view.findViewById(R.id.theme_card);
        themeColorImg = (ImageView) view.findViewById(R.id.theme_color_img);
        themeColorImg.setBackgroundColor(homeActivity.themeColor);
        themeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Choose color")
                        .initialColor(MovieList.themeColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(9)
                        .showColorPreview(true)
                        .lightnessSliderOnly()
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int color) {
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int color, Integer[] allColors) {
                                setHomeActivityColor(color);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .build();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(MovieList.themeColor);
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(MovieList.themeColor);
                    }
                });

                dialog.show();
            }
        });
    }

    public void setHomeActivityColor(int color){
        homeActivity.themeColor = color;
        themeColorImg.setBackgroundColor(color);
        mCallback.onColorChanged();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = ((Activity) (getContext())).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getDarkColor(color));

        }
    }

    public int getDarkColor(int color) {
        int darkColor = 0;

        int r = Math.max(Color.red(color) - 25, 0);
        int g = Math.max(Color.green(color) - 25, 0);
        int b = Math.max(Color.blue(color) - 25, 0);

        darkColor = Color.rgb(r, g, b);

        return darkColor;
    }
}