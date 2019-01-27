package app.mian.lawrene.posterdesigner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;

import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;
import com.vistrav.ask.Ask;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    String filePath = "";
    private static final int PERMISSION_REQUEST = 100;
    private static final int GALLERY_PICK = 1;
    private static final int CAMERA_REQUEST = 1888;
    CardView selectorCard;
    List<Sticker> stickerList = new ArrayList<>();
    RelativeLayout mainImage, editingOptions;
    ImageView postImageView, captureImage, pickFromGallery;
    LinearLayout rotateLinearLayout, buttomButtons, styleLayout;
    ScrollView characterlayout;
    AlertDialog b;
    ImageAdapter imageAdapter;
    FontAdapter fontAdapter;
    StickerAdapter stickerAdapter;
    EditText labelText;
    AdView adView;
    List<Backgrounds> backgroundsList = new ArrayList<>();
    ImageView imageView;
    List<ImageView> imageViewList = new ArrayList<>();
    List<Fonts> englishFontsList = new ArrayList<>();
    List<Fonts> urduFontsList = new ArrayList<>();
    List<Fonts> arabicFontsList = new ArrayList<>();
    List<Fonts> hindiFontsList = new ArrayList<>();
    SeekBar yAxis, xAxis, zAxis, sizeSeek, spacingSeek, lineSeek;
    TextView postTextView;
    GridView backgroundGridView, fontTitles, stickerGridView;
    ListView englishFontListView, urduFontListView, arabicFontListView, hindiFontListView;
    Button rotatebutton, colorpickerbutton, textcharacter, backgroundbutton, styleButton, stickerbutton;
    int currentColor;
    int xDelta;
    int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        checkpermission();
        loadbackgrounds();
        loadEnglishfonts();
        loadUrdufonts();
        loadArabicFonts();
        loadHindiFont();
        loadButtonActions();
        loadStickers();

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        sizeSeek.setProgress(20);
        lineSeek.setProgress(20);

        labelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTextDialog();
            }
        });

        xAxis.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                postTextView.setRotationX(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        yAxis.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                postTextView.setRotationY(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        zAxis.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                postTextView.setRotation(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lineSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                postTextView.setLineSpacing(+i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                postTextView.setTextSize(+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        spacingSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (postTextView.getText().toString().contains(" ")) {
                    int space = spacingSeek.getProgress();
                    switch (space) {
                        case 0:
                            if (postTextView.getText().toString().contains("  ")) {
                                postTextView.setText(postTextView.getText().toString().replace("  ", " "));
                                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                            }

                            break;


                        case 1:
                            if (postTextView.getText().toString().contains(" ") && !postTextView.getText().toString().contains("  ")) {
                                postTextView.setText(postTextView.getText().toString().replace(" ", "  "));
                                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                            } else if (postTextView.getText().toString().contains("   ")) {
                                postTextView.setText(postTextView.getText().toString().replace("   ", "  "));
//                                Toast.makeText(MainActivity.this, "make space double", Toast.LENGTH_SHORT).show();
                            }
                            break;


                        case 2:
                            if (postTextView.getText().toString().contains("  ") && !postTextView.getText().toString().contains("   ")) {
                                postTextView.setText(postTextView.getText().toString().replace("  ", "   "));
                                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                            } else if (postTextView.getText().toString().contains("    ")) {
                                postTextView.setText(postTextView.getText().toString().replace("    ", "   "));
                            }
                            break;


                        case 3:
                            if (postTextView.getText().toString().contains("   ") && !postTextView.getText().toString().contains("    ")) {
                                postTextView.setText(postTextView.getText().toString().replace("   ", "    "));
                                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                            } else if (postTextView.getText().toString().contains("     ")) {
                                postTextView.setText(postTextView.getText().toString().replace("     ", "    "));
                            }
                            break;


                        case 4:
                            if (postTextView.getText().toString().contains("    ") && !postTextView.getText().toString().contains("     ")) {
                                postTextView.setText(postTextView.getText().toString().replace("    ", "     "));
                                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                            } else if (postTextView.getText().toString().contains("      ")) {
                                postTextView.setText(postTextView.getText().toString().replace("      ", "     "));
                            }
                            break;
                        case 5:
                            if (postTextView.getText().toString().contains("     ") && !postTextView.getText().toString().contains("      ")) {
                                postTextView.setText(postTextView.getText().toString().replace("     ", "      "));
                                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                            } else if (postTextView.getText().toString().contains("       ")) {
                                postTextView.setText(postTextView.getText().toString().replace("       ", "      "));
                            }
                            break;
                        case 6:
                            if (postTextView.getText().toString().contains("      ") && !postTextView.getText().toString().contains("       ")) {
                                postTextView.setText(postTextView.getText().toString().replace("      ", "       "));
                                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                            } else if (postTextView.getText().toString().contains("        ")) {
                                postTextView.setText(postTextView.getText().toString().replace("        ", "       "));
                            }
                            break;

                        case 7:
                            if (postTextView.getText().toString().contains("       ") && !postTextView.getText().toString().contains("        ")) {
                                postTextView.setText(postTextView.getText().toString().replace("       ", "        "));
                                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                            } else if (postTextView.getText().toString().contains("         ")) {
                                postTextView.setText(postTextView.getText().toString().replace("         ", "        "));
                            }
                            break;

                        case 8:
                            if (postTextView.getText().toString().contains("        ") && !postTextView.getText().toString().contains("         ")) {
                                postTextView.setText(postTextView.getText().toString().replace("        ", "         "));
                                Toast.makeText(MainActivity.this, "" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                            } else if (postTextView.getText().toString().contains("          ")) {
                                postTextView.setText(postTextView.getText().toString().replace("          ", "         "));
                            }
                            break;

                    }

                } else {
//                    Toast.makeText(MainActivity.this, "has no tokens", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        englishFontListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Typeface typeface = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/" + englishFontsList.get(i).getFontname().toLowerCase() + ".ttf");
                postTextView.setTypeface(typeface);
            }
        });
        backgroundGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0 && position != 1) {
                    if (postImageView.getVisibility() == View.INVISIBLE) {
                        postImageView.setVisibility(View.VISIBLE);
                    }
                    postImageView.setImageResource(backgroundsList.get(position).getImage());
                } else if (position == 0) {
                    openDialogBackground(false);
                } else if (position == 1) {
                    selectorCard.setVisibility(View.VISIBLE);
                    buttomButtons.setVisibility(View.GONE);
                    stickerGridView.setVisibility(View.GONE);
                    labelText.setVisibility(View.GONE);
                    rotateLinearLayout.setVisibility(View.GONE);
                    fontTitles.setVisibility(View.GONE);
                    backgroundGridView.setVisibility(View.GONE);
                }
            }
        });

        postTextView.setOnTouchListener(onTouchListener());
    }

    public void loadButtonActions() {

        colorpickerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTextDialog(false);
            }
        });

        editingOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rotateLinearLayout.getVisibility() != View.VISIBLE) {
                    selectorCard.setVisibility(View.GONE);
                    buttomButtons.setVisibility(View.VISIBLE);
                    fontTitles.setVisibility(View.GONE);
                    rotateLinearLayout.setVisibility(View.GONE);
                    labelText.setVisibility(View.VISIBLE);
                    stickerGridView.setVisibility(View.GONE);
                    backgroundGridView.setVisibility(View.GONE);
                }
            }
        });

        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectorCard.getVisibility() == View.VISIBLE) {
                    selectorCard.setVisibility(View.GONE);
                    buttomButtons.setVisibility(View.VISIBLE);
                    fontTitles.setVisibility(View.GONE);
                    stickerGridView.setVisibility(View.GONE);
                    labelText.setVisibility(View.VISIBLE);
                    rotateLinearLayout.setVisibility(View.GONE);
                    backgroundGridView.setVisibility(View.GONE);
                }
            }
        });

        rotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateLinearLayout.setVisibility(View.VISIBLE);
                backgroundGridView.setVisibility(View.GONE);
                buttomButtons.setVisibility(View.GONE);
                styleLayout.setVisibility(View.GONE);
                fontTitles.setVisibility(View.GONE);
                stickerGridView.setVisibility(View.GONE);
                characterlayout.setVisibility(View.GONE);

            }
        });

        styleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                styleLayout.setVisibility(View.VISIBLE);
                rotateLinearLayout.setVisibility(View.GONE);
                characterlayout.setVisibility(View.GONE);
                buttomButtons.setVisibility(View.GONE);
                fontTitles.setVisibility(View.VISIBLE);
                stickerGridView.setVisibility(View.GONE);
                backgroundGridView.setVisibility(View.GONE);

            }
        });

        backgroundbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundGridView.setVisibility(View.VISIBLE);
                rotateLinearLayout.setVisibility(View.GONE);
                characterlayout.setVisibility(View.GONE);
                fontTitles.setVisibility(View.GONE);
                stickerGridView.setVisibility(View.GONE);
                styleLayout.setVisibility(View.GONE);
                buttomButtons.setVisibility(View.GONE);
            }
        });

        textcharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundGridView.setVisibility(View.GONE);
                rotateLinearLayout.setVisibility(View.GONE);
                characterlayout.setVisibility(View.VISIBLE);
                fontTitles.setVisibility(View.GONE);
                stickerGridView.setVisibility(View.GONE);
                styleLayout.setVisibility(View.GONE);
                buttomButtons.setVisibility(View.GONE);
            }
        });

        stickerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundGridView.setVisibility(View.GONE);
                rotateLinearLayout.setVisibility(View.GONE);
                characterlayout.setVisibility(View.GONE);
                fontTitles.setVisibility(View.GONE);
                stickerGridView.setVisibility(View.VISIBLE);
                styleLayout.setVisibility(View.GONE);
                buttomButtons.setVisibility(View.GONE);
            }
        });

        stickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                imageView = new ImageView(MainActivity.this);
                imageViewList.add(imageView);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
