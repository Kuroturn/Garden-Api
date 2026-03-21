package com.garden.api.world;

import javax.annotation.Nullable;

public interface CachingClientLevel {

    @Nullable
    ClonedClientLevel garden_api$getCachedClone();

    void garden_api$setCachedClone(@Nullable ClonedClientLevel cachedClone);

}

