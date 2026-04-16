package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VolumeCategory;
import com.garden.api.voicechat.api.events.UnregisterVolumeCategoryEvent;

public class UnregisterVolumeCategoryEventImpl extends VolumeCategoryEventImpl implements UnregisterVolumeCategoryEvent {

    public UnregisterVolumeCategoryEventImpl(VolumeCategory category) {
        super(category);
    }

}
