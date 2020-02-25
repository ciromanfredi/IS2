package com.example.is2.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.is2.LoginActivity;
import com.example.is2.R;
import com.example.is2.javaclass.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
//import com.google.firebase.firestore.auth.User;

public class ProfileFragment extends Fragment {

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private DatabaseReference mDatabaseRef;

    private static final String TAG = "ProfileFragment";

    private static final int THUMBNAIL_SIZE = 3 ;
    Button logout_btn;
    DatabaseReference users;
    FirebaseUser userfire;
    User userobj=null;
    ImageView img_Profilo;
    Button btn_change;
    Uri imageUri;


    private static final int PICK_IMAGE = 100;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout){
            Toast.makeText(getActivity(),"Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            getActivity().finish();
            Intent logout_int = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(logout_int);
        }
        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        userfire = FirebaseAuth.getInstance().getCurrentUser();
        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String uidreq=userfire.getUid();
        if(getArguments()!=null)
            uidreq=getArguments().getString("uidreq");

        boolean mostra=(uidreq==userfire.getUid());
        System.out.println("uidreq "+uidreq+" mostra "+mostra);

        img_Profilo = (ImageView)getActivity().findViewById(R.id.profile_picture);
        btn_change = (Button)getActivity().findViewById(R.id.buttonChange);
        FirebaseStorage fs=FirebaseStorage.getInstance();
        StorageReference sr= fs.getReference().child("uploads/"+uidreq+".jpeg");
            sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getActivity())
                            .load(uri)
                            .into(img_Profilo);
                    System.out.println("getActivity"+getActivity()+" uri "+uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    System.out.println("[ProfileFragment] eccezione");
                }
            });


        if(mostra) {
            btn_change.setVisibility(VISIBLE);
            btn_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            });
          }
        else
            btn_change.setVisibility(GONE);

        users = FirebaseDatabase.getInstance().getReference("Users").child(uidreq);

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("[ProfileFragment] numero figli (users): " + dataSnapshot.getChildrenCount());
                userobj = dataSnapshot.getValue(User.class);

                TextView profilo_nome = getActivity().findViewById(R.id.profile_name);
                profilo_nome.setText(userobj.getNome());

                TextView profilo_cognome = getActivity().findViewById(R.id.profile_surname);
                profilo_cognome.setText(userobj.getCognome());

                TextView profilo_email = getActivity().findViewById(R.id.profile_email);
                profilo_email.setText(userobj.getEmail());

                TextView profilo_numero = getActivity().findViewById(R.id.profile_phone);
                profilo_numero.setText(userobj.getTelefono());

                TextView profilo_citta = getActivity().findViewById(R.id.profile_place);
                profilo_citta.setText(userobj.getCitta());

                TextView profilo_username = getActivity().findViewById(R.id.profile_username);
                profilo_username.setText(userobj.getUsername());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("on cancelled");
            }
        });

    }


    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        if (gallery.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(gallery, PICK_IMAGE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)    {
        super.onActivityResult(requestCode,resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE && data!=null) {

                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    img_Profilo.setImageBitmap(bitmap);
                    handleUpload(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

    }

    private void handleUpload(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("uploads")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ",e.getCause() );
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri);
                        setUserProfileUrl(uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Updated succesfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}



/*


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
                System.out.println("imageURIIIII" + imageUri);

            mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //        mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_LONG).show();



                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            final StorageReference reference = FirebaseStorage.getInstance().getReference()
                                    .child("uploads")
                                    .child(uid + ".jpeg");


                            String prova ="aaaa";
                            Upload upload = new Upload(prova, taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            System.out.println("DOWNLOAD URI  " +  taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            //   taskSnapshot.getDownloadUrl().toString());
                            System.out.println("ciaoooooooo");
                            String uploadId = mDatabaseRef.push().getKey();
                             System.out.println("KEYYY "+ uploadId);
                            System.out.println("ciaoooo2");
                            mDatabaseRef.child(uploadId).setValue(upload);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                /*    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });

        } else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
        return;
    }


    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("users/me/profile.png").getDownloadUrl()
            .addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri uri) {
            // Got the download URL for 'users/me/profile.png'
        })
.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

}

*/
