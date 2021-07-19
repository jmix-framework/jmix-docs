package search.ex1.screen.card;

import io.jmix.ui.screen.*;
import search.ex1.entity.Card;

@UiController("sample_Card.browse")
@UiDescriptor("card-browse.xml")
@LookupComponent("cardsTable")
public class CardBrowse extends StandardLookup<Card> {
}