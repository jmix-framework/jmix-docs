package com.company.onboarding.view.component.virtuallist;

import com.company.onboarding.entity.User;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.fragmentrenderer.FragmentRenderer;
import io.jmix.flowui.fragmentrenderer.RendererItemContainer;

// tag::fragment[]
@FragmentDescriptor("user-fragment.xml") // <1>
@RendererItemContainer("userDc") // <2>
public class UserFragment extends FragmentRenderer<HorizontalLayout, User> {
}
// end::fragment[]