package com.example.wode.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wode.R;
import com.example.wode.base.DataBaseActivity;
import com.example.wode.widget.LoadRecyclerView;
import com.example.wode.util.BaseUtil;
import com.example.wode.util.Product;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends DataBaseActivity<ProductContract.HomePraenter> implements ProductContract.HomeView {
    public static final int ADAPTER_TYPE=0;
   private String TAG="商品列表";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] items={"电子产品","学习用品","体育用品","生活用品"};
    private List<LoadRecyclerView> mList;//viewpager里的view
    private ProductPraenter praenter;//初始化parenter
    @Override
    protected boolean IsShowTitle() {
        return true;
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //设置标题
        setTitleText(TAG);
        praenter=new ProductPraenter(this);
        mList=new ArrayList<>();
        /**
         * 总共四个item
         */
        mList.add(new LoadRecyclerView(this));
        mList.add(new LoadRecyclerView(this));
        mList.add(new LoadRecyclerView(this));
        mList.add(new LoadRecyclerView(this));

        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.vpager);
        //   LoadRecyclerView loadRecyclerView=new LoadRecyclerView(getActivity());
        initViewPager();
        praenter.getProducts();

    }
    private void initViewPager() {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return items.length;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {

                container.addView(mList.get(position));
                return mList.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                if (mList!=null)
                {
                    container.removeView(mList.get(position));
                }
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return items[position];
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_list;
    }

    int page=20,pageon=0;
    @Override
    public void initViewPagerItem(List<Product> list, final int i) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mList.get(i).setLayoutManager(gridLayoutManager);
        BaseHomeAdapter productsAdapter=new BaseHomeAdapter(list,this,ADAPTER_TYPE,R.layout.layout_product_item);//创建一个适配器
        mList.get(i).setAdapter(productsAdapter);//设置适配器
        /**
         * 监听item点击事件
         */
        productsAdapter.setOnItemClick(new BaseHomeAdapter.onItemClick() {
            @Override
            public void onClick(BaseUtil pro) {
                toDetail((Product) pro);//去商品详情页
            }
        });
        /**
         * 监听recycleview的下拉事件 触发onLoad函数时证明 已经拉到底部
         */
        mList.get(i).setOnLoadRecycler(new LoadRecyclerView.OnLoadRecycler() {
            @Override
            public void onLoad() {

                praenter.toLoad(i,((BaseHomeAdapter)mList.get(i).getAdapter()).getList().size(),20);//请求数据接口
            }
        });
    }

    @Override
    public void updatePagerItem(List<Product> list, int i) {
        BaseHomeAdapter productsAdapter=((BaseHomeAdapter)mList.get(i).getAdapter());
//        productsAdapter.setList(list);
        productsAdapter.getList().addAll(list);//适配器增加数据
        productsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoad() {
        super.showOnloading(true);
    }

    @Override
    public void hideLoad() {
        showOnloading(false);
    }

    public void toDetail(Product product){//去商品详情页
        Intent intent=new Intent(this,DetailsActivity.class);
        Log.i("ProductList",product.toString());
        intent.putExtra("product",product);//product类已实现序列化接口 所以可以通过intent传递
        startActivity(intent);
    }

    @Override
    public void OnLoading() {
        findViewById(R.id.loadinglayout).setVisibility(View.VISIBLE);
    }
}
