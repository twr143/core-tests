package defaultmethods;

import java.util.Comparator;

/**
 * Created by ilya on 19.04.2017.
 */
public class SortByRankThenSuit implements Comparator<Card>
{
  public int compare(Card firstCard, Card secondCard)
  {
    int compVal =
            firstCard.getRank().value() - secondCard.getRank().value();
    if (compVal != 0)
      return compVal;
    else
      return firstCard.getSuit().value() - secondCard.getSuit().value();
  }
}