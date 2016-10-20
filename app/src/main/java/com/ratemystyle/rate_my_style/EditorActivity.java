package com.ratemystyle.rate_my_style;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

/**
 * Created by Jean Paul on 13-10-2016.
 */

/**
 * Main activity of the photo editor.
 */
public class EditorActivity extends Activity {
    private Toolbar toolbar;
    private View backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

//            if (ImageUtils.gdk())
//            {
//                // HACK: create faked view in order to read bitcode in resource
//                View view = new View(getApplication());
//                byte[] pgm;
//                int pgmLength;
//                // read bitcode in res
//                InputStream is = view.getResources().openRawResource(R.raw.libjni_photoeditor_portable);
//                try {
//                    try {
//                        pgm = new byte[1024];
//                        pgmLength = 0;
//                        while(true) {
//                            int bytesLeft = pgm.length - pgmLength;
//                            if (bytesLeft == 0) {
//                                byte[] buf2 = new byte[pgm.length * 2];
//                                System.arraycopy(pgm, 0, buf2, 0, pgm.length);
//                                pgm = buf2;
//                                bytesLeft = pgm.length - pgmLength;
//                            }
//                            int bytesRead = is.read(pgm, pgmLength, bytesLeft);
//                            if (bytesRead <= 0) {
//                                break;
//                            }
//                            pgmLength += bytesRead;
//                        }
//                        ImageUtils.init(pgm, pgmLength);
//                    } finally {
//                        is.close();
//                    }
//                } catch(IOException e) {
//                    throw new Resources.NotFoundException();
//                }
//            }
//            toolbar = (Toolbar) findViewById(R.id.toolbar);
//            toolbar.initialize();
//            final EffectsBar effectsBar = (EffectsBar) findViewById(R.id.effects_bar);
//            final View actionBar = findViewById(R.id.action_bar);
//            final View quickviewOn = findViewById(R.id.quickview_on_button);
//            backButton = findViewById(R.id.action_bar_back);
//            backButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (actionBar.isEnabled()) {
//                        if (quickviewOn.getVisibility() == View.VISIBLE) {
//                            quickviewOn.performClick();
//                        } else if (effectsBar.hasEffectOn()) {
//                            effectsBar.effectsOff(null);
//                        } else {
//                            tryRun(new Runnable() {
//                                @Override
//                                public void run() {
//                                    finish();
//                                }
//                            });
//                        }
//                    }
//                }
//            });
//            Intent intent = getIntent();
//            if (Intent.ACTION_EDIT.equalsIgnoreCase(intent.getAction()) && (intent.getData() != null)) {
//                toolbar.openPhoto(intent.getData());
//            }
//        }
//        private void tryRun(final Runnable runnable) {
//            if (findViewById(R.id.save_button).isEnabled()) {
//                // Pop-up a dialog before executing the runnable to save unsaved photo.
//                AlertDialog.Builder builder = new AlertDialog.Builder(this)
//                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                toolbar.savePhoto(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        runnable.run();
//                                    }
//                                });
//                            }
//                        })
//                        .setNeutralButton(R.string.no, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                runnable.run();
//                            }
//                        })
//                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // no-op
//                            }
//                        });
//                builder.setMessage(R.string.save_photo).show();
//                return;
//            }
//            runnable.run();
//        }
//        @Override
//        public void onBackPressed() {
//            backButton.performClick();
//        }
    }
}

