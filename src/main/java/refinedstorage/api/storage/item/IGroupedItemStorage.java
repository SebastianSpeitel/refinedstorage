package refinedstorage.api.storage.item;

import net.minecraft.item.ItemStack;
import refinedstorage.api.network.INetworkMaster;
import refinedstorage.api.storage.CompareUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * This holds all items from all the connected storages from a {@link INetworkMaster}.
 * <p>
 * Refined Storage uses this class mainly for use in Grids and Detectors to avoid querying
 * individual {@link IItemStorage} constantly (performance impact) and to send and detect storage changes
 * more efficiently.
 */
public interface IGroupedItemStorage {
    /**
     * Rebuilds the global item list.
     * Typically called when a {@link IItemStorageProvider} is added or removed from the network.
     */
    void rebuild();

    /**
     * Adds an item to the global item list.
     * <p>
     * Note that this doesn't modify any of the connected storages, but just modifies the global item list.
     * Use {@link INetworkMaster#insertItem(ItemStack, int, boolean)} to add an item to an actual storage.
     * <p>
     * Will merge it with another item if it already exists.
     *
     * @param stack      the stack to add, do NOT modify
     * @param rebuilding whether this method is called while the storage is rebuilding
     */
    void add(@Nonnull ItemStack stack, boolean rebuilding);

    /**
     * Removes a item from global item list.
     * <p>
     * Note that this doesn't modify any of the connected storages, but just modifies the global item list.
     * Use {@link INetworkMaster#extractItem(ItemStack, int, int)} to remove an item from an actual storage.
     *
     * @param stack the item to remove, do NOT modify
     */
    void remove(@Nonnull ItemStack stack);

    /**
     * Gets an item from the network.
     *
     * @param stack the stack to find
     * @param flags the flags to compare on, see {@link CompareUtils}
     * @return null if no item is found, or the stack, do NOT modify
     */
    @Nullable
    ItemStack get(@Nonnull ItemStack stack, int flags);

    /**
     * Gets an item from the network by hash, see {@link refinedstorage.api.network.NetworkUtils#getItemStackHashCode(ItemStack)}.
     *
     * @return null if no item is found matching the hash, or the stack, do NOT modify
     */
    @Nullable
    ItemStack get(int hash);

    /**
     * @return all items in this storage network
     */
    Collection<ItemStack> getStacks();

    /**
     * @return the item storages connected to this network
     */
    List<IItemStorage> getStorages();
}
