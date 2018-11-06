package edu.cnm.deepdive.videopoker.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.Converter;
import edu.cnm.deepdive.videopoker.model.Game;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.db.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;

public class GameActivity extends AppCompatActivity {

  private static final int BET_MAX = 5;
  private static final int HAND_SIZE = 5;
  private static final String EMPTY_STRING = "";

  private CardButton[] cardButtons;
  private Button dealButton;
  private Button drawButton;
  private Button betOneButton;
  private Button betMaxButton;

  private TextView winningHandView;
  private TextView winView;
  private TextView betView;
  private TextView purseView;

  private boolean firstDeal = true;
  private boolean debug = true;
  private boolean viewAsDollars = false;
  private boolean fastDisplay = true;

  private Game game;
  private Converter converter = new Converter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    game = new Game(50, 0.25);
    new PokerTask().execute(game.getPlayerHand();
    setupButtons();
    setupTextViews();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    // TODO rig hand menu option for testing/future potential
    switch (item.getItemId()) {
      default:
        handled = super.onOptionsItemSelected(item);
        break;
      case R.id.fast_display:
        fastDisplay = !fastDisplay;
        break;
      case R.id.switch_currency_view:
        viewAsDollars = !viewAsDollars;
        winView.setText(getWinString(game.getWin(), game.getCreditValue(), viewAsDollars));
        purseView.setText(getPurseString(game.getPurse(), game.getCreditValue(), viewAsDollars));
        betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
        break;
      case R.id.exit:
        System.exit(0);
    }
    return handled;
  }

  private String getWinString(int win, double creditValue, boolean viewAsDollars) {
    if (viewAsDollars) {
      return getString(R.string.win_text_dollar_format, (double) win * creditValue);
    } else {
      return getString(R.string.win_text_credits_format, win);
    }
  }

  private String getPurseString(int purse, double creditValue, boolean viewAsDollars) {
    if (viewAsDollars) {
      return getString(R.string.purse_text_dollar_format, (double) purse * creditValue);
    }
    return getString(R.string.purse_text_credits_format, purse);
  }

  private String getBetString(int bet, double creditValue, boolean viewAsDollars) {
    if (viewAsDollars) {
      return getString(R.string.bet_text_dollar_format, (double) bet * creditValue);
    } else {
      return getString(R.string.bet_text_credits_format, bet);
    }
  }

  private void setupButtons() {
    dealButton = findViewById(R.id.deal_button);
    dealButton.setEnabled(false);
    drawButton = findViewById(R.id.draw_button);
    drawButton.setEnabled(false);
    betOneButton = findViewById(R.id.bet1_button);
    betMaxButton = findViewById(R.id.bet_max_button);
    cardButtons = new CardButton[]{
        findViewById(R.id.card1),
        findViewById(R.id.card2),
        findViewById(R.id.card3),
        findViewById(R.id.card4),
        findViewById(R.id.card5),
    };
    // cards start off disabled and invisible
    for (CardButton card : cardButtons) {
      card.setVisibility(View.INVISIBLE);
      card.setEnabled(false);
      card.setOnClickListener((v) -> card.toggle());
    }

    betOneButton.setOnClickListener((v) -> {
      betOne();
      dealButton.setEnabled(true);
    });

    betMaxButton.setOnClickListener((v) -> {
      betMax();
      dealButton.setEnabled(true);
    });

    dealButton.setOnClickListener((v) -> {
      // special actions for the initial deal when the game first begins
      // activate and make cards visible
      if (firstDeal) {
        for (CardButton card : cardButtons) {
          card.setVisibility(View.VISIBLE);
          card.setEnabled(true);
        }
        firstDeal = false;
      }
      deal();
      setupDraw();
    });

    drawButton.setOnClickListener((v) -> {
      draw();
      collectWinnings();
      resetGame();
    });
  }

  private void setupTextViews() {
    winningHandView = findViewById(R.id.win_notifier);
    winView = findViewById(R.id.win_view);
    betView = findViewById(R.id.bet_view);
    purseView = findViewById(R.id.purse_view);
    winningHandView.setText(EMPTY_STRING);
    winView.setText(getWinString(game.getWin(), game.getCreditValue(), viewAsDollars));
    winView.setVisibility(View.INVISIBLE);
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
    purseView.setText(getPurseString(game.getPurse(), game.getCreditValue(), viewAsDollars));
  }

