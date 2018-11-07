package edu.cnm.deepdive.videopoker.model;

import android.support.annotation.MainThread;
import edu.cnm.deepdive.videopoker.controller.GameActivity;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.db.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class PlayerHand extends Stack<Card> {

  private final String BUST = "\u2639";

  public String getBustString() {
    return BUST;
  }

  public PokerHand getBestHand() {
    return bestHand;
  }

  public void setBestHand(PokerHand bestHand) {
    this.bestHand = bestHand;
  }

  Converter converter = new Converter();

  PokerHand bestHand;

  public PlayerHand(Card... cards) {
    for (Card card : cards) {
      this.add(card);
    }
  }


}
