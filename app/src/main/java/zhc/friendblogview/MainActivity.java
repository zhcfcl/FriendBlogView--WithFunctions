package zhc.friendblogview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zhc.friendblogview.adapter.BlogListAdapter;
import zhc.friendblogview.domain.BlogDomain;

public class MainActivity extends AppCompatActivity {

    private ListView friendBlogList;
    private BlogListAdapter blogListAdapter ;
    private List<BlogDomain> blogDomainList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        friendBlogList = (ListView)findViewById(R.id.friendBlog_list);
    }

    private void initEvent(){

    }

    private void initData(){
        initLocalData();
        initBlogData();
    }


    //初始化朋友圈
    private void initBlogData(){
        blogListAdapter = new BlogListAdapter(this.getBaseContext(),blogDomainList);
        blogListAdapter.notifyDataSetChanged();
        friendBlogList.setAdapter(blogListAdapter);
    }


    //初始化示例信息
    private void initLocalData(){
        for(int i=0;i<20;i++){
            BlogDomain blogDomain = new BlogDomain();
            blogDomain.setName("name"+i);
            blogDomain.setInfo("info"+i);
            blogDomain.setContent("this is content "+i);
            blogDomain.setHeadImg(R.mipmap.ic_launcher);
            blogDomainList.add(blogDomain);
        }
    }
}
