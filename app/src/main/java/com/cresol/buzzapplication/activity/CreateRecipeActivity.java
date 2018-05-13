package com.cresol.buzzapplication.activity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.model.CreateRecipeModel;
import com.cresol.buzzapplication.model.StandardOutput;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saurabh on 9/24/2016.
 */
public class CreateRecipeActivity extends DrawerActivity {
    EditText nameEditTxt, timeEditTxt, howtoMakeEditTxt, recipeIngredientsEditTxt;
    Spinner spinLost;
    ImageView CaptureImage;

    Bitmap imageBitmap;

    //YOU CAN EDIT THIS TO WHATEVER YOU WANT
    private static final int SELECT_PICTURE = 1;
    private String filemanagerstring;
    private String selectedImagePath;
    private File output = null;
    ProgressBar pb;
    UserSessionManager session;
    File mImageFile = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "temp.png");

    Uri tempURI = Uri.fromFile(mImageFile);
    api buzzdApi;
    UserSessionManager sessionManager;
    TextView create_txt_id;
    EditText recipeName;
    EditText recipeHowToMake;
    EditText recipeIngredients;
    EditText recipeDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_create_recipe, null, false);

        drawer.addView(contentView, 0);
        showCartCountOnCart();
             //get the view of activity
        //  ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
        //  .findViewById(android.R.id.content)).getChildAt(0);
        // drawer.addView(viewGroup);

        GlobalMethods.overrideFonts(this, contentView);


        CaptureImage = (ImageView) findViewById(R.id.create_recipe_img);
        create_txt_id = (TextView) findViewById(R.id.create_txt_id);

        create_txt_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetroCallForAddingRecipes();
            }
        });

        sessionManager = new UserSessionManager(this);
        buzzdApi = GlobalMethods.GetBuzzdAPI(this);


    }


    public void GalleryImage(View view) {
        try {
            //  Intent intent = new Intent();
            //   intent.setType("recipeImage/*");
            //  intent.setAction(Intent.ACTION_GET_CONTENT);
            //  startActivityForResult(Intent.createChooser(intent,
            //       "Select Picture"), SELECT_PICTURE);
            if (Build.VERSION.SDK_INT < 19) {
                Intent intent = new Intent();
                intent.setType("recipeImage/jpeg");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("recipeImage/jpeg");
                startActivityForResult(intent, SELECT_PICTURE);

            }

        } catch (Exception e) {


        }
    }

    public void CaptureImage(View view) {
        try {

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, 0);

        } catch (Exception e) {


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 0 && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                CaptureImage.setImageBitmap(imageBitmap);
            } else if (resultCode == RESULT_OK) {

                if (requestCode == SELECT_PICTURE) {

                    Uri selectedImageUri = data.getData();

                    //OI FILE Manager
                    filemanagerstring = selectedImageUri.getPath();


                    selectedImagePath = getPath(this, selectedImageUri);


                    //DEBUG PURPOSE - you can delete this if you want
                    if (selectedImagePath != null) {
                        try {

                            //This line is used for solving out of memory problem
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8;

                            imageBitmap = BitmapFactory.decodeFile(selectedImagePath, options);


                            Bitmap bm = BitmapFactory.decodeFile(selectedImagePath, options);
                            CaptureImage.setImageBitmap(bm);

                        } catch (Exception e) {
                            e.getMessage();

                        }
                    } else {
                        Toast.makeText(this, "selectedImagePath is null", Toast.LENGTH_SHORT).show();
                    }

                    if (filemanagerstring != null) {
                        //   System.out.println(filemanagerstring);
                        //  CaptureImage.setImageBitmap(BitmapFactory.decodeFile(filemanagerstring));
                    } else {
                        Toast.makeText(this, "filemanagerstring is null", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } catch (Exception e) {


        }
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void RetroCallForAddingRecipes() {
        try {
            if (GlobalMethods.isNetworkAvailable(this)) {
                CreateRecipeModel obj = new CreateRecipeModel();
                obj.setIngredients(recipeIngredients.getText().toString());
                obj.setRecipieDescription(recipeDescription.getText().toString());
                obj.setRecipieHowToMake(recipeHowToMake.getText().toString());
                obj.setRecipieName(recipeName.getText().toString());
                obj.setStartingPrice("price");
                obj.setUserID(13);

                String imgString;
                if (imageBitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    imgString = Base64.encodeToString(byteArray,
                            Base64.NO_WRAP);
                    obj.setRecipieImage(imgString);
                }
                Call<StandardOutput> outputCall = buzzdApi.CreateRecipe(obj);

                GlobalMethods.ShowProgressBar(this);
                outputCall.enqueue(new Callback<StandardOutput>() {
                    @Override
                    public void onResponse(Call<StandardOutput> call, Response<StandardOutput> response) {
                        if (response.isSuccessful() && response.body() != null) {

                        }
                        GlobalMethods.HideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<StandardOutput> call, Throwable t) {
                        GlobalMethods.HideProgressBar();
                    }
                });
            }
        } catch (Exception e) {
            String m = e.getMessage().toString();
        }
    }


    public void showCartCountOnCart() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_drawer_id);
        setSupportActionBar(toolbar);
        TextView toolbarCartTxt = (TextView) findViewById(R.id.toolbar_cart_text);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.toolbar_image_layout);

        int count = GlobalMethods.GetCount(this);
        toolbarCartTxt.setText(String.valueOf(count));

        //Remove apptitle from Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GlobalMethods.GetCount(getApplicationContext()) != 0) {
                    Intent i = new Intent(getApplicationContext(), YourOrderActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Cart is Empty.Please add product", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}