package com.wswdteam.txtlauncher;

import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIcon;
import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultIcons;
import static com.wswdteam.txtlauncher.TxTLauncherApp.homeSysIcon;
import static com.wswdteam.txtlauncher.TxTLauncherApp.iconSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.packageMan;
import static com.wswdteam.txtlauncher.TxTLauncherApp.textColorMode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.List;

public class IconManagement {
    private static Boolean mainIcon = false;



    //
    //  Fő nézet: alapértelmezett gombok beállítása
    //
    public static void setIconColor(Activity act, int id, int color) {
        ImageView btn;
        Drawable drw;
        btn = act.findViewById(id);
        drw = btn.getDrawable();
        if (drw != null) {
            drw = DrawableCompat.wrap(drw).mutate();
            DrawableCompat.setTint(drw, color);
            btn.setImageDrawable(drw);
        }
    }


    //
    // Fő képernyő vezérlő alapikonok kezelése
    //

    public static void iconMainApp(View view) {
        ImageView appF = view.findViewById(R.id.applistButton);
        Drawable mIcon = defaultIcons.get(2);
        appF.clearColorFilter();
        appF.setBackground(null);
        if ((homeSysIcon) && (!adaptiveIcon)) {
            int iSize = mIcon.getIntrinsicHeight();
            //appF.setImageDrawable(getDefaultIconMain(iSize, mIcon));
            mainIcon = true;
            appF.setImageDrawable(getDefaultMonoIcon(view, mIcon, iSize));
            mainIcon = false;
            appF.setPadding(0, 0, 0, 0);
        } else {
            appF.setImageDrawable(mIcon);
        }
        if (textColorMode) {
            if (homeSysIcon) {
                if (adaptiveIcon) {
                    appF.setColorFilter(adaptiveIconColor);
                } else {
                    appF.setColorFilter(defaultIconColor);
                }
            } else {
                appF.setColorFilter(adaptiveIconColor);
            }
        } else {
            appF.setColorFilter(defaultIconColor);
        }
    }



    // fő ikonok kirajzolása
    private static Drawable mainDrawable(View view, Drawable appI, Drawable drI) {
        Drawable iconToDisplay;
        int iSize = appI.getIntrinsicHeight();
        if (adaptiveIcon && appI instanceof AdaptiveIconDrawable) {
            AdaptiveIconDrawable adaptI = (AdaptiveIconDrawable) appI;
            Drawable monoIcon = adaptI.getMonochrome();
            if (monoIcon != null) {
                iconToDisplay = getDefaultMonoIcon(view, monoIcon, iSize);
                if (iconToDisplay == null) {
                    iconToDisplay = getDefaultIconMain(iconSize, drI);
                }
            } else {
                iconToDisplay = getDefaultMonoIcon(view, drI, iSize);
            }
        } else {
            if (adaptiveIcon) {
                iconToDisplay = getDefaultIconMain(iconSize, drI);
            } else {
                LayerDrawable layer = new LayerDrawable(new Drawable[]{appI});
                layer.setLayerSize(0, iconSize, iconSize);
                iconToDisplay = layer;
            }
        }
        if (!adaptiveIcon) {
            if (homeSysIcon) {
                iconToDisplay = appI;
            } else {
                iconToDisplay = drI;
            }
        }
        assert iconToDisplay != null;
        iconToDisplay.setBounds(0, 0, iconSize, iconSize);
        return iconToDisplay;
    }


    // Segédfüggvény a kód tisztaságáért
    private static Drawable getDefaultIconMain(int iconSize, Drawable drI) {
        if (drI != null) {
            drI.mutate();
            drI.setTint(adaptiveIconColor);
            drI.setBounds(0, 0, iconSize, iconSize);
            LayerDrawable layer = new LayerDrawable(new Drawable[]{drI});
            layer.setLayerSize(0, iconSize, iconSize);
            return layer;
        }
        return null;
    }



    //
    // gomb szinezése
    //
    public static void imageButtonColorize(Context cont, View view, Intent in, ImageView iv, Drawable dr, int deficon) {
        List<ResolveInfo> appDL = cont.getPackageManager().queryIntentActivities(in, 0);
        ResolveInfo appD = appDL.get(0);
        if (homeSysIcon) {
            Drawable appI = appD.loadIcon(packageMan);
            @SuppressLint("UseCompatLoadingForDrawables")
            Drawable appI2 = mainDrawable(view, appI, dr);
            iv.setImageDrawable(appI2);
            iv.clearColorFilter();
            if (adaptiveIcon) {
                if (textColorMode) {
                    iv.setColorFilter(adaptiveIconColor);
                } else {
                    iv.setColorFilter(defaultIconColor);
                }
            }
        } else {
            iv.setImageDrawable(defaultIcons.get(deficon));
            if (textColorMode) {
                iv.clearColorFilter();
                iv.setColorFilter(adaptiveIconColor);
            } else {
                if (!homeSysIcon) {
                    iv.clearColorFilter();
                    iv.setColorFilter(defaultIconColor);
                }
            }
        }
    }