//
//                if (position == 0) {
//                    selectImageFromGallery();
////                    imageView.setImageResource(stickerList.get(position).getImage());
//                }
//
//                else {
                imageView.setImageResource(stickerList.get(position).getImage());
//                }

                mainImage.addView(imageView);
                imageView.setOnTouchListener(onTouchListenerSticker());

                for (int i = 0; i < imageViewList.size(); i++) {
                    final ImageView currentImage = imageViewList.get(i);

                    currentImage.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            PopupMenu popupMenu = new PopupMenu(MainActivity.this, imageView);
                            popupMenu.getMenuInflater().inflate(R.menu.sticker_menu, popupMenu.getMenu());
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
//                                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                                    mainImage.removeView(currentImage);
                                    imageViewList.remove(currentImage);
                                    return true;
                                }
                            });
                            popupMenu.show();

                            return true;
                        }
                    });

                }

//
            }
        });

        pickFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFromGallery();
            }
        });

        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImageWithCamera();
            }
        });

        postTextView.setOnTouchListener(onTouchListener());
    }

    private void loadStickers() {
        stickerList.add(new Sticker(R.drawable.angry));
        stickerList.add(new Sticker(R.drawable.bored));
        stickerList.add(new Sticker(R.drawable.bored_1));
        stickerList.add(new Sticker(R.drawable.bored_2));
        stickerList.add(new Sticker(R.drawable.confused));
        stickerList.add(new Sticker(R.drawable.crying));
        stickerList.add(new Sticker(R.drawable.crying_1));
        stickerList.add(new Sticker(R.drawable.embarrassed));
        stickerList.add(new Sticker(R.drawable.emoticons));
        stickerList.add(new Sticker(R.drawable.happy));
        stickerList.add(new Sticker(R.drawable.happy_1));
        stickerList.add(new Sticker(R.drawable.happy_2));
        stickerList.add(new Sticker(R.drawable.happy_3));
        stickerList.add(new Sticker(R.drawable.happy_4));

        stickerAdapter = new StickerAdapter(this, stickerList);
        stickerGridView.setAdapter(stickerAdapter);
    }

    private void showTextDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edit_text);

        if (postTextView.getText().toString().equals("TEST TEXT")) {
            editText.setText("");
        } else if (!postTextView.getText().toString().equals("TEST TEXT")) {
            editText.setText(postTextView.getText().toString());
            editText.setSelection(postTextView.getText().toString().length());
        }
