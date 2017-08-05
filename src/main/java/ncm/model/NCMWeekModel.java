package ncm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by wenxiangzhou214164 on 2017/7/29.
 */
@Entity
@Table(name = "ncm_week_record")
public class NCMWeekModel implements Serializable {

    private static final long serialVersionUID = 7669735325610685750L;
    @Id
    @GeneratedValue
    private Long id;

    private String song;

    private Long songId;

    private String singer;

    private Long singerId;

    private Integer rank;//start with 1;

    private Integer times;

    private Date date;

    private Integer incre;

    private Double width;

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void settimes(Integer times) {
        this.times = times;
    }

    public Integer getIncre() {
        return incre;
    }

    public void setIncre(Integer incre) {
        this.incre = incre;
    }

    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatetime;

    @Column(insertable = false, updatable = false,
            columnDefinition = "CURRENT_TIMESTAMP")
    private Timestamp createtime;

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "NCMWeekModel{" +
                "song='" + song + '\'' +
                ", songId=" + songId +
                ", singer='" + singer + '\'' +
                ", singerId=" + singerId +
                ", rank=" + rank +
                ", times=" + times +
                ", date=" + date +
                ", incre=" + incre +
                ", width=" + width +
                ", updatetime=" + updatetime +
                ", createtime=" + createtime +
                '}';
    }
}
