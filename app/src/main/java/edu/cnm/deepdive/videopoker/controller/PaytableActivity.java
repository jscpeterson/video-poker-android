package edu.cnm.deepdive.videopoker.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.dao.PaytableDao;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.List;

public class PaytableActivity extends AppCompatActivity {

  private static final String PAYTABLE_ID_KEY = "paytableId";
  private static final String PAYTABLE_NAME_KEY = "paytableNameKey";

  private long paytableId;
  private String paytableName;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle extras = getIntent().getExtras();
    setContentView(R.layout.activity_paytable);
    paytableId = extras.getLong(PAYTABLE_ID_KEY);
    paytableName = extras.getString(PAYTABLE_NAME_KEY);
    this.setTitle(paytableName);
    new GetPaytableData().execute(paytableId);
  }

  @Override
  protected void onPause() {
    super.onPause();
    finish();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return (super.onOptionsItemSelected(item));
  }

  private class GetPaytableData extends AsyncTask<Long, Void, List<PokerHand>> {

    List<PokerHand> paytable;
    PaytableDatabase db;
    PokerHandDao dao;

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected List<PokerHand> doInBackground(Long... longs) {
      db = PaytableDatabase.getInstance(getApplicationContext());
      dao = db.getPokerHandDao();
      paytable = dao.selectPokerHandsByBetOneFromPaytable(longs[0]);
      return null;
    }

    @Override
    protected void onPostExecute(List<PokerHand> pokerHands) {
//      TextView pokerHandView = findViewById(R.id.paytable_hand_name);
//      TextView bet1View = findViewById(R.id.paytable_bet1);
//      TextView bet2View = findViewById(R.id.paytable_bet2);
//      TextView bet3View = findViewById(R.id.paytable_bet3);
//      TextView bet4View = findViewById(R.id.paytable_bet4);
//      TextView bet5View = findViewById(R.id.paytable_bet5);

      TableLayout tableLayout = findViewById(R.id.paytable_layout);

      for (PokerHand hand : paytable) {
        if (!hand.isShowInTable()) {
          //Skip loop if hand has showInTable flag set to false (value is 0 or there is a duplicate)
          continue;
        }
        System.out.println("loop started");
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 5, 5, 5);
        TableRow row = (TableRow)
            LayoutInflater.from(PaytableActivity.this).inflate(R.layout.paytable_row,
                findViewById(R.id.paytable_row_layout), false);
        row.setLayoutParams(lp);
        System.out.println(hand.getName());
        System.out.println(R.id.paytable_hand_name);
        TextView pokerHandView = new TextView(PaytableActivity.this);
        pokerHandView.setText(hand.getName());
        row.addView(pokerHandView);
        TextView bet1View = new TextView(PaytableActivity.this);
        bet1View.setText(Integer.toString(hand.getBetOneValue()));
        row.addView(bet1View);
        TextView bet2View = new TextView(PaytableActivity.this);
        bet2View.setText(Integer.toString(hand.getBetOneValue()*2));
        row.addView(bet2View);
        TextView bet3View = new TextView(PaytableActivity.this);
        bet3View.setText(Integer.toString(hand.getBetOneValue()*3));
        row.addView(bet3View);
        TextView bet4View = new TextView(PaytableActivity.this);
        bet4View.setText(Integer.toString(hand.getBetOneValue()*4));
        row.addView(bet4View);
        TextView bet5View = new TextView(PaytableActivity.this);
        bet5View.setText(Integer.toString(hand.getBetFiveValue()));
        row.addView(bet5View);
        tableLayout.addView(row);
      }
      super.onPostExecute(pokerHands);
    }

  }

  private class ChangePayout extends AsyncTask<Integer, Void, List<PokerHand>> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected List<PokerHand> doInBackground(Integer... integers) {
      return null;
    }

    @Override
    protected void onPostExecute(List<PokerHand> pokerHands) {
      super.onPostExecute(pokerHands);
    }

  }

}