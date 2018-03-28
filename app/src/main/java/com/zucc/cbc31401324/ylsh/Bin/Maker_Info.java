package com.zucc.cbc31401324.ylsh.Bin;

import com.zucc.cbc31401324.ylsh.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/21.
 */

public class Maker_Info implements Serializable {

    private static final long serialVersionUID = 2986906504881950206L;
    /**
     * 图片ID
     */
    private int imgId;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 距离
     */
    private String distance;
    /**
     * 钓点类型
     */
    private String site_type;
    /**
     * 在钓人数
     */
    private String count_people;

    public static List<Maker_Info> maker_infos = new ArrayList<Maker_Info>();

    static
    {
        maker_infos.add(new Maker_Info( R.drawable.pic, "浙江大学城市学院",
                "河流", "0", "209m"));
        maker_infos.add(new Maker_Info( R.drawable.pic, "浙江大学城市学院2",
                "河流","1", "897m"));
        maker_infos.add(new Maker_Info( R.drawable.pic, "浙江大学城市学院3",
                "河流","2", "249m"));
        maker_infos.add(new Maker_Info( R.drawable.pic, "浙江大学城市学院4",
                "河流","3", "679m"));
    }

    public Maker_Info()
    {
    }

    public Maker_Info(int imgId, String name, String site_type, String count_people, String distance)
    {
        super();
        this.imgId = imgId;
        this.name = name;
        this.distance = distance;
        this.site_type = site_type;
        this.count_people = count_people;
    }
    public String getName()
    {
        return name;
    }

    public int getImgId()
    {
        return imgId;
    }

    public void setImgId(int imgId)
    {
        this.imgId = imgId;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public String getSite_type()
    {
        return site_type;
    }

    public void setSite_type(String site_type)
    {
        this.site_type = site_type;
    }

    public void setCount_people(String count_people)
    {
        this.count_people = count_people;
    }

    public String getCount_people()
    {
        return count_people;
    }
}