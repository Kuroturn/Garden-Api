package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VolumeCategory;
import com.garden.api.voicechat.api.events.RegisterVolumeCategoryEvent;

public class RegisterVolumeCategoryEventImpl extends VolumeCategoryEventImpl implements RegisterVolumeCategoryEvent {

    public RegisterVolumeCategoryEventImpl(VolumeCategory category) {
        super(category);
    }

}
