package com.example.projectremnant.Checklist;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectremnant.Checklist.Fragments.CategoryFragment;
import com.example.projectremnant.Contracts.ItemContracts;
import com.example.projectremnant.DataModels.Items.Item;
import com.example.projectremnant.DataModels.Items.Weapon;
import com.example.projectremnant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChecklistActivity extends AppCompatActivity {

    private static final String TAG = "ChecklistActivity.TAG";

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("items");
    private ArrayList<Item>[] mItems = (ArrayList<Item>[]) new ArrayList[6];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_activity);

        //TODO: Need to launch two fragments, the progress fragment and category fragment.
        // Category fragment is a gridview of 3 columns
        // Two Rows.

        updateCategories();

        //TODO: Grab all items from the database and put them in an array of array of item objects.
        gatherItems();


    }

    private void updateCategories() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_categories, CategoryFragment.newInstance())
                .commit();
    }


    //TODO: This may possibly benefit from being put into a service?
    private void gatherItems() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long armorSetsCount = dataSnapshot.child("armor").getChildrenCount();
                long amuletsCount = dataSnapshot.child("amulets").getChildrenCount();
                long modsCount = dataSnapshot.child("mods").getChildrenCount();
                long traitsCount = dataSnapshot.child("traits").getChildrenCount();
                long ringsCount = dataSnapshot.child("rings").getChildrenCount();
                long weaponsCount = dataSnapshot.child("weapons").getChildrenCount();

                long itemTotal = armorSetsCount + amuletsCount + modsCount + traitsCount + ringsCount + weaponsCount;

                Log.i(TAG, "onDataChange: total items in database: " + itemTotal);
                Log.i(TAG, "onDataChange: armor sets: " + armorSetsCount);
                Log.i(TAG, "onDataChange: amulets: " + amuletsCount);
                Log.i(TAG, "onDataChange: mods: " + modsCount);
                Log.i(TAG, "onDataChange: traits: " + traitsCount);
                Log.i(TAG, "onDataChange: rings: " + ringsCount);
                Log.i(TAG, "onDataChange: weapons: " + weaponsCount);

                //TODO: Gather the base items into their arrays.
                gatherBaseItems(amuletsCount, modsCount, ringsCount, traitsCount);

                //TODO: Gather the special items into their arrays. Armor and weapons.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //TODO: Data retrieval works, use the setup from the amulets for the rest of these items, have to add the item to the array as well.
    private void gatherBaseItems(long _amuletCount, long _modCount, long _ringCount, long _traitCount) {

        //Item array lists
        // for each of the base item categories.
        final ArrayList<Item> amulets = new ArrayList<>();
        final ArrayList<Item> mods = new ArrayList<>();
        final ArrayList<Item> rings = new ArrayList<>();
        final ArrayList<Item> traits = new ArrayList<>();

        //Gather the amulets and add them to the amulets array.
        for (int i = 0; i < _amuletCount; i++) {
            mDatabase.child("amulets").child("amulet_" + (i+1)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long amuletId = dataSnapshot.child(ItemContracts.KEY_AMULETID).getValue(Long.class);
                    String amuletName = dataSnapshot.child(ItemContracts.KEY_AMULETNAME).getValue(String.class);
                    String amuletBonus = dataSnapshot.child(ItemContracts.KEY_AMULETBONUS).getValue(String.class);
                    String unlockCriteria = dataSnapshot.child(ItemContracts.KEY_UNLOCKCRITERIA).getValue(String.class);
                    String wikiPage = dataSnapshot.child(ItemContracts.KEY_WIKIPAGE).getValue(String.class);

                    Item amulet = new Item(amuletName, amuletId, amuletBonus, unlockCriteria, wikiPage);
                    amulets.add(amulet);
                    Log.i(TAG, "onDataChange: Amulet " + amuletId + " added");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Gather the mods and add them to the mods array.
        for (int i = 0; i < _modCount; i++) {
            mDatabase.child("mods").child("mod_" + (i+1)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long modId = dataSnapshot.child(ItemContracts.KEY_MODID).getValue(Long.class);
                    String modName = dataSnapshot.child(ItemContracts.KEY_MODNAME).getValue(String.class);
                    String modBonus = dataSnapshot.child(ItemContracts.KEY_MODBONUS).getValue(String.class);
                    String unlockCriteria = dataSnapshot.child(ItemContracts.KEY_UNLOCKCRITERIA).getValue(String.class);
                    String wikiPage = dataSnapshot.child(ItemContracts.KEY_WIKIPAGE).getValue(String.class);

                    Item mod = new Item(modName, modId, modBonus, unlockCriteria, wikiPage);
                    mods.add(mod);
                    Log.i(TAG, "onDataChange: Mod " + modId + " added");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Gather the rings and add them to the rings array.
        for (int i = 0; i < _ringCount; i++) {
            mDatabase.child("rings").child("ring_" + (i+1)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long ringId = dataSnapshot.child(ItemContracts.KEY_RINGID).getValue(Long.class);
                    String ringName = dataSnapshot.child(ItemContracts.KEY_RINGNAME).getValue(String.class);
                    String ringBonus = dataSnapshot.child(ItemContracts.KEY_RINGBONUS).getValue(String.class);
                    String unlockCriteria = dataSnapshot.child(ItemContracts.KEY_UNLOCKCRITERIA).getValue(String.class);
                    String wikiPage = dataSnapshot.child(ItemContracts.KEY_WIKIPAGE).getValue(String.class);

                    Item ring = new Item(ringName, ringId, ringBonus, unlockCriteria, wikiPage);
                    rings.add(ring);
                    Log.i(TAG, "onDataChange: Ring " + ringId + " added");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Gather the traits and add them to the traits array.
        for (int i = 0; i < _traitCount; i++) {
            mDatabase.child("traits").child("trait_" + (i+1)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long traitId = dataSnapshot.child(ItemContracts.KEY_TRAITID).getValue(Long.class);
                    String traitName = dataSnapshot.child(ItemContracts.KEY_TRAITNAME).getValue(String.class);
                    String traitBonus = dataSnapshot.child(ItemContracts.KEY_TRAITBONUS).getValue(String.class);
                    String unlockCriteria = dataSnapshot.child(ItemContracts.KEY_UNLOCKCRITERIA).getValue(String.class);
                    String wikiPage = dataSnapshot.child(ItemContracts.KEY_WIKIPAGE).getValue(String.class);

                    Item trait = new Item(traitName, traitId, traitBonus, unlockCriteria, wikiPage);
                    traits.add(trait);
                    Log.i(TAG, "onDataChange: Trait " + traitId + " added");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Add the four item array created above to the mItems array.
        // Index: 0 = amulets, 1 = mods, 2 = rings, 3 = traits
        mItems[0] = amulets;
        mItems[1] = mods;
        mItems[2] = rings;
        mItems[3] = traits;
    }

    private void gatherSpecialItems(long _weaponsCount, long _armorSetsCount) {

        //Item array lists 
        // for each of the special item categories.
        final ArrayList<Item> armorSets = new ArrayList<>();
        final ArrayList<Item> weapons = new ArrayList<>();

        //Gather all of the weapons and store them into the weapons array.
        for (int i = 0; i < _weaponsCount; i++) {
            mDatabase.child("weapons").child("weapon_" + (i+1)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long weaponId = dataSnapshot.child(ItemContracts.KEY_WEAPONSID).getValue(Long.class);
                    String weaponName = dataSnapshot.child(ItemContracts.KEY_WEAPONSNAME).getValue(String.class);
                    Long weaponCategory = dataSnapshot.child(ItemContracts.KEY_WEAPONSCATEGORY).getValue(Long.class);
                    String unlockCriteria = dataSnapshot.child(ItemContracts.KEY_UNLOCKCRITERIA).getValue(String.class);
                    String wikiPage = dataSnapshot.child(ItemContracts.KEY_WIKIPAGE).getValue(String.class);

                    Weapon weapon = new Weapon(weaponName, unlockCriteria, weaponId, wikiPage, weaponCategory);
                    weapons.add(weapon);
                    Log.i(TAG, "onDataChange: Weapon " + weaponId + " added");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



        mItems[4] = weapons;
    }

}