    //
    // Ikon lista: ikonok kezelése
    //

    //
    // ikonok kiszedése és szinezáse
    //
    static Drawable getDrawableTxT(View view, Drawable appI, int iconSize) {
        Drawable iconToDisplay;
        Drawable base = appI != null ? appI.mutate() : null;

        if (adaptiveIcon && base instanceof AdaptiveIconDrawable) {
            AdaptiveIconDrawable adaptI = (AdaptiveIconDrawable) base;
            Drawable monoIcon = adaptI.getMonochrome();
            if (monoIcon == null) {
                monoIcon = ContextCompat.getDrawable(view.getContext(), R.drawable.app);
            }
            iconToDisplay = getDefaultMonoIcon(view, monoIcon, iconSize);
            if (iconToDisplay == null) {
                iconToDisplay = getDefaultIconAsLayer(view, iconSize);
            }
        } else {
            if (adaptiveIcon) {
                iconToDisplay = getDefaultIconAsLayer(view, iconSize);
            } else {
                if (base == null) {
                    iconToDisplay = getDefaultIconAsLayer(view, iconSize);
                } else {
                    LayerDrawable layer = new LayerDrawable(new Drawable[]{base});
                    layer.setLayerSize(0, iconSize, iconSize);
                    layer.setBounds(0, 0, iconSize, iconSize);
                    iconToDisplay = layer;
                }
            }
        }
        assert iconToDisplay != null;
        iconToDisplay.setBounds(0, 0, iconSize, iconSize);
        return iconToDisplay;
    }

    // alapértelmezett app ikon
    private static Drawable getDefaultIconAsLayer(View view, int iconSize) {
        try {
            Drawable d = ContextCompat.getDrawable(view.getContext(), R.drawable.app);
            if (d != null) {
                d = d.mutate();
                if (textColorMode) {
                    d.setTint(adaptiveIconColor);
                }
                LayerDrawable layer = new LayerDrawable(new Drawable[]{d});
                layer.setLayerSize(0, iconSize, iconSize);
                layer.setBounds(0, 0, iconSize, iconSize);
                return layer;
            }
        } catch (Exception e) {
            // Hiba kezelése
        }
        return null;
    }