//


        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(MainActivity.this, "" + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                postTextView.setText(editText.getText().toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        b = dialogBuilder.create();
        b.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        b.show();
    }

    private void showOtherDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Want to see other apps by Mian");
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.other_apps_dialog, null);
        dialogBuilder.setView(dialogView);

        final GridView otherAppsGridView = (GridView) dialogView.findViewById(R.id.other_apps);
        stickerList.add(new Sticker(R.drawable.angry));
        stickerAdapter = new StickerAdapter(MainActivity.this, stickerList);
        otherAppsGridView.setAdapter(stickerAdapter);

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
                moveTaskToBack(true);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(MainActivity.this, "cancel pressed" , Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private View.OnTouchListener onTouchListenerSticker() {
        return new View.OnTouchListener() {


            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;

                }
                mainImage.invalidate();

                return false;
            }
        };
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {


            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;

                }
                mainImage.invalidate();

                return true;
            }
        };
    }

    private void setUpViews() {
        labelText = findViewById(R.id.label_edttext);
        postImageView = findViewById(R.id.background_image);
        mainImage = findViewById(R.id.mainLayout);
        styleLayout = findViewById(R.id.style_layout);
        styleButton = findViewById(R.id.style_button);
        editingOptions = findViewById(R.id.editing_options);
        rotatebutton = findViewById(R.id.rotate_button);
        buttomButtons = findViewById(R.id.bottom_buttons);
        stickerbutton = findViewById(R.id.stickerbutton);
        pickFromGallery = findViewById(R.id.pick_from_gallery);
        captureImage = findViewById(R.id.capture_image);
        backgroundbutton = findViewById(R.id.wallpaper_button);
        stickerGridView = findViewById(R.id.stickergrid);
        xAxis = findViewById(R.id.x_axis);
        yAxis = findViewById(R.id.y_axis);
        textcharacter = findViewById(R.id.text_character);
        zAxis = findViewById(R.id.z_axis);
        lineSeek = findViewById(R.id.line_spacingSeekbar);
        sizeSeek = findViewById(R.id.textsizeseekbar);
        spacingSeek = findViewById(R.id.word_spacingSeekbar);
        fontTitles = findViewById(R.id.font_titles);
        characterlayout = findViewById(R.id.characterlayout);
        backgroundGridView = findViewById(R.id.backgroud_grid_view);
        selectorCard = findViewById(R.id.selector_card);
        englishFontListView = findViewById(R.id.englishListView);
        urduFontListView = findViewById(R.id.urduListView);
        arabicFontListView = findViewById(R.id.arabicListView);
        hindiFontListView = findViewById(R.id.hindiListView);
        postTextView = findViewById(R.id.post_text);
        colorpickerbutton = findViewById(R.id.color_picker);
        rotateLinearLayout = findViewById(R.id.rotate_layout);

        List<Fonts> fonttitles = new ArrayList<>();

        fonttitles.add(new Fonts("English"));
        fonttitles.add(new Fonts("Urdu"));
        fonttitles.add(new Fonts("Arabic"));
        fonttitles.add(new Fonts("Hindi"));

        fontAdapter = new FontAdapter(this, fonttitles);
        fontTitles.setAdapter(fontAdapter);
    }

    //    When loading fonts please ensure that the name you use here is the same with the fontname in font folder
    private void loadEnglishfonts() {
//        Add english fonts here
        englishFontsList.add(new Fonts("Baloony"));
        englishFontsList.add(new Fonts("Brewsky"));
        englishFontsList.add(new Fonts("Cheeky Rabbit"));
        englishFontsList.add(new Fonts("Delicious"));
        englishFontsList.add(new Fonts("Diploma"));
        englishFontsList.add(new Fonts("Happy cat"));
        englishFontsList.add(new Fonts("Corsiva"));
        englishFontsList.add(new Fonts("My crush"));
        englishFontsList.add(new Fonts("Water park"));

        fontAdapter = new FontAdapter(this, englishFontsList);
        englishFontListView.setAdapter(fontAdapter);
    }

    private void loadUrdufonts() {
//        Add urdu fonts here
        urduFontsList.add(new Fonts("Delicious"));
        urduFontsList.add(new Fonts("Diploma"));
        urduFontsList.add(new Fonts("Happy cat"));
        urduFontsList.add(new Fonts("Corsiva"));
        urduFontsList.add(new Fonts("My crush"));
        urduFontsList.add(new Fonts("Water park"));
        urduFontsList.add(new Fonts("Baloony"));
        urduFontsList.add(new Fonts("Brewsky"));
        urduFontsList.add(new Fonts("Cheeky Rabbit"));
        fontAdapter = new FontAdapter(this, urduFontsList);
        urduFontListView.setAdapter(fontAdapter);
    }

    private void loadArabicFonts() {
//        Add arabic fonts here
        arabicFontsList.add(new Fonts("Baloony"));
        arabicFontsList.add(new Fonts("Brewsky"));
        arabicFontsList.add(new Fonts("Cheeky Rabbit"));
        arabicFontsList.add(new Fonts("Delicious"));
        arabicFontsList.add(new Fonts("Diploma"));
        arabicFontsList.add(new Fonts("Happy cat"));
        arabicFontsList.add(new Fonts("Corsiva"));
        arabicFontsList.add(new Fonts("My crush"));
        arabicFontsList.add(new Fonts("Water park"));

        fontAdapter = new FontAdapter(this, arabicFontsList);
        arabicFontListView.setAdapter(fontAdapter);
    }

    private void loadHindiFont() {
//        Add hindi fonts here
        hindiFontsList.add(new Fonts("Delicious"));
        hindiFontsList.add(new Fonts("Diploma"));
        hindiFontsList.add(new Fonts("Happy cat"));
        hindiFontsList.add(new Fonts("Corsiva"));
        hindiFontsList.add(new Fonts("My crush"));
        hindiFontsList.add(new Fonts("Water park"));
        hindiFontsList.add(new Fonts("Baloony"));
        hindiFontsList.add(new Fonts("Brewsky"));
        hindiFontsList.add(new Fonts("Cheeky Rabbit"));
        fontAdapter = new FontAdapter(this, hindiFontsList);
        hindiFontListView.setAdapter(fontAdapter);
    }

    private void loadbackgrounds() {
//        Add background here
        backgroundsList.add(new Backgrounds(R.drawable.plain));
        backgroundsList.add(new Backgrounds(R.drawable.images));
        backgroundsList.add(new Backgrounds(R.drawable.images3));
        backgroundsList.add(new Backgrounds(R.drawable.images2));
        backgroundsList.add(new Backgrounds(R.drawable.images4));
        backgroundsList.add(new Backgrounds(R.drawable.images5));
        backgroundsList.add(new Backgrounds(R.drawable.images6));
        backgroundsList.add(new Backgrounds(R.drawable.images9));

        imageAdapter = new ImageAdapter(this, backgroundsList);
        backgroundGridView.setAdapter(imageAdapter);
    }

    private void openTextDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                postTextView.setTextColor(color);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(MainActivity.this, "No color was selected", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void openDialogBackground(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mainImage.setBackgroundColor(color);
                postImageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(MainActivity.this, "No color was selected", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        if (selectorCard.getVisibility() == View.VISIBLE) {
            selectorCard.setVisibility(View.GONE);
            rotateLinearLayout.setVisibility(View.GONE);
            fontTitles.setVisibility(View.GONE);
            buttomButtons.setVisibility(View.VISIBLE);
            labelText.setVisibility(View.VISIBLE);
            stickerGridView.setVisibility(View.GONE);
            characterlayout.setVisibility(View.GONE);
            backgroundGridView.setVisibility(View.GONE);
        } else {

            if (buttomButtons.getVisibility() == View.VISIBLE) {
                showOtherDialog();

            } else {
                buttomButtons.setVisibility(View.VISIBLE);
                characterlayout.setVisibility(View.GONE);
                stickerGridView.setVisibility(View.GONE);
                fontTitles.setVisibility(View.GONE);
                backgroundGridView.setVisibility(View.GONE);
                rotateLinearLayout.setVisibility(View.GONE);
                styleLayout.setVisibility(View.GONE);
            }
        }
    }

    private void checkpermission() {
        Ask.on(this)
                .id(PERMISSION_REQUEST) // in case you are invoking multiple time Ask from same activity or fragment
                .forPermissions(Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withRationales("In other for the app to work perfectly you have to give it permission") //optional
                .go();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            saveDesign(mainImage);
        }

        if (item.getItemId() == R.id.share) {
            sharePic(mainImage);
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveDesign(View view) {
        try {
            File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "posterdesigner");

            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            String path = new File(android.os.Environment.getExternalStorageDirectory(), "posterdesigner") + "/" + generatename() + ".jpg";

//            Get the bitmap from the view
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bm = view.getDrawingCache();

            Utils.savePic(bm, path);

            Toast.makeText(this, "Poster has been saved as " + path, Toast.LENGTH_SHORT).show();
            view.destroyDrawingCache();

        } catch (NullPointerException ignored) {
            ignored.printStackTrace();
        }
    }

    public void sharePic(View view) {

        try {
            File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "posterdesigner/shared");

            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            String path = new File(android.os.Environment.getExternalStorageDirectory(), "posterdesigner/shared") + "/" + generatename() + ".jpg";

            filePath = path;
//            Get the bitmap from the view
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bm = view.getDrawingCache();

            Utils.savePic(bm, path);

//            Toast.makeText(this, "Poster has been saved as " + path, Toast.LENGTH_SHORT).show();
            view.destroyDrawingCache();

        } catch (NullPointerException ignored) {
            ignored.printStackTrace();
        }


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my awesome post");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
        shareIntent.setType("image/jpeg");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(shareIntent, "Share"));
    }

    public String generatename() {
        int min = 1109983749;
        int max = 1932398349;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        return String.valueOf(i1);
    }

    private void selectImageFromGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Image to Upload"), GALLERY_PICK);
    }

    public void captureImageWithCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            if (stickerGridView.getVisibility() == View.VISIBLE) {
                imageView = new ImageView(MainActivity.this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
                mainImage.addView(imageView);
                imageView.setOnTouchListener(onTouchListener());

                Uri selectedImage = data.getData();
                Picasso.get().load(selectedImage).noPlaceholder().centerCrop().fit().into(imageView);
            } else {
                selectorCard.setVisibility(View.GONE);
                buttomButtons.setVisibility(View.GONE);
                fontTitles.setVisibility(View.GONE);
                labelText.setVisibility(View.VISIBLE);
                stickerGridView.setVisibility(View.GONE);
                characterlayout.setVisibility(View.GONE);
                backgroundGridView.setVisibility(View.VISIBLE);

                Toast.makeText(this, "Image has been picked", Toast.LENGTH_SHORT).show();
                Uri selectedImage = data.getData();
                Picasso.get().load(selectedImage).noPlaceholder().centerCrop().fit().into(postImageView);
            }

        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            postImageView.setImageBitmap(photo);
        }
    }

}
