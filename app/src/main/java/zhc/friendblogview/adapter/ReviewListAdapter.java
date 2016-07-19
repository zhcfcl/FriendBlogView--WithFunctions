package zhc.friendblogview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import zhc.friendblogview.R;
import zhc.friendblogview.domain.ReviewDomain;

/**
 * Created by zhouh on 2016/4/21.
 */
public class ReviewListAdapter extends BaseAdapter{
    private Context context;//运行上下文
    private ArrayList<ReviewDomain> mList = new ArrayList<>();//数据集合
    private LayoutInflater mInflater;//视图容器
//    TopicDomain topicDomain = new TopicDomain();

    //    private BitmapManager 				bmpManager;
    static class ViewHolder {                //自定义控件集合
        //        public ImageView topicImg;
//        public TextView topicTitle;
        public TextView reviewName;
        public TextView reviewContent;
//        public LinearLayout topicForums;
    }

    /**
     * 实例化Adapter
     *
     */
    public ReviewListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
//        this.bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.widget_dface_loading));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ReviewDomain getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * ListView Item设置
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //自定义视图
        ViewHolder viewHolder = null;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.blog_review_list,parent,false);
            viewHolder.reviewName = (TextView) convertView.findViewById(R.id.list_review_name);
            viewHolder.reviewContent = (TextView)convertView.findViewById(R.id.list_review_content);


            if(null != convertView)
            {
                convertView.setTag(viewHolder);
            }
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ReviewDomain reviewDomain = mList.get(position);
        viewHolder.reviewContent.setTextColor(Color.BLACK);
        viewHolder.reviewName.setTextColor(Color.BLUE);
        viewHolder.reviewName.setText(reviewDomain.getName());
        viewHolder.reviewContent.setText(reviewDomain.getContent());
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    public void add(ReviewDomain item){
        mList.add(item);
    }

    public void addAll(ArrayList<ReviewDomain> items){
        mList=items;
    }

}
