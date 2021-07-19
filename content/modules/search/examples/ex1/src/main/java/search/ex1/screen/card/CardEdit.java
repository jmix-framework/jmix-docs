package search.ex1.screen.card;

import io.jmix.ui.screen.*;
import search.ex1.entity.Card;

@UiController("sample_Card.edit")
@UiDescriptor("card-edit.xml")
@EditedEntityContainer("cardDc")
public class CardEdit extends StandardEditor<Card> {
}