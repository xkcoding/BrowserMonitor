package ncm.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by wenxiangzhou214164 on 2017/8/3.
 */
@Entity
@Table(name = "ncm_all_record")
public class NCMAllModel {

    @Id
    @GeneratedValue
    private Long id;

    private String song;

    private Long songId;

    private String singer;

    private Long singerId;

    private Integer rank;//start with 1;

    private Double width;

    private Integer listen;

    private Date date;

    @Column(insertable = false, updatable = false,
            columnDefinition = "DEFAULT CURRENT_TIMESTAMP AFTER")
    private Timestamp createtime;

    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatetime;

    @OneToOne
    @JoinColumn(name = "songId", unique = true, insertable = false, nullable = false, updatable = false)
    private NCMSongModel ncmSongModel;

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void setListen(Integer listen) {
        this.listen = listen;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public NCMSongModel getNcmSongModel() {
        return ncmSongModel;
    }

    public void setNcmSongModel(NCMSongModel ncmSongModel) {
        this.ncmSongModel = ncmSongModel;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public int getListen() {
        return listen;
    }

    public void setlisten(int listen) {
        this.listen = listen;
    }

    @Override
    public String toString() {
        return "NCMAllModel{" +
                "id=" + id +
                ", song='" + song + '\'' +
                ", songId=" + songId +
                ", singer='" + singer + '\'' +
                ", singerId=" + singerId +
                ", rank=" + rank +
                ", width=" + width +
                ", listen=" + listen +
                ", date=" + date +
                '}';
    }
}
