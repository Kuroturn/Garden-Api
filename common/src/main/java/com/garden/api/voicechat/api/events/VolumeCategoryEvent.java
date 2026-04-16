package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.VolumeCategory;

public interface VolumeCategoryEvent extends ServerEvent {

    /**
     * @return the volume category
     */
    VolumeCategory getVolumeCategory();

}
