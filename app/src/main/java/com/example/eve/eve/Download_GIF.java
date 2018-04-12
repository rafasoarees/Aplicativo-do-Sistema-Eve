package com.example.eve.eve;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Detran_2 on 30/10/2017.
 */

public class Download_GIF extends AsyncTask<String, Void, String> {
    String url_image;
    File localFile = null;
    String filepath;


    public Download_GIF(String url) {
        url_image = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl(url_image);
        storageRef.getName();
        Log.i("ref", "" + storageRef.getName());

        //get download file url
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.i("Main", "File uri: " + uri.toString());

                //download the file
                // showProgressDialog("Download File", "Downloading File...");

                try {
                    localFile = File.createTempFile("images", ".jpeg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final File finalLocalFile = localFile;
                storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());

                        // save(bitmap);
                        String teste = "IMG_" + storageRef.getName();
                                                   saveArrayToInternalStorage(teste, bitmap);

//        String filepath = null;
//        try {
//            //set the download URL, a url that points to a file on the internet
//            //this is the file to be downloaded
//            URL url = null;
//            try {
//                url = new URL(url_image);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            //create the new connection
//            assert url != null;
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//            //set up some things on the connection
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setDoOutput(true);
//            //and connect!
//            urlConnection.connect();
//            //set the path where we want to save the file
//            //in this case, going to save it on the root directory of the
//            //sd card.
//            File SDCardRoot = Environment.getExternalStorageDirectory();
//            //create a new file, specifying the path, and the filename
//            //which we want to save the file as.
//
//            String filename = "downloadedFile.jpeg";   // you can download to any type of file ex:.jpeg (image) ,.txt(text file),.mp3 (audio file)
//            Log.i("Local filename:", "" + filename);
//            File file = new File(SDCardRoot, filename);
//
//
//            //this will be used to write the downloaded data into the file we created
//            FileOutputStream fileOutput = new FileOutputStream(file);
//
//            //this will be used in reading the data from the internet
//            InputStream inputStream = urlConnection.getInputStream();
//
//            //this is the total size of the file
//            int totalSize = urlConnection.getContentLength();
//            //variable to store total downloaded bytes
//            int downloadedSize = 0;
//
//            //create a buffer...
//            byte[] buffer = new byte[1024];
//            int bufferLength = 0; //used to store a temporary size of the buffer
//
//            //now, read through the input buffer and write the contents to the file
//            while ((bufferLength = inputStream.read(buffer)) > 0) {
//                //add the data in the buffer to the file in the file output stream (the file on the sd card
//                fileOutput.write(buffer, 0, bufferLength);
//                //add up the size so we know how much is downloaded
//                downloadedSize += bufferLength;
//                //this is where you would do something to report the prgress, like this maybe
//                Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
//
//            }
//            //close the output stream when done
//            fileOutput.close();
//            if (downloadedSize == totalSize) {
//                filepath = file.getPath();
//            }
//
//            //catch some possible errors...
//        } catch (MalformedURLException | ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            filepath = null;
//            e.printStackTrace();
//        }
//        Log.i("filepath:", " " + filepath);
//
//        return filepath;
                        filepath = localFile.toString();
                        Log.i("path",filepath);

                    }
                });

            }
        });
        return filepath;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public void saveArrayToInternalStorage(String name, Bitmap bmp) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Pictures/share/" + name );
            fos.write(byteArray);
            fos.close();

        } catch (IOException e) {
            Log.w("InternalStorage", "Error writing", e);
        }
    }

}