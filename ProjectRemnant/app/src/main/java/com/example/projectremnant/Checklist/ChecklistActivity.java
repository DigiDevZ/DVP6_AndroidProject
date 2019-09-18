package com.example.projectremnant.Checklist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.projectremnant.Checklist.Fragments.CategoryFragment;
import com.example.projectremnant.Checklist.Fragments.ChecklistFragment;
import com.example.projectremnant.Contracts.ItemContracts;
import com.example.projectremnant.DataModels.Items.Armor;
import com.example.projectremnant.DataModels.Items.Item;
import com.example.projectremnant.DataModels.Items.Weapon;
import com.example.projectremnant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChecklistActivity extends AppCompatActivity implements CategoryFragment.CategoryFragmentListener, ChecklistFragment.OnItemClicked {

    private static final String TAG = "ChecklistActivity.TAG";

    private static final String TAG_C = "FragmentC";

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("items");
    private ArrayList<Item>[] mItems = (ArrayList<Item>[]) new ArrayList[6];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_activity);

        //TODO: Need to launch two fragments, the progress fragment and category fragment.
        // Category fragment is a gridview of 3 columns
        // Two Rows.

        //Grab all items from the database and put them in an array of array of item objects.
        gatherItems();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag(TAG_C) != null) {
            //If the fragment being viewed is the checklist fragment, then pop it out of the back stack.
            getSupportFragmentManager().popBackStack("ChecklistFragment",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else {
            super.onBackPressed();
        }
    }

    /**
     * The below methods are used for launching fragments.
     */

    private void updateCategories() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_categories, CategoryFragment.newInstance(mItems))
                .commit();
    }

    private void loadChecklist(int _category, ArrayList<Item> _items) {
        FrameLayout frameLayout = findViewById(R.id.fragment_container_list);
        frameLayout.setVisibility(View.VISIBLE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_list, ChecklistFragment.newInstance(_items, _category), TAG_C)
                .addToBackStack("ChecklistFragment")
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

                //Gather the base items into their arrays.
                gatherBaseItems(amuletsCount, modsCount, ringsCount, traitsCount);

                //Gather the special items into their arrays. Armor and weapons.
                gatherSpecialItems(weaponsCount, armorSetsCount);

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Update the fragments.
                updateCategories();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //This method will gather the base items from the database.
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

    //This method will gather the special items from the database.
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

        //Gather all of the armor sets and store them into the armorSets array.
        for (int i = 0; i < _armorSetsCount; i++) {
            mDatabase.child("armor").child("armorSet_" + (i+1)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long armorSetId = dataSnapshot.child(ItemContracts.KEY_ARMORSETID).getValue(Long.class);
                    String armorSetName = dataSnapshot.child(ItemContracts.KEY_ARMORSETNAME).getValue(String.class);
                    String armorSetBonus = dataSnapshot.child(ItemContracts.KEY_ARMORSETBONUS).getValue(String.class);
                    String armorSetPieces = dataSnapshot.child(ItemContracts.KEY_ARMORPIECES).getValue(String.class);
                    String armorSetBonusStages = dataSnapshot.child(ItemContracts.KEY_ARMORSETBONUSSTAGES).getValue(String.class);
                    String unlockCriteria = dataSnapshot.child(ItemContracts.KEY_UNLOCKCRITERIA).getValue(String.class);
                    String wikiPage = dataSnapshot.child(ItemContracts.KEY_WIKIPAGE).getValue(String.class);

                    Armor armor = new Armor(armorSetName, armorSetId, armorSetPieces, armorSetBonus, armorSetBonusStages, unlockCriteria, wikiPage);
                    armor.getArmorBonusStages();
                    armor.getArmorPieces();

                    armorSets.add(armor);
                    Log.i(TAG, "onDataChange: Armor Set " + armorSetId + " added");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Add the four item array created above to the mItems array.
        // Index: 4 = weapons, 5 = armor sets
        mItems[4] = weapons;
        mItems[5] = armorSets;
    }

    /**
     * Interface Methods for the Category Fragment.
     */


    //TODO: Implement the lists fragment that pulls up and has a list with all of the items of that category.
    // Then the item activity.
    @Override
    public void armorsTapped(ArrayList<Item> _armorSets) {
        Log.i(TAG, "armorsTapped: size: " + _armorSets.size());
        loadChecklist(5, _armorSets);
    }

    @Override
    public void amuletsTapped(ArrayList<Item> _amulets) {
        Log.i(TAG, "amuletsTapped: size: " + _amulets.size());
        loadChecklist(0, _amulets);
    }

    @Override
    public void weaponsTapped(ArrayList<Item> _weapons) {
        Log.i(TAG, "weaponsTapped: size: " + _weapons.size());
        loadChecklist(4, _weapons);
    }

    @Override
    public void modsTapped(ArrayList<Item> _mods) {
        Log.i(TAG, "modsTapped: size: " + _mods.size());
        loadChecklist(1, _mods);
    }

    @Override
    public void traitsTapped(ArrayList<Item> _traits) {
        Log.i(TAG, "traitsTapped: size: " + _traits.size());
        loadChecklist(3, _traits);
    }

    @Override
    public void ringsTapped(ArrayList<Item> _rings) {
        Log.i(TAG, "ringsTapped: size: " + _rings.size());
        loadChecklist(2, _rings);
    }

    /**
     * Interface Methods for the Checklist Fragment.
     */
    
    @Override
    public void itemClicked(int _position, int _category) {
        Log.i(TAG, "itemClicked: ");
    }
}
