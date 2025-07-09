package tallestred.witch_additions;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class WADataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, WitchAdditions.MODID);
    public static final Supplier<AttachmentType<String>> FROG_TRANSFORMED_FROM = ATTACHMENT_TYPES.register(
            "frog_transformed_from", () -> AttachmentType.builder(() -> "").serialize(Codec.STRING).build()
    );
}
