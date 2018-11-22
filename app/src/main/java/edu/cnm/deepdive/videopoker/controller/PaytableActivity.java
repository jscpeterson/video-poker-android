package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import edu.cnm.deepdive.videopoker.R;

public class PaytableActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_paytable);
    this.setTitle("PAYTABLE");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.home) {
      onBackPressed();
      return false;
    }
    return(super.onOptionsItemSelected(item));
  }
}