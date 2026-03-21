package com.garden.api.network.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import com.garden.api.animatable.GeoBlockEntity;
import com.garden.api.animatable.stateless.StatelessGeoBlockEntity;
import com.garden.api.core.animation.RawAnimation;
import com.garden.api.util.ClientUtils;
import com.garden.api.util.NetworkUtil;

import java.util.function.Supplier;

/**
 * Packet for instructing clients to set a play state for an animation for a {@link StatelessGeoBlockEntity}
 */
public class StatelessBlockEntityPlayAnimPacket {
	private final BlockPos blockPos;
	private final RawAnimation animation;

	public StatelessBlockEntityPlayAnimPacket(BlockPos blockPos, RawAnimation animation) {
		this.blockPos = blockPos;
		this.animation = animation;
	}

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.blockPos);
        NetworkUtil.writeRawAnimationToBuffer(animation, buffer);
    }

    public static StatelessBlockEntityPlayAnimPacket decode(FriendlyByteBuf buffer) {
        BlockPos blockPos = buffer.readBlockPos();
        RawAnimation animation = NetworkUtil.readRawAnimationFromBuffer(buffer);

        return new StatelessBlockEntityPlayAnimPacket(blockPos, animation);
    }

    public void receivePacket(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context handler = context.get();

        handler.enqueueWork(() -> {
            if (ClientUtils.getLevel().getBlockEntity(blockPos) instanceof GeoBlockEntity blockEntity && blockEntity instanceof StatelessGeoBlockEntity statelessAnimatable)
                statelessAnimatable.handleClientAnimationPlay(blockEntity, 0, animation);
        });
    }
}
