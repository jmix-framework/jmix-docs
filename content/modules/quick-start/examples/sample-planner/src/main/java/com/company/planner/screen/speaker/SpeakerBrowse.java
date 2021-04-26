package com.company.planner.screen.speaker;

import io.jmix.ui.screen.*;
import com.company.planner.entity.Speaker;

@UiController("planner_Speaker.browse")
@UiDescriptor("speaker-browse.xml")
@LookupComponent("speakersTable")
public class SpeakerBrowse extends StandardLookup<Speaker> {
}