package com.example.projectremnant.ItemInfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectremnant.DataModels.Items.Item;
import com.example.projectremnant.R;

import org.w3c.dom.Text;

public class ItemDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ItemDetailsActivity";

    //TODO: Need to figure out why my views are null.
    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    private TextView tv_itemName;
    private TextView tv_itemBonus;
    private TextView tv_itemUnlockCriteria;
    private TextView tv_itemCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_activitiy);

        tv_itemName = findViewById(R.id.tv_itemName);
        tv_itemBonus = findViewById(R.id.tv_itemBonus);
        tv_itemUnlockCriteria = findViewById(R.id.tv_itemUnlockCriteria);
        tv_itemCategory = findViewById(R.id.tv_itemCategory);

        CheckBox cb_itemOwned = findViewById(R.id.cb_itemOwned);
        Button btn_viewWiki = findViewById(R.id.btn_viewWiki);



        //Need to get the starter intent that will have the item information and the category.
        Intent i = getIntent();
        if(i != null) {
            final Item item = (Item) i.getSerializableExtra(EXTRA_ITEM);
            int category = i.getIntExtra(EXTRA_CATEGORY, -1);

            //Set the click listener for the button.
            btn_viewWiki.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: Starting intent to view. wiki page: " + item.getItemWikiPage());
                    //Intent to the wikiPage of an item.
                    String uriString = item.getItemWikiPage();
                    Uri uri = Uri.parse(uriString);

                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(viewIntent);
                }
            });


            String itemCategory = "";
            switch (category) {
                case 0:
                    itemCategory = getString(R.string.item_details_activity_category_amulet);
                    displayBasicItemInfo(item, itemCategory);
                    break;
                case 1:
                    itemCategory = getString(R.string.item_details_activity_category_mod);
                    displayBasicItemInfo(item, itemCategory);
                    break;
                case 2:
                    itemCategory = getString(R.string.item_details_activity_category_ring);
                    displayBasicItemInfo(item, itemCategory);
                    break;
                case 3:
                    itemCategory = getString(R.string.item_details_activity_category_trait);
                    displayBasicItemInfo(item, itemCategory);
                    break;
                //TODO: if the category is armor or weapons, split off from here.
                case 4:
                    itemCategory = getString(R.string.item_details_activity_category_weapon);

                    break;
                case 5:
                    itemCategory = getString(R.string.item_details_activity_category_armor);
                    
                    break;
                default:
                    break;
            }
            //End of intent null check.
        }
        //End of on create.
    }


    //TODO: May make two more methods that handle the armor and weapons since those display more.
    private void displayBasicItemInfo(Item _item, String _category) {
        //Display the info from the item.
        tv_itemName.setText(_item.getItemName());
        tv_itemUnlockCriteria.setText(_item.getItemUnlockCriteria());
        tv_itemBonus.setText(_item.getItemBonus());
        tv_itemCategory.setText(_category);
    }

}