    // mono app ikon
    public static Drawable getDefaultMonoIcon(View view, Drawable monoIcon, int mIconSize) {
        try {
            if (monoIcon != null) {
                int iconSizeL = iconSize;
                monoIcon = monoIcon.mutate();
                monoIcon.setTint(adaptiveIconColor);
                monoIcon.setBounds(0, 0, mIconSize, mIconSize);
                // 1
                // - float negInset = -0.50f; // -50%
                // - InsetDrawable insetIcon = new InsetDrawable(monoIcon, negInset);
                // - LayerDrawable layer = new LayerDrawable(new Drawable[]{insetIcon});
                // - layer.setLayerSize(0, iconSize, iconSize);
                // - layer.setBounds(0, 0, iconSize, iconSize);
                // 2 - nagyított ikon, de drive eltűnik
                // - int largerSize = (int) (iconSize * 1.5f);
                // - int offset = (largerSize - iconSize) / 2;
                // - LayerDrawable layer = new LayerDrawable(new Drawable[]{monoIcon});
                // - layer.setLayerSize(0, largerSize, largerSize);
                // - layer.setLayerInset(0, -offset, -offset, -offset, -offset);
                // - layer.setBounds(0, 0, iconSize, iconSize);
                // 3 - teljes háttér
                // - GradientDrawable background = new GradientDrawable();
                // - int baseColor = ContextCompat.getColor(view.getContext(), R.color.transparentgray);
                // - int translucentColor = ColorUtils.setAlphaComponent(baseColor, 180);
                // - background.setColor(translucentColor);
                // - background.setShape(GradientDrawable.OVAL);
                // - background.setCornerRadius(iconSize * 0.22f);
                // 4
                // - GradientDrawable background = new GradientDrawable();
                // - background.setColor(Color.TRANSPARENT);
                // - int baseColor = ContextCompat.getColor(view.getContext(), R.color.transparentgray);
                // - int translucentColor = ColorUtils.setAlphaComponent(baseColor, 180);
                // - background.setStroke(iconPadding, translucentColor);
                // - background.setShape(GradientDrawable.OVAL);
                // - int inset = iconPadding;
                // - if (full) {
                // -   inset = inset * 4;
                // - }
                // - LayerDrawable layer = new LayerDrawable(new Drawable[]{background, monoIcon});
                // - layer.setLayerSize(0, iconSize, iconSize);
                // - layer.setLayerInset(1, inset, inset, inset, inset);
                // - return layer;

                //  Nagyobb ideiglenes Bitmap az elemzéshez és vágáshoz
                int rawSize = mIconSize * 2;
                Bitmap rawBitmap = Bitmap.createBitmap(rawSize, rawSize, Bitmap.Config.ARGB_8888);
                Canvas rawCanvas = new Canvas(rawBitmap);
                monoIcon.setBounds(0, 0, rawSize, rawSize);
                monoIcon.draw(rawCanvas);
                // Automatikus margóvágás (tartalom határainak keresése)
                int minX = rawSize, minY = rawSize, maxX = 0, maxY = 0;
                boolean hasContent = false;
                for (int y = 0; y < rawSize; y++) {
                    for (int x = 0; x < rawSize; x++) {
                        int pixel = rawBitmap.getPixel(x, y);
                        int r = Color.red(pixel);
                        int g = Color.green(pixel);
                        int b = Color.blue(pixel);
                        int a = Color.alpha(pixel);
                        if (!(r > 240 && g > 240 && b > 240) && a > 50) {
                            if (x < minX) minX = x;
                            if (x > maxX) maxX = x;
                            if (y < minY) minY = y;
                            if (y > maxY) maxY = y;
                            hasContent = true;
                        }
                    }
                }
                if (!hasContent || minX >= maxX || minY >= maxY) {
                    minX = 0; minY = 0; maxX = rawSize; maxY = rawSize;
                }
                int croppedWidth = maxX - minX;
                int croppedHeight = maxY - minY;
                Bitmap croppedBitmap = Bitmap.createBitmap(rawBitmap, minX, minY, croppedWidth, croppedHeight);
                // Aránytartó méretezés és középre igazítás belső paddinggel
                Bitmap finalBitmap = Bitmap.createBitmap(mIconSize, mIconSize, Bitmap.Config.ARGB_8888);
                Canvas finalCanvas = new Canvas(finalBitmap);
                float paddingPercentage;
                if (mIconSize == iconSizeL) {
                    paddingPercentage = 0.3f;
                } else {
                    paddingPercentage = 0.2f;
                }
                float maxAvailableSize = mIconSize * (1.0f - (paddingPercentage * 2));
                float scale = Math.min(maxAvailableSize / croppedWidth, maxAvailableSize / croppedHeight);
                float scaledWidth = croppedWidth * scale;
                float scaledHeight = croppedHeight * scale;
                float left = (mIconSize - scaledWidth) / 2f;
                float top = (mIconSize - scaledHeight) / 2f;
                Rect srcRect = new Rect(0, 0, croppedWidth, croppedHeight);
                RectF dstRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
                Paint tintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                finalCanvas.drawBitmap(croppedBitmap, srcRect, dstRect, tintPaint);
                //int baseColor = ContextCompat.getColor(view.getContext(), R.color.transparentgray);
                GradientDrawable background = new GradientDrawable();
                if ((mIconSize == iconSizeL) || mainIcon) {
                    int baseColor = adaptiveIconColor;
                    int translucentColor = ColorUtils.setAlphaComponent(baseColor, 120);
                    // A keret távolsága a szélektől
                    int frameMargin = (int) (mIconSize * 0.1f);
                    background.setShape(GradientDrawable.OVAL);
                    background.setStroke((int) (mIconSize * 0.05f), translucentColor);
                    // Keret vastagsága
                    background.setColor(Color.TRANSPARENT);
                    // Itt állítjuk be a margót, hogy a keret ne érjen ki teljesen a szélére
                    if (mainIcon) {
                        background.setBounds(0, 0, mIconSize, mIconSize);
                    } else {
                        background.setBounds(frameMargin, frameMargin, mIconSize - frameMargin, mIconSize - frameMargin);
                    }
                    background.draw(finalCanvas);
                } else {
                    background.setBounds(0, 0, mIconSize, mIconSize);
                }
                Drawable mi = new BitmapDrawable(view.getContext().getResources(), finalBitmap);
                if (!textColorMode) {
                    mi.setTint(defaultIconColor);
                }
                return mi;
            }
        } catch (Exception e) {
            // Hiba kezelése
        }
        return null;
    }


}
