package zhc.friendblogview.domain;

import android.media.Image;

/**
 * Created by apple on 16/6/18.
 */
public class BlogDomain {

    private int headImg;
    private String name;
    private String info;
    private String content;

    public int getHeadImg() {
        return headImg;
    }

    public void setHeadImg(int headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
