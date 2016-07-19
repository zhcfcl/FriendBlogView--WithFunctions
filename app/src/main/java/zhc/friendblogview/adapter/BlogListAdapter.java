package zhc.friendblogview.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zhc.friendblogview.R;
import zhc.friendblogview.domain.ReviewDomain;
import zhc.friendblogview.domain.BlogDomain;

/**
 * Created by zhc on 16/1/6.
 * 用于ListView的内容适配
 */
public class BlogListAdapter extends BaseAdapter {
    private Context context;
    private int itemViewResource;
    private LayoutInflater mInflater;
    private List<BlogDomain> listItems;
    private List<View> viewCaches = new ArrayList<>();
    private ArrayList<ReviewListAdapter> reviewListAdapters;
    private final static String FLAG="ZHC";

    public BlogListAdapter(Context context, List<BlogDomain> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);    //创建视图容器并设置上下文
        this.listItems = data;
        initViewCaches(data.size());
        initReviewAdapter(data.size());
    }

    //初始化缓存
    private void initViewCaches(int size){
        for(int i=0;i<size;i++){
            View view = null;
            viewCaches.add(view);
        }
    }

    //初始化ReviewAdapters
    private void initReviewAdapter(int size){
        //为每个评论适配一个adapter
        reviewListAdapters = new ArrayList<>();
        for(int i=0;i<size;i++){
            ReviewListAdapter reviewListAdapter = new ReviewListAdapter(context);
            reviewListAdapters.add(reviewListAdapter);
        }
    }

    public final class ViewHolder {
        public ImageView headImg;
        public TextView nameTxt,infoTxt,contentTxt,likeBtn,reviewBtn,shareBtn;
        public EditText reviewTxt;
        public Button reviewReleaseBtn;
        public ListView reviewList;
    }


    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(viewCaches.get(position)==null) {

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.blog_list_view, null);
            holder.headImg = (ImageView) convertView.findViewById(R.id.head_image);
            holder.nameTxt = (TextView) convertView.findViewById(R.id.title_name);
            holder.infoTxt = (TextView) convertView.findViewById(R.id.title_info);
            holder.contentTxt = (TextView) convertView.findViewById(R.id.content_txt);
            holder.likeBtn = (TextView) convertView.findViewById(R.id.id_bottom_like_TxtBtn);
            holder.reviewBtn = (TextView) convertView.findViewById(R.id.id_bottom_review_TxtBtn);
            holder.shareBtn = (TextView) convertView.findViewById(R.id.id_bottom_share_TxtBtn);
            holder.reviewReleaseBtn = (Button)convertView.findViewById(R.id.id_review_releaseBtn);
            holder.reviewTxt = (EditText)convertView.findViewById(R.id.id_review_editText);
            holder.reviewList = (ListView) convertView.findViewById(R.id.id_review_list);


            initData(holder, position);
            initEvnent(holder, position);


            convertView.setTag(holder);


            viewCaches.add(convertView);
            return convertView;
        }else{
            return viewCaches.get(position);
        }
    }


    //初始化控件内容
    private void initData(ViewHolder holder,int position){
        final SharedPreferences sp = context.getSharedPreferences(FLAG, 0);
        boolean isLike = sp.getBoolean("isLike"+position,false);
        if(isLike)
        {
            holder.likeBtn.setText("已赞");
            holder.likeBtn.setEnabled(false);
        }else{
            holder.likeBtn.setText("赞");
            holder.likeBtn.setEnabled(true);
        }

        holder.headImg.setImageResource(listItems.get(position).getHeadImg());
        holder.nameTxt.setText(listItems.get(position).getName());
        holder.contentTxt.setText(listItems.get(position).getContent());
        holder.infoTxt.setText(listItems.get(position).getInfo());
        holder.reviewList.setAdapter(reviewListAdapters.get(position));
    }

    //初始化事件
    private void initEvnent(final ViewHolder holder, final int position){
        final SharedPreferences sp = context.getSharedPreferences(FLAG, 0);
        final SharedPreferences.Editor editor = sp.edit();

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.likeBtn.setText("已赞");
                holder.likeBtn.setEnabled(false);
                editor.putBoolean("isLike"+position,true);
                editor.commit();
            }
        });

        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareMsg("标题","信息标题",listItems.get(position).getContent(),null);
            }
        });

        //初始化评论与分享
        holder.reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.reviewTxt.getVisibility() == View.GONE) {
                    Log.i("reviewClick:", "Gone");
                    holder.reviewTxt.setVisibility(View.VISIBLE);
                    holder.reviewTxt.setFocusable(true);
                    holder.reviewTxt.setFocusableInTouchMode(true);
                    holder.reviewTxt.requestFocus();
                    holder.reviewList.setVisibility(View.VISIBLE);
                    holder.reviewReleaseBtn.setVisibility(View.VISIBLE);
                } else {
                    //再次点击评论关闭软键盘
                    Log.i("reviewClick:", "Visible");
                    holder.reviewTxt.setVisibility(View.GONE);
                    holder.reviewReleaseBtn.setVisibility(View.GONE);
                    closeKeyBoard(v);
                }
            }
        });


        holder.reviewReleaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewDomain reviewDomain = new ReviewDomain();
                reviewDomain.setContent(holder.reviewTxt.getText().toString());
                reviewDomain.setName("localUser");
                reviewListAdapters.get(position).add(reviewDomain);
                reviewListAdapters.get(position).notifyDataSetChanged();

                holder.reviewTxt.setText("");
                holder.reviewTxt.setVisibility(View.GONE);
                holder.reviewReleaseBtn.setVisibility(View.GONE);

                //每次增加与评论高度相同的高度
                ViewGroup.LayoutParams params = holder.reviewList.getLayoutParams();
                params.height += ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, v.getResources().getDisplayMetrics()));
                holder.reviewList.setLayoutParams(params);
                closeKeyBoard(v);
            }
        });
    }


    /**
     * activityTitle: 活动标题
     * msgTitle 消息标题
     * msgText 消息内容
     * imgPath 需要分享图片时的图片路径
    */

    //分享
    public void shareMsg(String activityTitle, String msgTitle, String msgText,
                         String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/png");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    //关闭软键盘
    private void closeKeyBoard(View v) {
        ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
