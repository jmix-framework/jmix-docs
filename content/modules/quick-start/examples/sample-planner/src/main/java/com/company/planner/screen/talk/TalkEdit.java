package com.company.planner.screen.talk;

import io.jmix.ui.screen.*;
import com.company.planner.entity.Talk;

@UiController("planner_Talk.edit")
@UiDescriptor("talk-edit.xml")
@EditedEntityContainer("talkDc")
public class TalkEdit extends StandardEditor<Talk> {
    //tag::init-entity[]
    @Subscribe
    public void onInitEntity(InitEntityEvent<Talk> event) {
        event.getEntity().setDuration(1);
    }
    //end::init-entity[]
}