package com.iug.jerusalem.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iug.jerusalem.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddArticleActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editText;
    CheckBox checkBoxDoors, checkBoxHistory, checkBoxLandmarks, checkBoxNeighboringTowns;
    Button buttonImage, buttonAddArticle;

    int REC_CODE = 100;

    Uri imageUri = null;

    FirebaseFirestore db;
    StorageReference storageRef;

    String newImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        initItems();

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddArticleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    String[] params = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(AddArticleActivity.this, params, REC_CODE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "image"), REC_CODE);
                }
            }
        });

        buttonAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBoxHistory.isChecked() && !checkBoxLandmarks.isChecked() && !checkBoxDoors.isChecked() && !checkBoxNeighboringTowns.isChecked()) {
                    Toast.makeText(getApplicationContext(), "لم يتم اختيار قسم للمقال", Toast.LENGTH_SHORT).show();

                } else if (imageUri == null) {
                    Toast.makeText(getApplicationContext(), "لم يتم اختيار صورة للمقال", Toast.LENGTH_SHORT).show();

                } else if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "لم يتم ادخال نص للمقال", Toast.LENGTH_SHORT).show();

                } else {
                    addArticle(editText.getText().toString().trim(), getDepartmentSelected(), imageUri);
                }
            }
        });

        checkBoxDoors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkBoxHistory.setChecked(false);
                    checkBoxLandmarks.setChecked(false);
                    checkBoxNeighboringTowns.setChecked(false);
                }
            }
        });

        checkBoxHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkBoxDoors.setChecked(false);
                    checkBoxLandmarks.setChecked(false);
                    checkBoxNeighboringTowns.setChecked(false);
                }
            }
        });

        checkBoxLandmarks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkBoxHistory.setChecked(false);
                    checkBoxDoors.setChecked(false);
                    checkBoxNeighboringTowns.setChecked(false);
                }
            }
        });

        checkBoxNeighboringTowns.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkBoxHistory.setChecked(false);
                    checkBoxLandmarks.setChecked(false);
                    checkBoxDoors.setChecked(false);
                }
            }
        });

    }

    private void initItems() {
        toolbar = findViewById(R.id.toolbar);
        editText = findViewById(R.id.editText);
        buttonImage = findViewById(R.id.buttonAddImage);
        buttonImage = findViewById(R.id.buttonAddImage);
        checkBoxDoors = findViewById(R.id.checkBoxDoors);
        checkBoxHistory = findViewById(R.id.checkBoxHistory);
        checkBoxLandmarks = findViewById(R.id.checkBoxLandmarks);
        checkBoxNeighboringTowns = findViewById(R.id.checkBoxNeighboringTowns);
        buttonAddArticle = findViewById(R.id.buttonAdd);

        db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addArticle(String details, String departmentSelected, Uri imageUri) {
        Toast.makeText(this, "جاري اضافة الموضوع...", Toast.LENGTH_LONG).show();

        newImageName = getRandomString() + ".jpg";

        StorageReference reference = storageRef.child(newImageName);
        reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("TAG", "onComplete: " + task.getException());
                    return;
                }
                Log.e("TAG", "onComplete: " + task.getResult());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "onFailure: " + e.getMessage());
            }
        });

        Map<String, Object> map = new HashMap<>();
        map.put("Details", details);
        map.put("image", "https://firebasestorage.googleapis.com/v0/b/jerusalem-88298.appspot.com/o/" + newImageName + "?alt=media&token=3aa2e1df-8455-4a4b-b1c0-d3a186db3235");
        map.put("video", "");
        map.put("hasVideo", 0);

        db.collection(departmentSelected)
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "تم اضافة المقال", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "فشل اضافة المقال", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getDepartmentSelected() {
        if (checkBoxDoors.isChecked()) {
            return "JerusalemDoors";
        } else if (checkBoxHistory.isChecked()) {
            return "JerusalemHistory";
        } else if (checkBoxNeighboringTowns.isChecked()) {
            return "JerusalemNeighboringTowns";
        } else {
            return "JerusalemLandmarks";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REC_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                imageUri = data.getData();
            }
        }
    }

    protected String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

}