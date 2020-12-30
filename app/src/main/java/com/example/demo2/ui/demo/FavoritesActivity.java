package com.example.demo2.ui.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.demo2.R;
import com.example.demo2.adapter.FavoritesAdapter;
import com.example.demo2.base.BaseActivity;
import com.example.demo2.interfaces.IBasePresenter;
import com.example.demo2.sql.Favorites;
import com.example.demo2.sql.Realms;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoritesActivity extends BaseActivity {


    @BindView(R.id.recycler_favorites)
    SwipeRecyclerView recyclerFavorites;

    private List<Favorites> list;
    private FavoritesAdapter favoritesAdapter;
    private RealmResults<Favorites> all;


    @Override
    protected int getLayout() {
        return R.layout.activity_favorites;
    }

    @Override
    protected IBasePresenter createPrenter() {
        return null;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(this));
        favoritesAdapter = new FavoritesAdapter(this, list);
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                //                SwipeMenuItem deleteItem = new SwipeMenuItem(MyCarActivity.this); // 各种文字和图标属性设置。
//                leftMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。
                SwipeMenuItem deleteItem = new SwipeMenuItem(FavoritesActivity.this);
                // 各种文字和图标属性设置。
                deleteItem.setBackgroundColor(Color.parseColor("#FF3D39"))
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(200);
                rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        };
        // 设置监听器。
        recyclerFavorites.setSwipeMenuCreator(mSwipeMenuCreator);
        OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                // 左侧还是右侧菜单：
                int direction = menuBridge.getDirection();
                // 菜单在Item中的Position：
                int menuPosition = menuBridge.getPosition();
                //删除数据库
                Realms.getRealm(FavoritesActivity.this).executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        all.get(menuPosition).deleteFromRealm();
                    }
                });
                list.remove(menuPosition);
                favoritesAdapter.notifyDataSetChanged();
            }
        };
        // 菜单点击监听。
        recyclerFavorites.setOnItemMenuClickListener(mItemMenuClickListener);
        //！！！设置完监听和menu监听必须在这个rcy最后设置适配器 不然以下错误：
        //java.lang.IllegalStateException: Cannot set menu creator, setAdapter has already been called.
        recyclerFavorites.setAdapter(favoritesAdapter);
        recyclerFavorites.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        all = Realms.getRealm(FavoritesActivity.this).where(Favorites.class).findAll();
        list.clear();
        list.addAll(all);
        favoritesAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
