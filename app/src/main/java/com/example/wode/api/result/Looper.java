package com.example.wode.api.result;

import java.util.ArrayList;
import java.util.List;

public class Looper {

    public static List<String> imgurls;
    public static List<String> titles;

    public static List<String> imgListString() {
        List<String> imageData = new ArrayList<>();
//        imageData.add("http://d.hiphotos.baidu.com/image/h%3D200/sign=72b32dc4b719ebc4df787199b227cf79/58ee3d6d55fbb2fb48944ab34b4a20a44723dcd7.jpg");
//        imageData.add("http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg");
//        imageData.add("http://pic.4j4j.cn/upload/pic/20130815/5e604404fe.jpg");
//        imageData.add("http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg");
//        imageData.add("http://d.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d22dc28839a442309f79052d265.jpg");
//        imageData.add("http://i0.hdslb.com/bfs/bangumi/image/35d2f9951cf99f38dce232637bae21014819ce6d.jpg@2320w_664h.jpg");
        imageData.add("http://i0.hdslb.com/bfs/archive/c2f5f3f406cde625d61bc8be651e21210b4e99ba.jpg@620w_220h.webp");
        imageData.add("http://i0.hdslb.com/bfs/archive/e78f12c6ee2281a8fcd32a0d1b8c0e1543b04476.png@620w_220h.webp");
        imageData.add("http://i0.hdslb.com/bfs/archive/1425caed30650d7092060d4977ea9835c57cc023.jpg@620w_220h.webp");
        imageData.add("http://i0.hdslb.com/bfs/archive/753f3a3032e477f576b87160e4080efa10563029.jpg@620w_220h.webp");
        return imageData;
    }

    public static List<String> titleListString() {
        List<String> titleData = new ArrayList<>();
 //       titleData.add("第四次我们一起活动");
        titleData.add("强森带队勇闯绝境");
        titleData.add("刷N遍依然感动的高分韩剧");
        titleData.add("我们的存在本身就是谋逆");
        titleData.add("游园日记最终");
        return titleData;
    }
}