  private void betOne() {
    if (game.getBet() < BET_MAX) {
      game.setBet(game.getBet() + 1);
    }
    if (game.getBet() == BET_MAX || game.getBet() >= game.getPurse()) {
      betOneButton.setEnabled(false);
      betMaxButton.setEnabled(false);
    }
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
  }

  private void betMax() {
    game.setBet(BET_MAX);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
  }

  private void deal() {
    winView.setVisibility(View.INVISIBLE);
    winningHandView.setText(EMPTY_STRING);
    game.setPurse(game.getPurse() - game.getBet());
    purseView.setText(getPurseString(game.getPurse(), game.getCreditValue(), viewAsDollars));
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
    game.getDeck().shuffle();
    game.getDeck().dealAndReplace(game.getPlayerHand());
    for (int i = 0; i < game.getPlayerHand().size(); i++) {
      displayCards(i);
    }

    // Evaluate hand to display if the player was dealt a winning hand.
    game.getPlayerHand().evaluateHand();
    String bestHand = game.getPlayerHand().getBestHand().getName();
    // Avoid returning bust string if the dealt hand is not a winning hand.
    if (!bestHand.equals(game.getPlayerHand().getBustString())) {
      winningHandView.setText(game.getPlayerHand().getBestHand().getName());
    }
    game.getPlayerHand().clearWins();
  }

  private void setupDraw() {
    dealButton.setEnabled(false);
    drawButton.setEnabled(true);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    for (CardButton card : cardButtons) {
      card.setEnabled(true);
    }
  }

  private void draw() {
    for (int i = 0; i < HAND_SIZE; i++) {
      if (!cardButtons[i].isChecked()) {
        game.getDeck().push(game.getPlayerHand().get(i));
        game.getPlayerHand().set(i, game.getDeck().remove(0));
        displayCards(i);
      }
    }
  }

  private void displayCards(int index) {
    String resourceId = game.getPlayerHand().get(index).getResourceId();
    int identifier = getResources()
        .getIdentifier(resourceId, "drawable", "edu.cnm.deepdive.videopoker");
    cardButtons[index].setImageResource(identifier);
  }

  private void collectWinnings() {
    // ADD TO PURSE
    winningHandView.setText(game.getPlayerHand().getBestHand().getName());
    if (game.getWin() > 0) {
      winView.setVisibility(View.VISIBLE);
      if (fastDisplay) {
        winView.setText(getWinString(game.getWin(), game.getCreditValue(), viewAsDollars));
        game.setPurse(game.getPurse() + game.getWin());
        purseView.setText(getPurseString(game.getPurse(), game.getCreditValue(), viewAsDollars));
      } else {
        for (int i = 0; i < game.getWin(); ++i) {
          // TODO Slow down point accumulation display
          winView.setText(getWinString(i + 1, game.getCreditValue(), viewAsDollars));
          game.setPurse(game.getPurse() + 1);
          purseView.setText(getPurseString(game.getPurse(), game.getCreditValue(), viewAsDollars));
        }
      }
    }
  }

  private void resetGame() {
    game.getPlayerHand().clearWins();
    game.setBet(0);
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
    dealButton.setEnabled(false);
    drawButton.setEnabled(false);
    for (CardButton card : cardButtons) {
      card.setEnabled(false);
      card.setChecked(false);
    }
    if (game.getPurse() >= BET_MAX) {
      betMaxButton.setEnabled(true);
      betOneButton.setEnabled(true);
    } else if (game.getPurse() > 0) {
      betOneButton.setEnabled(true);
    } else {
      winningHandView.setText(R.string.purse_empty_text);
    }
  }


  private class PokerTask extends AsyncTask<PlayerHand, Void, Void> {

    @Override
    protected Void doInBackground(PlayerHand... playerHands) {
    for (PokerHand pokerHand : Paytable.getInstance(GameActivity.this).getPokerHandDao().selectPokerHandsByBetOne()) {
      if (converter.parseRuleSequence(pokerHand.getRuleSequence(), playerHands[0])) {
        playerHands[0].setBestHand(pokerHand);
        if (game.getBet() < BET_MAX) {
          game.setWin(game.getBet() * playerHands[0].getBestHand().getBetOneValue());
        } else {
          game.setWin(game.getBet() * playerHands[0].getBestHand().getBetFiveValue());
        }
      }
    }
  }
  }
}
